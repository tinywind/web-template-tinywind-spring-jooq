package org.tinywind.webtemplate.service;

import org.tinywind.webtemplate.config.RequestGlobal;
import org.tinywind.webtemplate.config.RequestMessage;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseService {
    @Autowired
    protected RequestGlobal g;

    @Autowired
    protected RequestMessage message;
}
