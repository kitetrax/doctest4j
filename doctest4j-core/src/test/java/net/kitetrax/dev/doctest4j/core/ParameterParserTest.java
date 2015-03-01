package net.kitetrax.dev.doctest4j.core;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import java.io.IOException;


public class ParameterParserTest {

  @Test
  public void testParseSingleValue() throws Throwable {
    ParameterParser fixture = new ParameterParser("parser/example.dt4j");

    Parameter parameter1 = parameter("#param1", ParameterType.SINGLE_VALUE);
    Parameter parameter2 = parameter("#param2", ParameterType.SINGLE_VALUE);

    assertEquals("value1", fixture.getValue(parameter1));
    assertEquals("value2", fixture.getValue(parameter2));
  }

  @Test
  public void testParseSingleValueShouldNotFailForBrokenFile() throws IOException {
    ParameterParser fixture = new ParameterParser("parser/broken.dt4j");

    Parameter parameter1 = parameter("#param1", ParameterType.SINGLE_VALUE);

    assertEquals("value1", fixture.getValue(parameter1));
  }

  private Parameter parameter(String xpath, ParameterType type) {
    Parameter parameter = mock(Parameter.class);
    when(parameter.selector()).thenReturn(xpath);
    when(parameter.type()).thenReturn(type);
    return parameter;
  }

}
