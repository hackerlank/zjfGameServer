package byCodeGame.game.module.register.service;

import java.sql.Connection;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import javax.sql.DataSource;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.module.hero.service.HeroService;
import byCodeGame.game.module.login.LoginConstant;
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

	private HeroService heroService;

	public void HeroService(HeroService heroService) {
		this.heroService = heroService;
	}

	@Override
	public Message register(String account) {
		Message message = new Message();
		message.setType(RegisterConstant.REGISTER);

		ReentrantLock reentrantLock = CacheLockUtil.getLock(String.class, account);
		reentrantLock.lock();
		try {
			Set<String> accountSet = RoleCache.getAccountSet();
			if (accountSet.contains(account)) { // 判定账号是否存在
				return null;
			}

			Connection conn = null;
			try { // mysql事务
				conn = dataSource.getConnection();
				conn.setAutoCommit(false);

				// 用户数据
				Role role = new Role();
				this.roleRegisterDataInit(role, conn);

				conn.commit(); // 提交JDBC事务
				conn.setAutoCommit(true); // 恢复JDBC事务的默认提交方式
				// 加入role缓存
				RoleCache.putRole(role);
				RoleCache.getAccountSet().add(account);
				RoleCache.getNameSet().add(role.getName());
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
				return null;
			} finally {
				if (conn != null) {
					try {
						conn.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
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
		//初始化玩家的英雄
		heroService.createHeros(RegisterConstant.INIT_HERO_NUM, conn);
	}
}
