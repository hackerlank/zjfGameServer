package byCodeGame.game.moudle.target;

public class TargetConstant {
	
	/** 获取目标信息		*/
	public static final short GET_TARGET_INFO = 22601;
	/** 领取目标奖励		*/
	public static final short GET_TARGET_AWARD = 22602;
	/** 更新目标			*/
	public static final short UP_TARGET_INFO = 22603;
	/** 领取活动奖励		*/
	public static final short GET_ACTIVE_AWARD = 22604;
	/** 获取活动信息		*/
	public static final short GET_ACTIVE_INFO = 22605;

	/** 未完成状态			*/
	public static final byte NO_COMP = 0;
	/** 完成状态			*/
	public static final byte IS_COMP = 1;
	/** 没有后续目标		*/
	public static final int NO_TARGET = 0;
	/** 已经领取状态		*/
	public static final byte ALREADY_GET = 2;
	
	
	/** 等级目标			*/
	public static final Byte LV_TYPE = 1;
	
	/** 首冲活动			*/
	public static final int ACTIVE_FIRST_RECHARGE = 1;
	/** 开服连续登陆活动	*/
	public static final int ACTIVE_KEEP_LOGIN = 2;
}
