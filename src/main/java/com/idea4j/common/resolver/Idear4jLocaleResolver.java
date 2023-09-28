package com.idea4j.common.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 *
 * @author markee
 * @date 2016/8/2
 */
@Slf4j
public class Idear4jLocaleResolver extends AcceptHeaderLocaleResolver {

    public static final String LOCALE = "locale";

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Locale locale=(Locale)session.getAttribute(LOCALE);
        if (locale==null){
            session.setAttribute(LOCALE,request.getLocale());
            return request.getLocale();
        }else{
            return locale;
        }
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        log.info("setLocale:"+locale.toString());
        request.getSession().setAttribute(LOCALE,locale);
    }

}
