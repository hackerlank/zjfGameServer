package byCodeGame.game.entity.po;

/**
 * 用于服务器启动时将数据库中的账号 姓名等信息查询出来后做缓存用
 * @author Lenovo
 *
 */
public class StartRole {

	private int id;
	
	private String name;
	
	private String account;
	
	private byte nation;

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

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public byte getNation() {
		return nation;
	}

	public void setNation(byte nation) {
		this.nation = nation;
	}	
}
