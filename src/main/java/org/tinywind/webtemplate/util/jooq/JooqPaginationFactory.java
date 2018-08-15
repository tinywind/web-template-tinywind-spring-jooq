package org.tinywind.webtemplate.util.jooq;

import org.tinywind.webtemplate.util.page.Pagination;
import org.tinywind.webtemplate.util.spring.SpringApplicationContextAware;
import org.jooq.*;

import java.util.List;

public class JooqPaginationFactory {
    private JooqPaginationFactory() {
    }

    public static <T> Pagination create(SelectLimitStep query, Class<T> klass, int page, int numberOfRowsPerPage) {
        return create(query, record -> record.into(klass), page, numberOfRowsPerPage);
    }

    public static <T> Pagination create(SelectLimitStep query, RecordMapper<? extends Record, T> mapper, int page, int numberOfRowsPerPage) {
        final DSLContext create = create();
        final Table table = query.asTable();

        @SuppressWarnings("unchecked") final List<T> rows = (List<T>) create.selectFrom(table)
                .limit(numberOfRowsPerPage)
                .offset(numberOfRowsPerPage * page)
                .fetch(mapper);

        final int count = create.fetchCount(table);

        return new Pagination<>(rows, page, count, numberOfRowsPerPage);
    }

    private static DSLContext create() {
        return (DSLContext) SpringApplicationContextAware.getBean("dsl");
    }
}
