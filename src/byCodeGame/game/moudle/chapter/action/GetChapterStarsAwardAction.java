package byCodeGame.game.moudle.chapter.action;

import org.apache.mina.core.session.IoSession;

import byCodeGame.game.cache.local.RoleCache;
import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.moudle.chapter.service.ChapterService;
import byCodeGame.game.navigation.ActionSupport;
import byCodeGame.game.remote.Message;

public class GetChapterStarsAwardAction implements ActionSupport{
	private ChapterService chapterService;
	public void setChapterService(ChapterService chapterService) {
		this.chapterService = chapterService;
	}
	
	public void execute(Message message, IoSession session) {
		Message msg = new Message();
		Role role = RoleCache.getRoleBySession(session);
		int chapterId = message.getInt();
		int process = message.getInt();
		
		msg = chapterService.getChapterStarsAward(role, chapterId, process);
		if(msg == null)
		{
			return;
		}else {
			session.write(msg);
		}
	}


	
}
