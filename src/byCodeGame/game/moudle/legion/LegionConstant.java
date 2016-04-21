package byCodeGame.game.moudle.legion;
public class LegionConstant {
	/** 创建军团	 */
	public static final short CREAT_LEGION = 20701;
	/** 获取军团信息	 */
	public static final short GET_LEGION_DATA = 20702;
	/** 获取军团列表	 */
	public static final short GET_LEGION_LIST = 20703;
	/** 申请加入军团	 */
	public static final short APPLY_JOIN_LEGION = 20704;
	/** 获取申请成员列表	 */
	public static final short GET_APPLY_LIST = 20705;
	/** 同意玩家加入军团	 */
	public static final short AGREE_JOIN_LEGION = 20706;
	/** 设置副团长		 */
	public static final short SET_DEPUTY = 20707;
	/** 修改军团图标	 */
	public static final short CHANG_FACE_ID = 20708;
	/** 修改军团公告	 */
	public static final short CHANG_NOTICE = 20709;
	/** 获取成员列表	 */
	public static final short GET_ALL_MEMBER = 20710;
	/** 退出公会	 	 */
	public static final short QUIT_LEGION = 20711;
	/** 公会ID搜索		 */
	public static final short SEARCH_LEGION = 20712;
	/** 升级军团科技	 	 */
	public static final short UPGRADE_SCIENCE = 20713;
	/** 设定军团科技升级的种类 */
	public static final short SET_APPOINT_SCIENCE = 20714;
	/** 设定自动接受是否开启	 */
	public static final short SET_AUTO_AGREE_JOIN = 20715;
	/** 军团兑换			 */
	public static final short EXCHANGE_LEGION = 20716;
	/** 拒绝玩家加入		 */
	public static final short REJECT_JOIN = 20717;
	/** 通知客户端信息改变	 */
	public static final short INFO_CHANGE = 20718;
	/** 撤销军团申请		 */
	public static final short CANCEL_APPLY = 20719;
	/** 增加军团人数		 */
	public static final short ADD_MAX_PEOPLE_NUM = 20720;
	/** 新玩家加入军团		 */
	public static final short NEW_PLAYER_JOIN = 20721;
	/** 玩家退出军团		 */
	public static final short PLAYER_QUIT_LEGION = 20722;
	/** 玩家被踢出军团		 */
	public static final short BE_KICK_LEGION = 20723;
	/** 获取兑换信息		 */
	public static final short GET_EXCHANGE_INFO = 20724;
	/** 世界大战集结旗		 */
	public static final short SET_TARGET = 20725;
	/** 修改旗号			 */
	public static final short CHANGE_SHORT_NAME = 20726;
	
	/** 创建军团所需金币数	 */
	public static final int CREAT_LEGION_NEED_GOLD = 300;
	
	/** 初始军团头像ID	 */
	public static final int DEFAULT_FACE_ID = 1;
	/** 初始军团等级	 */
	public static final byte DEFAULT_LV = 1;
	/** 初始军团最大人数	 */
	public static final int DEFAULT_PEOPLE_NUM = 50;
	/** 申请军团最大数量	 */
	public static final int MAX_ROLE_APPLY_NUM = 3;
	
	/** 主角未加入军团	 */
	public static final int NOT_IN_LEGION = 0;
	
	/** 玩家已申请		 */
	public static final int IS_APPLY = 1;
	/** 玩家未申请		 */
	public static final int NOT_APPLY = 0;
	
	/** 设置副团长	 	*/
	public static final byte DEPUTY_TYPE_1 = 1;
	/** 撤销副团长	 	*/
	public static final byte DEPUTY_TYPE_2 = 2;
	/** 踢出成员	 	*/
	public static final byte DEPUTY_TYPE_3 = 3;
	/** 转让会长	 	*/
	public static final byte DEPUTY_TYPE_4 = 4;
	
	/** 成员类型 会长	*/
	public static final byte MEMBER_TYPE1 = 1;
	/** 成员类型 副会长	*/
	public static final byte MEMBER_TYPE2 = 2;
	/** 成员类型 成员	*/
	public static final byte MEMBER_TYPE3 = 3;
	
	/** 退出公会后公会ID */
	public static final int QUITE_LEGION = 0;
	/** 升级公会科技时 	使用的类型 银币*/
	public static final byte TYPE_UP_Y = 1;
	/** 升级公会科技时	使用的类型 金币*/
	public static final byte TYPE_UP_J = 2;
	/** 计算军团科技	是否升级		  */
	public static final int COUNT_LV_UP = 0;
	/** 计算军团科技       经验提高的倍数	  */
	public static final int MULTIPLE_UP = 1000;
	/** 计算军团科技      VIP等级提高的倍数	  */
	public static final int	MULTIPLE_VIP_LV = 500;
	/** 升级时使用银币	贡献提升的倍数(除法)*/
	public static final int MULTIPLE_CONTRIBUTION_DOWN = 100;
	/** 科技升级 数量:1			  */
	public static final int UP = 1;
	/** 科技的类型	最小值		  */
	public static final int TYPE_MIN = 0;
	/** 科技的类型	最大值		  */
	public static final int TYPE_MAX = 5;
	/** 科技初始值*/
	public static final String SCIENCE_INIT = "1,0;1,0;1,0;1,0;1,0;";
	
	/** 军团图标的最小值			  */
	public static final int FACEID_MIN = 0;
	/** 军团图标的最大值			  */
	public static final int FACEID_MAX = 128;
	
	/** 玩家升级军团科技每天的判定值	  	*/
	public static final byte UP_SCIENCE = 0;
	/** 玩家升级军团科技每天的判定值		*/
	public static final byte UP_LIMIT_SCIENCE = 1;
	/** 默认的初始军团科技升级 类型			*/
	public static final byte DE_APPOINT_SCIENCE = 1;
	
	/** 判断会长是否可以解散工会			*/
	public static final int MIN_LEGION_SIZE = 1;
	
	/** 自动接受功能的状态	0关闭		*/
	public static final byte CLOSE_AUTO_AGREE = 0;
	/** 自动接受功能的状态	1开启		*/
	public static final byte OPEN_AUTO_AGREE = 1;
	/** 自动接受弄能的最低等级			*/
	public static final int MIN_LV_AUTO = 30;
	
	/** 军团兑换的物品分类 	1装备		*/
	public static final byte EQUIP_LEGION = 1;
	/** 军团兑换的物品分类	2道具		*/
	public static final byte PROP_LEGION = 2;
	/** 军团兑换的物品分类	3武将		*/
	public static final byte HERO_LEGION = 3;
	/** 拼接军团奖励类型	9 装备		*/
	public static final byte TYPE_EQUIP = 9;
	/** 拼接军团奖励类型	10道具		*/
	public static final byte TYPE_ITEM = 10;

	
	/** 道具兑换的数量					*/
	public static final int NUMBER_PROP = 1;
	/** 军团兑换的邮件标题				*/
	public static final String TITLE_EXCHANGE_LEGION = "zlpsb 你欠我一个邮件标题";
	/** 军团兑换的邮件正文				*/
	public static final String CONTEXT_EXCHANGE_LEGION = "zlpsb 你欠我一个邮件内容";
	/** 军团兑换的邮件类型				*/
	public static final byte TYPE_EXCHANGE_LEGION = 2;
	
	/** 副团长的最大数量				*/
	public static final int MAX_DEPT_LEGION = 3;
	/** 自动申请的等级上限				*/
	public static final int MAX_LV_AUTO = 80;
	
	/** 创建公会时玩家的最低等级			*/
	public static final short MIN_LV_CREAT = 1;
	/** 公会的最大人数					*/
	public static final int MAX_PEOPLE_NUM = 200;
	/** 扩充公会人数每次增加的值			*/
	public static final int ADD_NUM_PEOPLE = 5;
	/** 扩充公会人数的花费				*/
	public static final int COST_ADD_PEOPLE = 200;
	
	/** 强效研发  判定值 50 				*/
	public static final int USE_GOLD_COST_1 = 50;
	/** 强效研发 判定值 100				*/
	public static final int USE_GOLD_COST_2 = 100;
	/** 强效研发 判定值 200				*/
	public static final int USE_GOLD_COST_3 = 200;
	/** 强效研发 vip等级判定  5			*/
	public static final short USE_GOLD_VIP_LV_1 = 5;
	/** 强效研发 vip等级判定  7			*/
	public static final short USE_GOLD_VIP_LV_2 = 7;
	/** 强效研发的倍数					*/
	public static final double USE_GOLD_D1 = 1.5;
	public static final double USE_GOLD_D2 = 2.0;
	public static final double USE_GOLD_D3 = 3.0;
	
	/** 公会名字的最大长度				*/
	public static final int MAX_LENGTH_LEGION_NAME = 8;
	/** 公会公告的最大长度				*/
	public static final int MAX_LENGTH_LEGION_NOTICE = 100;
	/** 判定升级科技时比较的军团等级科技	*/
	public static final int NEED_TYPE_SCIENCE = 1;
	
	/** 被踢出公会的邮件标题				*/
	public static final String BE_KICK_LEGION_TITILE = "公会消息";
	/** 被踢出公会的邮件正文1			*/
	public static final String BE_KICK_LEGION_TEXT_1 = "主公抱歉：您被" ;
	/** 被踢出公会的邮件正文2			*/
	public static final String BE_KICK_LEGION_TEXT_2 = "请离了公会！" ;
	
	/** 不可集结城市编号				*/
	public static final int[] ILLEGAL_CITY_ID = {53,54,55,10,29,52};
	/** 旗号的长度判定					*/
	public static final int MAX_LENGTH_SHORT_NAME = 1;
}
