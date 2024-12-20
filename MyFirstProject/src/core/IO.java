package core;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class IO {
	
	public static void main(String[] args) {
	}
	
	//將查詢資料匯出
	public void outputFile(List<String> list , String filepath) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(filepath);
			int i = 0;
			int listsize = list.size();
			while (i < listsize) {
				String row = list.get(i);
				writer.append(row + "\n");
				i++;
			}
			System.out.println("已將資料匯出至" + filepath);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
