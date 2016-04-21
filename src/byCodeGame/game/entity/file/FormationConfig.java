package byCodeGame.game.entity.file;

/**
 * 阵型相关配置类
 * @author 王君辉
 *
 */
public class FormationConfig {

	/** 阵型ID	 */
	private int id;
	/** 开启等级限制	 */
	private int needLv;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNeedLv() {
		return needLv;
	}
	public void setNeedLv(int needLv) {
		this.needLv = needLv;
	}
}
