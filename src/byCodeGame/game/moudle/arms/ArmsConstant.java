package byCodeGame.game.moudle.arms;

public class ArmsConstant {

	/** 获取兵种科技信息 */
	public static final short GET_ARMS_RESEARCH = 22401;
	/** 根据兵种id兵种升级 */
	public static final short ADD_ARMS_RESEARCH_LV = 22402;

	/** 获取天赋信息 */
	public static final short GET_ROLE_ARMY_INFO = 22450;
	/** 升级天赋 */
	public static final short UP_ROLE_ARMY = 22451;
	/** 重置天赋 */
	public static final short RE_SET_ROLE_ARMY = 22452;

	/** 获得某一个英雄的兵种信息 */
	public static final short GET_HERO_SOLDIER_DATA = 22401;
	/** 升级兵种等级 */
	public static final short UPDATE_HERO_SOLDIER_EXP = 22402;
	/** 升级英雄兵种技能 */
	public static final short UPDATE_HERO_SOLDIER_SKILL = 22403;
	/** 进阶英雄兵种 */
	public static final short UPDATE_HERO_SOLDIER_TYPE = 22404;
	/** 更换英雄兵种 */
	public static final short CHANGE_HERO_SOLDIER = 22405;

	/** 初次升级天赋的等级 */
	public static final int FIRST_UP = 1;
	/** 重置天赋的消耗 */
	public static final int RE_SET_NEED = 100;
	/** 天赋的初始等级 */
	public static final int NUMBER_0 = 0;
	/** 达到前置点数 */
	public static final byte CAN_UP = 1;
	/** 未到达前置点数 */
	public static final byte CAN_NOT_UP = 0;
	/** 每次通关获取的天赋点 */
	public static final int NUM_CHAPTER = 10;
	/** 兵种已解锁 */
	public static final byte UNLOCK = 1;
	/** 兵种未解锁 */
	public static final byte NOT_UNLOCK = 0;
	/** 兵种不可用 **/
	public static final byte CAN_NOT_USE = 2;
	/** 一阶兵种 **/
	public static final int QUALITY_1 = 1;
	/**二阶兵种*/
	public static final int QUALITY_2 = 2;
	/**三阶兵种*/
	public static final int QUALITY_3 = 3;
	/**兵种升级*/
	public static final short HERO_SOLDIER_LV_UP = 22406;
	/** 兵种进阶最大值	 */
	public static final int MAX_HERO_SOLDIER_QUALITY = 3;
	/*初级印绶id	 */
	public static final int SMALL_HERO_SOLDIER_QUALITY_PROP_ID = 20031;
	/**高级印绶id	 */
	public static final int BIG_HERO_SOLDIER_QUALITY_PROP_ID = 20032;
	/*一阶兵符id	 */
	public static final int HERO_SOLDIER_EXP1_PROP_ID = 20033;
	/** 二阶兵符id	 */
	public static final int HERO_SOLDIER_EXP2_PROP_ID = 20034;
	/*三阶兵符id	 */
	public static final int HERO_SOLDIER_EXP3_PROP_ID = 20035;
	/*100%	 */
	public static final float percent100 = 1.0f;
	/**50%	 */
	public static final float percent50 = 0.5f;
	/**20%	 */
	public static final float percent20 = 0.2f;
	
}
