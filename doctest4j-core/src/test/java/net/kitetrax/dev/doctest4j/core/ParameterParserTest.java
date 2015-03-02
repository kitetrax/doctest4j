package net.kitetrax.dev.doctest4j.core;

import static org.junit.Assert.assertArrayEquals;
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

  @Test
  public void testParseTableValue() throws Throwable {
    ParameterParser fixture = new ParameterParser("parser/example.dt4j");

    Parameter parameter = parameter("#data", ParameterType.TABLE);

    Table expected =
        Table.builder().withRow(new Object[] {"123", "456", "789"})
            .withRow(new Object[] {"abc", "def", "geh"}).build();

    assertEqualTables(expected, (Table) fixture.getValue(parameter));
  }

  private Parameter parameter(String xpath, ParameterType type) {
    Parameter parameter = mock(Parameter.class);
    when(parameter.selector()).thenReturn(xpath);
    when(parameter.type()).thenReturn(type);
    return parameter;
  }

  private void assertEqualTables(Table expected, Table actual) {
    assertEquals("both tables should contain the same amount of rows", expected.getRows().length,
        actual.getRows().length);

    for (int i = 0; i < expected.getRows().length; i++) {
      assertArrayEquals("both rows should contain the same data", expected.getRows()[i],
          actual.getRows()[i]);
    }
  }

}
