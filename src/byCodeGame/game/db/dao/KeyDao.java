package byCodeGame.game.db.dao;

import byCodeGame.game.entity.Key;
import byCodeGame.game.entity.bo.Recharge;

public interface KeyDao {
	/**
	 * 特权码
	 * @param id
	 * @return
	 */
	public Key getKey(String id);
	
	public void updateKey(Key key);
	
	
	public void insterRecharge(Recharge recharge);
}
