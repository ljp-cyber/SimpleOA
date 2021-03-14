package com.ljp.simpleoa.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ljp.simpleoa.Constant;
import com.ljp.simpleoa.model.Receipts;
import com.ljp.simpleoa.model.ReceiptsRecord;
import com.ljp.simpleoa.model.Worker;
import com.ljp.simpleoa.service.ReceiptsService;
import com.ljp.simpleoa.utils.PageInfo;

@Controller
@RequestMapping("/receipts")
public class ReceiptsController {

	@Autowired
	ReceiptsService receiptsService;

	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String receipts_add(Model model) {
		Receipts receipts = new Receipts();
		model.addAttribute(receipts);
		model.addAttribute("items", Constant.COST_TYPE);
		return "receipts_add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String receipts_add(Receipts receipts, HttpServletRequest httpServletRequest) {
		System.out.println("*****receipts_add******" + receipts);
//		HttpSession session = httpServletRequest.getSession();
//		Worker user = (Worker) session.getAttribute("worker");
		Worker user = (Worker)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		receiptsService.creat(receipts, user);
		return "redirect:/receipts/toAdd";
	}

	@RequestMapping(value = "/toCheck", method = RequestMethod.POST)
	public String receipts_check(Integer receiptsId, Model model) {
		model.addAttribute(receiptsService.queryReceiptsByPK(receiptsId, true));
		ReceiptsRecord receiptsRecord = new ReceiptsRecord();
		receiptsRecord.setReceiptsId(receiptsId);
		model.addAttribute("record", receiptsRecord);
		return "receipts_check";
	}

	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public String receipts_check(ReceiptsRecord record, HttpServletRequest httpServletRequest, String dealWay) {
		System.out.println("*****receipts_check******" + record);
		System.out.println("*****receipts_check******" + dealWay);
//		HttpSession session = httpServletRequest.getSession();
//		Worker user = (Worker) session.getAttribute("worker");
		Worker user = (Worker)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		receiptsService.check(record, user, dealWay);
		return "redirect:/receipts/deal";
	}

	@RequestMapping(value = "/deal", method = RequestMethod.GET)
	public String receipts_deal(@RequestParam(value = "toPage", required = false) String toPage, Model model,
			HttpServletRequest httpServletRequest) {
		HttpSession session = httpServletRequest.getSession();
//		Worker user = (Worker) session.getAttribute("worker");
		Worker user = (Worker)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		PageInfo pageInfo = (PageInfo) session.getAttribute("dealPageInfo");
		if (pageInfo == null) {
			pageInfo = new PageInfo(Constant.PAGE_SIZE, 0);
		}

		model.addAttribute(receiptsService.getReceiptsByDealerIdAndPageInfo(user,pageInfo,toPage));
		session.setAttribute("dealPageInfo", pageInfo);
		return "receipts_deal";
	}

	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String receipts_detail(Integer receiptsId, HttpServletRequest httpServletRequest) {
//		HttpSession session = httpServletRequest.getSession();
//		Worker user = (Worker) session.getAttribute("worker");
		Worker user = (Worker)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		receiptsService.submit(receiptsId, user);
		return "redirect:/receipts/deal";
	}

	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public String receipts_detail(Integer receiptsId, Model model) {
		model.addAttribute(receiptsService.queryReceiptsByPK(receiptsId, true));
		return "receipts_detail";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String receipts_list(@RequestParam(value = "toPage", required = false) String toPage, Model model,
			HttpServletRequest httpServletRequest) {
		HttpSession session = httpServletRequest.getSession();
//		Worker user = (Worker) session.getAttribute("worker");
		Worker user = (Worker)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		PageInfo pageInfo = (PageInfo) session.getAttribute("pageInfo");
		if (pageInfo == null) {
			pageInfo = new PageInfo(Constant.PAGE_SIZE, 0);
		}

		List<Receipts> receiptsList = receiptsService.getReceiptsByCreaterIdAndPageInfo(user, pageInfo, toPage);

		model.addAttribute(receiptsList);
		session.setAttribute("pageInfo", pageInfo);
		return "receipts_list";
	}
	
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String receipts_list(String receiptsId) {
		System.out.println(receiptsId);
		receiptsService.removeReceipts(receiptsId);
		return "redirect:/receipts/list";
	}

	@RequestMapping(value = "/toUpdate", method = RequestMethod.POST)
	public String receipts_update(Integer receiptsId, Model model) {
		model.addAttribute(receiptsService.queryReceiptsByPK(receiptsId, true));
		model.addAttribute("items", Constant.COST_TYPE);
		return "receipts_update";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String receipts_update(Receipts receipts, HttpServletRequest httpServletRequest,RedirectAttributes attributes) {
		System.out.println("******receipts_update******" + receipts);
//		HttpSession session = httpServletRequest.getSession();
//		Worker user = (Worker) session.getAttribute("worker");
		Worker user = (Worker)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		receiptsService.update(receipts, user);
		return "redirect:/receipts/deal";
	}
}
