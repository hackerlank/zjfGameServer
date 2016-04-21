package byCodeGame.game.entity.po;

import java.util.Map;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.cache.local.ServerCache;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.util.PVPUitls;
import cn.bycode.game.battle.data.TroopData;

/**
 * 世界部队
 * 
 * @author wcy
 *
 */
public class WorldArmy {
	private String id;
	private int roleId;
	/**领队武将*/
	private int heroCaptainId;
	private FormationData formationData;
	private int location;
	private TroopData troopData = null;
	private byte status;
	/**  最后一次死亡时间		*/
	private int lastDeadTime;
	private String name;
	private int clientId;
	private int winTime;

	/**
	 * @return the formationData
	 */
	public FormationData getFormationData() {
		return formationData;
	}

	/**
	 * @param formationData the formationData to set
	 */
	public void setFormationData(FormationData formationData) {
		this.formationData = formationData;
	}

	/**
	 * @return the roleId
	 */
	public int getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the location
	 */
	public int getLocation() {
		return location;
	}

	/**
	 * @param (必须确保已经设置roleId)
	 */
	public void setLocation(int location,Role role) {
		if(role == null) return;
		this.location = location;
		Map<Byte, Integer> data = formationData.getData();
		for(Integer heroId : data.values()){
			if(heroId>2){
				role.getHeroMap().get(heroId).setLocation(location);
			}
		}
	}

	/**
	 * @return the troopData
	 */
	public TroopData getTroopData() {
		if (troopData == null) {
			if(this.getRoleId() == -1)
			{
				this.troopData = PVPUitls.getTroopDataByWorldArmyNPC(this);
			}else {
//				int cityNum = ServerCache.getServer().getNationCityNumMap().get(RoleCache.getRoleById(this.roleId).getNation());
				int cityNum = 1;
				this.troopData = PVPUitls.getTroopDataByWorldArmy(this);
				this.troopData = PVPUitls.getTroopDataByTroopData(troopData, this.winTime, cityNum);
			}
			
		}else {
			//根据BUFF修改部队属性
			if(this.getRoleId() != -1)
			{
//				int cityNum = ServerCache.getServer().getNationCityNumMap().get(RoleCache.getRoleById(this.roleId).getNation());
				int cityNum = 1;
				this.troopData = 
					PVPUitls.getTroopDataByTroopData(troopData, this.winTime, cityNum);
			}
		}
		return troopData;
	}
	
	/**
	 * 移除战斗数据
	 * 
	 * @author wcy 2016年1月7日
	 */
	public void removeTroopData() {
		this.troopData = null;
		// 洞洞眼说这里写winTime = 0没有问题，我信了
		this.winTime = 0;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the status
	 */
	public byte getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(byte status) {
		this.status = status;
	}

	/**
	 * @return the heroId
	 */
	public int getHeroCaptainId() {
		return heroCaptainId;
	}

	/**
	 * @param heroCaptainId the heroId to set
	 */
	public void setHeroCaptainId(int heroCaptainId) {
		this.heroCaptainId = heroCaptainId;
	}

	public int getLastDeadTime() {
		return lastDeadTime;
	}

	public void setLastDeadTime(int lastDeadTime) {
		this.lastDeadTime = lastDeadTime;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the clientID
	 */
	public int getClientId() {
		return clientId;
	}

	/**
	 * @param clientID the clientID to set
	 */
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getWinTime() {
		return winTime;
	}

	public void setWinTime(int winTime) {
		this.winTime = winTime;
	}
	
}
