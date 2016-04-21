package byCodeGame.game.db.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.bycode.game.battle.data.ResultData;

public class ResultsConverter implements ResultConverter<ResultData>{
	@Override
	public ResultData convert(ResultSet rs) throws SQLException {
		ResultData resultData = new ResultData();
		resultData.uuid=rs.getString("uuid");
		resultData.time=rs.getString("time");
		resultData.winCamp=rs.getInt("winCamp");
		resultData.attPlayerId=rs.getInt("attPlayerId");
		resultData.defPlayerId=rs.getInt("defPlayerId");
		resultData.attPlayerIsNpc=rs.getInt("attPlayerIsNpc")==0?false:true;
		resultData.defPlayerIsNpc=rs.getInt("defPlayerIsNpc")==0?false:true;
		resultData.report=rs.getString("report");
		resultData.attStars=rs.getInt("attStars");
		resultData.attLost=rs.getInt("attLost");
		resultData.defStars=rs.getInt("defStars");
		resultData.defLost=rs.getInt("defLost");
		resultData.fRound=rs.getInt("fRound");
		resultData.attName = rs.getString("attName");
		resultData.defName = rs.getString("defName");
		return resultData;
	}
}
