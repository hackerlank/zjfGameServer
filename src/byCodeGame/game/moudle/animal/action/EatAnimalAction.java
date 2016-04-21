package byCodeGame.game.moudle.animal.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.animal.service.AnimalService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class EatAnimalAction implements ActionSupport {

	private AnimalService animalService;
	public void setAnimalService(AnimalService animalService) {
		this.animalService = animalService;
	}
	
	@Override
	public void execute(Message message, IoSession session) {
		Role role = RoleCache.getRoleBySession(session);
		int id = message.getInt();
		
		Message msg = animalService.eatAnimal(role, id);
		if(msg == null){
			return;
		}else{
			session.write(msg);
		}
	}
}
