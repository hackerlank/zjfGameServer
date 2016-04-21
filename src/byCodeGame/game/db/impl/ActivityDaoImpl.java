package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.convert.ActivityConverter;
import byCodeGame.game.db.convert.IntegerConverter;
import byCodeGame.game.db.dao.ActivityDao;
import byCodeGame.game.entity.bo.Activity;

/**
 * 
 * @author wcy 2016年4月19日
 *
 */
public class ActivityDaoImpl extends DataAccess implements ActivityDao {
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Activity getActivityByActivityId(int activityId) {
		final String sql = "select * from activity where activityId=? ";
		try {
			Connection conn = dataSource.getConnection();
			Activity result = super.queryForObject(sql, new ActivityConverter(), conn, activityId);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int insertActivity(Activity activity, Connection conn) {
		int id = 0;
		final String sql = "insert into activity(id,activityId,status,data) values(?,?,?,?)";
		try {
			id = super.insertNotCloseConn(sql, new IntegerConverter(), conn, null, activity.getActivityId(),
					activity.getStatus(), activity.getDataStr());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public void insertActivity(Activity activity) {
		try {
			Connection conn = dataSource.getConnection();
			int id = this.insertActivity(activity, conn);
			activity.setId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateActivity(Activity activity) {
		final String sql = "update activity set activityId=?,status=?,data=? where id =? limit 1";
		try {
			Connection conn = dataSource.getConnection();
			super.update(sql, conn, activity.getActivityId(), activity.getStatus(), activity.getDataStr(),
					activity.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
