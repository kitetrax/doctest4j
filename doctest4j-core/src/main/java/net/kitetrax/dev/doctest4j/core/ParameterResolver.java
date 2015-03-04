package net.kitetrax.dev.doctest4j.core;

import org.junit.runners.model.FrameworkMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParameterResolver {

  private final ParameterParser parameterParser;

  private final ParameterPermutator parameterPermutor;

  public ParameterResolver(Class<?> classUnderTest) throws IOException {
    parameterParser = new ParameterParser(classUnderTest.getName().replace('.', '/') + ".dt4j");
    parameterPermutor = new ParameterPermutator();
  }

  public Object[][] getParameters(FrameworkMethod method) {
    List<Parameter> annotations = getParameterAnnotations(method);
    Object[] parameterRow = new Object[annotations.size()];
    for (int i = 0; i < annotations.size(); i++) {
      parameterRow[i] = parameterParser.getValue(annotations.get(i));
    }
    return parameterPermutor.permute(parameterRow);
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
