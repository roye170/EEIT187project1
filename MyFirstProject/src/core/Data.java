package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Data {
	
	public static void main(String[] args) {
		Data aboutData = new Data();
		aboutData.getData("C:\\MyFirstProject\\source.csv");
	}
	
	//讀取csv
	public List<String> getData(String sourcePath){
		List<String> arrayList = new ArrayList<String>();
		FileInputStream fileInputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileInputStream = new FileInputStream(new File(sourcePath));
			inputStreamReader = new InputStreamReader(fileInputStream, "utf-8");
			bufferedReader = new BufferedReader(inputStreamReader);
			String content = "";
			while (bufferedReader.ready()) {
				content = bufferedReader.readLine();
				arrayList.add(content);
				System.out.println(content);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileInputStream.close();
				inputStreamReader.close();
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return arrayList;
	}
}
