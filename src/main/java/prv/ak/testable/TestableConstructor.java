package prv.ak.testable;

import java.lang.annotation.*;

@Target(ElementType.CONSTRUCTOR)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestableConstructor {
    String[] args() default {};
}
