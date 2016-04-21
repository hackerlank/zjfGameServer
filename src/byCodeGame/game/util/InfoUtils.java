package byCodeGame.game.util;

import byCodeGame.game.entity.bo.Prop;
import byCodeGame.game.entity.file.EquipConfig;

/**
 * 将装备信息转换成字符串
 * @author win7n
 *
 */
public class InfoUtils {
	
	public static String getInfoEquip(EquipConfig config,Prop prop)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(config.toString()).append(",").append(prop.getLv());
		
		return sb.toString();
	}
}
