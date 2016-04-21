package byCodeGame.game.moudle.nation.service;

import java.util.Map;

import byCodeGame.game.cache.file.CityInfoConfigCache;
import byCodeGame.game.cache.local.CityCache;
import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.bo.RoleCity;
import byCodeGame.game.moudle.nation.NationConstant;
import byCodeGame.game.moudle.task.service.TaskService;
import byCodeGame.game.remote.Message;
import byCodeGame.game.util.Utils;

public class NationServiceImpl implements NationService {
	private TaskService taskService;
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
	
	/**
	 * 设置国籍
	 */
	public Message setNation(Role role, byte nation) {
		Message message = new Message();
		message.setType(NationConstant.SET_NATION);
		if (role.getNation() > 0) {
			message.putShort(ErrorCode.NATION_AREADY_HAS);
			return message;
		}
		if(nation==NationConstant.LUO_TYPE){
			nation=getMinNationNum();
		}
		role.setNation(nation);
		role.setStrongHold(CityInfoConfigCache.getBirthCityByNation(role.getNation()).getCityId());
		role.setChange(true);
		this.taskService.checkNationTask(role, (byte)1);
		
		if(role.getNation() == NationConstant.LUO_TYPE){
			RoleCache.getLuoMap().put(role.getId(), role);
		}else if(role.getNation() == NationConstant.SHU_TYPE)
		{
			RoleCache.getShuMap().put(role.getId(), role);
		}else if (role.getNation() == NationConstant.WEI_TYPE) {
			RoleCache.getWeiMap().put(role.getId(), role);
		}else if (role.getId() == NationConstant.WU_TYPE ) {
			RoleCache.getWuMap().put(role.getId(), role);
		}
		message.putShort(ErrorCode.SUCCESS);
//		message.putInt(role.getId());
//		message.put(nation);
//		int cityId=0;
//		switch (nation) {
//		case NationConstant.SHU_TYPE:
//			cityId=5;
//			break;
//		case NationConstant.WEI_TYPE:
//			cityId=1;
//			break;
//		case NationConstant.WU_TYPE:
//			cityId=9;
//			break;
//		default:
//			break;
//		}
//		Map<Integer, Integer> rmCityKeymap=CityCache.getCityRoleKeyByCityIdMap(role.getRoleCity().getCityId());
//		Integer mapKey=role.getRoleCity().getMapKey();
//		rmCityKeymap.remove(mapKey);
//		Map<Integer, Integer> cityKeymap=CityCache.getCityRoleKeyByCityIdMap(cityId);
//		
//		//加入缓存
//		CityCache.getCityRoleByCityId(cityId).add(role.getId());
//		//移除缓存
//		Integer x = role.getId();
//		CityCache.getCityRoleByCityId(role.getRoleCity().getCityId()).remove(x);
//		
//		int maxI=Utils.getMaxKeyByMap(cityKeymap);
//		for (int i = 1; i <=(maxI+1) ; i++) {
//			if (!cityKeymap.containsKey(i)) {
//				cityKeymap.put(i, role.getId());
//				RoleCity roleCity=new RoleCity();
//				roleCity.setCityId(cityId);
//				roleCity.setMapKey(i);
//				roleCity.setRoleId(role.getId());
//				role.setRoleCity(roleCity);
//				break;
//			}
//		}
//		
//		message.putInt(role.getRoleCity().getCityId());
//		message.putInt(role.getRoleCity().getMapKey());
		return message;
	}
	
	/**
	 * 获取服务器当前阵营人数
	 * @author xjd
	 */
	public Message getNumnation() {
		Message message = new Message();
		message.setType(NationConstant.GET_NATION_NUM);
 		message.putInt(RoleCache.getShuMap().size());
		message.putInt(RoleCache.getWeiMap().size());
		message.putInt(RoleCache.getWuMap().size());
		return message;
	}
	
	/**
	 * 获取玩家数最小的阵营(国籍 )
	 * @return
	 */
	private byte getMinNationNum(){
		int shu=RoleCache.getShuMap().size();
		int wei=RoleCache.getWeiMap().size();
		int wu=RoleCache.getWuMap().size();
		int [] arr={shu,wei,wu};
		int min=arr[0];
		for (int i : arr) {
			min=min>i?i:min;
		}
		byte nation=1;
		if(min==shu){
			nation=1;
		}else if(min==wei){
			nation=2;
		}else{
			nation=3;
		}
		return nation;
	}
}
