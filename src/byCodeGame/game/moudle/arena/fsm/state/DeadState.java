package byCodeGame.game.moudle.arena.fsm.state;

import byCodeGame.game.moudle.arena.ArenaConstant;
import byCodeGame.game.moudle.arena.fsm.control.Control;
import byCodeGame.game.moudle.arena.msg.DeadArenaFightMsg;

/**
 * 死亡状态
 * @author Lenovo
 *
 */
public class DeadState extends State {

	public DeadState(byte type,Control control){
		super(type, control);
	}
	
	@Override
	public void enter() {
		super.enter();
		control.getArenaObjFightData().setState(ArenaConstant.State.DEAD.getVal());
		DeadArenaFightMsg msg = new DeadArenaFightMsg();
		msg.setType(ArenaConstant.State.DEAD.getVal());
		msg.setUuid(control.getArenaObjFightData().getUUID());
		control.getArenaFightData().addMessage(msg);
	}

	@Override
	public void exit() {
		// TODO 自动生成的方法存根
		super.exit();
	}

	@Override
	public void update() {
		// TODO 自动生成的方法存根
		super.update();
	}

	@Override
	public void init() {
		// TODO 自动生成的方法存根
		super.init();
	}

	@Override
	public byte checkTransitions() {
		// TODO 自动生成的方法存根
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
