package byCodeGame.game.skill.buff;

import byCodeGame.game.entity.po.ArenaFightData;
import byCodeGame.game.entity.po.ArenaObjFightData;
import byCodeGame.game.moudle.arena.msg.BuffArenaFightMsg;

/**
 * 眩晕
 * @author Lenovo
 *
 */
public class XuanYunBuff extends BaseBuff {
	public XuanYunBuff(int buffId,ArenaFightData arenaFightData,
			ArenaObjFightData arenaObjFightData,ArenaObjFightData targetData){
		super(buffId, arenaFightData, arenaObjFightData, targetData);
		canUse = true;
		targetObjData.setXuanYun(true);
		
		BuffArenaFightMsg msg = new BuffArenaFightMsg();
		msg.setType((byte)6);
		msg.setUuid(targetObjData.getUUID());
		msg.setBuffId(buffId);
		msg.setBuffType(1);
		msg.setStr(null);
		arenaFightData.addMessage(msg);
	}
	
	@Override
	public void update() {
		super.update();
		if((arenaFightData.getNowTime() - startTime) >= useTime){
			canUse = false;
		}else{
			BuffArenaFightMsg msg = new BuffArenaFightMsg();
			msg.setType((byte)6);
			msg.setUuid(targetObjData.getUUID());
			msg.setBuffId(buffId);
			msg.setBuffType(2);
			msg.setStr(null);
			arenaFightData.addMessage(msg);
		}
	}

	@Override
	public void quit() {
		super.quit();
		targetObjData.setXuanYun(false);
		
		BuffArenaFightMsg msg = new BuffArenaFightMsg();
		msg.setType((byte)6);
		msg.setUuid(targetObjData.getUUID());
		msg.setBuffId(buffId);
		msg.setBuffType(-1);
		msg.setStr(null);
		arenaFightData.addMessage(msg);
	}

	@Override
	public ArenaObjFightData getArenaObjFightData() {
		return super.getArenaObjFightData();
	}

	@Override
	public void setArenaObjFightData(ArenaObjFightData arenaObjFightData) {
		super.setArenaObjFightData(arenaObjFightData);
	}

	@Override
	public int getUseTime() {
		return super.getUseTime();
	}

	@Override
	public void setUseTime(int useTime) {
		super.setUseTime(useTime);
	}

	@Override
	public long getStartTime() {
		return super.getStartTime();
	}

	@Override
	public void setStartTime(long startTime) {
		super.setStartTime(startTime);
	}

	@Override
	public int getValue() {
		return super.getValue();
	}

	@Override
	public void setValue(int intValue) {
		super.setValue(intValue);
	}

	@Override
	public boolean isCanUse() {
		return super.isCanUse();
	}

	@Override
	public void setCanUse(boolean canUse) {
		super.setCanUse(canUse);
	}

	@Override
	public ArenaObjFightData getTargetObjData() {
		return super.getTargetObjData();
	}

	@Override
	public void setTargetObjData(ArenaObjFightData targetObjData) {
		super.setTargetObjData(targetObjData);
	}

	@Override
	public ArenaFightData getArenaFightData() {
		return super.getArenaFightData();
	}

	@Override
	public void setArenaFightData(ArenaFightData arenaFightData) {
		super.setArenaFightData(arenaFightData);
	}

	@Override
	public int getBuffId() {
		return super.getBuffId();
	}

	@Override
	public void setBuffId(int buffId) {
		super.setBuffId(buffId);
	}

}
