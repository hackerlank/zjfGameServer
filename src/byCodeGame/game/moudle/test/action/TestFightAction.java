package byCodeGame.game.moudle.test.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.moudle.test.service.TestService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;
import byCodeGame.game.util.test.FightTestData;

public class TestFightAction implements ActionSupport{
	private TestService testService;
	public void setTestService(TestService testService) {
		this.testService = testService;
	}
	
	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		FightTestData attData = new FightTestData();
		attData.setFormationId(message.getInt());
		attData.setFormationLv(message.getInt());
		for(int i = 1 ; i<=5;i++)
		{
			String temp = message.getString(session);
			String[] strs = temp.split(",");
			attData.getHeroMap().put(Integer.parseInt(strs[0]), temp);
		}
		FightTestData defData = new FightTestData();
		defData.setFormationId(message.getInt());
		defData.setFormationLv(message.getInt());
		for(int i = 1 ; i<=5;i++)
		{
			String temp = message.getString(session);
			String[] strs = temp.split(",");
			defData.getHeroMap().put(Integer.parseInt(strs[0]), temp);
		}
		
		msg = testService.testFight(attData, defData);
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}
}
