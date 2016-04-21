package byCodeGame.game.util;

import java.util.List;
import java.util.Map;

import byCodeGame.game.cache.file.HeroConfigCache;
import byCodeGame.game.entity.bo.Build;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.file.HeroConfig;
import byCodeGame.game.entity.po.LevyInfo;

public class InComeUtils {
	/**
	 * 获取减少的CD
	 * @param role
	 * @param hero
	 * @param type
	 * @return
	 */
	public static int getValueCaption(Role role , Hero hero)
	{
//		MainCityConfig x = MainCityConfigCache.getMainCityConfig(role.getLv());
//		if(x == null) x = MainCityConfigCache.getMainCityConfig(80);
		HeroConfig config = HeroConfigCache.getHeroConfigById(hero.getHeroId());
		int captain = (int)(config.getCaptain() + (hero.getRebirthLv() * config.getCaptainGrowValue()));
//		return (int)(x.getCityCutDownCD() + captain * x.getHeroCutDownCD()); 
		double n = Math.pow(1.005,captain);
		int delta = (int) ((1.0-1.0/n)*100.0);
		return delta;
	}
	
	/**
	 * 获取增加的粮草
	 * @param role
	 * @param hero
	 * @param type
	 * @return
	 */
	public static int getValuePower(Role role , Hero hero)
	{
//		MainCityConfig x = MainCityConfigCache.getMainCityConfig(role.getLv());
//		if(x == null) x = MainCityConfigCache.getMainCityConfig(80);
//		HeroConfig config = HeroConfigCache.getHeroConfigById(hero.getHeroId());
//		int power = (int)(config.getPower() + (hero.getRebirthLv() * config.getPowerGrowValue()));
//		int result = (int)(x.getCityAddFood() + power * x.getHeroAddFood()); 
		int result = 0;
		return result;
	}
	
	/**
	 * 获取增加的银币
	 * @param role
	 * @param hero
	 * @param type
	 * @return
	 */
	public static int getValueIn(Role role , Hero hero)
	{
//		MainCityConfig x = MainCityConfigCache.getMainCityConfig(role.getLv());
//		if (x == null)
//			x = MainCityConfigCache.getMainCityConfig(80);
//		HeroConfig config = HeroConfigCache.getHeroConfigById(hero.getHeroId());
//		int in = (int) (config.getIntel() + (hero.getRebirthLv() * config.getIntelGrowValue()));
//
//		int result = (int) (x.getCityAddCoin() + in * x.getHeroAddCoin());
		int result = 0;
		return result;
	}
	
	/**
	 * 检查是已经有同类型的派遣事件
	 * @param role
	 * @param type
	 * @return 有同类型派遣返回false，没有返回而true
	 */
	public static boolean checkLevy(Role role , byte type ,int heroId)
	{
		boolean flag = true;
		
		
		if(role.getBuild().getLevyMap().containsKey(heroId))
		{
			flag = false;
			return flag;
		}
		for(LevyInfo x :role.getBuild().getLevyMap().values())
		{
			if(x.getType() == type)
			{
				flag= false;
				break;
			} 
		}
		
		return flag;
	}
	
	/**
	 * 获得一种建筑的委派数据，如果该建筑没有委派，则返回为空
	 * @param role
	 * @param type
	 * @return
	 * @author wcy 2016年1月20日
	 */
	public static LevyInfo getLevyInfoByType(Role role,byte type){
		Build build = role.getBuild();
		Map<Integer,LevyInfo> heroLevyMap = build.getLevyMap();
		LevyInfo result = null;
		for (LevyInfo levyInfo : heroLevyMap.values()) {
			byte levyType = levyInfo.getType();
			if (levyType == type) {
				result = levyInfo;
			}
		}
		
		return result;
	}
	
	/**
	 * 战斗消耗粮草
	 * 
	 * @param hp 总兵力数
	 * @param allLevel 参战的英雄等级总和
	 * @param heroNum 参展英雄个数
	 * @return
	 * @author wcy 2016年4月13日
	 */
//	public static int fightNeedFood(int hp, int allLevel, int heroNum) {
//		float allLevelFloat = (float) allLevel;
//		int food = (int) Math.ceil(hp * (0.012 + 0.0015 * Math.pow(Math.ceil(allLevelFloat / heroNum), 0.75)));
//		return food;
//	}
	
	/**
	 * 战役消耗粮草
	 * @param heroList
	 * @return
	 * @author wcy 2016年4月18日
	 */
	public static int fightNeedFood(List<Hero> heroList) {
		float hp = .0f;
		float lv = .0f;
		int food = 0;
		for (Hero hero : heroList) {
			hp = hero.getArmsNum();
			lv = hero.getLv();
			food += (int) Math.ceil(hp * (0.012 + 0.0015 * Math.pow(lv, 0.75)));
		}
		return food;
	}
}
