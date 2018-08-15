package org.tinywind.webtemplate.interceptor;

import org.tinywind.webtemplate.jooq.enums.UserEntityGrade;
import org.tinywind.webtemplate.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author tinywind
 * @since 2016-09-03
 */
@Component
public class LoginRequireInterceptor extends AnnotationHandlerInterceptorAdapter<LoginRequired> {
    private static final Logger logger = LoggerFactory.getLogger(LoginRequireInterceptor.class);

    public LoginRequireInterceptor() {
        super(LoginRequired.class);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, LoginRequired annotation) throws Exception {
        final User user = g.getUser();
        if (user != null) {
            if (Objects.equals(UserEntityGrade.MANAGER, annotation.grade()) && !Objects.equals(UserEntityGrade.MANAGER, user.getGrade()))
                return processFail(request, response, annotation);

            return true;
        }
        return processFail(request, response, annotation);
    }

    private boolean processFail(HttpServletRequest request, HttpServletResponse response, LoginRequired annotation) throws Exception {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        if (annotation.type().equals(LoginRequired.Type.API)) {
            sendUnauthorizedJsonResult(response);
            return false;
        }

        if (annotation.type().equals(LoginRequired.Type.POPUP)) {
            closePopup(request, response);
            return false;
        }

        g.alert(g.isLogin() ? "error.access.denied" : "error.login.require");
        redirectMain(request, response);
        return false;
    }
}
