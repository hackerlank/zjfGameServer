package byCodeGame.game.db.dao;

import java.sql.Connection;

import byCodeGame.game.entity.bo.Barrack;

public interface BarrackDao {
	
	public void insertBarrack(Barrack barrack ,Connection conn);
	
	public void updataBarrack(Barrack barrack);
	
	public Barrack getBarrack(int id);
	

}
