package com.ljp.simpleoa.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ReceiptsRecord {
    private Integer receiptsRecordId;

    private Integer receiptsId;

    private Integer dealId;
    
    private Worker dealer;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date dealTime;

	private String dealType;

	@Override
	public String toString() {
		return "ReceiptsRecord [receiptsRecordId=" + receiptsRecordId + ", receiptsId=" + receiptsId + ", dealId="
				+ dealId + ", dealer=" + dealer + ", dealTime=" + dealTime + ", dealType=" + dealType + ", dealResult="
				+ dealResult + ", represention=" + represention + "]";
	}

	private String dealResult;

    private String represention;

    public Worker getDealer() {
		return dealer;
	}

    public Integer getDealId() {
        return dealId;
    }

    public String getDealResult() {
        return dealResult;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public String getDealType() {
        return dealType;
    }

    public Integer getReceiptsId() {
        return receiptsId;
    }

    public Integer getReceiptsRecordId() {
        return receiptsRecordId;
    }

    public String getRepresention() {
        return represention;
    }

    public void setDealer(Worker dealer) {
		this.dealer = dealer;
	}

    public void setDealId(Integer nextDealId) {
        this.dealId = nextDealId;
    }

    public void setDealResult(String dealResult) {
        this.dealResult = dealResult == null ? null : dealResult.trim();
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType == null ? null : dealType.trim();
    }

    public void setReceiptsId(Integer createrId) {
        this.receiptsId = createrId;
    }

    public void setReceiptsRecordId(Integer receiptsRecordId) {
        this.receiptsRecordId = receiptsRecordId;
    }

    public void setRepresention(String represention) {
        this.represention = represention == null ? null : represention.trim();
    }
}