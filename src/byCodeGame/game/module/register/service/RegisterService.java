package byCodeGame.game.module.register.service;

import byCodeGame.game.module.Service;
import byCodeGame.game.remote.Message;

public interface RegisterService extends Service {

	Message register(String account,String name);

}
