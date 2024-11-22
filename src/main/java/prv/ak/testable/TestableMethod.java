package prv.ak.testable;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestableMethod {
    String expectedValue() default "";
    String[] args() default {};
}
