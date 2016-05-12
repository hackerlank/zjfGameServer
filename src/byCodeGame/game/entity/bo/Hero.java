package byCodeGame.game.entity.bo;

/**
 * 
 * @author wcy 2016年4月28日
 *
 */
public class Hero {
	// 英雄数据库id
	private int id;
	// 玩家id
	private int roleId;
	// 英雄id
	private int heroId;
	// 心情
	private int emotion;
	// 饥饿值
	private int hungry;
	// 熟练度
	private int effective;
	// 技能id
	private int skillId;
	// 天赋等级
	private byte talentLv;
	// 领悟值
	private int realize;
	// 年龄值
	private int age;
	// 喜爱的技能
	private int loveSkillId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getLoveSkillId() {
		return loveSkillId;
	}

	public void setLoveSkillId(int loveSkillId) {
		this.loveSkillId = loveSkillId;
	}

}
