package byCodeGame.game.entity.po;

import cn.bycode.game.battle.data.TroopData;
import byCodeGame.game.entity.bo.Role;

public class RoomRole {
	/** 玩家信息						*/
	private Role role;
	/** 玩家状态						*/
	private byte roleStatus;
	/** 玩家战斗时所用数据				*/
	private TroopData troopData;
	/** 玩家本次战斗胜利次数				*/
	private int winTime;
	/** 玩家总计战斗回合（判定顺序时使用）	*/
	private int battleTime;
	/** 本次战役总计战损				*/
	private int lostAll;
	/** 是否可以继续战斗				*/
	private int isDead;
	
	
	
	
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public byte getRoleStatus() {
		return roleStatus;
	}
	public void setRoleStatus(byte roleStatus) {
		this.roleStatus = roleStatus;
	}
	public TroopData getTroopData() {
		return troopData;
	}
	public void setTroopData(TroopData troopData) {
		this.troopData = troopData;
	}
	public int getWinTime() {
		return winTime;
	}
	public void setWinTime(int winTime) {
		this.winTime = winTime;
	}
	public int getBattleTime() {
		return battleTime;
	}
	public void setBattleTime(int battleTime) {
		this.battleTime = battleTime;
	}
	public int getLostAll() {
		return lostAll;
	}
	public void setLostAll(int lostAll) {
		this.lostAll = lostAll;
	}
	public int getIsDead() {
		return isDead;
	}
	public void setIsDead(int isDead) {
		this.isDead = isDead;
	}
}
