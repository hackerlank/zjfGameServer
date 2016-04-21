package byCodeGame.game.moudle.animal.service;

import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;

public interface AnimalService {

	/**
	 * 获取神兽信息
	 * @param role
	 * @return
	 */
	public Message getAnimalInfo(Role role);
	
	/**
	 * 喂养神兽
	 * @param role
	 * @param id
	 * @return
	 */
	public Message eatAnimal(Role role,int id);
}
