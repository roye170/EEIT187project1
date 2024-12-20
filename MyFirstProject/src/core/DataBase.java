package core;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import utils.JDBCutil;

public class DataBase {

	public void createTable() {
		String sql = "CREATE TABLE firstproject(name NVARCHAR(25), country NVARCHAR(100),"
				   + "location NVARCHAR(100), year DATE, type NVARCHAR(10))";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = JDBCutil.getConnection();
			statement = connection.prepareStatement(sql);
			statement.execute();
			System.out.println("已新增表格");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCutil.closeResource(connection, statement);
		}
	}
	
	//將csv檔的資料插入到資料表
	public void insertInto() throws ParseException {
		String sql = "INSERT INTO firstproject VALUES(?,?,?,?,?)";
		String sourcePath = "C:\\MyFirstProject\\source.csv";
		Data aboutData = new Data();
		ArrayList<String> arrayList = new ArrayList<String>(aboutData.getData(sourcePath));
		Connection connection = JDBCutil.getConnection();
		PreparedStatement statement = null;
		String name = null;
		String country = null;
		String location = null;
		Date sqlDate= null;
		String type = null;
		try {
			int count = 1;
			statement = connection.prepareStatement(sql);
			while (arrayList.get(count) != null) {
					String row = arrayList.get(count);
					String[] filed = row.split(",");
					name = filed[0];
					country = filed[1];
					location = filed[2];
					String year = filed[3];
					sqlDate = Date.valueOf(year + "-01-01");
					type = filed[4];
					statement.setString(1, name);
					statement.setString(2, country);
					statement.setString(3, location);
					statement.setDate(4, sqlDate);
					statement.setString(5, type);
					statement.execute();
					count++;
				}
			System.out.println("資料新增完成");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("新增: "+name+"@"+country+"@"+location+"@"+sqlDate+"@"+type+" 失敗");
		} finally {
			JDBCutil.closeResource(connection, statement);
		}
	}
	
}
