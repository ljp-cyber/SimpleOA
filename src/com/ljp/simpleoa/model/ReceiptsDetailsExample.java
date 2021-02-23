package com.ljp.simpleoa.model;

import java.util.ArrayList;
import java.util.List;

public class ReceiptsDetailsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ReceiptsDetailsExample() {
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

        public Criteria andReceiptsDetailsIdIsNull() {
            addCriterion("receipts_details_id is null");
            return (Criteria) this;
        }

        public Criteria andReceiptsDetailsIdIsNotNull() {
            addCriterion("receipts_details_id is not null");
            return (Criteria) this;
        }

        public Criteria andReceiptsDetailsIdEqualTo(Integer value) {
            addCriterion("receipts_details_id =", value, "receiptsDetailsId");
            return (Criteria) this;
        }

        public Criteria andReceiptsDetailsIdNotEqualTo(Integer value) {
            addCriterion("receipts_details_id <>", value, "receiptsDetailsId");
            return (Criteria) this;
        }

        public Criteria andReceiptsDetailsIdGreaterThan(Integer value) {
            addCriterion("receipts_details_id >", value, "receiptsDetailsId");
            return (Criteria) this;
        }

        public Criteria andReceiptsDetailsIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("receipts_details_id >=", value, "receiptsDetailsId");
            return (Criteria) this;
        }

        public Criteria andReceiptsDetailsIdLessThan(Integer value) {
            addCriterion("receipts_details_id <", value, "receiptsDetailsId");
            return (Criteria) this;
        }

        public Criteria andReceiptsDetailsIdLessThanOrEqualTo(Integer value) {
            addCriterion("receipts_details_id <=", value, "receiptsDetailsId");
            return (Criteria) this;
        }

        public Criteria andReceiptsDetailsIdIn(List<Integer> values) {
            addCriterion("receipts_details_id in", values, "receiptsDetailsId");
            return (Criteria) this;
        }

        public Criteria andReceiptsDetailsIdNotIn(List<Integer> values) {
            addCriterion("receipts_details_id not in", values, "receiptsDetailsId");
            return (Criteria) this;
        }

        public Criteria andReceiptsDetailsIdBetween(Integer value1, Integer value2) {
            addCriterion("receipts_details_id between", value1, value2, "receiptsDetailsId");
            return (Criteria) this;
        }

        public Criteria andReceiptsDetailsIdNotBetween(Integer value1, Integer value2) {
            addCriterion("receipts_details_id not between", value1, value2, "receiptsDetailsId");
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

        public Criteria andCostTypeIsNull() {
            addCriterion("cost_type is null");
            return (Criteria) this;
        }

        public Criteria andCostTypeIsNotNull() {
            addCriterion("cost_type is not null");
            return (Criteria) this;
        }

        public Criteria andCostTypeEqualTo(String value) {
            addCriterion("cost_type =", value, "costType");
            return (Criteria) this;
        }

        public Criteria andCostTypeNotEqualTo(String value) {
            addCriterion("cost_type <>", value, "costType");
            return (Criteria) this;
        }

        public Criteria andCostTypeGreaterThan(String value) {
            addCriterion("cost_type >", value, "costType");
            return (Criteria) this;
        }

        public Criteria andCostTypeGreaterThanOrEqualTo(String value) {
            addCriterion("cost_type >=", value, "costType");
            return (Criteria) this;
        }

        public Criteria andCostTypeLessThan(String value) {
            addCriterion("cost_type <", value, "costType");
            return (Criteria) this;
        }

        public Criteria andCostTypeLessThanOrEqualTo(String value) {
            addCriterion("cost_type <=", value, "costType");
            return (Criteria) this;
        }

        public Criteria andCostTypeLike(String value) {
            addCriterion("cost_type like", value, "costType");
            return (Criteria) this;
        }

        public Criteria andCostTypeNotLike(String value) {
            addCriterion("cost_type not like", value, "costType");
            return (Criteria) this;
        }

        public Criteria andCostTypeIn(List<String> values) {
            addCriterion("cost_type in", values, "costType");
            return (Criteria) this;
        }

        public Criteria andCostTypeNotIn(List<String> values) {
            addCriterion("cost_type not in", values, "costType");
            return (Criteria) this;
        }

        public Criteria andCostTypeBetween(String value1, String value2) {
            addCriterion("cost_type between", value1, value2, "costType");
            return (Criteria) this;
        }

        public Criteria andCostTypeNotBetween(String value1, String value2) {
            addCriterion("cost_type not between", value1, value2, "costType");
            return (Criteria) this;
        }

        public Criteria andCostMoneyIsNull() {
            addCriterion("cost_money is null");
            return (Criteria) this;
        }

        public Criteria andCostMoneyIsNotNull() {
            addCriterion("cost_money is not null");
            return (Criteria) this;
        }

        public Criteria andCostMoneyEqualTo(Long value) {
            addCriterion("cost_money =", value, "costMoney");
            return (Criteria) this;
        }

        public Criteria andCostMoneyNotEqualTo(Long value) {
            addCriterion("cost_money <>", value, "costMoney");
            return (Criteria) this;
        }

        public Criteria andCostMoneyGreaterThan(Long value) {
            addCriterion("cost_money >", value, "costMoney");
            return (Criteria) this;
        }

        public Criteria andCostMoneyGreaterThanOrEqualTo(Long value) {
            addCriterion("cost_money >=", value, "costMoney");
            return (Criteria) this;
        }

        public Criteria andCostMoneyLessThan(Long value) {
            addCriterion("cost_money <", value, "costMoney");
            return (Criteria) this;
        }

        public Criteria andCostMoneyLessThanOrEqualTo(Long value) {
            addCriterion("cost_money <=", value, "costMoney");
            return (Criteria) this;
        }

        public Criteria andCostMoneyIn(List<Long> values) {
            addCriterion("cost_money in", values, "costMoney");
            return (Criteria) this;
        }

        public Criteria andCostMoneyNotIn(List<Long> values) {
            addCriterion("cost_money not in", values, "costMoney");
            return (Criteria) this;
        }

        public Criteria andCostMoneyBetween(Long value1, Long value2) {
            addCriterion("cost_money between", value1, value2, "costMoney");
            return (Criteria) this;
        }

        public Criteria andCostMoneyNotBetween(Long value1, Long value2) {
            addCriterion("cost_money not between", value1, value2, "costMoney");
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

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
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