package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.AwardSign;

public class AwardSignCache {
	private static Map<Integer, AwardSign> awardMap = new HashMap<Integer, AwardSign>();
	
	public static void putAwardSign(AwardSign awardSign)
	{
		awardMap.put(awardSign.getNumber(), awardSign);
	}
	
	public static AwardSign getAwardSignByNumber(int number)
	{
		return awardMap.get(number);
	}
	
	public static Map<Integer, AwardSign> getSignAwardMap()
	{
		return awardMap;
	}
}
