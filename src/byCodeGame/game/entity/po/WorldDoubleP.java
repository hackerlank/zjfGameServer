package byCodeGame.game.entity.po;

public class WorldDoubleP {
	private int roleId;
	
	private int startTime;

	
	public WorldDoubleP() {
		
	}
	
	public WorldDoubleP(String[] value)
	{
		this.roleId = Integer.parseInt(value[0]);
		this.startTime = Integer.parseInt(value[1]);
	}
	
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	
	
	
}
