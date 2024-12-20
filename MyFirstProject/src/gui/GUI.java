package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import core.CRUD;
import core.IO;

public class GUI implements ActionListener, FocusListener {
	
	JFrame jFrame = new JFrame("世界遺產");
	JPanel jPanel = new JPanel();
	JTable jTable;
	JScrollPane jScrollPane;
	JButton insert;
	JButton delete;
	JButton select;
	JButton update;
	JButton output;
	JTextField fieldOfName;
	JTextField fieldOfCountry;
	JTextField fieldOfLocation;
	JTextField fieldOfType;
	
	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.getTable();
	}
	
	//table
	public void getTable() {
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		CustomTable customTable = new CustomTable();
		//表格
		jTable = new JTable(customTable);
		jScrollPane = new JScrollPane(jTable);
		jScrollPane.setPreferredSize(new Dimension(580, 450));
		jPanel.add(jScrollPane, BorderLayout.NORTH);
		jFrame.add(jPanel);
		//按鈕
		insert = new JButton("新增");
		delete = new JButton("刪除");
		select = new JButton("查詢");
		update = new JButton("更新");
		output = new JButton("匯出");
		insert.setPreferredSize(new Dimension(60,20));
		delete.setPreferredSize(new Dimension(60,20));
		select.setPreferredSize(new Dimension(60,20));
		update.setPreferredSize(new Dimension(60,20));
		output.setPreferredSize(new Dimension(60,20));
		jPanel.add(insert, BorderLayout.EAST);
		jPanel.add(delete, BorderLayout.EAST);
		jPanel.add(select, BorderLayout.EAST);
		jPanel.add(update, BorderLayout.EAST);
		jPanel.add(output, BorderLayout.EAST);
		insert.addActionListener(this);
		delete.addActionListener(this);
		select.addActionListener(this);
		update.addActionListener(this);
		output.addActionListener(this);
		//label
		fieldOfName = new JTextField(50);
		fieldOfName.setText("請輸入遺產名稱");
		fieldOfName.setPreferredSize(new Dimension(60,20));
		jPanel.add(fieldOfName);
		fieldOfCountry = new JTextField(50);
		fieldOfCountry.setText("請輸入遺產所屬國家");
		fieldOfCountry.setPreferredSize(new Dimension(60,20));
		jPanel.add(fieldOfCountry);
		fieldOfLocation = new JTextField(50);
		fieldOfLocation.setText("請輸入遺產所在地");
		fieldOfLocation.setPreferredSize(new Dimension(60,20));
		jPanel.add(fieldOfLocation);
		fieldOfType = new JTextField(50);
		fieldOfType.setText("請輸入遺產種類");
		fieldOfType.setPreferredSize(new Dimension(60,20));
		jPanel.add(fieldOfType);
		fieldOfName.addFocusListener(this);
		fieldOfCountry.addFocusListener(this);
		fieldOfLocation.addFocusListener(this);
		fieldOfType.addFocusListener(this);
		//調整視窗大小
		jFrame.setSize(600,650);
		jFrame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton)e.getSource();
		CRUD crud = new CRUD();
		if (button == insert) {
			String name = fieldOfName.getText();
			String country = fieldOfCountry.getText();
			String location = fieldOfLocation.getText();
			String type = fieldOfType.getText();
			crud.insertInfo(name, country, location, type);
			JOptionPane.showMessageDialog(null, "已成功遞交"+ name+"的遺產申請");
			updateTable();
		} else if (button == delete) {
			String name = fieldOfName.getText();
			crud.deleteInfo(name);
			JOptionPane.showMessageDialog(null, "已成功刪除"+ name+"的資料");
			updateTable();
		} else if (button == select) {
			String name = null;
			String country = null;
			String location = null;
			String type = null;
			if (!fieldOfName.getText().equals("請輸入遺產名稱")) {
				name = fieldOfName.getText();
			}
			if (!fieldOfCountry.getText().equals("請輸入遺產所屬國家")) {
				country = fieldOfCountry.getText();
			}
			if (!fieldOfLocation.getText().equals("請輸入遺產所在地")) {
				location = fieldOfLocation.getText();
			}
			if (!fieldOfType.getText().equals("請輸入遺產種類")) {
				type = fieldOfType.getText();
			}
			CustomTable customtable = new CustomTable();
			customtable.setData(crud.selectInfo(name, country, location, type));
            jTable.setModel(customtable);
            updateTable();
            if (customtable.getRowCount() == 0) {
           	 JOptionPane.showMessageDialog(null,"查無資料");
            }
		} else if (button == update) {
			String name = fieldOfName.getText();
			String country = fieldOfCountry.getText();
			crud.updateCountry(name, country);
			JOptionPane.showMessageDialog(null, "已成功更新"+ name+"的國家");
			updateTable();
		} else if (button == output) {
			JFileChooser jFileChooser = new JFileChooser();
			int showSaveDialog = jFileChooser.showSaveDialog(jFrame);
			if (showSaveDialog == JFileChooser.APPROVE_OPTION) {
				File file = jFileChooser.getSelectedFile();
				String filepath = file.toString();
				IO io = new IO();
				List<String> dataList = convertJTableToList(jTable);
				io.outputFile(dataList, filepath);
				JOptionPane.showMessageDialog(null,"已將資料匯出至" + filepath);
			}
		}
	}
	
	private void updateTable() {
        jTable.repaint();
        jScrollPane.setViewportView(jTable);
    }

	@Override
	public void focusGained(FocusEvent e) {
		JTextField textField = (JTextField) e.getSource();
        if (textField.getText().startsWith("請輸入")) {
            textField.setText("");
        }
	}

	@Override
	public void focusLost(FocusEvent e) {
		 if (fieldOfName.getText().isEmpty()) {
			 fieldOfName.setText("請輸入遺產名稱");
         } else if (fieldOfCountry.getText().isEmpty()) {
        	 fieldOfCountry.setText("請輸入遺產所屬國家");
         } else if (fieldOfLocation.getText().isEmpty()) {
        	 fieldOfLocation.setText("請輸入遺產所在地");
         } else if (fieldOfType.getText().isEmpty()) {
        	 fieldOfType.setText("請輸入遺產種類");
         }
	}
	
	// 將 JTable 的資料轉換為 List<String>
    private static List<String> convertJTableToList(JTable table) {
        List<String> dataList = new ArrayList<>();

        // 獲取 JTable 的資料模型
        AbstractTableModel model = (AbstractTableModel) table.getModel();
        table.getModel();
        // 獲取資料行數和列數
        int rowCount = model.getRowCount();
        int colCount = model.getColumnCount();
        // 遍歷資料行，將每個資料行的資料轉換為 String，並添加到 List 中
        for (int row = 0; row < rowCount; row++) {
            StringBuilder sb = new StringBuilder();
            for (int col = 0; col < colCount; col++) {
                Object value = model.getValueAt(row, col);
                sb.append(value.toString());
                if (col < colCount - 1) {
                    sb.append(", "); // 可以按需求添加分隔符號
                }
            }
            dataList.add(sb.toString());
        }
        return dataList;
    }
}