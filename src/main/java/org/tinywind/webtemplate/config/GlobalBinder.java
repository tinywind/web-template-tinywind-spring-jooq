package org.tinywind.webtemplate.config;

import org.tinywind.webtemplate.model.User;
import org.tinywind.webtemplate.util.spring.TagExtender;
import org.tinywind.webtemplate.util.spring.TimestampPropertyEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @author tinywind
 */
@ControllerAdvice
@PropertySource("classpath:maven.properties")
public class GlobalBinder {
    @Autowired
    private TagExtender tagExtender;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private RequestGlobal g;
    @Autowired
    private CachedEntity cachedEntity;
    @Autowired
    private RequestMessage requestMessage;

    @Value("${application.version}")
    private String version;
    @Value("${application.devel:true}")
    private Boolean devel;

    @ModelAttribute("devel")
    public Boolean devel() {
        return devel;
    }

    @ModelAttribute("version")
    public String version() {
        return version;
    }

    @ModelAttribute("cached")
    public CachedEntity cachedEntity() {
        return cachedEntity;
    }

    @ModelAttribute("g")
    public RequestGlobal requestGlobal() {
        return g;
    }

    @ModelAttribute("request")
    public HttpServletRequest request() {
        return request;
    }

    @ModelAttribute("tagExtender")
    public TagExtender tagExtender() {
        return tagExtender;
    }

    @ModelAttribute("user")
    public User user() {
        return g.getUser();
    }

    @ModelAttribute("message")
    public RequestMessage message() {
        return requestMessage;
    }

    @InitBinder
    public void registerCustomEditors(WebDataBinder binder) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

        binder.registerCustomEditor(Timestamp.class, new TimestampPropertyEditor("yyyy-MM-dd HH:mm:ss"));
        binder.registerCustomEditor(Timestamp.class, new TimestampPropertyEditor("yyyy-MM-dd HH:mm"));
    }
}
