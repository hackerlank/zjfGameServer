package byCodeGame.game.moudle.chapter.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.chapter.service.ChapterService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class GetAllChapterDataAction implements ActionSupport {

	private ChapterService chapterService;
	public void setChapterService(ChapterService chapterService) {
		this.chapterService = chapterService;
	}
	
	@Override
	public void execute(Message message, IoSession session) {
		Role role = RoleCache.getRoleBySession(session);
		Message msg = chapterService.getAllChapterData(role);
		if(msg == null){
			return;
		}else{
			session.write(msg);
		}
	}

}
