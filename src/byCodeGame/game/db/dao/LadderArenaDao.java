package byCodeGame.game.db.dao;

import java.sql.Connection;
import java.util.List;

import byCodeGame.game.entity.bo.LadderArena;
import byCodeGame.game.entity.bo.Role;

public interface LadderArenaDao {

	public void insterLadderArena(Connection conn);
	
	public void updateLadderArena(LadderArena ladderArena);
	/**
	 * 插入新玩家竞技场排名
	 * @param conn
	 * @param ladderArena
	 */
	public void insterLadderArenaById(Connection conn,Role role);
	/**
	 * 获取天梯排名
	 * @return  
	 */ 
	public List<LadderArena> getAllLadderArena();
}
