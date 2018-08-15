package org.tinywind.webtemplate.controller;

import org.tinywind.webtemplate.config.RequestGlobal;
import org.tinywind.webtemplate.config.RequestMessage;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author tinywind
 * @since 2017-05-15
 */
public abstract class BaseController {

    @Autowired
    protected RequestGlobal g;

    @Autowired
    protected RequestMessage message;

    protected String redirectMain() {
        return "redirect:/";
    }

    protected String closingPopup() {
        return "closing-popup";
    }
}
