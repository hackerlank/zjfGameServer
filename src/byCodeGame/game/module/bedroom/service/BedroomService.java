package byCodeGame.game.module.bedroom.service;

import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;

public interface BedroomService {
	Message sleep(Role role,int heroId,int position);
	
	Message addPropToPosition(int propServerId,byte position);
}
