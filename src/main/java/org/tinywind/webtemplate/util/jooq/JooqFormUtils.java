package org.tinywind.webtemplate.util.jooq;

import org.tinywind.webtemplate.util.FormUtils;
import org.tinywind.webtemplate.util.spring.SpringApplicationContextAware;
import org.jooq.*;
import org.jooq.impl.TableImpl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JooqFormUtils extends FormUtils {
    private static DSLContext create() {
        return (DSLContext) SpringApplicationContextAware.getBean("dsl");
    }

    public static <R extends Record, T extends TableImpl<R>> Map<String, String> options(boolean withWholeExpression, String messageSourceLabelPrefix, T table, TableField<R, ?> idField) {
        final LinkedHashMap<String, String> map = new LinkedHashMap<>();
        if (withWholeExpression)
            map.put("", requestMessage().getText("text.whole"));

        create().select(idField)
                .from(table)
                .forEach(r -> {
                    final String id = r.get(idField).toString();
                    map.put(id, requestMessage().getText(messageSourceLabelPrefix + "." + id));
                });

        return map;
    }

    public static <R extends Record, T extends TableImpl<R>> Map<String, String> options(String messageSourceLabelPrefix, T table, TableField<R, ?> idField) {
        return options(false, messageSourceLabelPrefix, table, idField);
    }

    public static <R extends Record, T extends TableImpl<R>> Map<String, String> options(boolean withWholeExpression, T table, TableField<R, ?> idField, TableField<R, ?> nameField) {
        final LinkedHashMap<String, String> map = new LinkedHashMap<>();
        if (withWholeExpression)
            map.put("", requestMessage().getText("text.whole"));

        create().select(idField, nameField)
                .from(table)
                .forEach(r -> map.put(r.get(idField).toString(), r.get(nameField).toString()));

        return map;
    }

    public static <R extends Record, T extends TableImpl<R>> Map<String, String> options(T table, TableField<R, ?> idField, TableField<R, ?> nameField) {
        return options(false, table, idField, nameField);
    }

    public static <R extends Record, T extends TableImpl<R>, E> List<E> list(T table, SortField<?> sortField, RecordMapper<? super Record, E> mapper) {
        return create().select()
                .from(table)
                .orderBy(sortField)
                .fetch(mapper);
    }

    public static <R extends Record, T extends TableImpl<R>, E> List<E> list(T table, RecordMapper<? super Record, E> mapper) {
        return create().select()
                .from(table)
                .fetch(mapper);
    }
}
