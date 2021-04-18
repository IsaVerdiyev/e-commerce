package ibar.task.ecommerce.demo.services;

public class QuiryBuilder {
    String baseQuery;
    String whereQuery;
    String groupByQuery;
    String orderByQuery;

    public QuiryBuilder(String baseQuery) {
        this.baseQuery = baseQuery;
    }

    public QuiryBuilder where(String whereQuery){
        if(whereQuery == null){
            whereQuery = " where " + whereQuery;
        }
        else{
            whereQuery += ", " + whereQuery;
        }
        return this;
    }

    public QuiryBuilder groupBy(String groupByQuery){
        if(groupByQuery == null){
            groupByQuery = " group by " + groupByQuery;
        }
        else{
            groupByQuery += ", " + groupByQuery;
        }
        return this;
    }

    public QuiryBuilder orderBy(String orderByQuery){
        if(orderByQuery == null){
            orderByQuery = " order by " + orderByQuery;
        }
        else {
            orderByQuery += ", " + orderByQuery;
        }
        return this;
    }


    String getQuery(){
        return baseQuery + whereQuery + groupByQuery + orderByQuery;
    }
}
