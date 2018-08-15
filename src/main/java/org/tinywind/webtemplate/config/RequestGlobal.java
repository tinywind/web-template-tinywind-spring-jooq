package org.tinywind.webtemplate.config;

import org.tinywind.webtemplate.model.User;
import org.tinywind.webtemplate.service.FileService;
import org.tinywind.webtemplate.service.storage.SessionStorage;
import org.tinywind.webtemplate.util.spring.SpringApplicationContextAware;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

/**
 * @author tinywind
 */
@Component
public class RequestGlobal {
    private static final Logger logger = LoggerFactory.getLogger(RequestGlobal.class);
    private static final String REQUEST_GLOBAL_CURRENT_USER = "REQUEST_GLOBAL_CURRENT_USER";
    private static final String REQUEST_GLOBAL_ALERTS = "REQUEST_GLOBAL_ALERTS";

    @Autowired
    private HttpSession session;
    @Autowired
    private FileService fileService;
    @Autowired
    private SessionStorage sessionStorage;

    public User getUser() {
        return sessionStorage.get(session.getId(), REQUEST_GLOBAL_CURRENT_USER, User.class);
    }

    public boolean isLogin() {
        return getUser() != null;
    }

    public void setCurrentUser(User user) {
        sessionStorage.set(session.getId(), REQUEST_GLOBAL_CURRENT_USER, user);
    }

    public void invalidateSession() {
        sessionStorage.expire(session.getId());
    }

    public void alert(String code, Object... objects) {
        final List<String> alerts = getAlerts();
        alerts.add(SpringApplicationContextAware.requestMessage().getText(code, objects));
        sessionStorage.set(session.getId(), REQUEST_GLOBAL_ALERTS, alerts);
    }

    public void alertString(String string) {
        final List<String> alerts = getAlerts();
        alerts.add(string);
        sessionStorage.set(session.getId(), REQUEST_GLOBAL_ALERTS, alerts);
    }

    @SuppressWarnings("unchecked")
    public List<String> getAlerts() {
        final List<String> alerts = sessionStorage.get(session.getId(), REQUEST_GLOBAL_ALERTS, List.class, ArrayList::new);
        return new ArrayList<>(alerts);
    }

    public List<String> popAlerts() {
        final List<String> alerts = getAlerts().stream().distinct().collect(Collectors.toList());
        sessionStorage.remove(session.getId(), REQUEST_GLOBAL_ALERTS);
        return alerts;
    }

    public String urlEncode(String s) throws UnsupportedEncodingException {
        return URLEncoder.encode(s, "UTF-8");
    }

    public String now(String format) {
        return dateFormat(new Date(System.currentTimeMillis()), format);
    }

    public String dateFormat(Date date, String format) {
        final DateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        return dateFormat.format(date);
    }

    public String now() {
        return now("yyyy-MM-dd");
    }

    public String dateFormat(Date date) {
        return dateFormat(date, "yyyy-MM-dd");
    }

    public String escapeQuote(String text) {
        return text
                .replaceAll("'", "\\\\'")
                .replaceAll("\"", "\\\\\"")
                .replaceAll("[\n]", "\\\\n")
                .replaceAll("[\r]", "\\\\r");
    }

    public String htmlQuote(String text) {
        if (StringUtils.isEmpty(text))
            return "";
        return text.replaceAll("\"", "&quot;").replaceAll("'", "&#39;").replaceAll("<", "&lt;");
    }

    public String url(String path) {
        return fileService.url(path);
    }

    public String url(Long fileId) {
        return fileService.url(fileId);
    }

    public String numberOnlyCount(Double number, int count, boolean wholeInteger) {
        final String[] parts = new String[]{number.intValue() + "", (number - number.intValue()) + ""};

        final int eIndex = number.toString().indexOf("E");
        if (eIndex >= 0) {
            final Double f = number - number.intValue();
            String floatString = f.toString();
            final Integer e = Integer.valueOf(floatString.substring(floatString.indexOf("E") + 1)) * -1;
            if (floatString.startsWith("-") && !parts[0].startsWith("-")) parts[0] = "-" + parts[0];
            floatString = floatString.replaceAll("[.-]", "");
            for (Integer i = 1; i < e; i++) floatString = "0" + floatString;

            parts[1] = floatString;
        } else if (parts[1].startsWith("0.")) {
            parts[1] = parts[1].substring(2);
        } else if (parts[1].startsWith("-0.")) {
            parts[1] = parts[1].substring(3);
        }

        final int integerCount = parts[0].length() >= count ? count : parts[0].length();
        final String integerNumber = (number < 0 && !parts[0].startsWith("-") ? "-" : "") +
                (wholeInteger || parts[0].length() <= count
                        ? parts[0]
                        : "" + (Integer.valueOf(parts[0]) - Double.valueOf(Integer.valueOf(parts[0]) % Math.pow(10, parts[0].length() - count)).intValue()));

        final String floatNumber = integerCount >= count || count - integerCount <= 0
                ? ""
                : "." + String.format("%0" + (count - integerCount) + "d", Double.valueOf(Math.floor(Double.valueOf("0." + parts[1]) * Math.pow(10, count - integerCount))).intValue());

        return integerNumber + floatNumber;
    }

    public String phoneNumber(String s) {
        if (StringUtils.isEmpty(s))
            return "";

        s = s.replaceAll("[^0-9]", "");
        final int length = s.length();

        final String[] parts = new String[]{
                length < 8 ? null : s.substring(0, length < 11 ? length - 7 : length - 8),
                length < 5 ? null : s.substring(length < 10 ? (length >= 7 ? length - 7 : 0) : length - 8, length - 4),
                s.substring(length < 5 ? 0 : length - 4, length)
        };

        return length >= 8 ? parts[0] + "-" + parts[1] + "-" + parts[2]
                : length >= 5 ? parts[1] + "-" + parts[2]
                : parts[2];
    }

    public String paddingNumber(Number number, int count) {
        if (number == null) return "";
        return String.format("%0" + count + "d", number);
    }
}
