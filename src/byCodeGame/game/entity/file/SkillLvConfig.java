package byCodeGame.game.entity.file;
/**
 * 
 * @author AIM
 *
 */
public class SkillLvConfig {
	/**技能等级id*/
	private int id;
	/**名称*/
	private String name;
	/**需要达到的熟练度*/
	private int effective;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getEffective() {
		return effective;
	}
	public void setEffective(int effective) {
		this.effective = effective;
	}
	
}
