package byCodeGame.game.entity.po;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import byCodeGame.game.moudle.arena.fsm.control.Control;
import byCodeGame.game.moudle.arena.msg.ArenaFightMsg;


/**
 * 竞技场战斗数据类  
 * @author Lenovo
 *
 */
	
public class ArenaFightData {
	/** 帧数	 */
	private int frames;
	/** 每场战斗的唯一识别号	 */
	private String uuid;
	/** 当前毫秒数	 */
	private long nowTime;
	/** 游戏是否暂停	 */
	private boolean isPause;
	/** 起始暂停时间	 */
	private long startPauseTime;
	/** 暂停时间	 */
	private long pauseTime;
	
	public ArenaFightData(int frames){
		this.setFrames(frames);
	}

	private Map<String, ArenaObjFightData> myObjDataMap 
		= new HashMap<String, ArenaObjFightData>();
	
	private Map<String, ArenaObjFightData> targetObjDataMap 
		= new HashMap<String, ArenaObjFightData>();
	
	private Map<String, ArenaObjFightData> allObjDataMap 
		= new HashMap<String, ArenaObjFightData>();
	
	/** 控制器集合	 */
	private  Map<String, Control> controlMap = new HashMap<String, Control>();
	
	private Map<Integer, List<ArenaFightMsg>> msgList = new HashMap<Integer, List<ArenaFightMsg>>();
	
	/**
	 * 查询游戏是否结束暂停
	 */
	public void checkIsPause(){
		if((nowTime - startPauseTime) > pauseTime){
			isPause = false;
			
			startPauseTime = 0;
			pauseTime = 0;
		}
	}
	
	
	public Map<String, ArenaObjFightData> getMyObjDataMap() {
		return myObjDataMap;
	}
	public Map<String, ArenaObjFightData> getTargetObjDataMap() {
		return targetObjDataMap;
	}
	public  Map<String, Control> getControlMap() {
		return controlMap;
	}
	public Map<String, ArenaObjFightData> getAllObjDataMap() {
		return allObjDataMap;
	}
	public void setAllObjDataMap(Map<String, ArenaObjFightData> allObjDataMap) {
		this.allObjDataMap = allObjDataMap;
	}
	public int getFrames() {
		return frames;
	}
	public void setFrames(int frames) {
		this.frames = frames;
	}
	/**
	 * 加一帧
	 */
	public void addFrames(){
		this.frames += 1;
	}
	
	public void addMessage(ArenaFightMsg arenaFightMsg){
		List<ArenaFightMsg> list = null;
		if(!msgList.containsKey(this.frames)){
			list = new ArrayList<ArenaFightMsg>();
			msgList.put(this.frames, list);
		}else{
			list = msgList.get(this.frames);
		}
		
		list.add(arenaFightMsg);
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public long getNowTime() {
		return nowTime;
	}
	public void setNowTime(long nowTime) {
		this.nowTime = nowTime;
	}
	public Map<Integer, List<ArenaFightMsg>> getMsgList() {
		return msgList;
	}
	public void setMsgList(Map<Integer, List<ArenaFightMsg>> msgList) {
		this.msgList = msgList;
	}
	public boolean isPause() {
		return isPause;
	}
	public void setPause(boolean isPause) {
		this.isPause = isPause;
	}
	public long getStartPauseTime() {
		return startPauseTime;
	}
	public void setStartPauseTime(long startPauseTime) {
		this.startPauseTime = startPauseTime;
	}
	public long getPauseTime() {
		return pauseTime;
	}
	public void setPauseTime(long pauseTime) {
		this.pauseTime = pauseTime;
	}
}
