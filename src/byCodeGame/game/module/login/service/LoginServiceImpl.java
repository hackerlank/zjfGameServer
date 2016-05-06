package byCodeGame.game.module.login.service;

import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.cache.local.SessionCache;
import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.module.login.LoginConstant;
import byCodeGame.game.module.role.service.RoleService;
import byCodeGame.game.remote.Message;
import byCodeGame.game.tools.CacheLockUtil;
import byCodeGame.game.util.SysManager;

/**
 * 登录服务
 * 
 * @author AIM
 *
 */
public class LoginServiceImpl implements LoginService {

	private RoleService roleService;

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

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

	@Override
	public Message getRoleData(String account, IoSession ioSession) {
		// ReentrantLock reentrantLock = CacheLockUtil.getLock(String.class,
		// account);
		// reentrantLock.lock();

		try {
			Message message = new Message();
			message.setType(LoginConstant.GET_ROLE_DATA);

			Role role = roleService.getRoleByAccount(account);
			if (role == null) {
				message.putShort(ErrorCode.NO_ROLE);
				return message;
			}

			IoSession oldSession = SessionCache.getSessionByRoleId(role.getId());
			if (oldSession != null) { // 该账号已登录
				oldSession.setAttribute("roleId", null);
				oldSession.close(false);
			}

			// session绑定ID
			ioSession.setAttribute("roleId", role.getId());
			// session放入缓存
			SessionCache.addSession(role.getId(), ioSession);

			return message;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// finally {
		// reentrantLock.unlock();
		// }

	}

}
