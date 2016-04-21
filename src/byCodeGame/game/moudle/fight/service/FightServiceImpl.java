package byCodeGame.game.moudle.fight.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.mina.core.session.IoSession;

import com.sun.xml.internal.ws.util.UtilException;

import byCodeGame.game.cache.file.ChapterConfigCache;
import byCodeGame.game.cache.file.EquipConfigCache;
import byCodeGame.game.cache.file.HeroConfigCache;
import byCodeGame.game.cache.file.HeroFavourConfigCache;
import byCodeGame.game.cache.file.IconUnlockConfigCache;
import byCodeGame.game.cache.file.StarStrategyCache;
import byCodeGame.game.cache.file.ZhuanshengConfigCache;
import byCodeGame.game.cache.local.ResultsCache;
import byCodeGame.game.cache.local.SessionCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.db.dao.HeroDao;
import byCodeGame.game.db.dao.ResultsDao;
import byCodeGame.game.entity.bo.Chapter;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.file.ChapterConfig;
import byCodeGame.game.entity.file.EquipConfig;
import byCodeGame.game.entity.file.HeroConfig;
import byCodeGame.game.entity.file.HeroFavourConfig;
import byCodeGame.game.entity.file.IconUnlockConfig;
import byCodeGame.game.entity.file.StarStrategy;
import byCodeGame.game.entity.file.TroopCounterConfig;
import byCodeGame.game.entity.po.ChapterReward;
import byCodeGame.game.entity.po.FormationData;
import byCodeGame.game.moudle.chapter.ChapterConstant;
import byCodeGame.game.moudle.chapter.service.ChapterService;
import byCodeGame.game.moudle.fight.FightConstant;
import byCodeGame.game.moudle.hero.service.HeroService;
import byCodeGame.game.moudle.role.RoleConstant;
import byCodeGame.game.moudle.role.service.RoleService;
import byCodeGame.game.moudle.task.service.TaskService;
import byCodeGame.game.remote.Message;
import byCodeGame.game.util.HeroUtils;
import byCodeGame.game.util.InComeUtils;
import byCodeGame.game.util.PVPUitls;
import byCodeGame.game.util.Utils;
import cn.bycode.game.battle.cache.TroopCounterConfigCache;
import cn.bycode.game.battle.core.Battle;
import cn.bycode.game.battle.data.ResultData;
import cn.bycode.game.battle.data.TroopData;

public class FightServiceImpl implements FightService {

	private RoleService roleService;
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	private ChapterService chapterService;
	public void setChapterService(ChapterService chapterService) {
		this.chapterService = chapterService;
	}

	private HeroService heroService;
	public void setHeroService(HeroService heroService) {
		this.heroService = heroService;
	}
	
	private ResultsDao resultsDao;
	public void setResultsDao(ResultsDao resultsDao) {
		this.resultsDao = resultsDao;
	}

	private HeroDao heroDao;
	public void setHeroDao(HeroDao heroDao) {
		this.heroDao = heroDao;
	}
	
	private TaskService taskService;
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
	
	public Message requestAttackChapter(Role role,int cid){
		Message message = new Message();
		message.setType(FightConstant.Action.REQUEST_ATK_CHAPTER.getVal());

		ChapterConfig chapterConfig = ChapterConfigCache.getChapterConfigById(cid);
		if(chapterConfig == null){
			return null;
		}
		//超过每日次数
		if(this.getChapterTimes(role, cid) >= chapterConfig.getDayTime()){
			message.putShort(ErrorCode.CHAPTER_MAX_TIMES);
			return message;
		}
		FormationData formationData = role.getFormationData().get(role.getUseFormationID());
		if(formationData.getData().size() <= 0){	//玩家阵型没有武将
			return null;
		}
		//当前所攻打的关卡从未通关  此处逻辑用来判定当前关卡是否可以攻打
		if(!role.getChapter().getStarMap().containsKey(cid) && 
				role.getChapter().getNowChapterId() != 0){	

			ChapterConfig chapterConfig2 = ChapterConfigCache.
					getChapterConfigById(role.getChapter().getNowChapterId());
			if(chapterConfig2.getNextChapterId() != cid){
				return null;
			}
		}
		if(role.getChapter().getNowChapterId() == FightConstant.AWARD_TYPE)
		{
			if(cid != FightConstant.FIRST_CHAPTER_ID)
			{
				return null;
			}
		}
		
		/*
		 * 进入战斗前的 粮草检测
		 * 如果粮草判断不足则 
		 */
		int tempAllHp = FightConstant.NULL_HERO;
		int heroNum = 0;
		int totalLevel = 0;
		List<Hero> heroList = new ArrayList<>();
		for (int x : formationData.getData().values()) {
			if (x <= FightConstant.NUM_USE_2)
				continue;
			Hero hero = role.getHeroMap().get(x);
//			heroNum++;
//			totalLevel += hero.getLv();
//			// hero.setArmsNum(HeroUtils.getMaxHp(role, hero));
//			tempAllHp += hero.getArmsNum();
			heroList.add(hero);
		}
//		tempAllHp = tempAllHp / 10;
		int fightNeedFood = InComeUtils.fightNeedFood(heroList);
		if(role.getFood() < fightNeedFood)	
		{
			message.putShort(ErrorCode.NO_FOOD);
			return message;
		}
		//PVEUtil
		TroopData roleData = PVPUitls.getTroopDataByRole(role);
		TroopData chapterData = PVPUitls.getTroopDataByChapterId(cid);
		ResultData resultData = new Battle().fightPVP(roleData, chapterData);
		
		/*
		 * 根据结果判定之后的处理流程
		 */
		this.returnStar(resultData, chapterConfig.getStarStrategy());
		resultsDao.insertResults(resultData);
		//如果失败
		if(resultData.winCamp != FightConstant.TYPE_ATT)
		{
			int heroExp = FightConstant.NUM_0;
			//武将获得经验
			String expStr = heroService.addHeroExp(role, heroExp);
			//粮草消耗
			roleService.addRoleFood(role,-(int)(fightNeedFood*.2));
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
			message.putInt((int)(fightNeedFood *.2));
			message.putInt(resultData.defStars);
			message.putInt(resultData.defLost);
			message.putInt(resultData.fRound);
			message.putInt(FightConstant.NUM_0);
			message.putString(expStr);
			message.putInt(FightConstant.NUM_0);
			message.putInt(FightConstant.NUM_0);
			message.putString(FightConstant.NUM_0+"");
			message.putInt(FightConstant.NUM_0);
			message.putInt(FightConstant.NUM_0);
			message.putInt(FightConstant.NUM_0);
			message.putInt(FightConstant.NUM_0);
			message.putInt(FightConstant.NUM_0);
			message.putInt(FightConstant.NUM_0);
			message.putInt(FightConstant.NUM_0);
			return message;
		}
		//粮草消耗
		roleService.addRoleFood(role, -fightNeedFood);
		IoSession is = SessionCache.getSessionById(role.getId());
		//是否首次通关
		boolean flag = this.checkFisrtComplet(role, cid, chapterConfig, is);
		//增加关卡今日次数
		this.addChapterTimes(role, cid, FightConstant.BYTE_1);
		//记录星数
		this.checkStarsChapter(role, cid,(byte)resultData.attStars);
		//检查当前关卡号  1704功能图标解锁
		this.checkNowChapterId(role, cid);
		//奖励
		int money = chapterConfig.getCoins();
//		int exp = chapterConfig.getExp();
		int heroExp = chapterConfig.getHeroExp();
		int exploit = chapterConfig.getExploit();
//		roleService.addRoleExp(role, exp);
		roleService.addRoleMoney(role, money);
		roleService.addRoleExploit(role, exploit);
		//武将获得经验
		String expStr = heroService.addHeroExp(role, heroExp);
		Set<ChapterReward> randomReward = null;
		if(flag)
		{
			randomReward = Utils.changStrToAward(chapterConfig.getFirstDrop());
		}else {
			randomReward = this.randomChapterReward(role, chapterConfig);
		}
				
		chapterService.getAward(role, randomReward);
		
		
		this.taskService.checkChapterTask(role,cid);
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
		message.putInt(fightNeedFood);
		message.putInt(resultData.defStars);
		message.putInt(resultData.defLost);
		message.putInt(resultData.fRound);
		StringBuilder sb = new StringBuilder();
		message.putInt(heroExp);
		message.putString(expStr);
		message.putInt(money);
		message.putInt(exploit);
		if(randomReward != null)
		{
			for(ChapterReward x : randomReward)
			{
				sb.append(x.getItemId()).append(",").append(x.getNum()).append(";");
			}
		}
		if(sb.length() <= FightConstant.AWARD_TYPE)
		{
			sb.append(FightConstant.AWARD_TYPE);
		}
		message.putString(sb.toString());
		//好感度相关
		Hero temp = null;
		HeroFavourConfig x = null;
		if(chapterConfig.getUnLockHero() != FightConstant.NUM_0
				&& chapterConfig.getUnLockHeroType() == FightConstant.NUM_USE_1)
		{
			temp = role.getHeroMap().get(chapterConfig.getUnLockHero());
			x = HeroFavourConfigCache.getHeroFavourConfig(temp.getEmotionLv());
		}
		
		if(flag)
		{
			message.putInt(chapterConfig.getUnLockHeroType());
			message.putInt(chapterConfig.getUnLockHero());
		}else if(role.getHeroMap().containsKey(chapterConfig.getUnLockHero())){
//			heroService.addHeroEmotion(role, chapterConfig.getUnLockHero(),
//					chapterConfig.getFavour());
//			x = HeroFavourConfigCache.getHeroFavourConfig(temp.getEmotionLv());
			message.putInt(FightConstant.NUM_USE_2);
			message.putInt(chapterConfig.getUnLockHero());
		}else {
			message.putInt(FightConstant.NUM_0);
			message.putInt(FightConstant.NUM_0);
		}
		return message;
	}

	/**
	 * 计算星数PvE
	 */
	public void returnStar(ResultData res,int type){
		if(res.winCamp != FightConstant.TYPE_ATT)
		{
			return;
		}
		byte rStar = 0;
		StarStrategy starStrategy = StarStrategyCache.getStarStrategyById(type);
		if(starStrategy == null) return;
		switch (starStrategy.getStrategyID()) {
		case FightConstant.TYPE_STARS_1:
			double rank = (double)res.attLost / res.attAllHp;
			int temp = (int)(rank * 100);
			if(temp <= starStrategy.getStar3())
			{
				rStar = FightConstant.STARS_3;
			}else if (temp <= starStrategy.getStar2()) {
				rStar = FightConstant.STARS_2;
			}else {
				rStar = FightConstant.STARS_1;
			}
			
			res.attStars = rStar;
			break;
		case FightConstant.TYPE_STARS_2:
			if(res.fRound <= starStrategy.getStar3())
			{
				rStar = FightConstant.STARS_3;
			}else if (res.fRound <= starStrategy.getStar2()) {
				rStar = FightConstant.STARS_2;
			}else {
				rStar = FightConstant.STARS_1;
			}
			break;
		default:
			break;
		}
	}
	

	public Message getChapterReward(Role role,IoSession is){
		Message message = new Message();
//		message.setType(FightConstant.Action.GET_CHAPTER_REWARD.getVal());
//		Set<ChapterReward> rewardSet = role.getFightSnapData().getRewardSet();
//		for(ChapterReward reward : rewardSet){
//			if(reward.getType() == FightConstant.REWARD_TYPE_ITEM){				//道具
//				//判断
//				if(propService.checkItemInBackPack(role, reward.getItemId()) || 
//						propService.getEmptyBackPack(role) >= 1){
//					propService.addProp(role, reward.getItemId(), (byte)2, reward.getNum(), is);
//				}
//			}else if(reward.getType() == FightConstant.REWARD_TYPE_EQUIP){		//装备
//				if(propService.getEmptyBackPack(role) >= reward.getNum()){
//					for (int i = 0; i < reward.getNum(); i++) {
//						propService.addProp(role, reward.getItemId(), (byte)1, 1, is);
//					}
//				}
//			}
//		}
//		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

	public byte getChapterTimes(Role role,int cid){
		byte times = 0;
		if(role.getChapter().getTimesMap().containsKey(cid)){
			times = role.getChapter().getTimesMap().get(cid);
		}
		return times;
	}

	/**
	 * 玩家通关后增加次数
	 * @param role
	 * @param cid
	 * @param times
	 */
	public void addChapterTimes(Role role,int cid,byte times){
		byte newTimes = (byte)(this.getChapterTimes(role, cid) + times);
		role.getChapter().getTimesMap().put(cid, newTimes);
		role.getChapter().setChange(true);
	}

	
	/**
	 * 获取关卡随机奖励
	 * @author xjd
	 */
	public Set<ChapterReward> randomChapterReward(Role role,ChapterConfig chapterConfig){
		String randomReward = chapterConfig.getRandomReward();
//		if(randomReward == null || randomReward.equals("")){
//			return null;
//		}
//		String[] strs = (randomReward == null || randomReward.equals(""))?
//				 null : randomReward.split(";");
		Set<ChapterReward> set = new HashSet<ChapterReward>();
//		for(String reward : strs){
//			String[] rewardArr = reward.split(",");
//			//奖励概率
//			if (Byte.valueOf(rewardArr[1]) == (byte)9) {
//				EquipConfig config = EquipConfigCache.getEquipConfigById(Integer.parseInt(rewardArr[2]));
//				if(config == null) continue;
//			}
//			int rewardRand = Integer.parseInt(rewardArr[0]);
//			byte type = Byte.valueOf(rewardArr[1]);
//			int itemId = Integer.parseInt(rewardArr[2]);
//			byte num = Byte.valueOf(rewardArr[3]);
//			int randow = Utils.getRandomNum(0, 10000);
//			if(randow <= rewardRand){	//获得奖励
//				ChapterReward chapterReward = new ChapterReward();
//				chapterReward.setItemId(itemId);
//				chapterReward.setType(type);
//				chapterReward.setNum(num);
//				set.add(chapterReward);
//			}
//		}
		this.getRandomChapterAward(randomReward, set);
		//碎片奖励
		String debrisAward = chapterConfig.getDebrisAward();
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
	
	/***
	 * 服务器启动时 停用<br/>
	 * 
	 * init所有关卡信息
	 * @author xjd
	 */
	public void initChapterArmyConf() {
		for(Entry<Integer, ChapterConfig> x : ChapterConfigCache.getchapterData().entrySet())
		{
			ChapterConfig chapterConfig = x.getValue();
			ChapterConfigCache.getCorpMap().put(chapterConfig.getId(),PVPUitls.getTroopDataByChapterId(chapterConfig.getId()));
		}
	}
	
	/**
	 * 服务器启动时初始化兵种克制
	 */
	public void initTroopCounter() {
		Map<Integer, TroopCounterConfig> temp = byCodeGame.game.cache.file.TroopCounterConfigCache.getAllmap();
		
		for(Entry<Integer, TroopCounterConfig> x : temp.entrySet())
		{
			cn.bycode.game.battle.entity.TroopCounterConfig config = new cn.bycode.game.battle.entity.TroopCounterConfig();
			config.setId(x.getKey());
			config.setFootman(x.getValue().getFootman());
			config.setKnight(x.getValue().getKnight());
			config.setArcher(x.getValue().getArcher());
			config.setMage(x.getValue().getMage());
			config.setLancer(x.getValue().getLancer());
			TroopCounterConfigCache.getAllmap().put(config.getId(), config);
		}
	}
	
	@Override
	public ResultData getResultDataByUUID(String uuid) {
		ResultData resultData = null;
		resultData = ResultsCache.getRoleMapByUUID(uuid);
		if(resultData == null){
			resultData = this.getDBResultDataByUUID(uuid);
		}
		return resultData;
	}

	@Override
	public ResultData getDBResultDataByUUID(String uuid) {
		ResultData resultData = resultsDao.getResults(uuid);
		if(resultData == null)
		{
			return null;
		}
		ResultsCache.putRoleMap(resultData);
		return resultData;
	}
	
	/**
	 * 根据UUID获取战斗信息
	 */
	public Message getReportByUUID(String uuid) {
		Message message = new Message();
		message.setType(FightConstant.Action.GET_REPORT_UUID.getVal());
		ResultData resultData = this.getResultDataByUUID(uuid);
		if(resultData == null)
		{
			message.putShort(ErrorCode.ERR_UUID_REPORT);
			return message;
		}
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
		message.putInt(resultData.defLost);
		message.putInt(resultData.defStars);
		message.putInt(resultData.fRound);
		message.putInt(FightConstant.NULL_HERO);//经验
		message.putString("");//经验字符串
		message.putInt(FightConstant.NULL_HERO);//银币奖励
		message.putInt(FightConstant.NULL_HERO);//战功奖励
		message.putString("");//奖励
		message.putInt(FightConstant.NULL_HERO);//武将解锁
		message.putInt(FightConstant.NULL_HERO);//武将ID
		return message;
	}
	
	private void getRandomChapterAward(String randomReward ,Set<ChapterReward> set)
	{
		if(randomReward == null || randomReward.equals("")){
			return ;
		}
		String[] strs = randomReward.split(";");
		for(String reward : strs){
			String[] rewardArr = reward.split(",");
			//奖励概率
			if (Byte.valueOf(rewardArr[1]) == (byte)9) {
				EquipConfig config = EquipConfigCache.getEquipConfigById(Integer.parseInt(rewardArr[2]));
				if(config == null) continue;
			}
			int rewardRand = Integer.parseInt(rewardArr[0]);
			byte type = Byte.valueOf(rewardArr[1]);
			int itemId = Integer.parseInt(rewardArr[2]);
			int num = Integer.valueOf(rewardArr[3]);
			int randow = Utils.getRandomNum(0, 10000);
			if(randow <= rewardRand){	//获得奖励
				ChapterReward chapterReward = new ChapterReward();
				chapterReward.setItemId(itemId);
				chapterReward.setType(type);
				chapterReward.setNum(num);
				set.add(chapterReward);
			}
		}
	}

	private void checkStarsChapter(Role role ,int cid ,byte stars)
	{
		//判断是否曾经有星数
		if(!role.getChapter().getStarMap().containsKey(cid))
		{
			role.getChapter().getStarMap().put(cid, stars);
			role.getChapter().setAllStarMap(role.getChapter().getStarMap());
		}else if (role.getChapter().getStarMap().containsKey(cid)) {
			byte tempChapterStar = role.getChapter().getStarMap().get(cid);
			if(tempChapterStar < stars)
			{
				role.getChapter().getStarMap().put(cid, stars);
				role.getChapter().setAllStarMap(role.getChapter().getStarMap());
			}	
		}
	}
	
	private boolean checkFisrtComplet(Role role , int cid ,ChapterConfig chapterConfig,IoSession is)
	{
		boolean flag = false;
		if(!role.getChapter().getStarMap().containsKey(cid)){
			flag = true;
			//关卡通关后获得武将
			if(chapterConfig.getUnLockHeroType() == FightConstant.NUM_USE_1){
				HeroConfig config = HeroConfigCache.getHeroConfigById(chapterConfig.getUnLockHero());
				if(role.getHeroMap().containsKey(config.getHeroId()))
				{
//					heroService.addHeroEmotion(role, chapterConfig.getUnLockHero(), chapterConfig.getFavour());
					return flag;
				}
				int heroId = chapterConfig.getUnLockHero();
				Hero hero = heroService.createHero(role, heroId);
				heroService.addHero(is, hero);
			}
		}
		return flag;
	}
	
	private void checkNowChapterId(Role role ,int cid)
	{
		Chapter chapter = role.getChapter();
		
		if(chapter.getNowChapterId() < cid)
		{
			chapter.setNowChapterId(cid);
			
			List<IconUnlockConfig> list=IconUnlockConfigCache.getChapterUnlockList(cid);
			if(list==null){
				return;
			}
			//图标解锁
//			this.sendMessage1704(role, list);
		}
	}
//	private void sendMessage1704(Role role,List<IconUnlockConfig> list){
//		IoSession is = SessionCache.getSessionById(role.getId());
//		Message message = new Message();
//		message.setType(RoleConstant.OPEN_NEW_BUILD);
//		
//		StringBuffer sb=new StringBuffer();
//		for (IconUnlockConfig iconUnlockConfig : list) {
//			sb.append(iconUnlockConfig.getId()).append(",");
//			
//		}
//		String iconUnlock=role.getIconUnlock();
//		String sb2=sb.toString();
//		role.setIconUnlock(iconUnlock+sb2);
//		message.putString(sb2);
//		is.write(message);
//	}
}
