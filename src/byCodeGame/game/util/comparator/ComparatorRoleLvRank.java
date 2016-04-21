package byCodeGame.game.util.comparator;

import java.util.Comparator;

import byCodeGame.game.entity.po.Rank;
import byCodeGame.game.entity.po.RoleLvRank;

public class ComparatorRoleLvRank implements Comparator<Rank> {

	private static ComparatorRoleLvRank _instance = null;

	private ComparatorRoleLvRank() {
		// TODO Auto-generated constructor stub
	}

	public static ComparatorRoleLvRank getInstance() {
		if (_instance == null)
			_instance = new ComparatorRoleLvRank();
		return _instance;
	}

	@Override
	public int compare(Rank o1, Rank o2) {
		RoleLvRank r1 = (RoleLvRank) o1;
		RoleLvRank r2 = (RoleLvRank) o2;
		if (r1.getRoleLv() != r2.getRoleLv()) {
			return r2.getRoleLv() - r1.getRoleLv();
		}
		return r1.getName().compareTo(r2.getName());
	}

}
