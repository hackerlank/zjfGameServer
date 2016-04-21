package byCodeGame.game.util.comparator;

import java.util.Comparator;

/**
 * 世界等级排序
 * 
 * @author wcy 2016年2月22日
 *
 */
public class ComparatorWorldLv implements Comparator<Integer> {
	private static ComparatorWorldLv _instance = null;

	private ComparatorWorldLv() {

	}

	public static ComparatorWorldLv getInstance() {
		if (_instance == null) {
			_instance = new ComparatorWorldLv();
		}
		return _instance;
	}

	@Override
	public int compare(Integer o1, Integer o2) {
		return o2 - o1;
	}
}
