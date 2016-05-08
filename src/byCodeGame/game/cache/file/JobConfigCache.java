package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.JobConfig;

public class JobConfigCache {
	private static Map<Integer, JobConfig> map = new HashMap<>();

	public static void putJobConfig(JobConfig config) {
		int jobId = config.getJobId();
		map.put(jobId, config);
	}
	
	public static JobConfig getJobConfigById(int id){
		return map.get(id);
	}
}
