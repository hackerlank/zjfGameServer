package byCodeGame.game.moudle.arena.fsm.state;

import byCodeGame.game.entity.po.ArenaFightData;
import byCodeGame.game.entity.po.ArenaObjFightData;
import byCodeGame.game.moudle.arena.ArenaConstant;
import byCodeGame.game.moudle.arena.fsm.control.Control;
import byCodeGame.game.moudle.arena.msg.OutBattleArenaFightMsg;

/**
 * 未上场状态
 * @author Lenovo
 *
 */
public class OutBattleState extends State {

	
	public OutBattleState(byte type,Control control){
		super(type, control);
	}
	
	@Override
	public void enter() {
		// TODO 自动生成的方法存根
		super.enter();
		
//		control.getArenaObjFightData().setState(ArenaConstant.State.OUT_BATTLE.getVal());
//		ArenaFightData arenaFightData = control.getArenaFightData();
//		ArenaObjFightData arenaObjFightData = control.getArenaObjFightData();
//		
//		OutBattleArenaFightMsg outBattleArenaFightMsg = new OutBattleArenaFightMsg();
//		outBattleArenaFightMsg.setType(ArenaConstant.State.OUT_BATTLE.getVal());
//		outBattleArenaFightMsg.setNowFrame(arenaFightData.getFrames());
//		outBattleArenaFightMsg.setUuid(arenaObjFightData.getUUID());
//		outBattleArenaFightMsg.setX(arenaObjFightData.getPosX());
//		outBattleArenaFightMsg.setHeroId(arenaObjFightData.getHeroId());
//		
//		control.getArenaFightData().addMessage(outBattleArenaFightMsg);
	}

	@Override
	public void exit() {
		// TODO 自动生成的方法存根
		super.exit();
	}

	@Override
	public void update() {
		super.update();
		ArenaFightData arenaFightData = control.getArenaFightData();
		ArenaObjFightData arenaObjFightData = control.getArenaObjFightData();
		
		if(arenaObjFightData.getSkill().getUseType() == 1){	//入场即使用技能
			arenaObjFightData.getSkill().use();
		}
		
		OutBattleArenaFightMsg outBattleArenaFightMsg = new OutBattleArenaFightMsg();
		outBattleArenaFightMsg.setType(ArenaConstant.State.OUT_BATTLE.getVal());
		outBattleArenaFightMsg.setNowFrame(arenaFightData.getFrames());
		outBattleArenaFightMsg.setUuid(arenaObjFightData.getUUID());
		outBattleArenaFightMsg.setX(arenaObjFightData.getPosX());
		outBattleArenaFightMsg.setY(arenaObjFightData.getPosY());
		outBattleArenaFightMsg.setHp(arenaObjFightData.getMaxHp());
		outBattleArenaFightMsg.setMaxHp(arenaObjFightData.getMaxHp());
		outBattleArenaFightMsg.setHeroId(arenaObjFightData.getHeroId());
		control.getArenaFightData().addMessage(outBattleArenaFightMsg);
		control.getArenaObjFightData().setState(ArenaConstant.State.MOVE.getVal());
	}

	@Override
	public void init() {
		// TODO 自动生成的方法存根
		super.init();
	}

	@Override
	public byte checkTransitions() {
		ArenaObjFightData objData = control.getArenaObjFightData();
		if(objData.getState() == ArenaConstant.State.OUT_BATTLE.getVal()){
			return ArenaConstant.State.OUT_BATTLE.getVal();
		}
		if(objData.getState() == ArenaConstant.State.MOVE.getVal()){
			return ArenaConstant.State.MOVE.getVal();
		}
		return super.checkTransitions();
	}

	@Override
	public byte getType() {
		// TODO 自动生成的方法存根
		return super.getType();
	}

	@Override
	public Control getControl() {
		// TODO 自动生成的方法存根
		return super.getControl();
	}

}
