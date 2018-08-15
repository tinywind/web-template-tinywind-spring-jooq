package org.tinywind.webtemplate.model;

import org.tinywind.webtemplate.jooq.tables.pojos.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends UserEntity {
}
