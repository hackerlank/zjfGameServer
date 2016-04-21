package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import byCodeGame.game.entity.bo.Role;

public class RoleConverter implements ResultConverter<Role> {

	public Role convert(ResultSet rs) throws SQLException{
		Role role = new Role();
		role.setId(rs.getInt("id"));
		role.setName(rs.getString("name"));
		role.setLv(rs.getShort("lv"));
		role.setExp(rs.getInt("exp"));
		role.setMoney(rs.getInt("money"));
		role.setGold(rs.getInt("gold"));
		role.setSumGold(rs.getInt("sumGold"));
		role.setPrestige(rs.getInt("prestige"));
		role.setFood(rs.getInt("food"));
		role.setExploit(rs.getInt("exploit"));
		role.setVipLv(rs.getByte("vipLv"));
		role.setAlreadyGetVipAward(rs.getString("alreadyGetVipAward"));
		role.setArmyToken(rs.getInt("armyToken"));
		role.setUseFormationID(rs.getInt("useFormationID"));
		role.setAccount(rs.getString("account"));
		role.setNation(rs.getByte("nation"));
		role.setLegionId(rs.getInt("legionId"));
		role.setApplyLegion(rs.getString("applyLegion"));
		role.setLastOffLineTime(rs.getLong("lastOffLineTime"));
		role.setMaxBagNum(rs.getShort("maxBagNum"));
		role.setRoleScience(rs.getString("roleScience"));
		role.setFormationStr(rs.getString("formationStr"));
		role.setLegionContribution(rs.getInt("legionContribution"));
		role.setLastGetArmyToken(rs.getLong("lastGetArmyToken"));
		role.setFaceId(rs.getByte("faceId"));
		role.setPopulation(rs.getInt("population"));
		role.setPopulationLimit(rs.getInt("populationLimit"));
		role.setBuildQueue(rs.getString("buildQueue"));
		role.setRoleScienceQueue(rs.getString("roleScienceQueue"));
		role.setLoginNum(rs.getInt("loginNum"));
		role.setOnLineSumTime(rs.getLong("onLineSumTime"));
		role.setPlayerOperation(rs.getString("playerOperation"));
		role.setArmsResearchStr(rs.getString("armsResearchStr"));
		role.setRank(rs.getInt("rank"));
		role.setIconUnlock(rs.getString("iconUnlock"));
		role.setFightValue(rs.getInt("fightValue"));
		role.setLeadPoint(rs.getInt("leadPoint"));
		role.setLeadStr(rs.getString("leadStr"));
		role.setWorldFormationStr(rs.getString("worldFormation"));
		role.setStrongHold(rs.getInt("strongHold"));
		role.setHistoryStr(rs.getString("history"));
		role.setPumHistoryStr(rs.getString("pumHistoryStr"));
		role.setGetPstr(rs.getString("getPstr"));
		role.setRankRecordTimeStr(rs.getString("rankRecordTime"));
		role.setVillageDefStr(rs.getString("villageDefStr"));
		role.setVillageMineFarmWorldArmyId(rs.getString("villageMineFarmWorldArmyId"));
		role.setVillageMineFarmStartTime(rs.getInt("villageMineFarmStartTime"));
		role.setVillageNation(rs.getByte("villageNation"));
		role.setWorldDoublePStr(rs.getString("worldDoublePStr"));
		
		return role;
	}
	
}
