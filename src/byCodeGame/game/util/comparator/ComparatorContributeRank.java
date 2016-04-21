package byCodeGame.game.util.comparator;

import java.util.Comparator;

import byCodeGame.game.entity.po.ContributeRank;

/**
 * 贡献帮排序
 * @author wcy 2016年1月18日
 *
 */
public class ComparatorContributeRank implements Comparator<ContributeRank>{

	private static ComparatorContributeRank _instance = null;
	public static ComparatorContributeRank getInstance(){
		if(_instance == null){
			_instance = new ComparatorContributeRank();
		}
		return _instance;
	}
	private ComparatorContributeRank() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int compare(ContributeRank o1, ContributeRank o2) {
		int contribute1 = o1.getContribute();
		int contribute2 = o2.getContribute();
		
		if(contribute1!=contribute2){
			return contribute2 - contribute1;
		}
		int time1 = o1.getEnterTime();
		int time2 = o2.getEnterTime();
		
		return time1-time2;
	}

}
