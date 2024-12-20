package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ServerTableDao;
import utils.JDBCutil;

public class CRUD implements ServerTableDao{
	static InputStreamReader inputStreamReader = null;
	static BufferedReader reader = null;
	
	//掃描使用者輸入的資料
	public String testInputStream() {
		String str = "";
		try {
			inputStreamReader = new InputStreamReader(System.in);
			reader = new BufferedReader(inputStreamReader);
			str = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	//關閉流
	public void closeStream() {
		if (inputStreamReader != null) {
			try {
				inputStreamReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override //新增資料
	public void insertInfo(String name, String country, String location, String type) {
		String sql = "INSERT INTO firstproject(name, country, location, type) VALUES(?,?,?,?)";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = JDBCutil.getConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			statement.setString(2, country);
			statement.setString(3, location);
			statement.setString(4, type);
			statement.execute();
			System.out.println("已成功遞交\""+name+"\"的申請");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCutil.closeResource(connection, statement);
		}
	}

	@Override //刪除資料
	public void deleteInfo(String name) {
		String sql = "DELETE FROM firstproject WHERE name = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = JDBCutil.getConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			statement.execute();
			System.out.println("已成功刪除\""+name+"\"的資料");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCutil.closeResource(connection, statement);
		}
	}

	@Override //更新資料
	public void updateCountry(String name, String country) {
		String sql = "UPDATE firstproject SET country = ? WHERE name = ?";
		String check = "SELECT * FROM firstproject WHERE name = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		PreparedStatement checkPreparedStatement = null;
		ResultSet checkresult = null;
		try {
			connection = JDBCutil.getConnection();
			statement = connection.prepareStatement(sql);
			checkPreparedStatement = connection.prepareStatement(check);
			checkPreparedStatement.setString(1, name);
			checkresult = checkPreparedStatement.executeQuery();
			statement.setString(1, country);
			statement.setString(2, name);
			if (checkresult.next()) {
				statement.execute();
				System.out.println("已成功更新\""+name+"\"的國家為\""+country+"\"");
			} else {
				System.out.println("查無name為: "+name+" 的資料");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCutil.closeResource(connection, statement);
		}
	}

	@Override //搜尋資料
	public List<String> selectInfo(String name, String country, String location, String type) {
		String sql = "SELECT name,country, location, YEAR(year) AS year,type FROM firstproject";
		//判斷有無此變數，組合sql語句
		int count = 0;
		if (name != null) {
			sql = sql + " WHERE name = '" + name + "'";
			count++;
		}
		if (country != null) {
			if (count == 0) {
				sql = sql + " WHERE country = '" + country + "'";
				count++;
			}else {
				sql = sql + " AND country = '" + country + "'";
			}
		}
		if (location != null) {
			if (count == 0) {
				sql = sql + " WHERE location = '" + location + "'";
				count++;
			}else {
				sql = sql + " AND location = '" + location + "'";
			}
		}
		if (type != null) {
			if (count == 0) {
				sql = sql + " WHERE type = '" + type + "'";
			}else {
				sql = sql + " AND type = '" + type + "'";
			}
		}
		//連接資料庫取得資料
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultset = null;
		List<String> List = new ArrayList<String>();
		try {
			connection = JDBCutil.getConnection();
			statement = connection.prepareStatement(sql);
			resultset = statement.executeQuery();
			while (resultset.next()) {
				String listname = resultset.getString("name");
							//"地名: " + resultset.getString("name");
				String listcountry = resultset.getString("country");
							//"國家: " + resultset.getString("country");
				String listlocation = resultset.getString("location");
							//"所在地: " + resultset.getString("location");
				String listyear = resultset.getString("year");
							//"入選年份: " + resultset.getString("year");
				String listtype = resultset.getString("type");
							//"遺產種類: " + resultset.getString("type");
				List.add(listname+","+listcountry+","+listlocation+","+listyear+","+listtype);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCutil.closeResource(connection, statement,resultset);
		}
		return List;
	}
	
}
