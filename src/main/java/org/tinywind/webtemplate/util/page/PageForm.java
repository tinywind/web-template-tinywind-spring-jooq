package org.tinywind.webtemplate.util.page;

/**
 * @author tinywind
 * @since 2016-09-03
 */
public class PageForm extends PageQueryableForm {
    @PageQueryable
    protected Integer page = 0;

    @PageQueryable
    protected Integer limit = 10;

    public final String getQuery(int toPage) {
        page = toPage;
        return getQuery();
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}