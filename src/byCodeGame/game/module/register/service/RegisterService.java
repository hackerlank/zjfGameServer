package byCodeGame.game.module.register.service;

import byCodeGame.game.remote.Message;

public interface RegisterService {

	Message register(String account, String name);

	void init();
}
