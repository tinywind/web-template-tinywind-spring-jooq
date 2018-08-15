package org.tinywind.webtemplate.repository;

import org.tinywind.webtemplate.jooq.tables.pojos.FileEntity;
import org.springframework.stereotype.Repository;

import static org.tinywind.webtemplate.jooq.tables.FileEntity.FILE_ENTITY;

/**
 * @author tinywind
 * @since 2018-01-14
 */
@Repository
public class FileRepository extends BaseRepository {

    public FileEntity fileEntity(Long fileId) {
        return create.selectFrom(FILE_ENTITY)
                .where(FILE_ENTITY.ID.eq(fileId))
                .fetchOneInto(FileEntity.class);
    }

    public Long save(long size, String originalName, String path) {
        return create.insertInto(FILE_ENTITY)
                .set(FILE_ENTITY.SIZE, size)
                .set(FILE_ENTITY.ORIGINAL_NAME, originalName)
                .set(FILE_ENTITY.PATH, path)
                .returning(FILE_ENTITY.ID)
                .fetchOne()
                .getId();
    }
}
