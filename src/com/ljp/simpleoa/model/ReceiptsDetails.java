package com.ljp.simpleoa.model;

public class ReceiptsDetails {
    @Override
	public String toString() {
		return "ReceiptsDetails [receiptsDetailsId=" + receiptsDetailsId + ", receiptsId=" + receiptsId + ", costType="
				+ costType + ", costMoney=" + costMoney + ", represention=" + represention + "]";
	}

	private Integer receiptsDetailsId;

    private Integer receiptsId;

    private String costType;

    private Long costMoney;

    private String represention;

    public Integer getReceiptsDetailsId() {
        return receiptsDetailsId;
    }

    public void setReceiptsDetailsId(Integer receiptsDetailsId) {
        this.receiptsDetailsId = receiptsDetailsId;
    }

    public Integer getReceiptsId() {
        return receiptsId;
    }

    public void setReceiptsId(Integer receiptsId) {
        this.receiptsId = receiptsId;
    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType == null ? null : costType.trim();
    }

    public Long getCostMoney() {
        return costMoney;
    }

    public void setCostMoney(Long costMoney) {
        this.costMoney = costMoney;
    }

    public String getRepresention() {
        return represention;
    }

    public void setRepresention(String represention) {
        this.represention = represention == null ? null : represention.trim();
    }
}