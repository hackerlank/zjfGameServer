package byCodeGame.game.entity.bo;


public class Arena {
	private int rank;
	private String lv;
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getLv() {
		return lv;
	}
	public void setLv(String lv) {
		this.lv = lv;
	}
	
//	private int id;
//	/** 列兵	 */
//	private String lv1;
//	private Set<Integer> lv1Set = new HashSet<Integer>();
//	private Lock lv1Lock = new ReentrantLock();
//	/** 伍长	 */
//	private String lv2;
//	private Set<Integer> lv2Set = new HashSet<Integer>();
//	private Lock lv2Lock = new ReentrantLock();
//	/** 什长	 */
//	private String lv3;
//	private Set<Integer> lv3Set = new HashSet<Integer>();
//	private Lock lv3Lock = new ReentrantLock();
//	/** 都伯	 */
//	private String lv4;
//	private Set<Integer> lv4Set = new HashSet<Integer>();
//	private Lock lv4Lock = new ReentrantLock();
//	/** 屯将	 */
//	private String lv5;
//	private Set<Integer> lv5Set = new HashSet<Integer>();
//	private Lock lv5Lock = new ReentrantLock();
//	/** 曲长	 */
//	private String lv6;
//	private Set<Integer> lv6Set = new HashSet<Integer>();
//	private Lock lv6Lock = new ReentrantLock();
//	
//	
//	public int getId() {
//		return id;
//	}
//	public void setId(int id) {
//		this.id = id;
//	}
//	public Set<Integer> getLv1Set() {
//		return lv1Set;
//	}
//	public void setLv1Set(Set<Integer> lv1Set) {
//		this.lv1Set = lv1Set;
//	}
//	public Set<Integer> getLv2Set() {
//		return lv2Set;
//	}
//	public void setLv2Set(Set<Integer> lv2Set) {
//		this.lv2Set = lv2Set;
//	}
//	public Set<Integer> getLv3Set() {
//		return lv3Set;
//	}
//	public void setLv3Set(Set<Integer> lv3Set) {
//		this.lv3Set = lv3Set;
//	}
//	public Set<Integer> getLv4Set() {
//		return lv4Set;
//	}
//	public void setLv4Set(Set<Integer> lv4Set) {
//		this.lv4Set = lv4Set;
//	}
//	public Set<Integer> getLv5Set() {
//		return lv5Set;
//	}
//	public void setLv5Set(Set<Integer> lv5Set) {
//		this.lv5Set = lv5Set;
//	}
//	public Set<Integer> getLv6Set() {
//		return lv6Set;
//	}
//	public void setLv6Set(Set<Integer> lv6Set) {
//		this.lv6Set = lv6Set;
//	}
//	public String getLv1() {
//		StringBuilder sb = new StringBuilder();
//		for(Integer id : lv1Set){
//			sb.append(id).append(",");
//		}
//		this.lv1 = sb.toString();
//		return lv1;
//	}
//	public void setLv1(String lv1) {
//		if(lv1 != null && !lv1.equals("")){
//			String[] strs = lv1.split(",");
//			for(String str : strs){
//				lv1Set.add(Integer.valueOf(str));
//			}
//		}
//	}
//	public String getLv2() {
//		StringBuilder sb = new StringBuilder();
//		for(Integer id : lv2Set){
//			sb.append(id).append(",");
//		}
//		this.lv2 = sb.toString();
//		return lv2;
//	}
//	public void setLv2(String lv2) {
//		if(lv2 != null && !lv2.equals("")){
//			String[] strs = lv2.split(",");
//			for(String str : strs){
//				lv2Set.add(Integer.valueOf(str));
//			}
//		}
//	}
//	public String getLv3() {
//		StringBuilder sb = new StringBuilder();
//		for(Integer id : lv3Set){
//			sb.append(id).append(",");
//		}
//		this.lv3 = sb.toString();
//		return lv3;
//	}
//	public void setLv3(String lv3) {
//		if(lv3 != null && !lv3.equals("")){
//			String[] strs = lv3.split(",");
//			for(String str : strs){
//				lv3Set.add(Integer.valueOf(str));
//			}
//		}
//	}
//	public String getLv4() {
//		StringBuilder sb = new StringBuilder();
//		for(Integer id : lv4Set){
//			sb.append(id).append(",");
//		}
//		this.lv4 = sb.toString();
//		return lv4;
//	}
//	public void setLv4(String lv4) {
//		if(lv4 != null && !lv4.equals("")){
//			String[] strs = lv4.split(",");
//			for(String str : strs){
//				lv4Set.add(Integer.valueOf(str));
//			}
//		}
//	}
//	public String getLv5() {
//		StringBuilder sb = new StringBuilder();
//		for(Integer id : lv5Set){
//			sb.append(id).append(",");
//		}
//		this.lv5 = sb.toString();
//		return lv5;
//	}
//	public void setLv5(String lv5) {
//		if(lv5 != null && !lv5.equals("")){
//			String[] strs = lv5.split(",");
//			for(String str : strs){
//				lv5Set.add(Integer.valueOf(str));
//			}
//		}
//	}
//	public String getLv6() {
//		StringBuilder sb = new StringBuilder();
//		for(Integer id : lv6Set){
//			sb.append(id).append(",");
//		}
//		this.lv6 = sb.toString();
//		return lv6;
//	}
//	public void setLv6(String lv6) {
//		if(lv6 != null && !lv6.equals("")){
//			String[] strs = lv6.split(",");
//			for(String str : strs){
//				lv6Set.add(Integer.valueOf(str));
//			}
//		}
//	}
//	public Lock getLv1Lock() {
//		return lv1Lock;
//	}
//	public void setLv1Lock(Lock lv1Lock) {
//		this.lv1Lock = lv1Lock;
//	}
//	public Lock getLv2Lock() {
//		return lv2Lock;
//	}
//	public void setLv2Lock(Lock lv2Lock) {
//		this.lv2Lock = lv2Lock;
//	}
//	public Lock getLv3Lock() {
//		return lv3Lock;
//	}
//	public void setLv3Lock(Lock lv3Lock) {
//		this.lv3Lock = lv3Lock;
//	}
//	public Lock getLv4Lock() {
//		return lv4Lock;
//	}
//	public void setLv4Lock(Lock lv4Lock) {
//		this.lv4Lock = lv4Lock;
//	}
//	public Lock getLv5Lock() {
//		return lv5Lock;
//	}
//	public void setLv5Lock(Lock lv5Lock) {
//		this.lv5Lock = lv5Lock;
//	}
//	public Lock getLv6Lock() {
//		return lv6Lock;
//	}
//	public void setLv6Lock(Lock lv6Lock) {
//		this.lv6Lock = lv6Lock;
//	}
}
