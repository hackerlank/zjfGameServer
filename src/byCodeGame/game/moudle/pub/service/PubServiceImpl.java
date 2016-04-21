package byCodeGame.game.moudle.pub.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javax.management.relation.RoleNotFoundException;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import byCodeGame.game.cache.file.BuildConfigCache;
import byCodeGame.game.cache.file.EquipConfigCache;
import byCodeGame.game.cache.file.GeneralNumConstantCache;
import byCodeGame.game.cache.file.HeroConfigCache;
import byCodeGame.game.cache.file.HeroFavourConfigCache;
import byCodeGame.game.cache.file.ItemCache;
import byCodeGame.game.cache.file.MainBuildingConfigCache;
import byCodeGame.game.cache.file.MapInfoCache;
import byCodeGame.game.cache.file.PubConfigCache;
import byCodeGame.game.cache.file.PubPropConfigCache;
import byCodeGame.game.cache.file.PubStrategyConfigCache;
import byCodeGame.game.cache.file.TavernConfigCache;
import byCodeGame.game.cache.file.TavernShopCache;
import byCodeGame.game.cache.file.TavernStrategyConfigCache;
import byCodeGame.game.cache.local.SessionCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.db.dao.HeroDao;
import byCodeGame.game.entity.bo.Build;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Prop;
import byCodeGame.game.entity.bo.Pub;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.file.BuildConfig;
import byCodeGame.game.entity.file.EquipConfig;
import byCodeGame.game.entity.file.HeroConfig;
import byCodeGame.game.entity.file.HeroFavourConfig;
import byCodeGame.game.entity.file.Item;
import byCodeGame.game.entity.file.MainBuildingConfig;
import byCodeGame.game.entity.file.MapInfo;
import byCodeGame.game.entity.file.PubConfig;
import byCodeGame.game.entity.file.PubPropConfig;
import byCodeGame.game.entity.file.PubStrategyConfig;
import byCodeGame.game.entity.file.TavernConfig;
import byCodeGame.game.entity.file.TavernShop;
import byCodeGame.game.entity.file.TavernStrategyConfig;
import byCodeGame.game.entity.po.FormationData;
import byCodeGame.game.entity.po.LevyInfo;
import byCodeGame.game.entity.po.MapInfoData;
import byCodeGame.game.entity.po.MapInfoItemData;
import byCodeGame.game.entity.po.PubMapInfo;
import byCodeGame.game.entity.po.TalkResult;
import byCodeGame.game.entity.po.VisitData;
import byCodeGame.game.entity.po.WineDesk;
import byCodeGame.game.moudle.hero.HeroConstant;
import byCodeGame.game.moudle.hero.service.HeroService;
import byCodeGame.game.moudle.inCome.InComeConstant;
import byCodeGame.game.moudle.inCome.service.InComeService;
import byCodeGame.game.moudle.mail.service.MailService;
import byCodeGame.game.moudle.prop.PropConstant;
import byCodeGame.game.moudle.prop.service.PropService;
import byCodeGame.game.moudle.pub.PubConstant;
import byCodeGame.game.moudle.role.service.RoleService;
import byCodeGame.game.moudle.task.service.TaskService;
import byCodeGame.game.remote.Message;
import byCodeGame.game.util.HeroUtils;
import byCodeGame.game.util.InComeUtils;
import byCodeGame.game.util.Utils;

public class PubServiceImpl implements PubService {

	@SuppressWarnings("unused")
	private static final Logger pubLog = LoggerFactory.getLogger("pub");

	private HeroDao heroDao;

	public void setHeroDao(HeroDao heroDao) {
		this.heroDao = heroDao;
	}

	private RoleService roleService;

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	private HeroService heroService;

	public void setHeroService(HeroService heroService) {
		this.heroService = heroService;
	}

	private MailService mailService;

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	private TaskService taskService;

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	private PropService propService;

	public void setPropService(PropService propService) {
		this.propService = propService;
	}

	private InComeService inComeService;

	public void setInComeService(InComeService inComeService) {
		this.inComeService = inComeService;
	}

	public Message singleTalk(Role role, IoSession is, byte currencyType) {
		Message message = new Message();
		message.setType(PubConstant.TALK_PUB);

		Pub pub = role.getPub();
//		// 如果玩家等级未到达10级
//		if (role.getLv() < PubConstant.ENTER_PUB_LV) {
//			message.putShort(ErrorCode.NO_LV);
//			return message;
//		}
//
//		// 如果国家没有国际则返回
//		if (role.getNation() == (byte) 0) {
//			message.putShort(ErrorCode.NO_NATION);
//			return message;
//		}

		TalkResult talkResult = new TalkResult();
		// 检查是否免费
		int currentTime = Utils.getNowTime();		
		
		// 免费抽
		if (checkFreeSingleTalk(role, currencyType, currentTime)) {
			// 保存免费会谈时间

			saveFreeTalkTime(currencyType, pub, currentTime);
			
			if (currencyType == PubConstant.TYPE_TALK_EXPLOIT) {
				pub.setFreeExploitNum(pub.getFreeExploitNum()+1);
			} else if (currencyType == PubConstant.TYPE_TALK_GOLD) {
				pub.setFreeGoldNum(pub.getFreeGoldNum()+1);
			}
			randomResult(role, talkResult, currencyType);
			reckonTalk(role, is, talkResult, currencyType);
			//特判，第一次战功抽要放入整形
			if(this.checkIsAddToFormationData(role,currencyType))
			{
				role.getFormationData().get(1001).clearData();
				role.getFormationData().get(1001).putHero((byte)5, talkResult.getId());
			}
			pub.setChang(true);

			// 检查是否有会谈的任务
			this.taskService.checkPubTalkTask(role, currencyType, (byte) 1);

			HashMap<Byte, Integer> periodMapping = getTimePeriod(pub);

			message.putShort(ErrorCode.SUCCESS);
			message.put(currencyType);
			message.putInt(0);
			message.put(talkResult.getType());
			message.putInt(talkResult.getId());
			message.putInt(talkResult.getNumber());
			message.putString(talkResult.getName());
			message.putInt(periodMapping.get(currencyType));
			return message;
		}

		/**
		 * 花钱抽奖
		 */
		// 检查钱是否足够
		int drawType = this.getDrawType(currencyType,PubConstant.TALK_TYPE_SINGLE);
		int cost = TavernStrategyConfigCache.getTavenStrategyConfig(drawType).getCost();
		
		if (currencyType == PubConstant.TYPE_TALK_EXPLOIT) {
			if (role.getExploit() < cost) {
				message.putShort(ErrorCode.NO_EXPLOIT);
				return message;
			}
		} else if (currencyType == PubConstant.TYPE_TALK_GOLD) {
			if (role.getGold() < cost) {
				message.putShort(ErrorCode.NO_GOLD);
				return message;
			}
		}
		// 允许抽奖
		if (currencyType == PubConstant.TYPE_TALK_EXPLOIT) {
			roleService.addRoleExploit(role, -cost);
		} else if (currencyType == PubConstant.TYPE_TALK_GOLD) {
			roleService.addRoleGold(role, -cost);
			// 获得积分
			// pub.setTalkMile(pub.getTalkMile() +
			// PubConstant.SINGLE_GOLD_SEND_MILE);
		}

		randomResult(role, talkResult, currencyType);
		reckonTalk(role, is, talkResult, currencyType);

		// 检查是否有会谈的任务
		this.taskService.checkPubTalkTask(role, currencyType, (byte) 1);

		HashMap<Byte, Integer> periodMapping = getTimePeriod(pub);

		message.putShort(ErrorCode.SUCCESS);
		message.put((byte) currencyType);
		message.putInt(cost);
		message.put(talkResult.getType());
		message.putInt(talkResult.getId());
		message.putInt(talkResult.getNumber());
		message.putString(talkResult.getName());
		message.putInt(periodMapping.get(currencyType));
		message.put((byte) talkResult.getFirstGet());

		return message;
	}

	/**
	 * 检查是否加入阵形
	 * @param role
	 * @param currencyType
	 * @return
	 * @author wcy 2016年1月14日
	 */
	private boolean checkIsAddToFormationData( Role role,byte currencyType) {
		Pub pub = role.getPub();
		return pub.getTotalTalksNumber() == 1&&currencyType == PubConstant.TYPE_TALK_EXPLOIT;
	}
	
	/**
	 * 单次会谈随机
	 * @param role
	 * @param talkResult
	 * @param currencyType
	 * @author wcy 2016年1月6日
	 */
	private void randomResult(Role role,TalkResult talkResult,byte currencyType){
		//增加会谈次数
		this.addTotalTalkTimes(role, currencyType);
		
		int lv = (int)role.getBuild().getBuildLvMap().get(InComeConstant.TYPE_PUB);
		int drawType = this.getDrawType(currencyType, PubConstant.TALK_TYPE_SINGLE);
		TavernStrategyConfig strategyConfig = TavernStrategyConfigCache.getTavenStrategyConfig(drawType);
		int setId = 0;
		if(checkFirstTalk(role, currencyType)){
			setId = strategyConfig.getFirstDrawSet();
		}else if(checkNodeTalk(role,currencyType)){
			setId = strategyConfig.getNodeSet();
		}else{
			int heroProb = 0;
			int debrisProb = 0;
			int propProb = 0;
			if(currencyType == PubConstant.TYPE_TALK_EXPLOIT){
				PubConfig pubConfig = PubConfigCache.getPubConfigByLv(lv);
				heroProb = pubConfig.getHeroProb();
				//未中加成
				heroProb += role.getPub().getMissHero() * GeneralNumConstantCache.getValue("PUB_TALK_ADD_POWER");
				debrisProb = pubConfig.getDebrisProb();
				propProb = pubConfig.getPropProb();
			}else if(currencyType == PubConstant.TYPE_TALK_GOLD){
				heroProb = strategyConfig.getHeroProb();
				debrisProb = strategyConfig.getDebrisProb();
				propProb = strategyConfig.getPropProb();				
			}
			
			int totalPropWeight = heroProb + debrisProb + propProb;
			int randValue = Utils.getRandomNum(totalPropWeight);
			if (randValue <= heroProb) {// 武将
				setId = strategyConfig.getHeroSet();
				if (currencyType == PubConstant.TYPE_TALK_EXPLOIT) {
					this.getExploitSingleTalkHero(role,talkResult,setId);
					//未中次数清零
					if(!checkFirstTalk(role, currencyType))
					{
						role.getPub().setMissHero(PubConstant.AWARD_NOTHING);
					}
					
					// 检查如果是武将，是否已经有，有的话变为将魂
					this.checkHeroRepeatAndChange(role, talkResult);
					return;
				}
			} else if (randValue > heroProb && randValue < (heroProb + debrisProb)) {// 将魂
				setId = strategyConfig.getDebrisSet();
			} else {// 道具
				setId = strategyConfig.getPropSet();
			}			
		}
		
		//未抽中战功武将 miss次数++
		if(currencyType == PubConstant.TYPE_TALK_EXPLOIT)
		{
			int num = role.getPub().getMissHero();
			num++;
			role.getPub().setMissHero(num);
		}
		
		int setTotalWeight = TavernConfigCache.getProbWeightCount(setId, role.getNation())
				+ TavernConfigCache.getProbWeightCount(setId, PubConstant.NATION_TYPE_4);
		ArrayList<TavernConfig> array = new ArrayList<>();
		ArrayList<TavernConfig> list1 = TavernConfigCache.getTavernConfigMapBySet(setId, role.getNation());
		ArrayList<TavernConfig> list2 = TavernConfigCache.getTavernConfigMapBySet(setId, PubConstant.NATION_TYPE_4);
		if(list1!=null){
			array.addAll(list1);
		}
		if(list2!=null){
			array.addAll(list2);
		}
		
		// 随机得到集中的对象
		int randValue = Utils.getRandomNum(setTotalWeight);
		TavernConfig tavernConfig = this.filterTavernConfigList(array, randValue);	
		
		talkResult.setId(tavernConfig.getId());		
		talkResult.setNumber(tavernConfig.getNum());

		int type = tavernConfig.getType();
		if (type == 1) {// 表示道具
			talkResult.setFirstGet(0);
			talkResult.setType(PubConstant.AWARD_PROP);
			Item item = ItemCache.getItemById(tavernConfig.getId());
//			if(item == null)System.err.println("战功抽道具为空，id是"+tavernConfig.getId());
			
			talkResult.setName(item.getName());
		} else if (type == 2) {// 表示武将
			talkResult.setFirstGet(1);
			talkResult.setType(PubConstant.AWARD_HERO);
			HeroConfig heroConfig = HeroConfigCache.getHeroConfigById(tavernConfig.getId());
//			if(heroConfig == null)	System.err.println("武将为空，id是"+tavernConfig.getId());				
			
			talkResult.setName(heroConfig.getName());
		}
		
		// 检查如果是武将，是否已经有，有的话变为将魂
		this.checkHeroRepeatAndChange(role, talkResult);
	}
	
	/**
	 * 根据权值和随机数找到对应的值
	 * @param array
	 * @param randValue
	 * @return
	 * @author wcy 2016年1月6日
	 */
	private TavernConfig filterTavernConfigList(ArrayList<TavernConfig> array ,int randValue){
		int min = 0;
		int max = 0;
		TavernConfig result = null;
		for (TavernConfig tavernConfig : array) {
			int weight = tavernConfig.getProbWeight();
			min = max;
			max += weight;

			if (randValue >= min && randValue < max) {
				result = tavernConfig;
				break;
			}
		}
		return result;
	}
	
	/**
	 * 战功抽武将特例
	 * @return
	 * @author wcy 2016年1月6日
	 */
	private void getExploitSingleTalkHero(Role role,TalkResult talkResult,int setId) {
		Pub pub = role.getPub();
		Set<Integer> heroSet = pub.getHeroMoneySet();
		
		heroSet = this.combineHeroSet(heroSet, role, setId);
		int heroId = randomExploitSingleTalkHeroId(heroSet);
		HeroConfig heroConfig = HeroConfigCache.getHeroConfigById(heroId);
		
		talkResult.setFirstGet(0);
		talkResult.setId(heroId);
		talkResult.setName(heroConfig.getName());
		talkResult.setNumber(1);
		talkResult.setType(PubConstant.AWARD_HERO);		
	}
	
	/**
	 * 随机出战功武将的id
	 * @param heroSet
	 * @return
	 * @author wcy 2016年1月6日
	 */
	private int randomExploitSingleTalkHeroId(Set<Integer> heroSet){
		int count = 0;
		int result = 0;
		int randValue = Utils.getRandomNum(heroSet.size());
		for (Integer heroId : heroSet) {
			if (randValue == count) {
				result = heroId;
				break;
			}
			count++;
		}
		return result;
	}

	/**
	 * 检查是否首抽
	 * @param role
	 * @param currencyType
	 * @return
	 * @author wcy 2016年1月6日
	 */
	private boolean checkFirstTalk(Role role,byte currencyType){
		int totalTalksNumber = this.getTotalTalkTimes(role, currencyType);
		
		if(totalTalksNumber == 1){
			return true;
		}
		return false;
	}

	/**
	 * 获得总共会谈次数
	 * @param role
	 * @param currencyType
	 * @return
	 * @author wcy 2016年1月6日
	 */
	private int getTotalTalkTimes(Role role,byte currencyType){
		Pub pub = role.getPub();
		int totalTalksNumber = 0;
		if(currencyType == PubConstant.TYPE_TALK_EXPLOIT){
			totalTalksNumber = pub.getTotalTalksNumber();
		}else if(currencyType == PubConstant.TYPE_TALK_GOLD){
			totalTalksNumber = pub.getTotalTalksNumber2();
		}
		return totalTalksNumber;
	}
	/**
	 * 增加会谈总次数
	 * @param role
	 * @param currencyType
	 * @author wcy 2016年1月7日
	 */
	private void addTotalTalkTimes(Role role,byte currencyType){
		Pub pub = role.getPub();
		if(currencyType == PubConstant.TYPE_TALK_EXPLOIT){
			pub.setTotalTalksNumber(pub.getTotalTalksNumber()+1);
		}else if(currencyType == PubConstant.TYPE_TALK_GOLD){
			pub.setTotalTalksNumber2(pub.getTotalTalksNumber2()+1);
		}
	}
	
	/**
	 * 检查节点抽
	 * @param role
	 * @param currencyType
	 * @param strategyConfig
	 * @return
	 * @author wcy 2016年1月6日
	 */
	private boolean checkNodeTalk(Role role, byte currencyType) {
		int drawType = this.getDrawType(currencyType, PubConstant.TALK_TYPE_SINGLE);
		TavernStrategyConfig strategyConfig = TavernStrategyConfigCache.getTavenStrategyConfig(drawType);
		int node = strategyConfig.getNode();
		int totalTalksNumber = this.getTotalTalkTimes(role, currencyType);
		if (node != 0 && totalTalksNumber != 0 && totalTalksNumber % node == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 是否是免费单次会谈
	 * @param role
	 * @param currencyType
	 * @param currentTime
	 * @return
	 * @author wcy 2016年1月7日
	 */
	private boolean checkFreeSingleTalk(Role role,byte currencyType,int currentTime){
		int drawType = this.getDrawType(currencyType,PubConstant.TALK_TYPE_SINGLE);
		Pub pub = role.getPub();
		HashMap<Byte, Integer> endTimeMapping = getEndTime(pub);
		Integer endTime = endTimeMapping.get(currencyType);

		int dayTime = TavernStrategyConfigCache.getTavenStrategyConfig(drawType).getDaytime();
		int freeNum = 0;
		boolean isFree = false;
		if (currencyType == PubConstant.TYPE_TALK_EXPLOIT) {
			freeNum = pub.getFreeExploitNum();
			if (freeNum <= dayTime && currentTime >= endTime)
				isFree = true;
		} else if (currencyType == PubConstant.TYPE_TALK_GOLD) {
			freeNum = pub.getFreeGoldNum();
			if (currentTime >= endTime)
				isFree = true;
		}
//		System.out.println("freeNum" + freeNum);
		return isFree;
	}
	
	/**
	 * 检查是否保底抽 前9次没有A级将，给保底抽 前9次有节点抽不给保底抽
	 * 
	 * @param currencyType
	 * @param index
	 * @param size
	 * @param hasAHero
	 * @param hasNode
	 * @return
	 * @author wcy 2016年1月6日
	 */
	private boolean checkRemedyTalk(byte currencyType, int index, int size, boolean hasAHero, boolean hasNode) {
		// 最后一次抽
		if (index + 1 == size) {
			int multipleDrawType = this.getDrawType(currencyType, PubConstant.TALK_TYPE_MULTIPLE);
			int singleDrawType = this.getDrawType(currencyType, PubConstant.TALK_TYPE_SINGLE);
			TavernStrategyConfig multipleStrategyConfig = TavernStrategyConfigCache
					.getTavenStrategyConfig(multipleDrawType);
			TavernStrategyConfig singleStrategyConfig = TavernStrategyConfigCache
					.getTavenStrategyConfig(singleDrawType);
			boolean requestNode = false;
			boolean requestRemedy = false;
			// 是否要求节点抽
			requestNode = singleStrategyConfig.getNode() != 0;
			// 是否要求保底抽
			requestRemedy = multipleStrategyConfig.getRemedySet() != 0;

			if (requestNode) {
				// 是否有节点抽
				if (!hasNode) {
					if (requestRemedy) {
						if (!hasAHero) {
							return true;
						}
					}
				}
			} else {
				if (requestRemedy) {
					if (!hasAHero) {
						return true;
					}
				}
			}

		}
		return false;
	}
	
	/**
	 * 检查武将是否重复，重复则变成将魂
	 * @param role
	 * @param talkResult
	 * @author wcy 2016年1月7日
	 */
	private boolean checkHeroRepeatAndChange(Role role,TalkResult talkResult){
		if (talkResult.getType() == PubConstant.AWARD_HERO && role.getHeroMap().containsKey(talkResult.getId())) {
			int heroConvertHeroSoul = GeneralNumConstantCache.getValue("HERO_CONVERT_HEROSOUL");			
			talkResult.setId(talkResult.getId() + HeroConstant.HERO_STAR_PROP_INITITIAL_INDEX);
			talkResult.setName(ItemCache.getItemById(talkResult.getId()).getName());
			talkResult.setType(PubConstant.AWARD_PROP);
			talkResult.setNumber(heroConvertHeroSoul);
			return true;
		}
		return false;
	}
	/**
	 * 将酒馆升级产出的武将加上抽取集中的武将，集中的武将包括本国的和群雄将，返回一个新的对象，不影响原来的集
	 * @param set
	 * @return
	 * @author wcy 2016年1月4日
	 */
	private Set<Integer> combineHeroSet(Set<Integer> pubHeroSet,Role role,int set){
		Set<Integer> heroSet = new HashSet<>();
		if (pubHeroSet != null) {
			heroSet.addAll(pubHeroSet);
		}
		ArrayList<TavernConfig> list = TavernConfigCache.getTavernConfigMapBySet(set, role.getNation());
		if (list != null) {
			for (TavernConfig tavernConfig : list) {// 国家将
				int id = tavernConfig.getId();
				heroSet.add(id);
			}
		}
		list = TavernConfigCache.getTavernConfigMapBySet(set, 0);
		if (list != null) {
			for (TavernConfig tavernConfig : list) {// 群雄将
				int id = tavernConfig.getId();
				heroSet.add(id);
			}
		}
		return heroSet;
	}

	/**
	 * 保存免费会谈时间
	 * 
	 * @param currencyType
	 * @param pub
	 * @param currentTime
	 * @author wcy
	 */
	private void saveFreeTalkTime(byte currencyType, Pub pub, int currentTime) {
		if (currencyType == PubConstant.TYPE_TALK_GOLD) {
			pub.setFreeGoldStartTalkTime(currentTime);			
		} else if (currencyType == PubConstant.TYPE_TALK_EXPLOIT) {
			pub.setFreeMoneyStartTalkTime(currentTime);			
		}
	}

	public Message multipleTalk(Role role, IoSession is, byte currencyType) {
		Message message = new Message();
		message.setType(PubConstant.MULTIPLE_TALK_PUB);

		// 如果玩家等级未到达10级
//		if (role.getLv() < PubConstant.ENTER_PUB_LV) {
//			message.putShort(ErrorCode.NO_LV);
//			return message;
//		}
//
//		// 如果国家没有国际则返回
//		if (role.getNation() == (byte) 0) {
//			message.putShort(ErrorCode.NO_NATION);
//			return message;
//		}

		int mutliDrawType = getDrawType(currencyType, PubConstant.TALK_TYPE_MULTIPLE);
		TavernStrategyConfig multipleConfig = TavernStrategyConfigCache.getTavenStrategyConfig(mutliDrawType);
		int cost = multipleConfig.getCost();
		ArrayList<TalkResult> talkResults = new ArrayList<>(PubConstant.MULTIPLE_TALK_TIMES);
		for (int i = 0; i < PubConstant.MULTIPLE_TALK_TIMES; i++) {
			talkResults.add(new TalkResult());
		}

		/**
		 * 花钱抽奖
		 */
		// 检查钱是否足够
		if (currencyType == PubConstant.TYPE_TALK_EXPLOIT) {
			if (role.getMoney() < cost) {
				message.putShort(ErrorCode.NO_EXPLOIT);
				return message;
			}
		} else if (currencyType == PubConstant.TYPE_TALK_GOLD) {
			if (role.getGold() < cost) {
				message.putShort(ErrorCode.NO_GOLD);
				return message;
			}
		}
		// 允许抽奖
		if (currencyType == PubConstant.TYPE_TALK_EXPLOIT) {
			roleService.addRoleMoney(role, -cost);
		} else if (currencyType == PubConstant.TYPE_TALK_GOLD) {
			roleService.addRoleGold(role, -cost);
//			role.getPub().setTalkMile(pub.getTalkMile() + PubConstant.MULTIPLE_GOLD_SEND_MILE);
		}
		message.putShort(ErrorCode.SUCCESS);
		message.put(currencyType);
		message.putInt(multipleConfig.getCost());
//		if (currencyType == PubConstant.TYPE_TALK_EXPLOIT) {
//			message.putInt(0);
//		} else if (currencyType == PubConstant.TYPE_TALK_GOLD) {
//			message.putInt(PubConstant.MULTIPLE_GOLD_SEND_MILE);
//		}
		message.put((byte) talkResults.size());

		randomResults(talkResults, currencyType, role);
		role.getPub().setChang(true);
		for (TalkResult talkResult : talkResults) {
			reckonTalk(role, is, talkResult, currencyType);
			message.put(talkResult.getType());
			message.putInt(talkResult.getId());
			message.putInt(talkResult.getNumber());
			message.putString(talkResult.getName());
		}

		return message;
	}

	/**
	 * 携带武将扩充功能
	 * 
	 * @author xjd
	 */
	public Message pubExpandHero(Role role) {
		Message message = new Message();

		return message;
	}

	/**
	 * 酒馆登用功能
	 * 
	 * @author xjd
	 */
	public Message pubUsedHero(Role role, int id) {
		Message message = new Message();

		return message;
	}

	/**
	 * 武将离队
	 * 
	 * @author xjd
	 */
	public Message pubHeroDequeue(Role role, int id) {
		Message message = new Message();
		message.setType(PubConstant.HERO_DEQUEUE);
		Map<Integer, Hero> map = role.getHeroMap();
		if (map.size() <= PubConstant.MIN_HERO_LIST) {
			message.putShort(ErrorCode.ERR_MIN_HERO_LIST);
			return message;
		}
		Hero hero = map.get(id);
		if (hero == null) {
			return null;
		}
		// 判断武将是否上阵
		for (Map.Entry<Integer, FormationData> formationData : role.getFormationData().entrySet()) {
			if (formationData.getValue().getData().containsValue(hero.getHeroId())) {
				for (Map.Entry<Byte, Integer> m2 : formationData.getValue().getData().entrySet()) {
					if (m2.getValue() == hero.getHeroId()) {
//						System.out.println(m2.getKey());
						formationData.getValue().getData().put(m2.getKey(), 2);// 将上阵武将位置改为已解锁
					}
				}

				// formationData.getValue().getData().put(formationData.getValue().getData(),
				// value)
				// message.putShort(ErrorCode.AREADY_IN_FORMATION);
				// return message;
			}
		}
		Collection<Integer> temp = hero.getEquipMap().values();
		boolean flag = this.propService.uninstallEquipAuto(role, hero.getHeroId(), temp);
		if (!flag) {
			message.putShort(ErrorCode.PROP_IS_EQUIP);
			return message;
		}

		hero.setStatus(PubConstant.SATUTS_HERO_DEQUEUE);
		map.remove(hero.getHeroId());

		hero.setChange(true);
		role.setChange(true);
		this.taskService.checkPubTask(role, (byte) 2);

		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

	/**
	 * 获取玩家酒馆信息
	 * 
	 * @author xjd
	 */
	public Message getPubInfo(Role role) {
		Message message = new Message();
		message.setType(PubConstant.GET_PUB_INFO);
		Pub pub = role.getPub();
		Build build = role.getBuild();
		Short lv = build.getBuildLvMap().get(InComeConstant.TYPE_PUB);
		if(lv == null){
			message.putShort(ErrorCode.BUILD_NO_LOCK);
			return message;
		}
		
		TavernStrategyConfig singleExploit = TavernStrategyConfigCache.getTavenStrategyConfig(PubConstant.TALK_SINGLE_EXPLOIT);
		TavernStrategyConfig singleGold = TavernStrategyConfigCache.getTavenStrategyConfig(PubConstant.TALK_SINGLE_GOLD);
		TavernStrategyConfig multiGold = TavernStrategyConfigCache.getTavenStrategyConfig(PubConstant.TALK_MULIPLE_GOLD);
		
		message.putInt(singleExploit.getCost());
		message.putInt(singleGold.getCost());
		message.putInt(multiGold.getCost());
		
		Set<Integer> exploitHeroSet = this.combineHeroSet(pub.getHeroMoneySet(), role, singleExploit.getHeroSet());
		message.put((byte) exploitHeroSet.size());
		for (Integer integer : exploitHeroSet) {
			message.putInt(integer);
		}
		
		Set<Integer> goldHeroSet = this.combineHeroSet(pub.getHeroGoldSet(), role, singleGold.getHeroSet());
		message.put((byte) goldHeroSet.size());
		for (Integer integer : goldHeroSet) {
			message.putInt(integer);
		}

		HashMap<Byte, Integer> timePeriodMapping = getTimePeriod(pub);

		message.putInt(timePeriodMapping.get(PubConstant.TYPE_TALK_EXPLOIT));
		message.putInt(timePeriodMapping.get(PubConstant.TYPE_TALK_GOLD));
		
		Set<Integer> visitSet = this.combineHeroSet(null, role, multiGold.getVisitSet());
		
		message.putInt(visitSet.size());
		for(Integer visitHeroId:visitSet){
			HeroConfig config = HeroConfigCache.getHeroConfigById(visitHeroId);
			int minR = config.getMinR();
			int maxR = config.getMaxR();
			message.putInt(visitHeroId);
			message.put((byte) minR);
			message.put((byte) maxR);
		}
		
		return message;
	}

	@Override
	public Message enterPub(Role role) {
		Message message = new Message(PubConstant.ENTER_PUB);

		// 如果玩家等级未到达10级
		if (role.getLv() < PubConstant.ENTER_PUB_LV) {
			message.putShort(ErrorCode.NO_LV);
			return message;
		}

		// 如果国家没有国际则返回
		if (role.getNation() == (byte) 0) {
			message.putShort(ErrorCode.NO_NATION);
			return message;
		}

		message.putShort(ErrorCode.SUCCESS);
		HashMap<Byte, Integer> mapping = getTimePeriod(role.getPub());
		int moneyPeriodTime = mapping.get((byte) 1);
		int goldPeriodTime = mapping.get((byte) 2);
		
		TavernStrategyConfig singleExploit = TavernStrategyConfigCache.getTavenStrategyConfig(PubConstant.TALK_SINGLE_EXPLOIT);
		TavernStrategyConfig singleGold = TavernStrategyConfigCache.getTavenStrategyConfig(PubConstant.TALK_SINGLE_GOLD);
		TavernStrategyConfig multiGold = TavernStrategyConfigCache.getTavenStrategyConfig(PubConstant.TALK_MULIPLE_GOLD);
		
		message.putInt(moneyPeriodTime);
		message.putInt(goldPeriodTime);
		message.putInt(singleExploit.getCost());
		message.putInt(singleGold.getCost());
		message.putInt(multiGold.getCost());

		return message;
	}

	@Override
	public Message showPubWeekHeros(Role role, IoSession is) {
		Message message = new Message(PubConstant.GET_PUB_HEROS);
		// 如果玩家等级未到达10级
		if (role.getLv() < PubConstant.ENTER_PUB_LV) {
			message.putShort(ErrorCode.NO_LV);
			return message;
		}

		// 如果国家没有国际则返回
		if (role.getNation() == (byte) 0) {
			message.putShort(ErrorCode.NO_NATION);
			return message;
		}

		TavernShop[] tavernShops = getWeekHeros(role.getNation());

		message.putInt(tavernShops.length);
		for (int i = 0; i < tavernShops.length; i++) {
			TavernShop tavernShop = tavernShops[i];
			int heroId = tavernShop.getHeroID();
			byte get = 0;
			int emotionMax = 0;
			int emotion = 0;
			int rebirthLv = 0;
			int maxRebirthLv = HeroConfigCache.getHeroConfigById(heroId).getMaxR();
			Hero hero = role.getHeroMap().get(heroId);
			message.putInt(heroId);
			message.putInt(tavernShop.getCost());
			if (hero != null) {
				get = 1;
				HeroFavourConfig heroFavourConfig = HeroFavourConfigCache.getHeroFavourConfig(hero.getEmotionLv());
				// TODO
				emotionMax = 0;
				emotion = hero.getEmotion();
				rebirthLv = hero.getRebirthLv();
			}
			message.put(get);
			message.putInt(emotionMax);
			message.putInt(emotion);
			message.putInt(rebirthLv);
			message.putInt(maxRebirthLv);

		}

		return message;
	}

	@Override
	public Message mileConvertHero(Role role, IoSession is, int heroId) {
		Message message = new Message(PubConstant.MILE_CONVERT_HERO);
		// 如果玩家等级未到达10级
		if (role.getLv() < PubConstant.ENTER_PUB_LV) {
			message.putShort(ErrorCode.NO_LV);
			return message;
		}

		// 如果国家没有国际则返回
		if (role.getNation() == (byte) 0) {
			message.putShort(ErrorCode.NO_NATION);
			return message;
		}
		Pub pub = role.getPub();
		int mile = pub.getTalkMile();
		TavernShop tavernShop = TavernShopCache.getTavernShop(heroId);
		int cost = tavernShop.getCost();
		if (mile < cost) {
			message.putShort(ErrorCode.NO_MILE);
			return message;
		}

		message.putShort(ErrorCode.SUCCESS);
		Hero hero = role.getHeroMap().get(heroId);
		if (hero == null) {
			hero = heroService.createHero(role, heroId);
			heroService.addHero(is, hero);
		} else {
			int emotionValue = tavernShop.getNum();
			heroService.addHeroEmotion(role, heroId, emotionValue);
		}
		hero.setChange(true);
		pub.setTalkMile(pub.getTalkMile() - cost);
		message.putInt(cost);
		return message;
	}

	public void initPubMap() {
		for (MapInfo info : MapInfoCache.getMapInfo(1).values()) {
			MapInfoCache.getMap_1_use().put(info.getId(), new MapInfoData(info));
		}

		for (MapInfo info : MapInfoCache.getMapInfo(2).values()) {
			MapInfoCache.getMap_2_use().put(info.getId(), new MapInfoData(info));
		}

		for (MapInfo info : MapInfoCache.getMapInfo(3).values()) {
			MapInfoCache.getMap_3_use().put(info.getId(), new MapInfoData(info));
		}

	}

	/**
	 * 处理玩家会谈获得的武将或者装备
	 * 
	 * @param role
	 * @param talkResult
	 * @author wcy
	 * @param currencyType
	 */
	private void reckonTalk(Role role, IoSession is, TalkResult talkResult, int currencyType) {
		byte talkResultType = talkResult.getType();
		int talkResultNum = talkResult.getNumber();
		int talkResultId = talkResult.getId();

		if (talkResultType == PubConstant.AWARD_HERO) {// 8 武将
			int heroId = talkResultId;
			int emotion = talkResultNum;
			addHero(role, is, heroId, emotion, talkResult);
		} else if(talkResultType == PubConstant.AWARD_PROP){ // 9 其他消耗品			
			propService.addProp(role, talkResultId, PropConstant.FUNCTION_TYPE_2, talkResultNum, is);
		}

	}

	/**
	 * 处理玩家在会谈时获得武将的逻辑 在会谈奖励主逻辑中调用
	 * 
	 * @param role
	 * @param heroId 英雄编号
	 * @param is
	 * @author xjd
	 * @param hero
	 */
	private Hero getHero(Role role, int heroId, IoSession is) {
		// 创建武将实例对象
		Hero hero = heroService.createHero(role, heroId);
		// 通知玩家
		heroService.addHero(is, hero);
		return hero;
	}

	/**
	 * 获得每周英雄
	 * 
	 * @return
	 * @author wcy
	 */
	private TavernShop[] getWeekHeros(int country) {
		ArrayList<TavernShop> array = TavernShopCache.getWeekHeros(country);
		TavernShop[] a = new TavernShop[array.size()];
		return array.toArray(a);
	}

	/**
	 * 增加英雄
	 * 
	 * @param role
	 * @param is
	 * @param heroId
	 * @param emotionValue
	 * @param talkResult
	 * @author wcy
	 */
	private void addHero(Role role, IoSession is, int heroId, int emotionValue, TalkResult talkResult) {
		// 检查有没有该武将
		Hero hero = role.getHeroMap().get(heroId);
		// 武将不在自己的列表中，则到所有英雄中去取到该英雄
		if (hero == null) {
			// 创建武将实例对象
			hero = getHero(role, heroId, is);
			talkResult.setNumber(hero.getEmotion());
			talkResult.setFirstGet(1);

			// 从集中删除
			Pub pub = role.getPub();
//			Set<Integer> goldSet = pub.getHeroGoldSet();
//			Set<Integer> moneySet = pub.getHeroMoneySet();
//			goldSet.remove(heroId);
//			moneySet.remove(heroId);
			pub.setChang(true);
		} else {
			// 设置好感度
			heroService.addHeroEmotion(role, heroId, emotionValue);
			talkResult.setFirstGet(0);
		}

		hero.setChange(true);
	}

	/**
	 * 
	 * @param talkResults
	 * @param currencyType money(1) gold(2)
	 * @param country
	 * @param singleConfig
	 * @param multiConfig
	 * @param pub
	 * @author wcy
	 */
//	private void randomResults(TalkResult[] talkResults, int currencyType, int country,
//			TavernStrategyConfig singleConfig, TavernStrategyConfig multiConfig, Pub pub) {
//		Random rand = new Random();
//		boolean noHero = true;
//		boolean nodeRandom = false;
//
//		for (int t = 0; t < talkResults.length; t++) {
//			int setRandNum = rand.nextInt(10000);
//			int mutliHeroNum = multiConfig.getHeroProb();
//			int multiPropNum = multiConfig.getPropProb();
//			int multiRemedySet = multiConfig.getRemedySet();
//			int totalTalksNumber = pub.getTotalTalksNumber();
//			int singleNodeNum = singleConfig.getNode();
//
//			int set = 0;
//			int countryId = 0;
//			int count = 0;
//			if (singleNodeNum != 0 && totalTalksNumber != 0 && totalTalksNumber % singleNodeNum == 0) {
//				// 节点抽
//				nodeRandom = true;
//				set = singleConfig.getNodeSet();
//				countryId = country;
//				count = TavernConfigCache.getProbWeightCount(set, countryId);
//				if (countryId != 0)
//					count += TavernConfigCache.getProbWeightCount(set, 0);
//			} else if (multiRemedySet != 0 && (t + 1) == talkResults.length && noHero && !nodeRandom) {
//				// 保底抽
//				// 前9次没有A级将，给保底抽
//				// 前9次有节点抽不给保底抽
//				set = multiRemedySet;
//				countryId = country;
//				count = TavernConfigCache.getProbWeightCount(set, countryId);
//				if (countryId != 0)
//					count += TavernConfigCache.getProbWeightCount(set, 0);
//			} else if (setRandNum < mutliHeroNum) {
//				set = multiConfig.getHeroSet();
//				countryId = country;
//				count = TavernConfigCache.getProbWeightCount(set, countryId);
//				if (countryId != 0)
//					count += TavernConfigCache.getProbWeightCount(set, 0);
//			} else if(setRandNum>=mutliHeroNum&&setRandNum<multiPropNum){
//				set = multiConfig.getPropSet();
//				countryId = 0;
//				count = TavernConfigCache.getProbWeightCount(set, countryId);
//			}else{
//				set = multiConfig.getDebrisSet();
//				countryId = country;
//				count = TavernConfigCache.getProbWeightCount(set, countryId);
//				if(countryId!=0)
//					count+=TavernConfigCache.getProbWeightCount(set, 0);
//			}
//
//			ArrayList<TavernConfig> array = TavernConfigCache.getTavernConfigMapBySet(set, countryId);
//			int randNum = Utils.getRandomNum(0, count);
//
//			int accumlate = 0;
//			boolean isExist = false;
//			for (int i = 0; i < array.size(); i++) {
//				TavernConfig con = array.get(i);
//				accumlate += con.getProbWeight();
//				if (randNum > accumlate) {
//					continue;
//				} else {
//					talkResults[t].setId(con.getId());
//
//					if (con.getType() == 1) {// 道具
//						talkResults[t].setType(ItemCache.getItemById(con.getId()).getType());
//						talkResults[t].setName(ItemCache.getItemById(con.getId()).getName());
//					} else if (con.getType() == 2) {// 武将
//						talkResults[t].setType(PubConstant.AWARD_HERO);
//						talkResults[t].setName(HeroConfigCache.getHeroConfigById(con.getId()).getName());
//						if (HeroConfigCache.getHeroConfigById(talkResults[t].getId()).getQuality() >= 3) {
//							noHero = false;
//						}
//
//					}
//					talkResults[t].setNumber(con.getNum());
//					talkResults[t].setFirstGet(0);
//					if (currencyType == 2) {
//						pub.setTotalTalksNumber(totalTalksNumber + 1);
//					}
//					isExist = true;
//					break;
//				}
//			}
//			// 如果是道具集，则以下循环不会进入
//			array = TavernConfigCache.getTavernConfigMapBySet(set, 0);
//			for (int i = 0; i < array.size() && !isExist; i++) {
//				TavernConfig con = array.get(i);
//				accumlate += con.getProbWeight();
//				if (randNum > accumlate) {
//					continue;
//				} else {
//					talkResults[t].setId(con.getId());
//					if (con.getType() == 1) {// 道具
//						talkResults[t].setType(ItemCache.getItemById(con.getId()).getType());
//						talkResults[t].setName(ItemCache.getItemById(con.getId()).getName());
//					} else if (con.getType() == 2) {// 武将
//						talkResults[t].setType(PubConstant.AWARD_HERO);
//						talkResults[t].setName(HeroConfigCache.getHeroConfigById(con.getId()).getName());
//						if (HeroConfigCache.getHeroConfigById(talkResults[t].getId()).getQuality() >= 3) {
//							noHero = false;
//						}
//					}
//					talkResults[t].setNumber(con.getNum());
//					talkResults[t].setFirstGet(0);
//					if (currencyType == 2) {
//						pub.setTotalTalksNumber(totalTalksNumber + 1);
//					}
//
//					break;
//				}
//			}
//
//		}
//
//		// 将最后一个插到随机的前面去
//		int index = Utils.getRandomNum(0, PubConstant.MULTIPLE_TALK_TIMES);
//		TalkResult last = talkResults[PubConstant.MULTIPLE_TALK_TIMES - 1];
//		for (int i = PubConstant.MULTIPLE_TALK_TIMES - 1; i > 0; i--) {
//			if (i == index) {
//				talkResults[i] = last;
//			} else {
//				talkResults[i] = talkResults[i - 1];
//			}
//		}
//
//	}
	
	/**
	 * 
	 * @param talkResults
	 * @param currencyType money(1) gold(2)
	 * @param country
	 * @param singleConfig
	 * @param multiConfig
	 * @param pub
	 * @author wcy
	 */
//	private void randomResults(List<TalkResult> talkResults, byte currencyType, Role role) {
//		Pub pub = role.getPub();
//		int mutliDrawType = getDrawType(currencyType, PubConstant.TALK_TYPE_MULTIPLE);
//		int singleDrawType = getDrawType(currencyType, PubConstant.TALK_TYPE_SINGLE);
//		TavernStrategyConfig multipleConfig = TavernStrategyConfigCache.getTavenStrategyConfig(mutliDrawType);
//		TavernStrategyConfig singleConfig = TavernStrategyConfigCache.getTavenStrategyConfig(singleDrawType);
//		int country = role.getNation();
//		
//		Random rand = new Random();
//		boolean noHero = true;
//		boolean nodeRandom = false;
//		int heroConvertHeroSoul = GeneralNumConstantCache.getValue("HERO_CONVERT_HEROSOUL");
//
//		for (int t = 0; t < talkResults.size(); t++) {
//			TalkResult talkResult = talkResults.get(t);
//			
//			//决定抽到的是武将道具将魂
//			int setRandNum = rand.nextInt(10000);
//			int mutliHeroNum = multipleConfig.getHeroProb();
//			int multiPropNum = multipleConfig.getPropProb();
//			int multiRemedySet = multipleConfig.getRemedySet();
//			int totalTalksNumber = pub.getTotalTalksNumber();
//			int singleNodeNum = singleConfig.getNode();
//
//			int set = 0;
//			int countryId = 0;
//			int totalWeight = 0;
//			if (singleNodeNum != 0 && totalTalksNumber != 0 && totalTalksNumber % singleNodeNum == 0) {
//				// 节点抽
//				nodeRandom = true;
//				set = singleConfig.getNodeSet();
//				countryId = country;
//				totalWeight = TavernConfigCache.getProbWeightCount(set, countryId);
//				if (countryId != 0)
//					totalWeight += TavernConfigCache.getProbWeightCount(set, 0);
//			} else if (multiRemedySet != 0 && (t + 1) == talkResults.size() && noHero && !nodeRandom) {
//				// 保底抽
//				// 前9次没有A级将，给保底抽
//				// 前9次有节点抽不给保底抽
//				set = multiRemedySet;
//				countryId = country;
//				totalWeight = TavernConfigCache.getProbWeightCount(set, countryId);
//				if (countryId != 0)
//					totalWeight += TavernConfigCache.getProbWeightCount(set, 0);
//			} else if (setRandNum < mutliHeroNum) {//武将
//				set = multipleConfig.getHeroSet();
//				countryId = country;
//				totalWeight = TavernConfigCache.getProbWeightCount(set, countryId);
//				if (countryId != 0)
//					totalWeight += TavernConfigCache.getProbWeightCount(set, 0);
//			} else if (setRandNum >= mutliHeroNum && setRandNum < multiPropNum) {//道具
//				set = multipleConfig.getPropSet();
//				countryId = 0;
//				totalWeight = TavernConfigCache.getProbWeightCount(set, countryId);
//			} else {//将魂
//				set = multipleConfig.getDebrisSet();
//				countryId = country;
//				totalWeight = TavernConfigCache.getProbWeightCount(set, countryId);
//				if (countryId != 0)
//					totalWeight += TavernConfigCache.getProbWeightCount(set, 0);
//			}
//			
//			ArrayList<TavernConfig> list = new ArrayList<>();
//			ArrayList<TavernConfig> list1 = TavernConfigCache.getTavernConfigMapBySet(set, country);
//			ArrayList<TavernConfig> list2 = TavernConfigCache.getTavernConfigMapBySet(set, 0);
//			if (list1 != null)
//				list.addAll(list1);
//			if (list2 != null)
//				list.addAll(list2);
//		
//			int randNum = Utils.getRandomNum(0, totalWeight);
//
//			TavernConfig tempTavernConfig = this.filterTavernConfigList(list, randNum);
//			
//			int id = tempTavernConfig.getId();
//			int num = tempTavernConfig.getNum();
//			int type = tempTavernConfig.getType();
//			
//			talkResult.setId(id);
//			talkResult.setNumber(num);
//			if (type == 1) {
//				talkResult.setFirstGet(0);
//				talkResult.setName(ItemCache.getItemById(id).getName());
//				talkResult.setType(PubConstant.AWARD_PROP);
//			} else if (type == 2) {
//				talkResult.setFirstGet(1);
//				talkResult.setName(HeroConfigCache.getHeroConfigById(id).getName());
//				talkResult.setType(PubConstant.AWARD_HERO);
//				
//				// 检查如果是武将，是否已经有，有的话变为将魂
//				if (talkResult.getType() == PubConstant.AWARD_HERO && role.getHeroMap().containsKey(talkResult.getId())) {
//					talkResult.setId(talkResult.getId() + HeroConstant.HERO_STAR_PROP_INITITIAL_INDEX);
//					talkResult.setName(ItemCache.getItemById(talkResult.getId()).getName());
//					talkResult.setType(PubConstant.AWARD_PROP);
//					talkResult.setNumber(heroConvertHeroSoul);
//				}else{
//					noHero = false;
//				}
//				
//			}
//			
//			
//		}
//
//		// 将最后一个插到随机的前面去
//		Collections.swap(talkResults, talkResults.size() - 1,  Utils.getRandomNum(0, talkResults.size()));
//	}

	private void randomResults(List<TalkResult> talkResults, byte currencyType, Role role) {
		int mutliDrawType = this.getDrawType(currencyType, PubConstant.TALK_TYPE_MULTIPLE);
		int singleDrawType = this.getDrawType(currencyType, PubConstant.TALK_TYPE_SINGLE);
		TavernStrategyConfig multipleConfig = TavernStrategyConfigCache.getTavenStrategyConfig(mutliDrawType);
		TavernStrategyConfig singleConfig = TavernStrategyConfigCache.getTavenStrategyConfig(singleDrawType);
		
		boolean hasAHero = false;
		boolean hasNode = false;

		int size = talkResults.size();
		for (int t = 0; t < size; t++) {
			//增加会谈次数
			this.addTotalTalkTimes(role, currencyType);
			TalkResult talkResult = talkResults.get(t);
			
			int setId = 0;
			if (this.checkFirstTalk(role, currencyType)) {
				setId = singleConfig.getFirstDrawSet();
			} else if (this.checkNodeTalk(role, currencyType)) {
				// 节点抽
				hasNode = true;
				setId = singleConfig.getNodeSet();
			} else if (this.checkRemedyTalk(currencyType, t, size, hasAHero, hasNode)) {
				// 保底抽
				setId = multipleConfig.getRemedySet();
			} else {
				int mutliHeroNum = multipleConfig.getHeroProb();
				int multiPropNum = multipleConfig.getPropProb();
				int multiDebrisNum = multipleConfig.getDebrisProb();
				
				int totalWeight = mutliHeroNum + multiPropNum + multiDebrisNum;
				int setRandNum = Utils.getRandomNum(totalWeight);
				if (setRandNum < mutliHeroNum) {// 武将
					setId = multipleConfig.getHeroSet();
				} else if (setRandNum >= mutliHeroNum && setRandNum < (multiPropNum + mutliHeroNum)) {// 道具
					setId = multipleConfig.getPropSet();
				} else {// 将魂
					setId = multipleConfig.getDebrisSet();
				}
			}
			
			int setTotalWeight = TavernConfigCache.getProbWeightCount(setId, role.getNation())
					+ TavernConfigCache.getProbWeightCount(setId, PubConstant.NATION_TYPE_4);
			
			ArrayList<TavernConfig> list = new ArrayList<>();
			ArrayList<TavernConfig> list1 = TavernConfigCache.getTavernConfigMapBySet(setId, role.getNation());
			ArrayList<TavernConfig> list2 = TavernConfigCache.getTavernConfigMapBySet(setId, PubConstant.NATION_TYPE_4);
			if (list1 != null)
				list.addAll(list1);
			if (list2 != null)
				list.addAll(list2);
		
			int randNum = Utils.getRandomNum(setTotalWeight);

			TavernConfig tempTavernConfig = this.filterTavernConfigList(list, randNum);
			
			int id = tempTavernConfig.getId();
			int num = tempTavernConfig.getNum();
			int type = tempTavernConfig.getType();
			
			talkResult.setId(id);
			talkResult.setNumber(num);
			if (type == 1) {//表示道具
				talkResult.setFirstGet(0);
				talkResult.setName(ItemCache.getItemById(id).getName());
				talkResult.setType(PubConstant.AWARD_PROP);
			} else if (type == 2) {//表示武将
				HeroConfig heroConfig = HeroConfigCache.getHeroConfigById(id);
				talkResult.setFirstGet(1);
				talkResult.setName(heroConfig.getName());
				talkResult.setType(PubConstant.AWARD_HERO);
				if (heroConfig.getQuality() >= 3)
					hasAHero = true;
			}
			
			// 检查如果是武将，是否已经有，有的话变为将魂
			this.checkHeroRepeatAndChange(role, talkResult);
		}

		// 将最后一个插到随机的前面去
		Collections.swap(talkResults, talkResults.size() - 1,  Utils.getRandomNum(size));
	}
	
	/**
	 * 
	 * @param currencyType
	 * @param talkType single(0) or multiple(1) talk
	 * @author wcy
	 */
	private int getDrawType(byte currencyType, int talkType) {
		int result = 0;
		if (talkType == PubConstant.TALK_TYPE_SINGLE) {
			if (currencyType == PubConstant.TYPE_TALK_EXPLOIT) {
				result = PubConstant.TALK_SINGLE_EXPLOIT;
			} else if (currencyType == PubConstant.TYPE_TALK_GOLD) {
				result = PubConstant.TALK_SINGLE_GOLD;
			}
		} else if (talkType == PubConstant.TALK_TYPE_MULTIPLE) {
			if (currencyType == PubConstant.TYPE_TALK_EXPLOIT) {
				result =  PubConstant.TALK_MULIPLE_EXPLOIT;
			} else if (currencyType == PubConstant.TYPE_TALK_GOLD) {
				result = PubConstant.TALK_MULIPLE_GOLD;
			}
		}
		return result;

		// HashMap<Integer, Integer> singleTalkMapping = new HashMap<Integer,
		// Integer>();
		// HashMap<Integer, Integer> multipleTalkMapping = new HashMap<Integer,
		// Integer>();
		// singleTalkMapping.put(1, 1);
		// singleTalkMapping.put(2, 3);
		// multipleTalkMapping.put(1, 2);
		// multipleTalkMapping.put(2, 4);
		// HashMap<Integer, HashMap<Integer, Integer>> mapping = new
		// HashMap<Integer, HashMap<Integer, Integer>>();
		// mapping.put(0, singleTalkMapping);
		// mapping.put(1, multipleTalkMapping);
		//
		// return mapping.get(talkType).get(currencyType);

	}

	/**
	 * 获得免费会谈的结束时间
	 * 
	 * @param pub
	 * @param config TavernStrategyConfig
	 * @return HashMap<金币或银币，结束时间>
	 * @author wcy
	 */
	private HashMap<Byte, Integer> getEndTime(Pub pub) {

//		HashMap<Byte, Integer> endTimeMapping = new HashMap<Byte, Integer>();
//		int exploitEndTime = pub.getFreeMoneyStartTalkTime() == 0 ? 0 : pub.getFreeMoneyStartTalkTime()
//				+ PubStrategyConfigCache.getCdByDrawType(PubConstant.TYPE_TALK_EXPLOIT);
//		int goldEndTime = pub.getFreeGoldStartTalkTime() == 0 ? 0 : pub.getFreeGoldStartTalkTime()
//				+ PubStrategyConfigCache.getCdByDrawType(PubConstant.TYPE_TALK_GOLD);
	
		HashMap<Byte, Integer> endTimeMapping = new HashMap<Byte, Integer>();
		int exploitEndTime = pub.getFreeMoneyStartTalkTime() == 0 ? 0 : pub.getFreeMoneyStartTalkTime()
				+ TavernStrategyConfigCache.getTavenStrategyConfig(PubConstant.TALK_SINGLE_EXPLOIT).getCd();
		int goldEndTime = pub.getFreeGoldStartTalkTime() == 0 ? 0 : pub.getFreeGoldStartTalkTime()
				+ TavernStrategyConfigCache.getTavenStrategyConfig(PubConstant.TALK_SINGLE_GOLD).getCd();

		endTimeMapping.put(PubConstant.TYPE_TALK_EXPLOIT, exploitEndTime);
		endTimeMapping.put(PubConstant.TYPE_TALK_GOLD, goldEndTime);
		return endTimeMapping;
	}

	/**
	 * 获得下次免费抽的时间段
	 * 
	 * @param pub
	 * @param currencyType
	 * @return
	 * @author wcy
	 */
	private HashMap<Byte, Integer> getTimePeriod(Pub pub) {
		int nowTime = (int) (System.currentTimeMillis() / 1000);

		HashMap<Byte, Integer> endTimeMapping = getEndTime(pub);
		int exploitEndTime = endTimeMapping.get(PubConstant.TYPE_TALK_EXPLOIT);
		int goldEndTime = endTimeMapping.get(PubConstant.TYPE_TALK_GOLD);

		HashMap<Byte, Integer> periodMapping = new HashMap<Byte, Integer>();
		if (exploitEndTime == 0) {
			periodMapping.put(PubConstant.TYPE_TALK_EXPLOIT, 0);
		} else {
			int period = exploitEndTime - nowTime;
			if (period < 0) {
				period = 0;
			}
			if(pub.getFreeExploitNum()>TavernStrategyConfigCache.getTavenStrategyConfig(PubConstant.TALK_SINGLE_EXPLOIT).getDaytime()){
				periodMapping.put(PubConstant.TYPE_TALK_EXPLOIT, -1);
			}else{
				periodMapping.put(PubConstant.TYPE_TALK_EXPLOIT, period);
			}
		}

		if (goldEndTime == 0) {
			periodMapping.put(PubConstant.TYPE_TALK_GOLD, 0);
		} else {
			int period = goldEndTime - nowTime;
			if (period < 0) {
				period = 0;
			}
			periodMapping.put(PubConstant.TYPE_TALK_GOLD, period);
		}

		return periodMapping;
	}

	@Override
	public Message makeWine(Role role, int riceNum, IoSession is) {
		Message message = new Message();
		message.setType(PubConstant.MAKE_WINES);

		Prop prop = role.getConsumePropMap().get(PubConstant.PROP_RICE_ID);
		if (prop == null) {
			message.putShort(ErrorCode.ERR_NUMBER_SELL);
			return message;
		}
		
		int currentNum = prop.getNum();
		if (currentNum < riceNum) {
			// 没有那么多大米
			message.putShort(ErrorCode.ERR_NUMBER_SELL);
			return message;
		}

		int wineNum = this.convertBigRiceNumToWineNum(riceNum);
		int useRiceNum = wineNum*PubConstant.WINE_NEED_RICE_NUM;
		prop.setNum((short) (prop.getNum() - useRiceNum));
		propService.addProp(role, PubConstant.PROP_WINE_ID, PropConstant.FUNCTION_TYPE_2, wineNum, is);

		int remainWineNum = this.convertBigRiceNumToWineNum((int)prop.getNum());
		prop.setChange(true);

		this.taskService.checkMake(role, PubConstant.BUILD_TYPE_PUB, wineNum);
		message.putShort(ErrorCode.SUCCESS);
		message.putInt(useRiceNum);// 消耗的大米数量
		message.putInt(remainWineNum);// 剩余可以酿造的最大数量
		
		return message;
	}

	/**
	 * 根据大米数量获得可酿造的酒的数量
	 * 
	 * @param riceNum
	 * @return
	 * @author wcy
	 */
	private int convertBigRiceNumToWineNum(int riceNum) {
		int wineNum = riceNum / PubConstant.WINE_NEED_RICE_NUM;
		return wineNum;
	}

	@Override
	public void resetDeskHero(Role role) {
		Pub pub = role.getPub();
		// 获得桌子数量
		byte deskNum = (byte) pub.getDeskMap().size();

		HashMap<Byte, WineDesk> deskHeroMap = pub.getWineDeskMap();
		HashMap<Integer, Hero> tempHeroMap = new HashMap<>();
		Map<Integer, Hero> heroMap = role.getHeroMap();
		for (Hero hero : heroMap.values()) {
			tempHeroMap.put(hero.getHeroId(), hero);
		}

		Random rand = new Random();
		for (byte i = 0; i < deskNum; i++) {
			WineDesk desk = deskHeroMap.get(i);
			if (desk == null) {
				desk = new WineDesk();
				desk.setSeatNum(PubConstant.SEAT_NUM);

				deskHeroMap.put(i, desk);
			}

			if (pub.getDeskMap().get(i) != PubConstant.UNLOCK) {
				// 如果没有解锁，则初始化武将id为0
				for (int j = 0; j < desk.getSeatNum(); j++) {
					desk.getDeskHeroMap().put(j, 0);
				}
				continue;
			}

			desk.setNeedWinesNum(0);
			for (int j = 0; j < desk.getSeatNum(); j++) {
				int size = tempHeroMap.size();
				// 没有武将，则设置id是0
				if (size == 0) {
					desk.getDeskHeroMap().put(j, 0);
					continue;
				}

				int indexId = rand.nextInt(size);
				// 找到该武将,并加入到i号酒桌的j号座位
				int heroId = 0;
				int count = 0;
				for (Hero hero : tempHeroMap.values()) {
					if (count == indexId) {
						heroId = hero.getHeroId();
						desk.getDeskHeroMap().put(j, heroId);
						// 根据武将的质量计算需要消耗的酒的数量
						HeroConfig heroConfig = HeroConfigCache.getHeroConfigById(heroId);
						int quality = heroConfig.getQuality();
						int addWineNum = getQualityConsumeWineDeltaNumMapping(quality)
								+ PubConstant.HERO_DRINK_WINE_BASE_NUM;
						desk.setNeedWinesNum(desk.getNeedWinesNum() + addWineNum);

						break;
					}

					count++;
				}
				// 移除该武将
				tempHeroMap.remove(heroId);
			}

		}

	}

	@Override
	public void resetTalkSpeedUpTimes(Role role) {
		Pub pub = role.getPub();
		byte vipLv = role.getVipLv();
		HashMap<Byte, Integer> freeTalkTimeMap = pub.getFreeTalkTimeMap();

		freeTalkTimeMap.put(PubConstant.TYPE_TALK_GOLD, PubConstant.FREE_GOLD_TALK_SPEED_UP_TIMES
				+ getVipSpeedUpAddTimes(vipLv));
	}

	@Override
	public void resetFreeChangeDeskHeroTimes(Role role) {
		Pub pub = role.getPub();
		pub.setFreeChangeDeskHeroTimes(PubConstant.FREE_CHANGE_DESK_HERO_TIMES);
	}

	/**
	 * 寻访
	 * 
	 * @author xjd
	 */
	public Message visitMap(Role role, int mapId, int heroId) {
		Message message = new Message();
		message.setType(PubConstant.VISIT_PUB);
		long nowTime = System.currentTimeMillis() / 1000;
		inComeService.refreshLevyData(role, nowTime);
		// 上次寻访未结束
		if (!InComeUtils.checkLevy(role, InComeConstant.TYPE_PUB, heroId)) {
			message.putShort(ErrorCode.VISIT_NOT);
			return message;
		}

		// 声望为敌对的城市不能寻访
		int flag = role.getPub().getPubMap().get(mapId).getPrestige() / PubConstant.PRESTIG_VALUE;
		if (flag <= PubConstant.PRESTIG_BAD) {
			message.putShort(ErrorCode.ROOM_ID_ERR);
			return message;
		}
		//检测体力
		if(!inComeService.checkCanSendHero
				(role.getHeroMap().get(heroId), GeneralNumConstantCache.getValue("USE_MANUAL_1")))
		{
			message.putShort(ErrorCode.NO_MANUAL);
			return message;
		}
		
		// 创建 派遣事件
		int cd = this.getVisitCd(HeroUtils.getCaptainValue(role.getHeroMap().get(heroId)));
		LevyInfo levyInfo = new LevyInfo();
		levyInfo.setType(InComeConstant.TYPE_PUB);
		levyInfo.setStartTime(nowTime);
		levyInfo.setValue(mapId);
		levyInfo.setValueOther(role.getPub().getPubMap().get(mapId).getPrestige());
		levyInfo.setHeroId(heroId);
		levyInfo.setCd(cd);

		role.getBuild().getLevyMap().put(heroId, levyInfo);
		role.getBuild().setChange(true);

		this.taskService.checkVisit(role, PubConstant.BUILD_TYPE_PUB);

		message.putShort(ErrorCode.SUCCESS);
		message.putInt(levyInfo.getCd());
		return message;
	}

	@Override
	public Message drinkWine(Role role, byte deskNum) {
		Message message = new Message();
		message.setType(PubConstant.DRINK_WINE);
		Pub pub = role.getPub();
		HashMap<Byte, WineDesk> desks = pub.getWineDeskMap();
		WineDesk desk = desks.get(deskNum);
		if (desk.getIsDrink() == PubConstant.ALREADY_DRINK) {
			// 已经宴请过了
			message.putShort(ErrorCode.ALREADY_DRINK);
			return message;
		}

		// 检查该桌有没有武将
		boolean hasHero = false;
		HashMap<Integer, Integer> deskHeroMap = desk.getDeskHeroMap();
		for (int i = 0; i < desk.getSeatNum(); i++) {
			Integer heroId = deskHeroMap.get(i);
			if (heroId != 0 && heroId != null) {
				hasHero = true;
				break;
			}
		}
		if (!hasHero) {
			message.putShort(ErrorCode.NO_HERO);
			return message;
		}

		int needWineNum = desk.getNeedWinesNum();
		Prop wine = role.getConsumePropMap().get(PubConstant.PROP_WINE_ID);
		if (wine == null || wine.getNum() < needWineNum) {
			message.putShort(ErrorCode.ERR_NUMBER_SELL);
			return message;
		}

		wine.setNum((short) (wine.getNum() - needWineNum));
		wine.setChange(true);

		// 可以宴请
		IoSession is = SessionCache.getSessionById(role.getId());
		message.putShort(ErrorCode.SUCCESS);
		message.putInt(needWineNum);
		HashMap<Integer, Integer> heroArray = new HashMap<>();
		for (Integer heroId : deskHeroMap.values()) {
			if (heroId != 0) {
				// 增加好感度
				Hero hero = role.getHeroMap().get(heroId);
				int propValue = getHeroPropValue(hero);
				// heroService.addHeroEmotion(role, heroId, emotionValue);
				propService.addProp(role, HeroConstant.HERO_STAR_PROP_INITITIAL_INDEX + heroId,
						PropConstant.FUNCTION_TYPE_2, propValue, is);
				heroArray.put(heroId, propValue);
			}
		}
		int size = heroArray.size();
		message.putInt(size);
		for (Entry<Integer, Integer> entry : heroArray.entrySet()) {
			int heroId = entry.getKey();
			int propValue = entry.getValue();
			message.putInt(HeroConstant.HERO_STAR_PROP_INITITIAL_INDEX + heroId);
			message.putInt(propValue);
		}

		this.taskService.checkDesk(role, PubConstant.B_NUM1);
		desk.setIsDrink(PubConstant.ALREADY_DRINK);

		return message;
	}

	private int getHeroPropValue(Hero hero) {
//		HeroConfig heroConfig = HeroConfigCache.getHeroConfigById(hero.getHeroId());
//		int quality = heroConfig.getQuality();
//		int constant = 1;
//
//		HashMap<Integer, Integer> map = new HashMap<>();
//		map.put(4, 7);
//		map.put(3, 5);
//		map.put(2, 3);
//		map.put(1, 1);
//		int value = constant + map.get(quality);

		int randValue = Utils.getRandomNum(0, 10);
		if(randValue<5)
			return 1;
		else if(randValue<8)
			return 2;
		else 
			return 3;	
		
	}

	@Override
	public Message showWineDesk(Role role) {
		Message message = new Message();
		message.setType(PubConstant.SHOW_WINE_DESK);
		Pub pub = role.getPub();
		byte deskNum = (byte) pub.getDeskMap().size();
		HashMap<Byte, WineDesk> wineDeskMap = pub.getWineDeskMap();
		byte vipLv = role.getVipLv();
		message.putInt(pub.getFreeChangeDeskHeroTimes());
		message.put(deskNum);
		for (byte i = 0; i < deskNum; i++) {
			WineDesk desk = wineDeskMap.get(i);
			byte isDrink = desk.getIsDrink();
			int needWineNum = desk.getNeedWinesNum();
			HashMap<Byte, Byte> deskMap = pub.getDeskMap();
			byte deskStatus = deskMap.get(i);

			if (vipLv >= getDeskIndexNeedVipLv(i) && deskStatus == PubConstant.CAN_NOT_USE) {
				deskMap.put(i, PubConstant.LOCK);
				pub.setChang(true);
			}

			deskStatus = deskMap.get(i);

			HashMap<Integer, Integer> deskHeroMap = desk.getDeskHeroMap();

			message.put(isDrink);
			message.putInt(needWineNum);
			message.put(deskStatus);
			for (int j = 0; j < desk.getSeatNum(); j++) {
				Integer heroId = deskHeroMap.get(j);
				if (heroId == null)
					heroId = 0;
				message.putInt(heroId);
			}
		}

		return message;

	}

	@Override
	public Message showMakeWine(Role role) {
		Message message = new Message();
		message.setType(PubConstant.SHOW_MAKE_WINE);
		
		int nowTime = Utils.getNowTime();
		inComeService.checkHeroManual(role,nowTime);
		Build build = role.getBuild();
		Short pubLv = build.getBuildLvMap().get(InComeConstant.TYPE_PUB);
		if(pubLv == null){
			message.putShort(ErrorCode.BUILD_NO_LOCK);
			return message;
		}
		MainBuildingConfig mainBuildingConfig = MainBuildingConfigCache.getMainBuildingConfig(
				InComeConstant.TYPE_FORGE, pubLv);
		int cdTime = mainBuildingConfig.getCd();

		// 最大酿酒数
		Map<Integer, Prop> consumePropMap = role.getConsumePropMap();
		Prop rice = consumePropMap.get(PubConstant.PROP_RICE_ID);

		int riceNum = rice != null ? rice.getNum() : 0;
		int maxWineNum = convertBigRiceNumToWineNum(riceNum);

		// 刷新领取数量
		refreshGetWines(role, nowTime);
		int getWineNum = build.getInComeCacheMap().get(InComeConstant.TYPE_PUB);

		// 剩余时间
		Map<Byte, Long> lastIncomeTimeMap = build.getBuildLastIncomeTimeMap();
		long startTime = lastIncomeTimeMap.get(InComeConstant.TYPE_PUB);
		int remainTime = (int) (cdTime - (nowTime - startTime) % cdTime);

		// 每小时产量
		int inCome = inComeService.getHourIncomeValue(role, InComeConstant.TYPE_PUB);

		// 当前酒和米的数量
		Prop wines = role.getConsumePropMap().get(PubConstant.PROP_WINE_ID);

		int winesNum = 0;
		if (wines != null) {
			winesNum = wines.getNum();
		}

		message.putInt(maxWineNum);
		message.putInt(remainTime);
		message.putInt(getWineNum);
		message.putInt(inCome);
		message.putInt(winesNum);
		message.putInt(riceNum);
		message.putInt(PubConstant.WINE_NEED_RICE_NUM);

		return message;
	}

	@Override
	public void refreshGetWines(Role role, long nowTime) {
//		Build build = role.getBuild();
//		Short pubLv = build.getBuildLvMap().get(InComeConstant.TYPE_PUB);
//		if(pubLv == null){
//			return ;
//		}
//		MainBuildingConfig mainBuildingConfig = MainBuildingConfigCache.getMainBuildingConfig(InComeConstant.TYPE_PUB,
//				pubLv);
//		int cdTime = mainBuildingConfig.getCd();
//
//		int inCome = inComeService.getHourIncomeValue(role, InComeConstant.TYPE_PUB);
//		Map<Byte, Long> lastIncomeTimeMap = build.getBuildLastIncomeTimeMap();
//		long startTime = lastIncomeTimeMap.get(InComeConstant.TYPE_PUB);
//
//		// 可领取次数
//		int getWineTimes = (int) (nowTime - startTime) / cdTime;
//		// 可领取数量
//		int getWineNum = getWineTimes * inCome;
//		Map<Byte, Integer> incomeCacheMap = build.getInComeCacheMap();
//		Integer cache = incomeCacheMap.get(InComeConstant.TYPE_PUB);
//		getWineNum += cache;
//
//		int maxCapacity = mainBuildingConfig.getCapacity();
//		if (getWineNum > maxCapacity) {
//			getWineNum = maxCapacity;
//		}
//
//		incomeCacheMap.put(InComeConstant.TYPE_PUB, getWineNum);
//		lastIncomeTimeMap.put(InComeConstant.TYPE_PUB, startTime + getWineTimes * cdTime);
		inComeService.refreshGetResources(role, nowTime, InComeConstant.TYPE_PUB);
	}

	/**
	 * 获取寻访奖励
	 * 
	 * @author xjd
	 */

	public Message getVisitAward(Role role, int id, IoSession is) {
		Message message = new Message();
		message.setType(PubConstant.GET_VISIT_AWARD);
		// 判断ID是否合法
		if (!role.getPub().getVisitDataMap().containsKey(id)) {
			message.putShort(ErrorCode.ROOM_ID_ERR);
			return message;
		}

		// 根据规则随机奖励
		VisitData visitData = role.getPub().getVisitDataMap().get(id);
		// 根据城市ID和声望值选择Po
		int flag = visitData.getPrestige() / PubConstant.PRESTIG_VALUE;
		int badOrGod = this.getGoodOrBadNum(role.getHeroMap().get(visitData.getHeroId()));
		MapInfoItemData chooesBean = this.randomAward(role, visitData.getCityId(), flag, badOrGod);
		byte reType = PubConstant.NATION_TYPE_4;
		if (chooesBean == null)
			return null;
		int chooes = chooesBean.getId();

		// 处理奖励
		if (chooes < PubConstant.EQUIP_FLAG) {
			reType = PubConstant.TYPE_HERO;
			// 武将
			HeroConfig heroConfig = HeroConfigCache.getHeroConfigById(chooes);
			if (heroConfig.getQuality() <= PubConstant.PRESTIG_GOOD) {
				role.getPub().getHeroMoneySet().add(chooes);
			} else {
				role.getPub().getHeroGoldSet().add(chooes);
			}
		} else if (chooes >= PubConstant.EQUIP_FLAG && chooes < PubConstant.ITEM_FLAG) {
			// 装备
			reType = PubConstant.TYPE_EQUIP;
			propService.addProp(role, chooes, PubConstant.B_NUM1, chooesBean.getNum(), is);
		} else if (chooes >= PubConstant.ITEM_FLAG) {
			// 道具
			reType = PubConstant.TYPE_ITEM;
			propService.addProp(role, chooes, PubConstant.B_NUM2, chooesBean.getNum(), is);
		}

		// 清除数据
		role.getPub().getVisitDataMap().remove(id);
		role.getPub().setChang(true);

		message.putShort(ErrorCode.SUCCESS);
		message.put(reType);
		message.putInt(chooes);
		message.putInt(chooesBean.getNum());
		message.putInt(visitData.getHeroId());
		return message;
	}

	/**
	 * 获取寻访城市信息
	 * 
	 * @author xjd
	 */
	public Message getMapInfo(Role role) {
		Message message = new Message();
		message.setType(PubConstant.GET_MAPINFO);

		message.putString(role.getPub().getHeroMoney());
		message.putString(role.getPub().getHeroGold());

		message.putInt(role.getPub().getPubMap().size());
		for (PubMapInfo x : role.getPub().getPubMap().values()) {

			message.putInt(x.getMapId());
			message.putInt(x.getPrestige());
			message.putInt(x.getPrestige() / PubConstant.PRESTIG_VALUE);
		}
		return message;
	}

	@Override
	public Message changeDeskHeroByDeskNum(Role role, byte deskIndex) {
		Message message = new Message();
		message.setType(PubConstant.CHANGE_DESK_HERO);

		Pub pub = role.getPub();

		changeDeskHero(role, deskIndex);
		WineDesk desk = pub.getWineDeskMap().get(deskIndex);
		desk.setIsDrink(PubConstant.NOT_DRINK);

		// 检查换一批是否有英雄
		boolean notExist = true;
		for (Integer heroId : desk.getDeskHeroMap().values()) {
			if (heroId != 0 && heroId != null) {
				notExist = false;
			}
		}
		if (notExist) {
			message.putShort(ErrorCode.NO_HERO);
			return message;
		}

		// 检查是否可以免费换一批
		int freeChangeTimes = pub.getFreeChangeDeskHeroTimes();
		if (freeChangeTimes > 0) {
			freeChangeTimes--;
			pub.setFreeChangeDeskHeroTimes(freeChangeTimes);
		} else {
			int gold = role.getGold();
			// 检查是否是vip
			byte vipLv = role.getVipLv();
			int needCostGold = vipLv > 0 ? PubConstant.CHANGE_DESK_HERO_COST_GOLD_VIP
					: PubConstant.CHANGE_DESK_HERO_COST_GOLD;
			if (gold < needCostGold) {
				message.putShort(ErrorCode.NO_GOLD);
				return message;
			}
			roleService.addRoleGold(role, -PubConstant.CHANGE_DESK_HERO_COST_GOLD);
		}

		message.putShort(ErrorCode.SUCCESS);
		HashMap<Integer, Integer> deskHeroMap = desk.getDeskHeroMap();
		for (int i = 0; i < desk.getSeatNum(); i++) {
			message.putInt(deskHeroMap.get(i));
		}
		message.putInt(pub.getFreeChangeDeskHeroTimes());
		message.putInt(desk.getNeedWinesNum());

		return message;
	}

	/**
	 * 换一批英雄
	 * 
	 * @param role
	 * @param deskIndex
	 * @author wcy
	 */
	private void changeDeskHero(Role role, byte deskIndex) {
		Pub pub = role.getPub();
		HashMap<Byte, WineDesk> wineDeskMap = pub.getWineDeskMap();
		HashMap<Integer, Hero> tempHeroMap = new HashMap<>();
		Map<Integer, Hero> heroMap = role.getHeroMap();
		for (Hero hero : heroMap.values()) {
			tempHeroMap.put(hero.getHeroId(), hero);
		}

		// 删除其他桌上的武将
		for (Map.Entry<Byte, WineDesk> byteWineDeskEntry : wineDeskMap.entrySet()) {
			byte index = byteWineDeskEntry.getKey();
			WineDesk desk = byteWineDeskEntry.getValue();
			if (deskIndex != index) {
				HashMap<Integer, Integer> map = desk.getDeskHeroMap();
				for (Integer heroId : map.values()) {
					tempHeroMap.remove(heroId);
				}
			}
		}

		Random rand = new Random();
		WineDesk desk = wineDeskMap.get(deskIndex);
		if (desk == null) {
			desk = new WineDesk();
			desk.setSeatNum(PubConstant.SEAT_NUM);
			wineDeskMap.put(deskIndex, desk);
		}

		desk.setNeedWinesNum(0);
		for (int j = 0; j < desk.getSeatNum(); j++) {
			int size = tempHeroMap.size();
			// 没有武将，则设置id是0
			if (size == 0) {
				desk.getDeskHeroMap().put(j, 0);
				continue;
			}

			int indexId = rand.nextInt(size);
			// 找到该武将,并加入到酒桌的j号座位
			int heroId = 0;
			int count = 0;
			for (Hero hero : tempHeroMap.values()) {
				if (count == indexId) {
					heroId = hero.getHeroId();
					desk.getDeskHeroMap().put(j, heroId);
					// 根据武将的质量计算需要消耗的酒的数量
					HeroConfig heroConfig = HeroConfigCache.getHeroConfigById(heroId);
					int quality = heroConfig.getQuality();
					int addWineNum = getQualityConsumeWineDeltaNumMapping(quality)
							+ PubConstant.HERO_DRINK_WINE_BASE_NUM;
					desk.setNeedWinesNum(desk.getNeedWinesNum() + addWineNum);

					break;
				}
				count++;
			}
			// 移除该武将
			tempHeroMap.remove(heroId);
		}
	}

	/**
	 * 获得不同品阶需要消耗酒的增益数量
	 * 
	 * @return
	 * @author wcy
	 */
	private int getQualityConsumeWineDeltaNumMapping(int quality) {
		HashMap<Integer, Integer> qualityMap = new HashMap<>();
		qualityMap.put(4, 3);
		qualityMap.put(3, 2);
		qualityMap.put(2, 1);
		qualityMap.put(1, 0);
		int add = qualityMap.get(quality);
		return add;
	}

	private int getVisitCd(int heroCaptin) {
		int cd = PubConstant.VISIT_CD - heroCaptin * PubConstant.VISIT_HERO_CUT;
		if (cd < 0) {
			cd = 1;
		}
		return cd;
	}

	/**
	 * 随机寻访奖励
	 * 
	 * @param cityId
	 * @param flag
	 * @return
	 */
	private MapInfoItemData randomAward(Role role, int cityId, int flag, int badOrGod) {
		Map<Integer, MapInfoData> temp = null;
		switch (flag) {
		case PubConstant.PRESTIG_BAD:

			break;
		case PubConstant.PRESTIG_SOSO:
			temp = MapInfoCache.getMap_1_use();
			break;
		case PubConstant.PRESTIG_GOOD:
			temp = MapInfoCache.getMap_2_use();
			break;
		case PubConstant.PRESTIG_GOD:
			temp = MapInfoCache.getMap_3_use();
			break;
		default:
			temp = MapInfoCache.getMap_3_use();
			break;
		}
		if (temp == null)
			return null;
		MapInfoData tempData = temp.get(cityId);
		if (tempData == null)
			return null;
		Map<Integer, MapInfoItemData> awarMap = null;

		// 选择好坏集合
		switch (badOrGod) {
		case 1:
			awarMap = tempData.getSoso();
			break;
		case 2:
			awarMap = tempData.getGood();
			break;
		default:
			break;
		}

		// 过滤已有武将
		awarMap.keySet().removeAll(role.getHeroMap().keySet());
		// 开始计算权重过程
		int limit = 0;
		for (MapInfoItemData x : awarMap.values()) {
			limit += x.getWeight();
		}
		int r = Utils.getRandomNum(limit);
		int min = 0;
		MapInfoItemData chooes = null;
		for (MapInfoItemData x : awarMap.values()) {
			if (min <= r && r < min + x.getWeight()) {
				chooes = x;
				break;
			}
			min += x.getWeight();
		}
		return chooes;
	}

	/**
	 * 根据武将智力选择集合
	 * 
	 * @param hero
	 * @return
	 */
	private int getGoodOrBadNum(Hero hero) {
		double b = 0.1 + 0.002 * HeroUtils.getIntelValue(hero);
		double r = Math.random();
		if (r <= b) {
			return 2;
		} else {
			return 1;
		}
	}

	@Override
	public Message getWine(Role role, IoSession is) {
		Message message = new Message();
		message.setType(PubConstant.GET_WINES);
		int nowTime = Utils.getNowTime();
		this.inComeService.checkHeroManual(role,nowTime);
		Build build = role.getBuild();
		Short pubLv = build.getBuildLvMap().get(InComeConstant.TYPE_PUB);
		if(pubLv == null){
			message.putShort(ErrorCode.BUILD_NO_LOCK);
			return message;
		}
		MainBuildingConfig buildConfig = MainBuildingConfigCache.getMainBuildingConfig(InComeConstant.TYPE_PUB, pubLv);
		int cdTime = buildConfig.getCd();

		// 获取配属英雄缩减后的cd时间
		
		refreshGetWines(role, nowTime);

		// 剩余时间
		Map<Byte, Long> lastIncomeTimeMap = build.getBuildLastIncomeTimeMap();
		long startTime = lastIncomeTimeMap.get(InComeConstant.TYPE_PUB);
		int remainTime = (int) ((nowTime - startTime) % cdTime);

		Map<Byte, Integer> incomeCacheMap = build.getInComeCacheMap();
		int getWineNum = incomeCacheMap.get(InComeConstant.TYPE_PUB);
		if (getWineNum == 0) {// 检查领取是否是空
			message.putShort(ErrorCode.NO_PROP);
			return message;
		}
		propService.addProp(role, PubConstant.PROP_WINE_ID, PropConstant.FUNCTION_TYPE_2, getWineNum, is);
		// 酒馆缓存清空
		incomeCacheMap.put(InComeConstant.TYPE_PUB, 0);

		inComeService.recordIncomeRank(role, InComeConstant.TYPE_PUB, getWineNum, nowTime);
		message.putShort(ErrorCode.SUCCESS);
		message.putInt(getWineNum);
		message.putInt(remainTime);

		return message;
	}

	@Override
	public Message unlockWineDesk(Role role, byte deskIndex) {
		Message message = new Message();
		message.setType(PubConstant.UNLOCK_DESK);

		Pub pub = role.getPub();
		byte vipLv = role.getVipLv();
		byte needVipLv = this.getDeskIndexNeedVipLv(deskIndex);
		HashMap<Byte, Byte> deskStatusMap = pub.getDeskMap();
		byte status = deskStatusMap.get(deskIndex);
		int needGold = this.getDeskNeedGold(deskIndex);
		// 检查是否已经解锁
		if (status == PubConstant.UNLOCK) {
			message.putShort(ErrorCode.ALREADY_UNLOCK);
			return message;
		}

		// 检查vip等级
		if (vipLv < needVipLv) {
			message.putShort(ErrorCode.NO_VIP_LV);
			return message;
		}

		// 检查金币是否足够
		int gold = role.getGold();
		if (gold < needGold) {
			message.putShort(ErrorCode.NO_GOLD);
			return message;
		}

		// 可以解锁
		deskStatusMap.put(deskIndex, PubConstant.UNLOCK);
		changeDeskHero(role, deskIndex);
		WineDesk desk = pub.getWineDeskMap().get(deskIndex);
		roleService.addRoleGold(role, -needGold);
		pub.setChang(true);

		message.putShort(ErrorCode.SUCCESS);
		HashMap<Integer, Integer> deskHeroMap = desk.getDeskHeroMap();
		for (int i = 0; i < desk.getSeatNum(); i++) {
			message.putInt(deskHeroMap.get(i));
		}

		message.putInt(pub.getFreeChangeDeskHeroTimes());
		message.putInt(desk.getNeedWinesNum());
		message.putInt(needGold);
		return message;
	}

	/**
	 * 获得对酒桌操作需要的等级
	 * 
	 * @param deskIndex
	 * @return
	 * @author wcy
	 */
	private byte getDeskIndexNeedVipLv(byte deskIndex) {
		HashMap<Byte, Byte> vipMap = new HashMap<>();
		vipMap.put(PubConstant.DESK1, PubConstant.DESK1_NEED_VIP_LV);
		vipMap.put(PubConstant.DESK2, PubConstant.DESK2_NEED_VIP_LV);
		vipMap.put(PubConstant.DESK3, PubConstant.DESK3_NEED_VIP_LV);

		return vipMap.get(deskIndex);
	}

	/**
	 * 解锁酒桌所需的金币数量
	 * 
	 * @param deskIndex
	 * @return
	 * @author wcy
	 */
	private int getDeskNeedGold(byte deskIndex) {
		HashMap<Byte, Integer> goldMap = new HashMap<>();
		goldMap.put(PubConstant.DESK1, PubConstant.DESK1_NEED_GOLD);
		goldMap.put(PubConstant.DESK2, PubConstant.DESK2_NEED_GOLD);
		goldMap.put(PubConstant.DESK3, PubConstant.DESK3_NEED_GOLD);
		return goldMap.get(deskIndex);
	}

	@Override
	public void initPub(Role role){
		long nowTime = System.currentTimeMillis() / 1000;
		Build build = role.getBuild();
		build.getBuildLastIncomeTimeMap().put(InComeConstant.TYPE_PUB, nowTime);
//		build.getInComeCacheMap().put(InComeConstant.TYPE_PUB, 0);
		build.addInComeCacheMap(InComeConstant.TYPE_PUB, 0);
		build.setChange(true);
	}
	
	@Override
	public void initPubHero(Role role) {
		Pub pub = role.getPub();
		Build build = role.getBuild();
		Map<Byte, Short> buildLvMap = build.getBuildLvMap();
		Short lv = buildLvMap.get(InComeConstant.TYPE_PUB);
		if(lv == null){
			return;
		}
		PubConfig pubConfig = PubConfigCache.getPubConfigByLv((int) lv);
		ArrayList<Integer> exploitHeroList = pubConfig.getExploitHeroList();
//		ArrayList<Integer> goldHeroList = pubConfig.getGoldHeroList();
//		Set<Integer> goldSet = pub.getHeroGoldSet();
		Set<Integer> moneySet = pub.getHeroMoneySet();
		for (Integer heroId : exploitHeroList) {
			if(heroId == 0)
			{
				continue;
			}
			moneySet.add(heroId);
		}
//		for (Integer heroId : goldHeroList) {
//			goldSet.add(heroId);
//		}

//		Set<Integer> hasHero = getOwnHeroSet(role);
//		goldSet.removeAll(hasHero);
//		moneySet.removeAll(hasHero);
	}

	@Override
	public void pubLvUpAddHero(Role role, int lv) {
		Pub pub = role.getPub();
		PubConfig pubConfig = PubConfigCache.getPubConfigByLv(lv);
		ArrayList<Integer> array = pubConfig.getExploitHeroList();
		Set<Integer> heroSet = pub.getHeroMoneySet();
		for (Integer heroId : array) {
			if (heroId != 0)
				heroSet.add(heroId);
		}

//		array = pubConfig.getGoldHeroList();
//		heroSet = pub.getHeroGoldSet();
//		for (Integer heroId : array) {
//			if (heroId != 0)
//				heroSet.add(heroId);
//		}
		pub.setChang(true);
	}

	/**
	 * 自己武将的集
	 * 
	 * @param role
	 * @return
	 * @author wcy
	 */
	private Set<Integer> getOwnHeroSet(Role role) {
		Set<Integer> hasHero = new HashSet<Integer>();
		for (Integer heroId : role.getHeroMap().keySet()) {
			hasHero.add(heroId);
		}
		return hasHero;
	}

	@Override
	public Message speedUpFreeGoldTalkTime(Role role) {
		Message message = new Message();
		message.setType(PubConstant.SPEED_UP);
		Pub pub = role.getPub();
		Build build = role.getBuild();
		Integer attachHeroId = build.getAttachHeroMap().containsKey(InComeConstant.TYPE_PUB)?
				build.getAttachHeroMap().get(InComeConstant.TYPE_PUB).getHeroId() : null;
		HashMap<Byte, Integer> freeTalkTimeMap = pub.getFreeTalkTimeMap();
		int times = freeTalkTimeMap.get(PubConstant.TYPE_TALK_GOLD);
		// 是否用完
		if (times <= 0) {
			message.putShort(ErrorCode.NO_TIMES);
			return message;
		}
		// 如果可以免费抽奖，则不让加速
		HashMap<Byte, Integer> periodMapping = getTimePeriod(pub);
		Integer period = periodMapping.get(PubConstant.TYPE_TALK_GOLD);
		if (period <= 0) {
			message.putShort(ErrorCode.NO_NEED_SPEED);
			return message;
		}

		// 没有配属武将
		if (attachHeroId == null || attachHeroId == 0) {
			message.putShort(ErrorCode.NO_HERO);
			return message;
		}

		// 可以加速
		Hero attachHero = role.getHeroMap().get(attachHeroId);
		int reduceDeltaTime = inComeService.getPubTalkReduceDelta(attachHero);
		pub.setFreeGoldStartTalkTime(pub.getFreeGoldStartTalkTime() - reduceDeltaTime);

		freeTalkTimeMap.put(PubConstant.TYPE_TALK_GOLD, freeTalkTimeMap.get(PubConstant.TYPE_TALK_GOLD) - 1);
		periodMapping = getTimePeriod(pub);
		int periodTime = periodMapping.get(PubConstant.TYPE_TALK_GOLD);
		pub.setChang(true);

		message.putShort(ErrorCode.SUCCESS);
		message.putInt(periodTime);

		return message;
	}

	private int getVipSpeedUpAddTimes(byte vipLv) {
		HashMap<Byte, Integer> map = new HashMap<>();
		map.put((byte) 0, 0);
		map.put((byte) 1, 1);
		map.put((byte) 2, 2);
		map.put((byte) 3, 3);
		map.put((byte) 4, 4);
		map.put((byte) 5, 5);
		map.put((byte) 6, 6);
		map.put((byte) 7, 7);
		map.put((byte) 8, 8);

		if(vipLv>8){
			vipLv = 8;
		}
		return map.get(vipLv);
	}

}
