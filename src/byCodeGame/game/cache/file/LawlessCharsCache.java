package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.LawlessChars;

public class LawlessCharsCache {
	private static Map<String, LawlessChars> map = new HashMap<String, LawlessChars>();
	
	
	public static void putLawlessChars(LawlessChars lawlessChars)
	{
		map.put(lawlessChars.getChars(), lawlessChars);
	}
	
	public static LawlessChars getLawlessCharsByString(String chars)
	{
		return map.get(chars);
	}
}
