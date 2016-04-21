package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.TaskInfo;

public class TaskInfoCache {

	private static Map<Integer, TaskInfo> taskInfoMap = new HashMap<Integer, TaskInfo>();
	
	
	public void putTaskInfo(TaskInfo taskInfo){
		taskInfoMap.put(taskInfo.getTaskId(), taskInfo);
	}
	
	public static TaskInfo getputTaskInfoById(int taskId){
		return taskInfoMap.get(taskId);
	}
}
