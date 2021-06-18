package com.ljp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReceiptsRecordExample implements Serializable{
	
	private static final long serialVersionUID = 1L;
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ReceiptsRecordExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andReceiptsRecordIdIsNull() {
            addCriterion("receipts_record_id is null");
            return (Criteria) this;
        }

        public Criteria andReceiptsRecordIdIsNotNull() {
            addCriterion("receipts_record_id is not null");
            return (Criteria) this;
        }

        public Criteria andReceiptsRecordIdEqualTo(Integer value) {
            addCriterion("receipts_record_id =", value, "receiptsRecordId");
            return (Criteria) this;
        }

        public Criteria andReceiptsRecordIdNotEqualTo(Integer value) {
            addCriterion("receipts_record_id <>", value, "receiptsRecordId");
            return (Criteria) this;
        }

        public Criteria andReceiptsRecordIdGreaterThan(Integer value) {
            addCriterion("receipts_record_id >", value, "receiptsRecordId");
            return (Criteria) this;
        }

        public Criteria andReceiptsRecordIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("receipts_record_id >=", value, "receiptsRecordId");
            return (Criteria) this;
        }

        public Criteria andReceiptsRecordIdLessThan(Integer value) {
            addCriterion("receipts_record_id <", value, "receiptsRecordId");
            return (Criteria) this;
        }

        public Criteria andReceiptsRecordIdLessThanOrEqualTo(Integer value) {
            addCriterion("receipts_record_id <=", value, "receiptsRecordId");
            return (Criteria) this;
        }

        public Criteria andReceiptsRecordIdIn(List<Integer> values) {
            addCriterion("receipts_record_id in", values, "receiptsRecordId");
            return (Criteria) this;
        }

        public Criteria andReceiptsRecordIdNotIn(List<Integer> values) {
            addCriterion("receipts_record_id not in", values, "receiptsRecordId");
            return (Criteria) this;
        }

        public Criteria andReceiptsRecordIdBetween(Integer value1, Integer value2) {
            addCriterion("receipts_record_id between", value1, value2, "receiptsRecordId");
            return (Criteria) this;
        }

        public Criteria andReceiptsRecordIdNotBetween(Integer value1, Integer value2) {
            addCriterion("receipts_record_id not between", value1, value2, "receiptsRecordId");
            return (Criteria) this;
        }

        public Criteria andReceiptsIdIsNull() {
            addCriterion("receipts_id is null");
            return (Criteria) this;
        }

        public Criteria andReceiptsIdIsNotNull() {
            addCriterion("receipts_id is not null");
            return (Criteria) this;
        }

        public Criteria andReceiptsIdEqualTo(Integer value) {
            addCriterion("receipts_id =", value, "receiptsId");
            return (Criteria) this;
        }

        public Criteria andReceiptsIdNotEqualTo(Integer value) {
            addCriterion("receipts_id <>", value, "receiptsId");
            return (Criteria) this;
        }

        public Criteria andReceiptsIdGreaterThan(Integer value) {
            addCriterion("receipts_id >", value, "receiptsId");
            return (Criteria) this;
        }

        public Criteria andReceiptsIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("receipts_id >=", value, "receiptsId");
            return (Criteria) this;
        }

        public Criteria andReceiptsIdLessThan(Integer value) {
            addCriterion("receipts_id <", value, "receiptsId");
            return (Criteria) this;
        }

        public Criteria andReceiptsIdLessThanOrEqualTo(Integer value) {
            addCriterion("receipts_id <=", value, "receiptsId");
            return (Criteria) this;
        }

        public Criteria andReceiptsIdIn(List<Integer> values) {
            addCriterion("receipts_id in", values, "receiptsId");
            return (Criteria) this;
        }

        public Criteria andReceiptsIdNotIn(List<Integer> values) {
            addCriterion("receipts_id not in", values, "receiptsId");
            return (Criteria) this;
        }

        public Criteria andReceiptsIdBetween(Integer value1, Integer value2) {
            addCriterion("receipts_id between", value1, value2, "receiptsId");
            return (Criteria) this;
        }

        public Criteria andReceiptsIdNotBetween(Integer value1, Integer value2) {
            addCriterion("receipts_id not between", value1, value2, "receiptsId");
            return (Criteria) this;
        }

        public Criteria andNextDealIdIsNull() {
            addCriterion("deal_id is null");
            return (Criteria) this;
        }

        public Criteria andNextDealIdIsNotNull() {
            addCriterion("deal_id is not null");
            return (Criteria) this;
        }

        public Criteria andNextDealIdEqualTo(Integer value) {
            addCriterion("deal_id =", value, "dealId");
            return (Criteria) this;
        }

        public Criteria andNextDealIdNotEqualTo(Integer value) {
            addCriterion("deal_id <>", value, "dealId");
            return (Criteria) this;
        }

        public Criteria andNextDealIdGreaterThan(Integer value) {
            addCriterion("deal_id >", value, "dealId");
            return (Criteria) this;
        }

        public Criteria andNextDealIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("deal_id >=", value, "dealId");
            return (Criteria) this;
        }

        public Criteria andNextDealIdLessThan(Integer value) {
            addCriterion("deal_id <", value, "dealId");
            return (Criteria) this;
        }

        public Criteria andNextDealIdLessThanOrEqualTo(Integer value) {
            addCriterion("deal_id <=", value, "dealId");
            return (Criteria) this;
        }

        public Criteria andNextDealIdIn(List<Integer> values) {
            addCriterion("deal_id in", values, "dealId");
            return (Criteria) this;
        }

        public Criteria andNextDealIdNotIn(List<Integer> values) {
            addCriterion("deal_id not in", values, "dealId");
            return (Criteria) this;
        }

        public Criteria andNextDealIdBetween(Integer value1, Integer value2) {
            addCriterion("deal_id between", value1, value2, "dealId");
            return (Criteria) this;
        }

        public Criteria andNextDealIdNotBetween(Integer value1, Integer value2) {
            addCriterion("deal_id not between", value1, value2, "dealId");
            return (Criteria) this;
        }

        public Criteria andDealTimeIsNull() {
            addCriterion("deal_time is null");
            return (Criteria) this;
        }

        public Criteria andDealTimeIsNotNull() {
            addCriterion("deal_time is not null");
            return (Criteria) this;
        }

        public Criteria andDealTimeEqualTo(Date value) {
            addCriterion("deal_time =", value, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeNotEqualTo(Date value) {
            addCriterion("deal_time <>", value, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeGreaterThan(Date value) {
            addCriterion("deal_time >", value, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("deal_time >=", value, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeLessThan(Date value) {
            addCriterion("deal_time <", value, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeLessThanOrEqualTo(Date value) {
            addCriterion("deal_time <=", value, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeIn(List<Date> values) {
            addCriterion("deal_time in", values, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeNotIn(List<Date> values) {
            addCriterion("deal_time not in", values, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeBetween(Date value1, Date value2) {
            addCriterion("deal_time between", value1, value2, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeNotBetween(Date value1, Date value2) {
            addCriterion("deal_time not between", value1, value2, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTypeIsNull() {
            addCriterion("deal_type is null");
            return (Criteria) this;
        }

        public Criteria andDealTypeIsNotNull() {
            addCriterion("deal_type is not null");
            return (Criteria) this;
        }

        public Criteria andDealTypeEqualTo(String value) {
            addCriterion("deal_type =", value, "dealType");
            return (Criteria) this;
        }

        public Criteria andDealTypeNotEqualTo(String value) {
            addCriterion("deal_type <>", value, "dealType");
            return (Criteria) this;
        }

        public Criteria andDealTypeGreaterThan(String value) {
            addCriterion("deal_type >", value, "dealType");
            return (Criteria) this;
        }

        public Criteria andDealTypeGreaterThanOrEqualTo(String value) {
            addCriterion("deal_type >=", value, "dealType");
            return (Criteria) this;
        }

        public Criteria andDealTypeLessThan(String value) {
            addCriterion("deal_type <", value, "dealType");
            return (Criteria) this;
        }

        public Criteria andDealTypeLessThanOrEqualTo(String value) {
            addCriterion("deal_type <=", value, "dealType");
            return (Criteria) this;
        }

        public Criteria andDealTypeLike(String value) {
            addCriterion("deal_type like", value, "dealType");
            return (Criteria) this;
        }

        public Criteria andDealTypeNotLike(String value) {
            addCriterion("deal_type not like", value, "dealType");
            return (Criteria) this;
        }

        public Criteria andDealTypeIn(List<String> values) {
            addCriterion("deal_type in", values, "dealType");
            return (Criteria) this;
        }

        public Criteria andDealTypeNotIn(List<String> values) {
            addCriterion("deal_type not in", values, "dealType");
            return (Criteria) this;
        }

        public Criteria andDealTypeBetween(String value1, String value2) {
            addCriterion("deal_type between", value1, value2, "dealType");
            return (Criteria) this;
        }

        public Criteria andDealTypeNotBetween(String value1, String value2) {
            addCriterion("deal_type not between", value1, value2, "dealType");
            return (Criteria) this;
        }

        public Criteria andDealResultIsNull() {
            addCriterion("deal_result is null");
            return (Criteria) this;
        }

        public Criteria andDealResultIsNotNull() {
            addCriterion("deal_result is not null");
            return (Criteria) this;
        }

        public Criteria andDealResultEqualTo(String value) {
            addCriterion("deal_result =", value, "dealResult");
            return (Criteria) this;
        }

        public Criteria andDealResultNotEqualTo(String value) {
            addCriterion("deal_result <>", value, "dealResult");
            return (Criteria) this;
        }

        public Criteria andDealResultGreaterThan(String value) {
            addCriterion("deal_result >", value, "dealResult");
            return (Criteria) this;
        }

        public Criteria andDealResultGreaterThanOrEqualTo(String value) {
            addCriterion("deal_result >=", value, "dealResult");
            return (Criteria) this;
        }

        public Criteria andDealResultLessThan(String value) {
            addCriterion("deal_result <", value, "dealResult");
            return (Criteria) this;
        }

        public Criteria andDealResultLessThanOrEqualTo(String value) {
            addCriterion("deal_result <=", value, "dealResult");
            return (Criteria) this;
        }

        public Criteria andDealResultLike(String value) {
            addCriterion("deal_result like", value, "dealResult");
            return (Criteria) this;
        }

        public Criteria andDealResultNotLike(String value) {
            addCriterion("deal_result not like", value, "dealResult");
            return (Criteria) this;
        }

        public Criteria andDealResultIn(List<String> values) {
            addCriterion("deal_result in", values, "dealResult");
            return (Criteria) this;
        }

        public Criteria andDealResultNotIn(List<String> values) {
            addCriterion("deal_result not in", values, "dealResult");
            return (Criteria) this;
        }

        public Criteria andDealResultBetween(String value1, String value2) {
            addCriterion("deal_result between", value1, value2, "dealResult");
            return (Criteria) this;
        }

        public Criteria andDealResultNotBetween(String value1, String value2) {
            addCriterion("deal_result not between", value1, value2, "dealResult");
            return (Criteria) this;
        }

        public Criteria andRepresentionIsNull() {
            addCriterion("represention is null");
            return (Criteria) this;
        }

        public Criteria andRepresentionIsNotNull() {
            addCriterion("represention is not null");
            return (Criteria) this;
        }

        public Criteria andRepresentionEqualTo(String value) {
            addCriterion("represention =", value, "represention");
            return (Criteria) this;
        }

        public Criteria andRepresentionNotEqualTo(String value) {
            addCriterion("represention <>", value, "represention");
            return (Criteria) this;
        }

        public Criteria andRepresentionGreaterThan(String value) {
            addCriterion("represention >", value, "represention");
            return (Criteria) this;
        }

        public Criteria andRepresentionGreaterThanOrEqualTo(String value) {
            addCriterion("represention >=", value, "represention");
            return (Criteria) this;
        }

        public Criteria andRepresentionLessThan(String value) {
            addCriterion("represention <", value, "represention");
            return (Criteria) this;
        }

        public Criteria andRepresentionLessThanOrEqualTo(String value) {
            addCriterion("represention <=", value, "represention");
            return (Criteria) this;
        }

        public Criteria andRepresentionLike(String value) {
            addCriterion("represention like", value, "represention");
            return (Criteria) this;
        }

        public Criteria andRepresentionNotLike(String value) {
            addCriterion("represention not like", value, "represention");
            return (Criteria) this;
        }

        public Criteria andRepresentionIn(List<String> values) {
            addCriterion("represention in", values, "represention");
            return (Criteria) this;
        }

        public Criteria andRepresentionNotIn(List<String> values) {
            addCriterion("represention not in", values, "represention");
            return (Criteria) this;
        }

        public Criteria andRepresentionBetween(String value1, String value2) {
            addCriterion("represention between", value1, value2, "represention");
            return (Criteria) this;
        }

        public Criteria andRepresentionNotBetween(String value1, String value2) {
            addCriterion("represention not between", value1, value2, "represention");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria implements Serializable{
    	
    	private static final long serialVersionUID = 1L;

        protected Criteria() {
            super();
        }
    }

    public static class Criterion implements Serializable{
    	
    	private static final long serialVersionUID = 1L;
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}