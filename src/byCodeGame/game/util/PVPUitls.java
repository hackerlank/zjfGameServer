package byCodeGame.game.util;

import java.util.Map;
import java.util.Map.Entry;

import byCodeGame.game.cache.file.ArmsConfigCache;
import byCodeGame.game.cache.file.BattleDepreciationCache;
import byCodeGame.game.cache.file.ChapterArmsConfigCache;
import byCodeGame.game.cache.file.ChapterConfigCache;
import byCodeGame.game.cache.file.CountryDepreciationCache;
import byCodeGame.game.cache.file.HeroConfigCache;
import byCodeGame.game.cache.file.RankConfigCache;
import byCodeGame.game.cache.file.RoleScienceConfigCache;
import byCodeGame.game.cache.file.TrialTowerConfigCache;
import byCodeGame.game.cache.file.WorldWarArmsConfigCache;
import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.file.ArmsConfig;
import byCodeGame.game.entity.file.BattleDepreciation;
import byCodeGame.game.entity.file.ChapterArmsConfig;
import byCodeGame.game.entity.file.ChapterConfig;
import byCodeGame.game.entity.file.CountryDepreciation;
import byCodeGame.game.entity.file.HeroConfig;
import byCodeGame.game.entity.file.RankConfig;
import byCodeGame.game.entity.file.RoleScienceConfig;
import byCodeGame.game.entity.file.TrialTowerConfig;
import byCodeGame.game.entity.file.WorldWarArmsConfig;
import byCodeGame.game.entity.po.WorldArmy;
import cn.bycode.game.battle.core.Battle;
import cn.bycode.game.battle.data.CorpData;
import cn.bycode.game.battle.data.FormationData;
import cn.bycode.game.battle.data.ResultData;
import cn.bycode.game.battle.data.TroopData;

public class PVPUitls {
	/**
	 * PVP对战初始化   1为 左方   2为右方
	 * @param role1
	 * @param role2
	 */
	public static ResultData getPVP(Role role1,Role role2){
			TroopData data1=PVPUitls.getTroopDataByRole(role1);
			TroopData data2=PVPUitls.getTroopDataByRole(role2);
			Battle battle = new Battle();
			ResultData result = battle.fightPVP(data1, data2);
			return result; 
	}
	public static TroopData getTroopDataByRole(Role role){
		Map<Byte, Integer> map1=role.getFormationData().get(role.getUseFormationID()).getData();//role1阵型信息详情
		TroopData data = new TroopData();
		data.setPlayerName(role.getName());
		data.setPlayerId(role.getId());
		FormationData fmt = new FormationData();
		RankConfig config = RankConfigCache.getRankConfigById(role.getRank());
		for(Entry<Integer, Integer> x : role.getRoleScienceMap().entrySet())
		{
			RoleScienceConfig roleScienceConfig = RoleScienceConfigCache.getRoleScienceConfig(x.getKey(), x.getValue());
			if(roleScienceConfig == null) continue;
			fmt.atk1 +=roleScienceConfig.getAtk();
			fmt.atk2 +=roleScienceConfig.getZatk();
			fmt.atk3 +=roleScienceConfig.getJatk();;
			fmt.def1 +=roleScienceConfig.getDef();
			fmt.def2 +=roleScienceConfig.getZdef();
			fmt.def3 +=roleScienceConfig.getJdef();
		}
		fmt.name = "已取消";  
		data.setFormation(fmt);
		CorpData[] corps = new CorpData[10];
		for (int i = 1; i <= 9; i++) {
			corps[i] = new CorpData();
			corps[i].hpMax = 0;
			corps[i].setHpCur(0);
		}
		for (Map.Entry<Byte, Integer> map : map1.entrySet()) {
			if(map.getValue()>2){
				corps[map.getKey()] = new CorpData();
   				int heroId=role.getHeroMap().get(map.getValue()).getHeroId();
				Hero hero=role.getHeroMap().get(map.getValue());
				HeroConfig heroConfig=HeroConfigCache.getHeroConfigById(heroId);
				ArmsConfig armsConfig=ArmsConfigCache.getArmsConfigById(hero.getUseArmyId());
				corps[map.getKey()].generalID = heroConfig.getImgId();
				corps[map.getKey()].generalName = HeroConfigCache.getHeroConfigById(heroId).getName();
				corps[map.getKey()].skillId = hero.getSkillId();
				corps[map.getKey()].skillLv = hero.getSkillLv();
				corps[map.getKey()].generalLevel = hero.getLv();
				corps[map.getKey()].generalLea = HeroUtils.getCaptainValue(hero);
				corps[map.getKey()].generalStr = HeroUtils.getPowerValue(hero);
				corps[map.getKey()].generalInt = HeroUtils.getIntelValue(hero);
				corps[map.getKey()].armyID = hero.getUseArmyId();
				corps[map.getKey()].armyAtk1Rate=armsConfig.getAtkRate();
//				corps[map.getKey()].armyDef1Rate=RATE_1/(RATE_1 + armsConfig.getDefRate());
				corps[map.getKey()].armyDef1Rate=armsConfig.getDefRate();
				corps[map.getKey()].armyAtk2Rate=armsConfig.getTacticsAtkRate();
//				corps[map.getKey()].armyDef2Rate=RATE_1/(RATE_1 + armsConfig.getTacticsDefRate());
				corps[map.getKey()].armyDef2Rate=armsConfig.getTacticsDefRate();
				corps[map.getKey()].armyAtk3Rate=armsConfig.getTrickAtkRate();
//				corps[map.getKey()].armyDef3Rate=RATE_1/(RATE_1 + armsConfig.getTrickDefRate());
				corps[map.getKey()].armyDef3Rate=armsConfig.getTrickDefRate();
   				Map<Integer, Integer> abMap = HeroUtils.getEquipAb(role, hero);
				corps[map.getKey()].hpMax=hero.getArmsNum();
				corps[map.getKey()].hpStart=hero.getArmsNum();
				corps[map.getKey()].setHpCur(hero.getArmsNum());
				//corps[map.getKey()].energy=0;
				corps[map.getKey()].atk1= hero.getGeneralAttrck() * armsConfig.getAtkRate(); //+ abMap.get(HeroUtils.ATK_NUM);
				corps[map.getKey()].atk1 += fmt.atk1;
				corps[map.getKey()].def1=hero.getGeneralDefence() * armsConfig.getDefRate();// + abMap.get(HeroUtils.DEF_NUM);
				corps[map.getKey()].def1 += fmt.def1;
				corps[map.getKey()].atk2=hero.getPowerAttack() * armsConfig.getTacticsAtkRate(); // + abMap.get(HeroUtils.ZATK_NUM);
				corps[map.getKey()].atk2 += fmt.atk2;
				corps[map.getKey()].def2=hero.getPowerDefence() * armsConfig.getTacticsDefRate();// + abMap.get(HeroUtils.ZDEF_NUM);
				corps[map.getKey()].def2 += fmt.def2;
				corps[map.getKey()].StgAtt=hero.getMagicalAttack() * armsConfig.getTrickAtkRate();// + abMap.get(HeroUtils.CATK_NUM);
				corps[map.getKey()].StgAtt += fmt.atk3;
				corps[map.getKey()].StgDef=hero.getMagicalDefence() * armsConfig.getTrickDefRate();// + abMap.get(HeroUtils.CDEF_NUM);
				corps[map.getKey()].StgDef += fmt.def3;
				//计算增加伤害
				corps[map.getKey()].AllDamADD = config.getAddHurt();//abMap.get(HeroUtils.ADDHURT_NUM);
//				corps[map.getKey()].AllDamRM = abMap.get(HeroUtils.RMHURT_NUM);
				corps[map.getKey()].troopType = armsConfig.getFunctionType();
				corps[map.getKey()].atkType = armsConfig.getAttackType();
				corps[map.getKey()].skillID2 = armsConfig.getArmSkillID();
				corps[map.getKey()].skillID2_lv = role.getBarrack().getArmySkillLvById(armsConfig.getArmSkillID());
			}
		}
		data.setCorps(corps);	
		return data;
	}
	/***
	 * 更具关卡ID转换成战斗数据
	 * @param id
	 * @return
	 */
	public static TroopData getTroopDataByChapterId(int id){
		TroopData data = new TroopData();
		ChapterConfig cc=ChapterConfigCache.getChapterConfigById(id);
		data.setPlayerName(cc.getTitle());
		data.setPlayerId(cc.getId());
		FormationData fmt = new FormationData();
		fmt.name = "已取消";  
		data.setFormation(fmt);
		CorpData[] corps = new CorpData[10];
		for (int i = 1; i <= 9; i++) {
			corps[i] = new CorpData();
			corps[i].hpMax = 0;
			corps[i].setHpCur(0);
		}
		for (Map.Entry<Integer, String> map : cc.getTroops().entrySet()) {
			if(!map.getValue().equals("0")
					&& !map.getValue().equals("0;")){
				corps[map.getKey()] = new CorpData();
				System.out.println(map.getValue());
				System.err.println("鲜肉是戳男，又错了");
				ChapterArmsConfig cac=ChapterArmsConfigCache.getChapterArmsConfig(map.getValue());
				ArmsConfig armsConfig = ArmsConfigCache.getArmsConfigById(cac.getArmyId());
				
				int heroId=cac.getHeroId();
				//Hero hero=role.getHeroMap().get(map.getValue());
				HeroConfig heroConfig=HeroConfigCache.getHeroConfigById(heroId);
				corps[map.getKey()].generalID = heroConfig.getImgId();
				corps[map.getKey()].generalName = HeroConfigCache.getHeroConfigById(heroId).getName();
				corps[map.getKey()].skillId = heroConfig.getSkillId();
				corps[map.getKey()].skillLv = cac.getArmySkillLv();
//				corps[map.getKey()].generalLevel = cac.getLv();
				corps[map.getKey()].generalLea = cac.getCaptain();
//						heroConfig.getCaptain()
//						+ ((int) (heroConfig.getCaptainGrowValue()) * cac.getRank());
				corps[map.getKey()].generalStr = cac.getPower();
//						heroConfig.getPower()
//						+ ((int) (heroConfig.getPowerGrowValue()) * cac.getRank());
				corps[map.getKey()].generalInt = cac.getIntel();
//						heroConfig.getIntel()
//						+ ((int) (heroConfig.getIntelGrowValue()) * cac.getRank());
				corps[map.getKey()].armyID = cac.getArmyId();
//				corps[map.getKey()].armyAtk1Rate= PVPUitls.getAtkRate(cac.getAtkRate(), armsConfig.getAtkRate());
//				corps[map.getKey()].armyDef1Rate= PVPUitls.getDefRate(cac.getGeneralDefence(), armsConfig.getDefRate());
//				corps[map.getKey()].armyAtk2Rate= PVPUitls.getAtkRate(cac.getTacticsAtkRate() , armsConfig.getTacticsAtkRate());
//				corps[map.getKey()].armyDef2Rate= PVPUitls.getDefRate(cac.getTacticsDefRate(), armsConfig.getTacticsDefRate());
//				corps[map.getKey()].armyAtk3Rate= PVPUitls.getAtkRate(cac.getTrickAtkRate() , armsConfig.getTrickAtkRate());
//				corps[map.getKey()].armyDef3Rate= PVPUitls.getDefRate(cac.getMagicalDefence() , armsConfig.getTrickDefRate());
				int hpMax=cac.getHp();
				corps[map.getKey()].hpMax=hpMax;
				corps[map.getKey()].hpStart=hpMax;
				corps[map.getKey()].setHpCur(hpMax);
				//corps[map.getKey()].energy=0;
				corps[map.getKey()].atk1=cac.getGeneralAttack();
				corps[map.getKey()].def1=cac.getGeneralDefence();
				corps[map.getKey()].atk2=cac.getPowerAttack();
				corps[map.getKey()].def2=cac.getPowerDefence();
				corps[map.getKey()].StgAtt=cac.getMagicalAttack();
				corps[map.getKey()].StgDef=cac.getMagicalDefence();
				corps[map.getKey()].troopType = armsConfig.getFunctionType();
				corps[map.getKey()].atkType = armsConfig.getAttackType();
				corps[map.getKey()].skillID2 = armsConfig.getArmSkillID();
				corps[map.getKey()].skillID2_lv = cac.getArmySkillLv();
			}
		}
		data.setCorps(corps);	
		return data;
	}
	
	/**
	 * 更具通天塔ID转换成战斗数据
	 * @param id
	 * @return
	 */
	public static TroopData getTroopDataByBabelId(int id ,int process)
	{
		TroopData data = new TroopData();
		TrialTowerConfig tt = TrialTowerConfigCache.getTrialTowerConfigById(id,process);
		data.setPlayerName(tt.getTitle());
		data.setPlayerId(tt.getProcess());
		FormationData fmt = new FormationData();
		fmt.name = "已取消";  
		data.setFormation(fmt);
		
		CorpData[] corps = new CorpData[10];
		for (int i = 1; i <= 9; i++) {
			corps[i] = new CorpData();
			corps[i].hpMax = 0;
			corps[i].setHpCur(0);
		}
		for (Map.Entry<Integer, String> map : tt.getTroops().entrySet()) {
			if(!map.getValue().equals("0")){
				corps[map.getKey()] = new CorpData();
				ChapterArmsConfig cac=ChapterArmsConfigCache.getChapterArmsConfig(map.getValue());
				ArmsConfig armsConfig = ArmsConfigCache.getArmsConfigById(cac.getArmyId());
				int heroId=cac.getHeroId();
				//Hero hero=role.getHeroMap().get(map.getValue());
				HeroConfig heroConfig=HeroConfigCache.getHeroConfigById(heroId);
				corps[map.getKey()].generalID = heroConfig.getImgId();
				 corps[map.getKey()].generalName = HeroConfigCache.getHeroConfigById(heroId).getName();
				corps[map.getKey()].skillId = heroConfig.getSkillId();
				corps[map.getKey()].skillLv = cac.getArmySkillLv();
//				corps[map.getKey()].generalLevel = cac.getArmySkillLv();
				corps[map.getKey()].generalLea = heroConfig.getCaptain()
						+ ((int) (heroConfig.getCaptainGrowValue()) * cac.getRank());
				corps[map.getKey()].generalStr = heroConfig.getPower()
						+ ((int) (heroConfig.getPowerGrowValue()) * cac.getRank());
				corps[map.getKey()].generalInt =heroConfig.getIntel()
						+ ((int) (heroConfig.getIntelGrowValue()) * cac.getRank());
				corps[map.getKey()].armyID = cac.getArmyId();
//				corps[map.getKey()].armyAtk1Rate= PVPUitls.getAtkRate(cac.getAtkRate(), armsConfig.getAtkRate());
//				corps[map.getKey()].armyDef1Rate= PVPUitls.getDefRate(cac.getGeneralDefence(), armsConfig.getDefRate());
//				corps[map.getKey()].armyAtk2Rate= PVPUitls.getAtkRate(cac.getTacticsAtkRate() , armsConfig.getTacticsAtkRate());
//				corps[map.getKey()].armyDef2Rate= PVPUitls.getDefRate(cac.getTacticsDefRate(), armsConfig.getTacticsDefRate());
//				corps[map.getKey()].armyAtk3Rate= PVPUitls.getAtkRate(cac.getTrickAtkRate() , armsConfig.getTrickAtkRate());
//				corps[map.getKey()].armyDef3Rate= PVPUitls.getDefRate(cac.getMagicalDefence() , armsConfig.getTrickDefRate());
				int hpMax=cac.getHp();
				corps[map.getKey()].hpMax=hpMax;
				corps[map.getKey()].hpStart=hpMax;
				corps[map.getKey()].setHpCur(hpMax);
				//corps[map.getKey()].energy=0;
				corps[map.getKey()].atk1=cac.getGeneralAttack();
				corps[map.getKey()].def1=cac.getGeneralDefence();
				corps[map.getKey()].atk2=cac.getPowerAttack();
				corps[map.getKey()].def2=cac.getPowerDefence();
				corps[map.getKey()].StgAtt=cac.getMagicalAttack();
				corps[map.getKey()].StgDef=cac.getMagicalDefence();
				corps[map.getKey()].troopType = armsConfig.getFunctionType();
				corps[map.getKey()].skillID2 = armsConfig.getArmSkillID();
				corps[map.getKey()].skillID2_lv = cac.getArmySkillLv();
			}
		}
		data.setCorps(corps);	
		return data;
	}
	
	/**
	 * 根据通天塔配置表获取NPC部队 新
	 * @param tt
	 * @return
	 */
	public static TroopData getTroopDataByTrialConfig(TrialTowerConfig tt)
	{
		TroopData data = new TroopData();
		data.setPlayerName(tt.getTitle());
		data.setPlayerId(tt.getProcess());
		FormationData fmt = new FormationData();
		fmt.name = "已取消";  
		data.setFormation(fmt);
		data.setFightValue(tt.getFightValue());
		
		CorpData[] corps = new CorpData[10];
		for (int i = 1; i <= 9; i++) {
			corps[i] = new CorpData();
			corps[i].hpMax = 0;
			corps[i].setHpCur(0);
		}
		for (Map.Entry<Integer, String> map : tt.getTroops().entrySet()) {
			if(!map.getValue().equals("0")){
				corps[map.getKey()] = new CorpData();
				ChapterArmsConfig cac=ChapterArmsConfigCache.getChapterArmsConfig(map.getValue());
				ArmsConfig armsConfig = ArmsConfigCache.getArmsConfigById(cac.getArmyId());
				int heroId=cac.getHeroId();
				//Hero hero=role.getHeroMap().get(map.getValue());
				HeroConfig heroConfig=HeroConfigCache.getHeroConfigById(heroId);
				corps[map.getKey()].generalID = heroConfig.getImgId();
				 corps[map.getKey()].generalName = HeroConfigCache.getHeroConfigById(heroId).getName();
				corps[map.getKey()].skillId = heroConfig.getSkillId();
				corps[map.getKey()].skillLv = cac.getArmySkillLv();
//				corps[map.getKey()].generalLevel = cac.getArmySkillLv();
				corps[map.getKey()].generalLea = heroConfig.getCaptain()
						+ ((int) (heroConfig.getCaptainGrowValue()) * cac.getRank());
				corps[map.getKey()].generalStr = heroConfig.getPower()
						+ ((int) (heroConfig.getPowerGrowValue()) * cac.getRank());
				corps[map.getKey()].generalInt =heroConfig.getIntel()
						+ ((int) (heroConfig.getIntelGrowValue()) * cac.getRank());
				corps[map.getKey()].armyID = cac.getArmyId();
//				corps[map.getKey()].armyAtk1Rate= PVPUitls.getAtkRate(cac.getAtkRate(), armsConfig.getAtkRate());
//				corps[map.getKey()].armyDef1Rate= PVPUitls.getDefRate(cac.getGeneralDefence(), armsConfig.getDefRate());
//				corps[map.getKey()].armyAtk2Rate= PVPUitls.getAtkRate(cac.getTacticsAtkRate() , armsConfig.getTacticsAtkRate());
//				corps[map.getKey()].armyDef2Rate= PVPUitls.getDefRate(cac.getTacticsDefRate(), armsConfig.getTacticsDefRate());
//				corps[map.getKey()].armyAtk3Rate= PVPUitls.getAtkRate(cac.getTrickAtkRate() , armsConfig.getTrickAtkRate());
//				corps[map.getKey()].armyDef3Rate= PVPUitls.getDefRate(cac.getMagicalDefence() , armsConfig.getTrickDefRate());
				int hpMax=cac.getHp();
				corps[map.getKey()].hpMax=hpMax;
				corps[map.getKey()].hpStart=hpMax;
				corps[map.getKey()].setHpCur(hpMax);
				//corps[map.getKey()].energy=0;
				corps[map.getKey()].atk1=cac.getGeneralAttack();
				corps[map.getKey()].def1=cac.getGeneralDefence();
				corps[map.getKey()].atk2=cac.getPowerAttack();
				corps[map.getKey()].def2=cac.getPowerDefence();
				corps[map.getKey()].StgAtt=cac.getMagicalAttack();
				corps[map.getKey()].StgDef=cac.getMagicalDefence();
				corps[map.getKey()].troopType = armsConfig.getFunctionType();
				corps[map.getKey()].atkType = armsConfig.getAttackType();
				corps[map.getKey()].skillID2 = armsConfig.getArmSkillID();
				corps[map.getKey()].skillID2_lv = cac.getArmySkillLv();
			}
		}
		data.setCorps(corps);	
		return data;
	}
	
	/**
	 * 获取世界大战战斗信息
	 * @param worldArmy
	 * @return
	 * @author xjd
	 */
	public static TroopData getTroopDataByWorldArmy(WorldArmy worldArmy)
	{
		Map<Byte, Integer> map1=worldArmy.getFormationData().getData();//role1阵型信息详情
		TroopData data = new TroopData();
		Role role = RoleCache.getRoleById(worldArmy.getRoleId());
		data.setPlayerName(role.getName());
		data.setPlayerId(role.getId());
		FormationData fmt = new FormationData();
		for(Entry<Integer, Integer> x : role.getRoleScienceMap().entrySet())
		{
			RoleScienceConfig roleScienceConfig = RoleScienceConfigCache.getRoleScienceConfig(x.getKey(), x.getValue());
			if(roleScienceConfig == null) continue;
			fmt.atk1 +=roleScienceConfig.getAtk();
			fmt.atk2 +=roleScienceConfig.getZatk();
			fmt.atk3 +=roleScienceConfig.getJatk();;
			fmt.def1 +=roleScienceConfig.getDef();
			fmt.def2 +=roleScienceConfig.getZdef();
			fmt.def3 +=roleScienceConfig.getJdef();
		}
		fmt.name = "已取消";  
		data.setFormation(fmt);
		CorpData[] corps = new CorpData[10];
		for (int i = 1; i <= 9; i++) {
			corps[i] = new CorpData();
			corps[i].hpMax = 0;
			corps[i].setHpCur(0);
		}
		
		for (Map.Entry<Byte, Integer> map : map1.entrySet()) {
			if(map.getValue()>2){
				corps[map.getKey()] = new CorpData();
   				int heroId=role.getHeroMap().get(map.getValue()).getHeroId();
				Hero hero=role.getHeroMap().get(map.getValue());
				HeroConfig heroConfig=HeroConfigCache.getHeroConfigById(heroId);
				ArmsConfig armsConfig=ArmsConfigCache.getArmsConfigById(hero.getUseArmyId());
				corps[map.getKey()].generalID = heroConfig.getImgId();
				corps[map.getKey()].generalName = HeroConfigCache.getHeroConfigById(heroId).getName();
				corps[map.getKey()].skillId = hero.getSkillId();
				corps[map.getKey()].skillLv = hero.getSkillLv();
				corps[map.getKey()].generalLevel = hero.getLv();
				corps[map.getKey()].generalLea = HeroUtils.getCaptainValue(hero);
				corps[map.getKey()].generalStr = HeroUtils.getPowerValue(hero);
				corps[map.getKey()].generalInt = HeroUtils.getIntelValue(hero);
				corps[map.getKey()].armyID = hero.getUseArmyId();
				corps[map.getKey()].armyAtk1Rate=armsConfig.getAtkRate();
				corps[map.getKey()].armyDef1Rate=RATE_1/(RATE_1 + armsConfig.getDefRate());
				corps[map.getKey()].armyAtk2Rate=armsConfig.getTacticsAtkRate();
				corps[map.getKey()].armyDef2Rate=RATE_1/(RATE_1 + armsConfig.getTacticsDefRate());
				corps[map.getKey()].armyAtk3Rate=armsConfig.getTrickAtkRate();
				corps[map.getKey()].armyDef3Rate=RATE_1/(RATE_1 + armsConfig.getTrickDefRate());
   				Map<Integer, Integer> abMap = HeroUtils.getEquipAb(role, hero);
				corps[map.getKey()].hpMax=hero.getArmsNum();
				corps[map.getKey()].hpStart=hero.getArmsNum();
				corps[map.getKey()].setHpCur(hero.getArmsNum());
				//corps[map.getKey()].energy=0;
				corps[map.getKey()].atk1= hero.getGeneralAttrck(); //+ abMap.get(HeroUtils.ATK_NUM);
				corps[map.getKey()].atk1 += fmt.atk1;
				corps[map.getKey()].def1=hero.getGeneralDefence();// + abMap.get(HeroUtils.DEF_NUM);
				corps[map.getKey()].def1 += fmt.def1;
				corps[map.getKey()].atk2=hero.getPowerAttack(); // + abMap.get(HeroUtils.ZATK_NUM);
				corps[map.getKey()].atk2 += fmt.atk2;
				corps[map.getKey()].def2=hero.getPowerDefence();// + abMap.get(HeroUtils.ZDEF_NUM);
				corps[map.getKey()].def2 += fmt.def2;
				corps[map.getKey()].StgAtt=hero.getMagicalAttack();// + abMap.get(HeroUtils.CATK_NUM);
				corps[map.getKey()].StgAtt += fmt.atk3;
				corps[map.getKey()].StgDef=hero.getMagicalDefence();// + abMap.get(HeroUtils.CDEF_NUM);
				corps[map.getKey()].StgDef += fmt.def3;
				corps[map.getKey()].AllDamADD = abMap.get(HeroUtils.ADDHURT_NUM);
				corps[map.getKey()].AllDamRM = abMap.get(HeroUtils.RMHURT_NUM);
				corps[map.getKey()].troopType = armsConfig.getFunctionType();
				corps[map.getKey()].atkType = armsConfig.getAttackType();
				corps[map.getKey()].skillID2 = armsConfig.getArmSkillID();
				corps[map.getKey()].skillID2_lv = role.getBarrack().getArmySkillLvById(armsConfig.getArmSkillID());
			}
		}
		data.setCorps(corps);	
		return data;
		
	}
	
	public static TroopData getTroopDataByWorldArmyNPC(WorldArmy worldArmy)
	{
		Map<Byte, Integer> map1=worldArmy.getFormationData().getData();//role1阵型信息详情
		TroopData data = new TroopData();
		data.setPlayerName("肉肉复制大军");
		data.setPlayerId(-1);
		FormationData fmt = new FormationData();
		fmt.name = "已取消";  
		data.setFormation(fmt);
		CorpData[] corps = new CorpData[10];
		for (int i = 1; i <= 9; i++) {
			corps[i] = new CorpData();
			corps[i].hpMax = 0;
			corps[i].setHpCur(0);
		}
		for (Entry<Byte, Integer> map : map1.entrySet()) {
			if(map.getValue() != 0
					&& !map.getValue().equals("0;")){
				corps[map.getKey()] = new CorpData();
//				System.out.println(map.getValue());
//				System.err.println("鲜肉是戳男，又错了");
//				ChapterArmsConfig cac=ChapterArmsConfigCache.getChapterArmsConfig(map.getValue());
				WorldWarArmsConfig wac = WorldWarArmsConfigCache.getCityGarrisonConfigById(map.getValue());
				ArmsConfig armsConfig = ArmsConfigCache.getArmsConfigById(wac.getArmyId());
				
				int heroId=wac.getImgID();
				corps[map.getKey()].generalID = heroId;
				corps[map.getKey()].generalName = "肉肉复制大军";
				corps[map.getKey()].skillId = wac.getSkillID();
				corps[map.getKey()].skillLv = wac.getArmySkillLv();
//				corps[map.getKey()].generalLevel = cac.getLv();
				corps[map.getKey()].generalLea = wac.getCaptain();
				corps[map.getKey()].generalStr = wac.getPower();
				corps[map.getKey()].generalInt = wac.getIntel();
				corps[map.getKey()].armyID = wac.getArmyId();
				corps[map.getKey()].armyAtk1Rate= 1;//PVPUitls.getAtkRate(wac.getAtkRate(), armsConfig.getAtkRate());
				corps[map.getKey()].armyDef1Rate= 1;//PVPUitls.getDefRate(cac.getGeneralDefence(), armsConfig.getDefRate());
				corps[map.getKey()].armyAtk2Rate= 1;//PVPUitls.getAtkRate(cac.getTacticsAtkRate() , armsConfig.getTacticsAtkRate());
				corps[map.getKey()].armyDef2Rate= 1;//PVPUitls.getDefRate(cac.getTacticsDefRate(), armsConfig.getTacticsDefRate());
				corps[map.getKey()].armyAtk3Rate= 1;//PVPUitls.getAtkRate(cac.getTrickAtkRate() , armsConfig.getTrickAtkRate());
				corps[map.getKey()].armyDef3Rate= 1;//PVPUitls.getDefRate(cac.getMagicalDefence() , armsConfig.getTrickDefRate());
				int hpMax= wac.getHp();
				corps[map.getKey()].hpMax=hpMax;
				corps[map.getKey()].hpStart=hpMax;
				corps[map.getKey()].setHpCur(hpMax);
				//corps[map.getKey()].energy=0;
				corps[map.getKey()].atk1=wac.getGeneralAttack();
				corps[map.getKey()].def1=wac.getGeneralDefence();
				corps[map.getKey()].atk2=wac.getKongfuAttack();
				corps[map.getKey()].def2=wac.getKongfuDefence();
				corps[map.getKey()].StgAtt= wac.getMagicalAttack();
				corps[map.getKey()].StgDef= wac.getMagicalDefence();
				corps[map.getKey()].troopType = armsConfig.getFunctionType();
				corps[map.getKey()].atkType = armsConfig.getAttackType();
				corps[map.getKey()].skillID2 = armsConfig.getArmSkillID();
				corps[map.getKey()].skillID2_lv = wac.getArmySkillLv();
			}
		}
		data.setCorps(corps);	
		return data;
		
	}
	
	/**
	 * 势力占据城市数量 玩家本部队连胜关系
	 * 根据BUFF修改 TroopData的属性
	 * @param data
	 * @param kill
	 * @return
	 */
	public static TroopData getTroopDataByTroopData(TroopData data , int kill ,int cityNum)
	{
		//根据最战胜次数
		BattleDepreciation battleDepreciation = BattleDepreciationCache.getBattleDepreciation(kill);
		CountryDepreciation countryDepreciation = CountryDepreciationCache.getCountryDepreciation(cityNum);
		for(CorpData x :data.getCorps())
		{
			if(x == null)
			{
				continue;
			}
			double hpMax = (double)x.hpMax;
			double d1 = countryDepreciation.getHp();
			double d2 =  battleDepreciation.getHp();
			int timeMax = (int)(hpMax * d1 * d2);
			if(x.getHpCur() > timeMax)
			{
				x.setHpCur(timeMax);
			}else if (x.hpMax < timeMax && x.getHpCur() == x.hpMax) {
				x.setHpCur(timeMax);
			}
			x.atk1 = x.atk1 * battleDepreciation.getGeneralAtk() * countryDepreciation.getGeneralAtk();
			x.def1 = x.def1 * battleDepreciation.getGeneralDef() * countryDepreciation.getGeneralDef();
			x.atk2 = x.atk2 * battleDepreciation.getKongfuAtk() * countryDepreciation.getKongfuAtk();
			x.def2 = x.def2 * battleDepreciation.getKongfuDef() * countryDepreciation.getKongfuDef();
			x.StgAtt = x.StgAtt * battleDepreciation.getMagicAtk() * countryDepreciation.getMagicAtk();
			x.StgDef = x.StgDef * battleDepreciation.getMagicDef() * countryDepreciation.getMagicDef();
		
		}
		
		
		return data;
	}
	
	
	public static CorpData getCorpDataByHero(Role role ,Hero hero)
	{
		//科技加成
		FormationData fmt = new FormationData();
		for(Entry<Integer, Integer> x : role.getRoleScienceMap().entrySet())
		{
			RoleScienceConfig roleScienceConfig = RoleScienceConfigCache.getRoleScienceConfig(x.getKey(), x.getValue());
			if(roleScienceConfig == null) continue;
			fmt.atk1 +=roleScienceConfig.getAtk();
			fmt.atk2 +=roleScienceConfig.getZatk();
			fmt.atk3 +=roleScienceConfig.getJatk();;
			fmt.def1 +=roleScienceConfig.getDef();
			fmt.def2 +=roleScienceConfig.getZdef();
			fmt.def3 +=roleScienceConfig.getJdef();
		}
		fmt.name = "已取消";  
		CorpData corps = new CorpData();
		int heroId = hero.getHeroId();
		HeroConfig heroConfig=HeroConfigCache.getHeroConfigById(heroId);
		ArmsConfig armsConfig=ArmsConfigCache.getArmsConfigById(hero.getUseArmyId());
		corps.generalID = heroConfig.getImgId();
		corps.generalName = HeroConfigCache.getHeroConfigById(heroId).getName();
		corps.skillId = hero.getSkillId();
		corps.skillLv = hero.getSkillLv();
		corps.generalLevel = hero.getLv();
		corps.generalLea = HeroUtils.getCaptainValue(hero);
		corps.generalStr = HeroUtils.getPowerValue(hero);
		corps.generalInt = HeroUtils.getIntelValue(hero);
		corps.armyID = hero.getUseArmyId();
		corps.armyAtk1Rate=armsConfig.getAtkRate();
		corps.armyDef1Rate=RATE_1/(RATE_1 + armsConfig.getDefRate());
		corps.armyAtk2Rate=armsConfig.getTacticsAtkRate();
		corps.armyDef2Rate=RATE_1/(RATE_1 + armsConfig.getTacticsDefRate());
		corps.armyAtk3Rate=armsConfig.getTrickAtkRate();
		corps.armyDef3Rate=RATE_1/(RATE_1 + armsConfig.getTrickDefRate());
			Map<Integer, Integer> abMap = HeroUtils.getEquipAb(role, hero);
		corps.hpMax=hero.getArmsNum();
		corps.hpStart=hero.getArmsNum();
		corps.setHpCur(hero.getArmsNum());
		//corps.energy=0;
		corps.atk1= hero.getGeneralAttrck(); //+ abMap.get(HeroUtils.ATK_NUM);
		corps.atk1 += fmt.atk1;
		corps.def1=hero.getGeneralDefence();// + abMap.get(HeroUtils.DEF_NUM);
		corps.def1 += fmt.def1;
		corps.atk2=hero.getPowerAttack(); // + abMap.get(HeroUtils.ZATK_NUM);
		corps.atk2 += fmt.atk2;
		corps.def2=hero.getPowerDefence();// + abMap.get(HeroUtils.ZDEF_NUM);
		corps.def2 += fmt.def2;
		corps.StgAtt=hero.getMagicalAttack();// + abMap.get(HeroUtils.CATK_NUM);
		corps.StgAtt += fmt.atk3;
		corps.StgDef=hero.getMagicalDefence();// + abMap.get(HeroUtils.CDEF_NUM);
		corps.StgDef += fmt.def3;
		corps.AllDamADD = abMap.get(HeroUtils.ADDHURT_NUM);
		corps.AllDamRM = abMap.get(HeroUtils.RMHURT_NUM);
		corps.troopType = armsConfig.getFunctionType();
		corps.atkType = armsConfig.getAttackType();
		corps.skillID2 = armsConfig.getArmSkillID();
		corps.skillID2_lv = role.getBarrack().getArmySkillLvById(armsConfig.getArmSkillID());
		corps.setFightValue(hero.getFightValue());
		return corps;
	}
	
	private static final double RATE_1 = 10000;
	
	/***
	 * <b>AI专用 获取防御系数<b/>
	 * @param rate1 普通兵种系数
	 * @param rate2	额外系数
	 * @return
	 * @author xjd
	 */
	public static double getDefRate(int rate1 ,int rate2)
	{
		return RATE_1/(rate1+RATE_1) * rate2/RATE_1;
	}
	
	/***
	 * <b>AI专用 获取攻击系数</b>
	 * @param rate1	普通兵种系数
	 * @param rate2	额外系数
	 * @return
	 * @author xjd
	 */
	public static double getAtkRate(double rate1 , double rate2)
	{
		if(rate1 == 0)
		{
			rate1 = 1.0;
		}
		return rate1 * rate2;
	}
}
