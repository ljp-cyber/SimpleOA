package com.ljp.simpleoa.controller;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ljp.simpleoa.Constant;
import com.ljp.simpleoa.model.Worker;
import com.ljp.simpleoa.service.DepartmentService;
import com.ljp.simpleoa.service.WorkerService;
import com.ljp.simpleoa.utils.MyCollenctionUtils;
import com.ljp.simpleoa.utils.PageInfo;


@Controller
@RequestMapping("/worker")
public class WorkerController {
	
	@Autowired
	private WorkerService workerService;
	
	@Autowired
	private DepartmentService departmentService;
	
	private AtomicBoolean change = new AtomicBoolean(false);//TODO 之前用于判断缓存有没有改变暂时不用了，实现负责
	
	public WorkerController() {}
	
	private PageInfo processPageInfo(HttpServletRequest httpServletRequest) {
		HttpSession session = httpServletRequest.getSession();
		Object workerListPageInfo = session.getAttribute("workerListPageInfo");
		PageInfo pageInfo = null;
		if(workerListPageInfo==null) {
			pageInfo = new PageInfo(0);
			session.setAttribute("workerListPageInfo", pageInfo);
		}else {
			pageInfo = (PageInfo)workerListPageInfo;
		}
		return pageInfo;
		
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String worker_list(
			@RequestParam(value="toPage",required=false)String toPage,HttpServletRequest httpServletRequest,Model model) {
		Worker worker = (Worker)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		PageInfo pageInfo = processPageInfo(httpServletRequest);
		if(toPage!=null&&toPage.equals("next")) {
			pageInfo.nextPage();
		}else if(toPage!=null&&toPage.equals("last")) {
			pageInfo.lastPage();
		}
		List<Worker> list;
		if(worker.getWorkerId().equals(workerService.getGeneralManager().getWorkerId()))list = workerService.queryAll();
		else list = workerService.queryByDepartment(worker);
		pageInfo.setRowCount(list.size());
		List<Worker> thisList = MyCollenctionUtils.addSomeToList(
				list, pageInfo.getLimit(), pageInfo.getPageSize());
		httpServletRequest.setAttribute("pageInfo", "共"+list.size()+"项，"+"第"+pageInfo.getThisPage()+"/"+pageInfo.getPageCount()+"页");
		model.addAttribute(thisList);
		return "worker_list";
	}
	
	@RequestMapping(value="/remove",method=RequestMethod.GET)
	public String worker_remove(@RequestParam("sn")String sn) {
		int count = workerService.removeOne(sn);
		if(count>0) {
			change.set(true);
		}
		return "redirect:/worker/list";
	}
	
	@RequestMapping(value="/removeSome",method=RequestMethod.GET)
	public String worker_removeSome(@RequestParam("sn")String sn) {
		int count = workerService.removeSome(sn);
		if(count>0) {
			change.set(true);
		}
		return "redirect:/worker/list";
	}
	
	@RequestMapping(value = "/to_add",method=RequestMethod.GET)
	public String worker_add(Model model) {
		model.addAttribute("possList",Arrays.asList("员工","财务","总经理","部门经理"));
		model.addAttribute(departmentService.queryAll());
		model.addAttribute(new Worker());
		return "worker_add";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String worker_add(Worker worker) {
		worker.setWorkerPw("1234");
		int count = workerService.addOne(worker);
		if(count>0) {
			change.set(true);
		}
		return "redirect:/worker/to_add";
	}
	
	@RequestMapping(value="/to_update",method=RequestMethod.GET)
	public String worker_update(@RequestParam("sn")String sn,Model model) {
		model.addAttribute("possList",Arrays.asList(Constant.POST));
		model.addAttribute(departmentService.queryAll());
		model.addAttribute(workerService.queryOne(sn));
		return "worker_update";
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String worker_update(Worker worker) {
		int count = workerService.updateOne(worker);
		if(count>0) {
			change.set(true);
		}
		return "redirect:/worker/to_update?sn="+worker.getWorkerSn();
	}
}
