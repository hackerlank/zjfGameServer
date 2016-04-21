package byCodeGame.game.moudle.hero.service;


import org.apache.mina.core.session.IoSession;

import byCodeGame.game.entity.bo.Hero;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.file.HeroConfig;
import byCodeGame.game.entity.po.HeroSoldier;
import byCodeGame.game.remote.Message;

public interface HeroService {

	/**
	 * 角色登陆英雄数据处理
	 * 
	 * @param role
	 */
	public void heroDataLoginHandle(Role role);

	/**
	 * 获取武将列表
	 * 
	 * @param role
	 * @return
	 */
	public Message getHeroList(Role role);

	/**
	 * 获取武将兵种信息
	 * 
	 * @param role
	 * @param hero
	 * @return
	 * @author xjd
	 */
	public Message getHeroArmyInfo(Hero hero);

	/**
	 * 获取武将转生信息
	 * 
	 * @param hero
	 * @return
	 * @author xjd
	 */
	public Message getRebLvInfo(Role role, Hero hero);

	/**
	 * 突飞武将
	 * 
	 * @param role
	 * @param heroId
	 * @param type
	 * @return
	 */
	public Message quickTrainHero(Role role, int heroId, int propId);

	/**
	 * 计算武将当前等级
	 * 
	 * @param hero
	 */
	public void countHeroLv(Hero hero, Role role);

	/**
	 * 
	 * @param role
	 * @param formationId
	 * @return
	 */
	public Message updateUseFormationID(Role role, int formationId);

	/**
	 * 通知客户端增加武将
	 * 
	 * @return
	 */
	public void addHero(IoSession is, Hero hero);

	/**
	 * 保存阵型
	 * 
	 * @param role
	 * @param fId
	 * @param dataMap
	 * @return
	 */
	// public Message saveFormation(Role role,byte fId,Map<Byte, Integer>
	// dataMap);
	public Message saveFormation(Role role, int useFormationID, byte key, int heroId);

	/**
	 * 检查阵型是否合法
	 * @param role
	 * @param useFormationID
	 * @param key
	 * @param heroId
	 * @return
	 */
	public boolean getLocation(Role role, int useFormationID, byte key, int heroId);
	
	/**
	 * 获取阵型数据
	 * 
	 * @param role
	 * @param id 阵型id
	 * @return
	 */
	public Message getFormationData(Role role, int id);

	/**
	 * 获取所有阵型数据
	 * 
	 * @param role
	 * @return
	 */
	public Message getFormationDataAll(Role role);

	/**
	 * 进阶武将
	 * 
	 * @param role
	 * @return
	 */
	public Message upHeroRebirth(Role role, int heroId);

	/**
	 * 武将配置兵力
	 * 
	 * @param role
	 * @param armsMap
	 * @return
	 */
	public Message deployArmsForHero(Role role, int heroId, int armsId);

	/**
	 * 增加兵力
	 * 
	 * @param role
	 * @param armsNum 增加兵力数量
	 * @return
	 */
	public Message addArmsNum(Role role, int armsNum);

	/**
	 * 处理武将转生收益 技能
	 * 
	 * @param hero
	 * @param config
	 */
	public void updataHeroR(Hero hero, HeroConfig config);

	/**
	 * 升级技能
	 * 
	 * @param role
	 * @param heroId
	 * @param skillType
	 * @return
	 */
	public Message updateSkill(Role role, int heroId, int skillType);

	/**
	 * 增加英雄的情感值
	 * 
	 * @param role
	 * @param heroId
	 * @param emotionValue
	 * @return
	 * @author wcy
	 */
	public void addHeroEmotion(Role role, int heroId, int emotionValue);

	/***
	 * 增加英雄经验
	 * 
	 * @param xjd
	 */
	public String addHeroExp(Role role, int exp);

	/**
	 * 创建英雄
	 * 
	 * @param role
	 * @param heroId
	 * @param hero
	 * @author wcy
	 * @return
	 */
	public Hero createHero(Role role, int heroId);

	/**
	 * 创建英雄但不加入数据
	 * 
	 * @param role
	 * @param heroId
	 * @param hero
	 * @return
	 * @author wcy
	 */
	public Hero createHeroNotInsertDao(Role role, int heroId);

	/**
	 * 初始化武将面板 <br/>
	 * 
	 * @param hero
	 * @return
	 * @author xjd
	 */
	public void initHeroFightValue(Role role, Hero hero);

	/**
	 * 加入英雄兵种
	 * 
	 * @param hero
	 * @param soldierId
	 * @author wcy
	 */
	public HeroSoldier createHeroSoldier(Role role, Hero hero, int soldierId);

	/**
	 * 
	 * @param hero
	 * @return
	 * @author wcy
	 * @param role
	 */
	public Message getAllHeroArmsInfo(Role role, Hero hero);

	/**
	 * 重修
	 * 
	 * @param hero
	 * @return
	 * @author wcy
	 */
	public Message retrainArmy(Role role, int heroId);

	/**
	 * 显示转职信息
	 * 
	 * @param role
	 * @param heroId
	 * @return
	 * @author wcy
	 */
	public Message showDuty(Role role, int heroId);

	/**
	 * 武将转职
	 * 
	 * @param role
	 * @param hero
	 * @return
	 * @author wcy
	 */
	public Message upDutyLv(Role role, int heroId);

	/**
	 * 武将升星
	 * 
	 * @param role
	 * @param hero
	 * @return
	 * @author wcy
	 */
	public Message upRankLv(Role role, int heroId);

	/**
	 * 替换兵种
	 * 
	 * @param role
	 * @param hero
	 * @return
	 * @author wcy
	 */
	public Message insteadArmy(Role role, int heroId);

	/**
	 * 显示升星
	 * 
	 * @param role
	 * @param heroId
	 * @return
	 * @author wcy
	 */
	public Message showRank(Role role, int heroId);

	/**
	 * 显示详细英雄界面
	 * 
	 * @param role
	 * @param heroId
	 * @return
	 * @author wcy
	 */
	public Message showHeroDetail(Role role, int heroId);

	/**
	 * 显示重修
	 * 
	 * @param role
	 * @return
	 * @author wcy
	 */
	public Message showRetrain(Role role);

	/**
	 * 将魂合成武将
	 * 
	 * @param heroId
	 * @return
	 * @author wcy
	 */
	public Message compoundHero(Role role, int heroId, IoSession is);

	/**
	 * 初始化英雄服务
	 * 
	 * @author wcy
	 */
	public void initHeroService();
	
	/**
	 * 
	 * @param role
	 * @param hero
	 * @author wcy
	 */
	public Message showBuyManual(Role role,int heroId);
	
	/**
	 * 
	 * @param role
	 * @param hero
	 * @author wcy
	 */
	public Message buyManual(Role role,int heroId);

}
