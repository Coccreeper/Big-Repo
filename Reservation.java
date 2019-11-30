/**
	This is The Code That Genrate The Frame That contains The Clients
	And The Operation That can be done on The Clients Data
*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import java.util.Vector;

class Reservation extends JFrame{

	//TableModel For View Data
	DefaultTableModel tableModel = new DefaultTableModel();
	JTable table ;
	JTextField searchField = new JTextField(15);
	String filePath = "client data/res";
	//Create Buttons For : Add , Delete , Save ,Clear , Restore , And Back to Main Frame
	JButton //addRowButton = new JButton("Add New Row"),
			addColumnButton = new JButton("≈÷«›… ⁄„Êœ"),
			//deleteRowButton = new JButton("Delete Selected Row"),
			deleteColumnButton = new JButton("Õ–› «·⁄„Êœ"),
			saveButton = new JButton("Õ›Ÿ"),
			clearButton = new JButton("„”Õ"),
			restoreButton = new JButton("≈⁄«œ… «·»Ì«‰« "),
			back = new JButton("—ÃÊ⁄");
	//Constructor
	public Reservation(){
		super("«·⁄„·«¡"); // set Frame Title
		setIconImage(new ImageIcon("images/client.png").getImage());// set Frame Image
		buttons();//Set Cursor in Cursor shape when it rover above the buutons
		readFile(filePath);// Get Data from file
		table = new JTable(tableModel);
		table.setAutoCreateRowSorter(true);//To make the table enable to sort
		combo();// To set The ComboBox on The Categories Column
		searchField.setBorder(BorderFactory.createCompoundBorder(
		                            searchField.getBorder(),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		//*************************Panel 1*************************

		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(1,2));
		//panel1.add(addRowButton);
		panel1.add(addColumnButton);
		//panel1.add(deleteRowButton);
		panel1.add(deleteColumnButton);

		//*************************Panel 2*************************

		JPanel panel2 = new JPanel();
		panel2.add(saveButton);
		panel2.add(clearButton);
		panel2.add(restoreButton);

		//*************************Panel 3*************************

		JPanel panel3 = new JPanel();
		panel3.setLayout(new BorderLayout());
		panel3.add(panel1,BorderLayout.SOUTH);
		panel3.add(panel2,BorderLayout.CENTER);

		add(new JScrollPane(table),BorderLayout.CENTER);
		add(panel3,BorderLayout.SOUTH);


		/*addRowButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(table.getSelectedRow()>=0)
					tableModel.insertRow(table.getSelectedRow(),new Vector());
				else
					tableModel.addRow(new Vector());
			}
		});*/

		addColumnButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String colName = JOptionPane.showInputDialog("«”„ «·⁄„Êœ «·ÃœÌœ :");
				tableModel.addColumn(colName,new Vector());
				combo();
			}
		});

		table.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				if(e.getClickCount()==2){
					BookFrame frame = new BookFrame();
					for(int i=0;i<table.getColumnCount();i++){
						if(!table.getValueAt(table.getSelectedRow(),i).toString().equals("")){
							frame.addLabel(table.getColumnName(i));
							frame.addUneditableField((String)table.getValueAt(table.getSelectedRow(),i));
						}
					}
					frame.update();
					frame.setTable(tableModel);
					frame.setJTable(table);
					frame.addOkButton();
					frame.setAlwaysOnTop(true);
					frame.setVisible(true);
				}
			}
		});// To view Client Data when Double Click on the table

		/*deleteRowButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				deleteRow();
			}
		});*/

		deleteColumnButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(table.getSelectedColumn()>=0){
					TableColumnModel columnModel = table.getColumnModel();
					TableColumn tableColumn = columnModel.getColumn(table.getSelectedColumn());
					columnModel.removeColumn(tableColumn);
				}
			}
		});

		saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				save(filePath);
			}
		});

		clearButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tableModel.setRowCount(0);
			}
		});

		restoreButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				readFile(filePath);
				combo();
			}
		});

		//*************************Panel 4*************************

		JPanel panel4 = new JPanel();
		panel4.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel4.add(back);
			back.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					setVisible(false);
					new Main();
				}
		});
		JLabel searchType = new JLabel("»ÕÀ ⁄»— :");
		JLabel sp = new JLabel("                                                                                                                                                                             ");
		JComboBox searchBox = new JComboBox(new String[]{
						"«”„ «·⁄„Ì·","«”„ «·ﬂ «»","«· ’«‰Ì›"});
		searchBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
		JLabel searchLabel = new JLabel("»ÕÀ :");
		panel4.add(sp);
		panel4.add(searchField);
		panel4.add(searchLabel);
		panel4.add(searchBox);
		panel4.add(searchType);
		searchField.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e){
				readFile(filePath);
				String choose = searchField.getText();
				String type = searchBox.getSelectedItem().toString();
				int colNum = 0;
				if(type.equals("«”„ «·ﬂ «»"))
					colNum = 4;
				else if(type.equals("«”„ «·⁄„Ì·"))
					colNum = 0;
				else if(type.equals("«· ’«‰Ì›"))
					colNum = 3;
				if(!choose.equals("")){
					int rows = table.getRowCount();
					for(int i=0;i<rows;i++){
						if(!table.getValueAt(i,colNum).toString().contains(choose)){
							tableModel.removeRow(i);
							rows = table.getRowCount();
							i=-1;
						}
					}
				}
				combo();
			}
		});

		add(panel4,BorderLayout.NORTH);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//*************************Panel 5*************************

		JPanel panel5 = new JPanel();
		Dimension dim = panel5.getPreferredSize();
		dim.width = 120;
		panel5.setPreferredSize(dim);
		JButton addClient = new JButton("≈÷«›… ⁄„Ì·");
		addClient.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tableModel.addRow(new Vector());
				BookFrame frame = new BookFrame();
				for(int i=0;i<table.getColumnCount();i++){
						frame.addLabel(table.getColumnName(i));
						frame.addField((String)table.getValueAt(table.getRowCount()-1,i));
				}
				frame.update();
				frame.setTable(tableModel);
				frame.setJTable(table);
				frame.addOk2Button(filePath);
				frame.setAlwaysOnTop(true);
				frame.setVisible(true);
			}
		});
		panel5.add(addClient);
		JButton editClient = new JButton(" ⁄œÌ· «·⁄„Ì·");
		editClient.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				edit();
			}
		});
		panel5.add(editClient);
		JButton deleteClient = new JButton("Õ–› «·⁄„Ì·");
		deleteClient.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				deleteRow();
			}
		});
		panel5.add(deleteClient);
		JButton viewClient = new JButton("⁄—÷ »Ì«‰«  «·⁄„Ì·");
			viewClient.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if(table.getSelectedRow()>=0){
						BookFrame frame = new BookFrame();
							for(int i=0;i<table.getColumnCount();i++){
								if(!table.getValueAt(table.getSelectedRow(),i).toString().equals("")){
									frame.addLabel(table.getColumnName(i));
									frame.addUneditableField((String)table.getValueAt(table.getSelectedRow(),i));
								}
							}
						frame.update();
						frame.setTable(tableModel);
						frame.setJTable(table);
						frame.addOkButton();
						frame.setAlwaysOnTop(true);
						frame.setVisible(true);
					}
				}
			});
		panel5.add(viewClient);
		add(panel5,BorderLayout.WEST);
		addClient.setCursor(new Cursor(Cursor.HAND_CURSOR));
		editClient.setCursor(new Cursor(Cursor.HAND_CURSOR));
		deleteClient.setCursor(new Cursor(Cursor.HAND_CURSOR));
		viewClient.setCursor(new Cursor(Cursor.HAND_CURSOR));

		setJMenuBar(getMyMenuBar());
		setSize(1200,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(3);
		setVisible(true);
	}
	Vector getColumnNames(){
		Vector<String> colNames = new Vector<String>();
		for(int i=0;i<table.getColumnCount();i++)
			colNames.add(table.getColumnName(i));
		return colNames;
	}
	void buttons(){
		addColumnButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		deleteColumnButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		//addRowButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		//deleteRowButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		restoreButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		back.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	public void readFile(String file){
		try{
			ObjectInputStream input;
			if(file.endsWith(".dat"))
				input = new ObjectInputStream(new FileInputStream(file));
			else
				input = new ObjectInputStream(new FileInputStream(file+".dat"));
			Vector rowData = (Vector)input.readObject();
			Vector colNames = (Vector)input.readObject();
			tableModel.setDataVector(rowData,colNames);
			input.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void deleteRow(){
		if(table.getSelectedRow()>=0)
			tableModel.removeRow(table.getSelectedRow());
	}
	public void save(String file){// Save Data
		try{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file+".dat"));
			out.writeObject(tableModel.getDataVector());
			out.writeObject(getColumnNames());
			out.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void edit(){
		if(table.getSelectedRow()>-1){
			BookFrame frame = new BookFrame();
			for(int i=0;i<table.getColumnCount();i++){
					frame.addLabel(table.getColumnName(i));
					frame.addField((String)table.getValueAt(table.getSelectedRow(),i));
			}
			frame.update();
			frame.setTable(tableModel);
			frame.setJTable(table);
			frame.addOkButton(filePath);
			frame.setAlwaysOnTop(true);
			frame.setVisible(true);
		}
	}
	public void combo(){
		JComboBox comboBox = new JComboBox(new String[]{
				"”·›Ì…",
				"ÕœÌÀ", "’Ê›Ì…",
			"√Œ—Ï"});
		TableColumn categories = table.getColumn("Categories");
		categories.setCellEditor(new DefaultCellEditor(comboBox));
	}
	public void open(){
		JFileChooser file = new JFileChooser();
		file.setSelectedFile(new File("C:/Users/Abdalla/Desktop/Œ«’ »«·Ã«„⁄…/Library2 - Final - Copy/client data"));
		int option = file.showOpenDialog(null);
		if(option == JFileChooser.APPROVE_OPTION){
			File f = file.getSelectedFile();
			String path = f.getAbsolutePath();
			readFile(path);
		}
	}
	JMenuBar getMyMenuBar(){
		JMenuBar menuBar = new JMenuBar();

		JMenu file = new JMenu("„·›");
		JMenuItem open = new JMenuItem("› Õ");
		JMenuItem save = new JMenuItem("Õ›Ÿ");
		JMenuItem saveAs = new JMenuItem("Õ›Ÿ ›Ì");
		JMenuItem close = new JMenuItem("Œ—ÊÃ");

		open.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				open();
			}
		});

		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				save(filePath);
			}
		});

		saveAs.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String file = JOptionPane.showInputDialog(null,"«œŒ· «”„ «·„·›");
				filePath = "client data/"+file;
				save(filePath);
			}
		});

		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		file.add(open);
		file.add(new JSeparator());
		file.add(save);
		file.add(saveAs);
		file.add(new JSeparator());
		file.add(close);
		menuBar.add(file);

		JMenu view = new JMenu("⁄—÷");
		JMenu tableSetting = new JMenu("«·Ê«‰ «·ÃœÊ·");
		JMenuItem tableForeground = new JMenuItem("·Ê‰ «·Œÿ ›Ì «·ÃœÊ·");
		tableForeground.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				table.setForeground(getMyColor());
			}
		});
		tableSetting.add(tableForeground);
		JMenuItem tableBackground = new JMenuItem("Œ·›Ì… «·ÃœÊ·");
		tableBackground.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				table.setBackground(getMyColor());
			}
		});
		tableSetting.add(tableBackground);
		tableSetting.add(new JSeparator());
		JMenuItem tableSelectForeground = new JMenuItem("·Ê‰ «·Œÿ ⁄‰œ «· ÕœÌœ ›Ì «·ÃœÊ·");
		tableSelectForeground.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				table.setSelectionForeground(getMyColor());
			}
		});
		tableSetting.add(tableSelectForeground);
		JMenuItem tableSelectBackground = new JMenuItem("·Ê‰ Œ·›Ì… «· ÕœÌœ ›Ì «·ÃœÊ·");
		tableSelectBackground.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				table.setSelectionBackground(getMyColor());
			}
		});
		tableSetting.add(tableSelectBackground);
		view.add(tableSetting);

		JMenu searchSetting = new JMenu("«·Ê«‰ „—»⁄ «·»ÕÀ");
		JMenuItem searchForeground = new JMenuItem("·Ê‰ «·Œÿ ›Ì „—»⁄ «·»ÕÀ");
		searchForeground.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				searchField.setForeground(getMyColor());
			}
		});
		searchSetting.add(searchForeground);
		JMenuItem searchBackground = new JMenuItem("·Ê‰ Œ·›Ì… „—»⁄ «·»ÕÀ");
		searchBackground.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				searchField.setBackground(getMyColor());
			}
		});
		searchSetting.add(searchBackground);
		searchSetting.add(new JSeparator());
		JMenuItem searchCaret = new JMenuItem("·Ê‰ «·„ƒ‘— ›Ì „—»⁄ «·»ÕÀ");
		searchCaret.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				searchField.setCaretColor(getMyColor());
			}
		});
		searchSetting.add(searchCaret);

		view.add(tableSetting);
		view.add(searchSetting);
		menuBar.add(view);
		return menuBar;
	}
	public Color getMyColor(){
		Color color = Color.WHITE;
		color = JColorChooser.showDialog(null, "«Œ — ·Ê‰«", color );
		return color;
	}

	public static void main(String[] args){
		new Reservation();
	}
}