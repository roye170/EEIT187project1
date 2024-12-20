package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import core.CRUD;

public class CustomTable extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CRUD crud = new CRUD();
	public List<String> data = crud.selectInfo(null,null,null,null);
	
	public void setData(List<String> data) {
		this.data = data;
	}

	private String[] columns = {"遺產名稱","所屬國家","所在地","入選年份","遺產種類"};
 	
	public CustomTable() {
		
	}
	
	
	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String row =  data.get(rowIndex);
		String[] column = row.split(",");
		switch (columnIndex) {
		case 0:
			return column[0];
		case 1:
			return column[1];
		case 2:
			return column[2];
		case 3:
			return column[3];
		case 4:
			return column[4];
		}
		return column[columnIndex];
	}
	
	@Override
	public String getColumnName(int column) {
		return columns[column];
	}

}
