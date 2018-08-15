package org.tinywind.webtemplate.repository;

import org.tinywind.webtemplate.model.User;
import org.tinywind.webtemplate.model.form.LoginRequest;
import org.tinywind.webtemplate.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static org.tinywind.webtemplate.jooq.Tables.USER_ENTITY;

/**
 * @author tinywind
 * @since 2018-01-14
 */
@Repository
public class UserRepository extends BaseRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    @Autowired
    private FileService fileService;

    public User findOne(String id) {
        return create.selectFrom(USER_ENTITY)
                .where(USER_ENTITY.ID.eq(id))
                .fetchOneInto(User.class);
    }

    public User findOneByIdAndPassword(LoginRequest form) {
        return create.selectFrom(USER_ENTITY)
                .where(USER_ENTITY.ID.eq(form.getId()))
                .and(USER_ENTITY.PASSWORD.eq(form.getPassword()))
                .fetchOneInto(User.class);
    }
}