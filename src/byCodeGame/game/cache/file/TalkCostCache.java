package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.TalkCost;

public class TalkCostCache {
	private static Map<Integer, TalkCost>talkCostMap = new HashMap<Integer, TalkCost>();
	
	public static void putTalkCost(TalkCost talkCost){
		talkCostMap.put(talkCost.getTalksNumber(), talkCost);
	}
	
	public static TalkCost getTalkCostByNumber(int talksNumber){
		return talkCostMap.get(talksNumber);
	}
}

