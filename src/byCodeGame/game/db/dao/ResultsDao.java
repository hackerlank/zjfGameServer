package byCodeGame.game.db.dao;


import cn.bycode.game.battle.data.ResultData;

public interface ResultsDao {
	public void insertResults(ResultData resultData);
	
	public ResultData getResults(String uuid);
}
