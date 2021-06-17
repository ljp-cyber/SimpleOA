package com.ljp.simpleoa.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ljp.simpleoa.Constant;

public class Worker implements Serializable,UserDetails{
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((workerId == null) ? 0 : workerId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Worker other = (Worker) obj;
		if (workerId == null) {
			if (other.workerId != null)
				return false;
		} else if (!workerId.equals(other.workerId))
			return false;
		return true;
	}

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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO 等待重构转移此段代码到合适的地方
		ArrayList<GrantedAuthority> list = new ArrayList<>();
		System.out.println(getPost());
		String roleName;
		switch(getPost()) {
		case Constant.POST_GM:
			roleName=Constant.ROLE_GM;
			break;
		case Constant.POST_DM:
			roleName=Constant.ROLE_DM;;
			break;
		case Constant.POST_WK:
			roleName=Constant.ROLE_WK;
			break;
		case Constant.POST_FM:
			roleName=Constant.ROLE_FM;
			break;
//		case Constant.POST_IV:
//			roleName=Constant.ROLE_SHOW;
//			break;
		default:
			roleName=Constant.ROLE_TOURIST;
		}
		GrantedAuthority ga = new SimpleGrantedAuthority(roleName);
		list.add(ga);
		return list;
	}

	@Override
	public String getPassword() {
		return getWorkerPw();
	}

	@Override
	public String getUsername() {
		return getWorkerSn();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
    
    
}