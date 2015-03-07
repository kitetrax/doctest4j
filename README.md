# doctest4j
doctest4j is a simple yet powerful way of enhancing your unit tests.
The idea behind this library is to provide valueable test data and a
good and reliable documentation at the same time - as close as possible
toward the code.

Though the name indicates similarities with pythons doctest, it follows
it's own approach and is more influenced by concordion and cucumber.

## usage

The following simple example should give you a rough idea of how to use
doctest4j.

#### 1. set up your project

doctest4j comes along with the core library and a maven plugin which will provide access to the source code. Before you can set up the maven dependencies, you first have to add a new plugin group to mavens settings.xml.

```
  <pluginGroups>
    <pluginGroup>net.kitetrax.dev.doctest4j</pluginGroup>
  </pluginGroups>
```

Afterwards you can add the dependency to doctest4j-core to your project pom.xml and add the doctest4j-maven-plugin to your maven execution.

```xml
  <properties>
    <doctest4j.version>1.0.0-SNAPSHOT</doctest4j>
  </properties>
  <dependencies>
    <dependency>
      <groupId>net.kitetrax.dev.doctest4j</groupId>
      <artifactId>doctest4j-core</artifactId>
      <version>${doctest4j.version}</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
  <build>
    <plugins>
      <plugin>
        <groupId>net.kitetrax.dev.doctest4j</groupId>
        <artifactId>doctest4j-maven-plugin</artifactId>
        <version>${doctest4j.version}</version>
        <executions>
          <execution>
            <goals>
              <goal>generateTestData</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </build>
```

#### 2. create api and documentation

```java
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
    // TODO implement addition
    return 0;
  }
  
}
```
Did you notice the `id="addexample"`? This will help us to reference the test data.

#### 3. write your tests

```java
import static org.junit.Assert.assertEquals;

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
  @Parameter(selector = "#addexample", type = ParameterType.TABLE)
  @NameTemplate("{0} + {1} = {2}")
  public void addShouldCalculateCorrectSum(int summand1, int summand2, int expected) {
    Calculator calc = new Calculator();
    int actual = calc.add(summand1, summand2);
    assertEquals(expected, actual);
  }
  
}
```

#### 4. execute tests, fail, implement

Afterwards, you are ready to run your tests by using the normal `mvn test` command. Since you did not yet implement the add method, you should see three tests failing. Simply replacing `return 0;` by `return summand1 + summand2;` should get your tests green.

Yay, you finished your first tests using doctest4j.
