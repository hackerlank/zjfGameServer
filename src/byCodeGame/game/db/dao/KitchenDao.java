package byCodeGame.game.db.dao;

import java.sql.Connection;

import byCodeGame.game.entity.bo.Kitchen;

public interface KitchenDao {
	void insertKitchenNotCloseConnection(Kitchen kitchen, Connection conn);

	Kitchen getKitchenByRoleId(int roleId);

	void updateKitchen(Kitchen kitchen);
}
