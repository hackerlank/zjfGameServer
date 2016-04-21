package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.entity.bo.Barrack;

public class BarrackConverter implements ResultConverter<Barrack> {

	public Barrack convert(ResultSet rs) throws SQLException{
		Barrack barrack = new Barrack();
		barrack.setRoleId(rs.getInt("roleId"));
		barrack.setTroops(rs.getString("troops"));
		barrack.setQueue(rs.getString("queue"));
		barrack.setBuildLv(rs.getString("buildLv"));
		barrack.setArmySkillLv(rs.getString("armySkillLv"));
		
		return barrack;
	}
}
