package com.ljp.constroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ljp.bean.Department;
import com.ljp.serviceInterface.DepartmentServiceFace;
import com.ljp.utils.MyCollenctionUtils;
import com.ljp.utils.PageInfo;

@Controller
@RequestMapping("/department")
public class DepartmentController {
	
	private DepartmentServiceFace departmentService;
	
	private List<Department> list;
	
	private boolean change;
	
	private PageInfo pageInfo;
	@Autowired
	public DepartmentController(DepartmentServiceFace departmentService) {
		this.departmentService=departmentService;
		System.out.println("DepartmentController.init");
		pageInfo=new PageInfo(0);
		change=true;
	}
	
	@RequestMapping(value= {"/list"},method=RequestMethod.GET)
	public String department_list(@RequestParam(value="toPage",required=false)String toPage,HttpServletRequest httpServletRequest,Model model) {
		System.out.println("DepartmentController.department_list");
		if(toPage!=null&&toPage.equals("next")) {
			pageInfo.nextPage();
		}else if(toPage!=null&&toPage.equals("last")) {
			pageInfo.lastPage();
		}
		
		if(change==true) {
			list = departmentService.queryAll();
			pageInfo.setRowCount(list.size());
			change=false;
		}
		
		List<Department> thisList = MyCollenctionUtils.addSomeToList(
				list, pageInfo.getLimit(), pageInfo.getPageSize());
		httpServletRequest.setAttribute("pageInfo", "��"+list.size()+"��Ŀ��"+"��"+pageInfo.getThisPage()+"/"+pageInfo.getPageCount()+"ҳ");
		model.addAttribute(thisList);
		return "department_list";
	}
	
	@RequestMapping(value= {"/to_update"},method=RequestMethod.GET)
	public String department_list(@RequestParam String sn,Model model) {
		model.addAttribute(departmentService.queryBySn(sn));
		return "department_update";
	}
	
	@RequestMapping(value= {"/update"},method=RequestMethod.POST)
	public String department_list(Department department) {
		int changeCount = departmentService.updateOne(department);
		if(changeCount>0) {
			change=true;
		}
		return "department_update";
	}
	
	@RequestMapping(value= {"/to_add"},method=RequestMethod.GET)
	public String department_add(Model model) {
		model.addAttribute(new Department());
		return "department_add";
	}
	
	@RequestMapping(value= {"/add"},method=RequestMethod.POST)
	public String department_add(Department department) {
		int changeCount = departmentService.insertOne(department);
		if(changeCount>0) {
			change=true;
		}
		return "department_add";
	}
	
	@RequestMapping(value= {"/remove"},method=RequestMethod.GET)
	public String department_remove(@RequestParam("sn")String sn) {
		int changeCount = departmentService.removeOne(sn);
		if(changeCount>0) {
			change=true;
		}
		return "redirect:/department/list";
	}
	
	@RequestMapping(value= {"/removeSome"},method=RequestMethod.GET)
	public String department_removeSome(@RequestParam("sn")String sn) {
		System.out.println(sn);
		int changeCount = departmentService.removeSome(sn);
		if(changeCount>0) {
			change=true;
		}
		return "redirect:/department/list";
	}
}
