package byCodeGame.game.moudle.chapter.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.file.ChapterArmsConfigCache;
import byCodeGame.game.cache.file.ChapterAwardCache;
import byCodeGame.game.cache.file.ChapterConfigCache;
import byCodeGame.game.cache.file.EquipConfigCache;
import byCodeGame.game.cache.file.HeroConfigCache;
import byCodeGame.game.cache.file.VipConfigCache;
import byCodeGame.game.cache.local.SessionCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.db.dao.HeroDao;
import byCodeGame.game.entity.bo.Chapter;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Prop;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.file.ChapterArmsConfig;
import byCodeGame.game.entity.file.ChapterAward;
import byCodeGame.game.entity.file.ChapterConfig;
import byCodeGame.game.entity.file.EquipConfig;
import byCodeGame.game.entity.file.HeroConfig;
import byCodeGame.game.entity.file.VipConfig;
import byCodeGame.game.entity.po.ChapterReward;
import byCodeGame.game.moudle.chapter.ChapterConstant;
import byCodeGame.game.moudle.fight.service.FightService;
import byCodeGame.game.moudle.hero.service.HeroService;
import byCodeGame.game.moudle.mail.service.MailService;
import byCodeGame.game.moudle.prop.service.PropService;
import byCodeGame.game.moudle.role.service.RoleService;
import byCodeGame.game.moudle.task.service.TaskService;
import byCodeGame.game.remote.Message;
import byCodeGame.game.util.HeroUtils;
import byCodeGame.game.util.Utils;

public class ChapterServiceImpl implements ChapterService {

	private RoleService roleService;
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	private PropService propService;
	public void setPropService(PropService propService) {
		this.propService = propService;
	}

	private FightService fightService;
	public void setFightService(FightService fightService) {
		this.fightService = fightService;
	}

	private TaskService taskService;
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
	
	private HeroService heroService;
	public void setHeroService(HeroService heroService) {
		this.heroService = heroService;
	}
	
	private HeroDao heroDao;
	public void setHeroDao(HeroDao heroDao) {
		this.heroDao = heroDao;
	}
	
	private MailService mailService;
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}
	
	public Message getChapterData(Role role,int cid){
		Message message = new Message();
		message.setType(ChapterConstant.Action.CHAPTER_DATA.getVal());
		byte star = 0;
		Chapter chapter = role.getChapter();
		if(role.getChapter().getStarMap().containsKey(cid)){
			star = role.getChapter().getStarMap().get(cid);
		}
		ChapterConfig x = ChapterConfigCache.getChapterConfigById(cid);
		message.putShort(ErrorCode.SUCCESS);
		message.putInt(cid);
		message.put(star);
		String[] strs = x.getTroopStr().split(",");
		StringBuilder sb = new StringBuilder();
		for(String temp : strs)
		{
			ChapterArmsConfig cac = ChapterArmsConfigCache.getChapterArmsConfig(temp);
			if(cac == null)
			{
				sb.append(temp);
			}else {
				HeroConfig heroConfig = HeroConfigCache.getHeroConfigById(cac.getHeroId());
				
				sb.append(heroConfig.getImgId());
			}
			sb.append(",");
		}
		
		
		//是否可以重置及重置所需金币数量
		byte canReset = 0;
		int needGold = 0;
		byte refreshTimes = 0;	
		
		if(chapter.getRefreshTimesMap().containsKey(cid)){
			refreshTimes = chapter.getRefreshTimesMap().get(cid);
		}
		needGold = this.refreshNeedGold(role, refreshTimes);
		VipConfig vipConfig = VipConfigCache.getVipConfigByVipLv((int)role.getVipLv());
		if(vipConfig.getRefreshChapterTimes() <= refreshTimes){	//达到最大刷新次数
			canReset = 1;
			needGold = 0;
		}
		
		if (!role.getChapter().getTimesMap().containsKey(cid)
				|| role.getChapter().getTimesMap().get(cid) < x.getDayTime()) {
			canReset = 1;
			needGold = 0;
		}
		
		message.putString(sb.toString());
		message.putInt(x.getCoins());
		message.putInt(x.getExploit());
		message.putString(x.getRandomReward() == null ?
				"" : x.getRandomReward());
		message.putInt(x.getUnLockHero());
		message.putInt(role.getChapter().getTimesMap().containsKey(cid) ?
				role.getChapter().getTimesMap().get(cid) : 0);
		message.putInt(x.getDayTime());
		message.putInt(x.getAdvisablePower());
		message.put(canReset);
		message.putInt(needGold);
		
		return message;
	}


	public Message getAllChapterData(Role role){
		Message message = new Message();
		message.setType(ChapterConstant.Action.GET_ALL_CHAPTER_DATA.getVal());
		
		message.putInt(role.getChapter().getStarMap().size());
		for(Map.Entry<Integer, Byte> entry : role.getChapter().getStarMap().entrySet()){
			//关卡id
			int cId = entry.getKey();
			//星数
			byte star = entry.getValue();
			//今日攻打的次数
			byte times = 0 ;
			if(role.getChapter().getTimesMap().size() == 0)
			{
				times = 0 ;
			}else {
				if(role.getChapter().getTimesMap().containsKey(cId))
				{
					times = role.getChapter().getTimesMap().get(cId);
				}
			}
			
			ChapterConfig chapterConfig = ChapterConfigCache.getChapterConfigById(cId);
			message.putInt(cId);
			message.put(star);
			message.put(times);
			message.put((byte)chapterConfig.getDayTime());
			
		}
		return message;
	}

	public Message raidsChapter(Role role,int cid ,int num){
		Message message = new Message();
		message.setType(ChapterConstant.Action.RAIDS.getVal());

		byte star = ChapterConstant.CAN_NOT_GET;
		Chapter chapter = role.getChapter();
		if(!chapter.getStarMap().containsKey(cid))
		{
			message.putShort(ErrorCode.RAIDS_ERROR);
			return message;
		}
		star = chapter.getStarMap().get(cid);
//		byte fightTime = 0;
//		if(role.getChapter().getTimesMap().containsKey(cid)){
//			fightTime = role.getChapter().getTimesMap().get(cid);
//		}
		if(star != ChapterConstant.NEED_RAID_STARS){	//星数不够
			message.putShort(ErrorCode.RAIDS_ERROR);
			return message;
		}
//		if(fightTime <= 0){		//当前攻打次数为0
//			return null;
//		}
		ChapterConfig chapterConfig = ChapterConfigCache.getChapterConfigById(cid);
		if(chapterConfig == null){
			return null;
		}
		//超过每日次数
		if(fightService.getChapterTimes(role, cid) >= chapterConfig.getDayTime()){
			message.putShort(ErrorCode.CHAPTER_MAX_TIMES);
			return message;
		}
		//判断扫荡券是否足够
		int costGold = ChapterConstant.NUM_0;
		int useCopon = ChapterConstant.NUM_0;
		int temp = this.hasRaidsTicketsNum(role);
		if(temp < num )
		{
			costGold = (num - temp) * ChapterConstant.RAID_CHAPTER_COST;
			//判断玩家是否有足够的元宝
			if(role.getGold() < costGold)
			{
				message.putShort(ErrorCode.NO_GOLD);
				return message;
			}
			roleService.addRoleGold(role, -costGold);
			useCopon = temp;
			
		}else {
			useCopon = num;
		}
		
		IoSession is = SessionCache.getSessionById(role.getId());
//		propService.useProp(role, ChapterConstant.RAID_CHAPTER_ITEM_ID, (short)1, is);
		propService.addProp(role, ChapterConstant.RAID_CHAPTER_ITEM_ID, 
				ChapterConstant.USE_TYPE_GOLD,-useCopon, is);
		//奖励
		int money = chapterConfig.getCoins() * num;
		int exp = chapterConfig.getExp() * num;
		int expolit = chapterConfig.getExploit() * num;
		roleService.addRoleMoney(role, money);
		roleService.addRoleExploit(role, expolit);
//		//武将获得经验
//		int heroExp = chapterConfig.getHeroExp() * num;
//		heroService.addHeroExp(role, heroExp);
		
		//关卡随机奖励
		Set<ChapterReward> randomReward = new HashSet<ChapterReward>();
		int flag = ChapterConstant.NUM_0;
		do {
			flag++;
			randomReward.addAll(fightService.randomChapterReward(role, chapterConfig));
			randomReward.addAll(this.getRidAward(chapterConfig));
		} while (flag < num);
		this.getAward(role, randomReward);
		
		//增加次数
		fightService.addChapterTimes(role, cid, (byte)num);
		message.putShort(ErrorCode.SUCCESS);
		message.putInt(useCopon);
		message.putInt(costGold);
		message.putInt(money);
		message.putInt(exp);
		message.putInt(chapterConfig.getExploit());
		if(randomReward.size() == ChapterConstant.NUM_0)
		{
			message.put(ChapterConstant.CAN_NOT_GET);
		}else {
			message.put((byte)randomReward.size());
			for(ChapterReward set : randomReward){
				message.put(set.getType());
				message.putInt(set.getItemId());
				message.putInt(set.getNum());
			}
		}
		this.taskService.checkChapterTask(role, cid);
		
		return message;
	}

	public Message refreshChapterTimes(Role role,int cid){
		Message message = new Message();
		message.setType(ChapterConstant.Action.REFRESH_TIMES.getVal());
		Chapter chapter =role.getChapter();
		//判断次数是否有剩余
		ChapterConfig config = ChapterConfigCache.getChapterConfigById(cid);
		if(config == null) return null;
		
		if(!chapter.getStarMap().containsKey(cid))
		{
			return null;
		}
		
		if(!role.getChapter().getTimesMap().containsKey(cid)
				|| role.getChapter().getTimesMap().get(cid) < config.getDayTime())
		{
			message.putShort(ErrorCode.CHAPTER_HAS_TIME);
			return message;
		}
		
		byte times = 0;
		if(chapter.getRefreshTimesMap().containsKey(cid)){
			times = chapter.getRefreshTimesMap().get(cid);
		}
		VipConfig vipConfig = VipConfigCache.getVipConfigByVipLv((int)role.getVipLv());
		if(vipConfig.getRefreshChapterTimes() <= times){	//达到最大刷新次数
			message.putShort(ErrorCode.MAX_REFRESH_TIME);
			return message;
		}
		int needGold = this.refreshNeedGold(role, times);
		if(role.getGold() < needGold){
			message.putShort(ErrorCode.NO_GOLD);
			return message;
		}

		//刷新成功
		roleService.addRoleGold(role, -needGold);
		times += 1;
		chapter.getRefreshTimesMap().put(cid, times);
		chapter.getTimesMap().remove(cid);

		message.putShort(ErrorCode.SUCCESS);
		message.putInt(cid);
		message.putInt(needGold);
		message.putInt(config.getDayTime());
		message.putInt(config.getDayTime());
		return message;
	}

	/**
	 * 获取关卡奖励
	 * @author xjd
	 */
	public Message getChapterStarsAward(Role role, int chapterId, int process) {
		Message message = new Message();
		message.setType(ChapterConstant.Action.GET_CHAPTER_AWARD.getVal());
		ChapterAward chapterAward = ChapterAwardCache.getChapterAwardById(chapterId, process);
		if(chapterAward == null)
		{
			return null;
		}
		//判断是否已经领取过本奖励
		Chapter chapter = role.getChapter();
		if(chapter.getAlreadyGetAwardSet().contains(chapterAward.getRecordNo()))
		{
			message.putShort(ErrorCode.ALREADY_GET_AWARD);
			return message;
		}
		if(!chapter.getAllStarMap().containsKey(chapterId-ChapterConstant.NUM_1))
		{
			message.putShort(ErrorCode.NO_STARS);
			return message;
		}
		//判断所需星数是否达到
		if(chapter.getAllStarMap().get(chapterId-ChapterConstant.NUM_1) < chapterAward.getNeedStars())
		{
			message.putShort(ErrorCode.NO_STARS);
			return message;
		}
		//处理奖励
		Set<ChapterReward> set = Utils.changStrToAward(chapterAward.getAwardStr());
//		int needBack = this.checkBackPage(set);
//		//判断玩家背包是否足够
//		if(role.getMaxBagNum() < role.getBackPack().size() + needBack)
//		{
//			message.putShort(ErrorCode.BACKPACK_ERR);
//			return message;
//		}
		//处理奖励逻辑
		this.getAward(role, set);
		//返回
		message.putShort(ErrorCode.SUCCESS);
		message.put((byte)set.size());
		for(ChapterReward x : set)
		{
			message.put(x.getType());
			message.putInt(x.getItemId());
			message.putInt(x.getNum());
		}
		
		chapter.getAlreadyGetAwardSet().add(chapterAward.getRecordNo());
		chapter.setChange(true);
		return message;
	}
	
	/**
	 * 获取玩家关卡星数数据
	 * 
	 */
	public Message getAllStarsData(Role role) {
		Message message = new Message();
		message.setType(ChapterConstant.Action.GET_CHAPTER_STARS.getVal());
		
		message.putInt(role.getChapter().getAllStarMap().size());
		for(Entry<Integer, Integer> x : role.getChapter().getAllStarMap().entrySet())
		{
			message.putInt(x.getKey());
			message.putInt(x.getValue());
		}
		return message;
	}
	
	/**
	 * 获取成就奖励信息
	 * @author xjd
	 */
	public Message getChapterAwardData(Role role,int chapterId) {
		Message message = new Message();
		message.setType(ChapterConstant.Action.GET_CHAPTER_AWARD_DATA.getVal());
		message.putInt(ChapterAwardCache.getChapterAwardMap(chapterId).size());
		for(ChapterAward x : ChapterAwardCache.getChapterAwardMap(chapterId).values())
		{
			message.putInt(x.getProcess());
			message.putInt(x.getNeedStars());
			message.putString(x.getAwardStr());
			if(role.getChapter().getAlreadyGetAwardSet().contains(x.getRecordNo()))
			{
				message.put(ChapterConstant.ALREADY_GET);
			}else if (!role.getChapter().getAllStarMap().containsKey(chapterId-ChapterConstant.NUM_1)
					||role.getChapter().getAllStarMap().get(chapterId-ChapterConstant.NUM_1) < x.getNeedStars()) {
				message.put(ChapterConstant.CAN_NOT_GET);
			}else {
				message.put(ChapterConstant.CAN_GET);
			}
		}
		
		return message;
	}
	
	/**
	 * 获取单一章节关卡信息
	 * @author xjd
	 */
	public Message getPartChapterData(Role role, int part) {
		Message message = new Message();
		message.setType(ChapterConstant.Action.GET_PART_CHAPTER_DATA.getVal());
		
		//判定章节是否合法
		Map<Integer, Byte> temp = this.fingPartChapterData(role, part);
		
		message.putShort(ErrorCode.SUCCESS);
		message.putInt(temp.size());
		for(Entry<Integer, Byte> x : temp.entrySet())
		{
			//关卡id
			int cId = x.getKey();
			//星数
			byte star = x.getValue();
			//今日攻打的次数
			byte times = 0 ;
			if(role.getChapter().getTimesMap().size() == 0)
			{
				times = 0 ;
			}else {
				if(role.getChapter().getTimesMap().containsKey(cId))
				{
					times = role.getChapter().getTimesMap().get(cId);
				}
			}
			
			ChapterConfig chapterConfig = ChapterConfigCache.getChapterConfigById(cId);
			message.putInt(cId);
			message.put(star);
			message.put(times);
			message.put((byte)chapterConfig.getDayTime());
			if(chapterConfig.getUnLockHero() != 0 && chapterConfig.getUnLockHeroType() == 1)
			{
				message.putInt(chapterConfig.getUnLockHero());
			}else {
				message.putInt(ChapterConstant.NUM_0);
			}
		}
		if(role.getChapter().getAllStarMap().containsKey(part-ChapterConstant.NUM_1))
		{
			message.putInt(role.getChapter().getAllStarMap().get(part-ChapterConstant.NUM_1));
		}else {
			message.putInt(ChapterConstant.NUM_0);
		}
		
		ChapterAward award = ChapterAwardCache.getChapterAwardById(part, ChapterConstant.NUM_1);
		message.putInt(award.getToltalProcess());
		return message;
	}
	
	
	/**
	 * 获取刷新金钱
	 * @param role
	 * @param cid
	 * @return
	 */
	private int refreshNeedGold(Role role,byte times){
		int needGold = 0;
		if(times == 0){
			needGold = 20;
		}else if(times == 1){
			needGold = 50;
		}else if(times == 2){
			needGold = 100;
		}
		return needGold;
	}
	
	/**
	 * 查询玩家有多少扫荡券
	 * @param role
	 * @return
	 */
	private int hasRaidsTicketsNum(Role role){
//		int num = ChapterConstant.NUM_0;
//		for(Map.Entry<Integer, Prop> entry : role.getPropMap().entrySet()){
//			Prop tempProp = entry.getValue();
//			if(tempProp.getItemId() == ChapterConstant.RAID_CHAPTER_ITEM_ID){
//				num = (int)tempProp.getNum();
//				break;
//			}
//		}
		Prop prop = role.getConsumePropMap().get(ChapterConstant.RAID_CHAPTER_ITEM_ID);
		int num = prop == null ? ChapterConstant.NUM_0 : prop.getNum();
		return num;
	}

	private int checkBackPage(Set<ChapterReward> set)
	{
		int flag = 0;
		for(ChapterReward x : set)
		{
			if(x.getType() == ChapterConstant.Award.EQUIP.getVal() 
					|| x.getType() == ChapterConstant.Award.ITEM.getVal())
			{
				flag++;
			}
		}
		return flag;
	}
	
	private Map<Integer, Byte> fingPartChapterData(Role role ,int part)
	{
		Map<Integer, Byte> temp = new HashMap<Integer, Byte>();
		for(Map.Entry<Integer, Byte> entry : role.getChapter().getStarMap().entrySet()){
			//关卡id
			int cId = entry.getKey();
			ChapterConfig chapterConfig = ChapterConfigCache.getChapterConfigById(cId);
			if(chapterConfig.getBattleId() == part)
			{
				temp.put(entry.getKey(), entry.getValue());
			}
		}
		return temp;
	}
	
	/**
	 * 此处方法控制领取奖励的过程如果玩家背包数量不足则改为邮件发送
	 * @param role
	 * @param set
	 */
	public void getAward(Role role , Set<ChapterReward> set)
	{
		IoSession is  = SessionCache.getSessionById(role.getId());
		if(set == null)
		{
			return;
		}
		for(ChapterReward x : set)
		{
			switch (x.getType()) {
			case ChapterConstant.AWARD_EXP:
				roleService.addRoleExp(role, x.getNum());
				break;
			case ChapterConstant.AWARD_MONEY:
				roleService.addRoleMoney(role, x.getNum());
				break;
			case ChapterConstant.AWARD_GOLD:
				roleService.addRoleGold(role, x.getNum());
				break;
			case ChapterConstant.AWARD_EXPLOIT:
				roleService.addRoleExploit(role, x.getNum());
				break;
			case ChapterConstant.AWARD_FOOD:
				roleService.addRoleFood(role, x.getNum());
				break;
			case ChapterConstant.AWARD_POPULATION:
				roleService.addRolePopulation(role, x.getNum());
				break;
			case ChapterConstant.AWARD_ARMYTOKEN:
				roleService.getArmyToken(role, x.getNum());
				break;
			case ChapterConstant.AWARD_PRESTIGE:
				roleService.addRolePrestige(role, x.getNum());
				break;
			case ChapterConstant.AWARD_EQUIP:
				EquipConfig equipConfig = EquipConfigCache.getEquipConfigById(x.getItemId());
				if(equipConfig == null) break;
					propService.addProp(role, x.getItemId(), (byte)1, x.getNum(), is);
				break;
			case ChapterConstant.AWARD_ITEM:
					propService.addProp(role, x.getItemId(), (byte)2, x.getNum(), is);
				break;
			case ChapterConstant.AWARD_HERO:
				
//				HeroConfig config = HeroConfigCache.getHeroConfigById(x.getItemId());
//				if(config == null) break;
//				if(role.getHeroMap().containsKey(config.getHeroId()))
//				{
//					heroService.addHeroEmotion(role, x.getItemId(), x.getNum());
//					break;
//				}  
				int heroId = x.getItemId();
				Hero hero = heroService.createHero(role, heroId);
				heroService.addHero(is, hero);
			default:
				break;
			}
		}
	}


	private Set<ChapterReward> getRidAward(ChapterConfig chapterConfig)
	{
		Set<ChapterReward> set = new HashSet<ChapterReward>();
		String debrisAward = chapterConfig.getRaidAward();
		String[] strs2 = (debrisAward == null || debrisAward.equals(""))?
				null : debrisAward.split(";");
		int rewardRand = ChapterConstant.NUM_0;
		if(strs2 == null)
		{
			return set;
		}
		int randow = Utils.getRandomNum(0, 10000);
		for(String reward : strs2){
			String[] rewardArr = reward.split(",");
			//奖励概率
			if (Byte.valueOf(rewardArr[1]) == (byte)9) {
				EquipConfig config = EquipConfigCache.getEquipConfigById(Integer.parseInt(rewardArr[2]));
				if(config == null) continue;
			}
			rewardRand += Integer.parseInt(rewardArr[0]);
			byte type = Byte.valueOf(rewardArr[1]);
			int itemId = Integer.parseInt(rewardArr[2]);
			int num = Integer.valueOf(rewardArr[3]);
			
			if(randow <= rewardRand){	//获得奖励
				ChapterReward chapterReward = new ChapterReward();
				chapterReward.setItemId(itemId);
				chapterReward.setType(type);
				chapterReward.setNum(num);
				set.add(chapterReward);
				break;
			}
		}
		return set;
	}
	
	@Override
	public void initChapterConfig() {
		Map<Integer,ChapterConfig> map = ChapterConfigCache.getchapterData();
		for (ChapterConfig config : map.values()) {
			int advisablePower = 0;
			Map<Integer,String> troopsMap = config.getTroops();
			for(String troops:troopsMap.values()){
				if(troops.equals("0"))
					continue;
				
				ChapterArmsConfig cac = ChapterArmsConfigCache.getChapterArmsConfig(troops);
				int fightValue = HeroUtils.getHeroFightValue(cac.getHp(), cac.getGeneralAttack(), cac.getPowerAttack(),
						cac.getMagicalAttack(), cac.getGeneralDefence(), cac.getPowerDefence(),
						cac.getMagicalDefence(), cac.getCaptain(), cac.getPower(), cac.getIntel());
				advisablePower+=fightValue;
			}
			config.setAdvisablePower(advisablePower);
		}
	}
}
