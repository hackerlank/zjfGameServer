package byCodeGame.game.entity.file;

public class StrengthenConfig {
	private int lv;
	private int pt;
	private int yx;
	private int jl;
	private int ss;
	private int cs;
	private int sq;
	public int getLv() {
		return lv;
	}
	public void setLv(int lv) {
		this.lv = lv;
	}
	public int getPt() {
		return pt;
	}
	public void setPt(int pt) {
		this.pt = pt;
	}
	public int getYx() {
		return yx;
	}
	public void setYx(int yx) {
		this.yx = yx;
	}
	public int getJl() {
		return jl;
	}
	public void setJl(int jl) {
		this.jl = jl;
	}
	public int getSs() {
		return ss;
	}
	public void setSs(int ss) {
		this.ss = ss;
	}
	
	
	public int getCost(int i)
	{
		if(i == 1)
		{
			return pt;
		}else if (i == 2) {
			return yx;
		}else if (i == 3) {
			return jl;
		}else if (i == 4) {
			return ss;
		}else if (i == 5) {
			return cs;
		}else if (i == 6) {
			return sq;
		}
		
		return 0;
	}
	public int getCs() {
		return cs;
	}
	public void setCs(int cs) {
		this.cs = cs;
	}
	public int getSq() {
		return sq;
	}
	public void setSq(int sq) {
		this.sq = sq;
	}
}
