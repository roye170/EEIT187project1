package dao;

import java.util.List;

public interface ServerTableDao {
	
	//新增
	void insertInfo(String name, String country, String location, String type);
	
	//刪除
	void deleteInfo(String name);
	
	//更新
	void updateCountry(String name, String country);
	
	//查詢全部
	List<String> selectInfo(String name, String country, String location, String type);
	
}
