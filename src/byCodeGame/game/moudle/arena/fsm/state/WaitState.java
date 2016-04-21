package byCodeGame.game.moudle.arena.fsm.state;

import byCodeGame.game.entity.po.ArenaObjFightData;
import byCodeGame.game.moudle.arena.ArenaConstant;
import byCodeGame.game.moudle.arena.arenaUtil.ArenaFightUtil;
import byCodeGame.game.moudle.arena.fsm.control.Control;
import byCodeGame.game.moudle.arena.msg.WaitArenaFightMsg;


/**
 * 待机状态
 * @author Lenovo
 *
 */
public class WaitState extends State {

	
	public WaitState(byte type,Control control){
		super(type, control);
	}
	
	@Override
	public void enter() {
		super.enter();
		WaitArenaFightMsg waitArenaFightMsg = new WaitArenaFightMsg();
		waitArenaFightMsg.setType(ArenaConstant.State.WAIT.getVal());
		waitArenaFightMsg.setNowFrame(control.getArenaFightData().getFrames());
		waitArenaFightMsg.setUuid(control.getArenaObjFightData().getUUID());
		control.getArenaFightData().addMessage(waitArenaFightMsg);
	}

	@Override
	public void exit() {
		// TODO 自动生成的方法存根
		super.exit();
	}

	@Override
	public void update() {
		super.update();
		ArenaObjFightData obj = control.getArenaObjFightData();
		if(!obj.isXuanYun()){
			WaitArenaFightMsg waitArenaFightMsg = new WaitArenaFightMsg();
			waitArenaFightMsg.setType(ArenaConstant.State.WAIT.getVal());
			waitArenaFightMsg.setNowFrame(control.getArenaFightData().getFrames());
			waitArenaFightMsg.setUuid(obj.getUUID());
			control.getArenaFightData().addMessage(waitArenaFightMsg);
		}
	}

	@Override
	public void init() {
		super.init();
		
	}

	@Override
	public byte checkTransitions() {
		ArenaObjFightData objData = control.getArenaObjFightData();
//		long nowTime = control.getArenaFightData().getNowTime();
//		long lastAtkTime = objData.getLastAtkTime();
//		if(objData.getTargetObj() != null){//有战斗目标的情况下判定攻击间隔
//			if((nowTime - lastAtkTime) >= (long)objData.getAtkDelay()){
//				return ArenaConstant.State.ATK.getVal();
//			}else{
//				return ArenaConstant.State.WAIT.getVal();
//			}
//		}
		
		ArenaObjFightData targetData = 
				ArenaFightUtil.checkTarget(control.getArenaFightData(), 
						control.getArenaObjFightData());
		
		if(targetData != null){		//找到目标 进入攻击状态
			objData.setTargetObj(targetData);
			return ArenaConstant.State.ATK.getVal();
		}else{
			return ArenaConstant.State.MOVE.getVal();
		}
//		return super.checkTransitions();
		
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
