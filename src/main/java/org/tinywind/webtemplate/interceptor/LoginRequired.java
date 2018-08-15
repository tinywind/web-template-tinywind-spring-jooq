package org.tinywind.webtemplate.interceptor;

import org.tinywind.webtemplate.jooq.enums.UserEntityGrade;
import org.tinywind.webtemplate.model.User;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author tinywind
 * @since 2016-09-03
 */
@Target({TYPE, METHOD})
@Retention(RUNTIME)
@Documented
public @interface LoginRequired {
    Type type() default Type.PAGE;

    UserEntityGrade grade() default UserEntityGrade.NORMAL;

    enum Type {
        API, PAGE, POPUP
    }
}
