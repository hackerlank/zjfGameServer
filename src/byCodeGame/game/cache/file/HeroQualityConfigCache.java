package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.HeroQualityConfig;
/**
 * 武将质量
 * @author wcy 2016年4月7日
 *
 */
public class HeroQualityConfigCache {
	private static Map<Integer, HeroQualityConfig> map = new HashMap<>();

	public static void putHeroQualityConfig(HeroQualityConfig heroQualityConfig) {
		int quality = heroQualityConfig.getHeroQuality();
		map.put(quality, heroQualityConfig);
	}
	
	public static int getPByHeroQuality(int quality){
		int p = map.get(quality).getP();
		return p;
	}
}
