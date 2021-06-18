package com.ljp.bean;

import java.io.Serializable;

public class Department implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer departmentId;

    private String departmentSn;

    private String departmentName;

    private String address;

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentSn() {
        return departmentSn;
        
    }

    public void setDepartmentSn(String departmentSn) {
        this.departmentSn = departmentSn == null ? null : departmentSn.trim();
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }
}