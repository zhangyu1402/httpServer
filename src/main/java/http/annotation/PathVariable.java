package http.annotation;

import java.lang.annotation.*;

@Param
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PathVariable {
    String value() default "";
}
