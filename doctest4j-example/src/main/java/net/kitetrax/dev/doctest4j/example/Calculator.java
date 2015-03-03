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
   * <table id="addexample">
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

  /**
   * Calculates the product of multiplier and multiplicant.<br/>
   * <br/>
   * If the multiplier is <span id="multiplierA">2</span>, then following table gives an overview of
   * expected results:
   * 
   * <pre>
   * <table id="multiplyexampleA">
   *   <tr><th>multiplicant</th><th>product</th></tr>
   *   <tr><td>2</td><td>4</td></tr>
   *   <tr><td>3</td><td>6</td></tr>
   *   <tr><td>7</td><td>14</td></tr>
   * </table>
   * </pre>
   * 
   * And for a multiplier of <span id="multiplierB">5</span>, the following table gives you an idea
   * of expected products:
   * 
   * <pre>
   * <table id="multiplyexampleB">
   *   <tr><td>2</td><td>10</td></tr>
   *   <tr><td>3</td><td>15</td></tr>
   *   <tr><td>7</td><td>35</td></tr>
   * </table>
   * </pre>
   * 
   * @param multiplier
   * @param multiplicant
   * @return
   */
  public int multiply(int multiplier, int multiplicant) {
    return multiplier * multiplicant;
  }

}
