package byCodeGame.game.moudle.fight.service;

import java.util.Set;


import cn.bycode.game.battle.data.ResultData;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.file.ChapterConfig;
import byCodeGame.game.entity.po.ChapterReward;
import byCodeGame.game.remote.Message;

public interface FightService {

	
	/**
	 * 请求攻打关卡
	 * @param role
	 * @param hero
	 * @param cid
	 * @return
	 */
	public Message requestAttackChapter(Role role,int cid);
	
	/**
	 * 关卡随机奖励
	 * @param role
	 * @param chapterConfig
	 * @return 
	 */
	public Set<ChapterReward> randomChapterReward(Role role,ChapterConfig chapterConfig);
	
	/**
	 * 获取当日关卡通过的次数
	 * @param role
	 * @param cid
	 * @return
	 */
	public byte getChapterTimes(Role role,int cid);
	
	/**
	 * 返回具体星数
	 * @return
	 */
	public void returnStar(ResultData res,int type);
	
	/**
	 * 玩家通关后增加次数
	 * @param role
	 * @param cid
	 * @param times
	 */
	public void addChapterTimes(Role role,int cid,byte times);
	
	/**
	 * 初始化关卡配置
	 */
	public void initChapterArmyConf();
	
	/**
	 * 初始化 兵种克制
	 * @author xjd
	 */
	public void initTroopCounter();
	/**
	 * 根据传入的uuid值获取战斗信息
	 * @param uuid
	 * @return ResultData
	 */
	public ResultData getResultDataByUUID(String uuid);
	
	public ResultData getDBResultDataByUUID(String uuid);
	
	/**
	 * 根据UUID获取战斗回放
	 * @param uuid
	 * @return
	 */
	public Message getReportByUUID(String uuid);
}
