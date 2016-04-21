package byCodeGame.game.moudle.raid.service;

import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.mina.core.session.IoSession;

import cn.bycode.game.battle.core.Battle;
import cn.bycode.game.battle.data.ResultData;
import cn.bycode.game.battle.data.TroopData;
import cn.bycode.game.battle.test.Test;
import byCodeGame.game.cache.file.RaidArmsConfigCache;
import byCodeGame.game.cache.local.BattleLobbyCache;
import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.cache.local.SessionCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.entity.bo.BattleLobby;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.file.RaidArmsConfig;
import byCodeGame.game.entity.po.RoomData;
import byCodeGame.game.entity.po.RoomRole;
import byCodeGame.game.moudle.raid.RaidConstant;
import byCodeGame.game.moudle.role.service.RoleService;
import byCodeGame.game.remote.Message;
import byCodeGame.game.util.PVPUitls;
import byCodeGame.game.util.comparator.ComparatorRoomRole;

public class RaidServiceImpl implements RaidService{
	private RoleService roleService;
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	/** 初始化战役大厅			*/
	public void initBattleLobby() {
		BattleLobbyCache.setBattleLobby(BattleLobby.getInstance());
	}
	
	/**
	 * 获取大厅信息 2201
	 * @author xjd
	 */
	public Message getLobbyInfo() {
		Message message = new Message();
		message.setType(RaidConstant.GET_LOBBY_INFO);
		message.put((byte)BattleLobbyCache.getBattleLobby().getRoomMap().size());
		
		for(Entry<String, RoomData> entry : BattleLobbyCache.getBattleLobby().getRoomMap().entrySet())
		{
			message.putString(entry.getKey());
			message.putString(entry.getValue().getRoleList().get(RaidConstant.HOUSE_PLAYER_OW).getRole().getName());
			message.putInt(entry.getValue().getNeedLv());
			message.put((byte)entry.getValue().getRoleList().size());
			message.put(entry.getValue().getStatus());
			message.putInt(entry.getValue().getRoomOw());
			message.putInt(entry.getValue().getRaidArmsConfig().getRaidId());
			message.putString(entry.getValue().getCreatTime());
		}
		
		return message;
	}
	
	/**
	 * 创建新房间 2202
	 * @author xjd
	 */
	public Message creatNewRoom(Role role, int raidId) {
		Message message = new Message();
		message.setType(RaidConstant.CREATE_NEW_ROOM);
		BattleLobby battleLobby = BattleLobbyCache.getBattleLobby();
		//判断是否合法
		if(role.getRoomUUID() !=null && !role.getRoomUUID().equalsIgnoreCase(""))
		{
			return null;
		}
		//取出配置表信息
		RaidArmsConfig raidArmsConfig = RaidArmsConfigCache.getRiadArmsConfig(raidId);
		if(raidArmsConfig == null)
		{
			return null;
		}
		//判断玩家等级
		if((int)role.getLv() < raidArmsConfig.getNeedLv())
		{
			message.putShort(ErrorCode.NO_LV);
			return message;
		}
		//判断玩家团队战役次数是否足够
		if(role.getRaidTimes() <= RaidConstant.MIN_RAID_TIMES)
		{
			message.putShort(ErrorCode.NO_RAID_TIMES);
			return message;
		}
		//判断房间总数是否达到上限
		if(battleLobby.getRoomMap().size() >= battleLobby.MAX_ROOM_NUM)
		{
			message.putShort(ErrorCode.MAX_LOBBY_NUM);
			return message;
		}
		//创建房间成功
		String uuid = UUID.randomUUID().toString();//创建一个唯一的uuid索引
		RoomData roomData = new RoomData();//创建房间
		RoomRole roomRole = new RoomRole();//创建房间玩家
		roomRole.setRoleStatus(RaidConstant.HOUSE_OW_STATUS);//房主状态设置为 已准备
		roomRole.setRole(role);//存入玩家引用
		roomData.addRoomRole(roomRole);//增加房间玩家（map）
		roomData.setNeedLv(raidArmsConfig.getNeedLv());//设定房间需求等级
		roomData.setUuid(uuid);//存入房间标识
		roomData.setRaidArmsConfig(raidArmsConfig);//存入副本信息
		roomData.setRoomOw(role.getId()); //存入房主ID
		long temp = System.currentTimeMillis()/1000;
		roomData.setCreatTime(Long.toString(temp));
		//此处可能发生等待
		battleLobby.creatNewRoom(uuid, roomData);//将房间存入大厅
		role.setRoomUUID(uuid);
		
		//返回客户端信息
		message.putShort(ErrorCode.SUCCESS);
		message.putString(uuid);
		
		return message;
	}

	/***
	 * 加入房间 2203；
	 * @author xjd
	 */
	public Message joinRoom(Role role, String uuid) {
		Message message = new Message();
		message.setType(RaidConstant.JOIN_ROOM);
		//判断是否合法
		if(role.getRoomUUID() != null && !role.getRoomUUID().equals(""))
		{
			return null;
		}
		RoomData roomData = BattleLobbyCache.getBattleLobby().getRoomMap().get(uuid);
		if(roomData == null)
		{
			message.putShort(ErrorCode.ROOM_NOT_ACC);
			return message;
		}
		//判断等级是否符合
		if(role.getLv() < (short)roomData.getNeedLv())
		{
			message.putShort(ErrorCode.NO_LV);
			return message;
		}
		
		//判断房间人数是否达到上限
		RoomRole roomRole = new RoomRole();
		roomRole.setRole(role);
		short temp = roomData.addRoomRole(roomRole);
		if(temp != ErrorCode.SUCCESS)
		{
			message.putShort(temp);
			return message;
		}
		role.setRoomUUID(uuid);
		this.updataRoomInfo(roomData);
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	/**
	 * 获取房间信息 2204
	 * @author xjd
	 */
	public Message getRoomInfo(Role role) {
		Message message = new Message();
		message.setType(RaidConstant.GET_ROOM_INFO);
		RoomData roomData = BattleLobbyCache.getBattleLobby().getRoomMap().get(role.getRoomUUID());
		if(roomData == null)
		{
			return null;
		}
		message.put((byte)roomData.getRoleList().size());
		for(RoomRole x : roomData.getRoleList())
		{
			message.putString(x.getRole().getName());
			message.putInt(x.getRole().getId());
			message.putShort(x.getRole().getLv());
			message.put(x.getRoleStatus());
		}
		return message;
	}
	
	/***
	 * 更新房间信息 2205 (服务器主动推送)
	 * @author xjd
	 */
	public void updataRoomInfo(RoomData roomData) {
		BattleLobby battleLobby = BattleLobbyCache.getBattleLobby();
		Message message = new Message();
		message.setType(RaidConstant.UPDATA_ROOM_INFO);
		IoSession session = null ;
		//房间存在和不存在的情况
		if(battleLobby.getRoomMap().containsValue(roomData))
		{
			message.putShort(ErrorCode.SUCCESS);
			message.put(roomData.getStatus());
			message.put((byte)roomData.getRoleList().size());
			//存入房间所有信息
			for( RoomRole x: roomData.getRoleList())
			{
				
				message.putString(x.getRole().getName());
				message.putInt(x.getRole().getId());
				message.putShort(x.getRole().getLv());
				message.put(x.getRoleStatus());
				
			}
			//通知所有本房间的玩家信息改动（若果是房主踢人则被踢玩家从另一个接口接受信息）
			for(RoomRole x: roomData.getRoleList())
			{
				session = SessionCache.getSessionById(x.getRole().getId());
				if(session != null)
				{
					session.write(message);
					continue;
				}
			}
			
		}else {//房间不存在的情况
			message.putShort(ErrorCode.ROOM_NOT_ACC);
			for(RoomRole x: roomData.getRoleList())
			{
				session = SessionCache.getSessionById(x.getRole().getId());
				if(session != null)
				{
					session.write(message);
					continue;
				}
			}
		}
	}

	/**
	 * 退出房间 2206
	 * @author xjd
	 */
	public Message quitRoom(Role role) {
		Message message = new Message();
		message.setType(RaidConstant.QUIT_ROOM);
		BattleLobby battleLobby = BattleLobbyCache.getBattleLobby();
		RoomData roomData = battleLobby.getRoomMap().get(role.getRoomUUID());
		if(roomData == null)
		{
			return null;
		}
		//判断玩家的状态
		int index = this.findRoleIndex(role, roomData);
		RoomRole roomRole = roomData.getRoleList().get(index);
		if(roomRole == null)
		{
			return null;
		}
		
		if (roomData.getRoomOw() != role.getId() 
				&& roomRole.getRoleStatus() == RaidConstant.HOUSE_PLAYER_STATUS_READY) {
			message.putShort(ErrorCode.ERR_QUIT_STATUS);
			return message;
		}
		
		//退出成功
		this.clearRoleFromRoomData(role);
		
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	/**
	 * 踢出玩家 2207
	 * @author xjd
	 */
	public Message kickRole(Role role ,int index) {
		Message message = new Message();
		message.setType(RaidConstant.KICK_ROLE);
		//判断玩家是否是房主
		RoomData roomData = BattleLobbyCache.getBattleLobby().getRoomMap().get(role.getRoomUUID());
		if(role.getId() != roomData.getRoomOw())
		{
			message.putShort(ErrorCode.NO_ACCEPT);
			return message;
		}
		//判断目标用户是否存在
		Role targetRole = RoleCache.getRoleById(index);
		if(targetRole == null || !roomData.getRoleList().contains(targetRole))
		{
			message.putShort(ErrorCode.NULL_TARGET_ROLE);
			return message;
		}
		//踢出玩家 && 更新房间信息
		this.clearRoleFromRoomData(targetRole);
		//通知被踢出玩家
		this.beKickedFromRoom(targetRole);
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	/***
	 * 通知玩家被人踢出 2208 (服务器主动推送)
	 * @param role
	 * @author xjd
	 */
	public void beKickedFromRoom(Role role)
	{
		Message message = new Message();
		message.setType(RaidConstant.BE_KICKED_FROM_ROOM);
		
		message.putShort(ErrorCode.BE_KICKED_FROM_ROOM);
		IoSession ioSession = SessionCache.getSessionById(role.getId());
		if(ioSession == null)
		{
			return ;
		}else {
			ioSession.write(message);
		}
		
	}
	
	/***
	 * 玩家对应位置 2209
	 * @author xjd
	 */
	public Message saveRoomRoleFormation(Role role , int useingHeroID,
			int canChangeId, int canChangeId2) {
		RoomData roomData = BattleLobbyCache.getBattleLobby().getRoomMap().get(role.getRoomUUID());
		//判断玩家是否是房主
		Message message = new Message();
		message.setType(RaidConstant.SAVE_ROOMROLE_FORMATION);
		if(role.getId() != roomData.getRoomOw())
		{
			message.putShort(ErrorCode.NO_ACCEPT);
			return message;
		}
		if(roomData.getRoleList().size() < RaidConstant.HOUSE_PLAYER_NUMBER)
		{
			message.putShort(ErrorCode.ERR_SET_STATUS);
			return message;
		}
		RoomRole temp = roomData.getRoleList().get(RaidConstant.HOUSE_PLAYER_2);
		roomData.getRoleList().remove(temp);
		roomData.getRoleList().add(temp);
		this.updataRoomInfo(roomData);
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	/**
	 * 准备完毕/取消准备 2210
	 * @author xjd
	 */
	public Message setRoleStatus(Role role, byte status) {
		Message message = new Message();
		message.setType(RaidConstant.SET_ROLE_STATUS);
		RoomData roomData = BattleLobbyCache.getBattleLobby().getRoomMap().get(role.getRoomUUID());
		if(roomData == null)
		{
			return null;
		}
		RoomRole roomRole = roomData.getRoleList().get(this.findRoleIndex(role, roomData));
		if(roomRole == null)
		{
			return null;
		}
		//根据 status 选择操作类型
		if(status == RaidConstant.HOUSE_PLAYER_STATUS_NOT)
		{
			// 取消准备
			roomRole.setRoleStatus(RaidConstant.HOUSE_PLAYER_STATUS_NOT);
			this.updataRoomInfo(roomData);
			message.putShort(ErrorCode.SUCCESS);
			return message;
		// 准备完毕
		}else if (status == RaidConstant.HOUSE_PLAYER_STATUS_READY) {
			//准备完毕
			
			roomRole.setRoleStatus(RaidConstant.HOUSE_PLAYER_STATUS_READY);
			this.updataRoomInfo(roomData);
			
			message.putShort(ErrorCode.SUCCESS);
			return message;
		}
		return null;
	}
	
	/**
	 * 开始游戏 2211
	 * @author xjd
	 */
	public Message startBattle(Role role) {
		Message message = new Message();
		message.setType(RaidConstant.START_BATTLE);
		RoomData roomData = BattleLobbyCache.getBattleLobby().getRoomMap().get(role.getRoomUUID());
		if(roomData == null)
		{
			return null;
		}
		//判断玩家是否是房主
		if(role.getId() != roomData.getRoomOw())
		{
			return null;
		}
		//判断玩家状态是否是全部准备完毕状态
		boolean flag = true;
		for(RoomRole x : roomData.getRoleList())
		{
			if(x.getRoleStatus() == RaidConstant.HOUSE_PLAYER_STATUS_NOT)
			{
				flag = false;
			}
		}
		if(flag == false)
		{
			message.putShort(ErrorCode.CAN_NOT_START_BATTLE);
			return message;
		}
		//开始成功
		roomData.setStatus(RaidConstant.HOUSE_STATUS_START);
		//此处通知客户端
		this.startLoading(roomData);
		this.raidBattle(roomData);
		this.sendRes(roomData);
		//处理玩家奖励 && 扣除战损
		this.handleLost(roomData);
		//测试清空数据使用
		roomData.getResList().clear();
		roomData.getEnemyList().clear();
		roomData.getMyDataList().clear();
		
		
		//清除玩家信息&&房间信息
		this.battleEnd(roomData);
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	/**
	 * 初始化战斗数据
	 * @param roomData
	 * @author xjd
	 */
	private void startLoading(RoomData roomData)
	{
		for(RoomRole x : roomData.getRoleList())
		{
			x.setTroopData(PVPUitls.getTroopDataByRole(x.getRole()));
			roomData.getMyDataList().add(x.getTroopData());
		}
		//初始化战斗数据 - AI Test数据
		for(int i = 0 ; i < 9 ; i++)
		{
			roomData.getEnemyList().add(Test.getDefData());
		}
	}
	
	/**
	 * 战斗过程
	 * @param roomData
	 */
	public void raidBattle(RoomData roomData)
	{
		List<TroopData> myList = roomData.getMyDataList();
		List<RoomRole> roleList = roomData.getRoleList();
		List<TroopData> enemyList = roomData.getEnemyList();
		while(true)
		{
			//结束条件 AI全灭
			if(enemyList.size() <= 0)
			{
				//玩家胜利
				roomData.setBattleRes(RaidConstant.WIN_SIDE_PLAYER);
				break;
			}
			//结束条件 玩家全灭
			if(myList.size() <= 0)
			{
				roomData.setBattleRes(RaidConstant.WIN_SIDE_AI);
				break;
			}
			
			//开始匹配
			ResultData tempRes = new Battle().fightPVP(myList.get(0), enemyList.get(0));
			roomData.getResList().add(tempRes);
			//玩家战损增加
			RoomRole tempRole = roleList.get(this.findTarget(myList.get(0), roomData.getRoleList()));
			tempRole.setLostAll(tempRole.getLostAll() + tempRes.attLost);
			if(tempRes.winCamp == RaidConstant.WIN_SIDE_AI)
			{
				//从TroopData List 中
				myList.remove(0);
				tempRole.setIsDead(1);
				//将AI重新添加至队列尾部
				enemyList.add(enemyList.get(0));
				enemyList.remove(0);
			}else {
				//玩家获胜的情况
				enemyList.remove(0);
				tempRole.setBattleTime(tempRole.getBattleTime() + tempRes.fRound);
				tempRole.setWinTime(tempRole.getWinTime() + 1);
			}
			//胜利次数最大值为三次 达到三次从战斗数据中移除
			if(tempRole.getWinTime() >= 3)
			{
				tempRole.setIsDead(1);
				myList.remove(0);
			}
			//对roomRole进行排序
			Collections.sort(roleList, ComparatorRoomRole.getInstance());
			//对战斗数据List重新 刷新
			this.reFreshTroopList(roomData);
		}
		
	}
	
	
	/**
	 * 设置玩家加载进度 2213
	 * @author xjd
	 */
	public Message setLoadingProgress(Role role , double progress) {
		Message message = new Message();
		message.setType(RaidConstant.SET_LOADING_PROGRESS);
//		RoomData roomData = BattleLobbyCache.getBattleLobby().getRoomMap().get(role.getRoomUUID());
//		if(roomData == null)
//		{
//			return null;
//		}
//		RoomRole roomRole = roomData.getRoleList().get(this.findRoleIndex(role, roomData));
//		if(roomRole == null)
//		{
//			return null;
//		}
//		//加载进度
//		roomRole.setLoadProgress(progress);
//		//判断是否加载完成
//		if(progress >= RaidConstant.LOADING_READLY)
//		{
//			System.out.println(roomRole.getRole().getName()+":"+roomRole.getLoadProgress());
//			this.checkLoading(roomData);
//		}
//		
//		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	/**
	 * 更新其他玩家加载进度 2214
	 * @author xjd
	 */
	public Message updateLoadingProgress(Role role) {
		Message message = new Message();
		message.setType(RaidConstant.UPDATE_LOADING_PROGRESS);
//		RoomData roomData = this.getRoomData(role);
//		if(roomData == null)
//		{
//			return null;
//		}
//		//房间玩家人数
//		message.put((byte)roomData.getRoleList().size());
//		for(RoomRole x : roomData.getRoleList())
//		{
//			message.putInt(x.getRole().getId());
//			message.putString(x.getRole().getName());//玩家名字
//			message.putDouble(x.getLoadProgress());//进度
//		}
//		
		return message;
	}
	
	/**
	 * 告知客户端所有玩家资源加载完毕 2215(服务器主动推送)
	 * @param roomData
	 * @author xjd
	 */
	private void allPlayerReady(RoomData roomData)
	{
		Message message = new Message();
		message.setType(RaidConstant.ALL_PLAYER_READY);
		message.putShort(ErrorCode.SUCCESS);
		IoSession session = null;
		for(RoomRole roomRole : roomData.getRoleList())
		{
			session = SessionCache.getSessionById(roomRole.getRole().getId());
			if(session != null)
			{
				session.write(message);
			}
		}
	}
	
	/**
	 * 战斗结束清除房间信息
	 * @author xjd
	 */
	private void battleEnd(RoomData roomData) {
		//清除玩家信息
		for(RoomRole x : roomData.getRoleList())
		{
			x.getRole().setRoomUUID("");
		}
		//清除大厅中的房间信息
		BattleLobby battleLobby = BattleLobbyCache.getBattleLobby();
		battleLobby.getBattleRoomMap().remove(roomData.getUuid());
		battleLobby.getRoomMap().remove(roomData.getUuid());
	}
	
	/**
	 * 清除玩家在房间中的信息(退出||掉线||踢人)
	 * @author xjd
	 */
	public void clearRoleFromRoomData(Role role) {
		BattleLobby battleLobby = BattleLobbyCache.getBattleLobby();
		if(battleLobby == null)
		{
			return;
		}
		RoomData roomData = battleLobby.getRoomMap().get(role.getRoomUUID());
		if(roomData == null) return ;
		//从房间中清除该玩家信息
		role.setRoomUUID("");
		int temp = this.findRoleIndex(role, roomData);
		if(temp == -1)
		{
			return;
		}
		roomData.removeRoomRole(temp);
		if(roomData.getRoleList().size() <= 0)
		{
			battleLobby.deleteRoom(roomData.getUuid());
		}else {
			roomData.setRoomOw(roomData.getRoleList().get(RaidConstant.HOUSE_PLAYER_OW)
					.getRole().getId());
			roomData.getRoleList().get(RaidConstant.HOUSE_PLAYER_OW).setRoleStatus(RaidConstant.HOUSE_PLAYER_STATUS_READY);
		}
		//通知更新房间信息
		this.updataRoomInfo(roomData);
		
	}
	
	/**
	 * 找到玩家在战役大厅的信息
	 * @param role
	 * @param roomData
	 * @return
	 * @author xjd
	 */
	private int findRoleIndex(Role role , RoomData roomData)
	{
		int index = 0;
		for(RoomRole x : roomData.getRoleList())
		{
			if(x.getRole().equals(role))
			{
				return index;
			}
			index++;
		}
		return -1;
	}

	/**
	 * 获取房间
	 * @param role
	 * @return
	 * @author xjd
	 */
	@SuppressWarnings("unused")
	private RoomData getRoomData(Role role)
	{
		RoomData roomData = BattleLobbyCache.getBattleLobby().getRoomMap().get(role.getRoomUUID());
		return roomData;
	}

	/**
	 * 检查房间加载状态
	 * <font color = "red"><b>停用<b/><font/>
	 * @param roomData
	 * @author xjd
	 */
	@SuppressWarnings("unused")
	private void checkLoading(RoomData roomData)
	{
		boolean flag = true;
//		//检查其他玩家是否加载完毕
//		for(RoomRole x : roomData.getRoleList())
//		{
//			if(x.getLoadProgress() < RaidConstant.LOADING_READLY)
//			{
//				flag = false;
//				break;
//			}
//		}
		if(flag == false)
		{
			return;
		}
		//通知所有客户端所有玩家加载完毕
		this.allPlayerReady(roomData);
	}
	
	/***
	 * 根据TroopData 找出对应的RoomRole
	 * @return
	 */
	private int findTarget(TroopData data , List<RoomRole> list)
	{
		int x = -1 ;
		for(int i = 0 ; i < list.size(); i++)
		{
			if(data.getPlayerId() == list.get(i).getTroopData().getPlayerId())
			{
				x = i;
				break;
			}
		}
		return x;
	}

	/**
	 * 重置战斗List-玩家
	 * @param data
	 */
	private void reFreshTroopList(RoomData data)
	{
		data.getMyDataList().clear();
		//重置排序房间战斗数据 - 玩家
		for(RoomRole x : data.getRoleList())
		{
			if(x.getIsDead() != 0)
			{
				continue;
			}
			data.getMyDataList().add(x.getTroopData());
		}
	}
	
	/**
	 * 发送团队战役结果
	 * @param roomData
	 */
	private void sendRes(RoomData roomData)
	{
		Message message = new Message();
		message.setType((short)22219);
		message.put((byte)roomData.getBattleRes());
		message.putInt(roomData.getResList().size());
		for(ResultData res : roomData.getResList())
		{
			message.putString(res.report);
		}
		IoSession session = null;
		for(RoomRole x:roomData.getRoleList())
		{
			session = SessionCache.getSessionById(x.getRole().getId());
			if(session != null)
			{
				session.write(message);
			}
		}
		
	}

	private void handleLost(RoomData roomData)
	{
		//扣除战损 
		for(RoomRole x : roomData.getRoleList())
		{
			roleService.addRolePopulation(x.getRole(), -x.getLostAll());
			x.getRole().setRaidTimes((byte)(x.getRole().getRaidTimes() - 1));
		}
	}
}
