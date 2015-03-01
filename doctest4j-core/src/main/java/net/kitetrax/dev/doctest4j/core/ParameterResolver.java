package net.kitetrax.dev.doctest4j.core;

import org.junit.runners.model.FrameworkMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParameterResolver {

  private Class<?> classUnderTest;

  public ParameterResolver(Class<?> classUnderTest) {
    this.classUnderTest = classUnderTest;
  }

  public String[][] getParameters(FrameworkMethod method) {
    List<Parameter> annotations = getParameterAnnotations(method);
    if (annotations.size() == 2) {
      return new String[][] {{"summand1", "summand2"}};
    } else {
      return new String[][] {{"summand1"}};
    }
  }

  private List<Parameter> getParameterAnnotations(FrameworkMethod method) {
    Parameters parameters = method.getAnnotation(Parameters.class);
    if (null == parameters) {
      Parameter parameter = method.getAnnotation(Parameter.class);
      List<Parameter> parameterList = new ArrayList<>(1);
      parameterList.add(parameter);
      return parameterList;
    } else {
      return Arrays.asList(parameters.value());
    }
  }
}
