package local.database.mysql;

import java.sql.ResultSet;

public class MysqlQuery
{

	public ResultSet query()
	{
		String stmt = "SELECT `m`.`id` AS `ID`, "
				+ "`m`.`title` AS `Title`, "
				+ "`m`.`path` AS `Path`, "
				+ "`ge`.`genre` AS `Genre`, "
				+ "`gr`.`group` AS `Group`, "
				+ "`m`.`series_num` AS `Series Number`, "
				+ "`t`.`genre` AS `Type` "
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
