package byCodeGame.game.moudle.task.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import byCodeGame.game.cache.file.ArmsConfigCache;
import byCodeGame.game.cache.file.DailyTaskCache;
import byCodeGame.game.cache.file.DailyTaskMapCache;
import byCodeGame.game.cache.file.HeroConfigCache;
import byCodeGame.game.cache.file.LevelUnLockCache;
import byCodeGame.game.cache.file.TaskInfoCache;
import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.cache.local.SessionCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Mail;
import byCodeGame.game.entity.bo.Prop;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.bo.Tasks;
import byCodeGame.game.entity.file.ArmsConfig;
import byCodeGame.game.entity.file.DailyTask;
import byCodeGame.game.entity.file.DailyTaskMap;
import byCodeGame.game.entity.file.HeroConfig;
import byCodeGame.game.entity.file.LevelUnLock;
import byCodeGame.game.entity.file.TaskInfo;
import byCodeGame.game.entity.po.ChapterReward;
import byCodeGame.game.entity.po.Task;
import byCodeGame.game.moudle.chapter.service.ChapterService;
import byCodeGame.game.moudle.hero.service.HeroService;
import byCodeGame.game.moudle.inCome.InComeConstant;
import byCodeGame.game.moudle.inCome.service.InComeService;
import byCodeGame.game.moudle.mail.service.MailService;
import byCodeGame.game.moudle.prop.service.PropService;
import byCodeGame.game.moudle.role.service.RoleService;
import byCodeGame.game.moudle.task.TaskConstant;
import byCodeGame.game.remote.Message;
import byCodeGame.game.util.Utils;
import byCodeGame.game.util.events.GoldChangEvent;
import byCodeGame.game.util.handler.HandlerListener;

public class TaskServiceImpl implements TaskService,HandlerListener {
	private static final Logger taskLog = LoggerFactory.getLogger("task");
	
	private RoleService roleService;
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	private PropService propService;
	public void setPropService(PropService propService) {
		this.propService = propService;
	}
	private MailService mailService;
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}
	private InComeService inComeService;
	public void setInComeService(InComeService inComeService){
		this.inComeService = inComeService;
	}
	private ChapterService chapterService;
	public void setChapterService(ChapterService chapterService) {
		this.chapterService = chapterService;
	}
	private HeroService heroService;
	public void setHeroService(HeroService heroService) {
		this.heroService = heroService;
	}
	
	/**
	 * 监听器处理每日任务扣除金币时的业务逻辑
	 * @author xjd
	 */
	public void processEvent(GoldChangEvent event) {
		Role tempRole = RoleCache.getRoleById(event.getRoleId());
		this.checkResourceTask(tempRole, event.getType(), event.getValue());
	}
	
	
	public Message getDoningTask(Role role){
		Message message = new Message();
		message.setType(TaskConstant.GET_TASK);
		Map<Integer, Task> doingTask = role.getTasks().getDoingTaskMap();
		message.put((byte)doingTask.size());
		for(Map.Entry<Integer, Task> entry : doingTask.entrySet()){
			Task tempTask = entry.getValue();
			message.putInt(tempTask.getTaskId());
			TaskInfo tempInfo = TaskInfoCache.getputTaskInfoById(tempTask.getTaskId());
			message.put((byte)tempInfo.getFunctionType());
			message.putInt(tempTask.getCurrentNum());
			message.put(tempTask.getIsComplete());
			message.putString(tempInfo.getTitle());
			message.putString(tempInfo.getContent());
			message.putString(tempInfo.getAwardStr());
			message.putInt(tempInfo.getCompleteNum());
			message.put((byte)tempInfo.getTaskType());
			message.putInt(tempInfo.getType1());
			message.putInt(tempInfo.getType2());
		}
		return message;
	}
	
	/**
	 * 完成任务：<br/>
	 * 判定任务是否完成<br/>
	 * 领取任务奖励，若背包格子不够则通过邮件发送<br/>
	 * 检查是否有后续任务<br/>
	 * 完成任务(从doingMap中删除该任务)
	 * @author xjd
	 * 
	 */
	public Message completeTask(Role role , Integer taskId , IoSession is){
		Message message = new Message();
		message.setType(TaskConstant.COMPLETE_TASK);
		Task task = role.getTasks().getDoingTaskMap().get(taskId);
		if(task == null)
		{
			task = role.getTasks().getDailyTaskMap().get(taskId);
		}
		if(task == null){	//任务列别中没有该任务
			return null;
		}
		if(task.getIsComplete() != TaskConstant.COMPLENT){	//任务未完成
			message.putShort(ErrorCode.NOT_COM_TASK);
			return message;
		}
		//提取任务奖励
		String str = this.getAwardTask(role, taskId, is);
		//检查是否有下一阶段任务
		this.getNextTask(role, taskId);
		//删除已完成的任务
		if(role.getTasks().getDoingTaskMap().containsKey(taskId))
		{
			role.getTasks().getDoingTaskMap().remove(taskId);
		}else if (role.getTasks().getDailyTaskMap().containsKey(taskId)) {
			role.getTasks().getDailyTaskMap().remove(taskId);
		}
		
		role.getTasks().setChange(true);
		role.setChange(true);
		//记录日志
		String logStr = Utils.getLogString(role.getId(), taskId, str);
		taskLog.info(logStr);
				
		message.putShort(ErrorCode.SUCCESS);
		message.putInt(taskId);
		message.putString(str);

		return message;
	}

	/**
	 * 获取新的任务服务器主动推送 （通知客户端）
	 * @param role
	 * @param is
	 * @return
	 * @author xjd
	 */
	public void getNewTask(Task task ,Role role)
	{
		IoSession is = SessionCache.getSessionById(role.getId());
		TaskInfo tempInfo = TaskInfoCache.getputTaskInfoById(task.getTaskId());
		if(is == null || tempInfo == null) return;
		Message message = new Message();
		message.setType(TaskConstant.GET_NEW_TASK);
		message.putInt(task.getTaskId());
		message.put((byte)tempInfo.getFunctionType());
		message.putInt(task.getCurrentNum());
		message.put((byte)task.getIsComplete());
		message.putString(tempInfo.getTitle());
		message.putString(tempInfo.getContent());
		message.putString(tempInfo.getAwardStr());
		message.putInt(tempInfo.getCompleteNum());
		message.put((byte)tempInfo.getTaskType());
		message.putInt(tempInfo.getType1());
		message.putInt(tempInfo.getType2());
		is.write(message);
	}
	
	
	/**
	 * 504 更新任务进度
	 * @author xjd
	 */
	public void updateTask(Task task, Role role) {
		IoSession is = SessionCache.getSessionById(role.getId());
		if(is == null) return;
		Message message = new Message();
		message.setType(TaskConstant.UPDATE_TASK);
		message.putInt(task.getTaskId());
		message.putInt(task.getCurrentNum());
		message.put(task.getIsComplete());
		is.write(message);
		TaskInfo info = TaskInfoCache.getputTaskInfoById(task.getTaskId());
		if(info.getTaskType() == TaskConstant.TASK_TYPE_DAILY
				&& task.getIsComplete() == TaskConstant.COMPLENT)
		{
			this.atuoCompletTask(task, role);
		}
	}
	
	/**
	 * 505 领取每日任务奖励
	 * @author xjd
	 * 
	 */
	public Message getDailyTaskAward(Role role, int id) {
		Message message = new Message();
		message.setType(TaskConstant.GET_AWARD_DAILY_TASK);
		Tasks tasks = role.getTasks();
		DailyTask dailyTask = DailyTaskCache.getDailyTaskByNumber(id);
		//判断id是否合法
		if(dailyTask == null)
		{
			return null;
		}
		//判断奖励是否已经领取
		if(tasks.getAlreadyGetToday().contains((Integer)id))
		{
			message.putShort(ErrorCode.ALREADY_GET_TODAY);
			return message;
		}
		//判断完成度是否足够
		if(tasks.getDailyTaskComplete() < dailyTask.getNeeds())
		{
			message.putShort(ErrorCode.ERR_GET_DAILY_TASK_AWARD);
			return message;
		}
		
		//加入今日已领取的集合
		tasks.getAlreadyGetToday().add(id);
		Set<ChapterReward> set = Utils.changStrToAward(dailyTask.getAward());
		chapterService.getAward(role, set);
		
		//返回客户端
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	/**
	 * 506 一键完成每日任务
	 * @author xjd
	 */
	public Message quickCompleteDailyTask(Role role) {
		Message message = new Message();
		message.setType(TaskConstant.QUICK_COMPLETE_DAILT_TASK);
		Tasks tasks = role.getTasks();
		Map<Integer, Task> tempMap = tasks.getDailyTaskMap();
		//判断任务数量
		if(tempMap.size() == TaskConstant.TYPE_0)
		{
			message.putShort(ErrorCode.ERR_DAILY_TASK_MAP);
			return message;
		}
		//计算一键完成的花费
		int tempCost = TaskConstant.COST_QUICK_COMPLETE * tempMap.size();
		//判断玩家金币是否足够
		if(role.getGold() < tempCost)
		{
			message.putShort(ErrorCode.NO_GOLD);
			return message;
		}
		//扣除金币
		roleService.addRoleGold(role, -tempCost);
		//遍历任务奖励
		int awardComplete = TaskConstant.TYPE_0;
		for(Map.Entry<Integer, Task> entry : tempMap.entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			String tempStr[] = tempTaskInfo.getAwardStr().split(";");
			String temAward[] = tempStr[0].split(",");
			if(entry.getValue().getIsComplete() != TaskConstant.AUTO_COMPLENT)
			{
				awardComplete += Integer.parseInt(temAward[2]);
			}
		}
		//增加完成度
		tasks.setDailyTaskComplete(tasks.getDailyTaskComplete() + awardComplete);
		//清空每日任务列表
		tempMap.clear();
		
		message.putShort(ErrorCode.SUCCESS);
		message.putInt(awardComplete);
		return message;
	}
	
	/**
	 * 重置每日任务列表---第一次出现每日任务为2级完成新手引导时<br/>
	 * 之后每天凌晨4点重置所有任务，奖励，完成度
	 * @author xjd
	 */
	public void initDailyTask(Role role) {
		role.setArmsResearchNum(TaskConstant.ARMS_RESEARCH_NUM);
		//判断玩家等级
//		if(role.getLv() < TaskConstant.INIT_DAILY_TASK_LV)
//		{
//			return;
//		}
		//重置玩家每日完成度
		role.getTasks().setDailyTaskComplete(TaskConstant.TYPE_0);
		//重置玩家领取List
		role.getTasks().getAlreadyGetToday().clear();
		//重置玩家每日任务列表
		role.getTasks().getDailyTaskMap().clear();
		
		for(Map.Entry<Integer, DailyTaskMap> entry : DailyTaskMapCache.getMap().entrySet())
		{
			Task tempTask = new Task();
			tempTask.setTaskId(entry.getValue().getTaskId());
			TaskInfo info = TaskInfoCache.getputTaskInfoById(tempTask.getTaskId());
			if(info.getFunctionType() == TaskConstant.TYPE_VIP
					&& role.getVipLv() >= TaskConstant.COMPLENT)
			{
				tempTask.setCurrentNum(TaskConstant.COMPLENT);
				tempTask.setIsComplete(TaskConstant.AUTO_COMPLENT);
//				this.updateTask(tempTask, role);
//				this.getAwardTask(role, taskId, is);
				String temp = info.getAwardStr().split(";")[0].split(",")[2];
				role.getTasks().setDailyTaskComplete(role.getTasks().
						getDailyTaskComplete() + Integer.parseInt(temp));
			}
			role.getTasks().getDailyTaskMap().put(tempTask.getTaskId(), tempTask);
		}
//		role.onGoldChanged.addDemoListener(this);
		
		role.getTasks().setChange(true);
	}
	
	/***
	 * 507 获取每日任务数据
	 * @author xjd
	 */
	public Message getDailyTask(Role role) {
		Message message = new Message();
		message.setType(TaskConstant.GET_DAIL_TASK);
		
		message.putInt(role.getTasks().getDailyTaskComplete());
		message.put((byte)DailyTaskCache.getDailyTaskMap().size());
		for(Entry<Integer, DailyTask> x : DailyTaskCache.getDailyTaskMap().entrySet())
		{
			message.putInt(x.getValue().getNumber());
			message.putInt(x.getValue().getNeeds());
			if(x.getValue().getAward() == null || x.getValue().getAward().equals(""))
			{
				message.putString(TaskConstant.NOT_COMPLENT + "");
			}else {
				message.putString(x.getValue().getAward());
			}
			if(role.getTasks().getAlreadyGetToday().contains(x.getKey()))
			{
				message.put(TaskConstant.COMPLENT);
			}else {
				message.put(TaskConstant.NOT_COMPLENT);
			}
		}
		message.put((byte)DailyTaskMapCache.getMap().size());
		for(Entry<Integer, DailyTaskMap> entry : DailyTaskMapCache.getMap().entrySet()){
			int tempId = entry.getValue().getTaskId();
			Task tempTask = role.getTasks().getDailyTaskMap().get(tempId);
			TaskInfo tempInfo = TaskInfoCache.getputTaskInfoById(tempId);
			message.putInt(tempId);
			message.put((byte)tempInfo.getFunctionType());
			if(tempTask == null
					||tempTask.getIsComplete() == TaskConstant.AUTO_COMPLENT)
			{
				message.putInt(tempInfo.getCompleteNum());
				message.put(TaskConstant.COMPLENT);
				message.put(TaskConstant.COMPLENT);
			}else {
				message.putInt(tempTask.getCurrentNum());
				message.put(tempTask.getIsComplete());
				message.put(TaskConstant.NOT_COMPLENT);
			}
			message.putString(tempInfo.getTitle());
			message.putString(tempInfo.getContent());
			message.putString(tempInfo.getAwardStr());
			message.putInt(tempInfo.getCompleteNum());
			message.putInt(tempInfo.getType1());
			message.putInt(tempInfo.getType2());
		}
		return message;
	}
	
	/**
	 * 重载的建筑任务的方法，仅用于新建任务
	 * @author xjd
	 */
	public void checkBuildUpTask(Role role , byte type1 ,byte type2 , byte buildLv){
		Tasks tasks = role.getTasks();
		Map<Integer, Task> doingMap = tasks.getDoingTaskMap();
		
		for(Map.Entry<Integer, Task> entry : doingMap.entrySet()){
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_BUILD 
					&& tempTaskInfo.getType1() == (int)type1 
					&& tempTaskInfo.getType2() == (int)type2){
				
				if(tempTaskInfo.getType2() == 1){		//累计升级次数
					tempTask.setCurrentNum(tempTask.getCurrentNum() + 1);
				}
				if(tempTaskInfo.getType2() == 2){		//判断等级是否达到
					tempTask.setCurrentNum(buildLv);
				}
				if(tempTaskInfo.getType2() == 3){		//新建一个建筑
					tempTask.setCurrentNum(buildLv);
				}
				//判断该任务是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
	}
	/**
	 * 检查建筑升级任务的方法
	 * @param role
	 * @param type1
	 * @param buildLv
	 * @author xjd
	 */
	public void checkBuildUpTask(Role role , byte type1 , byte buildLv){
		Tasks tasks = role.getTasks();
		Map<Integer, Task> doingMap = tasks.getDoingTaskMap();
		
		for(Map.Entry<Integer, Task> entry : doingMap.entrySet()){
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_BUILD 
					&& tempTaskInfo.getType1() == (int)type1 ){
				
				if(tempTaskInfo.getType2() == 1){		//累计升级次数
					tempTask.setCurrentNum(tempTask.getCurrentNum() + 1);
				}
				if(tempTaskInfo.getType2() == 2){		//判断等级是否达到
					if(tempTask.getCurrentNum() < buildLv)
					{
						tempTask.setCurrentNum(buildLv);
					}
				}
				if(tempTaskInfo.getType2() == 3){		//新建一个建筑
					tempTask.setCurrentNum(buildLv);
				}
				//判断该任务是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
	}
	
	/**
	 * 玩家升级时检测是否有新的任务费解锁
	 * 若果有则加入Map中
	 * @author xjd
	 */
	public void lvUpTaskCheck(Role role) {
		LevelUnLock levelUnLock = LevelUnLockCache.getLevelUnLockByLv(role.getLv());
		if(levelUnLock == null) return;
		int[] temp  = levelUnLock.getTaskIdArr();
		//检查是否有新的任务解锁
		if(temp[0] == 0 && temp.length <= 1)
		{
			return;
		}
		//将任务加入正在执行的Map中
		for(int x : levelUnLock.getTaskIdArr())
		{
			Task tempTask = new Task();
			tempTask.setTaskId(x);
			role.getTasks().getDoingTaskMap().put(x, tempTask);
			this.getNewTask(tempTask, role);
		}
		role.getTasks().setChange(true);
	}
	
	/**
	 * 征收相关任务
	 * @author xjd
	 */
	public void checkInComeTask(Role role , byte type1 ,byte type2){
		Tasks tasks = role.getTasks();
		Map<Integer, Task> doingMap = tasks.getDoingTaskMap();
		Map<Integer, Task> dailyMap = tasks.getDailyTaskMap();
		for(Map.Entry<Integer, Task> entry : doingMap.entrySet()){
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_INCOME 
					&& tempTaskInfo.getType1() == type1){
				// 每小时总生产值超过(民居)
				if (tempTaskInfo.getType2() == type2 && tempTaskInfo.getType1() == TaskConstant.TYPE_HOUSE) {
					int houseHourIncome = inComeService.getHourIncomeValue(role, InComeConstant.TYPE_HOUSE);
					tempTask.setCurrentNum(houseHourIncome);
				}
				// 每小时生产总值超过(农田)
				if (tempTaskInfo.getType2() == type2 && tempTaskInfo.getType1() == TaskConstant.TYPE_FARM) {
					int farmHourIncome = inComeService.getHourIncomeValue(role, InComeConstant.TYPE_FARM);
					tempTask.setCurrentNum(farmHourIncome);
				}
//				//每小时生产总值超过(兵营)
//				if(tempTaskInfo.getType2() == 1 && tempTaskInfo.getType1() == 3){	
//					int farmHourIncome = inComeService.getHourIncomeValue(role, (byte)3);
//					tempTask.setCurrentNum(farmHourIncome);
//				}
				//派遣征收
				if(type2 == TaskConstant.TYPE_2 && tempTaskInfo.getType1() == type1
						||
				type2 == TaskConstant.TYPE_2 && tempTaskInfo.getType1() == TaskConstant.TYPE_0)
				{
					tempTask.setCurrentNum(tempTask.getCurrentNum() + TaskConstant.TYPE_1);
				}
				//判断该任务是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
		for(Map.Entry<Integer, Task> entry : dailyMap.entrySet()){
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTask.getIsComplete() == TaskConstant.AUTO_COMPLENT)
			{
				continue;
			}
			//type1 = 民居时
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_INCOME 
					&& tempTaskInfo.getType1() == type1 
					||
					tempTaskInfo.getFunctionType() == TaskConstant.TYPE_INCOME 
					&& tempTaskInfo.getType1() == TaskConstant.TYPE_0
					&& tempTaskInfo.getType2() == TaskConstant.TYPE_2){
				//每小时总生产值超过(民居)
				if(tempTaskInfo.getType2() == 1 && tempTaskInfo.getType1() == 3){	
					int houseHourIncome = inComeService.getHourIncomeValue(role, InComeConstant.TYPE_HOUSE);
					tempTask.setCurrentNum(houseHourIncome);
				}
				//每小时生产总值超过(农田)
				if(tempTaskInfo.getType2() == 1 && tempTaskInfo.getType1() == 2){	
					int farmHourIncome = inComeService.getHourIncomeValue(role, InComeConstant.TYPE_FARM);
					tempTask.setCurrentNum(farmHourIncome);
				}
				//征收次数
				if(type2 == TaskConstant.TYPE_2)
				{
					tempTask.setCurrentNum(tempTask.getCurrentNum() + TaskConstant.TYPE_1);
				}
				//判断该任务是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
	}

	/**
	 * 招募相关
	 * @author xjd
	 */
	public void checkRecruitTask(Role role, byte type1, int num) {
		Tasks tasks = role.getTasks();
		Map<Integer, Task> doingMap = tasks.getDoingTaskMap();
		Map<Integer, Task> dailyMap = tasks.getDailyTaskMap();
		
		for(Map.Entry<Integer, Task> entry : doingMap.entrySet()){
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_RECRUIT 
					&& tempTaskInfo.getType1() == type1)
			{
				if(tempTaskInfo.getType2() == 1){		//招募数量
					tempTask.setCurrentNum(tempTask.getCurrentNum() + 1);
				}
				if(tempTaskInfo.getType2() == 2){		//武将数量
					tempTask.setCurrentNum(num);
				}
				//判断该任务是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
		for(Map.Entry<Integer, Task> entry : dailyMap.entrySet()){
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTask.getIsComplete() == TaskConstant.AUTO_COMPLENT)
			{
				continue;
			}
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_RECRUIT 
					&& tempTaskInfo.getType1() == type1)
			{
				if(tempTaskInfo.getType2() == 1){		//招募数量
					tempTask.setCurrentNum(tempTask.getCurrentNum() + num);
				}
				if(tempTaskInfo.getType2() == 2){		//武将数量
					tempTask.setCurrentNum(num);
				}
				//判断该任务是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
		
	}

	/**
	 * 关卡任务
	 * @author xjd
	 */
	public void checkChapterTask(Role role, int type1) {
		Tasks tasks = role.getTasks();
		Map<Integer, Task> doingMap = tasks.getDoingTaskMap();
		Map<Integer, Task> dailyMap = tasks.getDailyTaskMap();
		for(Map.Entry<Integer, Task> entry : doingMap.entrySet()){
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_CHAPTER 
					&& tempTaskInfo.getType1() == type1 
					||tempTaskInfo.getFunctionType() == TaskConstant.TYPE_CHAPTER
					&& tempTaskInfo.getType1() == TaskConstant.TYPE_0)
			{
				if(tempTaskInfo.getType2() == 1){		//首次通关
					if(role.getChapter().getStarMap().containsKey(type1))
					{
						tempTask.setCurrentNum(tempTask.getCurrentNum() + 1);
					}
				}
				if(tempTaskInfo.getType2() == 2){		//通关次数
					tempTask.setCurrentNum(tempTask.getCurrentNum() + 1);
				}
				//任务是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
			
		}
		for(Map.Entry<Integer, Task> entry : dailyMap.entrySet()){
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTask.getIsComplete() == TaskConstant.AUTO_COMPLENT)
			{
				continue;
			}
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_CHAPTER 
					&& tempTaskInfo.getType1() == type1 
					||tempTaskInfo.getFunctionType() == TaskConstant.TYPE_CHAPTER
					&& tempTaskInfo.getType1() == TaskConstant.TYPE_0)
			{
				if(tempTaskInfo.getType2() == 1){		//首次通关
					tempTask.setCurrentNum(tempTask.getCurrentNum() + 1);
				}
				if(tempTaskInfo.getType2() == 2){		//通关次数
					tempTask.setCurrentNum(tempTask.getCurrentNum() + 1);
				}
				//任务是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
	}

	/**
	 * 邮件相关任务
	 * @author xjd
	 * 
	 */
	public void checkMailTask(Role role, byte type1) {
		Tasks tasks = role.getTasks();
		Map<Integer, Task> doingMap = tasks.getDoingTaskMap();
		for(Map.Entry<Integer, Task> entry : doingMap.entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_MAIL 
					&& tempTaskInfo.getType1() == type1)
			{
				if(tempTaskInfo.getType2() == 1){		//提取一次附件
					tempTask.setCurrentNum(tempTask.getCurrentNum() + 1);
				}
				//任务是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
			
		}
		
	}
	
	/**
	 * 拓展相关任务
	 * @author xjd
	 */
	public void checkExpandHeroTask(Role role, byte type1) {
		Tasks tasks = role.getTasks();
		Map<Integer, Task> doingMap = tasks.getDoingTaskMap();
		for(Map.Entry<Integer, Task> entry : doingMap.entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_EXPEND 
					&& tempTaskInfo.getType1() == type1)
			{
				if(tempTaskInfo.getType2() == 1){		//扩展一次武将栏
					tempTask.setCurrentNum(tempTask.getCurrentNum() + 1);
				}
				//任务是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
	}

	/**
	 * 配置兵种相关任务
	 * @author xjd
	 */
	public void checkDeployArmsTask(Role role, int armsId) {
		Tasks tasks = role.getTasks();
		ArmsConfig armsConfig = ArmsConfigCache.getArmsConfigById(armsId);
		Map<Integer, Task> doingMap = tasks.getDoingTaskMap();
		for(Map.Entry<Integer, Task> entry : doingMap.entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_DEPLOY 
					&& tempTaskInfo.getType1() == armsConfig.getFunctionType())
			{
				if(tempTaskInfo.getType2() == 1){		//保存一次兵种配置
					tempTask.setCurrentNum(tempTask.getCurrentNum() + 1);
				}
				//任务是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
	}
	
	/**
	 * 训练相关任务
	 * @author xjd
	 */
	public void checkTrainTask(Role role ,int heroId ,byte type) {
		Tasks tasks = role.getTasks();
		Map<Integer, Task> doingMap = tasks.getDoingTaskMap();
		for(Map.Entry<Integer, Task> entry : doingMap.entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_TRAIN 
					&& tempTaskInfo.getType1() == TaskConstant.COMPLENT
					||(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_TRAIN 
					&& tempTaskInfo.getType1() == heroId))
			{
				if(tempTaskInfo.getType2() == type){		//武将突飞一次
					tempTask.setCurrentNum(tempTask.getCurrentNum() + 1);
				}
				//任务是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
	}
	
	/**
	 * 科技相关任务
	 * @author xjd
	 * 
	 */
	public void checkScienceTask(Role role, int type1) {
		Tasks tasks = role.getTasks();
		Map<Integer, Task> doingMap = tasks.getDoingTaskMap();
		for(Map.Entry<Integer, Task> entry : doingMap.entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_SCIENCE
					&& tempTaskInfo.getType1() == type1
					||(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_SCIENCE
							&& tempTaskInfo.getType1() == TaskConstant.ANY_SCIENCE))
			{
				if(tempTaskInfo.getType2() == TaskConstant.TYPE_1){		//提升一级
					tempTask.setCurrentNum(TaskConstant.TYPE_1);
				}
				if(tempTaskInfo.getType2() == TaskConstant.TYPE_2)		//等级达到
				{
					int temp = type1;
					if(role.getRoleScienceMap().get((Integer)temp) > tempTask.getCurrentNum())
					{
						tempTask.setCurrentNum(role.getRoleScienceMap().get((Integer)temp));
					}
					
				}
				role.getTasks().setChange(true);
			}
			/*
			 * 全部科技的特殊情况
			 * 全部科技等级达到 指定等级
			 * 
			 */
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_SCIENCE
					&& tempTaskInfo.getType1() == TaskConstant.ALL_SCIENCE_TYPE
					&& tempTaskInfo.getType2() == TaskConstant.TYPE_2)
			{
				int temp =(Integer)this.getMinValue(role.getRoleScienceMap());
				tempTask.setCurrentNum(temp);
				role.getTasks().setChange(true);
			}
			//任务是否完成
			if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
				tempTask.setIsComplete(TaskConstant.COMPLENT);
			}
			role.getTasks().setChange(true);
			this.updateTask(tempTask, role);
		}
	}
	
	/**
	 * 等级相关任务
	 * @author xjd
	 */
	public void checkRoleLv(Role role, int type1) {
		Tasks tasks = role.getTasks();
		Map<Integer, Task> doingMap = tasks.getDoingTaskMap();
		for(Map.Entry<Integer, Task> entry : doingMap.entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_LV
					&& tempTaskInfo.getType1() == type1)
			{
				if(tempTaskInfo.getType2() == TaskConstant.TYPE_1){		//等级达到
					tempTask.setCurrentNum(tempTask.getCurrentNum() + TaskConstant.TYPE_1);
				}
				
				//判断是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
	}
	
	/**
	 * 会谈相关任务
	 * @author xjd
	 */
	public void checkPubTalkTask(Role role, byte type1 ,byte num) {
		Tasks tasks = role.getTasks();
		Map<Integer, Task> doingMap = tasks.getDoingTaskMap();
		Map<Integer, Task> dailyMap = tasks.getDailyTaskMap();
		for(Map.Entry<Integer, Task> entry : doingMap.entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_TALK
					&& tempTaskInfo.getType1() == type1)
			{
				if(tempTaskInfo.getType2() == TaskConstant.TYPE_1){		//会谈次数
					tempTask.setCurrentNum(tempTask.getCurrentNum() + num);
				}
				//判断是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
		for(Map.Entry<Integer, Task> entry : dailyMap.entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTask.getIsComplete() == TaskConstant.AUTO_COMPLENT)
			{
				continue;
			}
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_TALK
					&& tempTaskInfo.getType1() == type1)
			{
				if(tempTaskInfo.getType2() == TaskConstant.TYPE_1){		//会谈次数
					tempTask.setCurrentNum(tempTask.getCurrentNum() + num);
				}
				//判断是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
	}

	/**
	 * 装备相关任务
	 * @author xjd 
	 */
	public void checkEquipTask(Role role, byte type1 ,int equipType) {
		Tasks tasks = role.getTasks();
		Map<Integer, Task> doingMap = tasks.getDoingTaskMap();
		for(Map.Entry<Integer, Task> entry : doingMap.entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_EQUIP
					&& tempTaskInfo.getType1() == type1)
			{
				if(tempTaskInfo.getType2() == equipType){		//穿一件装备
					tempTask.setCurrentNum(TaskConstant.TYPE_1);
				}
				//判断是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
	}

	/**
	 * 强化相关任务
	 * @author xjd
	 */
	public void checkStrengthenTask(Role role, int type1, int num) {
		Tasks tasks = role.getTasks();
		Map<Integer, Task> doingMap = tasks.getDoingTaskMap();
		Map<Integer, Task> dailyMap = tasks.getDailyTaskMap();
		for(Map.Entry<Integer, Task> entry : doingMap.entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_SSTRENGTHEN
					&& tempTaskInfo.getType1() == type1
					|| tempTaskInfo.getFunctionType() == TaskConstant.TYPE_SSTRENGTHEN
					&& tempTaskInfo.getType1() == TaskConstant.TYPE_6)
			{
				if(tempTaskInfo.getType2() == TaskConstant.TYPE_1){		//强化一级
					tempTask.setCurrentNum(tempTask.getCurrentNum() 
							+ TaskConstant.TYPE_1);
				}
				if(tempTaskInfo.getType2() == TaskConstant.TYPE_2)		//强化到几级
				{
					if(tempTask.getCurrentNum() <= num)
					{
						tempTask.setCurrentNum(num);
					}
				}
				//判断是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
		for(Map.Entry<Integer, Task> entry : dailyMap.entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTask.getIsComplete() == TaskConstant.AUTO_COMPLENT)
			{
				continue;
			}
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_SSTRENGTHEN
					&& tempTaskInfo.getType1() == type1
					|| tempTaskInfo.getFunctionType() == TaskConstant.TYPE_SSTRENGTHEN
					&& tempTaskInfo.getType1() == TaskConstant.TYPE_6)
			{
				if(tempTaskInfo.getType2() == TaskConstant.TYPE_1){		//强化一级
					tempTask.setCurrentNum(tempTask.getCurrentNum() + 
							TaskConstant.TYPE_1);
				}
				if(tempTaskInfo.getType2() == TaskConstant.TYPE_2)		//强化到几级
				{
					if(tempTask.getCurrentNum() < num)
					{
						tempTask.setCurrentNum(num);
					}
				}
				//判断是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
	}
	
	/**
	 * 阵型相关任务
	 * @author xjd
	 */
	public void checkFormationTask(Role role, byte type1) {
		Tasks tasks = role.getTasks();
		Map<Integer, Task> doingMap = tasks.getDoingTaskMap();
		for(Map.Entry<Integer, Task> entry : doingMap.entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_FORMATION
					&& tempTaskInfo.getType1() == type1)
			{
				if(tempTaskInfo.getType2() == TaskConstant.TYPE_1){		//保存一次阵型
					tempTask.setCurrentNum(TaskConstant.TYPE_1);
				}
				//判断是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
	}
	
	/**
	 * 队形相关任务(登用 离队)
	 * @author xjd
	 */
	public void checkPubTask(Role role, byte type1) {
		Tasks tasks = role.getTasks();
		Map<Integer, Task> doingMap = tasks.getDoingTaskMap();
		for(Map.Entry<Integer, Task> entry : doingMap.entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_PUB
					&& tempTaskInfo.getType1() == type1)
			{
				if(tempTaskInfo.getType2() == TaskConstant.TYPE_1){		//1 数量达到
					tempTask.setCurrentNum(role.getHeroMap().size());
				}
				//判断是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
	}
	
	/**
	 * 竞技相关任务(每日)
	 * @author xjd
	 */
	public void checkArenaTask(Role role, byte type1) {
		Tasks tasks = role.getTasks();
		Map<Integer, Task> dailMap = tasks.getDailyTaskMap();
		for(Map.Entry<Integer, Task> entry : dailMap.entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_ARENA
					&& tempTaskInfo.getType1() == type1)
			{
				if(tempTaskInfo.getType2() == TaskConstant.TYPE_1){		//1	参加一次
					tempTask.setCurrentNum(TaskConstant.TYPE_1);
				}
				//判断是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
	}
	
	/**
	 * 签到相关任务(每日)
	 * @author xjd
	 */
	public void checkSignTask(Role role) {
		Tasks tasks = role.getTasks();
		Map<Integer, Task> dailMap = tasks.getDailyTaskMap();
		for(Map.Entry<Integer, Task> entry : dailMap.entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_SIGN)
			{
				if(tempTaskInfo.getType2() == TaskConstant.TYPE_1){		//1	签到一次
					tempTask.setCurrentNum(TaskConstant.TYPE_1);
				}
				//判断是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
	}
	
	/**
	 * 军团相关任务(每日)
	 * @author xjd
	 */
	public void checkLegionTask(Role role ,byte type1) {
		Tasks tasks = role.getTasks();
		Map<Integer, Task> dailMap = tasks.getDailyTaskMap();
		for(Map.Entry<Integer, Task> entry : dailMap.entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_LEGION
					&& tempTaskInfo.getType1() == type1)
			{
				if(tempTaskInfo.getType2() == TaskConstant.TYPE_1){		//1	贡献一次
					tempTask.setCurrentNum(TaskConstant.TYPE_1);
				}
				if(tempTaskInfo.getType2() == TaskConstant.TYPE_2){		//2 加入军团
					tempTask.setCurrentNum(TaskConstant.TYPE_1);
				}
				//判断是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
	}
	
	/**
	 * 资源相关任务(每日)
	 * @author xjd
	 */
	public void checkResourceTask(Role role, int type, int value) {
		Tasks tasks = role.getTasks();
		Map<Integer, Task> dailMap = tasks.getDailyTaskMap();
		for(Map.Entry<Integer, Task> entry : dailMap.entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_RESOURCE
					&& tempTaskInfo.getType1() == type)
			{
				if(tempTaskInfo.getType2() == TaskConstant.TYPE_1){		//1	消耗
					tempTask.setCurrentNum(value);
				}
				//判断是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
	}
	
	/**
	 * 扫荡相关任务(每日)
	 * @author xjd
	 */
	public void checkRaidsTask(Role role, int cid) {
		Tasks tasks = role.getTasks();
		Map<Integer, Task> dailyMap = tasks.getDailyTaskMap();
		Map<Integer, Task> doingMap = tasks.getDoingTaskMap();
		for(Map.Entry<Integer, Task> entry : dailyMap.entrySet()){
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTask.getIsComplete() == TaskConstant.AUTO_COMPLENT)
			{
				continue;
			}
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_RAIDS 
					&& tempTaskInfo.getType1() == cid 
					||tempTaskInfo.getFunctionType() == TaskConstant.TYPE_RAIDS
					&& tempTaskInfo.getType1() == TaskConstant.TYPE_0)
			{
				if(tempTaskInfo.getType2() == 1){		//首次扫荡
					tempTask.setCurrentNum(tempTask.getCurrentNum() + 1);
				}
				if(tempTaskInfo.getType2() == 2){		//扫荡次数
					tempTask.setCurrentNum(tempTask.getCurrentNum() + 1);
				}
				//任务是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
		for(Map.Entry<Integer, Task> entry : doingMap.entrySet()){
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_RAIDS 
					&& tempTaskInfo.getType1() == cid 
					||tempTaskInfo.getFunctionType() == TaskConstant.TYPE_RAIDS
					&& tempTaskInfo.getType1() == TaskConstant.TYPE_0)
			{
				if(tempTaskInfo.getType2() == 1){		//首次扫荡
					tempTask.setCurrentNum(tempTask.getCurrentNum() + 1);
				}
				if(tempTaskInfo.getType2() == 2){		//扫荡次数
					tempTask.setCurrentNum(tempTask.getCurrentNum() + 1);
				}
				//任务是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
	}
	
	/**
	 * 玩家相关任务
	 * @author xjd
	 */
	public void checkRoleNameTask(Role role, byte type1) {
		Tasks tasks = role.getTasks();
		Map<Integer, Task> doingMap = tasks.getDoingTaskMap();
		for(Map.Entry<Integer, Task> entry : doingMap.entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_ROLE
					&& tempTaskInfo.getType1() == type1)
			{
				if(tempTaskInfo.getType2() == TaskConstant.TYPE_1){		//1设定昵称
					tempTask.setCurrentNum(TaskConstant.TYPE_1);
				}
				//判断是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
	}
	
	/**
	 * 国家相关任务
	 * @author xjd
	 */
	public void checkNationTask(Role role, byte type1) {
		Tasks tasks = role.getTasks();
		Map<Integer, Task> doingMap = tasks.getDoingTaskMap();
		for(Map.Entry<Integer, Task> entry : doingMap.entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_NATION
					&& tempTaskInfo.getType1() == type1)
			{
				if(tempTaskInfo.getType2() == TaskConstant.TYPE_1){		//1加入一个国家
					tempTask.setCurrentNum(TaskConstant.TYPE_1);
				}
				if(tempTaskInfo.getType2() == TaskConstant.TYPE_2){		//2迁入一个封地
					tempTask.setCurrentNum(TaskConstant.TYPE_1);
				}
				//判断是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
	}
	
	/**
	 * 武将等级相关 22
	 * @author xjd
	 */
	public void checkHeroLv(Role role) {
		Tasks tasks = role.getTasks();
		Map<Integer, Task> doingMap = tasks.getDoingTaskMap();
		for(Map.Entry<Integer, Task> entry : doingMap.entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_HEROLV)
			{
				if(tempTaskInfo.getType1() == TaskConstant.TYPE_1){		//一个武将等级达到
					for(Hero hero : role.getHeroMap().values())
					{
						if(hero.getLv() >= tempTaskInfo.getType2())
						{
							tempTask.setCurrentNum(tempTask.getCurrentNum() + TaskConstant.TYPE_1);
							break;
						}
					}
				}else if (tempTaskInfo.getType1() == TaskConstant.TYPE_2) { // 多个武将
					int flag = 0;
					for(Hero hero : role.getHeroMap().values())
					{
						if(hero.getLv() >= tempTaskInfo.getType2())
						{
							flag++;
						}
					}
					if(flag > tempTask.getCurrentNum())
					{
						tempTask.setCurrentNum(flag);
					}
				}
				//判断是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
		
	}
	
	/**
	 * 资源点相关任务 23
	 * @author xjd
	 */
	public void checkPvp(Role role, byte type1) {
		Tasks tasks = role.getTasks();
		Map<Integer, Task> doingMap = tasks.getDoingTaskMap();
		for(Map.Entry<Integer, Task> entry : doingMap.entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_PVP
					&& tempTaskInfo.getType1() == type1)
			{
				if(tempTaskInfo.getType2() == TaskConstant.TYPE_1){		//成功次数达到
					tempTask.setCurrentNum(TaskConstant.TYPE_1);
				}
				//判断是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
	}
	
	/**
	 * 晋升相关 24
	 * @author xjd
	 */
	public void checkRlv(Role role, int heroId) {
		Tasks tasks = role.getTasks();
		Map<Integer, Task> doingMap = tasks.getDoingTaskMap();
		for(Map.Entry<Integer, Task> entry : doingMap.entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_RLV
					&& tempTaskInfo.getType1() == heroId)
			{
				if(tempTaskInfo.getType2() == TaskConstant.TYPE_1){		//晋升一次
					tempTask.setCurrentNum(TaskConstant.TYPE_1);
				}else if (tempTaskInfo.getType2() == TaskConstant.TYPE_2) {//晋升到
					Hero x = role.getHeroMap().get(heroId);
					if(x.getRebirthLv() >= tempTask.getCurrentNum())
					{
						tempTask.setCurrentNum(x.getRebirthLv());
					}
				}
				//判断是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
	}
	
	/**
	 * 主城场景相关 25
	 * 
	 * @author xjd
	 */
	public void checkBulidLv(Role role, byte type) {
		Tasks tasks = role.getTasks();
		Map<Integer, Task> doingMap = tasks.getDoingTaskMap();
		for(Map.Entry<Integer, Task> entry : doingMap.entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_BULIDLV
					&& tempTaskInfo.getType1() == type)
			{
				if(tempTaskInfo.getType2() == TaskConstant.TYPE_1)//等级达到
				{	
					Short lv = role.getBuild().getBuildLvMap().get(type);
					if(lv==null)return;
					tempTask.setCurrentNum(lv);
				}
				//判断是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
	}
	
	
	/**
	 * 配属相关	26
	 * @author xjd
	 */
	public void checkAttach(Role role, byte type) {
		Tasks tasks = role.getTasks();
		Map<Integer, Task> doingMap = tasks.getDoingTaskMap();
		for(Map.Entry<Integer, Task> entry : doingMap.entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_ATTACH
					&& tempTaskInfo.getType1() == type)
			{
				if(tempTaskInfo.getType2() == TaskConstant.TYPE_1)//任意配属
				{	
					
					tempTask.setCurrentNum(TaskConstant.TYPE_1);
				}
				//判断是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
	}
	
	/**
	 * 建筑队列任务 21
	 * @author xjd
	 */
	public void checkQueue(Role role, byte type1) {
		Tasks tasks = role.getTasks();
		Map<Integer, Task> doingMap = tasks.getDoingTaskMap();
		for(Map.Entry<Integer, Task> entry : doingMap.entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_QUEUE
					&& tempTaskInfo.getType1() == type1)
			{
				if(tempTaskInfo.getType2() == TaskConstant.TYPE_1){		//1使用一次
					tempTask.setCurrentNum(TaskConstant.TYPE_1);
				}
				//判断是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
	}
	
	/**
	 * 寻访相关 27
	 */
	public void checkVisit(Role role, byte type1) {
		Tasks tasks = role.getTasks();
		Map<Integer, Task> doingMap = tasks.getDoingTaskMap();
		for(Map.Entry<Integer, Task> entry : doingMap.entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_VISIT
					&& tempTaskInfo.getType1() == type1)
			{
				if(tempTaskInfo.getType2() == TaskConstant.TYPE_1){		//1 寻访次数
					tempTask.setCurrentNum(tempTask.getCurrentNum() + TaskConstant.TYPE_1);
				}
				//判断是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
		
		for(Map.Entry<Integer, Task> entry : role.getTasks().getDailyTaskMap().entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_VISIT
					&& tempTaskInfo.getType1() == type1)
			{
				if(tempTaskInfo.getType2() == TaskConstant.TYPE_1){		//1 寻访次数
					tempTask.setCurrentNum(tempTask.getCurrentNum() + TaskConstant.TYPE_1);
				}
				//判断是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
		
	}
	
	
	/**
	 * 制造相关	28
	 * @author xjd
	 */
	public void checkMake(Role role, byte type1, int num) {
		Tasks tasks = role.getTasks();
		Map<Integer, Task> doingMap = tasks.getDoingTaskMap();
		for(Map.Entry<Integer, Task> entry : doingMap.entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_MAKE
					&& tempTaskInfo.getType1() == type1)
			{
				if(tempTaskInfo.getType2() == TaskConstant.TYPE_1){		//1 制造数量
					tempTask.setCurrentNum(tempTask.getCurrentNum() + num);
				}
				//判断是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
		
		for(Map.Entry<Integer, Task> entry : role.getTasks().getDailyTaskMap().entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_MAKE
					&& tempTaskInfo.getType1() == type1)
			{
				if(tempTaskInfo.getType2() == TaskConstant.TYPE_1){		//1 制造数量
					tempTask.setCurrentNum(tempTask.getCurrentNum() + num);
				}
				//判断是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
		
	}
	
	/**
	 * 酒桌相关	29
	 * @author xjd
	 */
	public void checkDesk(Role role, byte type1) {
		Tasks tasks = role.getTasks();
		Map<Integer, Task> doingMap = tasks.getDoingTaskMap();
		for(Map.Entry<Integer, Task> entry : doingMap.entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_DESK
					&& tempTaskInfo.getType1() == type1)
			{
				if(tempTaskInfo.getType2() == TaskConstant.TYPE_1){		//1 次数达到
					tempTask.setCurrentNum(tempTask.getCurrentNum() + TaskConstant.TYPE_1);
				}
				//判断是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
		
		for(Map.Entry<Integer, Task> entry : role.getTasks().getDailyTaskMap().entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_DESK
					&& tempTaskInfo.getType1() == type1)
			{
				if(tempTaskInfo.getType2() == TaskConstant.TYPE_1){		//1 次数达到
					tempTask.setCurrentNum(tempTask.getCurrentNum() + TaskConstant.TYPE_1);
				}
				//判断是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
	}
	
	
	/**
	 * Vip相关	30
	 * @author xjd
	 */
	public void checkVip(Role role) {
		for(Map.Entry<Integer, Task> entry : role.getTasks().getDailyTaskMap().entrySet())
		{
			TaskInfo tempTaskInfo = TaskInfoCache.getputTaskInfoById(entry.getKey());
			Task tempTask = entry.getValue();
			if(tempTaskInfo.getFunctionType() == TaskConstant.TYPE_VIP
					&& tempTaskInfo.getType1() == TaskConstant.TYPE_1)
			{
				if(role.getVipLv() >= TaskConstant.COMPLENT)
				{
					tempTask.setCurrentNum(TaskConstant.COMPLENT);
				}
				//判断是否完成
				if(tempTask.getCurrentNum() >= tempTaskInfo.getCompleteNum()){
					tempTask.setIsComplete(TaskConstant.COMPLENT);
				}
				role.getTasks().setChange(true);
				this.updateTask(tempTask, role);
			}
		}
	}
	
	/**
	 * 获取任务的奖励
	 * @param role		
	 * @param taskId	任务ID
	 * @author xjd
	 * 
	 */
	private String getAwardTask(Role role ,int taskId , IoSession is)
	{
		Mail mail = new Mail();
		StringBuilder sb = new StringBuilder();
		StringBuilder sbMail = new StringBuilder();
		TaskInfo info = TaskInfoCache.getputTaskInfoById(taskId);
		if(info == null) return null;
		//取出奖励信息
		String str = info.getAwardStr();
		//拆分奖励
		String tempAward[] = str.split(";");
		//循环个奖励 并且处理相应逻辑
		for(int x = 0 ; x < tempAward.length; x++)
		{
			String[] award = tempAward[x].split(",");
			//处理业务逻辑
			switch (award[0]) {
			case TaskConstant.REWARD_TYPE_EXP:
				roleService.addRoleExp(role, Integer.parseInt(award[2]));
				//拼接奖励字符串
				sb.append(award[0]).append(",").append(TaskConstant.TYPE_0).
				append(",").append(award[2]).append(";");
				break;
			case TaskConstant.REWARD_TYPE_MONEY:
				roleService.addRoleMoney(role, Integer.parseInt(award[2]));
				//拼接奖励字符串
				sb.append(award[0]).append(",").append(TaskConstant.TYPE_0).
				append(",").append(award[2]).append(";");
				break;
			case TaskConstant.REWARD_TYPE_GOLD:
				roleService.addRoleGold(role, Integer.parseInt(award[2]));
				//拼接奖励字符串
				sb.append(award[0]).append(",").append(TaskConstant.TYPE_0).
				append(",").append(award[2]).append(";");
				break;
			case TaskConstant.REWARD_TYPE_EXPLOIT:
				roleService.addRoleExploit(role, Integer.parseInt(award[2]));
				//拼接奖励字符串
				sb.append(award[0]).append(",").append(TaskConstant.TYPE_0).
				append(",").append(award[2]).append(";");
				break;
			case TaskConstant.REWARD_TYPE_FOOD:
				roleService.addRoleFood(role, Integer.parseInt(award[2]));
				//拼接奖励字符串
				sb.append(award[0]).append(",").append(TaskConstant.TYPE_0).
				append(",").append(award[2]).append(";");
				break;
			case TaskConstant.REWARD_TYPE_PUPLATION:
				roleService.addRolePopulation(role,Integer.parseInt(award[2]));
				sb.append(award[0]).append(",").append(TaskConstant.TYPE_0).
				append(",").append(award[2]).append(";");
				break;
			case TaskConstant.REWARD_TYPE_ARMYTOKEN:
				roleService.getArmyToken(role, Integer.parseInt(award[2]));
				sb.append(award[0]).append(",").append(TaskConstant.TYPE_0).
				append(",").append(award[2]).append(";");
				break;
			case TaskConstant.REWARD_TYPE_PRISAGE:
				roleService.addRolePrestige(role, Integer.parseInt(award[2]));
				sb.append(award[0]).append(",").append(TaskConstant.TYPE_0).
				append(",").append(award[2]).append(";");
				break;
			case TaskConstant.REWARD_TYPE_ITEM:
//				if(propService.checkItemInBackPack(role, Integer.parseInt(award[1])) || 
//					propService.getEmptyBackPack(role) >= 1){
					propService.addProp(role, Integer.parseInt(award[1]), (byte)2, Integer.parseInt(award[2]), is);
//				}else {
//					sbMail.append(award[1]).append(",").append(award[0]).append(",").append(award[2]).append(";");
//				}
				break;
			case TaskConstant.REWARD_TYPE_EQUIP:
//				if(propService.getEmptyBackPack(role) >= Integer.parseInt(award[2])){
					for (int i = 0; i < Integer.parseInt(award[2]); i++) {
						propService.addProp(role, Integer.parseInt(award[1]), (byte)1, 1, is);
					}
//				}else {
//					for(int z = 0 ; z < Integer.parseInt(award[2]) ; z++)
//					{
//						sbMail.append(award[1]).append(",").append(award[0]).append(",").append(award[2]).append(";");
//					}
//				}
				break;
			case TaskConstant.REWARD_TYPE_HERO:
				HeroConfig config = HeroConfigCache.getHeroConfigById(Integer.parseInt(award[1]));
				if(config == null) break;
				if(role.getHeroMap().containsKey(config.getHeroId()))
				{
					this.heroService.addHeroEmotion(role, Integer.parseInt(award[1]), 
							Integer.parseInt(award[2]));
					break;
				}  
				int heroId = Integer.parseInt(award[1]);
				Hero hero = heroService.createHero(role, heroId);
				heroService.addHero(is, hero);
				break;
			case TaskConstant.REWARD_TYPE_DAILY:
				role.getTasks().setDailyTaskComplete(role.getTasks().getDailyTaskComplete() + Integer.parseInt(award[2]));
				sb.append(award[0]).append(",").append(TaskConstant.TYPE_0).
				append(",").append(award[2]).append(";");
				break;
			default:
				break;
			}
		}
		return sb.toString();
//		String tempStr = sbMail.toString();
//		if(tempStr == null || tempStr.equals(""))
//		{
//			
//		}
//		else {
//			mail.setTitle(TaskConstant.MAIL_TASK_TITLE);
//			mail.setContext(TaskConstant.MAIL_TASK_CONTEXT);
//			mail.setAttached(tempStr);
//			mailService.sendSYSMail(role, mail);
//			return sb.toString();
//		}
		
	}

	/**
	 * 获取下一阶段的任务
	 * @param role
	 * @param taskId
	 * @author xjd
	 */
	private void getNextTask(Role role , int taskId)
	{
		TaskInfo info = TaskInfoCache.getputTaskInfoById(taskId);
		
		if(info == null) return ;
		//检查本任务是否有后续任务
		int[] arr = info.getNextTaskIdArr();
		if(arr.length <= 1 && arr[0] == 0)
		{
			return;//没有后续
		}
		//循环数组将任务加入doingTaskMap中
		for(int x : arr)
		{
			Task tempTask = new Task();
			tempTask.setTaskId(x);
			role.getTasks().getDoingTaskMap().put(tempTask.getTaskId(), tempTask);
			//更新新任务的完成情况
			updateNewTaskCurrentNum(tempTask,role);
			this.getNewTask(tempTask, role);
		}
		role.getTasks().setChange(true);
	}

	/**
	 * 更新获取的新任务的进度
	 * @param newTask
	 * @param role
	 * @return 返回消息
	 */
	private void updateNewTaskCurrentNum(Task newTask, Role role) {
		if (newTask == null)
			return;
		int taskId = newTask.getTaskId();
		TaskInfo info = TaskInfoCache.getputTaskInfoById(taskId);
		int functionType = info.getFunctionType();

		if (functionType == TaskConstant.TYPE_BUILD) {
			HashMap<Integer, Collection<Integer>> buildLevelMap = new HashMap<Integer, Collection<Integer>>();
			buildLevelMap.put(TaskConstant.TYPE_HOUSE, role.getBuild()
					.getHouseMap().values());
			buildLevelMap.put(TaskConstant.TYPE_FARM, role.getBuild()
					.getFarmLvMap().values());
			if (info.getType2() == TaskConstant.TYPE_2) {
//				buildLevelMap.put(TaskConstant.TYPE_BARRACK, role.getBuild()
//						.getBarrackLvMap().values());

				for (Integer level : buildLevelMap.get(info.getType1())) {
					int currentNum = newTask.getCurrentNum();
					if (level > currentNum) {
						newTask.setCurrentNum(level);
					}
				}
			}
			if(info.getType2() == TaskConstant.TYPE_3)
			{
				int num = 0;
				for(int x : buildLevelMap.get(info.getType1()))
				{
					if(x > 0)
					{
						num++;
					}
				}
				newTask.setCurrentNum(num);
			}
		} else if (functionType == TaskConstant.TYPE_RECRUIT) {
			int heroCount = role.getHeroMap().size();
			newTask.setCurrentNum(heroCount);
			
		} else if (functionType == TaskConstant.TYPE_INCOME) {
			//每小时总生产值超过(民居)
			if(info.getType2() == 1 && info.getType1() == TaskConstant.TYPE_HOUSE){	
				int houseHourIncome = inComeService.getHourIncomeValue(role, InComeConstant.TYPE_HOUSE);
				newTask.setCurrentNum(houseHourIncome);
			}
			//每小时生产总值超过(农田)
			if(info.getType2() == 1 && info.getType1() == TaskConstant.TYPE_FARM){	
				int farmHourIncome = inComeService.getHourIncomeValue(role, InComeConstant.TYPE_FARM);
				newTask.setCurrentNum(farmHourIncome);
			}
		}else if (functionType == TaskConstant.TYPE_SCIENCE) {
			int level =role.getRoleScienceMap().get(info.getType1());
			newTask.setCurrentNum(level);
		} else if (functionType == TaskConstant.TYPE_SSTRENGTHEN) {
			int type1 = info.getType1();
			int type2 = info.getType2();
			if (type1 == TaskConstant.TYPE_1) {
				if (type2 == TaskConstant.TYPE_2) {
					// 将一件武器强化到5级
					for (Prop prop : role.getPropMap().values()) {
						int propLevel = prop.getLv();
						int slotId = prop.getSlotId();

						if (slotId == TaskConstant.TYPE_WEAPON
								&& propLevel > newTask.getCurrentNum())
							newTask.setCurrentNum(prop.getLv());
					}
				}

			} else if (type1 == TaskConstant.TYPE_6) {
				if (type2 == TaskConstant.TYPE_2) {
					// 强化任意一件装备到X级
					for (Prop prop : role.getPropMap().values()) {
						int propLevel = prop.getLv();
						if (propLevel > newTask.getCurrentNum())
							newTask.setCurrentNum(prop.getLv());
					}
				}
			}
		} else if (info.getFunctionType() == TaskConstant.TYPE_HEROLV) {// 武将等级任务
			for (Hero hero : role.getHeroMap().values()) {
				if (hero.getLv() >= info.getType2()) {
					newTask.setCurrentNum(newTask.getCurrentNum() + 1);
				}
			}
		} else if (info.getFunctionType() == TaskConstant.TYPE_LV) {
			newTask.setCurrentNum(role.getLv());
		} else if (info.getFunctionType() == TaskConstant.TYPE_RLV) {
			if(info.getType2() == TaskConstant.TYPE_2
					&& role.getHeroMap().get(info.getType1()) != null)
			{
				newTask.setCurrentNum(role.getHeroMap().get(info.getType1()).getRebirthLv());
			}
		} else if (info.getFunctionType() == TaskConstant.TYPE_CHAPTER
				&& info.getType1() != TaskConstant.TYPE_0) {
			if(role.getChapter().getStarMap().containsKey(info.getType1()))
			{
				newTask.setCurrentNum(TaskConstant.TYPE_1);
			}
		} else if (info.getFunctionType() == TaskConstant.TYPE_BULIDLV) {
			Short lv = role.getBuild().getBuildLvMap().get((byte)info.getType1());
			if(lv == null) lv = 0;
			newTask.setCurrentNum(lv);
		}
		
		if(newTask.getCurrentNum() >=info.getCompleteNum()){
			newTask.setIsComplete(TaskConstant.COMPLENT);
		}	
	}

	/** 
     * 求Map<K,V>中Value的最小值 
     * @param map 
     * @return 
     * @author xjd
     */ 
    private Object getMinValue(Map<Integer, Integer> map) { 
        if (map == null) return null; 
        Collection<Integer> c = map.values(); 
        Object[] obj = c.toArray(); 
        Arrays.sort(obj); 
        return obj[0]; 
    }

    /***
     * 自动完成每日任务
     * @param task
     * @param role
     * @author xjd
     */
    private void atuoCompletTask(Task task , Role role)
    {
    	int taskId = task.getTaskId();
    	IoSession is = SessionCache.getSessionById(role.getId());
    	byte flag = task.getIsComplete();
		if( flag == TaskConstant.AUTO_COMPLENT || flag == TaskConstant.NOT_COMPLENT){	//任务未完成或者已经领取过
			return ;
		}
		//提取任务奖励
		String str = this.getAwardTask(role, taskId, is);
		//删除已完成的任务(改变状态) 弃用删除模式
		task.setIsComplete(TaskConstant.AUTO_COMPLENT);
		
		role.getTasks().setChange(true);
		role.setChange(true);
		//记录日志
		String logStr = Utils.getLogString(role.getId(), taskId, str);
		taskLog.info(logStr);
    }


	
    /**
//     * 每日任务奖励处理方法
//     * @param role
//     * @param getMoney
//     * @param getFood
//     * @param getExploit
//     * @param getArmyToken
//     * @author xjd
//     */
//    private void getAwardTypeA(Role role , int getMoney)
//    {
//    	roleService.addRoleMoney(role, getMoney);
//    }
//    private void getAwardTypeB(Role role , int getMoney)
//    {
//    	roleService.addRoleMoney(role, getMoney);
//    }
//    private void getAwardTypeC(Role role , int getMoney , int getFood , int getExploit)
//    {
//    	roleService.addRoleMoney(role, getMoney);
//    	roleService.addRoleFood(role, getFood);
//    	roleService.addRoleExploit(role, getExploit);
//    }
//    private void getAwardTypeD(Role role , int getMoney , int getFood , int getExploit , int getArmyToken)
//    {
//    	roleService.addRoleMoney(role, getMoney);
//    	roleService.addRoleFood(role, getFood);
//    	roleService.addRoleExploit(role, getExploit);
//    	roleService.getArmyToken(role, getArmyToken);
//    }
}
