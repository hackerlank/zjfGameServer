package byCodeGame.game.moudle.arena.fsm.state;

import byCodeGame.game.entity.po.ArenaObjFightData;
import byCodeGame.game.moudle.arena.ArenaConstant;
import byCodeGame.game.moudle.arena.arenaUtil.ArenaFightUtil;
import byCodeGame.game.moudle.arena.fsm.control.Control;
import byCodeGame.game.moudle.arena.msg.AtkArenaFightMsg;

/**
 * 攻击状态
 * @author Lenovo
 *
 */
public class AtkState extends State {

	
	
	public AtkState(byte type,Control control){
		super(type, control);
	}
	
	@Override
	public void enter() {
		super.enter();
		control.getArenaObjFightData().setState(ArenaConstant.State.ATK.getVal());
	}

	@Override
	public void exit() {
		// TODO 自动生成的方法存根
		super.exit();
	}

	@Override
	public void update() {
		if(control.getArenaObjFightData().getTargetObj() != null){
			
			long nowTime = control.getArenaFightData().getNowTime();
			long lastAtkTime = control.getArenaObjFightData().getLastAtkTime();
			if((nowTime - lastAtkTime) < (long)control.getArenaObjFightData().getAtkDelay()){
				return;
			}else{
				ArenaObjFightData obj = control.getArenaObjFightData();
				ArenaObjFightData target = obj.getTargetObj();
				if(obj.getMorale() >= ArenaConstant.FULL_MORALE){	//释放技能
					
					
					obj.getSkill().use();
				}else{		//普通攻击
					if(!obj.isXuanYun()){
						int hurt = ArenaFightUtil.countHurt(obj, target);
						target.setHp(target.getHp() - hurt);
						target.setMorale(target.getMorale() + ArenaConstant.MORALE_HURT);
						obj.setLastAtkTime(control.getArenaFightData().getNowTime());
						obj.setMorale(obj.getMorale() + ArenaConstant.MORALE_ATK);
						
						AtkArenaFightMsg msg = new AtkArenaFightMsg();
						msg.setType(ArenaConstant.State.ATK.getVal());
						msg.setTargetUuid(target.getUUID());
						msg.setUuid(obj.getUUID());
						msg.setHurtVal(hurt);
						control.getArenaFightData().addMessage(msg);
					}
				}
			}
		}
	}

	@Override
	public void init() {
		// TODO 自动生成的方法存根
		super.init();
	}

	@Override
	public byte checkTransitions() {
		ArenaObjFightData objData = control.getArenaObjFightData();
		ArenaObjFightData target = objData.getTargetObj();
		long lastAtkTime = objData.getLastAtkTime();
		long nowTime = control.getArenaFightData().getNowTime();
		if(objData.getHp() <= 0){	//进入死亡状态
			return ArenaConstant.State.DEAD.getVal();
		}
		if((nowTime - lastAtkTime) < (long)objData.getAtkDelay() && objData.getTargetObj() != null){
			return ArenaConstant.State.ATK.getVal();
		}
		if(target.getHp() <= 0){		//目标死亡 待机
			objData.setTargetObj(null);
			return ArenaConstant.State.WAIT.getVal();
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
