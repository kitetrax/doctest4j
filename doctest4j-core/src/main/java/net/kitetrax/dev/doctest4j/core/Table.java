package net.kitetrax.dev.doctest4j.core;


public class Table {

  private final Object[][] rows;

  public static Builder builder() {
    return new Builder();
  }

  private Table(Object[][] rows) {
    this.rows = rows;
  }

  public Object[][] getRows() {
    return rows;
  }

  public static class Builder {

    private Object[][] rows = new Object[0][];

    public Builder withRow(Object[] row) {
      Object[][] newRows = new Object[rows.length + 1][];
      for (int i = 0; i < rows.length; i++) {
        newRows[i] = rows[i];
      }
      newRows[rows.length] = row;
      rows = newRows;
      return this;
    }

    public Table build() {
      return new Table(rows);
    }

  }

}
