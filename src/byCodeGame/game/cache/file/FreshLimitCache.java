package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.FreshLimit;

public class FreshLimitCache {
	private static Map<Integer, FreshLimit> vipLv1_Map =new HashMap<Integer, FreshLimit>();
	private static Map<Integer, FreshLimit> vipLv2_Map =new HashMap<Integer, FreshLimit>();
	private static Map<Integer, FreshLimit> vipLv3_Map =new HashMap<Integer, FreshLimit>();
	private static Map<Integer, FreshLimit> vipLv4_Map =new HashMap<Integer, FreshLimit>();
	private static Map<Integer, FreshLimit> vipLv5_Map =new HashMap<Integer, FreshLimit>();
	private static Map<Integer, FreshLimit> vipLv6_Map =new HashMap<Integer, FreshLimit>();
	private static Map<Integer, FreshLimit> vipLv7_Map =new HashMap<Integer, FreshLimit>();
	private static Map<Integer, FreshLimit> vipLv8_Map =new HashMap<Integer, FreshLimit>();
	private static Map<Integer, FreshLimit> vipLv9_Map =new HashMap<Integer, FreshLimit>();
	private static Map<Integer, FreshLimit> vipLv10_Map =new HashMap<Integer, FreshLimit>();
	private static Map<Integer, FreshLimit> vipLv11_Map =new HashMap<Integer, FreshLimit>();
	
	public static void putFreshLimit(FreshLimit freshLimit){
		if(freshLimit.getVipLv() == 1)
		{
			vipLv1_Map.put(freshLimit.getThisTime(), freshLimit);
		}else if (freshLimit.getVipLv() == 2) {
			vipLv2_Map.put(freshLimit.getThisTime(), freshLimit);
		}else if (freshLimit.getVipLv() == 3) {
			vipLv3_Map.put(freshLimit.getThisTime(), freshLimit);
		}else if (freshLimit.getVipLv() == 4) {
			vipLv4_Map.put(freshLimit.getThisTime(), freshLimit);
		}else if (freshLimit.getVipLv() == 5) {
			vipLv5_Map.put(freshLimit.getThisTime(), freshLimit);
		}else if (freshLimit.getVipLv() == 6) {
			vipLv6_Map.put(freshLimit.getThisTime(), freshLimit);
		}else if (freshLimit.getVipLv() == 7) {
			vipLv7_Map.put(freshLimit.getThisTime(), freshLimit);
		}else if (freshLimit.getVipLv() == 8) {
			vipLv8_Map.put(freshLimit.getThisTime(), freshLimit);
		}else if (freshLimit.getVipLv() == 9) {
			vipLv9_Map.put(freshLimit.getThisTime(), freshLimit);
		}else if (freshLimit.getVipLv() == 10) {
			vipLv10_Map.put(freshLimit.getThisTime(), freshLimit);
		}else if (freshLimit.getVipLv() == 11) {
			vipLv11_Map.put(freshLimit.getThisTime(), freshLimit);
		}
	}
			
	public static FreshLimit getFreshLimit(int vipLv,int thisTime){
		FreshLimit freshLimit = new FreshLimit();
		if(vipLv == 1)
		{
			freshLimit = vipLv1_Map.get(thisTime);
		}else if (vipLv == 2) {
			freshLimit = vipLv2_Map.get(thisTime);
		}else if (vipLv == 3) {
			freshLimit = vipLv3_Map.get(thisTime);
		}else if (vipLv == 4) {
			freshLimit = vipLv4_Map.get(thisTime);
		}else if (vipLv == 5) {
			freshLimit = vipLv5_Map.get(thisTime);
		}else if (vipLv == 6) {
			freshLimit = vipLv6_Map.get(thisTime);
		}else if (vipLv == 7) {
			freshLimit = vipLv7_Map.get(thisTime);
		}else if (vipLv == 8) {
			freshLimit = vipLv8_Map.get(thisTime);
		}else if (vipLv == 9) {
			freshLimit = vipLv9_Map.get(thisTime);
		}else if (vipLv == 10) {
			freshLimit = vipLv10_Map.get(thisTime);
		}else if (vipLv == 11) {
			freshLimit = vipLv11_Map.get(thisTime);
		}
		
		return freshLimit;
	}
}
