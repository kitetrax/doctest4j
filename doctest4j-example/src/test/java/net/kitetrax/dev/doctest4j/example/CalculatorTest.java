package net.kitetrax.dev.doctest4j.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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
  @Parameter(xpath = "//table/thead/th[0]", type = ParameterType.SINGLE_VALUE)
  @NameTemplate("assertEquals({0}, summand1)")
  public void verifyHeader1(String value) {
    assertEquals(value, "summand1");
  }


  @Test
  @Parameter(xpath = "//table/thead/th[0]", type = ParameterType.SINGLE_VALUE)
  @Parameter(xpath = "//table/thead/th[0]", type = ParameterType.SINGLE_VALUE)
  @NameTemplate("assertNotEquals({0}, {1})")
  public void verifyHeader2(String value1, String value2) {
    assertNotEquals(value1, value2);
  }

}
