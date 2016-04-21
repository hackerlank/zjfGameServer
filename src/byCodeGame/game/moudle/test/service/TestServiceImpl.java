package byCodeGame.game.moudle.test.service;

import cn.bycode.game.battle.core.Battle;
import cn.bycode.game.battle.data.ResultData;
import cn.bycode.game.battle.data.TroopData;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.moudle.test.TestConstant;
import byCodeGame.game.remote.Message;
import byCodeGame.game.util.test.FightTestData;
import byCodeGame.game.util.test.TestUtils;

public class TestServiceImpl implements TestService{

	public Message testFight(FightTestData attData , FightTestData defData) {
		Message message = new Message();
		message.setType(TestConstant.TEST_FIGHT);
		
		TroopData att = TestUtils.changeStrToTroop(attData);
		TroopData def = TestUtils.changeStrToTroop(defData);
		
		ResultData resultData = new Battle().fightPVP(att, def);
		resultData.attName = "wolegeca";
		resultData.defName = "xiaoxianrou";
		resultData.attPlayerId = 9999;
		resultData.defPlayerId = 9999;
		message.putShort(ErrorCode.SUCCESS);
		message.putString(resultData.uuid);
		message.putString(resultData.time);
		message.putInt(resultData.winCamp);
		message.putInt(resultData.attPlayerId);
		message.putInt(resultData.defPlayerId);
		message.putString(resultData.attName);
		message.putString(resultData.defName);
		message.putString(resultData.report);
		message.putInt(resultData.attStars);
		message.putInt(resultData.attLost);
		message.putInt(resultData.defStars);
		message.putInt(resultData.defLost);
		message.putInt(resultData.fRound);
//		System.out.println(resultData.report);
		return message;
	}
}
