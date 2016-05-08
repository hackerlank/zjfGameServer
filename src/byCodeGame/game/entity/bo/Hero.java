package byCodeGame.game.entity.bo;

/**
 * 
 * @author wcy 2016年4月28日
 *
 */
public class Hero {
	// 玩家id
	private int roleId;
	// 英雄id
	private int heroId;
	// 心情
	private int emotion;
	// 饥饿值
	private int hungry;
	// 疲劳度
	private int tired;
	// 熟练度
	private int effective;
	// 技能id
	private int skillId;
	// 天赋id
	private int talentJobId;
	// 天赋等级
	private byte talentLv;
	// 领悟值
	private int realize;
	// 年龄阶段id
	private int ageId;
	// 年龄值
	private int age;
	// 转生值
	private int rebirth;
	// 热度
	private int hotspot;
	// 喜爱的职业
	private int loveJobId;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getHeroId() {
		return heroId;
	}

	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}

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

	public int getTalentJobId() {
		return talentJobId;
	}

	public void setTalentJobId(int talentJobId) {
		this.talentJobId = talentJobId;
	}

	public byte getTalentLv() {
		return talentLv;
	}

	public void setTalentLv(byte talentLv) {
		this.talentLv = talentLv;
	}

	public int getRealize() {
		return realize;
	}

	public void setRealize(int realize) {
		this.realize = realize;
	}

	public int getAgeId() {
		return ageId;
	}

	public void setAgeId(int ageId) {
		this.ageId = ageId;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getRebirth() {
		return rebirth;
	}

	public void setRebirth(int rebirth) {
		this.rebirth = rebirth;
	}

	public int getHotspot() {
		return hotspot;
	}

	public void setHotspot(int hotspot) {
		this.hotspot = hotspot;
	}

	public int getLoveJobId() {
		return loveJobId;
	}

	public void setLoveJobId(int loveJobId) {
		this.loveJobId = loveJobId;
	}

}
