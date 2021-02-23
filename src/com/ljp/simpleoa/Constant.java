package com.ljp.simpleoa;

public class Constant {

	
	public static final String POST_GM = "总经理";
	public static final String POST_FM = "财务";
	public static final String POST_DM = "部门经理";
	public static final String POST_WK = "员工";
	public static final String[] POST = { POST_GM, POST_FM, POST_DM, POST_WK };

	
	public static final String COST_JIAOTONG = "交通";
	public static final String COST_ZHUSHU = "餐饮";
	public static final String COST_CANYIN = "住宿";
	public static final String COST_BANGONG = "办公";
	public static final String COST_OTHER = "其他";
	public static final String[] COST_TYPE = { COST_JIAOTONG, COST_ZHUSHU, COST_CANYIN, COST_BANGONG, COST_OTHER };

	
	public static final String STEP_CREAT = "创建";
	public static final String STEP_MODIFY = "修改";
	public static final String STEP_CHECK1 = "初审";
	public static final String STEP_CHECK2 = "复审";
	public static final String STEP_PAY = "支付";
	public static final String STEP_FINISH = "完成";
	public static final String STEP_END = "终止";
	public static final String[] DEAL_PROCESS = { STEP_CREAT, STEP_MODIFY, STEP_CHECK1, STEP_CHECK2, STEP_PAY, STEP_END,
			STEP_FINISH };

	
	public static final String STATE_CREAT = null;
	public static final String STATE_MODIFY = "待提交";
	public static final String STATE_CHECK1 = "待部经理审核";
	public static final String STATE_CHECK2 = "待总经理审核";
	public static final String STATE_PAY = "待支付";
	public static final String STATE_FINISH = "已打款";
	public static final String STATE_END = "已终结";
	public static final String[] RECEIPTS_STATE = { STATE_CREAT, STATE_MODIFY, STATE_CHECK1, STATE_CHECK2, STATE_PAY,
			STATE_FINISH, STATE_END };

	
	public static int RECHECK_MONEY = 5000;
	
	public static int PAGE_SIZE=10;
	
	public static String PAGE_NEXT="next";
	public static String PAGE_LAST="last";

	
	public static final String STEP_CREAT_CREAT = "创建";
	public static final String STEP_MODIFY_MODIFY = "修改";
	public static final String STEP_MODIFY_SUBMIT = "提交";
	public static final String STEP_CHECK_PAST = "通过";
	public static final String STEP_CHECK_BACK = "打回";
	public static final String STEP_CHECK_REFUSE = "拒绝";
	public static final String STEP_PAY_PAY = "打款";
	public static final String[] DEAL_OPERATION = { STEP_CREAT_CREAT, STEP_MODIFY_MODIFY, STEP_MODIFY_SUBMIT,
			STEP_CHECK_PAST, STEP_CHECK_BACK, STEP_CHECK_REFUSE, STEP_PAY_PAY };

	public static final String CHECK_TYPE_PERSONAL = "个人";
	public static final String CHECK_TYPE_UNITE = "会审";
	public static final String[] CHECK_TYPE= {CHECK_TYPE_PERSONAL,CHECK_TYPE_UNITE};

}
