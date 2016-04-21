package byCodeGame.game.skill.buff;

import byCodeGame.game.entity.po.ArenaFightData;
import byCodeGame.game.entity.po.ArenaObjFightData;
import byCodeGame.game.moudle.arena.msg.ArenaFightMsg;
import byCodeGame.game.moudle.arena.msg.BuffArenaFightMsg;

/**
 * 中毒
 * @author Lenovo
 *
 */
public class PosionBuff extends BaseBuff {
	/** 上一次的影响时间	 */
	private long lastAffectTime;

	public PosionBuff(int buffId,ArenaFightData arenaFightData,ArenaObjFightData arenaObjFightData,
			ArenaObjFightData targetData){
		super(buffId,arenaFightData,arenaObjFightData, targetData);
		intValue = (int)(arenaObjFightData.getMatk() * buffConfig.getValue2());
		canUse = true;
		
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
		if((arenaFightData.getNowTime() - startTime) > useTime){
			canUse = false;
		}else{
			int a = (int)(arenaFightData.getNowTime() - lastAffectTime);
			int b = (int)buffConfig.getValue1();
			if(a >= b){
				targetObjData.setHp(targetObjData.getHp() - intValue);
				lastAffectTime = arenaFightData.getNowTime();
				BuffArenaFightMsg msg = new BuffArenaFightMsg();
				msg.setType((byte)6);
				msg.setUuid(targetObjData.getUUID());
				msg.setBuffId(buffId);
				msg.setBuffType(2);
				StringBuilder sb = new StringBuilder();
				sb.append(intValue);
				msg.setStr(sb.toString());
				arenaFightData.addMessage(msg);
			}
		}
	}

	@Override
	public void quit() {
		super.quit();
		
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
		// TODO 自动生成的方法存根
		return super.getArenaObjFightData();
	}

	@Override
	public void setArenaObjFightData(ArenaObjFightData arenaObjFightData) {
		// TODO 自动生成的方法存根
		super.setArenaObjFightData(arenaObjFightData);
	}

	@Override
	public int getUseTime() {
		// TODO 自动生成的方法存根
		return super.getUseTime();
	}

	@Override
	public void setUseTime(int useTime) {
		// TODO 自动生成的方法存根
		super.setUseTime(useTime);
	}

	@Override
	public long getStartTime() {
		// TODO 自动生成的方法存根
		return super.getStartTime();
	}

	@Override
	public void setStartTime(long startTime) {
		// TODO 自动生成的方法存根
		super.setStartTime(startTime);
	}

	@Override
	public int getValue() {
		// TODO 自动生成的方法存根
		return super.getValue();
	}

	@Override
	public void setValue(int intValue) {
		// TODO 自动生成的方法存根
		super.setValue(intValue);
	}

	@Override
	public boolean isCanUse() {
		// TODO 自动生成的方法存根
		return super.isCanUse();
	}

	@Override
	public void setCanUse(boolean canUse) {
		// TODO 自动生成的方法存根
		super.setCanUse(canUse);
	}

	@Override
	public ArenaObjFightData getTargetObjData() {
		// TODO 自动生成的方法存根
		return super.getTargetObjData();
	}

	@Override
	public void setTargetObjData(ArenaObjFightData targetObjData) {
		// TODO 自动生成的方法存根
		super.setTargetObjData(targetObjData);
	}

	
}
