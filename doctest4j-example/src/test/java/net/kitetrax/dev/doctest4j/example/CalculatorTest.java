package net.kitetrax.dev.doctest4j.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import net.kitetrax.dev.doctest4j.core.ClassUnderTest;
import net.kitetrax.dev.doctest4j.core.Doctest4jRunner;
import net.kitetrax.dev.doctest4j.core.NameTemplate;
import net.kitetrax.dev.doctest4j.core.Parameter;
import net.kitetrax.dev.doctest4j.core.ParameterType;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Doctest4jRunner.class)
@ClassUnderTest(Calculator.class)
public class CalculatorTest {

  @Test
  public void justANormalTestMethod() {
    assertTrue(true);
  }

  @Test
  @Parameter(selector = "#addexample tr:eq(0) td:eq(0)", type = ParameterType.SINGLE_VALUE)
  @Parameter(selector = "#addexample tr:eq(0) td:eq(1)", type = ParameterType.SINGLE_VALUE)
  @Parameter(selector = "#addexample tr:eq(0) td:eq(2)", type = ParameterType.SINGLE_VALUE)
  @NameTemplate("{0} + {1} = {2}")
  public void testAdditionWithSingleValues(String summand1, String summand2, String expected) {
    Calculator calc = new Calculator();
    Integer actual = calc.add(Integer.valueOf(summand1), Integer.valueOf(summand2));
    assertEquals(Integer.valueOf(expected), actual);
  }

  @Test
  @Parameter(selector = "#addexample", type = ParameterType.TABLE)
  @NameTemplate("{0} + {1} = {2}")
  public void testAdditionWithTable(String summand1, String summand2, String expected) {
    Calculator calc = new Calculator();
    Integer actual = calc.add(Integer.valueOf(summand1), Integer.valueOf(summand2));
    assertEquals(Integer.valueOf(expected), actual);
  }

  @Test
  @Parameter(selector = "#multiplierA", type = ParameterType.SINGLE_VALUE)
  @Parameter(selector = "#multiplyexampleA", type = ParameterType.TABLE)
  @NameTemplate("{0} * {1} = {2}")
  public void testMultiply2ByX(String multiplier, String multiplicant, String expected) {
    Calculator calc = new Calculator();
    Integer actual = calc.multiply(Integer.valueOf(multiplier), Integer.valueOf(multiplicant));
    assertEquals(Integer.valueOf(expected), actual);
  }

  @Test
  @Parameter(selector = "#multiplierB", type = ParameterType.SINGLE_VALUE)
  @Parameter(selector = "#multiplyexampleB", type = ParameterType.TABLE)
  @NameTemplate("{0} * {1} = {2}")
  public void testMultiply5ByX(String multiplier, String multiplicant, String expected) {
    Calculator calc = new Calculator();
    Integer actual = calc.multiply(Integer.valueOf(multiplier), Integer.valueOf(multiplicant));
    assertEquals(Integer.valueOf(expected), actual);
  }

}
