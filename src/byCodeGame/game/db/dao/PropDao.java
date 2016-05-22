package byCodeGame.game.db.dao;

import java.util.List;

import byCodeGame.game.entity.bo.Prop;

public interface PropDao {
	List<Prop> getPropsByRoleId(int roleId);
}
