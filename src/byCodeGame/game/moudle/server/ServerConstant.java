package byCodeGame.game.moudle.server;

public class ServerConstant {
	public static final short NEW_HISTORY = 21525;
	
	public static final int SERVER_ID = 1;
	public static final int START_YEAR = 180;
	public static final int YEAR_ADD = 1;
	
	public static final byte SEASON_SPRING = 0;
	public static final byte SEASON_SUMMER = 1;
	public static final byte SEASON_AUTUMN = 2;
	public static final byte SEASON_WINTER = 3;
	
	/** 城市公会ID为0			*/
	public static final int NO_LEGION = 0;
	
	/** 世界大战记录类型	玩家行军成功	玩家	*/
	public static final byte MARCH_SUESS = 1;
	/** 世界大战记录类型	玩家行军成功	公会	*/
	public static final byte MARCH_SUESS_LEGION = 2;
	/** 世界大战记录类型	玩家行军失败	玩家	*/
	public static final byte MARCH_FAIL = 3;
	/** 世界大战记录类型	玩家战斗失败	玩家	*/
	public static final byte FIGHT_LOSS = 4;
	/** 世界大战记录类型	玩家战斗胜利	玩家	*/
	public static final byte FIGHT_WIN = 5;
	/** 世界大战记录类型	玩家战斗失败	公会	*/
	public static final byte FIGHT_LOSS_LEGION = 6;
	/** 世界大战记录类型	玩家战斗胜利	公会	*/
	public static final byte FIGHT_WIN_LEGION = 7;
	/** 世界大战记录类型	驻守信息		玩家	*/
	public static final byte CHANGE_DEFINFO = 8;
	/** 世界大战记录类型	驻守信息		公会	*/
	public static final byte CHANGE_DEFINFO_LEGION = 9;
	/** 世界大战记录类型	据点失效		玩家	*/
	public static final byte HOME_FAIL = 14;
	
	/** 世界大战记录类型	获得城市		势力	*/
	public static final byte GET_CITY_COUNTRY = 16;
	/** 世界大战记录类型	获得城市		公会	*/
	public static final byte GET_CITY_LEGION = 17;
	/** 世界大战记录类型	获得城市		自身	*/
	public static final byte GET_CITY = 18;
	/** 世界大战记录类型	失去城市		势力	*/
	public static final byte LOSS_CITY_COUNTRY = 19; 
	/** 世界大战记录类型	获得城市		公会	*/
	public static final byte LOSS_CITY_LEGION = 20;
	/** 世界大战记录类型	失去城市		自身	*/
	public static final byte LOSS_CITY = 22;
	/** 世界大战记录类型	失去矿点		自身	*/
	public static final byte LOSS_MINE = 23;
}

