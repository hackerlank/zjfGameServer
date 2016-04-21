package byCodeGame.game.moudle.babel.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.bycode.game.battle.core.Battle;
import cn.bycode.game.battle.core.Config;
import cn.bycode.game.battle.data.CorpData;
import cn.bycode.game.battle.data.ResultData;
import cn.bycode.game.battle.data.TroopData;
import byCodeGame.game.cache.file.ChapterArmsConfigCache;
import byCodeGame.game.cache.file.ChapterConfigCache;
import byCodeGame.game.cache.file.GeneralNumConstantCache;
import byCodeGame.game.cache.file.TowerConfigCache;
import byCodeGame.game.cache.file.TrialTowerConfigCache;
import byCodeGame.game.cache.file.VipConfigCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.file.ChapterArmsConfig;
import byCodeGame.game.entity.file.ChapterAward;
import byCodeGame.game.entity.file.ChapterConfig;
import byCodeGame.game.entity.file.TowerConfig;
import byCodeGame.game.entity.file.TrialTowerConfig;
import byCodeGame.game.entity.file.VipConfig;
import byCodeGame.game.entity.po.ChapterReward;
import byCodeGame.game.entity.po.Tower;
import byCodeGame.game.moudle.babel.BabelConstant;
import byCodeGame.game.moudle.chapter.service.ChapterService;
import byCodeGame.game.moudle.hero.HeroConstant;
import byCodeGame.game.moudle.role.service.RoleService;
import byCodeGame.game.remote.Message;
import byCodeGame.game.util.HeroUtils;
import byCodeGame.game.util.PVPUitls;
import byCodeGame.game.util.Utils;

public class BabelServiceImpl implements BabelService{

	private RoleService roleService;
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	private ChapterService chapterService;
	public void setChapterService(ChapterService chapterService) {
		this.chapterService = chapterService;
	}
	
	/**
	 * 初始化通天塔
	 */
	public void initTower(Role role) {
		Tower tower = new Tower();
		tower.setRoleId(role.getId());
		tower.setTroopData(new TroopData(role.getName(), role.getId())); 
		role.setTower(tower);
	}
	
	public Message getBabelInfo(Role role) {
		Message message = new Message();
		message.setType(BabelConstant.Action.GET_INFO.getVal());
		Tower tower = role.getTower();
		if(tower == null) return null;
		message.put(tower.getStatus());
		//今日可用次数
		VipConfig config = VipConfigCache.getVipConfigByVipLv(role.getVipLv());
		message.putInt(config.getTowerTimes() - tower.getTime());
		message.putInt(tower.getUseId());
		message.putInt(tower.getProcess());
		message.putInt(tower.getNpcData().size());
		for(TroopData x:tower.getNpcData().values())
		{
			message.putInt(x.getPlayerId());
			message.putString(x.toString());
			message.putInt(x.getFightValue());
		}
		StringBuilder sb = new StringBuilder();
		for(Integer x : tower.getUseHero())
		{
			sb.append(x).append(",");
		}
		message.putString(sb.toString());
		return message;
	}
	
	public Message FightBabel(Role role ,int id ) {
		Message message = new Message();
		message.setType(BabelConstant.Action.FIGHT_BABEL.getVal());
		Tower tower = role.getTower();
		//判断状态
		if(tower == null || tower.getStatus() != BabelConstant.CHOICS_HERO)
		{
			return null;
		}
		if(tower.getProcess() >= tower.getNpcData().size())
		{
			message.putShort(ErrorCode.ALREADY_PASS);
			return message;
		}
		if(tower.getProcess() >= id
				|| tower.getProcess()+1 != id)
		{
			message.putShort(ErrorCode.ERR_ID_BAB);
			return message;
		}
		TroopData roleData = tower.getTroopData();
		TroopData chapterData = tower.getNpcData().get(id);
		//判断troopdata
		boolean flag = false;
		for(int i = 1 ;i <= 9;i++)
		{
			if(roleData.getCorps()[i].getHpCur() > 0)
			{
				flag = true;
				break;
			}
		}
		if(!flag)//阵上无人
		{
			message.putShort(ErrorCode.NO_DATA_CAN_USE);
			return message;
		}
		
		ResultData resultData = new Battle().fightPVP(roleData, chapterData);
		//TODO 根据胜负结果判定
		TrialTowerConfig config = TrialTowerConfigCache.getTrialTowerConfigById(tower.getUseId(), id);
		if(resultData.winCamp == Config.CAMP_LEFT)
		{
			
			tower.setProcess(id);
			//TODO 奖励发放
			Set<ChapterReward> set = Utils.changStrToAward(config.getAward());
			
			String randomReward = config.getRandomReward();
			String[] rewards = randomReward.split(";");
			StringBuilder sb = new StringBuilder();
			for(String reward:rewards){
				String[] r = reward.split(",");
				int rand = Integer.parseInt(r[0]);
				int v = Utils.getRandomNum(10000);
				if(v<rand){
					sb.append(r[1]).append(r[2]).append(r[3]).append(";");
				}
			}
			set.addAll(Utils.changStrToAward(sb.toString()));
			chapterService.getAward(role, set);
		}
		
		//判定阵型中死亡的武将
		this.clearDeadData(roleData);
		
		message.putShort(ErrorCode.SUCCESS);
		message.putString(resultData.uuid);
		message.putString(resultData.time);
		message.putInt(resultData.winCamp);
		message.putInt(resultData.attPlayerId);
		message.putInt(resultData.defPlayerId);
		message.putString(resultData.attName);
		message.putString(resultData.defName);
		message.putString(resultData.report);
		message.putInt(resultData.attStars);
		message.putInt(resultData.attLost);
		message.putInt(resultData.defStars);
		message.putInt(resultData.defLost);
		message.putInt(resultData.fRound);
		return message;
	}
	
	public Message choiceBabelNpc(Role role, int userId ,String userHero) {
		Message message = new Message();
		message.setType(BabelConstant.Action.CHOICE_USERID.getVal());
		Tower tower = role.getTower();
		//过滤状态
		if(tower.getStatus() != BabelConstant.NEW_STATUS)
		{
			message.putShort(ErrorCode.NO_ACCEPT);
			return message;
		}
		// 过滤等级
		TowerConfig config = TowerConfigCache.getTowerConfigById(userId);
		if(config == null)
		{
			message.putShort(ErrorCode.NO_ACCEPT);
			return message;
		}
		if(role.getLv() < config.getLv())
		{
			message.putShort(ErrorCode.NO_LV);
			return message;
		}
		// 过滤武将信息
		String[] temp = userHero.split(",");
		if(temp.length > GeneralNumConstantCache.getValue("MAX_USE_HERO_NUM"))
		{
			message.putShort(ErrorCode.ERR_MAX_NUM_HERO);
			return message;
		}
		
		tower.getUseHero().clear();
		for(String x : temp)
		{
			tower.getUseHero().add(Integer.parseInt(x));
		}
		//调用方法
		this.initBabelNpc(role, userId);
		this.initRoleData(role);
		message.putShort(ErrorCode.SUCCESS);
		message.putInt(tower.getProcess());
		message.putInt(tower.getNpcData().size());
		for(TroopData x:tower.getNpcData().values())
		{
			message.putInt(x.getPlayerId());
			message.putString(x.toString());
			message.putInt(x.getFightValue());
		}
		return message;
	}

	/**
	 * 更改阵型
	 */
	public Message changeTroopData(Role role,int local, int heroId) {
		Message message = new Message();
		message.setType(BabelConstant.Action.CHANGE_TROOP.getVal());
		Tower tower = role.getTower();
		
		if(tower == null) return null;
		if(local > 9 || local < 1)
		{
			return null;
		}
		
		CorpData corpData = tower.getUseData().get(heroId);
		if(heroId != HeroConstant.RM_HERO 
				&& corpData.getHpCur() <= 0)
		{
			message.putShort(ErrorCode.ERR_CORP_DATA_ID);
			return message;
		}
		TroopData troopData = tower.getTroopData();
		//更改当前阵型
		if(heroId == HeroConstant.RM_HERO)
		{
			troopData.getCorps()[local] = new CorpData();
		}else {
			//判定 人数
			int size = 0;
			int index = 0;
			for(int i=1;i < troopData.getCorps().length;i++)
			{
				CorpData x = troopData.getCorps()[i];
				if(x.getHpCur() > 0 && x.generalID != heroId)
				{
					size++;
				}
				if(x.generalID == heroId)
				{
					index = i;
					size--;
				}
			}
			if(size + 1 > 5
					&&  troopData.getCorps()[local].generalID == 0)
			{
				message.putShort(ErrorCode.MAX_SIZE_TROOP);
				return message;
			}
			if(index != 0)
			{
				troopData.getCorps()[index] = troopData.getCorps()[local];
			}
				troopData.getCorps()[local] = tower.getUseData().get(heroId);
		}
		
		message.putShort(ErrorCode.SUCCESS);
		//TODO 通知客户端位置
		return message;
	}
	
	
	/**
	 * 获取当前武将信息
	 */
	public Message getHeroInfo(Role role) {
		Message message = new Message();
		message.setType(BabelConstant.Action.GET_HERO_INFO.getVal());
		
		Tower tower = role.getTower();
		if (tower == null) {
			return null;
		}
		
		//当前的英雄数量
		message.putInt(tower.getUseData().size());
		for(CorpData x :tower.getUseData().values())
		{
			message.putInt(x.generalID);
			message.putInt(x.getHpCur());
			message.putInt(x.hpMax);
			message.putInt(x.armyID);
			message.putInt(x.getFightValue());
		}
		message.putString(tower.getTroopData().toString());
		message.putInt(this.checkGoldRe(tower));
		return message;
	}
	
	/**
	 * 复活部队
	 */
	public Message reviveHero(Role role, int heroId) {
		Message message = new Message();
		message.setType(BabelConstant.Action.REVIVE_HERO.getVal());
		Tower tower = role.getTower();
		if(tower == null) return null;
		//检查部队状态
		CorpData corpData = tower.getUseData().get(heroId);
		if(corpData == null) return null;
		if(corpData.getHpCur() > BabelConstant.NO_PROCESS)
		{
			message.putShort(ErrorCode.ERR_REVIVE_HERO);
			return message;
		}
		//检查金币
		int needGold = this.checkGoldRe(tower);
		if(role.getGold() < needGold)
		{
			message.putShort(ErrorCode.NO_GOLD);
			return message;
		}
		this.roleService.addRoleGold(role, -needGold);
		
		tower.setReviveTime(tower.getReviveTime()+BabelConstant.CHOICS_USERID);
		corpData.setHpCur(corpData.hpMax);
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	/**
	 * 重置通天塔
	 * 
	 */
	public Message resetTower(Role role) {
		Message message = new Message();
		message.setType(BabelConstant.Action.RESET_TOWER.getVal());
		Tower tower = role.getTower();
		
		//TODO 检查次数
		
		//重置流程
		tower.getUseData().clear();
		tower.getUseHero().clear();
		tower.getNpcData().clear();
		tower.setTroopData(null);
		tower.setTroopData(new TroopData(role.getName(),role.getId()));
		tower.setUseId(BabelConstant.NO_PROCESS);
//		tower.setTime(tower.getTime() + BabelConstant.CHOICS_USERID);
		tower.setProcess(BabelConstant.NO_PROCESS);
		tower.setStatus(BabelConstant.NEW_STATUS);
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	
	/**
	 * 根据所选userId完成NPC部队生成
	 * 只有在选定userID后调用
	 */
	private void initBabelNpc(Role role ,int userId) {
		//更具ID 取出map
		Map<Integer, TrialTowerConfig> useMap = TrialTowerConfigCache.getMap(userId);
		if(useMap == null) return;
		
		Tower tower = role.getTower();
		tower.setUseId(userId);
		tower.setProcess(BabelConstant.NO_PROCESS);
		tower.setLastProcess(BabelConstant.NO_PROCESS);
		tower.getNpcData().clear();
		for(TrialTowerConfig x : useMap.values())
		{
			tower.getNpcData().put(x.getProcess(), PVPUitls.getTroopDataByTrialConfig(x));
		}
		tower.setStatus(BabelConstant.CHOICS_USERID);
	}
	
	/**
	 * 根据所选英雄完成本次可用武将部队数据生成
	 * 只有当选定武将后调用
	 * @param role
	 */
	private void initRoleData(Role role)
	{
		Tower tower = role.getTower();
		if(tower == null) return;
		tower.getUseData().clear();
		for(Integer x :tower.getUseHero())
		{
			Hero hero = role.getHeroMap().get(x); 
			if(x == null) continue; 
			tower.getUseData().put(x, PVPUitls.getCorpDataByHero(role, hero));
		}
		tower.setStatus(BabelConstant.CHOICS_HERO);
		tower.setTime(tower.getTime() + BabelConstant.CHOICS_USERID);
	}
	
	/**
	 * 清除已经阵亡的部队信息
	 * @param data
	 */
	private void clearDeadData(TroopData data)
	{
		List<Integer> temp = new ArrayList<Integer>();
		for(int i = 1 ; i <= 9 ; i++)
		{
			if(data.getCorps()[i].getHpCur() <= 0)
			{
				temp.add(i);
			}
		}
		
		for(int i : temp)
		{
			data.getCorps()[i] = new CorpData();
		}
	}
	
	private int checkGoldRe(Tower tower)
	{
		int baseCost = GeneralNumConstantCache.getValue("BASE_COST_REVIVE");
		int cost = (tower.getReviveTime()+1) * baseCost;
		int maxCost = GeneralNumConstantCache.getValue("MAX_COST_REVIVE");
		return cost > maxCost ? maxCost: cost;
	}
	
	@Override
	public void initTrialConfig() {
		List<TrialTowerConfig> list = TrialTowerConfigCache.getAllTrialTowerConfigList();
		for (TrialTowerConfig config : list) {
			int advisableFightValue = 0;
			Map<Integer, String> troopsMap = config.getTroops();
			for (String troops : troopsMap.values()) {
				if (troops.equals("0"))
					continue;

				ChapterArmsConfig cac = ChapterArmsConfigCache.getChapterArmsConfig(troops);
				int fightValue = HeroUtils.getHeroFightValue(cac.getHp(), cac.getGeneralAttack(), cac.getPowerAttack(),
						cac.getMagicalAttack(), cac.getGeneralDefence(), cac.getPowerDefence(),
						cac.getMagicalDefence(), cac.getCaptain(), cac.getPower(), cac.getIntel());
				advisableFightValue += fightValue;
			}
			config.setFightValue(advisableFightValue);
		}
	}
}
