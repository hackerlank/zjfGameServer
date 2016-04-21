package byCodeGame.game.util.comparator;

import java.util.Comparator;

import byCodeGame.game.entity.po.IncomeRank;
import byCodeGame.game.entity.po.Rank;

public class ComparatorIncomeRank implements Comparator<Rank> {
	private ComparatorIncomeRank() {
		// TODO Auto-generated constructor stub
	}

	private static ComparatorIncomeRank _instance = null;

	public static ComparatorIncomeRank getInstance() {
		if (_instance == null) {
			_instance = new ComparatorIncomeRank();
		}
		return _instance;
	}

	@Override
	public int compare(Rank o1, Rank o2) {
		IncomeRank r1 = (IncomeRank) o1;
		IncomeRank r2 = (IncomeRank) o2;

		if (r1.getIncomeNum() != r2.getIncomeNum()) {
			return r2.getIncomeNum() - r1.getIncomeNum();
		}

		return r1.getName().compareTo(r2.getName());
	}
}
