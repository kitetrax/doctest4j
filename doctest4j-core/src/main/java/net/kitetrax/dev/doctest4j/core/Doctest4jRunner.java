package net.kitetrax.dev.doctest4j.core;

import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.lang.annotation.Annotation;
import java.util.List;

public class Doctest4jRunner extends BlockJUnit4ClassRunner {

  private static final String TEST_CLASS_SUFFIX = "Test";

  private final ParameterResolver parameterResolver;

  public Doctest4jRunner(Class<?> testClass) throws Throwable {
    super(testClass);
    parameterResolver = new ParameterResolver(getClassUnderTest());
  }

  @Override
  protected Description describeChild(FrameworkMethod method) {
    if (isDoctest4jAnnotatedMethod(method)) {
      return describeDoctest4jChildren(method);
    } else {
      return super.describeChild(method);
    }
  }

  protected Description describeDoctest4jChildren(FrameworkMethod method) {
    String[][] parameters = parameterResolver.getParameters(method);

    Description description =
        Description.createSuiteDescription(method.getName(), method.getAnnotations());

    for (String[] p : parameters) {
      description.addChild(describeDoctest4jChild(method, p));
    }

    return description;
  }

  protected Description describeDoctest4jChild(FrameworkMethod method, String[] parameters) {
    return Description.createTestDescription(getName(), getDoctest4jChildName(method, parameters),
        method.getAnnotations());
  }

  protected String getDoctest4jChildName(FrameworkMethod method, String[] parameters) {
    NameTemplate annotation = method.getAnnotation(NameTemplate.class);
    String name = null == annotation ? method.getName() : annotation.value();
    for (int i = 0; i < parameters.length; i++) {
      name = name.replace("{" + i + "}", parameters[i]);
    }
    return name;
  }

  @Override
  protected void runChild(FrameworkMethod method, RunNotifier notifier) {
    if (isDoctest4jAnnotatedMethod(method)) {

    } else {
      super.runChild(method, notifier);
    }
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
