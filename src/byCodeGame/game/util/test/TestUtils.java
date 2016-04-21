package byCodeGame.game.util.test;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.cache.file.ArmsConfigCache;
import byCodeGame.game.cache.file.EquipConfigCache;
import byCodeGame.game.cache.file.HeroConfigCache;
import byCodeGame.game.cache.file.ProfessionCache;
import byCodeGame.game.cache.file.RoleScienceConfigCache;
import byCodeGame.game.cache.file.ZhuanshengConfigCache;
import byCodeGame.game.entity.file.ArmsConfig;
import byCodeGame.game.entity.file.EquipConfig;
import byCodeGame.game.entity.file.HeroConfig;
import byCodeGame.game.entity.file.Profession;
import byCodeGame.game.entity.file.RoleScienceConfig;
import byCodeGame.game.entity.file.ZhuanshengConfig;
import cn.bycode.game.battle.data.CorpData;
import cn.bycode.game.battle.data.FormationData;
import cn.bycode.game.battle.data.TroopData;

public class TestUtils {
	public static final int NUM_10000 = 10000;
	public static final int NUMBER_0 = 0;
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
	
	public static TroopData changeStrToTroop(FightTestData test)
	{
		TroopData data = new TroopData();
		data.setPlayerName(test.getName());
		data.setPlayerId(999);
		FormationData fmt = new FormationData();
		RoleScienceConfig roleScienceConfig= RoleScienceConfigCache.getRoleScienceConfig(test.getFormationId(),5);
		fmt.name = roleScienceConfig.getName();  
		fmt.level= test.getFormationLv();
		fmt.hp=roleScienceConfig.getHp();
		fmt.atk1=roleScienceConfig.getAtk()/NUM_10000 + 1;
		fmt.atk2=roleScienceConfig.getZatk()/NUM_10000 + 1;
		fmt.atk3=roleScienceConfig.getJatk()/NUM_10000 + 1;
		fmt.def1=roleScienceConfig.getDef()/NUM_10000 + 1;
		fmt.def2=roleScienceConfig.getZdef()/NUM_10000 + 1;
		fmt.def3=roleScienceConfig.getJdef()/NUM_10000 + 1;
		data.setFormation(fmt);
		CorpData[] corps = new CorpData[10];
		for (int i = 1; i <= 9; i++) {
			corps[i] = new CorpData();
			corps[i].hpMax = 0;
			corps[i].setHpCur(0);
		}
		Map<Integer, String> temp = test.getHeroMap();
		for (Map.Entry<Integer, String> map : temp.entrySet()) {
			if(!map.getValue().equals("") || map.getValue()!= null){
//				corps[map.getKey()] = new CorpData();
				String[] strs = map.getValue().split(",");
				int heroId = Integer.parseInt(strs[1]);
				int lv = 80;
				int rLv = 7;
				int armyId = Integer.parseInt(strs[2]);
				HeroConfig heroConfig=HeroConfigCache.getHeroConfigById(heroId);
				ArmsConfig armsConfig=ArmsConfigCache.getArmsConfigById(armyId);
				Profession profession = ProfessionCache.getProfessionById(heroConfig.getProfession());
				corps[map.getKey()].generalID = heroConfig.getImgId();
				corps[map.getKey()].generalName = HeroConfigCache.getHeroConfigById(heroId).getName();
				corps[map.getKey()].skillId = Integer.parseInt(strs[3]);
				corps[map.getKey()].generalLevel = lv;
				corps[map.getKey()].generalLea = heroConfig.getCaptain()
						+ ((int) (heroConfig.getCaptainGrowValue()) * rLv);
				corps[map.getKey()].generalStr = heroConfig.getPower()
						+ ((int) (heroConfig.getPowerGrowValue()) * rLv);
				corps[map.getKey()].generalInt =heroConfig.getIntel()
						+ ((int) (heroConfig.getIntelGrowValue()) * rLv);
				corps[map.getKey()].armyID = armyId;
				corps[map.getKey()].armyAtk1Rate=armsConfig.getAtkRate();
				corps[map.getKey()].armyDef1Rate=NUM_10000/(NUM_10000 + armsConfig.getDefRate());
				corps[map.getKey()].armyAtk2Rate=armsConfig.getTacticsAtkRate();
				corps[map.getKey()].armyDef2Rate=NUM_10000/(NUM_10000 + armsConfig.getTacticsDefRate());
				corps[map.getKey()].armyAtk3Rate=armsConfig.getTrickAtkRate();
				corps[map.getKey()].armyDef3Rate=NUM_10000/(NUM_10000 + armsConfig.getTrickDefRate());
				
				int hpMax=getMaxHp(heroConfig, lv, rLv) + 5434;
				
				corps[map.getKey()].hpMax=hpMax;
				corps[map.getKey()].setHpCur(hpMax);
				//corps[map.getKey()].energy=0;
				corps[map.getKey()].atk1=profession.getAttack() + 2262;
				corps[map.getKey()].atk1 *= fmt.atk1;
				corps[map.getKey()].def1=profession.getDefence() +  1131;
				corps[map.getKey()].def1 *= fmt.def1;
				corps[map.getKey()].atk2=profession.getKongfuAtk() + 2262;
				corps[map.getKey()].atk2 *= fmt.atk2;
				corps[map.getKey()].def2=profession.getKongfuDef() + 1131;
				corps[map.getKey()].def2 *= fmt.def2;
				corps[map.getKey()].StgAtt=profession.getMagicAtk() + 2262;
				corps[map.getKey()].StgAtt *= fmt.atk3;
				corps[map.getKey()].StgDef=profession.getMagicDef() +  1131;
				corps[map.getKey()].StgDef *= fmt.def3;
				corps[map.getKey()].AllDamADD = 0;
				corps[map.getKey()].AllDamRM = 0;
				corps[map.getKey()].troopType = ArmsConfigCache.getArmsConfigById(armyId).getFunctionType();
			}
		}
		data.setCorps(corps);	
		return data;
	}
	
	
	public static int getMaxHp( HeroConfig config ,
			int lv ,int rLv )
	{
		int maxHp = 0;
		ZhuanshengConfig config2 = ZhuanshengConfigCache.getZhuanshengConfig(rLv);
		Profession profession = ProfessionCache.getProfessionById(config.getProfession());
		if(config == null || config2 == null) return maxHp;
		
		
		maxHp += profession.getHp();
		
		maxHp += lv * profession.getHpGrow();
		
		
//		String[] strs = str.split(";");
//		for(String x : strs)
//		{
//			String[] temp = x.split(",");
//			EquipConfig equipConfig = EquipConfigCache.getEquipConfigById(Integer.parseInt(temp[0]));
//			if(equipConfig == null) continue;
//			if(equipConfig.getHp() != 0)
//			{
//				maxHp += equipConfig.getHp() + (equipConfig.getHpStrongValue()* Integer.parseInt(temp[1]));
//			}
//		}
		
//		for(Entry<Integer, Integer> x : role.getRoleScienceMap().entrySet())
//		{
//			RoleScienceConfig config3 = RoleScienceConfigCache.getRoleScienceConfig(x.getKey());
//			if(config2 == null) continue;
//			maxHp += config3.getHp() * x.getValue();
//		}
		
		return maxHp;
	}
	
	
	public static Map<Integer, Integer> getEquipAb(String str)
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
		String[] strs = str.split(";");
		for(String temp : strs)
		{
			String[] x = temp.split(",");
			int equipId = Integer.parseInt(x[0]);
			int lv = Integer.parseInt(x[1]);
			EquipConfig equipConfig = EquipConfigCache.getEquipConfigById(equipId);
			if(equipConfig == null) continue;
			
			atk += equipConfig.getAtk() + equipConfig.getAtkStrongValue() * lv;
			zatk += equipConfig.getZatk() + equipConfig.getZatkStrongValue() * lv;
			catk += equipConfig.getCatk() + equipConfig.getCatkStrongValue()* lv;
			def += equipConfig.getDef() + equipConfig.getDefStrongValue() * lv;
			zdef += equipConfig.getZdef() + equipConfig.getZdefStrongValue() * lv;
			cdef += equipConfig.getCdef() + equipConfig.getCatkStrongValue() * lv;
//			addHurt += equipConfig.getAddHurt();
//			rmHurt += equipConfig.getRmHurt();
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
		
		return abMap;
	}
}
