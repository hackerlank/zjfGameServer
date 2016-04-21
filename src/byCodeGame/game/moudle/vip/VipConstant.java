package byCodeGame.game.moudle.vip;

public class VipConstant {

	/** 充值	 */
	public static final short RECHARGE = 21502;
	
	/** 获取VIP信息	*/
	public static final short GET_VIP_INFO = 21515;
	/** vip等级提升	*/
	public static final short VIP_LV_UP = 21516;
	/** Vip礼包信息	*/
	public static final short GET_VIP_AWARD_INFO = 21517;
	/** 领取Vip礼包	*/
	public static final short GET_VIP_AWARD = 21518;
	
	
	/** 礼包状态   已领取 ：1	*/
	public static final byte VIP_AWARD_GET = 1;
	/** 礼包状态   未领取 ：2	*/
	public static final byte VIP_AWARD_CAN_GET = 2;
	/** 礼包状态   不可领取 ：3	*/
	public static final byte VIP_AWARD_CAN_NOT_GET = 3;
	
	/** 过滤0级礼包时使用		*/
	public static final int NUM_0 = 0;
	/** 首冲未完成				*/
	public static final byte NO_FIRST_RECHARGE = 0;
	/** 首冲可领取				*/
	public static final byte CAN_GET_FIRST_R = 1;
	/** 首冲已领取				*/
	public static final byte ALREADY_GET_FIRST = 2;
}
