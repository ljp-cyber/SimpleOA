package com.ljp.simpleoa.model;

import java.io.Serializable;

public class Worker implements Serializable{
	
	private static final long serialVersionUID = -5305510795889968181L;

	private Integer workerId;
    
	@Override
	public String toString() {
		return "Worker [workerId=" + workerId + ", workerSn=" + workerSn + ", workerPw=" + workerPw + ", workerName="
				+ workerName + ", departmentId=" + departmentId + ", post=" + post + ", department=" + department + "]";
	}

	private String workerSn;

    private String workerPw;

    private String workerName;

    private Integer departmentId;

    private String post;
    
    private Department department;

    public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public String getWorkerSn() {
        return workerSn;
    }

    public void setWorkerSn(String workerSn) {
        this.workerSn = workerSn == null ? null : workerSn.trim();
    }

    public String getWorkerPw() {
        return workerPw;
    }

    public void setWorkerPw(String workerPw) {
        this.workerPw = workerPw == null ? null : workerPw.trim();
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName == null ? null : workerName.trim();
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }
    
    
}