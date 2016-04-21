package byCodeGame.game.moudle.test.service;

import byCodeGame.game.remote.Message;
import byCodeGame.game.util.test.FightTestData;

public interface TestService {
	
	public Message testFight(FightTestData attData , FightTestData defData);
}
