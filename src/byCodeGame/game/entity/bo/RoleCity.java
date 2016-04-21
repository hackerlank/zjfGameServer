package byCodeGame.game.entity.bo;

import java.util.ArrayList;
import java.util.List;

import byCodeGame.game.entity.po.VassalData;


/**
 * 2015/5/7
 * @author shao
 *	玩家封地数据详情
 */
public class RoleCity {
	/**
	 * 玩家Id
	 */
	private int roleId;
	/**
	 * 所在城市id
	 */
	private int cityId;
	/**
	 * 封地所在位置
	 */
	private int mapKey;
	/**
	 * 主公Id
	 */
	private int myLordRoleId;
	/**
	 * 臣子Id 集合  RoleId,RoleId,RoleId,存储在数据库钟中的信息
	 */
	private String vassal;
	/**
	 * 臣子Id 集合  RoleId!最后征收时间,征收银币,增收粮草,增收人口; RoleId!最后征收时间,征收银币,增收粮草,增收人口;
	 */
	private List<VassalData> vassalRoleId;

	private boolean change;
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
		this.change = true;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
		this.change = true;
	}
	public int getMapKey() {
		return mapKey;
	}
	public void setMapKey(int mapKey) {
		this.mapKey = mapKey;
		this.change = true;
	}

	public boolean isChange() {
		return change;
	}
	public void setChange(boolean change) {
		this.change = change;
	}
	public int getMyLordRoleId() {
		return myLordRoleId;
	}
	public void setMyLordRoleId(int myLordRoleId) {
		this.myLordRoleId = myLordRoleId;
	}
	public String getVassal() {
		List<VassalData> list=this.vassalRoleId;
		StringBuffer sb=new StringBuffer();
		if(list!=null){
			for (VassalData vassalData : list) {
				sb.append(vassalData.getRoleId()).append("!").
				append(vassalData.getLastTime()).
				append(",").append(vassalData.getMoney()).
				append(",").append(vassalData.getFood()).
				append(",").append(vassalData.getPopulation())
				.append(";");
			}		
			vassal=	sb.toString();
		}else{
			vassal="";
		}
		return vassal;
	}
	public void setVassal(String vassal) {
		if(null!=vassal&&!vassal.trim().equals("")){
			String [] str=vassal.split(";");
			this.vassalRoleId=new ArrayList<VassalData>();
			for (String str1 : str) {
				VassalData v=new VassalData();
				String [] vassalData=str1.split("!");
				v.setRoleId(Integer.valueOf(vassalData[0]));
				String [] vassalInfo=vassalData[1].split(",");
				v.setLastTime(Integer.valueOf(vassalInfo[0]));
				v.setMoney(Integer.valueOf(vassalInfo[1]));
				v.setFood(Integer.valueOf(vassalInfo[2]));
				v.setPopulation(Integer.valueOf(vassalInfo[3]));
				this.vassalRoleId.add(v);
			}
		}
	}
	public List<VassalData> getVassalRoleId() {
		if(vassalRoleId==null){
			vassalRoleId=new ArrayList<VassalData>();
		}
		return vassalRoleId;
	}
	public VassalData getVassalByRoleId(int roleId) {
		if(vassalRoleId==null){
			vassalRoleId=new ArrayList<VassalData>();
		}
		for (VassalData vassalData : vassalRoleId) {
			if(vassalData.getRoleId()==roleId){
				return vassalData;
			}
		}
		return null;
	}
	public void setVassalRoleId(List<VassalData> vassalRoleId) {
		this.vassalRoleId = vassalRoleId;
	}
	
	/***
	 * 增加贡献值
	 * @param roleId
	 * @param value
	 * @param type
	 */
	public synchronized void addValue(int roleId,int value ,byte type)
	{
		for(VassalData x : this.vassalRoleId)
		{
			if(x.getRoleId() == roleId)
			{
				switch (type) {
				case (byte)2:
					x.setFood(x.getFood() + value);
					break;
				case (byte)3:
					x.setMoney(x.getMoney() + value);
					break;
				default:
					break;
				}
			}
		}
	}
}
