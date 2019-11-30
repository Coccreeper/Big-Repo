import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
class BookFrame extends JFrame{
	DefaultTableModel table ;
	JTable jtable;
	ArrayList<JLabel> labels = new ArrayList<JLabel>();
	ArrayList<JTextField> textFields = new ArrayList<JTextField>();
	GridBagConstraints gc = new GridBagConstraints();
	JButton ok = new JButton("OK");

	BookFrame(){
		super("Frame");
		ok.setCursor(new Cursor(Cursor.HAND_CURSOR));
		setLayout(new GridBagLayout());

		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.insets = new Insets(0,0,0,5);
		gc.fill = GridBagConstraints.NONE;

		setSize(400,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	public void save(String file){
		try{
			ObjectOutputStream out;
			if(file.endsWith("dat"))
				out = new ObjectOutputStream(new FileOutputStream(file));
			else
				out = new ObjectOutputStream(new FileOutputStream(file+".dat"));
			out.writeObject(table.getDataVector());
			out.writeObject(getColumnNames());
			out.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	Vector getColumnNames(){
		Vector<String> colNames = new Vector<String>();
		for(int i=0;i<table.getColumnCount();i++)
			colNames.add(table.getColumnName(i));
		return colNames;
	}

	void addLabel(String name){
		labels.add(new JLabel(name+" : "));
	}

	void addField(String text){
		textFields.add(new JTextField(text,15));
	}

	void addUneditableField(String text){
		JTextField field = new JTextField(text,15);
		field.setEnabled(false);
		textFields.add(field);
	}

	void addOkButton(){
		gc.gridx = 1;
		gc.gridy++;
		gc.weighty = 2;
		gc.anchor = GridBagConstraints.LINE_START;
		ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setVisible(false);
			}
		});
		add(ok,gc);
	}

	void addOkButton(String name){
		gc.gridx = 1;
		gc.gridy++;
		gc.weighty = 2;
		gc.anchor = GridBagConstraints.LINE_START;
		ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				for(int i=0;i<table.getColumnCount();i++){
					table.setValueAt((String)textFields.get(i).getText(),jtable.getSelectedRow(),i);
				}
				save(name);
				setVisible(false);
			}
		});
		add(ok,gc);
	}

	void addOk2Button(String name){
		gc.gridx = 1;
		gc.gridy++;
		gc.weighty = 2;
		gc.anchor = GridBagConstraints.LINE_START;
		ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				for(int i=0;i<table.getColumnCount();i++){
					table.setValueAt((String)textFields.get(i).getText(),jtable.getRowCount()-1,i);
				}
				save(name);
				setVisible(false);
			}
		});
		add(ok,gc);
	}

	void setJTable(JTable table){
		this.jtable = table;
	}

	void setTable(DefaultTableModel table){
		this.table = table;
	}

	void update(){
		for(int i=0;i<labels.size();i++){
			gc.gridx = 0;
			gc.anchor = GridBagConstraints.LINE_END;
			add(labels.get(i),gc);
			gc.gridx = 1;
			gc.anchor = GridBagConstraints.LINE_START;
			add(textFields.get(i),gc);
			gc.gridy++;
		}
	}

	public static void main(String[] args){
		new BookFrame();
	}
}