package byCodeGame.game.moudle.legion.service;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.entity.bo.Legion;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;

public interface LegionService {

	/**
	 * 创建军团
	 * @param role
	 * @param name		军团姓名
	 * @param faceId	头像id
	 * @return
	 */
	public Message creatLegion(Role role , String name ,String shortName);
	
	/**
	 * 获取所有军团名称
	 */
	public void getAllLegionName();
	
	/**
	 * 根据军团ID获取军团类
	 * @param legionId
	 * @return
	 */
	public Legion getLegionById(int legionId);
	
	/**
	 * 获取军团信息
	 * @return
	 */
	public Message getLegionData(Role role);
	
	/**
	 * 获取所有军团缓存 服务器启动时调用
	 */
	public void getAllLegion();
	
	/**
	 * 获取公会列表
	 * @return
	 */
	public Message getAllLegionList(Role role);
	
	/**
	 * 申请加入军团
	 * @param role
	 * @param legionId
	 * @return
	 */
	public Message applyJoinLegion(Role role , int legionId);
	
	/**
	 * 获取军团申请成员列表
	 * @return
	 */
	public Message getApplyList(Role role);
	
	/**
	 * 同意玩家加入军团
	 * @param roleId
	 * @return
	 */
	public Message agreeJoinLegion(Role role , int targetId);
	
	/**
	 * 同意所以玩家加入
	 * @param role
	 * @return
	 */
	public Message agreeAllRoleJoin(Role role);
	
	/**
	 * 副团的任命
	 * @param targetId	目标ID
	 * @return
	 * @author xjd
	 */
	public Message setDeputy(Role role , Legion legion , int targetId);
	/**
	 * 撤销副团长
	 * @param targetId
	 * @return
	 * @author xjd
	 */
	public Message cancelDeputy(Role role , Legion legion , Integer targetId);
	
	/**
	 * 踢出成员
	 * @param role
	 * @param legion
	 * @param targetId
	 * @return
	 */
	public Message kickMember(Role role , Legion legion ,Integer targetId);
	
	/**
	 * 转让会长
	 * @param role
	 * @param legion
	 * @param targetId
	 * @return
	 * @author xjd
	 */
	public Message changCaptain(Role role , Legion legion ,Integer targetId);
	
	/**
	 * 修改公会图标
	 * @param role
	 * @param legion
	 * @param faceId 要更换的id
	 * @return
	 * @author xjd
	 */
	public Message changFaceId(Role role , Legion legion , int faceId);
	
	/**
	 * 修改军团公告
	 * @param role
	 * @param legion
	 * @param notice
	 * @return
	 * @author xjd
	 */
	public Message changNotice(Role role , Legion legion , String notice);

	/**
	 * 获取军团成员列表
	 * @param role
	 * @param legion
	 * @return
	 * @author xjd
	 */
	public Message	getAllMember(Role role , Legion legion);

	/**
	 * 退出公会
	 * @param role
	 * @param legion
	 * @return
	 * @author xjd
	 */
	public Message quit(Role role ,Legion legion);
	/**
	 * 搜索公会ID
	 * @param id
	 * @return
	 * @author xjd
	 */
	public Message search(Integer id);
	/**
	 * 升级军团科技
	 * @param role
	 * @param legion
	 * @param typeM	金银币类型
	 * @return
	 * @author xjd
	 */
	public Message upgradeScience(Role role , Legion legion ,byte typeM , int useGold);

	/**
	 * 设定升级军团科技的类型
	 * @param role
	 * @param legion
	 * @param type
	 * @return
	 * @author xjd
	 */
	public Message setAppointScience(Role role,Legion legion,byte type);
	/**
	 * 设定是否开启自动接受申请
	 * @param legion
	 * @param type	0关闭 1开启
	 * @param minLv 最小接受等级
	 * @author xjd
	 * @return
	 */
	public Message setAutoAgreeJoin(Role role , Legion legion , byte type ,int minLv);

	/**
	 * 军团兑换
	 * @param role
	 * @param legion
	 * @param itemId 道具编号（武将编号）
	 * @param type 1装备 2道具 3武将
	 * @return
	 * @author xjd
	 */
	public Message exchangeLegion(Role role , Legion legion ,int itemId ,byte type ,IoSession is);

	/**
	 * 获取兑换信息
	 * @param role
	 * @param legion
	 * @param heroid
	 * @param type
	 * @return
	 */
	public Message getExchangeInfo(Role role,Legion legion ,byte type);
	
	/**
	 * 拒绝玩家加入
	 * @param role
	 * @return
	 * @author xjd
	 * 
	 */
	public Message rejectJoin(Role role , int targetId);

	/**
	 * 拒绝所有玩家申请
	 * @param role
	 * @return
	 * @author xjd
	 */
	public Message rejectAllJoin(Role role);
	
	/**
	 * 撤销军团申请
	 * @param role
	 * @param legionId
	 * @return
	 * @author xjd
	 */
	public Message cancelApply(Role role ,int legionId);
	
	/**
	 * 增加军团最大人数
	 * @param role
	 * @return
	 * @author xjd
	 */
	public Message addMaxPeopleNum(Role role);
	
	/**
	 * 设定世界大战集结旗
	 * @param role
	 * @return
	 */
	public Message setTarget(Role role ,int cityId);
	
	/**
	 * 修改旗号
	 * @param role
	 * @param shortName
	 * @return
	 */
	public Message changeShortName(Role role , String shortName);
	
}
