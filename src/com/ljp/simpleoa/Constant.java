package com.ljp.simpleoa;

public class Constant {

	
	public static final String POST_GM = "�ܾ���";
	public static final String POST_FM = "����";
	public static final String POST_DM = "���ž���";
	public static final String POST_WK = "Ա��";
	public static final String[] POST = { POST_GM, POST_FM, POST_DM, POST_WK };

	
	public static final String COST_JIAOTONG = "��ͨ";
	public static final String COST_ZHUSHU = "����";
	public static final String COST_CANYIN = "ס��";
	public static final String COST_BANGONG = "�칫";
	public static final String COST_OTHER = "����";
	public static final String[] COST_TYPE = { COST_JIAOTONG, COST_ZHUSHU, COST_CANYIN, COST_BANGONG, COST_OTHER };

	
	public static final String STEP_CREAT = "����";
	public static final String STEP_MODIFY = "�޸�";
	public static final String STEP_CHECK1 = "����";
	public static final String STEP_CHECK2 = "����";
	public static final String STEP_PAY = "֧��";
	public static final String STEP_FINISH = "���";
	public static final String STEP_END = "��ֹ";
	public static final String[] DEAL_PROCESS = { STEP_CREAT, STEP_MODIFY, STEP_CHECK1, STEP_CHECK2, STEP_PAY, STEP_END,
			STEP_FINISH };

	
	public static final String STATE_CREAT = null;
	public static final String STATE_MODIFY = "���ύ";
	public static final String STATE_CHECK1 = "�����������";
	public static final String STATE_CHECK2 = "���ܾ������";
	public static final String STATE_PAY = "��֧��";
	public static final String STATE_FINISH = "�Ѵ��";
	public static final String STATE_END = "���ս�";
	public static final String[] RECEIPTS_STATE = { STATE_CREAT, STATE_MODIFY, STATE_CHECK1, STATE_CHECK2, STATE_PAY,
			STATE_FINISH, STATE_END };

	
	public static int RECHECK_MONEY = 5000;
	
	public static int PAGE_SIZE=10;
	
	public static String PAGE_NEXT="next";
	public static String PAGE_LAST="last";

	
	public static final String STEP_CREAT_CREAT = "����";
	public static final String STEP_MODIFY_MODIFY = "�޸�";
	public static final String STEP_MODIFY_SUBMIT = "�ύ";
	public static final String STEP_CHECK_PAST = "ͨ��";
	public static final String STEP_CHECK_BACK = "���";
	public static final String STEP_CHECK_REFUSE = "�ܾ�";
	public static final String STEP_PAY_PAY = "���";
	public static final String[] DEAL_OPERATION = { STEP_CREAT_CREAT, STEP_MODIFY_MODIFY, STEP_MODIFY_SUBMIT,
			STEP_CHECK_PAST, STEP_CHECK_BACK, STEP_CHECK_REFUSE, STEP_PAY_PAY };

	public static final String CHECK_TYPE_PERSONAL = "����";
	public static final String CHECK_TYPE_UNITE = "����";
	public static final String[] CHECK_TYPE= {CHECK_TYPE_PERSONAL,CHECK_TYPE_UNITE};

}
