package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.entity.bo.Activity;

public class ActivityConverter implements ResultConverter<Activity> {
	@Override
	public Activity convert(ResultSet rs) throws SQLException {
		Activity activity = new Activity();
		activity.setId(rs.getInt("id"));
		activity.setActivityId(rs.getInt("activityId"));
		activity.setStatus(rs.getByte("status"));
		activity.setDataStr(rs.getString("data"));
		return activity;
	}
}
