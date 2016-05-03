package byCodeGame.game.db.dao;

import java.sql.Connection;

import byCodeGame.game.entity.bo.Bin;

public interface BinDao {
	void insertBinNotCloseConnection(Bin bin, Connection conn);

	Bin getBinByRoleId(int roleId);

	void updateBin(Bin bin);
}
