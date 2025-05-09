package com.studyhub.kartei.adapter.aspects;
import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckKarteikarteExists {
}
