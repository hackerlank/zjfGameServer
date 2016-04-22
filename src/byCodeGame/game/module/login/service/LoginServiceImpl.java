package byCodeGame.game.module.login.service;

import java.sql.Connection;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.cache.local.SessionCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.module.login.LoginConstant;
import byCodeGame.game.remote.Message;
import byCodeGame.game.tools.CacheLockUtil;
import byCodeGame.game.util.SysManager;
import byCodeGame.game.util.Utils;

public class LoginServiceImpl implements LoginService{

	@Override
	public Message login(String account, IoSession session) {
		ReentrantLock reentrantLock = CacheLockUtil.getLock(String.class, account);
		reentrantLock.lock();
		Message message = new Message();
		message.setType(LoginConstant.LOGIN);
		if (!SysManager.isSERVICE_FLAG()) {
			message.putShort(ErrorCode.SERVICE_CLOSED);
			return message;
		}
		try {
			Set<String> accountSet = RoleCache.getAccountSet();
			if (accountSet.contains(account)) { // 有数据 登录成功
				message.putShort(ErrorCode.SUCCESS);
			} else { // 无数据 创建角色
				message.putShort(ErrorCode.SHORT_TWO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			reentrantLock.unlock();
		}
		return message;
	}
	
	public Message register(String account){
		Message message = new Message();
		message.setType(LoginConstant.REGISTER);
				
		ReentrantLock reentrantLock = CacheLockUtil.getLock(String.class, account);
		reentrantLock.lock();
		try {
			int nowTime = Utils.getNowTime();
			Set<String> accountSet = RoleCache.getAccountSet();
			if (accountSet.contains(account)) { // 判定账号是否存在
				return null;
			}

			Connection conn = null;
			try { // mysql事务
//				conn = dataSource.getConnection();
				conn.setAutoCommit(false);

				// 用户数据
				Role role = new Role();
				
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
	public Message getRoleData(String account, IoSession ioSession) {
		ReentrantLock reentrantLock = CacheLockUtil.getLock(String.class, account);
		reentrantLock.lock();

		try {
			Message message = new Message();
			message.setType(LoginConstant.GET_ROLE_DATA);

			// 获取缓存中的role
			Role role = null;
//			Role role = RoleCache.getRoleByAccount(account);

			if (role == null) { // 缓存中没有role数据
//				role = roleDao.getRoleByAccount(account);
//				this.loginRoleModuleDataInit(role);
			}
			
		
			// 检查时候需要更新市场
			
			//检测配属英雄
			IoSession oldSession = SessionCache.getSessionByRoleId(role.getId());
			if (oldSession != null) { // 该账号已登录
				oldSession.setAttribute("roleId", null);
				oldSession.close(false);
			}

			// session绑定ID
			ioSession.setAttribute("roleId", role.getId());
			// session放入缓存
			SessionCache.addSession(role.getId(), ioSession);
			// System.out.println(ioSession + "放入缓存");

			
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			reentrantLock.unlock();
		}

	}
}
