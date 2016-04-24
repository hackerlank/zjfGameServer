package byCodeGame.game.entity.bo;

import byCodeGame.game.entity.bo.base.GameEntity;

public class Hero extends GameEntity{
	//心情
	private int emotion;
	//饥饿值
	private int hungry;
	//疲劳度
	private int tired;
	//熟练度
	private int effective;
	//技能id
	private int skillId;
	//天赋id
	private int giftId;
	//年龄阶段id
	private int ageId;
	//年龄阶段值
	private int ageCount;
	//转生值
	private int rebirth;
	//工作刷新时间
	private int workRefreshTime;
	//热度
	private int hotspot;

	public int getEmotion() {
		return emotion;
	}

	public void setEmotion(int emotion) {
		this.emotion = emotion;
	}

	public int getHungry() {
		return hungry;
	}

	public void setHungry(int hungry) {
		this.hungry = hungry;
	}

	public int getTired() {
		return tired;
	}

	public void setTired(int tired) {
		this.tired = tired;
	}

	public int getEffective() {
		return effective;
	}

	public void setEffective(int effective) {
		this.effective = effective;
	}

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public int getGiftId() {
		return giftId;
	}

	public void setGiftId(int giftId) {
		this.giftId = giftId;
	}

	public int getAgeId() {
		return ageId;
	}

	public void setAgeId(int ageId) {
		this.ageId = ageId;
	}

	public int getRebirth() {
		return rebirth;
	}

	public void setRebirth(int rebirth) {
		this.rebirth = rebirth;
	}

	public int getWorkRefreshTime() {
		return workRefreshTime;
	}

	public void setWorkRefreshTime(int workRefreshTime) {
		this.workRefreshTime = workRefreshTime;
	}

	public int getHotspot() {
		return hotspot;
	}

	public void setHotspot(int hotspot) {
		this.hotspot = hotspot;
	}

	public int getAgeCount() {
		return ageCount;
	}

	public void setAgeCount(int ageCount) {
		this.ageCount = ageCount;
	}

}
