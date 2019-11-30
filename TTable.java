/**
	This is The Code That Genrate The Frame That contains The books
	And The Operation That can be done on The Books Data
*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import java.util.Vector;

class TTable extends JFrame{
	//TableModel For View Data
	DefaultTableModel tableModel = new DefaultTableModel();
	JTable table ;
	JTextField searchField = new JTextField(15);
	String filePath = "books data/table";

	//ComboBox For Search
	JComboBox catBox = new JComboBox(new String[]{
				"«·ﬂ·", "”·›Ì…","ÕœÌÀ","’Ê›Ì…","√Œ—Ï"});

	//Create Buttons For : Add , Delete , Save ,Clear , Restore , And Back to Main Frame
	JButton //addRowButton = new JButton("Add New Row"),
			addColumnButton = new JButton("≈÷«›… ⁄„Êœ"),
			//deleteRowButton = new JButton("Delete Selected Row"),
			deleteColumnButton = new JButton("Õ–› ⁄„Êœ"),
			saveButton = new JButton("Õ›Ÿ"),
			clearButton = new JButton("„”Õ"),
			restoreButton = new JButton("≈⁄«œ… «·»Ì«‰« "),
			back = new JButton("—ÃÊ⁄");

	//Constructor
	public TTable(){
		super("«·ﬂ »"); // Frame Title
		setIconImage(new ImageIcon("images/book2.png").getImage()); // Frame Icon Image
		buttons(); // Set Cursor on Hand Cursor SHape when it is stop on button
		readFile(filePath); // Get The Saved Data From the Data File
		table = new JTable(tableModel);
		table.setAutoCreateRowSorter(true); // Set the table enable to be Sort
		combo(); // To set The ComboBox on The Categories Column
		searchField.setBorder(BorderFactory.createCompoundBorder(
				                            searchField.getBorder(),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		////////////////////////Panel1/////////////////////////////////

		//It Contains Add And Delete Column Buttons
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(1,2));
		//panel1.add(addRowButton);
		panel1.add(addColumnButton);
		//panel1.add(deleteRowButton);
		panel1.add(deleteColumnButton);

		////////////////////////Panel2/////////////////////////////////

		//It Contains Save ,Clear and Restore Buttons

		JPanel panel2 = new JPanel();
		panel2.add(saveButton);
		panel2.add(clearButton);
		panel2.add(restoreButton);

		////////////////////////Panel3/////////////////////////////////

		//To set Panel1 and Panel2 in one panel

		JPanel panel3 = new JPanel();
		panel3.setLayout(new BorderLayout());
		panel3.add(panel1,BorderLayout.SOUTH);
		panel3.add(panel2,BorderLayout.CENTER);

		////////////////////////Panel4/////////////////////////////////

		//It Contains Back Button And Search

		JPanel panel4 = new JPanel();
		panel4.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel4.add(back);
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setVisible(false);
				new Main();
			}
		});
		JLabel cat = new JLabel("     «· ’«‰Ì› : ");
		panel4.add(catBox);
		panel4.add(cat);
		JLabel space = new JLabel("                                                                                                                                               ");
		panel4.add(space);

		JLabel searchType = new JLabel("»ÕÀ ⁄»— : ");
		JComboBox searchBox = new JComboBox(new String[]{
						"«”„ «·ﬂ «»","«·„ƒ·›","—ﬁ„ «·≈Ìœ«⁄"});
		JLabel searchLabel = new JLabel("»ÕÀ : ");
		panel4.add(searchField);
		panel4.add(searchLabel);
		searchBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
		panel4.add(searchBox);
		panel4.add(searchType);

		searchField.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e){
				readFile(filePath);
				String choose = searchField.getText();
				String type = searchBox.getSelectedItem().toString();
				int colNum = 0;
				if(type.equals("«”„ «·ﬂ «»"))
					colNum = 0;
				else if(type.equals("«·„ƒ·›"))
					colNum = 1;
				else if(type.equals("—ﬁ„ «·≈Ìœ«⁄"))
					colNum = 2;
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


		add(new JScrollPane(table),BorderLayout.CENTER);
		add(panel3,BorderLayout.SOUTH);
		add(panel4,BorderLayout.NORTH);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // To select just one row

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
		});// ADD Column

		/*deleteRowButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tableModel.removeRow(table.getSelectedRow());
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
		});// Delete Column

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
		});// To view Book Data when Double Click on the table

		saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				save(filePath);
			}
		});// Save Data

		clearButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tableModel.setRowCount(0);
			}
		}); // Delete All Data on the Table

		restoreButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				readFile(filePath);
				combo();
			}
		}); // Get the Data back from the File

		catBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				readFile(filePath);
				String choose = catBox.getSelectedItem().toString();
				if(!choose.equals("«·ﬂ·")){
					int rows = table.getRowCount();
					for(int i=0;i<rows;i++){
						if(!choose.equals(table.getValueAt(i,3).toString())){
							tableModel.removeRow(i);
							rows = table.getRowCount();
							i=-1;
						}
					}
				}
				combo();
			}
		});// To view The Selected Cateogrie

		////////////////////////Panel5/////////////////////////////////

		//It Contains Add ,Edit ,Delete and View Book Buttons

		JPanel panel5 = new JPanel();
		Dimension dim = panel5.getPreferredSize();
		dim.width = 120;
		panel5.setPreferredSize(dim);
		JButton addBook = new JButton("≈÷«›… ﬂ «»");
		addBook.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tableModel.addRow(new Vector());
				BookFrame frame = new BookFrame();
				for(int i=0;i<table.getColumnCount();i++){
					frame.addLabel(table.getColumnName(i));//To create Lable
					frame.addField((String)table.getValueAt(table.getRowCount()-1,i));//Create TextField
				}
				frame.update();//Prepare The Frame
				frame.setTable(tableModel);
				frame.setJTable(table);
				frame.addOk2Button(filePath);
				frame.setAlwaysOnTop(true);
				frame.setVisible(true);
			}
		});
		panel5.add(addBook);
		JButton editBook = new JButton(" ⁄œÌ· «·ﬂ «»");
		editBook.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				edit();
			}
		});
		panel5.add(editBook);
		JButton deleteBook = new JButton("Õ–› «·ﬂ «»");
		deleteBook.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				deleteRow();
			}
		});
		panel5.add(deleteBook);
		JButton viewBook = new JButton("⁄—÷ »Ì«‰«  «·ﬂ «»");
			viewBook.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if(table.getSelectedRow()>=0){
						BookFrame frame = new BookFrame();
						for(int i=0;i<table.getColumnCount();i++){
							if(!table.getValueAt(table.getSelectedRow(),i).toString().equals("")){
								frame.addLabel(table.getColumnName(i));
								frame.addUneditableField((String)table.getValueAt(table.getSelectedRow(),i));							}
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
		panel5.add(viewBook);
		add(panel5,BorderLayout.WEST);
		addBook.setCursor(new Cursor(Cursor.HAND_CURSOR));
		editBook.setCursor(new Cursor(Cursor.HAND_CURSOR));
		deleteBook.setCursor(new Cursor(Cursor.HAND_CURSOR));
		viewBook.setCursor(new Cursor(Cursor.HAND_CURSOR));

		setJMenuBar(getMyMenuBar());
		setSize(1200,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(3);
		setVisible(true);
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

	public void edit(){// To Edit A book
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

	public void save(String file){// Save Data
		try{
			ObjectOutputStream out;
			if(file.endsWith("dat"))
				out = new ObjectOutputStream(new FileOutputStream(file));
			else
				out = new ObjectOutputStream(new FileOutputStream(file+".dat"));
			out.writeObject(tableModel.getDataVector());
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
	void buttons(){// To set Cursor on Hand Shape when it is above of a button
		addColumnButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		deleteColumnButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		catBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
		//addRowButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		//deleteRowButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		restoreButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		back.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	public void open(){
		JFileChooser file = new JFileChooser();
		file.setSelectedFile(new File("C:/Users/Abdalla/Desktop/Œ«’ »«·Ã«„⁄…/Library2 - Final - Copy/books data"));
		int option = file.showOpenDialog(null);
		if(option == JFileChooser.APPROVE_OPTION){
			File f = file.getSelectedFile();
			filePath = f.getAbsolutePath();
			readFile(filePath);
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
				filePath = "books data/"+file;
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
	public void combo(){//To set The cell like combobox
		JComboBox comboBox = new JComboBox(new String[]{
				"”·›Ì…",
				"ÕœÌÀ",
				"’Ê›Ì…", "√Œ—Ï",
			});
		TableColumn categories = table.getColumn("Categories");
		categories.setCellEditor(new DefaultCellEditor(comboBox));
		/*if(!comboBox.getSelectedItem().toString().equals("")){
			comboBox.addItemListener(new ItemListener(){
				public void itemStateChanged(ItemEvent e){
					String type = comboBox.getSelectedItem().toString();
					String index = "";
					if(type.equals("Computer Sciense"))
						index = "004";
					else if (type.equals("Database"))
						index = "005-74";
					else if (type.equals("Programming Languages"))
						index = "005-13";
					else if (type.equals("Management"))
						index = "658";
					else if (type.equals("Mathematics"))
						index = "510";
					if(table.getSelectedRow()>-1)
					tableModel.setValueAt(index,table.getSelectedRow(),4);
				}
			});
		}*/
	}


	public static void main(String[] args){
		new TTable();
	}
}