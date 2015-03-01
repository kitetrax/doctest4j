package net.kitetrax.dev.doctest4j.example;

/**
 * A simple calculator.
 */
public class Calculator {

  /**
   * Calculates the sum of the summand 1 and 2.<br/>
   * <br/>
   * Examples:
   * 
   * <pre>
   * <table>
   *   <thead>
   *    <tr><th>summand1</th><th>summand2</th><th>sum</th></tr>
   *   </thead>
   *   <tr><td>1</td><td>1</td><td>2</td></tr>
   *   <tr><td>10</td><td>555</td><td>565</td></tr>
   *   <tr><td>4</td><td>6</td><td>10</td></tr>
   * </table>
   * </pre>
   * 
   * @param summand1
   * @param summand2
   * @return
   */
  public int add(int summand1, int summand2) {
    return summand1 + summand2;
  }

}
