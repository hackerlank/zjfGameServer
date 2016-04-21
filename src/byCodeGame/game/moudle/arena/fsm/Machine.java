package byCodeGame.game.moudle.arena.fsm;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.moudle.arena.fsm.state.State;

public class Machine {

	/** 当前状态	 */
	private State currentState;
	/** 初始状态	 */
	private State defaultState;
	/** 将要切换的状态	 */
	private State goalState;
	/** 将要切换的状态ID	 */
	private int goalStateId;
	/** 状态集合	 */
	private Map<Byte, State> stateMap;
	
	public Machine(){
		stateMap = new HashMap<Byte, State>();
	}
	
	/**
	 * 每一帧都调用 
	 */
	public void updateMacine(){
		if(stateMap.size() == 0){
			return ;
		}
		if(currentState == null){
			currentState = defaultState;
		}
		if(currentState == null){
			return;
		}
		
		//切换状态
		int oldStateId = currentState.getType();
		goalStateId = currentState.checkTransitions();
		if(goalStateId != oldStateId){		//发现状态改变
			if(this.transitionState(goalStateId)){
				currentState.exit();
				currentState = goalState;
				currentState.enter();
			}
		}
		currentState.update();
	}
	
	/**
	 * 转换状态
	 * @param goalStateId
	 * @return
	 */
	public boolean transitionState(int goalStateId){
		boolean b = false;
		for(Map.Entry<Byte, State> entry : stateMap.entrySet()){
			State tempState = entry.getValue();
			if(tempState.getType() == goalStateId){
				goalState = tempState;
				return true;
			}
		}
		return b;
	}
	
	/**
	 * 增加一个状态
	 * @param state
	 */
	public void addState(State state){
		stateMap.put(state.getType(), state);
	}
	
	/**
	 * 设置默认状态
	 * @param state
	 */
	public void setDefaultState(State state){
		defaultState = state;
		defaultState.enter();
	}
	
	public void setGoalId(int goalId){
		this.goalStateId = goalId;
		if(transitionState(goalStateId)){
			if(currentState != null)
				currentState.exit();
		}
	}
	
	public void reset(){
		
	}
}
