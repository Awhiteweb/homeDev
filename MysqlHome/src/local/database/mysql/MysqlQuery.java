package local.database.mysql;

import java.sql.ResultSet;
import local.dto.VideoProvider;

public class MysqlQuery
{

	public ResultSet query()
	{
		String stmt = "SELECT `m`.`id` AS `" + VideoProider.ID + "`, "
				+ "`m`.`title` AS `" + VideoProider.TITLE + "`, "
				+ "`m`.`path` AS `" + VideoProider.LOCATION + "`, "
				+ "`ge`.`genre` AS `" + VideoProider.GENRE + "`, "
				+ "`gr`.`group` AS `" + VideoProider.GROUP + "`, "
				+ "`m`.`series_num` AS `" + VideoProider.SERIES_N + "`, "
				+ "`m`.`season_num` AS `" + VideoProider.SEASON_N + "`, "
				+ "`t`.`genre` AS `" + VideoProider.TYPE + "` "
				+ "FROM `main` AS `m` "
				+ "LEFT JOIN `genres` AS `ge` "
				+ "ON `m`.`genre` = `ge`.`id` "
				+ "LEFT JOIN `groups` AS `gr` "
				+ "ON `m`.`group` = `gr`.`id` "
				+ "LEFT JOIN `type` AS `t` "
				+ "ON `m`.`type` = `t`.`id` "
				+ "WHERE `m`.`type` = 1;";
		
		
		
		
		ResultSet result = null;
		return result;
	}
}
