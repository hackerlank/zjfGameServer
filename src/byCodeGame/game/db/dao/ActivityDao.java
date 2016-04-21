package byCodeGame.game.db.dao;

import java.sql.Connection;

import byCodeGame.game.entity.bo.Activity;

public interface ActivityDao {
	int insertActivity(Activity activity, Connection conn);

	void insertActivity(Activity activity);

	void updateActivity(Activity activity);

	Activity getActivityByActivityId(int activityId);
}
