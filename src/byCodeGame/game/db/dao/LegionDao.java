package byCodeGame.game.db.dao;

import java.util.List;

import byCodeGame.game.entity.bo.Legion;

public interface LegionDao {

	public int insertLegion(Legion legion);
	
	public void updateLegion(Legion legion);
	
	public Legion getLegionById(int id);
	
	public void removeLegion(int id);
	
	/**
	 * 获取所有军团名集合
	 * @return
	 */
	public List<String> getAllLegionName();
	
	/**
	 * 获取所有军团 服务器启动时触发
	 * @return
	 */
	public List<Legion> getAllLegion();
	
}
