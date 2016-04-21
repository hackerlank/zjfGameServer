package byCodeGame.game.moudle.animal.service;

import java.util.Map;

import byCodeGame.game.cache.file.AnimalConfigCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.bo.RoleArena;
import byCodeGame.game.entity.file.AnimalConfig;
import byCodeGame.game.moudle.animal.AnimalConstant;
import byCodeGame.game.moudle.role.service.RoleService;
import byCodeGame.game.remote.Message;

public class AnimalServiceImpl implements AnimalService {

	private RoleService roleService;
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	public Message getAnimalInfo(Role role){
		Message message = new Message();
		message.setType(AnimalConstant.Action.INFO.getVal());

		message.put((byte)role.getRoleArena().getAnimalMap().size());
		for(Map.Entry<Integer, Byte> entry : role.getRoleArena().getAnimalMap().entrySet()){
			message.putInt(entry.getKey());
			message.put(entry.getValue());
		}
		return message;
	}

	public Message eatAnimal(Role role,int id){
		Message message = new Message();
		message.setType(AnimalConstant.Action.EAT.getVal());
		RoleArena roleArena = role.getRoleArena();
		if(roleArena.getAnimalMap().containsKey(id)){	//没有这个神兽
			message.putShort(ErrorCode.NO_ANIMAL);
			return message;
		}
		
		byte times = roleArena.getAnimalMap().get(id);
		if(times >= 10){	//达到最大喂养次数
			message.putShort(ErrorCode.MAX_EAT_TIMES);
			return message;
		}
		//每日只能选一个神兽喂养
		if(role.getRoleArena().getTodayAnimal() > 0 && role.getRoleArena().getTodayAnimal() != id){	
			return null;
		}
		times += 1;
		AnimalConfig animalConfig = AnimalConfigCache.getAnimalConfig(times);
		if(animalConfig == null){
			return null;
		}
		if(role.getMoney() < animalConfig.getNeedMoney()){	//需求金钱不足
			message.putShort(ErrorCode.NO_MONEY);
			return message;
		}
		
		//喂养成功 扣除银币 增加次数
		roleService.addRoleMoney(role, -animalConfig.getNeedMoney());
		role.getRoleArena().getAnimalMap().put(id, times);
		role.getRoleArena().setTodayAnimal(id);
		
		message.putShort(ErrorCode.SUCCESS);
		message.putInt(id);
		message.putInt(animalConfig.getNeedMoney());
		
		return message;
	}


}
