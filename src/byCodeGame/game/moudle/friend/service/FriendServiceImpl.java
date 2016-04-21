package byCodeGame.game.moudle.friend.service;

import java.util.Set;

import byCodeGame.game.cache.local.SessionCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.entity.bo.Friend;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.friend.FriendConstant;
import byCodeGame.game.moudle.role.service.RoleService;
import byCodeGame.game.remote.Message;

public class FriendServiceImpl implements FriendService{
	private RoleService roleService ;
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	/**
	 * 获取玩家的好友信息
	 * @author xjd
	 */
	public Message getFriendinfo(Role role) {
		Message message = new Message();
		message.setType(FriendConstant.GET_INFO_FRIEND);
		//取出玩家好友信息
		Friend friend = role.getFriend();
		//好友数量
		message.put((byte)friend.getFriendListSet().size());
		for(Integer x : friend.getFriendListSet())
		{
			Role tempRole = roleService.getRoleById(x);
			message.putString(tempRole.getName());
			message.put((byte)role.getNation());
			message.putInt(tempRole.getLv());
			//判断好友是否在线
			if(SessionCache.getSessionById(x) !=null)
			{
				message.put(FriendConstant.FRIEND_ON_LINE);
			}else {
				message.put(FriendConstant.FREIND_OFF_LINE);
			}
		}
		//黑名单数量
		message.put((byte)friend.getBlackListSet().size());
		for(Integer x : friend.getBlackListSet())
		{
			Role tempRole = roleService.getRoleById(x);
			message.putString(tempRole.getName());
			message.put((byte)role.getNation());
			message.putInt(tempRole.getLv());
		}
		
		
		return message;
	}

	/**
	 * 增加好友名单
	 * @author xjd
	 */
	public Message addFriendList(Role role , String targetName) {
		Message message = new Message();
		message.setType(FriendConstant.MODIFY_FRIEND_LIST);
		//取出玩家好友信息
		Friend friend = role.getFriend();
		//判断目标是否合法
		Role targetRole = this.checkId(targetName);
		if(targetRole == null)
		{
			message.putShort(ErrorCode.NULL_TARGET_ROLE);
			return message;
		}
//		//判断好友是否达到上限
//		if(friend.getFriendList().length() >= FriendConstant.MAX_LENGTH_LIST)
//		{
//			message .putShort(ErrorCode.MAX_FRIEND_LIST);
//			return message;
//		}
		//判断目标是否已经是好友
		boolean flag = this.checkSet(friend.getFriendListSet(), targetRole.getId());
		if(flag)
		{
			message.putShort(ErrorCode.AREADY_IS_FRIEND);
			return message;
		}
		//增加好友
		friend.getFriendListSet().add(targetRole.getId());
		friend.setChang(true);
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	/**
	 * 删除好友
	 * @author xjd
	 */
	public Message removeFriendList(Role role, String targetName) {
		Message message = new Message();
		message.setType(FriendConstant.MODIFY_FRIEND_LIST);

		Friend friend = role.getFriend();
		//判断目标是否合法
		Role targetRole  = this.checkId(targetName);
		if(targetRole == null)
		{
			return null;
		}
		//判断目标是否已经是好友
		boolean flag = this.checkSet(friend.getFriendListSet(), targetRole.getId());
		if(!flag)
		{
			message.putShort(ErrorCode.IS_NOT_FRIEND);
			return message;
		}
		//删除好友
		friend.getFriendListSet().remove(targetRole.getId());
		friend.setChang(true);
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	/**
	 * 增加黑名单
	 * @author xjd
	 */
	public Message addBlackList(Role role, String targetName) {
		Message message = new Message();
		message.setType(FriendConstant.MODIFY_BLACK_LIST);
		//取出玩家黑名单信息
		Friend friend = role.getFriend();
		//判断ID是否合法
		Role targetRole = this.checkId(targetName);
		if(targetRole == null)
		{
			message.putShort(ErrorCode.NULL_TARGET_ROLE);
			return message;
		}
//		//判断黑名单是否达到上限
//		if(friend.getBlackList().length() >= FriendConstant.MAX_LENGTH_LIST)
//		{
//			message .putShort(ErrorCode.MAX_BLACK_LIST);
//			return message;
//		}
		//判断黑名单是否存在
		boolean flag = this.checkSet(friend.getBlackListSet(), targetRole.getId());
		if(flag)
		{
			message.putShort(ErrorCode.AREADY_IS_BLACK);
			return message;
		}
		//判断是目标是否在好友列表中
		if(friend.getFriendListSet().contains(targetRole.getId()))
		{
			friend.getFriendListSet().remove(targetRole.getId());
		}
		//增加黑名单
		friend.getBlackListSet().add(targetRole.getId());
		friend.setChang(true);
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

	/**
	 * 删除黑名单
	 * @author xjd
	 */
	public Message removeBlackList(Role role, String targetName) {
		Message message = new Message();
		message.setType(FriendConstant.MODIFY_BLACK_LIST);
		Friend friend = role.getFriend();
		//判断ID是否合法
		Role targetRole = this.checkId(targetName);
		if(targetRole == null)
		{
			return null;
		}
		//判断黑名单是否存在		
		boolean flag = this.checkSet(friend.getBlackListSet(), targetRole.getId());
		if(!flag)
		{
			message.putShort(ErrorCode.IS_NOT_BLACK);
			return message;
		}
		//删除黑名单
		friend.getBlackListSet().remove(targetRole.getId());
		friend.setChang(true);
		
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}
	
	/**
	 * 检查目标ID是否存在</br>
	 * 存在返回 true
	 * @param targetId
	 * @return
	 */
	private Role checkId(String targetName)
	{
		try {
			Role targetRole = roleService.getRoleByName(targetName);
			return targetRole;
		} catch (Exception e) {
			return null;
		}
		
		
	}
	
	/**
	 * 检查Set是否包含目标ID</br>
	 * 如果存在返回 true</br>
	 * 如果不存在返回false
	 * @param set
	 * @param targetId
	 * @return
	 */
	private boolean checkSet(Set<Integer> set ,int targetId)
	{
		boolean b = false;
		
		if(set.contains(targetId))
		{
			b = true;
		}
		return b;
	}
}
