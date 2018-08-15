package org.tinywind.webtemplate.repository;

import org.tinywind.webtemplate.config.RequestGlobal;
import org.tinywind.webtemplate.config.RequestMessage;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseRepository {
    @Autowired
    protected DSLContext create;

    @Autowired
    protected RequestGlobal g;

    @Autowired
    protected RequestMessage requestMessage;
}
