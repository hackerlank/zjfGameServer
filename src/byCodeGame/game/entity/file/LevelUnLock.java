package byCodeGame.game.entity.file;


/**
 * 等级解锁
 * @author 王君辉
 *
 */
public class LevelUnLock {

	/** 玩家等级	 */
	private int lv;
	
	/** 解锁的任务ID	 */
	private String taskId;
	/** 解锁的任务ID数组	 */
	private int[] taskIdArr;

	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		if(taskId !=null && !taskId.equals("")){
			String[] strs = taskId.split(",");
			this.taskIdArr = new int[strs.length];
			for (int i = 0; i < strs.length; i++) {
				taskIdArr[i] = Integer.parseInt(strs[i]);
			}
		}
	}

	public int[] getTaskIdArr() {
		return taskIdArr;
	}

}
