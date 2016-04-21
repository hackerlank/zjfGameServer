package byCodeGame.game.moudle.chapter.service;

import java.util.Set;

import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.po.ChapterReward;
import byCodeGame.game.remote.Message;

public interface ChapterService {

	/**
	 * 获取关卡信息
	 * @param role
	 * @param cid
	 * @return
	 */
	public Message getChapterData(Role role,int cid);
	
	/**
	 * 获取全部通过的关卡数据
	 * @param role
	 * @return
	 */
	public Message getAllChapterData(Role role);
	
	/**
	 * 获取玩家关卡星数数据
	 * @param role
	 * @return
	 */
	public Message getAllStarsData(Role role);
	
	/**
	 * 获取成就奖励信息
	 * @param role
	 * @return
	 */
	public Message getChapterAwardData(Role role ,int chapterId);
	
	/**
	 * 获取单一章节关卡信息
	 * @param role
	 * @param part
	 * @return
	 * @author xjd
	 */
	public Message getPartChapterData(Role role , int part);
	
	/**
	 * 扫荡关卡
	 * @param role
	 * @param cid	关卡ID
	 * @return
	 */
	public Message raidsChapter(Role role,int cid , int num);
	
	/**
	 * 重置关卡战斗次数
	 * @param role
	 * @param cid
	 * @return
	 */
	public Message refreshChapterTimes(Role role,int cid);
	
	/***
	 * 获取章节奖励
	 * @param role
	 * @param chapterId
	 * @param proess
	 * @return
	 */
	public Message getChapterStarsAward(Role role , int chapterId , int process);
	
	/***
	 * 处理奖励
	 * @param role
	 * @param set
	 */
	public void getAward(Role role , Set<ChapterReward> set);
	
	/**
	 * 初始化配置表
	 * 
	 * @author wcy 2016年4月15日
	 */
	public void initChapterConfig();
	
	
}
