package byCodeGame.game.util.comparator;

import java.util.Comparator;

import byCodeGame.game.entity.po.KillRank;
import byCodeGame.game.entity.po.Rank;

public class ComparatorKillRank implements Comparator<Rank> {
	private static ComparatorKillRank _instance = null;

	private ComparatorKillRank() {

	}

	public static ComparatorKillRank getInstance() {
		if (_instance == null)
			_instance = new ComparatorKillRank();
		return _instance;
	}

	@Override
	public int compare(Rank o1, Rank o2) {
		KillRank r1 = (KillRank) o1;
		KillRank r2 = (KillRank) o2;
		if (r1.getValue() != r2.getValue()) {
			return r2.getValue() - r1.getValue();
		}

		if (r1.getEnterTime() != r2.getEnterTime())
			return r1.getEnterTime() - r2.getEnterTime();
		return r1.getName().compareTo(r2.getName());
	}

}
