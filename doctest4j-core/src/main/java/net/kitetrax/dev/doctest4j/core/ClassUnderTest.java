package net.kitetrax.dev.doctest4j.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines the class under test. This need to be provided for every test class, so that doctest4j
 * can access the corresponding javadoc.<br/>
 * <br/>
 * If no annotation is present for the test class, doctest4j will try to resolve the class under
 * test by simple naming conventions; meaning that if the test class is called org.example.FooTest,
 * then it assumes that the corresponding class under test is org.example.Foo.<br/>
 * <br/>
 * Note: though it is possible to provide a value different than the real class under test, it is
 * not recommended to do so, because it would very likely confuse the readers of the test.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ClassUnderTest {

  Class<?> value();

}
