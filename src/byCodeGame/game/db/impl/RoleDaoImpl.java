
package byCodeGame.game.db.impl;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.convert.IntegerConverter;
import byCodeGame.game.db.convert.RoleConverter;
import byCodeGame.game.db.convert.StartRoleConverter;
import byCodeGame.game.db.convert.StringConverter;
import byCodeGame.game.db.dao.RoleDao;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.po.StartRole;

public class RoleDaoImpl extends DataAccess implements RoleDao {

	private DataSource dataSource;
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public int insertRole(Role role,Connection conn) {
		int id = 0;
		final String sql = "insert into role(id,name,account,lv,exp,money,gold,prestige,food,exploit,"
				+ "vipLv,alreadyGetVipAward,armyToken,useFormationID,maxBagNum,"
				+ "roleScience,legionContribution,lastGetArmyToken,faceId,raidTimes,population,populationLimit,buildQueue,roleScienceQueue,"
				+ "registerIp,registerTime,armsResearchStr,rank,iconUnlock,fightValue,leadPoint,leadStr,formationStr,worldFormation,rankRecordTime,"
				+ "villageDefStr,villageMineFarmWorldArmyId,villageMineFarmStartTime,villageNation,worldDoublePStr)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			id = super.insertNotCloseConn(sql, new IntegerConverter() , conn,null,role.getName(),role.getAccount(),role.getLv(),role.getExp(),
					role.getMoney(),role.getGold(),role.getPrestige(),role.getFood(),role.getExploit(),
					role.getVipLv(),role.getAlreadyGetVipAward(),role.getArmyToken(),role.getUseFormationID(),role.getMaxBagNum(),
					role.getRoleScience(),role.getLegionContribution(),
					role.getLastGetArmyToken(),role.getFaceId(),role.getRaidTimes(),role.getPopulation(role),role.getPopulationLimit(role),
					role.getBuildQueue(),role.getRoleScienceQueue(),role.getRegisterIp(),role.getRegisterTime(),role.getArmsResearchStr(),
					role.getRank(),role.getIconUnlock(),role.getFightValue(),role.getLeadPoint(),role.getLeadStr(),role.getFormationStr(),
					role.getWorldFormationStr(),role.getRankRecordTimeStr(),
					role.getVillageDefStr(),role.getVillageMineFarmWorldArmyId(),
					role.getVillageMineFarmStartTime(),role.getVillageNation(),role.getWorldDoublePStr());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public void updateRole(Role role) {
		final String sql = "update role set name=?,lv=?,exp=?,money=?,gold=?,prestige=?,food=?, "
				+ "exploit=?,vipLv=?,alreadyGetVipAward=?,armyToken=?,useFormationID=?,account=?,nation=?,legionId=?,applyLegion=?,"
				+ "lastOffLineTime=?,maxBagNum=?,"
				+ "roleScience=?,sumGold=?,formationStr=?,legionContribution=?,lastGetArmyToken=?,"
				+ "faceId=?,raidTimes=?,population=?,populationLimit=?,buildQueue=?,roleScienceQueue=?,lastLoginIp=?,"
				+ "loginNum=?,onLineSumTime=?,playerOperation=?,armsResearchStr=? ,rank=?, iconUnlock=?,fightValue=?,"
				+ "leadPoint=?,leadStr=?,worldFormation=?,strongHold=?,history=?,pumHistoryStr=?,getPstr=?,rankRecordTime=?,"
				+ "villageDefStr=?,villageMineFarmWorldArmyId=?,villageMineFarmStartTime=?,villageNation=?,worldDoublePStr=?"
				+ " where id=? limit 1";
		try {
			Connection conn = dataSource.getConnection();
			super.update(sql, conn, role.getName(),role.getLv(),role.getExp(),role.getMoney(),
					role.getGold(),role.getPrestige(),role.getFood(),role.getExploit(),role.getVipLv(),
					role.getAlreadyGetVipAward(),role.getArmyToken(),role.getUseFormationID(),role.getAccount(),role.getNation(),
					role.getLegionId(),role.getApplyLegion(),role.getLastOffLineTime(),
					role.getMaxBagNum(),
					role.getRoleScience(),
					role.getSumGold(),role.getFormationStr(),role.getLegionContribution(),role.getLastGetArmyToken(),
					role.getFaceId(),role.getRaidTimes(),role.getPopulation(role),role.getPopulationLimit(role),role.getBuildQueue(),
					role.getRoleScienceQueue(),role.getLastLoginIp(),role.getLoginNum(),role.getOnLineSumTime(),role.getPlayerOperation(),
					role.getArmsResearchStr(),role.getRank(),role.getIconUnlock(),role.getFightValue(),
					role.getLeadPoint(),role.getLeadStr(),role.getWorldFormationStr(),role.getStrongHold(),role.getHistoryStr(),
					role.getPumHistoryStr(),role.getGetPstr(),role.getRankRecordTimeStr(),role.getVillageDefStr(),role.getVillageMineFarmWorldArmyId(),
					role.getVillageMineFarmStartTime(),role.getVillageNation(),role.getWorldDoublePStr(),
					role.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Role getRoleById(int id){
		final String sql = "select * from role where id=? ";
		try {
			Connection conn = dataSource.getConnection();
			Role result = super.queryForObject(sql, new RoleConverter() , conn, id);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	public Role getRoleByName(String name){
		final String sql = "select * from role where name=? ";
		try {
			Connection conn = dataSource.getConnection();
			Role result = super.queryForObject(sql, new RoleConverter() , conn, name);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Role getRoleByAccount(String account){
		final String sql = "select * from role where account=? ";
		try {
			Connection conn = dataSource.getConnection();
			Role result = super.queryForObject(sql, new RoleConverter() , conn, account);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> getAllAccount(){
		String sql = "select account from role ";
		try {
			Connection conn = dataSource.getConnection();
			List<String> result = super.queryForList(sql, new StringConverter(),
					conn);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Role> getAllRole(){
		final String sql = "select * from role";
		try {
			Connection conn = dataSource.getConnection();
			List<Role> result = super.queryForList(sql, new RoleConverter() , conn);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> getAllName(){
		String sql = "select name from role ";
		try {
			Connection conn = dataSource.getConnection();
			List<String> result = super.queryForList(sql, new StringConverter(),
					conn);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void serverStartInit(){
		try {
			Connection conn = dataSource.getConnection();
			List<StartRole> resultTmep = super.queryForList(
							"select id,name,account,nation from role",new StartRoleConverter(), conn);
			if(resultTmep.size() > 0){
				for(StartRole startRole : resultTmep){
					RoleCache.getAccountSet().add(startRole.getAccount());
					RoleCache.getNameSet().add(startRole.getName());
					RoleCache.getNameIdMap().put(startRole.getName(), startRole.getId());
					Role role = new Role();
					role.setAccount(startRole.getAccount());
					role.setName(startRole.getName());
					role.setId(startRole.getId());
					role.setNation(startRole.getNation());
					RoleCache.putRoleNation(role);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
