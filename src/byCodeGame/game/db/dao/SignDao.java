package byCodeGame.game.db.dao;

import java.sql.Connection;

import byCodeGame.game.entity.bo.Sign;

public interface SignDao {
	/**
	 * 插入一条新的签到信息
	 * @param sign
	 * @param conn
	 * @author xjd
	 */
	public void insertSign(Sign sign , Connection conn);
	
	/**
	 * 更新签到
	 * @param sign
	 * @author xjd
	 */
	public void updateSign(Sign sign);
	
	public Sign getSignRoleId(int roleId);
}
