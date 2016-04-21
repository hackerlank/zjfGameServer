package byCodeGame.game.util;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.cache.file.EquipConfigCache;
import byCodeGame.game.cache.file.EquiptPrefixCache;
import byCodeGame.game.cache.file.HeroConfigCache;
import byCodeGame.game.cache.file.HeroFavourConfigCache;
import byCodeGame.game.cache.file.HeroQualityConfigCache;
import byCodeGame.game.cache.file.ProfessionCache;
import byCodeGame.game.cache.file.ZhuanshengConfigCache;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Prop;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.file.EquipConfig;
import byCodeGame.game.entity.file.EquiptPrefix;
import byCodeGame.game.entity.file.HeroConfig;
import byCodeGame.game.entity.file.HeroFavourConfig;
import byCodeGame.game.entity.file.Profession;
import byCodeGame.game.entity.file.ZhuanshengConfig;
import byCodeGame.game.moudle.inCome.InComeConstant.HeroAttrType;


/***
 * 武将计算数值使用的工具类
 * @author xjd
 *
 */
public class HeroUtils {
	/** 战斗力品质计算系数*/
	public static final double[] COEFFICIENT =  {0,0.9,1.0,1.1,1.2};
	
	public static final int NUMBER_0 = 0;
	public static final int ARMY_ID_1 = 2101;
	public static final int ARMY_ID_2 = 2102;
	public static final int ARMY_ID_3 = 2103;
	public static final int ARMY_ID_4 = 2104;
	public static final int ARMY_ID_5 = 2105;
	public static final int TYPE_1 = 1;
	public static final int TYPE_2 = 2;
	public static final int TYPE_3 = 3;
	public static final int TYPE_4 = 4;
	public static final int TYPE_5 = 5;
	public static final int ATK_NUM = 1;
	public static final int ZATK_NUM = 2;
	public static final int CATK_NUM = 3;
	public static final int DEF_NUM = 4;
	public static final int ZDEF_NUM = 5;
	public static final int CDEF_NUM = 6;
	public static final int CRIT_NUM = 7;
	public static final int BACK_NUM = 8;
	public static final int VAMPIRE_NUM = 9;
	public static final int ADDHURT_NUM = 10;
	public static final int RMHURT_NUM = 11;
	public static final int BJ_NUM = 12;
	
	/***
	 * 计算最大带兵数量（最大血量）<br/>
	 * 	(heroConfig.hp + (lv-1)*profession.hpGrow) 
	 * 	*
	 * 	(profession.hp/1000) * 
	 * 		
	 * @param role
	 * @param hero
	 * @param set
	 * @return
	 * @author xjd
	 */
	public static int getMaxHp(Role role , Hero hero)
	{
		int maxHp = NUMBER_0;
		if(hero == null) return maxHp;
		HeroConfig config = HeroConfigCache.getHeroConfigById(hero.getHeroId());
		Profession profession = ProfessionCache.getProfessionById(config.getProfession());
		int p = HeroQualityConfigCache.getPByHeroQuality(config.getQuality());
		if(config == null || profession == null) 
			return maxHp;
		maxHp += config.getHp();
		
		maxHp += (hero.getLv()-1) * profession.getHpGrow();
		
		double fac = (profession.getHp()/1000.0) * (p/1000.0);
		
		maxHp = (int)(maxHp * fac);
		ZhuanshengConfig zhuanshengConfig = ZhuanshengConfigCache.getZhuanshengConfig(hero.getRebirthLv());
		
		return maxHp + zhuanshengConfig.getHp();
	}

	
	/**
	 * 计算统帅
	 * @param hero
	 * @return
	 * @author xjd
	 */
	public static HeroConfig getCaptain(Hero hero)
	{
		HeroConfig heroConfig = new HeroConfig();
//		int captain = NUMBER_0;
//		int power = NUMBER_0;
//		int intel = NUMBER_0;
//		HeroConfig config = HeroConfigCache.getHeroConfigById(hero.getHeroId());
//		HeroFavourConfig favourConfig = HeroFavourConfigCache.getHeroFavourConfig(hero.getRank());
//		if(config == null || favourConfig == null) return heroConfig;
//		
//		captain += config.getCaptain();
//		captain += hero.getRebirthLv() * config.getCaptainGrowValue();
//		captain += favourConfig.getCaptain();
//		power += config.getPower();
//		power += hero.getRebirthLv() * config.getPowerGrowValue();	
//		power += favourConfig.getPower();
//		intel += config.getIntel();
//		intel += hero.getRebirthLv() * config.getIntelGrowValue();
//		intel += favourConfig.getIntel();
		
		heroConfig.setCaptain(getSanWei(HeroAttrType.captain, hero));
		heroConfig.setPower(getSanWei(HeroAttrType.power, hero));
		heroConfig.setIntel(getSanWei(HeroAttrType.intel, hero));
		
		return heroConfig;
	}
	
	/**
	 * 获取统帅
	 * @param hero
	 * @return
	 */
	public static int getCaptainValue(Hero hero)
	{
		
		int captain = NUMBER_0;
//		HeroConfig config = HeroConfigCache.getHeroConfigById(hero.getHeroId());
//		HeroFavourConfig favourConfig = HeroFavourConfigCache.getHeroFavourConfig(hero.getRank());
//		if(config == null || favourConfig == null) return NUMBER_0;
		
		
//		captain += config.getCaptain();
//		captain += hero.getRebirthLv() * config.getCaptainGrowValue();
//		captain += favourConfig.getCaptain();
		
		/**
		 * 统帅： P为各品阶武将系数； 
		 * 统帅=int（统帅初始值+（武将等级-1）*统帅成长值*2^（武将星级-1）*P/1000+转生统帅）；
		 */
		captain = getSanWei(HeroAttrType.captain, hero);

		return captain;
	}
	
	/**
	 * 获取武力
	 * @param hero
	 * @return
	 */
	public static int getPowerValue(Hero hero)
	{
		int power = NUMBER_0;
//		HeroConfig config = HeroConfigCache.getHeroConfigById(hero.getHeroId());
//		HeroFavourConfig favourConfig = HeroFavourConfigCache.getHeroFavourConfig(hero.getRank());
//		if(config == null || favourConfig == null) return NUMBER_0;
		
//		power += config.getPower();
//		power += hero.getRebirthLv() * config.getPowerGrowValue();
//		power += favourConfig.getPower();
		
		/**
		 * 武力： P为各品阶武将系数；
		 *  武力=int（武力初始值+（武将等级-1）*武力成长值*2^（武将星级-1）*P/1000+转生武力）；
		 */		
		power = getSanWei(HeroAttrType.power, hero);
		return power;
	}
	
	/**
	 * 获取智力
	 * @param hero
	 * @return
	 */
	public static int getIntelValue(Hero hero)
	{
		int intel = NUMBER_0;
//		HeroConfig config = HeroConfigCache.getHeroConfigById(hero.getHeroId());
//		HeroFavourConfig favourConfig = HeroFavourConfigCache.getHeroFavourConfig(hero.getRank());
//		if(config == null || favourConfig == null) return NUMBER_0;
		
//		intel += config.getIntel();
//		intel += hero.getRebirthLv() * config.getIntelGrowValue();
//		intel += favourConfig.getIntel();
		
		/**
		 * 智力： P为各品阶武将系数；
		 *  智力=int（智力初始值+（武将等级-1）*智力成长值*2^（武将星级-1）*P/1000+转生智力）；
		 */
		intel = getSanWei(HeroAttrType.intel, hero);
		return intel;
	}
	
	/**
	 * 任一三围属性值： P为各品阶武将系数；
	 *  任一三围属性值=int（初始值+（武将等级-1）*成长值*2^（武将星级-1）*P/1000+转生加成）；
	 * @param type
	 * @param hero
	 * @return
	 * @author wcy 2016年4月7日
	 */
	private static int getSanWei(HeroAttrType type,Hero hero){
		int star = hero.getRank();
		short heroLv = hero.getLv();
		short rebirthLv = hero.getRebirthLv();
		int heroId = hero.getHeroId();
		int result = getSanWei(type,star,rebirthLv,heroLv,heroId);
		return result;
	}
	
	public static int getSanWei(HeroAttrType type, int star, short rebirthLv, short heroLv, int heroId) {
		HeroConfig config = HeroConfigCache.getHeroConfigById(heroId);
		int quality = config.getQuality();
		int grow = 0;
		int init = 0;
		if (type == HeroAttrType.captain) {
			init = config.getCaptain();
			grow = config.getHeroCaptainGrowValue();
		}
		if (type == HeroAttrType.intel) {
			init = config.getIntel();
			grow = config.getHeroIntelGrowValue();
		}
		if (type == HeroAttrType.power) {
			init = config.getPower();
			grow = config.getHeroPowerGrowValue();
		}
		int P = HeroQualityConfigCache.getPByHeroQuality(quality);
		int rebirthAdd = getRebirthGrowAddValue(type, rebirthLv);
		int result = (int) (init + (heroLv - 1) * grow * Math.pow(2, (star - 1)) * P / 1000.0f + rebirthAdd);
		return result;
	}
	
	private static int getRebirthGrowAddValue(HeroAttrType type,short rebirthLv){
		int result = 0;
		
		ZhuanshengConfig config = ZhuanshengConfigCache.getZhuanshengConfig((int)rebirthLv);
		if (type == HeroAttrType.intel) {
			result = config.getIntelRebirthGrowValue();
		} else if (type == HeroAttrType.captain) {
			result = config.getCaptainRebirthGrowValue();
		} else if (type == HeroAttrType.power) {
			result = config.getPowerRebirthGrowValue();
		}
		
		return result;
	}
	/**
	 * 获取下次转生的三维
	 * @param heroId
	 * @param rLv
	 * @return
	 */
	public static HeroConfig getCaptain(int heroId , int rLv)
	{
		//TODO 未修改新版
		HeroConfig heroConfig = new HeroConfig();
		int captain = NUMBER_0;
		int power = NUMBER_0;
		int intel = NUMBER_0;
		HeroConfig config = HeroConfigCache.getHeroConfigById(heroId);
		if(config == null) return heroConfig;
		
		captain += config.getCaptain();
		captain += rLv * config.getCaptainGrowValue();
		power += config.getPower();
		power += rLv * config.getPowerGrowValue();	
		intel += config.getIntel();
		intel += rLv * config.getIntelGrowValue();
		
		heroConfig.setCaptain(captain);
		heroConfig.setPower(power);
		heroConfig.setIntel(intel);
		
		return heroConfig;
	}

	public static int getArmyIdByHero(HeroConfig config)
	{
		int armyId = NUMBER_0;
//		if(config.getType() == TYPE_1)
//		{
//			armyId = ARMY_ID_1;
//		}else if (config.getType() == TYPE_2) {
//			armyId = ARMY_ID_2;
//		}else if (config.getType() == TYPE_3) {
//			armyId = ARMY_ID_3;
//		}else if (config.getType() == TYPE_4){
//			armyId = ARMY_ID_4;
//		}else if (config.getType() == TYPE_5) {
//			armyId = ARMY_ID_5;
//		}
//		
		return armyId;
	}

//	public static void countArmyNum(Role role ,FormationData data)
//	{
//		int allHp = role.getPopulation(role);
//		int allHeroHp = NUMBER_0;
//			for(Integer x : data.getData().values())
//			{
//				Hero hero = role.getRecruitHeroMap().get(x);
//				if(hero == null) continue;
//				double tempHeroHp = (double)getMaxHp(role, hero) / allHeroHp;
//				int temp = (int)(tempHeroHp * allHp) ;
//				if(temp < 1) temp =1;
//				role.getRecruitHeroMap().get(x).setArmsNum(temp);
//				System.out.println();
//				allHp -= temp;
//			}
//	}

	public static Map<Integer, Integer> getEquipAb(Role role ,Hero hero)
	{
		Map<Integer, Integer> abMap = new HashMap<Integer, Integer>();
		int atk = NUMBER_0;
		int zatk = NUMBER_0;
		int catk = NUMBER_0;
		int def = NUMBER_0;
		int zdef = NUMBER_0;
		int cdef = NUMBER_0;
		int crit = NUMBER_0;
		int back = NUMBER_0;
		int addHurt = NUMBER_0;
		int rmHurt = NUMBER_0;
		int bingji = NUMBER_0;
		for(int x : hero.getEquipMap().values())
		{
			Prop prop = role.getPropMap().get(x);
			if(prop == null) continue;
			EquipConfig equipConfig = EquipConfigCache.getEquipConfigById(prop.getItemId());
			if(equipConfig == null) continue;
			EquiptPrefix prefix = EquiptPrefixCache.getEquipConfigById(prop.getPrefixId());
			atk += equipConfig.getAtk() + equipConfig.getAtkStrongValue() * prop.getLv();
			zatk += equipConfig.getZatk() + equipConfig.getZatkStrongValue() * prop.getLv();
			catk += equipConfig.getCatk() + equipConfig.getCatkStrongValue()* prop.getLv();
			def += equipConfig.getDef() + equipConfig.getDefStrongValue() * prop.getLv();
			zdef += equipConfig.getZdef() + equipConfig.getZdefStrongValue() * prop.getLv();
			cdef += equipConfig.getCdef() + equipConfig.getCatkStrongValue() * prop.getLv();
			if(prefix == null) continue;
			addHurt += prefix.getAddHurt();
			rmHurt += prefix.getRmHurt();
			bingji += prefix.getBj();
		}
		
		abMap.put(ATK_NUM, atk);
		abMap.put(ZATK_NUM, zatk);
		abMap.put(CATK_NUM, catk);
		abMap.put(DEF_NUM, def);
		abMap.put(ZDEF_NUM, zdef);
		abMap.put(CDEF_NUM, cdef);
		abMap.put(CRIT_NUM, crit);
		abMap.put(BACK_NUM, back);
		abMap.put(ADDHURT_NUM, addHurt);
		abMap.put(RMHURT_NUM, rmHurt);
		abMap.put(BJ_NUM, bingji);
		return abMap;
	}


	public static int getGrowValue(double value , short lv)
	{
		return (int)value * lv;
	}
	
	/**
	 * 战力值=（兵力*（普攻+战攻+策攻）*（普防+战防+策防）*（统帅+武力+智力）*0.044）^0.225
	 * @param armsNum
	 * @param generalAttack
	 * @param powerAttack
	 * @param magicAttack
	 * @param generalDefence
	 * @param powerDefence
	 * @param magicDefence
	 * @param captain
	 * @param power
	 * @param intel
	 * @return
	 * @author wcy 2016年4月15日
	 */
	public static int getHeroFightValue(int armsNum, double generalAttack, double powerAttack, double magicAttack,
			double generalDefence, double powerDefence, double magicDefence, int captain, int power, int intel) {
		int finalNum = (int) Math.ceil(Math.pow(
				armsNum * (generalAttack + powerAttack + magicAttack)
						* (generalDefence + powerDefence + magicDefence)
						* (captain + power + intel) * 0.044, 0.225));
		return finalNum;
	}
	
	
}
