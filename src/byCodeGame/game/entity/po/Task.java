package byCodeGame.game.entity.po;

public class Task {

	private int taskId;
	/** 当前进度	 */
	private int currentNum;
	/** 是否完成 0未完成，1已完成	 */
	private byte isComplete;
	

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getCurrentNum() {
		return currentNum;
	}

	public void setCurrentNum(int currentNum) {
		this.currentNum = currentNum;
	}

	public byte getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(byte isComplete) {
		this.isComplete = isComplete;
	}

	
}
