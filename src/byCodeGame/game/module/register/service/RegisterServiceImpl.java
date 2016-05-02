package byCodeGame.game.module.register.service;

import java.sql.Connection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import javax.sql.DataSource;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.db.dao.BedroomDao;
import byCodeGame.game.db.dao.RoleDao;
import byCodeGame.game.entity.bo.Bedroom;
import byCodeGame.game.entity.bo.Build;
import byCodeGame.game.entity.bo.Kitchen;
import byCodeGame.game.entity.bo.Pub;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.module.register.RegisterConstant;
import byCodeGame.game.remote.Message;
import byCodeGame.game.tools.CacheLockUtil;

/**
 * 
 * @author AIM
 *
 */
public class RegisterServiceImpl implements RegisterService {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private RoleDao roleDao;

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	private BedroomDao bedroomDao;

	public void setBedroomDao(BedroomDao bedroomDao) {
		this.bedroomDao = bedroomDao;
	}

	@Override
	public Message register(String account, String name) {
		Message message = new Message();
		message.setType(RegisterConstant.REGISTER);

		ReentrantLock reentrantLock = CacheLockUtil.getLock(String.class, account);
		reentrantLock.lock();
		try {
			Set<String> accountSet = RoleCache.getAccountSet();
			Set<String> nameSet = RoleCache.getNameSet();
			if (accountSet.contains(account)) { // 判定账号是否存在
				message.putShort(ErrorCode.REGISTER_ACCOUNT_REPEAT);
				return message;
			}

			if (nameSet.contains(name)) {
				message.putShort(ErrorCode.REGISTER_NAME_REPEAT);
				return message;
			}

			Connection conn = null;
			try {
				// mysql事务
				conn = dataSource.getConnection();
				conn.setAutoCommit(false);

				// 用户数据
				Role role = new Role();
				role.setAccount(account);
				role.setName(name);
				this.roleRegisterDataInit(role, conn);

				conn.commit(); // 提交JDBC事务

				// 加入role缓存,确保加入缓存池后在进行提交
				RoleCache.putRole(role);
				RoleCache.getAccountSet().add(role.getAccount());
				RoleCache.getNameSet().add(role.getName());

				conn.setAutoCommit(true); // 恢复JDBC事务的默认提交方式

				message.putShort(ErrorCode.SUCCESS);
				return message;

			} catch (Exception e1) {
				e1.printStackTrace();
				try {
					conn.rollback();// 回滚JDBC事务
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				e1.printStackTrace();
				message.putShort(ErrorCode.REGISTER_LACK_INFO);
				return message;
			} finally {
				if (conn != null) {
					try {
						conn.setAutoCommit(true);
						conn.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.putShort(ErrorCode.REGISTER_FAILED);
			return message;
		} finally {
			reentrantLock.unlock();
		}
	}

	/**
	 * 玩家数据注册初始化
	 * 
	 * @param role
	 */
	private void roleRegisterDataInit(Role role, Connection conn) {
		// 初始化玩家的英雄
		role = roleDao.insertRoleNotCloseConnection(role, conn);
		int roleId = role.getId();

		Build build = new Build();
		build.setRoleId(roleId);
		role.setBuild(build);

		// 卧室
		Bedroom bedroom = new Bedroom();
		bedroom.setRoleId(roleId);
		build.setBedroom(bedroom);
		bedroomDao.insertBedroomNotCloseConnection(bedroom, conn);

		// 厨房
		Kitchen kitchen = new Kitchen();
		kitchen.setRoleId(roleId);
		build.setKitchen(kitchen);

		// 酒馆
		Pub pub = new Pub();
		pub.setRoleId(roleId);
		build.setPub(pub);
	}

	@Override
	public void init() {
		List<String> accountList = roleDao.getAllAccount();
		List<String> nameList = roleDao.getAllName();
		Set<String> accountSet = RoleCache.getAccountSet();
		Set<String> nameSet = RoleCache.getNameSet();

		for (String account : accountList) {
			accountSet.add(account);
		}
		for (String name : nameList) {
			nameSet.add(name);
		}
	}
}
