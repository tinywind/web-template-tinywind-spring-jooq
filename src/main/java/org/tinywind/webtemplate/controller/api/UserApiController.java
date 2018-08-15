package org.tinywind.webtemplate.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.tinywind.webtemplate.model.User;
import org.tinywind.webtemplate.model.form.LoginRequest;
import org.tinywind.webtemplate.repository.UserRepository;
import org.tinywind.webtemplate.service.FileService;
import org.tinywind.webtemplate.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author tinywind
 * @since 2018-01-14
 */
@Api(description = "사용자 정보", tags = {"USER"}, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping(value = "api/user", produces = MediaType.APPLICATION_JSON_VALUE)
@PropertySource("classpath:application.properties")
public class UserApiController extends ApiBaseController {
    private static final Logger logger = LoggerFactory.getLogger(UserApiController.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileService fileService;

    @ApiOperation("로그인")
    @GetMapping("login")
    public JsonResult<User> login(@Valid LoginRequest form, BindingResult bindingResult) {
        if (!form.validate(bindingResult))
            return JsonResult.create(bindingResult);

        if (userRepository.findOne(form.getId()) == null)
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");

        final User user = userRepository.findOneByIdAndPassword(form);
        if (user == null)
            throw new IllegalArgumentException("비밀번호가 옳바르지 않습니다.");

        g.setCurrentUser(user);
        return JsonResult.data(user);
    }

    @ApiOperation("로그아웃")
    @GetMapping("logout")
    public JsonResult logout() {
        g.invalidateSession();
        return JsonResult.create();
    }
}
