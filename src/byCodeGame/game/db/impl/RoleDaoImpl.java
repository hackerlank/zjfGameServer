package byCodeGame.game.db.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import byCodeGame.game.db.access.DataAccess;
import byCodeGame.game.db.convert.RoleConverter;
import byCodeGame.game.db.convert.base.IntegerConverter;
import byCodeGame.game.db.convert.base.StringConverter;
import byCodeGame.game.db.dao.RoleDao;
import byCodeGame.game.entity.bo.Role;

/**
 * 
 * @author wcy 2016年4月28日
 *
 */
public class RoleDaoImpl extends DataAccess implements RoleDao {

	private final String insertSql = "insert into role values(?,?,?,?)";
	private final String selectAccountSql = "select * from role where account=? limit 1";
	private final String updateSql = "update role set name=?, account=?, loveHeroId=? where id=? limit 1";
	private final String selectAllAccountSql = "select account from role";
	private final String selectAllNameSql = "select name from role";

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private RoleConverter roleConverter;

	public void setRoleConverter(RoleConverter roleConverter) {
		this.roleConverter = roleConverter;
	}

	private IntegerConverter integerConverter;

	public void setIntegerConverter(IntegerConverter integerConverter) {
		this.integerConverter = integerConverter;
	}

	private StringConverter stringConverter;

	public void setStringConverter(StringConverter stringConverter) {
		this.stringConverter = stringConverter;
	}

	@Override
	public Role getRoleByAccount(String account) {
		try {
			Connection conn = dataSource.getConnection();
			this.queryForObject(selectAccountSql, roleConverter, conn, account);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Role insertRoleNotCloseConnection(Role role, Connection conn) {
		try {
			Integer id = this.insertNotCloseConn(insertSql, integerConverter, conn, null, role.getName(),
					role.getAccount(), role.getLoveHeroId());
			if (id != null) {
				role.setId(id);
				return role;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void updateRole(Role role) {
		try {
			Connection conn = dataSource.getConnection();
			this.update(updateSql, conn, role.getName(), role.getAccount(), role.getLoveHeroId(), role.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> getAllAccount() {
		List<String> list = null;
		try {
			Connection conn = dataSource.getConnection();
			list = this.queryForList(selectAllAccountSql, stringConverter, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<String> getAllName() {
		List<String> list = null;
		try {
			Connection conn = dataSource.getConnection();
			list = this.queryForList(selectAllNameSql, stringConverter, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
