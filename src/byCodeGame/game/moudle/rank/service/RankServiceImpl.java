package byCodeGame.game.moudle.rank.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.file.BarrackDrillAwardCache;
import byCodeGame.game.cache.file.GeneralNumConstantCache;
import byCodeGame.game.cache.file.LeaderBoardCache;
import byCodeGame.game.cache.file.LeaderRewardCache;
import byCodeGame.game.cache.file.RankRewardCache;
import byCodeGame.game.cache.local.CityCache;
import byCodeGame.game.cache.local.RankCache;
import byCodeGame.game.cache.local.RankCache.RankType;
import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.cache.local.ServerCache;
import byCodeGame.game.cache.local.SessionCache;
import byCodeGame.game.entity.bo.Build;
import byCodeGame.game.entity.bo.City;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Mail;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.bo.Server;
import byCodeGame.game.entity.file.BarrackDrillAward;
import byCodeGame.game.entity.file.LeaderBoard;
import byCodeGame.game.entity.file.LeaderReward;
import byCodeGame.game.entity.po.FormationData;
import byCodeGame.game.entity.po.HeroFightValueRank;
import byCodeGame.game.entity.po.IncomeRank;
import byCodeGame.game.entity.po.KillRank;
import byCodeGame.game.entity.po.OffensiveData;
import byCodeGame.game.entity.po.OwnCityRank;
import byCodeGame.game.entity.po.Rank;
import byCodeGame.game.entity.po.RankReward;
import byCodeGame.game.entity.po.RoleLvRank;
import byCodeGame.game.entity.po.WorldArmy;
import byCodeGame.game.moudle.chat.ChatConstant;
import byCodeGame.game.moudle.chat.service.ChatService;
import byCodeGame.game.moudle.inCome.service.InComeService;
import byCodeGame.game.moudle.mail.service.MailService;
import byCodeGame.game.moudle.prop.PropConstant;
import byCodeGame.game.moudle.rank.RankConstant;
import byCodeGame.game.moudle.role.service.RoleService;
import byCodeGame.game.util.Utils;
import byCodeGame.game.util.comparator.ComparatorHeroFightValue;
import byCodeGame.game.util.comparator.ComparatorHeroFightValueRank;
import byCodeGame.game.util.comparator.ComparatorIncomeRank;
import byCodeGame.game.util.comparator.ComparatorKillRank;
import byCodeGame.game.util.comparator.ComparatorOffensiveData;
import byCodeGame.game.util.comparator.ComparatorOwnCityRank;
import byCodeGame.game.util.comparator.ComparatorRoleLvRank;
import byCodeGame.game.util.comparator.ComparatorWorldLv;

public class RankServiceImpl implements RankService {
	private MailService mailService;

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	private RoleService roleService;

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	private ChatService chatService;
	public void setChatService(ChatService chatService){
		this.chatService = chatService;
	}

	private InComeService inComeService;
	public void setInComeService(InComeService inComeService){
		this.inComeService = inComeService;
	}
	@Override
	public int addOffensive(Role role, int nowTime, int hurtValue) {
		Map<Integer, Rank> offensiveDataMap = RankCache.getCurrentRankMapByRankType(RankType.OFFENSIVE);

		OffensiveData data = (OffensiveData) offensiveDataMap.get(role.getId());
		if (data == null) {
			data = new OffensiveData();
			data.setHurtValue(hurtValue);
			data.setOffensiveTime(nowTime);
			data.setRoleId(role.getId());

			offensiveDataMap.put(role.getId(), data);
		} else {
			data.setOffensiveTime(nowTime);
			data.setHurtValue(data.getHurtValue() + hurtValue);
		}

		return data.getHurtValue();
	}

	@Override
	public List<Rank> sortOffensive() {
		Map<Integer, Rank> offensiveDataMap = RankCache.getCurrentRankMapByRankType(RankType.OFFENSIVE);
		List<Rank> list = new ArrayList<>(offensiveDataMap.values());
		Collections.sort(list, ComparatorOffensiveData.getInstance());

		return list;
	}

	@Override
	public void sendOffensiveAward() {
		// 替换排行榜数据
		List<Rank> rankList = sortOffensive();

		// 发放奖励
		BarrackDrillAward barrackDrillAward = BarrackDrillAwardCache.getFirstBarrackDrillAward();
		int rankNum = 1;

		for (Rank baseRank : rankList) {
			OffensiveData rank = (OffensiveData) baseRank;
			Role role = roleService.getRoleById(rank.getRoleId());
			// 设置排行数字
			rank.setRankNum(rankNum);
			// 检查是否是正无穷，小于0表示正无穷
			if (barrackDrillAward.getMax() > 0) {
				if (!(rankNum >= barrackDrillAward.getMin() && rankNum <= barrackDrillAward.getMax())) {
					barrackDrillAward = BarrackDrillAwardCache.getBarrackDrillAwardByRank(rankNum);
				}
			}
			int propNum = barrackDrillAward.getMedal();
			String title = barrackDrillAward.getTitle();
			String content = barrackDrillAward.getContent();

			int propId = RankConstant.OFFENSIVE_AWARD_PROP;
			byte functionType = PropConstant.FUNCTION_TYPE_2;

			Mail mail = new Mail();
			mail.setTitle(title);
			mail.setContext(content);
			mail.setAttached(functionType + "," + propId + "," + propNum + ";");
			mailService.sendSYSMail(role, mail);

			rankNum++;
		}

		RankCache.setRankRecord(RankType.OFFENSIVE, rankList);

		// 当前排行榜清空
		RankCache.getCurrentRankMapByRankType(RankType.OFFENSIVE).clear();

	}

	@Override
	public void refreshWorldLv() {
		Server server = ServerCache.getServer();
		int worldLvNum = GeneralNumConstantCache.getValue("WORLD_LV_NUM");
		ConcurrentHashMap<Integer, Integer> map = server.getRoleLvMap();
		List<Integer> list = new ArrayList<>(map.values());
		Collections.sort(list, ComparatorWorldLv.getInstance());
		int len = list.size() > worldLvNum ? worldLvNum : list.size();
		double total = 0;
		for (int i = 0; i < len; i++) {
			total += list.get(i);
		}
		int worldLv = (int) Math.floor(total / len);
		server.setWorldLv(worldLv);
	}

	@Override
	public void refreshRoleLvRank(Role role, int nowTime) {
		Map<Integer, Rank> roleLvRankMap = RankCache.getCurrentRankMapByRankType(RankType.ROLE_LV);

		RoleLvRank data = (RoleLvRank) roleLvRankMap.get(role.getId());
		if (data == null) {
			data = new RoleLvRank();
			data.setName(role.getName());
			data.setRoleId(role.getId());

			roleLvRankMap.put(role.getId(), data);
		}
		data.setEnterTime(nowTime);
		data.setRoleLv(role.getLv());
		role.getRankRecordTimeMap().put(RankConstant.RANK_TYPE_ROLE_LV, nowTime);
	}

	@Override
	public List<Rank> sortRoleLvRank() {
		Map<Integer, Rank> map = RankCache.getCurrentRankMapByRankType(RankType.ROLE_LV);
		List<Rank> list = new ArrayList<>(map.values());
		Collections.sort(list, ComparatorRoleLvRank.getInstance());
		return list;
	}

	@Override
	public void sendRoleLvRankAward() {
		// 排行榜排序
		List<Rank> list = this.sortRoleLvRank();

		int rankNum = 1;
		for (Rank rank : list) {
			rank.setRankNum(rankNum);
			rankNum++;
		}
		// 保存当前排名
		RankCache.setRankRecord(RankType.ROLE_LV, list);
		// 当前排名清空
		RankCache.getCurrentRankMapByRankType(RankType.ROLE_LV).clear();
		
		this.sendRankAward(list, RankConstant.RANK_TYPE_ROLE_LV);
	}

	@Override
	public int addIncomeRank(Role role, int nowTime, byte type, int incomeValue) {
		Map<Integer, Rank> incomeRankMap = RankCache.getCurrentRankMapByRankType(RankType.INCOME);

		IncomeRank data = (IncomeRank) incomeRankMap.get(role.getId());
		if (data == null) {
			data = new IncomeRank();
			data.setName(role.getName());
			data.setRoleId(role.getId());

			incomeRankMap.put(role.getId(), data);
		}
		data.setEnterTime(nowTime);
		data.setIncomeNum(data.getIncomeNum() + incomeValue);
		
		return data.getIncomeNum();
	}

	@Override
	public List<Rank> sortIncomeRank() {
		Map<Integer, Rank> map = RankCache.getCurrentRankMapByRankType(RankType.INCOME);
		List<Rank> list = new ArrayList<>(map.values());
		Collections.sort(list, ComparatorIncomeRank.getInstance());
		return list;
	}
	
	@Override
	public void sortIncomeRank(List<Rank> list){
		Collections.sort(list,ComparatorIncomeRank.getInstance());
	}

	@Override
	public void sendIncomeRankAward() {
		// 排行榜排序
		List<Rank> list = this.sortIncomeRank();

		int rankNum = 1;
		for (Rank rank : list) {
			rank.setRankNum(rankNum);
			rankNum++;
		}
		// 保存当前排名
		RankCache.setRankRecord(RankType.INCOME, list);
		// 当前排名清空
		RankCache.getCurrentRankMapByRankType(RankType.INCOME).clear();
		
		this.sendRankAward(list, RankConstant.RANK_TYPE_INCOME);
	}

	@Override
	public int refreshHeroFightValue(Role role, int nowTime, int heroCount) {
		Map<Integer, Rank> fightValueMap = RankCache.getCurrentRankMapByRankType(RankType.FIGHT_VALUE);

		HeroFightValueRank data = (HeroFightValueRank) fightValueMap.get(role.getId());
		if (data == null) {
			data = new HeroFightValueRank();
			data.setName(role.getName());
			data.setRoleId(role.getId());

			fightValueMap.put(role.getId(), data);
		}
		data.setEnterTime(nowTime);
		int v1 = data.getFightValue();
		int fightValue = getHeroFightValue(role, heroCount);
		data.setFightValue(fightValue);
		int v2 = data.getFightValue();
		if (v1 != v2)
			role.getRankRecordTimeMap().put(RankConstant.RANK_TYPE_FIGHT_VALUE, nowTime);

		return fightValue;
	}

	private int getHeroFightValue(Role role, int heroCount) {
		Map<Integer, Hero> map = role.getHeroMap();
		ArrayList<Hero> heroList = new ArrayList<>(map.values());
		Collections.sort(heroList, ComparatorHeroFightValue.getInstance());

		int fightValue = 0;
		for (int i = 0; i < heroCount && i < heroList.size(); i++) {
			Hero hero = heroList.get(i);
			fightValue += hero.getFightValue();
		}
		return fightValue;
	}

	@Override
	public List<Rank> sortHeroFightValueRank() {
		Map<Integer, Rank> map = RankCache.getCurrentRankMapByRankType(RankType.FIGHT_VALUE);
		List<Rank> list = new ArrayList<>(map.values());
		Collections.sort(list, ComparatorHeroFightValueRank.getInstance());

		return list;
	}

	@Override
	public void sendHeroFightValueAward() {
		List<Rank> list = this.sortHeroFightValueRank();

		int rankNum = 1;
		for (Rank rank : list) {
			rank.setRankNum(rankNum);
			rankNum++;
		}
		// 保存当前排名
		RankCache.setRankRecord(RankType.FIGHT_VALUE, list);
		// 当前排名清空
		RankCache.getCurrentRankMapByRankType(RankType.FIGHT_VALUE).clear();
		
		this.sendRankAward(list, RankConstant.RANK_TYPE_FIGHT_VALUE);
	}

	@Override
	public void refreshOwnCityNum(int nowTime,int winRoleId,int lossRoleId) {
		Map<Integer, Rank> ownCityRankMap = RankCache.getCurrentRankMapByRankType(RankType.OWN_CITY);			
		
		Role winRole = roleService.getRoleById(winRoleId);
		if(winRole == null){
			return;
		}
		OwnCityRank rank = (OwnCityRank) ownCityRankMap.get(winRole.getId());
		if (rank == null) {
			rank = new OwnCityRank();
			rank.setRoleId(winRoleId);
			rank.setName(winRole.getName());

			ownCityRankMap.put(winRole.getId(), rank);
		}
		rank.setOwnCityCount(rank.getOwnCityCount() + 1);
		rank.setEnterTime(nowTime);
		winRole.getRankRecordTimeMap().put(RankConstant.RANK_TYPE_OWN_CITY, nowTime);
		
		Role lossRole = roleService.getRoleById(lossRoleId);
		if(lossRole!=null){
			OwnCityRank rank2 = (OwnCityRank)ownCityRankMap.get(lossRole.getId());
			rank2.setOwnCityCount(rank2.getOwnCityCount() - 1);			
		}
	}

	@Override
	public List<Rank> sortOwnCityRank() {
		Map<Integer, Rank> map = RankCache.getCurrentRankMapByRankType(RankType.OWN_CITY);
		List<Rank> list = new ArrayList<>(map.values());
		Collections.sort(list, ComparatorOwnCityRank.getInstance());

		return list;
	}

	@Override
	public void sendOwnCityAward() {
		List<Rank> list = this.sortOwnCityRank();

		int rankNum = 1;
		for (Rank rank : list) {
			rank.setRankNum(rankNum);
			rankNum++;
		}
		// 保存当前排名
		RankCache.setRankRecord(RankType.OWN_CITY, list);
		// 当前排名清空
		RankCache.getCurrentRankMapByRankType(RankType.OWN_CITY).clear();
		
		this.sendRankAward(list, RankConstant.RANK_TYPE_OWN_CITY);
	}

	@Override
	public void addKillRank(WorldArmy winArmy, WorldArmy lossArmy, int nowTime) {
		int winRoleId = winArmy.getRoleId();
		int lossRoleId = lossArmy.getRoleId();
		Role winRole = roleService.getRoleById(winRoleId);
		if(winRole == null)
			return;
		Role lossRole = roleService.getRoleById(lossRoleId);
		FormationData winData = winArmy.getFormationData();
		FormationData lossData = lossArmy.getFormationData();
		int winFightValue = this.getWorldArmyFightValue(winData, winRole);
		int lossFightValue = this.getWorldArmyFightValue(lossData, lossRole);

		Map<Integer, Rank> killMap = RankCache.getCurrentRankMapByRankType(RankType.KILL);
		KillRank rank = (KillRank) killMap.get(winRole.getId());
		if (rank == null) {
			rank = new KillRank();
			rank.setName(winRole.getName());
			rank.setRoleId(winRoleId);

			killMap.put(winRole.getId(), rank);
		}
		int point = this.getPoint(winFightValue, lossFightValue);
		int v1= rank.getValue();
		rank.setValue(rank.getValue() + point);
		int v2 = rank.getValue();
		rank.setEnterTime(nowTime);
		
		if (v1 != v2)
			winRole.getRankRecordTimeMap().put(RankConstant.RANK_TYPE_KILL, nowTime);

	}

	/**
	 * 获得世界部队的战斗力
	 * 
	 * @param data
	 * @param role
	 * @return
	 * @author wcy 2016年2月25日
	 */
	private int getWorldArmyFightValue(FormationData data, Role role) {
		int fightValue = 0;
		Map<Byte, Integer> map = data.getData();
		for (Integer heroId : map.values()) {
			if (heroId > 2) {
				Hero hero = role.getHeroMap().get(heroId);
				fightValue += hero.getFightValue();
			}
		}
		return fightValue;
	}

	/**
	 * 获得积分
	 * 
	 * @param winFightValue
	 * @param lossFightValue
	 * @return
	 * @author wcy 2016年2月25日
	 */
	private int getPoint(int winFightValue, int lossFightValue) {
		int hightFightValue = Math.max(winFightValue, lossFightValue);
		int lowFightValue = Math.min(winFightValue, lossFightValue);

		int delta = hightFightValue - lowFightValue;
		float rate = (float) delta / lossFightValue;
		int point = 0;
		if (rate > 0.3) {
			if (winFightValue < lossFightValue)
				point = 30;
			else
				point = 1;
		} else if (rate > 0.2 && rate <= 0.3) {

			if (winFightValue < lossFightValue)
				point = 25;
			else
				point = 2;
		} else if (rate > 0.1 && rate <= 0.2) {
			if (winFightValue < lossFightValue)
				point = 15;
			else
				point = 3;
		} else if (rate >= 0 && rate <= 0.1) {
			if (winFightValue < lossFightValue)
				point = 5;
			else
				point = 3;
		}
		return point;
	}

	@Override
	public void sendKillRankAward() {
		List<Rank> list = this.sortKillRank();

		int rankNum = 1;
		for (Rank rank : list) {
			rank.setRankNum(rankNum);

			rankNum++;
		}
		// 保存当前排名
		RankCache.setRankRecord(RankType.KILL, list);
		// 当前排名清空
		RankCache.getCurrentRankMapByRankType(RankType.KILL).clear();
		
		this.sendRankAward(list, RankConstant.RANK_TYPE_KILL);
	}

	@Override
	public List<Rank> sortKillRank() {
		Map<Integer, Rank> killMap = RankCache.getCurrentRankMapByRankType(RankType.KILL);
		List<Rank> list = new ArrayList<>(killMap.values());
		Collections.sort(list, ComparatorKillRank.getInstance());

		return list;
	}

	/**
	 * 进行奖励发送，排名需要已经排好序
	 * @param rankList
	 * @param rankType
	 * @author wcy 2016年3月5日
	 */
	private void sendRankAward(List<Rank> sortedRankList,byte rankType) {
		Map<Integer, RankReward> rankRewardMap = RankRewardCache.getMap().get(rankType);
		int size = rankRewardMap.size();//多少人有排名
		for (int i = 0; i < size; i++) {
			int roleId = sortedRankList.get(i).getRoleId();
			Role role = roleService.getRoleById(roleId);

			//找到对应排名的奖励，排名从1开始所以要加1
			RankReward rankReward = rankRewardMap.get(i+1);

			Mail mail = new Mail();
			mail.setTitle(rankReward.getMailTitle());
			mail.setContext(rankReward.getMailContent());
			mail.setAttached(rankReward.getItem());
			mailService.sendSYSMail(role, mail);
		}
	}
	
	@Override
	public void initRankMap() {
		int nowTime = Utils.getNowTime();
		List<Role> roleList = roleService.getAllRole();
		for(Role role:roleList){
			Map<Byte,Integer> rankRecordTimeMap = role.getRankRecordTimeMap();
			if(rankRecordTimeMap.size() == 0){
				StringBuilder sb = new StringBuilder();
				sb.append(RankConstant.RANK_TYPE_FIGHT_VALUE).append(",").append(nowTime).append(";");
				sb.append(RankConstant.RANK_TYPE_INCOME).append(",").append(nowTime).append(";");
				sb.append(RankConstant.RANK_TYPE_KILL).append(",").append(nowTime).append(";");
				sb.append(RankConstant.RANK_TYPE_OWN_CITY).append(",").append(nowTime).append(";");
				sb.append(RankConstant.RANK_TYPE_ROLE_LV).append(",").append(nowTime).append(";");
				role.setRankRecordTimeStr(sb.toString());
			}
			this.refreshRoleLvRank(role, rankRecordTimeMap.get(RankConstant.RANK_TYPE_ROLE_LV));
			this.refreshHeroFightValue(role, rankRecordTimeMap.get(RankConstant.RANK_TYPE_FIGHT_VALUE), RankConstant.HERO_FIGHT_VALUE_NUM);
			//刷新经营
			inComeService.checkHeroManual(role, nowTime);
			inComeService.refreshAllCache(role, nowTime);
			Map<Byte, Integer> map = role.getBuild().getInComeCacheMap();
			for (Map.Entry<Byte, Integer> entry : map.entrySet()) {
				byte type = entry.getKey();
				this.addIncomeRank(role, nowTime, type, 0);

			}
			
		}
		
		ConcurrentHashMap<Integer, City> allCity = CityCache.getAllCityMap();
		for(City city:allCity.values()){
			this.refreshOwnCityNum(nowTime, city.getCityOw(), -1);
		}
		
		//排名奖励初始化
		this.initAllRankReward();
	}
	
	private void initAllRankReward(){
		Map<Integer, List<LeaderBoard>> allMap = LeaderBoardCache.getLeaderBoardMap();
		for (Entry<Integer, List<LeaderBoard>> entrySet : allMap.entrySet()) {
			int type = entrySet.getKey();
			List<LeaderBoard> list = entrySet.getValue();
			for (LeaderBoard leaderBoard : list) {
				String leader = leaderBoard.getLeader();
				int index = leaderBoard.getIndex();
				LeaderReward leaderReward = LeaderRewardCache.getLeaderRewardById(index);
				String[] minAndMaxRankNum = leader.split(",");
				int minRankNum = Integer.parseInt(minAndMaxRankNum[0]);
				int maxRankNum = Integer.parseInt(minAndMaxRankNum[1]);
				if (minRankNum == maxRankNum) {// 如果相同说明唯一
					RankReward rankReward = this.createRankReward((byte) type, minRankNum, leaderReward);
					RankRewardCache.putRankReward(rankReward);
				} else {// 不相同需要填充
					for (int tempRankNum = minRankNum; tempRankNum <= maxRankNum; tempRankNum++) {
						RankReward rankReward = this.createRankReward((byte) type, tempRankNum, leaderReward);
						RankRewardCache.putRankReward(rankReward);
					}

				}
			}
		}
	}
	
	/**
	 * 创建排行奖励实例
	 * @param type
	 * @param rankNum
	 * @param leaderReward
	 * @return
	 * @author wcy 2016年3月5日
	 */
	private RankReward createRankReward(byte type,int rankNum,LeaderReward leaderReward){
		String mailTitle = leaderReward.getMailTile();
		String mailContent = leaderReward.getMailContent();
		String item = leaderReward.getItem();
		
		RankReward rankReward = new RankReward();
		rankReward.setMailContent(mailContent);
		rankReward.setMailTitle(mailTitle);
		rankReward.setRankNum(rankNum);
		rankReward.setType(type);
		rankReward.setItem(item);
		return rankReward;
	}
	
	

	@Override
	public RankType getRankType(byte type) {
		RankType rankType = null;
		if (type == RankConstant.RANK_TYPE_ROLE_LV)// 等级
			rankType = RankType.ROLE_LV;
		else if (type == RankConstant.RANK_TYPE_INCOME)// 经营
			rankType = RankType.INCOME;
		else if (type == RankConstant.RANK_TYPE_FIGHT_VALUE)// 战力
			rankType = RankType.FIGHT_VALUE;
		else if (type == RankConstant.RANK_TYPE_OWN_CITY)// 主城
			rankType = RankType.OWN_CITY;
		else if (type == RankConstant.RANK_TYPE_KILL)// 击杀
			rankType = RankType.KILL;
		return rankType;
	}
	
	@Override
	public List<Rank> getTopCountList(List<Rank> list,int count) {
		List<Rank> topThreeList = new ArrayList<>();
		for (int i = 0; i < count && i < list.size(); i++) {
			topThreeList.add(list.get(i));
		}
		return topThreeList;
	}
	
	@Override
	public void ifTopRankChangeThenNotable(byte type,List<Rank> originTopList, List<Rank> targetList) {
		int originLen = originTopList.size();
		int targetLen = targetList.size();
		int len = Math.min(originLen, targetLen);

		for (int i = 0; i < len; i++) {
			Rank originRank = originTopList.get(i);
			Rank targetRank = targetList.get(i);
			int targetRoleId = targetRank.getRoleId();
			Role targetRole = roleService.getRoleById(targetRoleId);
			if (originRank.getRoleId() != targetRank.getRoleId()) {// 一旦有不同的则发送

				StringBuilder sb = new StringBuilder();
				for (int j = 0; j < len; j++) {
					byte nation = targetRole.getNation();
					Rank rank = targetList.get(j);
					sb.append(type);
					sb.append(",");
					sb.append(roleService.getRoleById(targetRoleId).getName());
					sb.append(",");
					sb.append(rank.getRoleId());
					sb.append(",");
					sb.append(Utils.getNationName(nation));
					sb.append(";");
				}
				chatService.systemChat(targetRole, ChatConstant.MESSAGE_TYPE_RANKING, sb.toString());
				break;
			}

		}
	}

	@Override
	public void recordAllRoleIncome(int nowTime) {
		ConcurrentMap<Integer, Role> allRole = RoleCache.getRoleMap();
		for (Role role : allRole.values()) {
			// 记录每个建筑的库存
			Build build = role.getBuild();
			//如果用户不在线则刷新，在线则直接取
			if (SessionCache.getSessionById(role.getId()) == null) {
				// 不在线则刷新
				inComeService.checkHeroManual(role, nowTime);
				inComeService.refreshAllCache(role, nowTime);
			}
			for (Map.Entry<Byte, Integer> entrySet : build.getInComeCacheMap().entrySet()) {
				byte type = entrySet.getKey();
				int cache = entrySet.getValue();
				int remainCache = build.getIncomeLastDayCacheMap().get(type);
				int recordCache = cache;
				if (!this.isGetResourceBefore(role, type))
					// 没有收过资源，则今天的量需要减去之前的
					recordCache = cache - remainCache;
				this.addIncomeRank(role, nowTime, type, recordCache);

				build.getIncomeLastDayCacheMap().put(type, remainCache + cache);
			}
		}
	}

	/**
	 * 是否收过资源
	 * 
	 * @param remainCache
	 * @return
	 * @author wcy 2016年2月29日
	 */
	private boolean isGetResourceBefore(Role role, byte type) {
		Build build = role.getBuild();
		int remainCache = build.getIncomeLastDayCacheMap().get(type);
		if (remainCache != 0) {
			return false;
		}
		return true;
	}
	
	@Override
	public List<Rank> refreshAllIncomeCache(int nowTime) {		
		ConcurrentMap<Integer, Role> allRoleMap = RoleCache.getRoleMap();
		Map<Integer, Rank> rankMap = RankCache.getCurrentRankMapByRankType(RankType.INCOME);
		List<Rank> currentIncomeRankList = new ArrayList<>();
		
		for (Role role : allRoleMap.values()) {
			//刷新所有的池
			inComeService.checkHeroManual(role, nowTime);
			inComeService.refreshAllCache(role, nowTime);
			
			Build build = role.getBuild();
			
			int lastDayCache= 0;
			Map<Byte,Integer> incomeLastDayMap = build.getIncomeLastDayCacheMap();
			for(Integer value:incomeLastDayMap.values()){
				lastDayCache+=value;
			}
			
			Map<Byte, Integer> incomeCacheMap = build.getInComeCacheMap();
			int cacheValue = 0;
			for (Integer value : incomeCacheMap.values()) {
				cacheValue += value;
			}
			if(cacheValue<0){
				cacheValue=Integer.MAX_VALUE;
			}
			
			IncomeRank incomeRank = (IncomeRank)rankMap.get(role.getId());
			IncomeRank tempIncomeRank = new IncomeRank();
			int todayCache = cacheValue - lastDayCache;
			if (incomeRank == null) {
				tempIncomeRank.setEnterTime(nowTime);
				tempIncomeRank.setIncomeNum(todayCache);
				tempIncomeRank.setName(role.getName());
				tempIncomeRank.setRoleId(role.getId());
			} else {
				tempIncomeRank.setEnterTime(incomeRank.getEnterTime());
				tempIncomeRank.setIncomeNum(incomeRank.getIncomeNum());
				tempIncomeRank.setName(incomeRank.getName());
				tempIncomeRank.setRankNum(incomeRank.getRankNum());
				tempIncomeRank.setRoleId(incomeRank.getRoleId());

				tempIncomeRank.setIncomeNum(tempIncomeRank.getIncomeNum() + todayCache);
			}
			currentIncomeRankList.add(tempIncomeRank);
			
		}
		
		return currentIncomeRankList;
	}
}
