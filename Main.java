import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class Main extends JFrame{
	Main(){
		super("Library System");
		setResizable(false);
		setUndecorated(true);
		setLayout(null);
		setIconImage(new ImageIcon("images/book.png").getImage());
		JLabel background = new JLabel();
		background.setBounds(0,0,400,400);
		background.setIcon(new ImageIcon(new ImageIcon("images/background.jpg").getImage().getScaledInstance(
			background.getWidth(),background.getHeight(),Image.SCALE_DEFAULT)));
		JButton book = buttonSetting("«·ﬂ »","images/book2.png");
		book.setBounds(70,170,130,60);
		JButton client = buttonSetting("«·⁄„·«¡","images/client.png");
		client.setBounds(220,170,130,60);
		book.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setVisible(false);
				new TTable();
			}
		});
		client.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						setVisible(false);
						new Reservation();
					}
		});
		JButton exit = buttonSetting("images/exit.png");
		exit.setBounds(340,10,50,50);
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		add(exit);
		add(book);
		add(client);
		add(background);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400,400);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	JButton buttonSetting(String text,String image){
		JButton btn = new JButton(text,new ImageIcon(image));
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn.setForeground(Color.lightGray);
		btn.setBackground(Color.darkGray);
		btn.setFont(new Font("Serif",Font.BOLD,18));
		btn.setBorder(BorderFactory.createLineBorder(Color.GRAY,3));
		btn.setToolTipText("This is "+text+" Button");
		return btn;
	}
	JButton buttonSetting(String image){
			JButton btn = new JButton(new ImageIcon(image));
			btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
			btn.setForeground(Color.lightGray);
			btn.setBackground(Color.darkGray);
			btn.setFont(new Font("Serif",Font.BOLD,18));
			btn.setBorder(BorderFactory.createLineBorder(Color.GRAY,3));
			return btn;
	}
	public static void main(String[] args){
		new Main();
	}
}