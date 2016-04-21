package byCodeGame.game.moudle.task.service;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.po.Task;
import byCodeGame.game.remote.Message;

public interface TaskService {
	
	/**
	 * 获取任务信息
	 * @param role
	 * @return
	 */
	public Message getDoningTask(Role role);
	
	/**
	 * 完成任务 领取任务奖励
	 * @param role
	 * @param taskId
	 * @return
	 */
	public Message completeTask(Role role , Integer taskId ,IoSession is);

	/**
	 * 获取新的人任务
	 * @param role
	 * @param is
	 * @return
	 * @author xjd
	 */
	public void getNewTask(Task task , Role role);
	
	/**
	 * 更新任务进度 (服务器主动推送)
	 * @param task
	 * @param role
	 * @author xjd
	 */
	public void updateTask(Task task , Role role);
	
	/**
	 * 玩家升级时候调用
	 * 用于检查是否有新的任务被解锁
	 * 
	 * @param role
	 * @author xjd
	 * 
	 */
	public void lvUpTaskCheck(Role role);
	
	/**
	 * 领取每日任务奖励
	 * @param role
	 * @param id 奖励编号
	 * @return
	 * @author xjd
	 * 
	 */
	public Message getDailyTaskAward(Role role , int id);
	
	/***
	 * 一键完成每日任务
	 * @param role
	 * @return
	 * @author xjd
	 * 
	 */
	public Message quickCompleteDailyTask(Role role);
	
	/**
	 * 获取每日任务信息
	 * @param role
	 * @return
	 * @author xjd
	 */
	public Message getDailyTask(Role role);
	
	/**
	 * 重置&&初始化每日任务列表
	 * @param role
	 * @author xjd
	 */
	public void initDailyTask(Role role);
	
	/**
	 * 查询建筑任务,本方法仅用新建建筑时使用 1
	 * @param role
	 * @param type1
	 * @param buildLv	当前建筑等级
	 * @author xjd
	 */
	public void checkBuildUpTask(Role role , byte type1 , byte type2 ,byte buildLv);
	/**
	 * 重载方法，用于建筑升级时判断是否有任务 1
	 * @param role
	 * @param type1
	 * @param buildLv
	 * @author xjd
	 */
	public void checkBuildUpTask(Role role , byte type1 ,byte buildLv);
	
	/**
	 * 查询征收任务 3
	 * @param role
	 * @param type1 资源建筑类型
	 * @param type2 1表示征收产量，2表示已经征收的检查
	 */
	public void checkInComeTask(Role role , byte type1 ,byte type2);
	
	/**
	 * 查询招募任务的进度 2
	 * @param role 
	 * @param type1 	兵种还是武将
	 * @param num   	数量
	 * @author xjd
	 */
	public void checkRecruitTask(Role role , byte type1 , int num);
	
	/**
	 * 邮件相关任务 9
	 * @param role
	 * @param type1
	 * @author xjd
	 */
	public void checkMailTask(Role role , byte type1);
	
	/**
	 * 关卡任务 4
	 * @param role
	 * @param type1  关卡编号
	 * @param num
	 * @author xjd
	 */
	public void checkChapterTask(Role role , int type1 );

	/**
	 * 扩充相关 8
	 * @param role
	 * @param type1 1武将栏拓展
	 * @author xjd
	 */
	public void checkExpandHeroTask(Role role , byte type1);
	
	/**
	 * 配置兵种相关 7
	 * @param role
	 * @param type1 1配置兵种并且保存一次
	 * @author xjd
	 */
	public void checkDeployArmsTask(Role role , int armsId);

	/**
	 * 训练相关 6
	 * @param role
	 * @param type1 1武将突飞
	 * @author xjd
	 */
	public void checkTrainTask(Role role ,int heroId ,byte type);

	/**
	 * 科技相关任务 5
	 * @param role
	 * @param type1  0全部科技(其他为配置表科技编号)
	 * @author xjd
	 */
	public void checkScienceTask(Role role , int type1);

	/**
	 * 等级相关任务 10
	 * @param role
	 * @param type1 1提升一级
	 * @author xjd
	 */
	public void checkRoleLv(Role role , int type1);
	
	/**
	 * 会谈相关 11
	 * @param role
	 * @param type1 1银币  2金币
	 */
	public void checkPubTalkTask(Role role , byte type1 ,byte num);
	
	/**
	 * 装备相关任务 12
	 * @param role
	 * @param type1 穿戴装备
	 */
	public void checkEquipTask(Role role , byte type1 ,int equipType);
	
	/**
	 * 强化相关任务 13
	 * @param role
	 * @param type1 装备类型 1头 2衣 3腰 4 鞋 5武
	 * @param num
	 * @author xjd
	 */
	public void checkStrengthenTask(Role role , int type1 , int num);
	
	/**
	 * 阵型相关任务 14
	 * 
	 * @param role
	 * @param type1 阵型编号
	 * @return
	 * @author xjd
	 */
	public void checkFormationTask(Role role , byte type1 );
	
	/**
	 * 队形相关任务  15 
	 * @param role
	 * @param type1 1武将登用 2武将离队
	 * @author xjd
	 */
	public void checkPubTask(Role role , byte type1);
	
	/***
	 * 竞技相关任务  16
	 * @param role
	 * @param type1
	 * @author xjd
	 */
	public void checkArenaTask(Role role , byte type1);
	
	/**
	 * 签到相关任务  17
	 * @param role
	 * @author xjd
	 */
	public void checkSignTask(Role role);
	
	/**
	 * 军团相关任务  18
	 * @param role
	 * @author xjd
	 */
	public void checkLegionTask(Role role ,byte type1);
	
	/***
	 * 资源相关任务  19
	 * @param role
	 * @param type
	 * @param value 
	 * @author xjd
	 */
	public void checkResourceTask(Role role ,int type , int value);
	
	/***
	 * 扫荡相关任务  20
	 * @param role
	 * @param cid 关卡编号
	 * @author xjd
	 */
	public void checkRaidsTask(Role role , int cid);
	/**
	 * 玩家相关  98
	 * @param role
	 * @param type1 设定玩家名字
	 * @author xjd
	 */
	public void checkRoleNameTask(Role role , byte type1);
	
	/**
	 * 国家相关任务  99
	 * @param role
	 * @param type1 选择国家
	 * @author xjd
	 */
	public void checkNationTask(Role role , byte type1);

	/**
	 * 建筑队列任务 21
	 * @param role
	 * @param type1
	 * @author xjd
	 */
	public void checkQueue(Role role , byte type1);

	/***
	 * 武将等级相关 22
	 * @param role
	 */
	public void checkHeroLv(Role role);	
	/**
	 * 资源相关 攻打 征服 资源点 23
	 * @param role
	 * @param type1
	 * @author xjd
	 */
	public void checkPvp(Role role , byte type1);
	
	/**
	 * 晋升相关 24
	 */
	public void checkRlv(Role role , int heroId);

	/**
	 * 主城场景相关	25
	 * @param role
	 * @param type
	 * @author xjd
	 */
	public void checkBulidLv(Role role ,byte type);
	
	/**
	 * 配属相关		26
	 * @param role
	 * @param type
	 * @author xjd
	 */
	public void checkAttach(Role role , byte type);
	
	/**
	 * 寻访相关		27
	 * @param role
	 * @param type
	 * @author xjd
	 */
	public void checkVisit(Role role ,byte type);
	
	/**
	 * 制造相关		28
	 * @param role
	 * @param type1
	 * @param num
	 * @author xjd
	 */
	public void checkMake(Role role , byte type1 ,int num);
	
	/**
	 * 酒桌相关		29
	 * @param role
	 * @param type1
	 * @author xjd
	 */
	
	public void checkDesk(Role role, byte type1);
	
	/**
	 * Vip相关		30
	 * @param role
	 */
	public void checkVip(Role role);
}
