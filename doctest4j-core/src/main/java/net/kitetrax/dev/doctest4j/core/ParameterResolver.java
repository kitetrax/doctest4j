package net.kitetrax.dev.doctest4j.core;

import org.junit.runners.model.FrameworkMethod;

public class ParameterResolver {

  private Class<?> classUnderTest;

  public ParameterResolver(Class<?> classUnderTest) {
    this.classUnderTest = classUnderTest;
  }

  public String[][] getParameters(FrameworkMethod method) {
    return new String[][] { {"abc", "def"}, {"xyz", "qwe"}};
  }
}
