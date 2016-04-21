package byCodeGame.game.entity.po;

import java.util.ArrayList;
import java.util.List;

import cn.bycode.game.battle.data.ResultData;
import cn.bycode.game.battle.data.TroopData;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.entity.file.RaidArmsConfig;


/**
 * 组队战役中的房间
 * @author xjd
 * 
 * 存放该房间所有信息
 *
 */
	/** 房间唯一索引			*/
public class RoomData {
	private String uuid;
	/** 房主ID				*/
	private int roomOw;
	/** 房间玩家的信息			*/
	private List<RoomRole> roleList = new ArrayList<RoomRole>();
	/** 房间状态				*/
	private byte status;
	/** 副本配置表应用			*/
	private RaidArmsConfig raidArmsConfig;
	/** AI敌人				*/
	private List<TroopData> enemyList = new ArrayList<TroopData>();
	/** 玩家阵容数据			*/
	private List<TroopData> myDataList = new ArrayList<TroopData>();
	/** 战斗结果				*/
	private List<ResultData> resList = new ArrayList<ResultData>();
	/** 战役结果				*/
	private int battleRes;
	/** 创建时间				*/
	private String creatTime;
	
	/**
	 * 增加房间成员（带锁）
	 * @author xjd
	 */
	public synchronized short addRoomRole(RoomRole value)
	{
		if(roleList.size() >= 3)
		{
			return ErrorCode.MAX_ROOM_NUM ;
		}else {
			roleList.add(value);
			return ErrorCode.SUCCESS;
		}
	}
	
	/**
	 * 清除玩家在房间的信息(带锁)
	 * @param key
	 * @author xjd
	 */
	public synchronized void removeRoomRole(int key)
	{
		roleList.remove(key);
	}
	
	private int needLv;
	/*********************************Get&Set***************************************************/
	
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	public int getNeedLv() {
		return needLv;
	}
	public void setNeedLv(int needLv) {
		this.needLv = needLv;
	}
	public RaidArmsConfig getRaidArmsConfig() {
		return raidArmsConfig;
	}
	public void setRaidArmsConfig(RaidArmsConfig raidArmsConfig) {
		this.raidArmsConfig = raidArmsConfig;
	}
	public int getRoomOw() {
		return roomOw;
	}
	public void setRoomOw(int roomOw) {
		this.roomOw = roomOw;
	}

	public List<RoomRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RoomRole> roleList) {
		this.roleList = roleList;
	}

	public String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

	public List<ResultData> getResList() {
		return resList;
	}

	public void setResList(List<ResultData> resList) {
		this.resList = resList;
	}

	public List<TroopData> getEnemyList() {
		return enemyList;
	}

	public void setEnemyList(List<TroopData> enemyList) {
		this.enemyList = enemyList;
	}

	public List<TroopData> getMyDataList() {
		return myDataList;
	}

	public void setMyDataList(List<TroopData> myDataList) {
		this.myDataList = myDataList;
	}

	public int getBattleRes() {
		return battleRes;
	}

	public void setBattleRes(int battleRes) {
		this.battleRes = battleRes;
	}
	
	
}
