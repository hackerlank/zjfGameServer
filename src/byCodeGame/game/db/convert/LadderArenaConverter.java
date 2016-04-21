package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.entity.bo.LadderArena;

public class LadderArenaConverter implements ResultConverter<LadderArena> {

	public LadderArena convert(ResultSet rs) throws SQLException{
		LadderArena ladderArena = new LadderArena();
		ladderArena.setRoleId(rs.getInt("roleId"));
		ladderArena.setRank(rs.getShort("rank"));
		return ladderArena;
	}
}
