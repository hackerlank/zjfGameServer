package byCodeGame.game.moudle.arena.fsm.state;

import byCodeGame.game.entity.po.ArenaObjFightData;
import byCodeGame.game.entity.po.Vector2D;
import byCodeGame.game.moudle.arena.ArenaConstant;
import byCodeGame.game.moudle.arena.arenaUtil.ArenaFightUtil;
import byCodeGame.game.moudle.arena.fsm.control.Control;
import byCodeGame.game.moudle.arena.msg.MoveArenaFightMsg;

/**
 * 移动状态
 * @author Lenovo
 *
 */
public class MoveState extends State {

	
	public MoveState(byte type,Control control){
		super(type,control);
	}
	
	@Override
	public void enter() {
		super.enter();
	}

	@Override
	public void exit() {
		super.exit();
	}

	@Override
	public void update() {
		ArenaObjFightData objData = control.getArenaObjFightData();
		if(!objData.isXuanYun()){
			//每帧x+speed
			double speed = 0;
			speed = ArenaFightUtil.countMoveSpeed(control.getArenaObjFightData());
			double endX;
			if(control.getArenaObjFightData().getCamp() == 1){
				endX = 1280;
			}else{
				endX = 0;
			}
			Vector2D endPos = new Vector2D(endX,510);
			Vector2D xx = endPos.subtract(control.getNowPos());
			control.setVelocity(xx);
			
			if(control.getVelocity().getLength() > speed){
				control.getVelocity().normalize();
				control.setVelocity(control.getVelocity().multiply(speed));
				control.setNowPos(control.getNowPos().add(control.getVelocity()));
			}else{
				control.setNowPos(endPos);
			}
			
			
			objData.setPosX(control.getNowPos().getX());
			objData.setPosY(control.getNowPos().getY());
			
			MoveArenaFightMsg moveMsg = new MoveArenaFightMsg();
			moveMsg.setType((byte)1);
			moveMsg.setNowFrame(control.getArenaFightData().getFrames());
			moveMsg.setUuid(objData.getUUID());
			moveMsg.setX(objData.getPosX());
			moveMsg.setY(objData.getPosY());
			control.getArenaFightData().addMessage(moveMsg);
		}
		
		
	}

	@Override
	public void init() {
		super.init();
	}

	@Override
	public byte checkTransitions() {
		ArenaObjFightData objData = control.getArenaObjFightData();
		ArenaObjFightData targetData = 
				ArenaFightUtil.checkTarget(control.getArenaFightData(), 
						control.getArenaObjFightData());
		
		if(targetData != null){		//找到目标 进入攻击状态
			control.getArenaObjFightData().setTargetObj(targetData);
			return ArenaConstant.State.ATK.getVal();
		}
		
		if(objData.getHp() <= 0){	//进入死亡状态
			return ArenaConstant.State.DEAD.getVal();
		}
		
		if(objData.getCamp() == 1){
			if(objData.getPosX() >= (double)1280){
				return ArenaConstant.State.WAIT.getVal();
			}
		}else{
			if(objData.getPosX() <= (double)0){
				return ArenaConstant.State.WAIT.getVal();
			}
		}
		return super.checkTransitions();
	}

	@Override
	public byte getType() {
		return super.getType();
	}

	@Override
	public Control getControl() {
		return super.getControl();
	}

}
