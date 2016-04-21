package byCodeGame.game.moudle.arena.fsm.state;

import byCodeGame.game.moudle.arena.fsm.control.Control;

public class State {

	protected byte type;
	protected Control control;

	public State(byte type,Control control){
		this.type = type;
		this.control = control;
	}

	/**
	 * 切换状态时执行 只会执行一次
	 */
	public void enter(){}

	/**
	 * 离开状态时执行 只会执行一次
	 */
	public void exit(){}
	
	/**
	 * 每帧执行
	 */
	public void update(){}
	
	/**
	 * 实例化的时候调用一次
	 */
	public void init(){}
	
	/**
	 * 查询当前条件 并返回将要进入的状态ID
	 * @return
	 */
	public byte checkTransitions(){
		return type;
	}

	public byte getType() {
		return type;
	}

	public Control getControl() {
		return control;
	}
	
}
