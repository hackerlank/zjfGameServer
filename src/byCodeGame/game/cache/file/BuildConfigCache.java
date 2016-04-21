package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.BuildConfig;

public class BuildConfigCache {

	private static Map<Integer, BuildConfig> farmConfigMap = new HashMap<Integer, BuildConfig>();
	private static Map<Integer, BuildConfig> houseConfigMap = new HashMap<Integer, BuildConfig>();
	private static Map<Integer, BuildConfig> barrackConfigMap = new HashMap<Integer, BuildConfig>();
	private static Map<Integer, BuildConfig> pubConfigMap = new HashMap<>();
	private static Map<Integer, BuildConfig> propConfigMap = new HashMap<>();

	public static void putBuildConfig(BuildConfig buildConfig) {
		if (buildConfig.getType() == 1) {
			houseConfigMap.put(buildConfig.getLv(), buildConfig);
		}
		if (buildConfig.getType() == 2) {
			farmConfigMap.put(buildConfig.getLv(), buildConfig);
		}
		if (buildConfig.getType() == 3) {
			barrackConfigMap.put(buildConfig.getLv(), buildConfig);
		}
		if (buildConfig.getType() == 4) {
			pubConfigMap.put(buildConfig.getLv(), buildConfig);
		}
		if (buildConfig.getType() == 5) {
			propConfigMap.put(buildConfig.getLv(), buildConfig);
		}
	}

	/**
	 * 获取buildConfig
	 * 
	 * @param lv 建筑等级
	 * @param type 类型
	 * @return
	 */
	public static BuildConfig getBuildConfig(int lv, byte type) {
		BuildConfig buildConfig = null;
		if (type == 1) {
			buildConfig = houseConfigMap.get(lv);
		}
		if (type == 2) {
			buildConfig = farmConfigMap.get(lv);
		}
		if (type == 3) {
			buildConfig = barrackConfigMap.get(lv);
		}
		if (type == 4) {
			buildConfig = pubConfigMap.get(lv);
		}
		if (type == 5) {
			buildConfig = propConfigMap.get(lv);
		}
		return buildConfig;
	}

	public static Map<Integer, BuildConfig> getBarrackConfigMap() {
		return barrackConfigMap;
	}

	public static void setBarrackConfigMap(Map<Integer, BuildConfig> barrackConfigMap) {
		BuildConfigCache.barrackConfigMap = barrackConfigMap;
	}
}
