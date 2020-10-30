package annotation1;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//https://lingcoder.github.io/OnJava8/#/book/23-Annotations?id=%e5%85%83%e6%b3%a8%e8%a7%a3
@Target(ElementType.METHOD)//注解可以用于哪些地方
@Retention(RetentionPolicy.RUNTIME)//注解信息的保存时长
public @interface UseCase {
    int id();
    String description() default "no description";

}
