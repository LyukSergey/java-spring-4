package anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // Анотація буде доступна під час виконання
@Target(ElementType.TYPE) // Можна застосовувати лише до класів
public @interface Profiling {}