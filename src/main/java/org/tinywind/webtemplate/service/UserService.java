package org.tinywind.webtemplate.service;

import org.tinywind.webtemplate.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tinywind
 * @since 2018-01-14
 */
@Service
public class UserService extends BaseService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileService fileService;
}