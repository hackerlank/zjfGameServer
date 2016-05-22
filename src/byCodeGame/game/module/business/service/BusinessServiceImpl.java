package byCodeGame.game.module.business.service;

import byCodeGame.game.common.ErrorCode;
import byCodeGame.game.entity.bo.Business;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.entity.po.WorkInfo;
import byCodeGame.game.remote.Message;
import byCodeGame.game.util.Utils;

public class BusinessServiceImpl implements BusinessService {

	@Override
	public Message workForSelf(Role role, int heroId, String materials) {
		Message message = new Message();
		int nowTime = Utils.getNowTime();
		int roleId = role.getId();

		Business business = role.getBusiness();
		WorkInfo workInfo = new WorkInfo();

		workInfo.setWorkId(nowTime + "@" + heroId);
		workInfo.setStartTime(nowTime);
		workInfo.setLastRefreshTime(nowTime);
		workInfo.setHeroId(heroId);
		workInfo.setWorkRoleId(roleId);
		workInfo.setMasterRoleId(roleId);
		
		business.getWorkInfoMap().put(workInfo.getWorkId(), workInfo);
		message.putShort(ErrorCode.SUCCESS);
		return message;
	}

	@Override
	public Message workForOther(Role role, int targetRoleId, int heroId, String materials) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
