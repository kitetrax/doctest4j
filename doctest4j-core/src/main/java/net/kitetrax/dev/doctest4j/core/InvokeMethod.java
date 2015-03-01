package net.kitetrax.dev.doctest4j.core;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class InvokeMethod extends Statement {

  private final FrameworkMethod testMethod;

  private final Object target;

  private final Object[] parameters;


  public InvokeMethod(FrameworkMethod testMethod, Object target, Object[] parameters) {
    this.testMethod = testMethod;
    this.target = target;
    this.parameters = parameters;
  }

  @Override
  public void evaluate() throws Throwable {
    testMethod.invokeExplosively(target, parameters);
  }

}
