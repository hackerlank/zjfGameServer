package byCodeGame.game.entity.bo;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.po.RoomData;

/**
 * 团队战役大厅(全服唯一)
 * @author xjd
 *
 */
public class BattleLobby {
	private static BattleLobby battleLobby = null;
	private BattleLobby() {
		
	}
	/**
	 * 懒汉单例
	 * @return
	 * @author xjd
	 */
	public static BattleLobby getInstance()
	{
		if(battleLobby == null)
		{
			battleLobby = new BattleLobby();
		}
		return battleLobby;
	}
	
	/** 存放所有房间信息的Map	*/
	private Map<String, RoomData> roomMap = new HashMap<String, RoomData>();
	
	/** 存放战斗中的房间		*/
	private Map<String, RoomData> battleRoomMap = new HashMap<String, RoomData>();
	
	/** 大厅最大的房间数量		*/
	public final int MAX_ROOM_NUM = 20;
	
	
	/** 创建房间的方法(加锁)		*/
	public synchronized void  creatNewRoom(String key , RoomData value)
	{
		roomMap.put(key, value);
	}
	/** 删除房间的方法(带锁)		*/
	public synchronized void deleteRoom(String key)
	{
		roomMap.remove(key);
	}
	
	public Map<String, RoomData> getRoomMap() {
		return roomMap;
	}
	public void setRoomMap(Map<String, RoomData> roomMap) {
		this.roomMap = roomMap;
	}
	public Map<String, RoomData> getBattleRoomMap() {
		return battleRoomMap;
	}
	public void setBattleRoomMap(Map<String, RoomData> battleRoomMap) {
		this.battleRoomMap = battleRoomMap;
	}
	
}
