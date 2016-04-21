package byCodeGame.game.moudle.raid;

public class RaidConstant {
	
	/** 获取大厅信息		*/
	public static final short GET_LOBBY_INFO = 22201;
	/** 创建新房间			*/
	public static final short CREATE_NEW_ROOM = 22202;
	/** 加入房间			*/
	public static final short JOIN_ROOM = 22203;
	/** 获取房间信息		*/
	public static final short GET_ROOM_INFO = 22204;
	/** 更新房间信息		*/
	public static final short UPDATA_ROOM_INFO = 22205;
	/** 退出房间			*/
	public static final short QUIT_ROOM = 22206;
	/** 踢出房间			*/
	public static final short KICK_ROLE = 22207;
	/** 通知玩家被人踢出	*/
	public static final short BE_KICKED_FROM_ROOM = 22208;
	/** 设置上阵武将信息	*/
	public static final short SAVE_ROOMROLE_FORMATION = 22209;
	/** 准备完毕/取消准备	*/
	public static final short SET_ROLE_STATUS = 22210;
	/** 开始游戏			*/
	public static final short START_BATTLE = 22211;
	/** 通知客户端预加载	*/
	public static final short START_LOADING = 22212;
	/** 设置玩家加载进度	*/
	public static final short SET_LOADING_PROGRESS = 22213;
	/** 更新其他玩家加载进度	*/
	public static final short UPDATE_LOADING_PROGRESS = 22214;
	/** 所有玩家加载完毕	*/
	public static final short ALL_PLAYER_READY = 22215;
	/** 玩家场景切换完毕	*/
	public static final short READY_BATTLE = 22216;
	/** 战斗数据			*/
	public static final short BATTLE_MESSAGE = 22217;
	/** 更换武将			*/
	public static final short CHANGE_USEING_HERO = 22218;
	/** 战斗结果			*/
	public static final short RESULT_BATTLE = 22219;
	
	
	/** 玩家团队战役次数的判定条件			*/
	public static byte MIN_RAID_TIMES = 0; 
	
	/** 房间的状态		等待				*/
	public static byte HOUSE_STATUS_WAITE = 0;
	/** 房间的状态		开始				*/
	public static byte HOUSE_STATUS_START = 1;
	/** 房间的状态		游戏				*/
	public static byte HOUSE_STATUS_GAME = 2;
	
	/** 房间成员的编号 	1:房主（成员1）	*/
	public static int HOUSE_PLAYER_OW = 0;
	/** 房间成员的编号	2：成员2			*/
	public static int HOUSE_PLAYER_2 = 1;
	/** 房间成员的编号	3：成员3			*/
	public static int HOUSE_PLAYER_3 = 2;
	/** 房间成员的数量   					*/
	public static int HOUSE_PLAYER_NUMBER = 3;
	
	/** 未设定武将的情况下返回的武将编号	*/
	public static int HERO_ID_NULL = 0;
	
	/** 玩家的状态  0准备中				*/
	public static byte HOUSE_PLAYER_STATUS_NOT = 0;
	/** 玩家的状态  1准备完毕				*/
	public static byte HOUSE_PLAYER_STATUS_READY = 1;
	/** 玩家的状态  2游戏中				*/
	public static byte HOUSE_PLAYER_STATUS_GAME = 2;
	/** 房主创建房间时的装备状态			*/
	public static byte HOUSE_OW_STATUS = 1;
	
	/** 判定玩家加载是否完成				*/
	public static double LOADING_READLY = 1.0;
	
	public static byte BYTE_1 = 1;
	public static byte BYTE_2 = 2;
	
	/** 伤害判定的临界值				*/
	public static int HURT_VOTE = 0;
	/** 攻击的伤害最小值				*/
	public static int HURT_MIN = 1;
	/** 攻击类型	物理					*/
	public static byte ATK_TYPE_ATK = 1;
	/** 攻击类型	魔法					*/
	public static byte ATK_TYPE_MATK = 2; 
	
	/** 胜利阵营	玩家					*/
	public static int WIN_SIDE_PLAYER = 1;
	/** 胜利阵营	AI					*/
	public static int WIN_SIDE_AI = 2;
	
	public static enum State{
		/** 未出场(产卵)	*/
		OUT_BATTLE(0),
		/** 待机		 	*/
		WAIT(3),
		/** 移动		 	*/
		MOVE(1),
		/** 攻击		 	*/
		ATK(2),
		/** 死亡		 	*/
		DEAD(4),
		/** 技能		 	*/
		SKILL(5),
		/** BUFF		*/
		BUFF(6),
		/** 换人			*/
		CHANGE(10);
		
		private byte val;
		
		State(int val){
			this.val = (byte)val;
		}
		public byte getVal(){
			return val;
		}
	}

	public static enum Camp
	{
		/** 己方阵营	-1*/
		MY(-1),
		/** 敌方阵营	 1*/
		ENEMY(1);
		
		private byte val;
		
		Camp(int val)
		{
			this.val = (byte)val;
		}
		public byte getVal()
		{
			return val;
		}
	}
	
	
	public static enum Pos
	{
		/** 初始的Y位置	*/
		FIRST(410),
		/** 累加值		*/
		ADD(100);
		
		private double val;
		
		Pos(double val)
		{
			this.val = val;
		}
		public double getVal()
		{
			return val;
		}
	}
	
	public static enum Result
	{
		/** 失败		*/
		FAIL(0),
		/** 胜利		*/
		WIN(1);
		
		private byte val;
		
		Result(int val)
		{
			this.val = (byte)val;
		}
		public byte getVal()
		{
			return val;
		}
	}
}
