package org.tinywind.webtemplate.model.form;

import org.tinywind.webtemplate.util.spring.BaseForm;
import org.tinywind.webtemplate.util.valid.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginRequest extends BaseForm {
    @NotNull("아이디")
    private String id;
    @NotNull("비밀번호")
    private String password;
}
