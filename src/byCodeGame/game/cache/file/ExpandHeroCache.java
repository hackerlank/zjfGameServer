package byCodeGame.game.cache.file;

import java.util.HashMap;
import java.util.Map;

import byCodeGame.game.entity.file.ExpandHero;

public class ExpandHeroCache {
	private static Map<Integer, ExpandHero> vipLv0_Map =new HashMap<Integer, ExpandHero>();
	private static Map<Integer, ExpandHero> vipLv1_Map =new HashMap<Integer, ExpandHero>();
	private static Map<Integer, ExpandHero> vipLv2_Map =new HashMap<Integer, ExpandHero>();
	private static Map<Integer, ExpandHero> vipLv3_Map =new HashMap<Integer, ExpandHero>();
	private static Map<Integer, ExpandHero> vipLv4_Map =new HashMap<Integer, ExpandHero>();
	private static Map<Integer, ExpandHero> vipLv5_Map =new HashMap<Integer, ExpandHero>();
	private static Map<Integer, ExpandHero> vipLv6_Map =new HashMap<Integer, ExpandHero>();
	private static Map<Integer, ExpandHero> vipLv7_Map =new HashMap<Integer, ExpandHero>();
	private static Map<Integer, ExpandHero> vipLv8_Map =new HashMap<Integer, ExpandHero>();
	private static Map<Integer, ExpandHero> vipLv9_Map =new HashMap<Integer, ExpandHero>();
	private static Map<Integer, ExpandHero> vipLv10_Map =new HashMap<Integer, ExpandHero>();
	private static Map<Integer, ExpandHero> vipLv11_Map =new HashMap<Integer, ExpandHero>();
	
	public static void putExpandHero(ExpandHero expandHero){
		if(expandHero.getVipLv() == 0)
		{
			vipLv0_Map.put(expandHero.getExpandNumber(), expandHero);
		}else if(expandHero.getVipLv() == 1){
			vipLv1_Map.put(expandHero.getExpandNumber(), expandHero);
		}else if (expandHero.getVipLv() == 2) {
			vipLv2_Map.put(expandHero.getExpandNumber(), expandHero);
		}else if (expandHero.getVipLv() == 3) {
			vipLv3_Map.put(expandHero.getExpandNumber(), expandHero);
		}else if (expandHero.getVipLv() == 4) {
			vipLv4_Map.put(expandHero.getExpandNumber(), expandHero);
		}else if (expandHero.getVipLv() == 5) {
			vipLv5_Map.put(expandHero.getExpandNumber(), expandHero);
		}else if (expandHero.getVipLv() == 6) {
			vipLv6_Map.put(expandHero.getExpandNumber(), expandHero);
		}else if (expandHero.getVipLv() == 7) {
			vipLv7_Map.put(expandHero.getExpandNumber(), expandHero);
		}else if (expandHero.getVipLv() == 8) {
			vipLv8_Map.put(expandHero.getExpandNumber(), expandHero);
		}else if (expandHero.getVipLv() == 9) {
			vipLv9_Map.put(expandHero.getExpandNumber(), expandHero);
		}else if (expandHero.getVipLv() == 10) {
			vipLv10_Map.put(expandHero.getExpandNumber(), expandHero);
		}else if (expandHero.getVipLv() == 11) {
			vipLv11_Map.put(expandHero.getExpandNumber(), expandHero);
		}
	}
			
	public static ExpandHero getExpandHero(int vipLv,int expandNumber){
		ExpandHero expandHero = new ExpandHero();
		if(vipLv == 0)
		{
			expandHero = vipLv0_Map.get(expandNumber);
		}else if(vipLv == 1){
			expandHero = vipLv1_Map.get(expandNumber);
		}else if (vipLv == 2) {
			expandHero = vipLv2_Map.get(expandNumber);
		}else if (vipLv == 3) {
			expandHero = vipLv3_Map.get(expandNumber);
		}else if (vipLv == 4) {
			expandHero = vipLv4_Map.get(expandNumber);
		}else if (vipLv == 5) {
			expandHero = vipLv5_Map.get(expandNumber);
		}else if (vipLv == 6) {
			expandHero = vipLv6_Map.get(expandNumber);
		}else if (vipLv == 7) {
			expandHero = vipLv7_Map.get(expandNumber);
		}else if (vipLv == 8) {
			expandHero = vipLv8_Map.get(expandNumber);
		}else if (vipLv == 9) {
			expandHero = vipLv9_Map.get(expandNumber);
		}else if (vipLv == 10) {
			expandHero = vipLv10_Map.get(expandNumber);
		}else if (vipLv == 11) {
			expandHero = vipLv11_Map.get(expandNumber);
		}
		
		return expandHero;
	}
}
