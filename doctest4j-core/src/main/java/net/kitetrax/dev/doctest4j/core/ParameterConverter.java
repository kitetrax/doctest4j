package net.kitetrax.dev.doctest4j.core;

import org.apache.commons.beanutils.ConvertUtils;
import org.junit.runners.model.FrameworkMethod;

import java.lang.reflect.Parameter;

public class ParameterConverter {

  public Object[] convert(FrameworkMethod method, Object[] params) {
    Object[] converted = new Object[params.length];

    Parameter[] methodParams = method.getMethod().getParameters();
    for (int i = 0; i < methodParams.length; i++) {
      Parameter methodParam = methodParams[i];
      converted[i] = ConvertUtils.convert(params[i], methodParam.getType());
    }

    return converted;
  }

}
