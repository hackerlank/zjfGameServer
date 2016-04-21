package byCodeGame.game.entity.file;

import java.util.HashMap;
import java.util.Map;

public class TrialTowerConfig {
	/** 选择类型			*/
	private int useId;
	/** 阶段				*/
	private int process;
	/** 名字				*/
	private String title;
	/** troops			*/
	private String troops;
	/** 兵种信息			*/
	private int formationID;
	/** 兵种等级			*/
	private int formationLevel;
	/** 形象ID			*/
	private int imgId;
	/** 战斗力			*/
	private int fightValue;
	/** 奖励				*/
	private String award;
	/**随机奖励*/
	private String randomReward;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getFormationID() {
		return formationID;
	}
	public void setFormationID(int formationID) {
		this.formationID = formationID;
	}
	public int getFormationLevel() {
		return formationLevel;
	}
	public void setFormationLevel(int formationLevel) {
		this.formationLevel = formationLevel;
	}
	public Map<Integer, String> getTroops() {
		Map<Integer, String> data = new HashMap<Integer, String>();
		String str []=troops.split(",");
		int i=0;
		for (String arr : str) {
			i++;
			data.put(i, arr);
		}
		return data;
	}
	public void setTroops(String troops) {
		this.troops = troops;
	}
	
	public String getTroopsStr()
	{
		return this.troops;
	}
	public int getImgId() {
		return imgId;
	}
	public void setImgId(int imgId) {
		this.imgId = imgId;
	}
	public int getUseId() {
		return useId;
	}
	public void setUseId(int useId) {
		this.useId = useId;
	}
	public int getProcess() {
		return process;
	}
	public void setProcess(int process) {
		this.process = process;
	}
	public int getFightValue() {
		return fightValue;
	}
	public void setFightValue(int fightValue) {
		this.fightValue = fightValue;
	}
	public String getAward() {
		return award;
	}
	public void setAward(String award) {
		this.award = award;
	}
	public String getRandomReward() {
		return randomReward;
	}
	public void setRandomReward(String randomReward) {
		this.randomReward = randomReward;
	}
	
}
