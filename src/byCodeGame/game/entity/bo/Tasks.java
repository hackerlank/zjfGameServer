package byCodeGame.game.entity.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import byCodeGame.game.entity.po.Task;

public class Tasks {

	private int roleId;
	
	/** 进行中的任务 （任务ID,任务进度,是否完成;任务ID,任务进度,是否完成;）	 */
	private String doingTask;
	/** 进行中的任务集合	 key 任务ID  value Task*/
	private Map<Integer, Task> doingTaskMap = new HashMap<Integer, Task>();
	/** 每日任务集合		 key 任务ID	value Task*/
	private Map<Integer, Task> dailyTaskMap = new HashMap<Integer, Task>();
	/** 每日任务完成度							  */
	private int dailyTaskComplete ;
	/** 记录今日已领取的每日任务奖励				  */
	private List<Integer> alreadyGetToday = new ArrayList<Integer>();
	
	private boolean change;
	
//	/** 已完成的任务 (任务ID;任务ID;任务ID;)	 */
//	private String completeTask;
//	/** 已完成的任务集合	 */
//	private Set<Integer> completeTaskSet = new HashSet<Integer>();

	
	
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getDoingTask() {
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<Integer, Task> entry : getDoingTaskMap().entrySet()){
			Task task = entry.getValue();
			sb.append(task.getTaskId()).append(",").append(task.getCurrentNum()).
			append(",").append(task.getIsComplete()).append(";");
		}
		this.doingTask = sb.toString();
		return doingTask;
	}

	public void setDoingTask(String doingTask) {
		if(doingTask != null && !doingTask.equals("")){
			String[] rootStr = doingTask.split(";");
			for(String rootValue :rootStr){
				String[] a = rootValue.split(",");
				Task task = new Task();
				task.setTaskId(Integer.parseInt(a[0]));
				task.setCurrentNum(Integer.parseInt(a[1]));
				task.setIsComplete(Byte.valueOf(a[2]));
				this.getDoingTaskMap().put(task.getTaskId(),task);
			}
		}
	}

//	public String getCompleteTask() {
//		StringBuilder sb = new StringBuilder();
//		for(Integer taskId : getCompleteTaskSet()){
//			sb.append(taskId).append(";");
//		}
//		this.completeTask = sb.toString();
//		return completeTask;
//	}
//
//	public void setCompleteTask(String completeTask) {
//		if(completeTask != null && !completeTask.equals("")){
//			String[] strs = completeTask.split(";");
//			for(String str : strs){
//				getCompleteTaskSet().add(Integer.valueOf(str));
//			}
//		}
//	}

	public Map<Integer, Task> getDoingTaskMap() {
		return doingTaskMap;
	}

	public boolean isChange() {
		return change;
	}

	public void setChange(boolean change) {
		this.change = change;
	}

	public Map<Integer, Task> getDailyTaskMap() {
		return dailyTaskMap;
	}

	public void setDailyTaskMap(Map<Integer, Task> dailyTaskMap) {
		this.dailyTaskMap = dailyTaskMap;
	}

	public int getDailyTaskComplete() {
		return dailyTaskComplete;
	}

	public void setDailyTaskComplete(int dailyTaskComplete) {
		this.dailyTaskComplete = dailyTaskComplete;
	}

	public List<Integer> getAlreadyGetToday() {
		return alreadyGetToday;
	}

	public void setAlreadyGetToday(List<Integer> alreadyGetToday) {
		this.alreadyGetToday = alreadyGetToday;
	}

//	public Set<Integer> getCompleteTaskSet() {
//		return completeTaskSet;
//	}
}
