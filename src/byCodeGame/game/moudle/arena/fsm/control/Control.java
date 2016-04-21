package byCodeGame.game.moudle.arena.fsm.control;

import java.util.Iterator;
import java.util.Set;

import byCodeGame.game.entity.po.ArenaFightData;
import byCodeGame.game.entity.po.ArenaObjFightData;
import byCodeGame.game.entity.po.Vector2D;
import byCodeGame.game.moudle.arena.ArenaConstant;
import byCodeGame.game.moudle.arena.fsm.Machine;
import byCodeGame.game.moudle.arena.fsm.state.AtkState;
import byCodeGame.game.moudle.arena.fsm.state.DeadState;
import byCodeGame.game.moudle.arena.fsm.state.MoveState;
import byCodeGame.game.moudle.arena.fsm.state.OutBattleState;
import byCodeGame.game.moudle.arena.fsm.state.State;
import byCodeGame.game.moudle.arena.fsm.state.WaitState;
import byCodeGame.game.skill.buff.BaseBuff;

public class Control {

	private Machine machine;
	/** 所属战场的引用	 */
	private ArenaFightData arenaFightData;
	/** 这个控制器的角色数据	 */
	private ArenaObjFightData arenaObjFightData;
	/** 对应战场角色的唯一识别ID	 */
	private String UUID;
	/** 当前位置	 */
	private Vector2D nowPos;
	/** 每一帧移动的向量	 */
	private Vector2D velocity;
	
	public Control(ArenaFightData arenaFightData,ArenaObjFightData arenaObjFightData){
		this.arenaFightData = arenaFightData;
		this.arenaObjFightData = arenaObjFightData;
		this.nowPos = new Vector2D(arenaObjFightData.getPosX(), arenaObjFightData.getPosY());
		
		machine = new Machine();
		State outBattleState = new OutBattleState(ArenaConstant.State.OUT_BATTLE.getVal(), this);
		State waitState = new WaitState(ArenaConstant.State.WAIT.getVal(), this);
		State moveState = new MoveState(ArenaConstant.State.MOVE.getVal(), this);
		State atkState = new AtkState(ArenaConstant.State.ATK.getVal(), this);
		State deadState = new DeadState(ArenaConstant.State.DEAD.getVal(), this);
		
		machine.addState(waitState);
		machine.addState(moveState);
		machine.addState(atkState);
		machine.addState(deadState);
		machine.addState(outBattleState);
		
		machine.setDefaultState(outBattleState);
	}
	
	/**
	 * 每一帧都调用
	 */
	public void update(){
		this.updatePerceptions();
		machine.updateMacine();
	}
	
	public void init(){
		
	}
	
	/**
	 * 感知器
	 */
	public void updatePerceptions(){
		Set<BaseBuff> buffSet = arenaObjFightData.getBuffSet();
		Iterator<BaseBuff> it = buffSet.iterator();
		while(it.hasNext()){
			BaseBuff buff = it.next();
			if(buff.isCanUse()){
				buff.update();
			}else{
				buff.quit();
				it.remove();
			}
		}
	}

	public ArenaFightData getArenaFightData() {
		return arenaFightData;
	}

	public String getUUID() {
		return UUID;
	}

	public Vector2D getNowPos() {
		return nowPos;
	}

	public void setNowPos(Vector2D nowPos) {
		this.nowPos = nowPos;
	}

	public Vector2D getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2D velocity) {
		this.velocity = velocity;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public ArenaObjFightData getArenaObjFightData() {
		return arenaObjFightData;
	}

	public void setArenaObjFightData(ArenaObjFightData arenaObjFightData) {
		this.arenaObjFightData = arenaObjFightData;
	}
}
