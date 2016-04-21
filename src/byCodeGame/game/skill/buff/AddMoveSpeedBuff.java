package byCodeGame.game.skill.buff;

import byCodeGame.game.entity.po.ArenaFightData;
import byCodeGame.game.entity.po.ArenaObjFightData;

public class AddMoveSpeedBuff extends BaseBuff {

	public AddMoveSpeedBuff(int buffId,ArenaFightData arenaFightData,ArenaObjFightData arenaObjFightData,
			ArenaObjFightData targetData){
		super(buffId,arenaFightData,arenaObjFightData,targetData);
	}
	
	@Override
	public void update() {
		//增加50%的移动速度
		if(canUse){
			doubleValue = (double)(arenaObjFightData.getMoveSpeed() / 2);
			arenaObjFightData.setMoveSpeed(arenaObjFightData.getMoveSpeed() + doubleValue);
			canUse = false;
		}
	}

	@Override
	public void quit() {
		arenaObjFightData.setMoveSpeed(arenaObjFightData.getMoveSpeed() - doubleValue);
	}

}
