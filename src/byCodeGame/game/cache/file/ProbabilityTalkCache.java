package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.ProbabilityTalk;

public class ProbabilityTalkCache {
private static Map<Integer, ProbabilityTalk> probabilityTalktMap = new HashMap<Integer, ProbabilityTalk>();
	
	public static void putProbabilityTalk(ProbabilityTalk probabilityTalk){
		probabilityTalktMap.put(probabilityTalk.getFigure(), probabilityTalk);
	}
	
	public static ProbabilityTalk getProbabilityTalkByFigure(int figure){
		return probabilityTalktMap.get(figure);
	}
}
