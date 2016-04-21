package byCodeGame.game.entity.bo;

import java.util.HashSet;
import java.util.Set;

/**
 * 玩家的好友&黑名单
 * 
 * @author xjd
 *
 */
public class Friend {
	/** 玩家ID*/
	private int roleId;
	/** 玩家好友		 */
	private String friendList;
	/** 玩家好友Set	 */
	private Set<Integer> friendListSet = new HashSet<Integer>();
	/** 玩家黑名单		 */
	private String blackList;
	/** 玩家黑名单Set	 */
	private Set<Integer> blackListSet = new HashSet<Integer>();
	
	private boolean chang;
	
	
	
	public Set<Integer> getFriendListSet() {
		return friendListSet;
	}

	public void setFriendListSet(Set<Integer> friendListSet) {
		this.friendListSet = friendListSet;
	}
	
	public String getFriendList() {
		StringBuilder sb = new StringBuilder();
		for(Integer friendId : this.friendListSet){
			sb.append(friendId).append(",");
		}
		this.friendList = sb.toString();
		return friendList;
	}

	public void setFriendList(String friendList) {
		if(friendList != null && !friendList.equals("")){
			String[] strs = friendList.split(",");
			for(String friendId : strs){
				this.friendListSet.add(Integer.valueOf(friendId));
			}
		}
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public Set<Integer> getBlackListSet() {
		return blackListSet;
	}

	public void setBlackListSet(Set<Integer> blackListSet) {
		this.blackListSet = blackListSet;
	}

	public String getBlackList() {
		StringBuilder sb = new StringBuilder();
		for(Integer blackId : this.blackListSet){
			sb.append(blackId).append(",");
		}
		this.blackList = sb.toString();
		return blackList;
	}

	public void setBlackList(String blackList) {
		if(blackList != null && !blackList.equals("")){
			String[] strs = blackList.split(",");
			for(String blackId : strs){
				this.blackListSet.add(Integer.valueOf(blackId));
			}
		}
	}

	public boolean isChang() {
		return chang;
	}

	public void setChang(boolean chang) {
		this.chang = chang;
	}
}
