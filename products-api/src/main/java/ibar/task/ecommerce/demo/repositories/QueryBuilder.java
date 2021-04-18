package ibar.task.ecommerce.demo.repositories;

public class QueryBuilder {
    String baseQuery;
    String whereQuery;
    String groupByQuery;
    String orderByQuery;

    public QueryBuilder(String baseQuery) {
        this.baseQuery = baseQuery;
    }

    public QueryBuilder where(String addedQuery) {
        if (whereQuery == null) {
            this.whereQuery = " where " + addedQuery;
        } else {
            whereQuery += "and " + addedQuery;
        }
        return this;
    }

    public QueryBuilder groupBy(String addedQuery) {
        if (groupByQuery == null) {
            groupByQuery = " group by " + addedQuery;
        } else {
            groupByQuery += ", " + addedQuery;
        }
        return this;
    }

    public QueryBuilder orderBy(String addedQuery) {
        if (orderByQuery == null) {
            orderByQuery = " order by " + addedQuery;
        } else {
            orderByQuery += ", " + addedQuery;
        }
        return this;
    }


    public String getQuery() {
        return baseQuery + (whereQuery == null ? "" : whereQuery) + (groupByQuery == null ? "" : groupByQuery) + (orderByQuery == null ? "" : orderByQuery);
    }
}
