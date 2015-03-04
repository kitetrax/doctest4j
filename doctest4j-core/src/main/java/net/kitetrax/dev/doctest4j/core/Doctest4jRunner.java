package net.kitetrax.dev.doctest4j.core;

import org.junit.Test;
import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.internal.runners.statements.Fail;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Doctest4jRunner extends BlockJUnit4ClassRunner {

  private static final String TEST_CLASS_SUFFIX = "Test";

  private final ParameterResolver parameterResolver;

  private final ConcurrentHashMap<FrameworkMethod, Description> descriptions =
      new ConcurrentHashMap<>();

  public Doctest4jRunner(Class<?> testClass) throws Throwable {
    super(testClass);
    parameterResolver = new ParameterResolver(getClassUnderTest());
  }

  @Override
  protected Description describeChild(FrameworkMethod method) {
    Description description = descriptions.get(method);

    if (null == description) {
      if (isDoctest4jAnnotatedMethod(method)) {
        description = describeDoctest4jChildren(method);
      } else {
        description = super.describeChild(method);
      }
      descriptions.putIfAbsent(method, description);
    }

    return description;
  }

  protected Description describeDoctest4jChildren(FrameworkMethod method) {
    Object[][] parameters = parameterResolver.getParameters(method);

    Description description =
        Description.createSuiteDescription(method.getName(), method.getAnnotations());

    for (int i = 0; i < parameters.length; i++) {
      description.addChild(describeDoctest4jChild(method, parameters[i], i + 1));
    }

    return description;
  }

  protected Description describeDoctest4jChild(FrameworkMethod method, Object[] parameters,
      int testNumber) {
    return Description.createTestDescription(getName(),
        getDoctest4jChildName(method, parameters, testNumber), method.getAnnotations());
  }

  protected String getDoctest4jChildName(FrameworkMethod method, Object[] parameters, int testNumber) {
    NameTemplate annotation = method.getAnnotation(NameTemplate.class);
    String name =
        method.getName() + " (" + testNumber + ")"
            + (null == annotation ? "" : ": " + annotation.value());
    for (int i = 0; i < parameters.length; i++) {
      name = name.replace("{" + i + "}", parameters[i].toString());
    }
    return name;
  }

  @Override
  protected void runChild(FrameworkMethod method, RunNotifier notifier) {
    if (isDoctest4jAnnotatedMethod(method)) {
      runDoctest4jChildren(method, notifier);
    } else {
      super.runChild(method, notifier);
    }
  }

  protected void runDoctest4jChildren(FrameworkMethod method, RunNotifier notifier) {
    Description description = describeChild(method);

    List<Description> childDescriptions = description.getChildren();

    Object[][] parameters = parameterResolver.getParameters(method);

    for (int i = 0; i < parameters.length; i++) {
      runLeaf(doctest4jMethodBlock(method, parameters[i]), childDescriptions.get(i), notifier);
    }
  }

  protected Statement doctest4jMethodBlock(FrameworkMethod method, Object[] parameters) {
    Object test;
    try {
      test = new ReflectiveCallable() {
        @Override
        protected Object runReflectiveCall() throws Throwable {
          return createTest();
        }
      }.run();
    } catch (Throwable e) {
      return new Fail(e);
    }

    Statement statement = doctest4jMethodInvoker(method, test, parameters);
    statement = possiblyExpectingExceptions(method, test, statement);
    statement = withBefores(method, test, statement);
    statement = withAfters(method, test, statement);
    return statement;
  }

  protected Statement doctest4jMethodInvoker(FrameworkMethod method, Object test,
      Object[] parameters) {
    return new InvokeMethod(method, test, parameters);
  }

  @Override
  protected void validateTestMethods(List<Throwable> errors) {
    validatePublicVoidNoArgDefaultTestMethods(Test.class, false, errors);
    validatePublicVoidMethods(Parameters.class, false, errors);
    validatePublicVoidMethods(Parameter.class, false, errors);
  }

  protected void validatePublicVoidNoArgDefaultTestMethods(Class<? extends Annotation> annotation,
      boolean isStatic, List<Throwable> errors) {
    List<FrameworkMethod> methods = getTestClass().getAnnotatedMethods(annotation);

    for (FrameworkMethod eachTestMethod : methods) {
      if (!isDoctest4jAnnotatedMethod(eachTestMethod)) {
        eachTestMethod.validatePublicVoidNoArg(isStatic, errors);
      }
    }
  }

  protected void validatePublicVoidMethods(Class<? extends Annotation> annotation,
      boolean isStatic, List<Throwable> errors) {
    List<FrameworkMethod> methods = getTestClass().getAnnotatedMethods(annotation);

    for (FrameworkMethod eachTestMethod : methods) {
      eachTestMethod.validatePublicVoid(isStatic, errors);
    }
  }

  private boolean isDoctest4jAnnotatedMethod(FrameworkMethod method) {
    return null != method.getAnnotation(Parameters.class)
        || null != method.getAnnotation(Parameter.class);
  }

  private Class<?> getClassUnderTest() throws InitializationError {
    ClassUnderTest annotation = getTestClass().getAnnotation(ClassUnderTest.class);
    if (null == annotation) {
      String testClassName = getTestClass().getJavaClass().getCanonicalName();
      if (testClassName.endsWith(TEST_CLASS_SUFFIX)) {
        try {
          return Doctest4jRunner.class.getClassLoader().loadClass(
              testClassName.substring(0, testClassName.length() - TEST_CLASS_SUFFIX.length()));
        } catch (ClassNotFoundException e) {
        }
      }
      throw new InitializationError(
          "Could not find class under test. Do your classes follow the same naming convention?");
    } else {
      return annotation.value();
    }
  }
}
