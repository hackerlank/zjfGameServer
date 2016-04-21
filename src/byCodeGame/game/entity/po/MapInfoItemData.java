package byCodeGame.game.entity.po;

public class MapInfoItemData {
	private int id;
	private int weight;
	private int num;
	
	public MapInfoItemData() {
		// TODO Auto-generated constructor stub
	}
	
	public MapInfoItemData(String[] strs) {
		// TODO Auto-generated constructor stub
		this.id = Integer.parseInt(strs[0]);
		this.weight = Integer.parseInt(strs[1]);
		this.num = Integer.parseInt(strs[2]);
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	
}
