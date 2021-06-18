package com.ljp.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class Receipts implements Serializable{
	
	private static final long serialVersionUID = 1L;
    private Integer receiptsId;

    private Integer createrId;

    private Integer nextDealId;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date creatTime;

    private String cause;

    private Long totalmoney;

    private String state;
    
    private Worker creater;
    
    private Worker dealer;
    
    private List<ReceiptsDetails> items;
    
    private List<ReceiptsRecord> records;

    public List<ReceiptsRecord> getRecords() {
		return records;
	}

	public void setRecords(List<ReceiptsRecord> records) {
		this.records = records;
	}

	public List<ReceiptsDetails> getItems() {
		return items;
	}

	public void setItems(List<ReceiptsDetails> items) {
		this.items = items;
	}

	public Worker getCreater() {
		return creater;
	}

	public void setCreater(Worker creater) {
		this.creater = creater;
	}

	public Worker getDealer() {
		return dealer;
	}

	public void setDealer(Worker dealer) {
		this.dealer = dealer;
	}

	public Integer getReceiptsId() {
        return receiptsId;
    }

    public void setReceiptsId(Integer receiptsId) {
        this.receiptsId = receiptsId;
    }

    public Integer getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Integer createrId) {
        this.createrId = createrId;
    }

    public Integer getNextDealId() {
        return nextDealId;
    }

    public void setNextDealId(Integer nextDealId) {
        this.nextDealId = nextDealId;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause == null ? null : cause.trim();
    }

    public Long getTotalmoney() {
        return totalmoney;
    }

    public void setTotalmoney(Long totalmoney) {
        this.totalmoney = totalmoney;
    }

	@Override
	public String toString() {
		return "Receipts [receiptsId=" + receiptsId + ", createrId=" + createrId + ", nextDealId=" + nextDealId
				+ ", creatTime=" + creatTime + ", cause=" + cause + ", totalmoney=" + totalmoney + ", state=" + state
				+ ", creater=" + creater + ", dealer=" + dealer + ", items=" + items + "]";
	}

	public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }
}