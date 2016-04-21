package byCodeGame.game.moudle.legion.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;














































import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.Util;

import byCodeGame.game.cache.file.CityInfoConfigCache;
import byCodeGame.game.cache.file.EquipConfigCache;
import byCodeGame.game.cache.file.ExchangeForLegionConfigCache;
import byCodeGame.game.cache.file.GeneralNumConstantCache;
import byCodeGame.game.cache.file.HeroConfigCache;
import byCodeGame.game.cache.file.ItemCache;
import byCodeGame.game.cache.file.LegionScienceConfigCache;
import byCodeGame.game.cache.local.CityCache;
import byCodeGame.game.cache.local.LegionCache;
import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.cache.local.SessionCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.db.dao.HeroDao;
import byCodeGame.game.db.dao.LegionDao;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Legion;
import byCodeGame.game.entity.bo.Mail;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.file.EquipConfig;
import byCodeGame.game.entity.file.ExchangeForLegionConfig;
import byCodeGame.game.entity.file.HeroConfig;
import byCodeGame.game.entity.file.Item;
import byCodeGame.game.entity.file.LegionScienceConfig;
import byCodeGame.game.entity.po.ActivityMessage;
import byCodeGame.game.entity.po.LegionScience;
import byCodeGame.game.moudle.activity.service.ActivityService;
import byCodeGame.game.moudle.chat.ChatConstant;
import byCodeGame.game.moudle.chat.service.ChatService;
import byCodeGame.game.moudle.hero.service.HeroService;
import byCodeGame.game.moudle.legion.LegionConstant;
import byCodeGame.game.moudle.mail.service.MailService;
import byCodeGame.game.moudle.role.service.RoleService;
import byCodeGame.game.moudle.task.service.TaskService;
import byCodeGame.game.remote.Message;
import byCodeGame.game.util.HeroUtils;
import byCodeGame.game.util.Utils;

public class LegionServiceImpl implements LegionService {
	private static final Logger legionLog = LoggerFactory.getLogger("legion");
	
	private LegionDao legionDao; 
	public void setLegionDao(LegionDao legionDao) {
		this.legionDao = legionDao;
	}
	
	private RoleService roleService;
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	private MailService mailService;
	public void setMailService(MailService mailService)
	{
		this.mailService = mailService;
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
	
	private ChatService chatService;
	public void setChatService(ChatService chatService) {
		this.chatService = chatService;
	}
	
	private ActivityService activityService;
	public void setActivityService(ActivityService activityService) {
		this.activityService = activityService;
	}
	
	public Message creatLegion(Role role , String name ,String shortName){
		Message message = new Message();
		message.setType(LegionConstant.CREAT_LEGION);
		
		if(name.length() > LegionConstant.MAX_LENGTH_LEGION_NAME){
			message.putShort(ErrorCode.LEGION_NAME_LONG);
			return message;
		}
		if(LegionCache.checkLegionName(name)){	//工会姓名重复
			message.putShort(ErrorCode.LEGION_NAME_REPEAT);
			return message;
		}
		if(role.getGold() < LegionConstant.CREAT_LEGION_NEED_GOLD){		//金币不足
			message.putShort(ErrorCode.NO_GOLD);
			return message;
		}
		if(role.getLegionId() > 0 ){	//玩家已经加入公会
			message.putShort(ErrorCode.IN_LEGION);
			return message;
		}
		if(shortName.length() > LegionConstant.MAX_LENGTH_SHORT_NAME)
		{
			message.putShort(ErrorCode.ERR_LENGTH_SHORT_NAME);
			return message;
		}
		if(LegionCache.getLegionShortName().containsValue(shortName))
		{
			message.putShort(ErrorCode.ERR_SHORT_NAME);
			return message;
		}
		//等级过滤
		if(role.getLv() < LegionConstant.MIN_LV_CREAT)
		{
			message.putShort(ErrorCode.NO_LV);
			return message;
		}
		this.clearListLegion(role);
		//创建成功
		Legion legion = new Legion();
		legion.setName(name);
		legion.setShortName(shortName);
		legion.setFaceId(LegionConstant.DEFAULT_FACE_ID);
		legion.setLv(LegionConstant.DEFAULT_LV);
		legion.setMaxPeopleNum(LegionConstant.DEFAULT_PEOPLE_NUM);
		legion.setCaptainId(role.getId());
		legion.setNation(role.getNation());
		//军团科技初始值
		for(int i =1 ;i <= 5;i++)
		{
			LegionScience legionScience = new LegionScience();
			legionScience.setId(i);
			legionScience.setLv(LegionConstant.DEFAULT_LV);
			legionScience.setExp(LegionConstant.UP_SCIENCE);
			legion.getScienceMap().put(i, legionScience);
		}
		legion.getMemberSet().add(role.getId());
		legion.setAppointScience(LegionConstant.DE_APPOINT_SCIENCE);
		int legionId = legionDao.insertLegion(legion);
		legion.setLegionId(legionId);
		
		role.setLegionId(legionId);
		roleService.addRoleGold(role, -LegionConstant.CREAT_LEGION_NEED_GOLD);
		
		
		//将军团以及军团名加入缓存
		LegionCache.addLegionInCache(legion);
		LegionCache.getLegionNameList().add(name);
		LegionCache.getLegionShortName().put(legion.getLegionId(), shortName);
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	public void getAllLegionName(){
		List<String> nameList = legionDao.getAllLegionName();
		LegionCache.setLegionNameList(nameList);
	}
	
	public Legion getLegionById(int legionId){
		Legion legion = LegionCache.getLegionById(legionId);
		if(legion == null){
			legion = legionDao.getLegionById(legionId);
			LegionCache.addLegionInCache(legion);
		}
		return legion;       
	}
	
	public Message getLegionData(Role role){
		Message message = new Message();
		message.setType(LegionConstant.GET_LEGION_DATA);
		
		if(role.getLegionId() <= LegionConstant.NOT_IN_LEGION){	//主角未加入军团
			message.putInt(LegionConstant.NOT_IN_LEGION);
			return message;
		}
		Legion legion = this.getLegionById(role.getLegionId());
		if(legion == null){		//未找到军团信息
			return null;
		}
		
		message.putInt(legion.getLegionId());
		message.putInt(legion.getFaceId());
		message.putString(legion.getName());
		message.put(legion.getLv());
		if(legion.getNotice() == null){
			message.putString("");
		}else{
			message.putString(legion.getNotice());
		}
		Role captainRole = roleService.getRoleById(legion.getCaptainId());
		message.putString(captainRole.getName());
		//判定职位
		if(legion.getCaptainId() == role.getId()){
			message.put(LegionConstant.MEMBER_TYPE1);
		}else if(legion.getDeputyCaptainSet().contains(role.getId())){
			message.put(LegionConstant.MEMBER_TYPE2);
		}else{
			message.put(LegionConstant.MEMBER_TYPE3);
		}
		//获取军团当前设定的科技升级类型
		message.put(legion.getAppointScience());
		//获取军团科技信息
		Map<Integer, LegionScience> scienceMap = legion.getScienceMap();
		message.put((byte)scienceMap.size());
		for (int i = 1; i <= scienceMap.size(); i++) {
			StringBuilder sb = new StringBuilder();
			LegionScience tempLegionScience = scienceMap.get(i);
			sb.append(tempLegionScience.getId()).append(",")
			.append(tempLegionScience.getLv()).append(",")
			.append(tempLegionScience.getExp()).append(";");
			message.putString(sb.toString());
		}
		message.put(role.getUpLegionScience());
		message.put(legion.getAutoArgeeType());
		message.putInt(legion.getMinLv());
		message.putInt(role.getLegionContribution());
		message.putInt(legion.getMaxPeopleNum());
		message.putInt(legion.getMemberSet().size());
		message.putString(legion.getShortName());
		return message;
	}
	
	public void getAllLegion(){
		List<Legion> legionList = legionDao.getAllLegion();
		for(Legion tempLegion : legionList){
			LegionCache.addLegionInCache(tempLegion);
			LegionCache.getLegionShortName().put(tempLegion.getLegionId(), tempLegion.getShortName());
		}
	}

	public Message getAllLegionList(Role role){
		Message message = new Message();
		message.setType(LegionConstant.GET_LEGION_LIST);
		
		Map<Integer, Legion> legionMap = LegionCache.getLegionMap();
		message.putInt(legionMap.size());
		for(Map.Entry<Integer, Legion> entry : legionMap.entrySet()){
			Legion tempLegion = entry.getValue();
			message.putInt(tempLegion.getLegionId());
			message.putString(tempLegion.getName());
			message.put(tempLegion.getLv());
			message.putInt(tempLegion.getMemberSet().size());
			if(tempLegion.getApplyList().contains(role.getId()) 
					|| role.getApplyLegionSet().contains(tempLegion.getLegionId()))
			{
				message.putInt(LegionConstant.IS_APPLY);
			}else {
				message.putInt(LegionConstant.NOT_APPLY);
			}
			message.putInt(tempLegion.getMaxPeopleNum());
		}
		return message;
	}
	
	public Message applyJoinLegion(Role role , int legionId){
		Message message = new Message();
		message.setType(LegionConstant.APPLY_JOIN_LEGION);
		
		if(role.getApplyLegionSet().size() >= LegionConstant.MAX_ROLE_APPLY_NUM){	//最大申请数量
			message.putShort(ErrorCode.MAX_APPLY_NUM);
			return message;
		}
		Legion legion = LegionCache.getLegionById(legionId);
		if(legion == null){		//没有目标军团
			message.putShort(ErrorCode.NO_LEGION);
			return message;
		}
		if(role.getLegionId() > LegionConstant.NOT_IN_LEGION){		//玩家已经在军团中
			message.putShort(ErrorCode.IN_LEGION);
			return message;
		}
		
		if(legion.getAutoArgeeType() == LegionConstant.CLOSE_AUTO_AGREE)
		{
			legion.getApplyList().add(role.getId());
			role.getApplyLegionSet().add(legion.getLegionId());
		/*
		 * 自动接受功能开启的情况
		 * 玩家加入军团后清空玩家的军团申请列表
		 * 以及玩家申请过但是未被同意加入的军团申请列表
		 */
		}else if (legion.getAutoArgeeType() == LegionConstant.OPEN_AUTO_AGREE) {
			//军团达到最大人数
			if(legion.getMemberSet().size() > legion.getMaxPeopleNum()){	
				message.putShort(ErrorCode.MAX_PEOPLE_NUM);
				return message;
			}
			//申请但是未达到要求
			if(role.getLv() < (short)legion.getMinLv())
			{
				message.putShort(ErrorCode.NO_LV_ERR_LEGION);
				return message;
			}
			//加入成功
			legion.getMemberSet().add(role.getId());
			role.setLegionId(legion.getLegionId());
			this.clearListLegion(role);
			legion.setChange(true);
			role.setChange(true);
			this.newPlayerIn(legion, role);
			this.taskService.checkLegionTask(role, (byte)2);
		}
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	public Message getApplyList(Role role){
		Message message = new Message();
		message.setType(LegionConstant.GET_APPLY_LIST);
		
		if(role.getLegionId() <= LegionConstant.NOT_IN_LEGION){	//玩家未加入军团
			message.putShort(ErrorCode.NO_IN_LEGION);
			return message;
		}
		Legion legion = LegionCache.getLegionById(role.getLegionId());
		if(legion == null){
			message.putShort(ErrorCode.NO_LEGION);
			return message;
		}
		
		List<Integer> applyList = legion.getApplyList();
		message.putInt(applyList.size());
		for(Integer roleId : applyList){
			Role tempRole = roleService.getRoleById(roleId);
			message.putInt(tempRole.getId());
			message.putString(tempRole.getName());
			message.putShort(tempRole.getLv());
			message.putShort(tempRole.getVipLv());
		}
		return message;
	}
	
	public Message agreeJoinLegion(Role role , int targetId){
		Message message = new Message();
		message.setType(LegionConstant.AGREE_JOIN_LEGION);
		
		Legion legion  = LegionCache.getLegionById(role.getLegionId());
		Role targetRole = roleService.getRoleById(targetId);
		if(targetRole == null){		//目标用户错误
			message.putShort(ErrorCode.NULL_TARGET_ROLE);
			return message;
		}
		//军团申请列表
		List<Integer> applayList = legion.getApplyList();
		if(!applayList.contains(targetId)){		//目标用户未在军团申请列表内
			return null;
		}
		if(targetRole.getLegionId() > LegionConstant.NOT_IN_LEGION){		//目标用户已加入其它军团 移除申请
			message.putShort(ErrorCode.IN_ORDER_LEGION);
			Iterator<Integer> it = applayList.iterator();
			while(it.hasNext()){
				int tempId = it.next();
				if(tempId == targetId){
					it.remove();
					break;
				}
			}
			return message;
		}
		if(applayList.size() > legion.getMaxPeopleNum()){	//军团达到最大人数
			message.putShort(ErrorCode.MAX_PEOPLE_NUM);
			return message;
		}
		
		/*
		 * 玩家加入军团后清空玩家的军团申请列表
		 * 以及玩家申请过但是未被同意加入的军团申请列表
		 */
		this.clearListLegion(targetRole);
		//加入成功
		legion.getMemberSet().add(targetId);
		legion.setChange(true);
		targetRole.setLegionId(legion.getLegionId());
		targetRole.setChange(true);
		
		this.newPlayerIn(legion, targetRole);
		this.taskService.checkLegionTask(targetRole, (byte)2);
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	/**
	 * <font color ="red">已经弃用 </font>
	 */
	public Message agreeAllRoleJoin(Role role){
		Message message = new Message();
		message.setType(LegionConstant.AGREE_JOIN_LEGION);
		//加入成功的玩家ID集合
		Set<Integer> set = new HashSet<Integer>();
		
		Legion legion = LegionCache.getLegionById(role.getLegionId());
		if(legion == null){
			return null;
		}
		List<Integer> applyList = legion.getApplyList();
		Iterator<Integer> it = applyList.iterator();
		while(it.hasNext()){
			int tempRoleId = it.next();
			Role tempRole = roleService.getRoleById(tempRoleId);
			if(tempRole != null  && tempRole.getLegionId() <= 0 && applyList.contains(tempRole.getId())){	
				if(applyList.size() <= legion.getMaxPeopleNum()){
					legion.getMemberSet().add(tempRoleId);
					legion.setChange(true);
					tempRole.setLegionId(legion.getLegionId());
					tempRole.setChange(true);
					tempRole.setChange(true);
					set.add(tempRoleId);
					it.remove();
				}
			}
		}
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	/**
	 * 设置副团长的方法
	 * @author xjd
	 */
	public Message setDeputy(Role role , Legion legion , int targetId) {
		Message message = new Message();
		message.setType(LegionConstant.SET_DEPUTY);
		Role targetRole= roleService.getRoleById(targetId);
		//判断目标是否存在
		if(targetRole == null)
		{
			message.putShort(ErrorCode.NULL_TARGET_ROLE);
			return message;
		}
		//判断军团是否存在
		if(legion == null)
		{
			message.putShort(ErrorCode.NO_LEGION);
			return message;
		}
		//判断目标是否在此公会
		Set<Integer> memberSet = legion.getMemberSet();
		boolean b =false;
		if(memberSet.contains(targetRole.getId()))
		{
			b = true;
		}
		if(b == false)
		{
			message.putShort(ErrorCode.NO_IN_LEGION);
			return message;
		}
		//判断是否有操作权限
		if(role.getId() != legion.getCaptainId())
		{
			message.putShort(ErrorCode.NO_ACCEPT);
			return message;
		}
		//判断目标是否已经是副团长
		Set<Integer> set =legion.getDeputyCaptainSet();
		for(Integer i : set)
		{
			if(i == targetId)
			{
				message.putShort(ErrorCode.AREADY_IS_DEPUTY);
				return message;
			}
		}
		//判断目标是否是会长
		if(targetId == legion.getCaptainId())
		{
			message.putShort(ErrorCode.TARGET_IS_CAPTAIN);
			return message;
		}
		//判断副团长是否达到上限
		if(legion.getDeputyCaptainSet().size() >= LegionConstant.MAX_DEPT_LEGION)
		{
			message.putShort(ErrorCode.MAX_DEPT_LEGION);
			return message;
		}
		//将目标加入
		legion.getDeputyCaptainSet().add(targetId);
		legion.setChange(true);
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	/**
	 * 撤销副团长的方法
	 * @author xjd
	 */
	public Message cancelDeputy(Role role , Legion legion , Integer targetId) {
		Message message = new Message();
		message.setType(LegionConstant.SET_DEPUTY);
		Role targetRole= roleService.getRoleById(targetId);
		
		//判断目标是否存在
		if(targetRole == null)
		{
			message.putShort(ErrorCode.NULL_TARGET_ROLE);
			return message;
		}
		//判断军团是否存在
		if(legion == null)
		{
			message.putShort(ErrorCode.NO_LEGION);
			return message;
		}
		//判断目标是否在此公会
		Set<Integer> memberSet = legion.getMemberSet();
		boolean b =false;
		if(memberSet.contains(targetId))
		{
			b = true;
		}
		if(b == false)
		{
			message.putShort(ErrorCode.NO_IN_LEGION);
			return message;
		}
		//判断是否有操作权限
		if(role.getId() != legion.getCaptainId())
		{
			message.putShort(ErrorCode.NO_ACCEPT);
			return message;
		}
		//判断目标是否已经是副团长
		Set<Integer> set =legion.getDeputyCaptainSet();
		if(!set.contains(targetId))
		{
			message.putShort(ErrorCode.NOT_DEPUTY);
			return message;
		}
		//将目标撤除
		legion.getDeputyCaptainSet().remove(targetId);
		legion.setChange(true);
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
		
	}
	/**
	 * 踢出成员的方法
	 * @author xjd
	 */
	public Message kickMember(Role role, Legion legion, Integer targetId) {
		Message message = new Message();
		message.setType(LegionConstant.SET_DEPUTY);
		Role targetRole= roleService.getRoleById(targetId);
		//判断目标是否存在
		if(targetRole == null)
		{
			message.putShort(ErrorCode.NULL_TARGET_ROLE);
			return message;
		}
		//判断军团是否存在
		if(legion == null)
		{
			message.putShort(ErrorCode.NO_LEGION);
			return message;
		}
		//判断目标是否在此公会
		Set<Integer> memberSet = legion.getMemberSet();
		boolean b =false;
		if(memberSet.contains(targetId))
		{
			b=true;
		}
		if(b == false)
		{
			message.putShort(ErrorCode.NO_IN_LEGION);
			return message;
		}
		//判断是否有权限踢出会员
		boolean c = false;
		//判断是否为副会长
		Set<Integer> set =legion.getDeputyCaptainSet();
		if(set.contains(role.getId()))
		{
			c = true;
		//判断是否为会长
		} else if (role.getId() == legion.getCaptainId()) {
			c = true;
		}
		if(c == false)
		{
			message.putShort(ErrorCode.NO_ACCEPT);
			return message;
		}
		//判断目标是否为会长
		if(targetId == legion.getCaptainId())
		{
			message.putShort(ErrorCode.NO_ACCEPT);
			return message;
		}
		//判断目标是否为副会长
		if(set.contains(targetRole.getId()))
		{
			if(role.getId() != legion.getCaptainId())
			{
				message.putShort(ErrorCode.NO_ACCEPT);
				return message;
			}
			//从副会长的集合中移除目标
			legion.getDeputyCaptainSet().remove(targetId);
		}
		//踢出目标
		this.beKickPlayer(legion, role, targetRole);
		Mail mail = new Mail();
		mail.setAttached("");
		mail.setTitle(LegionConstant.BE_KICK_LEGION_TITILE);
		StringBuilder sb = new StringBuilder();
		sb.append(LegionConstant.BE_KICK_LEGION_TEXT_1).append(role.getName())
			.append(LegionConstant.BE_KICK_LEGION_TEXT_2);
		mail.setContext(sb.toString());
		mailService.sendSYSMail2(targetRole, mail);
		
		legion.getMemberSet().remove(targetId);
		targetRole.setLegionId(LegionConstant.QUITE_LEGION);
		legion.setChange(true);
		targetRole.setChange(true);
				
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	/**
	 * 转让会长
	 * @author xjd
	 */
	public Message changCaptain(Role role, Legion legion, Integer targetId) {
		Message message = new Message();
		message.setType(LegionConstant.SET_DEPUTY);
		Role targetRole= roleService.getRoleById(targetId);
		//判断目标是否存在
		if(targetRole == null)
		{
			message.putShort(ErrorCode.NULL_TARGET_ROLE);
			return message;
		}
		//判断军团是否存在
		if(legion == null)
		{
			message.putShort(ErrorCode.NO_LEGION);
			return message;
		}
		//判断目标是否在此公会
		Set<Integer> memberSet = legion.getMemberSet();
		boolean b =false;
		if(memberSet.contains(targetRole.getId()))
		{
			b = true;
		}
		if(b == false)
		{
			message.putShort(ErrorCode.NO_IN_LEGION);
			return message;
		}
		//判断是否有操作权限
		if(role.getId() != legion.getCaptainId())
		{
			message.putShort(ErrorCode.NO_ACCEPT);
			return message;
		}
		//判断转让目标是否为副会长
		Set<Integer> set = legion.getDeputyCaptainSet();
		if(set.contains(targetId))
		{
			legion.getDeputyCaptainSet().remove(targetId);
		}
		legion.setCaptainId(targetId);
		legion.setChange(true);
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	/**
	 * 修改军团图标
	 * @author xjd
	 */
	public Message changFaceId(Role role, Legion legion, int faceId) {
		Message message = new Message();
		message.setType(LegionConstant.CHANG_FACE_ID);
		boolean b =isAccept(role, legion);
		//判断权限
		if(b == false)
		{
			message.putShort(ErrorCode.NO_ACCEPT);
			return message;
		}
		//判断军团是否存在
		b = isLegion(legion);
		if(b ==false)
		{
			message.putShort(ErrorCode.NO_LEGION);
		}
		//判断军团图标是否合法
		if(faceId < LegionConstant.FACEID_MIN || faceId > LegionConstant.FACEID_MAX)
		{
			message.putShort(ErrorCode.ILLEGAL_FACEID);
			return message;
		}
		//设置军团
		legion.setFaceId(faceId);
		legion.setChange(true);
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	/**
	 * 修改军团公告
	 * @author xjd
	 */
	public Message changNotice(Role role, Legion legion, String notice) {
		Message message =new Message();
		message.setType(LegionConstant.CHANG_NOTICE);
		boolean b = isLegion(legion);
		//判断公会是否存在
		if(b == false)
		{
			message.putShort(ErrorCode.NO_LEGION);
			return message;
		}
		//判断是否有权限
		b = isAccept(role, legion);
		if(b == false)
		{
			message.putShort(ErrorCode.NO_ACCEPT);
			return message;
		}
		//判断公告是否合法
		if(notice.length() > LegionConstant.MAX_LENGTH_LEGION_NOTICE)
		{
			message.putShort(ErrorCode.ILLEGAL_NOTICE);
			return message;
		}
		//替换公告
		legion.setNotice(notice);
		legion.setChange(true);
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	/**
	 * 获取军团成员列表
	 * @author xjd
	 */
	public Message getAllMember(Role role, Legion legion) {
		Message  message = new Message();
		message.setType(LegionConstant.GET_ALL_MEMBER);
		boolean b =isInLegion(role, legion);
		//判断军团是否存在
		b = isLegion(legion);
		if(b == false)
		{
			message.putShort(ErrorCode.NO_LEGION);
			return message;
		}
		//判断玩家是否在军团内
		b = isInLegion(role, legion);
		if(b == false)
		{
			message.putShort(ErrorCode.NO_IN_LEGION);
			return message;
		}
		//获取成员列表
		Set<Integer> memberSet = legion.getMemberSet();
		//放入成员数量
		message.put((byte)memberSet.size());
		for(int i : memberSet)
		{
			Role memberRole = roleService.getRoleById(i);
			if(memberRole == null) continue;
			message.putString(memberRole.getName());
			message.putShort(memberRole.getLv());
			message.putInt(memberRole.getLegionContribution());
			Set<Integer> deputyCaptainSet =legion.getDeputyCaptainSet();
			//判断成员类型
			if(memberRole.getId() == legion.getCaptainId())
			{
				message.put(LegionConstant.MEMBER_TYPE1);
			} else if (deputyCaptainSet.contains(memberRole.getId())) {
				message.put(LegionConstant.MEMBER_TYPE2);
			} else {
				message.put(LegionConstant.MEMBER_TYPE3);
			}
			//最后离线时间
			IoSession ioSession = SessionCache.getSessionById(memberRole.getId());
			if(memberRole.getLastOffLineTime() == 0)
			{
				message.putString("当前在线");
			} else if (ioSession != null) {
				message.putString("当前在线");
			} else {
				message.putString(Long.toString(memberRole.getLastOffLineTime()));
			}
			message.putInt(memberRole.getId());
		}
		return message;
	}
	/**
	 * 退出公会
	 * @author xjd
	 */
	public Message quit(Role role, Legion legion) {
		Message message = new Message();
		message.setType(LegionConstant.QUIT_LEGION);
		boolean b =isInLegion(role, legion);
		//判断军团是否存在
		b = isLegion(legion);
		if(b == false)
		{
			message.putShort(ErrorCode.NO_LEGION);
			return message;
		}
		//判断玩家是否在军团内
		b = isInLegion(role, legion);
		if(b == false)
		{
			message.putShort(ErrorCode.NO_IN_LEGION);
			return message;
		}
		//判断玩家是否是会长
		if(role.getId() == legion.getCaptainId())
		{
			if(legion.getMemberSet().size() > LegionConstant.MIN_LEGION_SIZE)
			{
				message.putShort(ErrorCode.QUITE_ERR);
				return message;
			}
			//会长退出该工会
			role.setLegionId(LegionConstant.QUITE_LEGION);
			//从名字集合中删除名字
			LegionCache.getLegionNameList().remove(legion.getName());
			//从旗号集合中删除旗号
			LegionCache.getLegionShortName().remove(legion.getLegionId());
			//从缓存中删除该工会
			LegionCache.getLegionMap().remove((Integer)legion.getLegionId());
			//从数据库中删除该工会
			legionDao.removeLegion(legion.getLegionId());
			
			role.setLastQuiteLegion(System.currentTimeMillis()/1000);
			role.setChange(true);
			
			ActivityMessage am = new ActivityMessage();
			am.setLegion(legion);
			am.setAction(ActivityMessage.DISMISS_LEGION);
			am.setValue(legion.getLegionId());
			activityService.noticeActivity(am);

			message.putShort(ErrorCode.SUCCESS);
			return message;
		}
		//判断玩家是否是副会长
		Set<Integer> set =legion.getDeputyCaptainSet();
		if(set.contains(role.getId()))
		{
			legion.getMemberSet().remove(role.getId());
			legion.getDeputyCaptainSet().remove(role.getId());
		}else {
			legion.getMemberSet().remove(role.getId());
		}
		role.setLegionId(LegionConstant.QUITE_LEGION);
		role.setLastQuiteLegion(System.currentTimeMillis()/1000);
		role.setChange(true);
		legion.setChange(true);
		
		this.playerQuitLeguion(legion, role);
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	/**
	 * 公会ID搜索
	 * @author xjd
	 */
	public Message search(Integer id) {
		Message message = new Message();
		message.setType(LegionConstant.SEARCH_LEGION);
		Legion legion = LegionCache.getLegionById(id);
		if(legion == null)
		{
			message.putShort(ErrorCode.NO_LEGION);
			return message;
		}
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	/**
	 * 升级军团科技
	 * @author xjd
	 */
	public Message upgradeScience(Role role, Legion legion,byte typeM ,int useGold) {
		Message message = new Message();
		message.setType(LegionConstant.UPGRADE_SCIENCE);
		
		//判定是否强效研发符合Vip等级
		if(useGold == LegionConstant.USE_GOLD_COST_1)
		{
			
		}else if (useGold == LegionConstant.USE_GOLD_COST_2) {
			if(role.getVipLv() < LegionConstant.USE_GOLD_VIP_LV_1)
			{
				message.putShort(ErrorCode.NO_VIP_LV);
				return message;
			}
		}else if (useGold == LegionConstant.USE_GOLD_COST_3) {
			if(role.getVipLv() < LegionConstant.USE_GOLD_VIP_LV_2)
			{
				message.putShort(ErrorCode.NO_VIP_LV);
				return message;
			}
		}
		
		legion.getLock().lock();
		try
		{
			int type = (int)legion.getAppointScience();
			//判断科技类型
			if(type <= LegionConstant.TYPE_MIN || type > LegionConstant.TYPE_MAX)
			{
				message.putShort(ErrorCode.ERR_SCIENCE);
				return message;
			}
			boolean b =isInLegion(role, legion);
			//判断军团是否存在
			b = isLegion(legion);
			if(b == false)
			{
				message.putShort(ErrorCode.NO_LEGION);
				return message;
			}
			//判断玩家是否在军团内
			b = isInLegion(role, legion);
			if(b == false)
			{
				message.putShort(ErrorCode.NO_IN_LEGION);
				return message;
			}
			//判断玩家是否可以升级科技
			if(role.getUpLegionScience() != LegionConstant.UP_SCIENCE)
			{
				message.putShort(ErrorCode.ERR_NUMBER_SCIENCE);
				return message;
			}
			//取出当前军团经验（type类型）
			Map<Integer, LegionScience> map = legion.getScienceMap();
			LegionScience legionScience = (LegionScience)map.get(type);
			LegionScience legionScience2 = (LegionScience)map.get(LegionConstant.NEED_TYPE_SCIENCE);
			//判定军团等级是否足够
			if(legionScience.getLv() >= legionScience2.getLv() && legionScience.getId() != LegionConstant.NEED_TYPE_SCIENCE)
			{
				message.putShort(ErrorCode.ERR_UP_SCIENCE);
				return message;
			}
			int lv = legionScience.getLv();
			int allExp = legionScience.getExp();
			int exp = (role.getLv()*LegionConstant.MULTIPLE_UP) + (role.getVipLv()*LegionConstant.MULTIPLE_VIP_LV);
			//加上本次增加的经验,从配置表中取出下次升级需要的经验
			LegionScienceConfig legionScienceConfig = LegionScienceConfigCache.getLegionScienceConfig(type, lv);
			int need = legionScienceConfig.getNextLvExp();
			if(typeM == LegionConstant.TYPE_UP_Y)
			{
				//扣除银币
				if(role.getMoney() < exp)
				{
					message.putShort(ErrorCode.NO_MONEY);
					return message;
				}
				roleService.addRoleMoney(role, -exp);
				allExp += exp;
			}else if (typeM == LegionConstant.TYPE_UP_J) {
				
				if(role.getGold() < useGold)
				{
					message.putShort(ErrorCode.NO_GOLD);
					return message;
				}
				roleService.addRoleGold(role, -useGold);
				if(useGold == LegionConstant.USE_GOLD_COST_1)
				{
					exp *= LegionConstant.USE_GOLD_D1;
				}else if (useGold == LegionConstant.USE_GOLD_COST_2) {
					exp *= LegionConstant.USE_GOLD_D2;
				}else if (useGold == LegionConstant.USE_GOLD_COST_3) {
					exp *= LegionConstant.USE_GOLD_D3;
				}
				allExp += exp ;
			}
			//判断是否升级
			for(;;)
			{
				if(allExp - need < LegionConstant.COUNT_LV_UP)
				{
					legion.getScienceMap().get(type).setExp(allExp);
					break;
				}else if (allExp - need >= LegionConstant.COUNT_LV_UP) {
					//等级+1
					lv += LegionConstant.UP;
					legion.getScienceMap().get(type).setLv(lv);
					//经验值改变
					allExp = allExp - need;
					//军团属性改变
					legion.getScienceMap().get(type).setExp(allExp);
					//配置表等级改变
					legionScienceConfig =  LegionScienceConfigCache.getLegionScienceConfig(type, lv);
					//升级所需经验改变
					need = legionScienceConfig.getNextLvExp();
					//符合条件继续循环判断
					continue;
				}
			}
			//判断升级的是否是军团等级
			if(legionScience.getId() == 1)
			{
				legion.setLv((byte)lv);
			}
			//设置玩家本次获得的贡献
			int contribution = exp + role.getLegionContribution();
			role.setLegionContribution(contribution);
			role.setUpLegionScience(LegionConstant.UP_LIMIT_SCIENCE);
			role.setChange(true);
			legion.setChange(true);
			//记录日志
			String legionStr = Utils.getLogString(role.getId(),legion.getLegionId(),
					contribution,typeM,exp);
			legionLog.info(legionStr);
			
			
			//判断是否有任务
			this.taskService.checkLegionTask(role , (byte)1);
			
			message.putShort(ErrorCode.SUCCESS);
			message.putInt(exp);
			message.putInt(type);
			message.putInt(lv);
			message.putInt(allExp);
			message.putInt(role.getId());
			return message;
		}finally
		{
			legion.getLock().unlock();
		}
	}
	/**
	 * 设定军团科技升级的类型
	 * @author xjd
	 */
	public Message setAppointScience(Role role, Legion legion,byte type) {
		Message message = new Message();
		message.setType(LegionConstant.SET_APPOINT_SCIENCE);
		//判断军团是否存在
		if(legion == null)
		{
			message.putShort(ErrorCode.NO_LEGION);
			return message;
		}
		//判断玩家的权限是否足够
		boolean b = isAccept(role, legion);
		if(b == false)
		{
			message.putShort(ErrorCode.NO_ACCEPT);
			return message;
		}
		legion.setAppointScience(type);
		legion.setChange(true);
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	/**
	 * 设定是否开启自动接受申请功能
	 * @author xjd
	 */
	public Message setAutoAgreeJoin(Role role , Legion legion, byte type, int minLv) {
		Message message = new Message();
		message.setType(LegionConstant.SET_AUTO_AGREE_JOIN);
		if(minLv == 0 || minLv > LegionConstant.MAX_LV_AUTO)
		{
			message.putShort(ErrorCode.ILLEGAL_LV_AUTO);
			return message;
		}
		//判断玩家权限是否足够
		boolean b = this.isAccept(role, legion);
		if(b == false)
		{
			message.putShort(ErrorCode.NO_ACCEPT);
			return message;
		}
		//关闭自动接受申请
		if(type == LegionConstant.CLOSE_AUTO_AGREE)
		{
			legion.setAutoArgeeType(LegionConstant.CLOSE_AUTO_AGREE);
		//开启自动接受申请
		}else if (type == LegionConstant.OPEN_AUTO_AGREE) {
			legion.setAutoArgeeType(LegionConstant.OPEN_AUTO_AGREE);
			legion.setMinLv(minLv);
		}
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	/**
	 * 军团兑换
	 * @author xjd
	 */
	public Message exchangeLegion(Role role, Legion legion , int itemId ,byte type ,IoSession is) {
		Message message = new Message();
		message.setType(LegionConstant.EXCHANGE_LEGION);
		//检查兑换道具是否合法
		ExchangeForLegionConfig config = ExchangeForLegionConfigCache.getExchangeForLegionConfig(itemId, type);
		if(config == null)
		{
			message.putShort(ErrorCode.ERR_EXCHANGE_ITEM);
			return message;
		}
		//判断玩家是否有足够贡献
		if(role.getLegionContribution() < config.getCost())
		{
			message.putShort(ErrorCode.NO_CONTRIBUTION);
			return message;
		}
		/*
		 * 取出兑换物品
		 * 1 装备
		 * 2 道具
		 * 3 武将
		 * 通过邮件方式发送
		 */
		//装备
		Mail mail = new Mail();
		StringBuilder sb = new StringBuilder();
		if(type == LegionConstant.EQUIP_LEGION)
		{
			EquipConfig equipConfig = EquipConfigCache.getEquipConfigById(itemId);
			if(equipConfig == null)
			{
				return null;
			}
			
			sb.append(LegionConstant.TYPE_EQUIP)
			.append(equipConfig.getId()).append(",")
			.append(",")
			.append(LegionConstant.NUMBER_PROP).append(";");
			
			mail.setTargetName(role.getName());
			mail.setType(LegionConstant.TYPE_EXCHANGE_LEGION);
			mail.setTitle(LegionConstant.TITLE_EXCHANGE_LEGION);
			mail.setContext(LegionConstant.CONTEXT_EXCHANGE_LEGION);
			mail.setAttached(sb.toString());
			
			mailService.sendSYSMail(role, mail);
		//道具
		}else if (type == LegionConstant.PROP_LEGION) {
			Item item = ItemCache.getItemById(itemId);
			if(item == null)
			{
				return null;
			}
			
			sb.append(LegionConstant.TYPE_ITEM)
			.append(item.getId()).append(",")
			.append(",")
			.append(LegionConstant.NUMBER_PROP).append(";");
			
			mail.setTargetName(role.getName());
			mail.setType(LegionConstant.TYPE_EXCHANGE_LEGION);
			mail.setTitle(LegionConstant.TITLE_EXCHANGE_LEGION);
			mail.setContext(LegionConstant.CONTEXT_EXCHANGE_LEGION);
			mail.setAttached(sb.toString());
			
			mailService.sendSYSMail(role, mail);
		//武将
		}else if (type == LegionConstant.HERO_LEGION) {
			//判断是否可以兑换武将
			if(role.getHeroMap().containsKey(config.getItemId()))
			{
				message.putShort(ErrorCode.HERO_AREADY_HAS);
				return message;
			}
			//判断是否拥有权限
			if(config.getId() != LegionConstant.IS_APPLY)
			{
				ExchangeForLegionConfig temp = 
						ExchangeForLegionConfigCache.getHeroById(config.getId() - 1);
				if(!role.getHeroMap().containsKey(temp.getItemId()))
				{
					message.putShort(ErrorCode.ILLEGAL_ID_HERO);
					return message;
				}
			}
			
			HeroConfig config2 = HeroConfigCache.getHeroConfigById(config.getItemId());
			if(config2 == null) 
				return null;
			
			int heroId = config.getItemId();
			Hero hero = heroService.createHero(role,heroId);
			heroService.addHero(is, hero);
		}
		//扣除玩家贡献
		role.setLegionContribution(role.getLegionContribution() - config.getCost());
		
		//记录日志
		String legionStr = Utils.getLogString(role.getId(),legion.getLegionId(),
				type,itemId,config.getNumber(),config.getCost());
		legionLog.info(legionStr);
		
		message.putShort(ErrorCode.SUCCESS);
		message.putInt(role.getLegionContribution());
		return message;
	}
	
	/**
	 * 获取兑换信息
	 * @author xjd
	 */
	public Message getExchangeInfo(Role role, Legion legion, byte type) {
		Message message = new Message();
		message.setType(LegionConstant.GET_EXCHANGE_INFO);
		//检查兑换道具是否合法
		Map<Integer, ExchangeForLegionConfig> tempMap = null;
		if(type == LegionConstant.EQUIP_LEGION)
		{
			tempMap = ExchangeForLegionConfigCache.getEMap();
		}else if (type == LegionConstant.PROP_LEGION) {
			tempMap = ExchangeForLegionConfigCache.getIMap();
		}else if (type == LegionConstant.HERO_LEGION) {
			tempMap = ExchangeForLegionConfigCache.getHMap();
		}
		if(tempMap == null) return null;
		message.putInt(tempMap.size());
		for(ExchangeForLegionConfig x : tempMap.values())
		{
			message.putInt(x.getId());
			message.putInt(x.getItemId());
			message.putInt(x.getNumber());
			message.putInt(x.getCost());
			if(x.getType() == LegionConstant.HERO_LEGION)
			{
				if(role.getHeroMap().containsKey(x.getItemId()))
				{
					message.putInt(LegionConstant.IS_APPLY);
				}else {
					message.putInt(LegionConstant.NOT_APPLY);
				}
			}else {
				message.putInt(LegionConstant.NOT_APPLY);
			}
		}
		return message;
	}
	
	/**
	 * 拒绝玩家加入军团
	 * @author xjd
	 */
	public Message rejectJoin(Role role, int targetId) {
		Message message = new Message();
		message.setType(LegionConstant.REJECT_JOIN);
		//取出军团信息
		Legion legion  = LegionCache.getLegionById(role.getLegionId());
		//判断玩家权限是否足够
		boolean b = this.isAccept(role, legion);
		if(b == false)
		{
			message.putShort(ErrorCode.NO_ACCEPT);
			return message;
		}
		//找到目标玩家
		Role targetRole = roleService.getRoleById(targetId);
		//目标用户错误
		if(targetRole == null){		
			message.putShort(ErrorCode.NULL_TARGET_ROLE);
			return message;
		}
		//军团申请列表
		List<Integer> applayList = legion.getApplyList();
		//目标用户未在军团申请列表内
		if(!applayList.contains(targetId)){		
			return null;
		}
		//从军团的申请列表中删除
		Iterator<Integer> it = applayList.iterator();
		while(it.hasNext()){
			int tempId = it.next();
			if(tempId == targetId){
				it.remove();
				break;
			}
		}
		Integer x = (Integer)legion.getLegionId();
		targetRole.getApplyLegionSet().remove(x);
		
		targetRole.setChange(true);
		legion.setChange(true);
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	/**
	 * 拒绝所有玩家加入
	 * @author xjd
	 */
	public Message rejectAllJoin(Role role) {
		Message message = new Message();
		message.setType(LegionConstant.REJECT_JOIN);
		Legion legion = LegionCache.getLegionById(role.getLegionId());
		//判断玩家权限是否足够
		boolean b = this.isAccept(role, legion);
		if(b == false)
		{
			message.putShort(ErrorCode.NO_ACCEPT);
			return message;
		}
		//清空申请玩家的军团信息
		List<Integer> list = legion.getApplyList();
		//循环所有的申请玩家 删除该军团申请信息
		for(int x : list)
		{
			Role targetRole = RoleCache.getRoleById(x);
			targetRole.getApplyLegionSet().remove(legion.getLegionId());
			targetRole.setChange(true);
		}
		//清空军团自身的申请列表
		legion.getApplyList().clear();
		legion.setChange(true);
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

	/**
	 * 撤销申请
	 * @param role
	 * @param legionId
	 * @return
	 * @author xjd
	 */
	public Message cancelApply(Role role, int legionId) {
		Message message = new Message();
		message.setType(LegionConstant.CANCEL_APPLY);
		if(role.getLegionId() != LegionConstant.NOT_IN_LEGION)
		{
			message.putShort(ErrorCode.IN_LEGION);
			return message;
		}
		if(!role.getApplyLegionSet().contains(legionId))
		{
			message.putShort(ErrorCode.NO_LEGION);
			return message;
		}
		
		Legion legion = LegionCache.getLegionById(legionId);
		if(legion == null)
		{
			return null;
		}
		legion.getApplyList().remove((Integer)role.getId());
		role.getApplyLegionSet().remove((Integer)legionId);
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	/**
	 * 增加军团最大人数
	 * @author xjd
	 */
	public Message addMaxPeopleNum(Role role) {
		Message message = new Message();
		message.setType(LegionConstant.ADD_MAX_PEOPLE_NUM);
		if(role.getLegionId() == LegionConstant.NOT_IN_LEGION)
		{
			return null;
		}
		Legion legion = LegionCache.getLegionById(role.getLegionId());
		if(legion == null)
		{
			return null;
		}
		if(legion.getMaxPeopleNum() >= LegionConstant.MAX_PEOPLE_NUM
				|| legion.getMaxPeopleNum() + LegionConstant.ADD_NUM_PEOPLE > LegionConstant.MAX_PEOPLE_NUM)
		{
			message.putShort(ErrorCode.MAX_PEOPLE_NUM);
			return message;
		}
		if(role.getGold() < LegionConstant.COST_ADD_PEOPLE)
		{
			message.putShort(ErrorCode.NO_GOLD);
			return message;
		}
		roleService.addRoleGold(role, -LegionConstant.COST_ADD_PEOPLE);
		legion.setMaxPeopleNum(legion.getMaxPeopleNum() + LegionConstant.ADD_NUM_PEOPLE);
		
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	
	/**
	 * 设定世界大战集结旗
	 */
	public Message setTarget(Role role ,int cityId) {
		Message message = new Message();
		message.setType(LegionConstant.SET_TARGET);
		int nowTime = Utils.getNowTime();
		//权限检查
		Legion legion = LegionCache.getLegionById(role.getLegionId());
		if(legion == null) {
			message.putInt(ErrorCode.NO_LEGION);
			return message;
		}
		if(legion.getCaptainId() != role.getId())
		{
			message.putShort(ErrorCode.NO_ACCEPT);
			return message;
		}
		int lastCityTime = 0;
		// 如果不在旗子有效期
		if (nowTime > legion.getLastCityTime() + GeneralNumConstantCache.getValue("LEGION_EFFECTIVE_TIME")) {
			// 检查CD时间
			if (nowTime < legion.getLastCityTime() + GeneralNumConstantCache.getValue("LEGION_CITY_ID_TIME")) {
				message.putShort(ErrorCode.ERR_TIME_CIYT_ID);
				return message;
			}
			lastCityTime = nowTime;
		}else{
			lastCityTime = legion.getLastCityTime();
		}
		
		//检查城市ID
		for(int x : LegionConstant.ILLEGAL_CITY_ID)
		{
			if(x == cityId)
			{
				message.putShort(ErrorCode.ERR_CITY_ID);
				return message;
			}
		}
		//集结成功
		legion.setCityId(cityId);
		legion.setLastCityTime(lastCityTime);
		message.putShort(ErrorCode.SUCCESS);
		int legionId = role.getLegionId();
		String cityName = CityInfoConfigCache.getCityInfoConfigById(cityId).getCityName();
		String name = LegionCache.getLegionById(legionId).getName();
		
		StringBuilder sb = new StringBuilder();
		sb.append(cityId).append(",");
		sb.append(cityName).append(",");
		sb.append(name).append(",");
		sb.append(role.getLegionId()).append(",");
		chatService.systemChat(role, ChatConstant.MESSAGE_TYPE_LEGION, sb.toString());

		return message;
	}
	
	
	/**
	 * 修改旗号
	 */
	public Message changeShortName(Role role, String shortName) {
		Message message = new Message();
		message.setType(LegionConstant.CHANGE_SHORT_NAME);
		//旗号检查
		if(shortName.length() > LegionConstant.MAX_LENGTH_SHORT_NAME)
		{
			message.putShort(ErrorCode.ERR_LENGTH_SHORT_NAME);
			return message;
		}
		if(LegionCache.getLegionShortName().containsValue(shortName))
		{
			message.putShort(ErrorCode.ERR_SHORT_NAME);
			return message;
		}
		//权限检查
		Legion legion = LegionCache.getLegionById(role.getLegionId());
		if(legion == null) return null;
		if(legion.getCaptainId() != role.getId())
		{
			message.putShort(ErrorCode.NO_ACCEPT);
			return message;
		}
		//检查元宝
		int needNum = GeneralNumConstantCache.getValue("CHANGE_SHORT_NAME");
		if(role.getGold() < needNum)
		{
			message.putShort(ErrorCode.NO_GOLD);
			return message;
		}
		//修改旗号
		roleService.addRoleGold(role, -needNum);
		legion.setShortName(shortName);
		LegionCache.getLegionShortName().put(legion.getLegionId(), shortName);
		legion.setChange(true);
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	
	/**
	 * 判断是否有权限(会长||副会长)
	 * @param role
	 * @param legion
	 * @return
	 * @author xjd 
	 */
	private boolean isAccept(Role role , Legion legion)
	{
		boolean c = false;
		//判断是否为副会长
		Set<Integer> set =legion.getDeputyCaptainSet();
		if(set.contains(role.getId()))
		{
			c = true;
		}
		//判断是否为会长
		if(role.getId() == legion.getCaptainId())
		{
			c = true;
		}
		return c;
	}
	/**
	 * 判断军团是否存在
	 * @param role
	 * @param legion
	 * @return
	 * @author xjd
	 */
	private boolean isLegion(Legion legion)
	{
		boolean b = true;
		//判断军团是否存在
		if(legion == null)
		{
			b = false;
		}
		return b;		
	}
	/**
	 * 判断玩家是否在军团内
	 * @param role
	 * @param legion
	 * @return
	 * @author xjd
	 */
	private boolean isInLegion(Role role , Legion legion)
	{
		boolean b =false;
		Set<Integer> memberSet = legion.getMemberSet();
		if(memberSet.contains(role.getId()))
		{
			b = true;
		}
		return b;
	}
	/**
	 * 清空玩家的军团申请列表，军团申请列表中的该玩家编号
	 * @param role
	 * @author xjd
	 */
	private void clearListLegion(Role role)
	{
		Integer tempId = role.getId();
		//取出申请过的军团列表
		Set<Integer> set = role.getApplyLegionSet();
		//循环所有的额军团 删除该军团申请列表中的玩家信息
		for(Integer x : set)
		{
			Legion legion = LegionCache.getLegionById(x);
			if(legion == null) continue;
			legion.getApplyList().remove(tempId);
			legion.setChange(true);
		}
		//清空玩家自己的申请列表
		role.getApplyLegionSet().clear();
		role.setChange(true);
	}
	
	/***
	 * 新玩家加入军团
	 * @param legion
	 * @param targetRole
	 */
	private void newPlayerIn(Legion legion , Role targetRole)
	{
		Message message = new Message();
		message.setType(LegionConstant.NEW_PLAYER_JOIN);
		message.putString(targetRole.getName());
		message.putInt(legion.getLegionId());
		
		//通知
		for(int x : legion.getMemberSet())
		{
			IoSession ioSession = SessionCache.getSessionById(x);
			ioSession = SessionCache.getSessionById(x);
			if(ioSession != null) ioSession.write(message);
		}
		
	}
	/**
	 * 玩家退出公会
	 * @param legion
	 * @param targetRole
	 */
	private void playerQuitLeguion(Legion legion , Role targetRole)
	{
		Message message = new Message();
		message.setType(LegionConstant.PLAYER_QUIT_LEGION);
		message.putString(targetRole.getName());
		//通知
		for(int x : legion.getMemberSet())
		{
			IoSession ioSession = SessionCache.getSessionById(x);
			ioSession = SessionCache.getSessionById(x);
			if(ioSession != null) ioSession.write(message);
		}
	}
	
	private void beKickPlayer(Legion legion , Role role ,Role targetRole)
	{
		Message message = new Message();
		message.setType(LegionConstant.BE_KICK_LEGION);
		message.putString(role.getName());
		message.putString(targetRole.getName());
		//通知
		for(int x : legion.getMemberSet())
		{
			IoSession ioSession = SessionCache.getSessionById(x);
			ioSession = SessionCache.getSessionById(x);
			if(ioSession != null) ioSession.write(message);
		}
	}
	/**
	 * 待用未确定<br/>
	 * 通知客户端军团信息改变
	 * @param legion		军团（军团长，副团长）
	 * @param targetRole	目标用户
	 * @author xjd
	 */
	@SuppressWarnings("unused")
	private void changeInfo(Legion legion , Role targetRole)
	{
		Message message = new Message();
		message.setType(LegionConstant.INFO_CHANGE);
		
		//通知目标用户
		IoSession ioSession = SessionCache.getSessionById(targetRole.getId());
		ioSession.write(message);
		
		//通知军团长
		ioSession = SessionCache.getSessionById(legion.getCaptainId());
		if(ioSession != null) ioSession.write(message);
		
		//通知副团长
		for(int x : legion.getDeputyCaptainSet())
		{
			ioSession = SessionCache.getSessionById(x);
			if(ioSession != null) ioSession.write(message);
		}
	}
}
