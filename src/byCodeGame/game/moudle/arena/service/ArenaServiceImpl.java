package byCodeGame.game.moudle.arena.service;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.file.ArenaLvConfigCache;
import byCodeGame.game.cache.file.ArmsConfigCache;
import byCodeGame.game.cache.file.HeroConfigCache;
import byCodeGame.game.cache.file.RoleScienceConfigCache;
import byCodeGame.game.cache.file.SkillConfigCache;
import byCodeGame.game.cache.local.ArenaCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.db.dao.ArenaDao;
import byCodeGame.game.db.dao.LadderArenaDao;
import byCodeGame.game.db.dao.RoleArenaDao;
import byCodeGame.game.entity.bo.Arena;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.LadderArena;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.bo.RoleArena;
import byCodeGame.game.entity.file.ArenaLvConfig;
import byCodeGame.game.entity.file.ArmsConfig;
import byCodeGame.game.entity.file.HeroConfig;
import byCodeGame.game.entity.file.RoleScienceConfig;
import byCodeGame.game.entity.file.SkillConfig;
import byCodeGame.game.entity.po.ArenaFightData;
import byCodeGame.game.entity.po.ArenaObjFightData;
import byCodeGame.game.entity.po.ArenaTarget;
import byCodeGame.game.moudle.arena.ArenaConstant;
import byCodeGame.game.moudle.arena.fsm.control.Control;
import byCodeGame.game.moudle.arena.msg.ArenaFightMsg;
import byCodeGame.game.moudle.arena.msg.AtkArenaFightMsg;
import byCodeGame.game.moudle.arena.msg.BuffArenaFightMsg;
import byCodeGame.game.moudle.arena.msg.DeadArenaFightMsg;
import byCodeGame.game.moudle.arena.msg.MoveArenaFightMsg;
import byCodeGame.game.moudle.arena.msg.OutBattleArenaFightMsg;
import byCodeGame.game.moudle.arena.msg.WaitArenaFightMsg;
import byCodeGame.game.moudle.fight.service.FightService;
import byCodeGame.game.moudle.role.service.RoleService;
import byCodeGame.game.moudle.science.service.ScienceService;
import byCodeGame.game.remote.Message;
import byCodeGame.game.skill.BaseSkill;
import byCodeGame.game.util.Utils;


public class ArenaServiceImpl implements ArenaService {

	private RoleArenaDao roleArenaDao;
	public void setRoleArenaDao(RoleArenaDao roleArenaDao) {
		this.roleArenaDao = roleArenaDao;
	}
	private RoleService roleService;
	public void setRoleService(RoleService roleService){
		this.roleService = roleService;
	}
	private LadderArenaDao ladderArenaDao;
	public void setLadderArenaDao(LadderArenaDao ladderArenaDao) {
		this.ladderArenaDao = ladderArenaDao;
	}
	private DataSource dataSource;
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	private ArenaDao arenaDao;
	public void setArenaDao(ArenaDao arenaDao){
		this.arenaDao = arenaDao;
	}
	private FightService fightService;
	public void setFightService(FightService fightService){
		this.fightService = fightService;
	}

	private ScienceService scienceService;
	public void setScienceService(ScienceService scienceService){
		this.scienceService = scienceService;
	}

	public void arenaInit(){
		Arena arena = arenaDao.getArena();
		if(arena == null){
			arenaDao.insterArena();
		}
		arena = arenaDao.getArena();
		ArenaCache.setArena(arena);
	}

	public void ladderInit(){
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			List<LadderArena> ladderList = ladderArenaDao.getAllLadderArena();
			if(ladderList.size() <= 0){	
				for (int i = 0; i < 365; i++) {
					ladderArenaDao.insterLadderArena(conn);
				}
			}
			ladderList = ladderArenaDao.getAllLadderArena();
			for (LadderArena ladderArena : ladderList) {
				ArenaCache.getLadderMap().put(ladderArena.getRank(), ladderArena);
				ArenaCache.getLadderMap2().put(ladderArena.getRoleId(), ladderArena);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Message setFightHero(Role role,int heroId){
		Message message = new Message();
		message.setType(ArenaConstant.Action.SET_HERO.getVal());

		Map<Integer, Hero> heroMap = role.getHeroMap();
		if(!heroMap.containsKey(heroId)){	//玩家没有该武将
			return null;
		}
		Hero hero = heroMap.get(heroId);
		if(hero.getStatus() == 0){		//武将在酒馆中
			return null;
		}

		RoleArena roleArena = role.getRoleArena();
		roleArena.setHeroId(heroId);
		roleArena.setChange(true);

		message.putShort(ErrorCode.SUCCESS);
		message.putInt(heroId);
		return message;
	}
	
	/**
	 * 获取竞技场目标
	 */
	public Message getTarget(Role role){
		Message message = new Message();
		message.setType(ArenaConstant.Action.GET_TARGET.getVal());
		int rank=getRoleRank(role);
		List<LadderArena> ladderArenaList=getArenaTarget(rank);
		message.put((byte)ladderArenaList.size());
		
		
		for (LadderArena ladderArena : ladderArenaList) {
			message.putInt(ladderArena.getRank());
			message.putString(ladderArena.getRoleName());
			message.putInt(ladderArena.getRoleId());
			message.putInt(ladderArena.getLv());
			message.putInt(ladderArena.getRoleLv());
			
			int i=(int)(Math.random()*(8))+1;
			if(i==1){
				message.putInt(2307);
			}else if(i==2){
				message.putInt(2310);
			}else if(i==3){
				message.putInt(2313);
			}else if(i==4){
				message.putInt(2101);
			}else if(i==5){
				message.putInt(2201);
			}else if(i==6){
				message.putInt(2301);
			}else if(i==7){
				message.putInt(2305);
			}else if(i==8){
				message.putInt(2202);
			}else{
				message.putInt(2202);
			}
			
		}
		return message;
	}
	

	/**
	 * 获取竞技场目标  内部类
	 * @param rank
	 * @return
	 */
	public List<LadderArena> getArenaTarget(int rank){
		List<LadderArena> list=new ArrayList<LadderArena>();
		Map<Integer, LadderArena> ladderArenaMap=ArenaCache.getLadderMap();
		LadderArena ladderArena=new LadderArena();
		if(rank==1){
			
		}else if(rank==2){
			ladderArena= ladderArenaMap.get(1);
			if(ladderArena.getRoleId()==-1){
				ladderArena.setRoleLv(-1);
				ladderArena.setRoleName("机器人");
			}else{
				Role role=roleService.getRoleById(ladderArena.getRoleId());
				ladderArena.setRoleLv(role.getLv());
				ladderArena.setRoleName(role.getName());
			}
			list.add(ladderArena);
		}else if(rank==3){
			ladderArena= ladderArenaMap.get(1);
			if(ladderArena.getRoleId()==-1){
				ladderArena.setRoleLv(-1);
				ladderArena.setRoleName("机器人");
			}else{
				Role role=roleService.getRoleById(ladderArena.getRoleId());
				ladderArena.setRoleLv(role.getLv());
				ladderArena.setRoleName(role.getName());
			}
			list.add(ladderArena);
			ladderArena= ladderArenaMap.get(2);
			if(ladderArena.getRoleId()==-1){
				ladderArena.setRoleLv(-1);
				ladderArena.setRoleName("机器人");
			}else{
				Role role=roleService.getRoleById(ladderArena.getRoleId());
				ladderArena.setRoleLv(role.getLv());
				ladderArena.setRoleName(role.getName());
			}
			list.add(ladderArena);
		}else if(rank>3&&rank<16){
			int x=(int)(Math.random()*(rank-1))+1;
			int y=(int)(Math.random()*(rank-1))+1;
			int z=(int)(Math.random()*(rank-1))+1;
			while(y==x){
				y=(int)(Math.random()*(rank-1))+1;
			}
			while(z==x||z==y){
				z=(int)(Math.random()*(rank-1))+1;
			}
			ladderArena= ladderArenaMap.get(x);
			if(ladderArena.getRoleId()==-1){
				ladderArena.setRoleLv(-1);
				ladderArena.setRoleName("机器人");
			}else{
				Role role=roleService.getRoleById(ladderArena.getRoleId());
				ladderArena.setRoleLv(role.getLv());
				ladderArena.setRoleName(role.getName());
			}
			list.add(ladderArena);
			ladderArena= ladderArenaMap.get(y);
			if(ladderArena.getRoleId()==-1){
				ladderArena.setRoleLv(-1);
				ladderArena.setRoleName("机器人");
			}else{
				Role role=roleService.getRoleById(ladderArena.getRoleId());
				ladderArena.setRoleLv(role.getLv());
				ladderArena.setRoleName(role.getName());
			}
			list.add(ladderArena);
			ladderArena= ladderArenaMap.get(z);
			if(ladderArena.getRoleId()==-1){
				ladderArena.setRoleLv(-1);
				ladderArena.setRoleName("机器人");
			}else{
				Role role=roleService.getRoleById(ladderArena.getRoleId());
				ladderArena.setRoleLv(role.getLv());
				ladderArena.setRoleName(role.getName());
			}
			list.add(ladderArena);
		}else if(rank>=16){
			int x=(int)(Math.random()*5)+1;
			int y=(int)(Math.random()*5)+1;
			int z=(int)(Math.random()*5)+1;
			while(y==x){
				y=(int)(Math.random()*5)+1;
			}
			while(z==x||z==y){
				z=(int)(Math.random()*5)+1;
			}
			ladderArena= ladderArenaMap.get(rank-x);
			if(ladderArena.getRoleId()==-1){
				ladderArena.setRoleLv(-1);
				ladderArena.setRoleName("机器人");
			}else{
				Role role=roleService.getRoleById(ladderArena.getRoleId());
				ladderArena.setRoleLv(role.getLv());
				ladderArena.setRoleName(role.getName());
			}
			list.add(ladderArena);
			ladderArena= ladderArenaMap.get(rank-5-y);
			if(ladderArena.getRoleId()==-1){
				ladderArena.setRoleLv(-1);
				ladderArena.setRoleName("机器人");
			}else{
				Role role=roleService.getRoleById(ladderArena.getRoleId());
				ladderArena.setRoleLv(role.getLv());
				ladderArena.setRoleName(role.getName());
			}
			list.add(ladderArena);
			ladderArena= ladderArenaMap.get(rank-10-z);
			if(ladderArena.getRoleId()==-1){
				ladderArena.setRoleLv(-1);
				ladderArena.setRoleName("机器人");
			}else{
				Role role=roleService.getRoleById(ladderArena.getRoleId());
				ladderArena.setRoleLv(role.getLv());
				ladderArena.setRoleName(role.getName());
			}
			list.add(ladderArena);
		}
		return list;
	}

	/**
	 * 获取天梯排名
	 */
	public Message getLadder(){
		Message message = new Message();
		message.setType(ArenaConstant.Action.Ladder.getVal());
		Map<Integer, LadderArena> ladderMap=getAllLadderArena();
		message.putShort((short)ladderMap.size());
		for (Map.Entry<Integer, LadderArena> ladder2 : ladderMap.entrySet()) {
			LadderArena ladder3=ladder2.getValue();
			message.putInt(ladder3.getRank());
			message.putString(ladder3.getRoleName());
			message.putInt(ladder3.getLv());
			message.putInt(ladder3.getRoleId());
			message.putInt(ladder3.getRoleLv());
		}
		return message;
	}
	
	/**
	 * 获取当前品阶排名
	 */
	public Message getNowLadders(Role role){
		Message message = new Message();
		message.setType(ArenaConstant.Action.NowLadder.getVal());
		LadderArena ladderArena=getLvAndRank(role.getId());
		List<LadderArena> allLadderArenaMap=getNowLadder(ladderArena.getLv());
		message.putShort((short)allLadderArenaMap.size());
		for (LadderArena ladderArena2 : allLadderArenaMap) {
			message.putInt(ladderArena2.getNowRank());
			message.putString(ladderArena2.getRoleName());
			message.putInt(ladderArena2.getLv());
			message.putInt(ladderArena2.getRoleId());
			message.putInt(ladderArena2.getRoleLv());
		}
		return message;
	}
	
	public void refreshTarget(Role role){
		//目标
		Map<Byte, ArenaTarget> targetMap = role.getRoleArena().getTargetMap();
		//当前竞技场等级
		for(Map.Entry<Byte,ArenaTarget> entry : targetMap.entrySet()){
			
//			System.out.println(entry.getKey());
		}
		
		byte nowLv = role.getRoleArena().getLv();
		if(nowLv <= 5){		//列兵~曲长 
			int lowTarget = this.getLowTarget(nowLv);
			int sameTarget = this.getSameTarget(nowLv);
			int highTarget = this.getHighTarget(nowLv);
			ArenaTarget lowArenaTarget = new ArenaTarget();
			ArenaTarget sameArenaTarget = new ArenaTarget();
			ArenaTarget highArenaTarget = new ArenaTarget();

			if(lowTarget <= 0) lowTarget = roleService.randowRole();
			if(sameTarget <= 0) sameTarget = roleService.randowRole();
			if(highTarget <= 0) highTarget = roleService.randowRole();

			lowArenaTarget.setRoleId(lowTarget);
			lowArenaTarget.setPhaseLv((byte)(nowLv - 1));
			sameArenaTarget.setRoleId(sameTarget);
			sameArenaTarget.setPhaseLv((byte)nowLv);
			highArenaTarget.setRoleId(highTarget);
			highArenaTarget.setPhaseLv((byte)(nowLv + 1));
			targetMap.put((byte)1, highArenaTarget);
			targetMap.put((byte)2, sameArenaTarget);
			targetMap.put((byte)3, lowArenaTarget);
		}
		if(nowLv == 5){		//曲长晋级9品
			//随机的3个玩家排名
			Set<Integer> targetSet = new HashSet<Integer>();
			this.randowLastTen(targetSet);
			byte index = 0;
			for(Integer rankId : targetSet){
				index++;
				LadderArena ladderArena = ArenaCache.getLadderMap().get(rankId);
				ArenaTarget arenaTarget = new ArenaTarget();
				if(ladderArena.getRoleId() > 0){
					arenaTarget.setRoleId(ladderArena.getRoleId());
				}else{
					arenaTarget.setRobot(true);
				}
				targetMap.put(index, arenaTarget);
			}
		}
		int roleRank = this.getRoleRank(role);
		if(roleRank > 0){				//玩家在排名中
			if(roleRank > 10){	//10名之外
				//比自己高10个排名的天梯数据集合
				Set<LadderArena> rankIdSet = new HashSet<LadderArena>();
				//从rankIdSet中随机挑选3个的集合
				Set<LadderArena> targetSet = new HashSet<LadderArena>();
				for (int i = roleRank-1; i <= roleRank-10; i--) {
					LadderArena tempLadderArena = ArenaCache.getLadderMap().get(i);
					rankIdSet.add(tempLadderArena);
				}
				this.randowThree(rankIdSet, targetSet);
				byte index = 0;
				ArenaTarget arenaTarget = new ArenaTarget();
				for(LadderArena ladderArena : targetSet){
					index++;
					if(ladderArena.getRoleId() > 0){	
						arenaTarget.setRoleId(ladderArena.getRoleId());
					}else{
						arenaTarget.setRobot(true);
					}
					arenaTarget.setLadderRank(ladderArena.getRank());
					targetMap.put(index, arenaTarget);
				}
			}else{				//十名以内
				if(ArenaCache.getLadderMap().containsKey(nowLv - 1)){
					LadderArena ladderArena = ArenaCache.getLadderMap().get(nowLv - 1);
					ArenaTarget arenaTarget = new ArenaTarget();
					if(ladderArena.getRoleId() > 0){
						arenaTarget.setRoleId(ladderArena.getRoleId());
					}else{
						arenaTarget.setRobot(true);
					}
					arenaTarget.setLadderRank(ladderArena.getRank());
					targetMap.put((byte)1, arenaTarget);
				}
				if(ArenaCache.getLadderMap().containsKey(nowLv - 2)){
					LadderArena ladderArena = ArenaCache.getLadderMap().get(nowLv - 2);
					ArenaTarget arenaTarget = new ArenaTarget();
					if(ladderArena.getRoleId() > 0){
						arenaTarget.setRoleId(ladderArena.getRoleId());
					}else{
						arenaTarget.setRobot(true);
					}
					arenaTarget.setLadderRank(ladderArena.getRank());
					targetMap.put((byte)2, arenaTarget);
				}
				if(ArenaCache.getLadderMap().containsKey(nowLv - 3)){
					LadderArena ladderArena = ArenaCache.getLadderMap().get(nowLv - 3);
					ArenaTarget arenaTarget = new ArenaTarget();
					if(ladderArena.getRoleId() > 0){
						arenaTarget.setRoleId(ladderArena.getRoleId());
					}else{
						arenaTarget.setRobot(true);
					}
					arenaTarget.setLadderRank(ladderArena.getRank());
					targetMap.put((byte)3, arenaTarget);
				}
			}
		}
	}

	/**
	 * 随机选取3个目标
	 * @param rankIdSet
	 * @return
	 */
	private void randowThree(Set<LadderArena> rankIdSet,Set<LadderArena> targetSet){
		LadderArena[] rankArr = rankIdSet.toArray(new LadderArena[0]);
		int randow = Utils.getRandomNum(0, rankArr.length -1);
		LadderArena randowLadderArena = rankArr[randow];
		targetSet.add(randowLadderArena);
		if(targetSet.size() < 3){
			randowThree(rankIdSet, targetSet);
		}
	}

	/**
	 * 随机挑选天梯排名最后10位的3位  集合中的值为rankId而非roleId
	 * @param targetSet
	 */
	private void randowLastTen(Set<Integer> targetSet){
		int randowId = Utils.getRandomNum(291, 300);
		if(!targetSet.contains(randowId)){
			targetSet.add(randowId);
		}
		if(targetSet.size() < 3){
			randowLastTen(targetSet);
		}
	}

	/**
	 * 获取低一阶目标	(列兵)
	 * @param lv
	 * @return
	 */
	private int getLowTarget(int lv){
		int id = 0;
		if(ArenaCache.getArenaSetMap().containsKey(lv - 1)){
			Set<Integer> roleSet = ArenaCache.getArenaSetMap().get(lv - 1);
			Integer[] roleArr = roleSet.toArray(new Integer[0]);
			int randow = Utils.getRandomNum(0, roleArr.length - 1);
			id = roleArr[randow];
		}

		return id;
	}

	/**
	 * 获取相同阶级目标
	 * @param lv
	 * @return
	 */
	private int getSameTarget(int lv){
		int id = 0;
		if(ArenaCache.getArenaSetMap().containsKey(lv)){
			Set<Integer> roleSet = ArenaCache.getArenaSetMap().get(lv);
			Integer[] roleArr = roleSet.toArray(new Integer[0]);
			int randow = Utils.getRandomNum(0, roleArr.length);
			id = roleArr[randow];
		}
		return id;
	}

	/**
	 * 获取高一阶的目标
	 * @param lv
	 * @return
	 */
	private int getHighTarget(int lv){
		int id = 0;
		if(ArenaCache.getArenaSetMap().containsKey(lv + 1)){
			Set<Integer> roleSet = ArenaCache.getArenaSetMap().get(lv + 1);
			Integer[] roleArr = roleSet.toArray(new Integer[0]);
			int randow = Utils.getRandomNum(0, roleArr.length);
			id = roleArr[randow];
		}
		return id;
	}

	/**
	 * 获取玩家所在排名
	 * @param role
	 * @return
	 */
	private int getRoleRank(Role role){
		int rank = 0;
		Map<Integer, LadderArena> ladderMap = ArenaCache.getLadderMap();
		for(Map.Entry<Integer, LadderArena> entry : ladderMap.entrySet()){
			LadderArena tempLadderArena = entry.getValue();
			if(tempLadderArena.getRoleId() == role.getId()){
				rank = tempLadderArena.getRank();
				break;
			}
		}
		return rank;
	}

	public Message dareTarget(Role role,byte index,IoSession is){
		Message message = new Message();
//		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+index);
		message.setType(ArenaConstant.Action.DARE.getVal());
				long nowTime = System.currentTimeMillis() / 1000;
				RoleArena roleArena = role.getRoleArena();
				long cdTime = nowTime - roleArena.getLastFightTime();
				if(cdTime < ArenaConstant.DART_TIME){	//挑战间隔时间未到
					message.putShort(ErrorCode.ARENA_FIGHT_TIME_ERR);
					return message;
				}
				byte maxFightTimes = (byte)(10 + role.getVipLv());
				if(roleArena.getFightTimes() >= maxFightTimes){		//达到最大攻打次数 
					message.putShort(ErrorCode.ARENA_MAX_TIMES);
					return message;
				}
				if(index < 1 || index >3){		
					return null;
				}
				
				ArenaTarget arenaTarget = roleArena.getTargetMap().get(index);
		
		//star
			String uuid = UUID.randomUUID().toString();
			ArenaFightData arenaFightData = new ArenaFightData(0);
			arenaFightData.setUuid(uuid);
			this.countArenaObjData(role, 234234, arenaFightData);
			role.getRoleArena().setLastArenaFightData(arenaFightData);
		//end
		//		if(arenaTarget.getLadderRank() > 0){	//天梯战
		//			if(arenaTarget.isRobot()){
		//				
		//			}else{
		//				
		//			}
		//		}else{		//阶段战
		//			
		//		}
		int x=(int)(Math.random()*(2));
		if(x==1){
			message.putShort(ErrorCode.SUCCESS);
		}else{
			message.putShort(ErrorCode.ERROR);
		}
		message.putString(uuid);
		return message;
	}
	
	/**
	 * 判断对手是否被锁定  被锁定将无法被挑战
	 * @return  为false不可以被挑战
	 */
	public boolean rivalIsLock(int roleRank,int opponentRank){
		List<Integer> ladderLock=ArenaCache.getLadderLock();
		for (Integer integer : ladderLock) {
			if(integer==roleRank||integer==opponentRank){
				return false;
			}
		}
		ArenaCache.getLadderLock().add(roleRank);
		ArenaCache.getLadderLock().add(opponentRank);
		return true;
	}
	/**
	 * 挑战结束 falg为true表示成功排名互换,接触挑战锁定,挑战失败则直接解除挑战锁定
	 * @param falg
	 * @param roleId
	 * @param OpponentId
	 */
	public void theSuccessOf(boolean falg,int roleRank,int opponentRank){
		Map<Integer, LadderArena> ladderMap =ArenaCache.getLadderMap();
		int roleId=ladderMap.get(roleRank).getRoleId();
		int opponentId=ladderMap.get(opponentRank).getRoleId();
		if(falg==true){
			LadderArena ladderArena1=new LadderArena();
			ladderArena1.setRank(roleRank);
			ladderArena1.setRoleId(opponentId);
			LadderArena ladderArena2=new LadderArena();
			ladderArena2.setRank(opponentRank);
			ladderArena2.setRoleId(roleId);
			ArenaCache.getLadderMap().put(roleRank,ladderArena1);
			ArenaCache.getLadderMap().put(opponentRank,ladderArena2);
		}
		ArenaCache.getLadderLock().remove(roleId);
		ArenaCache.getLadderLock().remove(opponentId);
	}
	/**
	 * 竞技场增加经验 
	 * @param role
	 * @param exp
	 */
	private void addArenaExp(Role role,int exp){
		RoleArena roleArena = role.getRoleArena();
		ArenaLvConfig arenaLvConfig = ArenaLvConfigCache.getArenaLvConfig(roleArena.getLv());
		roleArena.setExp(roleArena.getExp() + exp);
		if(roleArena.getExp() >= arenaLvConfig.getNextExp()){	//进入下一阶段
			Set<Integer> oldSet = ArenaCache.getArenaSetMap().get(arenaLvConfig.getLv());
			oldSet.remove(role.getId());
			Set<Integer> newSet = ArenaCache.getArenaSetMap().get(arenaLvConfig.getLv()+1);
			newSet.add(role.getId());
			roleArena.setLv((byte)(roleArena.getLv()+1));
		}
		roleArena.setChange(true);
	}

	/**
	 * 判定竞技场胜利增加的经验值
	 * @param roleLv
	 * @param targetLv
	 * @return
	 */
	private int returnAddExp(byte roleLv,byte targetLv){
		int exp = 0;
		if(roleLv > targetLv){
			exp = 5;
		}else if(roleLv == targetLv){
			exp = 10;
		}else if(roleLv < targetLv){
			exp = 15;
		}

		return exp;
	}

	/**
	 * 阶段战胜利
	 * @param role
	 * @param arenaTarget
	 */
	public void arenaFightVictory(Role role,ArenaTarget arenaTarget){
		RoleArena roleArena = role.getRoleArena();
		byte lv = roleArena.getLv();
		byte targetLv = arenaTarget.getPhaseLv();
		int exp = this.returnAddExp(lv, targetLv);
		this.addArenaExp(role, exp);
	}

	/**
	 * 天梯赛战斗胜利
	 * @param role
	 * @param arenaTarget
	 */
	public void ladderFightVictory(Role role,ArenaTarget arenaTarget){
		//		RoleArena roleArena = role.getRoleArena();
		Map<Integer, LadderArena> ladderMap = ArenaCache.getLadderMap();
		LadderArena targetLadderArena = ladderMap.get(arenaTarget.getLadderRank());
		int targetId = targetLadderArena.getRoleId();
		LadderArena roleLadderArena;
		int roleRank = this.getRoleRank(role);

		if(targetId > 0){
			if(roleRank > 0){
				roleLadderArena = ladderMap.get(roleRank);
				roleLadderArena.setRoleId(targetId);
				roleLadderArena.setChange(true);
			}
			targetLadderArena.setRoleId(role.getId());
			targetLadderArena.setChange(true);
		}else{
			targetLadderArena.setRoleId(role.getId());
			targetLadderArena.setChange(true);
		}

	}

	/**
	 * 竞技场战斗失败
	 * @param role
	 */
	public void arenaFightLose(Role role){
		role.getRoleArena().setContinuousWinTimes((byte)0);
	}

	public Message getArenaData(Role role){
		Message message = new Message();
		message.setType(ArenaConstant.Action.DATA.getVal());
		LadderArena ladderArena=getLvAndRank(role.getId());
		message.putInt(ladderArena.getNowRank());
		message.putInt(ladderArena.getLv());
//		List<LadderArena> allLadderArenaMap=getNowLadder(ladderArena.getLv());
//		message.put((byte)allLadderArenaMap.size());
//		for (LadderArena ladderArena2 : allLadderArenaMap) {
//			message.putInt(ladderArena2.getNowRank());
//			message.putString(ladderArena2.getRoleName());
//			message.putInt(ladderArena2.getLv());
//			message.putInt(ladderArena2.getRoleId());
//			message.putInt(ladderArena2.getRoleLv());
//		}
//		Map<Integer, LadderArena> ladderMap=getAllLadderArena();
//		message.putShort((short)ladderMap.size());
//		for (Map.Entry<Integer, LadderArena> ladder2 : ladderMap.entrySet()) {
//			LadderArena ladder3=ladder2.getValue();
//			message.putInt(ladder3.getRank());
//			message.putString(ladder3.getRoleName());
//			message.putInt(ladder3.getLv());
//			message.putInt(ladder3.getRoleId());
//			message.putInt(ladder3.getRoleLv());
//		}
////		short rank = (short)this.getRoleRank(role);
////		message.putShort(rank);
////		message.put(role.getRoleArena().getLv());
////		message.putInt(role.getRoleArena().getHeroId());
		return message;
	}

	/** 每一帧所使用的毫秒数	 */
	private double FRAME_TIME = 16.6666666;
	/** 竞技场战斗最大时间(秒)	 */
	private int MAX_ARENA_FIGHT_TIME = 30;


	public Message arenaFightBegin(Role role,int targetId,IoSession is){

		Message message = new Message();
		message.setType(ArenaConstant.Action.TEST.getVal());

		boolean gameOver = true;
		//		int frameTimes = 0;
		//战斗时间
		long timeLine = 0;
		//当前行动的阵营 每次切换乘以-1则交换行动阵营
		//		int nowCamp = 1;
		String fightUuid = UUID.randomUUID().toString();
		ArenaFightData arenaFightData = new ArenaFightData(0);
		arenaFightData.setUuid(fightUuid);
		this.countArenaObjData(role, targetId, arenaFightData);
		while(gameOver){
			//计算当前为多少帧
			arenaFightData.addFrames();
			/* 模拟时间*/
			/* 播放第1帧时，  frameTimes = 1     timeLine  = 25   时间为0.025秒  */
			/* 播放第10帧时，  frameTimes = 10    timeLine  = 250  时间为0.25秒  */
			/* 播放第100帧时，  frameTimes = 100    timeLine  = 2500  时间为2.5秒*/
			timeLine = (long)(arenaFightData.getFrames() * FRAME_TIME) ;
			arenaFightData.setNowTime(timeLine);
			//			if(timeLine/1000 >= MAX_ARENA_FIGHT_TIME){	//达到最大时间
			//				gameOver = false;
			//				break;
			//			}
			
			if(!arenaFightData.isPause()){
				for(Map.Entry<String, Control> entry : arenaFightData.getControlMap().entrySet()){
					Control tempControl = entry.getValue();
					tempControl.update();
				}
			}else{
				arenaFightData.checkIsPause();
			}
			if(this.checkFightOver(role, arenaFightData)){
				gameOver = false;
			}
		}

		Map<Integer,List<ArenaFightMsg>> allList = arenaFightData.getMsgList();
		int index = 0;
		for(Map.Entry<Integer,List<ArenaFightMsg>> entry : allList.entrySet()){
			if(entry.getKey() > index){
				index = entry.getKey();
			}
		}

		message.putInt(index);
		for (int i = 0; i <= index+1; i++) {
			message.putInt(i);
			if(allList.containsKey(i)){
				List<ArenaFightMsg> tempList = allList.get(i);
				message.putShort((short)tempList.size());
			}else{
				message.putShort((short)0);
			}

			if(allList.containsKey(i)){
				List<ArenaFightMsg> tempList = allList.get(i);
				if(tempList.size() != 0){
					for(ArenaFightMsg msg : tempList){
						ArenaFightMsg tempMessage = msg;
						byte type = tempMessage.getType();
						if(type == ArenaConstant.State.OUT_BATTLE.getVal()){
							OutBattleArenaFightMsg outMsg = (OutBattleArenaFightMsg)tempMessage;
							message.put(outMsg.getType());
							message.putString(outMsg.getUuid());
							message.putInt(outMsg.getHeroId());
							message.putInt(outMsg.getHp());
							message.putInt(outMsg.getMaxHp());
							message.putDouble(outMsg.getX());
							message.putDouble(outMsg.getY());
						}
						if(type == ArenaConstant.State.MOVE.getVal()){
							MoveArenaFightMsg moveArenaFightMsg = (MoveArenaFightMsg)tempMessage;
							message.put(moveArenaFightMsg.getType());
							message.putString(moveArenaFightMsg.getUuid());
							message.putDouble(moveArenaFightMsg.getX());
							message.putDouble(moveArenaFightMsg.getY());
						}
						if(type == ArenaConstant.State.WAIT.getVal()){
							WaitArenaFightMsg waitArenaFightMsg = (WaitArenaFightMsg)tempMessage;
							message.put(waitArenaFightMsg.getType());
							message.putString(waitArenaFightMsg.getUuid());
						}
						if(type  == ArenaConstant.State.ATK.getVal()){
							AtkArenaFightMsg atkArenaFightMsg = (AtkArenaFightMsg)tempMessage;
							message.put(atkArenaFightMsg.getType());
							message.putString(atkArenaFightMsg.getUuid());
							message.putString(atkArenaFightMsg.getTargetUuid());
							message.putInt(-atkArenaFightMsg.getHurtVal());
						}
						if(type == ArenaConstant.State.DEAD.getVal()){
							DeadArenaFightMsg deadArenaFightMsg = (DeadArenaFightMsg)tempMessage;
							message.put(deadArenaFightMsg.getType());
							message.putString(deadArenaFightMsg.getUuid());
						}
						if(type == ArenaConstant.State.BUFF.getVal()){
							BuffArenaFightMsg deadArenaFightMsg = (BuffArenaFightMsg)tempMessage;
							message.put(deadArenaFightMsg.getType());
							message.putString(deadArenaFightMsg.getUuid());
							message.putInt(deadArenaFightMsg.getBuffId());
							message.putInt(deadArenaFightMsg.getBuffType());
							message.putString(deadArenaFightMsg.getStr());
						}
					}
				}
			}
		}
		return message;
	}
	
	/**
	 * 计算竞技场战斗角色的能力
	 * @param role
	 * @param targetId
	 */
	private void countArenaObjData(Role role,int targetId,ArenaFightData arenaFightData){
		//int heroId = role.getRoleArena().getHeroId();
		int heroId =2500;
		Hero hero = role.getHeroMap().get(heroId);
		HeroConfig heroConfig = HeroConfigCache.getHeroConfigById(hero.getHeroId());
//				Map<Integer, Integer> equipValueMap = fightService.getEquipUpAbility(hero, role);
		//转生次数
		short rebirthLv = hero.getRebirthLv();

		String uuid = UUID.randomUUID().toString();
		ArenaObjFightData arenaObjFightData = new ArenaObjFightData();
		arenaObjFightData.setHeroId(heroId);
		arenaObjFightData.setRoleId(role.getId());
		arenaObjFightData.setUUID(uuid);
		arenaObjFightData.setCamp((byte)1);
		arenaObjFightData.setPosX(0);
		arenaObjFightData.setPosY(510);
		//总血量
		int sumHp = 500;
//		sumHp = sumHp + heroConfig.getHp();
		//		sumHp = sumHp + fightService.getEquipSingleValue(equipValueMap, 5);
		arenaObjFightData.setMaxHp(sumHp);
		arenaObjFightData.setHp(sumHp);
		//总武力
		int sumAtk = (int)(heroConfig.getPowerGrowValue() * rebirthLv);
		sumAtk = sumAtk + heroConfig.getPower();
		arenaObjFightData.setPower(sumAtk);
		//总智力
		int sumInt = (int)(heroConfig.getIntelGrowValue() * rebirthLv);
		sumInt = sumInt + heroConfig.getIntel();
//		arenaObjFightData.setIntPower(sumInt);
//		//攻击速度
//		arenaObjFightData.setAtkDelay(heroConfig.getAtkSpeed());
//		//移动速度
//		double moveSpeed = heroConfig.getMoveSpeed();
//				moveSpeed = moveSpeed + fightService.getEquipSingleValue(equipValueMap, 6);
//		arenaObjFightData.setMoveSpeed((double)moveSpeed);
		//攻击力 （基础力量+力量成长*阶数）*2+（武将等级-1）*6
		int atk = (int)(heroConfig.getPower() + heroConfig.getPowerGrowValue() * rebirthLv * 2);
		atk = atk + ((hero.getLv() - 1) * 6);
		//		atk = atk + fightService.getEquipSingleValue(equipValueMap, 1);
		arenaObjFightData.setAtk(atk);
		//防御力     （基础力量+力量成长*阶数）+（武将等级-1）*2
		int def = (int)(heroConfig.getPower() + heroConfig.getPowerGrowValue() * rebirthLv);
		def = def + (hero.getLv() - 1) * 2;
		//		def = def + fightService.getEquipSingleValue(equipValueMap, 2);
		arenaObjFightData.setDef(def);
		//策略攻击	基础智力+智力成长*阶数）*2+（角色等级-1）*6
		int matk = (int)((heroConfig.getIntel() + heroConfig.getIntelGrowValue() 
				* rebirthLv) * 2);
		matk = matk + (hero.getLv() - 1) * 6;
		//		matk = matk + fightService.getEquipSingleValue(equipValueMap, 3);
		arenaObjFightData.setMatk(matk);
		//策略防御    （基础智力+智力成长）+（角色等级-1）*2
		int mdef = (int)(heroConfig.getIntel() + 
				heroConfig.getIntelGrowValue() * rebirthLv);
		mdef = mdef + (hero.getLv() - 1) * 2;
		//		mdef = mdef + fightService.getEquipSingleValue(equipValueMap, 4);
		arenaObjFightData.setMdef(mdef);
		//技能
		arenaObjFightData.setSkillId(heroConfig.getSkillId());
		//实例化 技能类
		SkillConfig skillConfig = SkillConfigCache.getSkillConfig(heroConfig.getSkillId());
		Class<?> a = null;
		BaseSkill skill = null;
		try {
			String str = "byCodeGame.game.skill." + skillConfig.getClassName();
			a = Class.forName(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			skill = (BaseSkill)a.newInstance();
			skill.setSkillId(heroConfig.getSkillId());
			skill.setArenaFightData(arenaFightData);
			skill.setObjData(arenaObjFightData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		arenaObjFightData.setSkill(skill);

		//攻击距离
//		arenaObjFightData.setAtkDistance(heroConfig.getAtkDistance());


		Control rolControl = new Control(arenaFightData, arenaObjFightData);

		arenaFightData.getControlMap().put(arenaObjFightData.getUUID(), rolControl);
		arenaFightData.getAllObjDataMap().put(arenaObjFightData.getUUID(), arenaObjFightData);
		arenaFightData.getMyObjDataMap().put(arenaObjFightData.getUUID(), arenaObjFightData);

		//实力小兵
		Map<Integer, Set<ArenaObjFightData>> armsFightObjMap =
				this.getHeroTroopData(role, hero, arenaFightData, arenaObjFightData, (byte)1);
		this.countArmsObjData(role, armsFightObjMap, arenaObjFightData, arenaFightData);

		//---------------test---------------
		String uuid2 = UUID.randomUUID().toString();
		ArenaObjFightData targetObjData = new ArenaObjFightData();
		targetObjData.setHeroId(2504);
		targetObjData.setUUID(uuid2);
		targetObjData.setHp(1200);
		targetObjData.setMaxHp(1200);
		targetObjData.setPosX(1280);
		targetObjData.setPosY(510);
		targetObjData.setCamp((byte)-1);
		targetObjData.setAtkDistance(60);
		targetObjData.setAtkDelay(4000);
		targetObjData.setMoveSpeed(1);
		targetObjData.setSkillId(9001);

		//实例化 技能类
		SkillConfig targetSkillConfig = SkillConfigCache.getSkillConfig(9001);
		Class<?> b = null;
		BaseSkill targetSkill = null;
		try {
			String str = "byCodeGame.game.skill." + skillConfig.getClassName();
			b = Class.forName(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			targetSkill = (BaseSkill)a.newInstance();
			targetSkill.setSkillId(9001);
			targetSkill.setArenaFightData(arenaFightData);
			targetSkill.setObjData(targetObjData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		targetObjData.setSkill(targetSkill);

		Control targetControl = new Control(arenaFightData, targetObjData);

		arenaFightData.getControlMap().put(targetObjData.getUUID(), targetControl);
		arenaFightData.getAllObjDataMap().put(targetObjData.getUUID(), targetObjData);
		arenaFightData.getTargetObjDataMap().put(targetObjData.getUUID(), targetObjData);

		role.getRoleArena().setLastArenaFightData(arenaFightData);
	}


	public Message getFightLoad(Role role,String uuid){
		Message message = new Message();
		message.setType(ArenaConstant.Action.LOAD.getVal());

		ArenaFightData arenaFightData = role.getRoleArena().getLastArenaFightData();
		Map<String, ArenaObjFightData> allObj = arenaFightData.getAllObjDataMap();
		message.putInt(allObj.size());
		for(Map.Entry<String, ArenaObjFightData>  entry : allObj.entrySet()){
			ArenaObjFightData tempData = entry.getValue();
			message.putInt(tempData.getHeroId());
			message.putInt(tempData.getSkillId());
		}
		return message;
	}

	public boolean checkFightOver(Role role,ArenaFightData arenaFightData){
		boolean b = false;
		Map<String, ArenaObjFightData> myDataMap = arenaFightData.getTargetObjDataMap();
		Map<String, ArenaObjFightData> targetDataMap = arenaFightData.getMyObjDataMap();
		int num1 = 0;
		int num2 = 0;
		for(Map.Entry<String, ArenaObjFightData> entry : myDataMap.entrySet()){
			if(entry.getValue().getState() == ArenaConstant.State.DEAD.getVal()){
				num1++;
			}
		}
		for(Map.Entry<String, ArenaObjFightData> entry : targetDataMap.entrySet()){
			if(entry.getValue().getState() == ArenaConstant.State.DEAD.getVal()){
				num2++;
			}
		}
		if(num1 >= myDataMap.size()){
			return true;
		}
		if(num2 >= targetDataMap.size()){
			return true;
		}
		return b;
	}

	/**
	 * 获取竞技场武将的兵力并全部转换成ArenaObjFightData</br>
	 * 此方法并未计算战斗对象各项能力数值
	 * @param role
	 * @param hero
	 * @param arenaFightData
	 */
	public Map<Integer,Set<ArenaObjFightData>> getHeroTroopData(Role role,Hero hero,
			ArenaFightData arenaFightData,ArenaObjFightData heroData,byte camp){
		//拥有的兵力集合
		Map<Integer, Integer> troopMap = role.getBarrack().getTroopMap();
		//武将的小兵集合  key:对应兵种  val:该兵种下的所有小兵集合
		Map<Integer,Set<ArenaObjFightData>>  armsFightObjMap 
		= new HashMap<Integer, Set<ArenaObjFightData>>();

		//遍历武将的兵种配置集合
//		for(Map.Entry<Integer, HerosTroops> entry : hero.getTroopMap().entrySet()){
//			HerosTroops tempHerosTroops = entry.getValue();
//			ArmsConfig armsConfig = ArmsConfigCache.getArmsConfigById(tempHerosTroops.getArmsId());
//			//实际拥有的该兵种兵力数量
//			int hasArmsNum = 0;
//			//配置到武将的小兵数量
//			int num = 0;
//			if(troopMap.containsKey(tempHerosTroops.getArmsId())){
//				hasArmsNum = troopMap.get(tempHerosTroops.getArmsId());
//			}
//			//设置数量  同时扣除兵营中的数量
//			if(hasArmsNum >= tempHerosTroops.getNum()){
//				int nowNum = hasArmsNum - tempHerosTroops.getNum();
//				troopMap.put(tempHerosTroops.getArmsId(), nowNum);
//				num = tempHerosTroops.getNum();
//			}else{
//				troopMap.remove(tempHerosTroops.getArmsId());
//				num = hasArmsNum;
//			}
//			//根据实际的兵力数量实例战斗对象
//			for (int i = 0; i < num; i++) {
//				String uuid = UUID.randomUUID().toString();
//				ArenaObjFightData obj = new ArenaObjFightData();
//				obj.setRoleId(role.getId());
//				obj.setHeroId(armsConfig.getId());
//				obj.setCamp(camp);
//				obj.setUUID(uuid);
//				if(camp == (byte)1){		//友方
//					arenaFightData.getMyObjDataMap().put(uuid, obj);
//				}else{						//敌方
//					arenaFightData.getTargetObjDataMap().put(uuid, obj);
//				}
//				arenaFightData.getAllObjDataMap().put(uuid, obj);
//				this.addArmsObj(armsFightObjMap, obj);
//			}
//		}
		return armsFightObjMap;
	}

	/**
	 * 将战斗单位加入集合（小兵)
	 * @param armsFightObj
	 * @param obj
	 */
	private void addArmsObj(Map<Integer,Set<ArenaObjFightData>>  armsFightObjMap,
			ArenaObjFightData obj){

		ArmsConfig armsConfig = ArmsConfigCache.getArmsConfigById(obj.getHeroId());
		Set<ArenaObjFightData> armsDataSet = null;
		if(armsConfig.getFunctionType() == 1){
			if(armsFightObjMap.containsKey(1)){
				armsDataSet = armsFightObjMap.get(1);
			}else{
				armsDataSet = new HashSet<ArenaObjFightData>();
				armsFightObjMap.put(1, armsDataSet);
			}
		}
		if(armsConfig.getFunctionType() == 2){
			if(armsFightObjMap.containsKey(2)){
				armsDataSet = armsFightObjMap.get(2);
			}else{
				armsDataSet = new HashSet<ArenaObjFightData>();
				armsFightObjMap.put(2, armsDataSet);
			}
		}
		if(armsConfig.getFunctionType() == 3){
			if(armsFightObjMap.containsKey(3)){
				armsDataSet = armsFightObjMap.get(3);
			}else{
				armsDataSet = new HashSet<ArenaObjFightData>();
				armsFightObjMap.put(3, armsDataSet);
			}
		}
		if(armsConfig.getFunctionType() == 4){
			if(armsFightObjMap.containsKey(4)){
				armsDataSet = armsFightObjMap.get(4);
			}else{
				armsDataSet = new HashSet<ArenaObjFightData>();
				armsFightObjMap.put(4, armsDataSet);
			}
		}
		armsDataSet.add(obj);
	}

	/**
	 * 计算小兵角色的能力与坐标
	 * @param armsFightObjMap
	 */
	private void countArmsObjData(Role role,Map<Integer,Set<ArenaObjFightData>>  armsFightObjMap,
			ArenaObjFightData heroData,ArenaFightData arenaFightData){
//		for(Map.Entry<Integer,Set<ArenaObjFightData>> entry : armsFightObjMap.entrySet()){
//			Set<ArenaObjFightData> tempSet = entry.getValue();
//			//兵种类型  1步兵 2骑兵 3弓兵 4策士
//			int type = entry.getKey();
//			//该兵种类型对应的 x坐标
//			double x = ArenaConstant.POS_X.get(type) + heroData.getPosX();
//			//该兵种类型的战斗单位数量
//			int num = tempSet.size();
//			//该集合下的第一个战斗单位的Y坐标  随后每个战斗单位-32
//			double baseY = ((num - 1) * 16) + heroData.getPosY();
//			int index = 0;
//			for(ArenaObjFightData tempData : tempSet){
//				//设置X,Y坐标
//				tempData.setPosX(x);
//				tempData.setPosY(baseY - (index * ArenaConstant.POS_Y_SPACING) );
//				index ++;
//				RoleScienceConfig roleScienceConfig = null;
//				ArmsConfig armsConfig = ArmsConfigCache.getArmsConfigById(tempData.getHeroId());
//				//攻击力
////				int atk = armsConfig.getAtk();
//				//防御力
////				int def = armsConfig.getDef();
//				//策略攻击
////				int matk = armsConfig.getMatk();
//				//策略防御
////				int mdef = armsConfig.getMdef();
//				//血量
////				int hp = armsConfig.getHp();
//				int scienceHpLv = scienceService.getRoleScienceById(role, 10);
////				roleScienceConfig = RoleScienceConfigCache.getRoleScienceConfig(10, scienceHpLv);
////				hp += roleScienceConfig.getHp();
//				//移动速度
////				int moveSpeed = armsConfig.getMoveSpeed();
//				//攻击速度
////				int atkSpeed = armsConfig.getAtkSpeed();
//				//攻击距离
////				int atkDistance = armsConfig.getAtkDistance();
//				//技能id
////				int skillId = armsConfig.getSkillId();
//				//攻击科技等级
//				int atkScienceLv = 0;
//				//防御科技等级
//				int defScienceLv = 0;
//				//移动速度科技等级
//				int moveScienceLv = 0;
//				//攻击射程
//				int atkDistanceScienceLv = 0;
//
//				if(armsConfig.getFunctionType() == 1){	//步兵
//					//增加atk matk def mdef hp
////					atkScienceLv = scienceService.getRoleScienceById(role, 4);
////					roleScienceConfig = RoleScienceConfigCache.getRoleScienceConfig(4, atkScienceLv);
//////					atk += roleScienceConfig.getAtk();
//////					matk += roleScienceConfig.getMatk();
////					defScienceLv = scienceService.getRoleScienceById(role, 5);
////					roleScienceConfig = RoleScienceConfigCache.getRoleScienceConfig(5, defScienceLv);
//////					def += roleScienceConfig.getDef();
//////					mdef += roleScienceConfig.getMdef();
////					//步兵血量科技等级
////					int bubingSLv = scienceService.getRoleScienceById(role, 6);
////					roleScienceConfig = RoleScienceConfigCache.getRoleScienceConfig(6, bubingSLv);
//////					hp += roleScienceConfig.getHp();
////				}
////				if(armsConfig.getFunctionType() == 2){	//骑兵
////					//增加 atk matk def mdef moveSpeed
////					atkScienceLv = scienceService.getRoleScienceById(role, 1);
////					roleScienceConfig = RoleScienceConfigCache.getRoleScienceConfig(1, atkScienceLv);
//////					atk += roleScienceConfig.getAtk();
//////					matk += roleScienceConfig.getMatk();
////					defScienceLv = scienceService.getRoleScienceById(role, 2);
////					roleScienceConfig = RoleScienceConfigCache.getRoleScienceConfig(2, defScienceLv);
//////					def += roleScienceConfig.getDef();
//////					mdef += roleScienceConfig.getMdef();
////					moveScienceLv = scienceService.getRoleScienceById(role, 3);
////					roleScienceConfig = RoleScienceConfigCache.getRoleScienceConfig(3, moveScienceLv);
//////					moveSpeed += roleScienceConfig.getSpd();
////				}
////				if(armsConfig.getFunctionType() == 3){	//弓兵
////					//增加  atk matk def mdef atkDistance
////					atkScienceLv = scienceService.getRoleScienceById(role, 7);
////					roleScienceConfig = RoleScienceConfigCache.getRoleScienceConfig(7, atkScienceLv);
//////					atk += roleScienceConfig.getAtk();
//////					matk += roleScienceConfig.getMatk();
////					defScienceLv = scienceService.getRoleScienceById(role, 9);
////					roleScienceConfig = RoleScienceConfigCache.getRoleScienceConfig(9, defScienceLv);
//////					def += roleScienceConfig.getDef();
//////					mdef += roleScienceConfig.getMdef();
////					atkDistanceScienceLv = scienceService.getRoleScienceById(role, 8);
////					roleScienceConfig = RoleScienceConfigCache.getRoleScienceConfig(8, atkDistanceScienceLv);
//////					atkDistance += roleScienceConfig.getRng();
////
////				}
////				if(armsConfig.getFunctionType() == 4){	//策士
////					//增加 atk matk def mdef
////					atkScienceLv = scienceService.getRoleScienceById(role, 11);
////					roleScienceConfig = RoleScienceConfigCache.getRoleScienceConfig(11, atkScienceLv);
//////					atk += roleScienceConfig.getAtk();
//////					matk += roleScienceConfig.getMatk();
////					defScienceLv = scienceService.getRoleScienceById(role, 12);
////					roleScienceConfig = RoleScienceConfigCache.getRoleScienceConfig(12, defScienceLv);
//////					def += roleScienceConfig.getDef();
//////					mdef += roleScienceConfig.getMdef();
////				}
////
//////				tempData.setAtk(atk);
//////				tempData.setMatk(matk);
//////				tempData.setDef(def);
//////				tempData.setMdef(mdef);
//////				tempData.setHp(hp);
//////				tempData.setMaxHp(hp);
//////				tempData.setMoveSpeed(moveSpeed);
//////				tempData.setAtkDelay(atkSpeed);
//////				tempData.setAtkDistance(atkDistance);
//////				tempData.setSkillId(skillId);
////
////				Control control = new Control(arenaFightData, tempData);
////
////				if(tempData.getCamp() == 1){
////					arenaFightData.getMyObjDataMap().put(tempData.getUUID(), tempData);
////				}else{
////					arenaFightData.getTargetObjDataMap().put(tempData.getUUID(), tempData);
////				}
////				arenaFightData.getControlMap().put(tempData.getUUID(), control);
//				arenaFightData.getAllObjDataMap().put(tempData.getUUID(), tempData);
//			}
//		}
	}
	
	
	/**
	 * 邵海挺 
	 * 
	 * 竞技场
	 */
	public static int lv1=1;
	public static int lv2=2;
	public static int lv3=3;
	public static int lv4=4;
	public static int lv5=5;
	public static int lv6=6;
	public static int lv1num=1;
	public static int lv2num=5;
	public static int lv3num=15;
	public static int lv4num=65;
	public static int lv5num=165;
	public static int lv6num=365;
	
	/**
	 * 根据排名获取机器人品阶
	 * @param rank
	 * @return
	 */
	public int getLvByRank(int rank){
		int i=-1;
		if(rank>0&&rank<=lv1num){
			i=lv1;
		}else if(rank>lv1num&&rank<=lv2num){
			i=lv2;
		}else if(rank>lv2num&&rank<=lv3num){
			i=lv3;
		}else if(rank>lv3num&&rank<=lv4num){
			i=lv4;
		}else if(rank>lv4num&&rank<=lv5num){
			i=lv5;
		}else if(rank>lv5num&&rank<=lv6num){
			i=lv6;
		}else{
			i=-1;
		}
		return i;
	}
	/**
	 * 根据roleId获取玩家竞技场数据信息
	 * @param roleId
	 * @return
	 */
	@SuppressWarnings("null")
	public  LadderArena getLvAndRank(int roleId){
		LadderArena ladderarena=new LadderArena();
		if(null==ArenaCache.getLadderMap2().get(roleId)){
			
			ladderarena.setRoleId(roleId);
			ladderarena.setLv(-1);
			ladderarena.setRank(-1);
		}else{
			ladderarena=ArenaCache.getLadderMap2().get(roleId);
			int rank=ladderarena.getRank();
			
			if(rank>0&&rank<=lv1num){
				ladderarena.setRoleId(roleId);
				ladderarena.setLv(lv1);
				ladderarena.setNowRank(rank);
			}else if(rank>lv1num&&rank<=lv2num){
				ladderarena.setRoleId(roleId);
				ladderarena.setLv(lv2);
				ladderarena.setNowRank(rank-lv1num);
			}else if(rank>lv2num&&rank<=lv3num){
				ladderarena.setRoleId(roleId);
				ladderarena.setLv(lv3);
				ladderarena.setNowRank(rank-lv2num);
			}else if(rank>lv3num&&rank<=lv4num){
				ladderarena.setRoleId(roleId);
				ladderarena.setLv(lv4);
				ladderarena.setNowRank(rank-lv3num);
			}else if(rank>lv4num&&rank<=lv5num){
				ladderarena.setRoleId(roleId);
				ladderarena.setLv(lv5);
				ladderarena.setNowRank(rank-lv4num);
			}else if(rank>lv5num&&rank<=lv6num){
				ladderarena.setRoleId(roleId);
				ladderarena.setLv(lv6);
				ladderarena.setNowRank(rank-lv5num);
			}else{
				ladderarena.setRoleId(roleId);
				ladderarena.setLv(-1);
				ladderarena.setNowRank(-1);
			}
		}
		return ladderarena;
	}
	/**
	 * 根据玩家等阶获取当前等阶玩家排名
	 * @param lv
	 * @return
	 */
	public  List<LadderArena> getNowLadder(int lv){
		Map<Integer, LadderArena> ladderArenaMap=ArenaCache.getLadderMap();
		List<LadderArena> list=new ArrayList<LadderArena>();
		if(lv==lv1){
			for(int i=1;i<=lv1num;i++){
				LadderArena ladderArena=ladderArenaMap.get(i);
				if(ladderArena.getRoleId()==-1){
					ladderArena.setRoleLv(-1);
					ladderArena.setLv(lv);
					ladderArena.setNowRank(i);
					ladderArena.setRoleName("机器人");
				}else{
					Role role=roleService.getRoleById(ladderArena.getRoleId());
					ladderArena.setRoleLv(role.getLv());
					ladderArena.setLv(lv);
					ladderArena.setNowRank(i);
					ladderArena.setRoleName(role.getName());
				}
				list.add(ladderArena);
			}
		}else if(lv==lv2){
			for(int i=lv1num+1;i<=lv2num;i++){
				LadderArena ladderArena=ladderArenaMap.get(i);
				if(ladderArena.getRoleId()==-1){
					ladderArena.setRoleLv(-1);
					ladderArena.setLv(lv);
					ladderArena.setNowRank(i-lv1num);
					ladderArena.setRoleName("机器人");
				}else{
					Role role=roleService.getRoleById(ladderArena.getRoleId());
					ladderArena.setRoleLv(role.getLv());
					ladderArena.setLv(lv);
					ladderArena.setNowRank(i-lv1num);
					ladderArena.setRoleName(role.getName());
				}
				list.add(ladderArena);
			}
		}else if(lv==lv3){
			for(int i=lv2num+1;i<=lv3num;i++){
				LadderArena ladderArena=ladderArenaMap.get(i);
				if(ladderArena.getRoleId()==-1){
					ladderArena.setRoleLv(-1);
					ladderArena.setLv(lv);
					ladderArena.setNowRank(i-lv2num);
					ladderArena.setRoleName("机器人");
				}else{
					Role role=roleService.getRoleById(ladderArena.getRoleId());
					ladderArena.setRoleLv(role.getLv());
					ladderArena.setLv(lv);
					ladderArena.setNowRank(i-lv2num);
					ladderArena.setRoleName(role.getName());
				}
				list.add(ladderArena);
			}
		}else if(lv==lv4){
			for(int i=lv3num+1;i<=lv4num;i++){
				LadderArena ladderArena=ladderArenaMap.get(i);
				if(ladderArena.getRoleId()==-1){
					ladderArena.setRoleLv(-1);
					ladderArena.setLv(lv);
					ladderArena.setNowRank(i-lv3num);
					ladderArena.setRoleName("机器人");
				}else{
					Role role=roleService.getRoleById(ladderArena.getRoleId());
					ladderArena.setRoleLv(role.getLv());
					ladderArena.setLv(lv);
					ladderArena.setNowRank(i-lv3num);
					ladderArena.setRoleName(role.getName());
				}
				list.add(ladderArena);
			}
		}else	if(lv==lv5){
			for(int i=lv4num+1;i<=lv5num;i++){
				LadderArena ladderArena=ladderArenaMap.get(i);
				if(ladderArena.getRoleId()==-1){
					ladderArena.setRoleLv(-1);
					ladderArena.setLv(lv);
					ladderArena.setNowRank(i-lv4num);
					ladderArena.setRoleName("机器人");
				}else{
					Role role=roleService.getRoleById(ladderArena.getRoleId());
					ladderArena.setRoleLv(role.getLv());
					ladderArena.setLv(lv);
					ladderArena.setNowRank(i-lv4num);
					ladderArena.setRoleName(role.getName());
				}
				list.add(ladderArena);
			}
		}else if(lv==lv6){
			for(int i=lv5num+1;i<=lv6num;i++){
				LadderArena ladderArena=ladderArenaMap.get(i);
				if(ladderArena.getRoleId()==-1){
					ladderArena.setRoleLv(-1);
					ladderArena.setLv(lv);
					ladderArena.setNowRank(i-lv5num);
					ladderArena.setRoleName("机器人");
				}else{
					Role role=roleService.getRoleById(ladderArena.getRoleId());
					ladderArena.setRoleLv(role.getLv());
					ladderArena.setLv(lv);
					ladderArena.setNowRank(i-lv5num);
					ladderArena.setRoleName(role.getName());
				}
				list.add(ladderArena);
			}
		}else{
			for(int i=lv6num+1;i<=ArenaCache.getLadderMap().size();i++){
				LadderArena ladderArena=ladderArenaMap.get(i);
				if(ladderArena.getRoleId()==-1){
					ladderArena.setRoleLv(-1);
					ladderArena.setLv(-1);
					ladderArena.setNowRank(i-lv6num);
					ladderArena.setRoleName("机器人");
				}else{
					Role role=roleService.getRoleById(ladderArena.getRoleId());
					ladderArena.setRoleLv(role.getLv());
					ladderArena.setLv(-1);
					ladderArena.setNowRank(i-lv6num);
					ladderArena.setRoleName(role.getName());
				}
				list.add(ladderArena);
			}
//			LadderArena ladderArena=new LadderArena();
//			ladderArena.setRoleLv(role.getLv());
//			ladderArena.setLv(-1);
//			ladderArena.setNowRank(-1);
//			ladderArena.setRoleId(-1);
//			ladderArena.setRoleName("未上榜");
//			list.add(ladderArena);
		}
		return list;
	}
	/**
	 * 获取全服排名详细信息
	 * @param lv
	 * @return
	 */
	public  Map<Integer, LadderArena> getAllLadderArena(){
		Map<Integer, LadderArena> ladderArenaMap=new ConcurrentHashMap<Integer, LadderArena>();
		Map<Integer, LadderArena> ladderArena=ArenaCache.getLadderMap();
		for (int i= 1; i <=ladderArena.size(); i++) {
			LadderArena ladderArena2=ladderArena.get(i);
			
			LadderArena ladderArena3=getLvAndRank(ladderArena2.getRoleId());
			if(ladderArena2.getRoleId()==-1){
				ladderArena2.setRoleLv(-1);
				ladderArena2.setLv(getLvByRank(ladderArena2.getRank()));
				ladderArena2.setRank(ladderArena2.getRank());
				ladderArena2.setRoleName("机器人");
				ladderArenaMap.put(i, ladderArena2);
			}else{
				Role role=roleService.getRoleById(ladderArena2.getRoleId());
				ladderArena2.setLv(ladderArena3.getLv());
				ladderArena2.setRank(ladderArena2.getRank());
				ladderArena2.setRoleLv(role.getLv());
				ladderArena2.setRoleName(role.getName());
				ladderArenaMap.put(i, ladderArena2);
			}
		}
		return ladderArenaMap;
	}
}
