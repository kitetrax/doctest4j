package net.kitetrax.dev.doctest4j.core;

import java.util.Arrays;

public class ParameterPermutator {

  public Object[][] permute(Object[] input) {
    Object[][] result = new Object[1][input.length];
    int column = 0;
    for (int i = 0; i < input.length; i++) {
      if (input[i] instanceof Table) {
        result = permuteTable((Table) input[i], result, column);
        column += ((Table) input[i]).getRows()[0].length;
      } else {
        addSingleValue(input[i], result, column++);
      }
    }
    return result;
  }

  private void addSingleValue(Object value, Object[][] data, int column) {
    for (int i = 0; i < data.length; i++) {
      data[i][column] = value;
    }
  }

  private Object[][] permuteTable(Table table, Object[][] data, int column) {
    int steps = data.length;
    Object[][] rows = table.getRows();
    Object[][] result = extendArray(data, rows.length, rows[0].length - 1);

    int row = 0;
    for (int i = 0; i < rows.length; i++) {
      for (int j = 0; j < steps; j++) {
        for (int k = 0; k < rows[i].length; k++) {
          result[row][column + k] = rows[i][k];
        }
        ++row;
      }
    }

    return result;
  }

  private Object[][] extendArray(Object[][] data, int multiplyRows, int addColumns) {
    Object[][] result = new Object[data.length * multiplyRows][data[0].length + addColumns];
    int steps = data.length;

    int row = 0;
    for (int i = 0; i < multiplyRows; i++) {
      for (int j = 0; j < steps; j++) {
        result[row++] = Arrays.copyOf(data[j], data[j].length + addColumns);
      }
    }

    return result;
  }
}
