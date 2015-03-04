package net.kitetrax.dev.doctest4j.core;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class ParameterPermutatorTest {

  @Test
  public void permuteOnlySingleValues() {
    Object[] input = new Object[] {"A", "B", "C"};

    Object[][] expected = new Object[][] {input};

    ParameterPermutator fixture = new ParameterPermutator();
    Object[][] actual = fixture.permute(input);

    assertArrayEquals(expected, actual);
  }

  @Test
  public void permuteTableValues() {
    Object[] input =
        new Object[] {Table.builder().withRow(new Object[] {"A", "B", "C"})
            .withRow(new Object[] {"D", "E", "F"}).build()};

    Object[][] expected = new Object[][] { {"A", "B", "C"}, {"D", "E", "F"}};

    ParameterPermutator fixture = new ParameterPermutator();
    Object[][] actual = fixture.permute(input);

    assertArrayEquals(expected, actual);
  }

  @Test
  public void permuteTableAndSingleValue() {
    Object[] input =
        new Object[] {
            "1",
            "2",
            Table.builder().withRow(new Object[] {"A", "B", "C"})
                .withRow(new Object[] {"D", "E", "F"}).build()};

    Object[][] expected = new Object[][] { {"1", "2", "A", "B", "C"}, {"1", "2", "D", "E", "F"}};

    ParameterPermutator fixture = new ParameterPermutator();
    Object[][] actual = fixture.permute(input);

    assertArrayEquals(expected, actual);
  }

  @Test
  public void permuteTableAndSingleValueAtBeginningAndEnd() {
    Object[] input =
        new Object[] {
            "1",
            Table.builder().withRow(new Object[] {"A", "B", "C"})
                .withRow(new Object[] {"D", "E", "F"}).build(), "2"};

    Object[][] expected = new Object[][] { {"1", "A", "B", "C", "2"}, {"1", "D", "E", "F", "2"}};

    ParameterPermutator fixture = new ParameterPermutator();
    Object[][] actual = fixture.permute(input);

    assertArrayEquals(expected, actual);
  }

  @Test
  public void permuteTableAndTableAndSingleValues() {
    Object[] input =
        new Object[] {
            "1",
            Table.builder().withRow(new Object[] {"A", "B"}).withRow(new Object[] {"C", "D"})
                .build(),
            "2",
            "3",
            Table.builder().withRow(new Object[] {"w", "x"}).withRow(new Object[] {"y", "z"})
                .build(), "4"};

    Object[][] expected =
        new Object[][] { {"1", "A", "B", "2", "3", "w", "x", "4"},
            {"1", "C", "D", "2", "3", "w", "x", "4"}, {"1", "A", "B", "2", "3", "y", "z", "4"},
            {"1", "C", "D", "2", "3", "y", "z", "4"}};

    ParameterPermutator fixture = new ParameterPermutator();
    Object[][] actual = fixture.permute(input);

    assertArrayEquals(expected, actual);
  }
}
