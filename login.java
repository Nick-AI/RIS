
import java.awt.EventQueue;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.awt.event.ActionEvent;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Component;
import javax.swing.JPopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Window.Type;
import java.awt.SystemColor;
public class login extends JFrame {
	private JTextField usernameField;
	private JPasswordField passwordField;
	private int counter=0;
	private static Statement stmt;
	public static Connection con;
	private String firstName;
	private String lastName;
	private int admin;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Class.forName("com.mysql.jdbc.Driver");  
					con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/sample","root","changeme");  
					stmt=con.createStatement();  
					String sql = "CREATE TABLE IF NOT EXISTS login (\n"
			                + "	username varchar(500) PRIMARY KEY,\n"
			                + "	name text NOT NULL,\n"
			                + "	admin boolean,\n"
			                + " password varChar(500)\n"
			                + ");";
					stmt.executeUpdate(sql);
					
					login frame = new login();
					frame.setVisible(true);
					
					
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public login() {
		setForeground(SystemColor.textHighlight);
		setAutoRequestFocus(false);
		
		setResizable(false);
		setAlwaysOnTop(true);
		getContentPane().setForeground(Color.DARK_GRAY);
		//getContentPane().setLayout(null);
		//getContentPane().setLocation(null,0);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		getContentPane().setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		setSize(220, 400);
		setLocationRelativeTo(null);
		
		JPanel LoginScreen = new JPanel();
		LoginScreen.setBackground(Color.LIGHT_GRAY);
		LoginScreen.setBounds(0, 0, 593, 392);
		getContentPane().add(LoginScreen);
		SpringLayout springLayout = new SpringLayout();
		LoginScreen.setLayout(springLayout);
		
		JLabel welcomeTitle = new JLabel("Welcome! Login to begin.");
		springLayout.putConstraint(SpringLayout.WEST, welcomeTitle, 208, SpringLayout.WEST, LoginScreen);
		welcomeTitle.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		welcomeTitle.setVerticalAlignment(SwingConstants.TOP);
		LoginScreen.add(welcomeTitle);
		
		usernameField = new JTextField();
		springLayout.putConstraint(SpringLayout.SOUTH, welcomeTitle, -17, SpringLayout.NORTH, usernameField);
		springLayout.putConstraint(SpringLayout.WEST, usernameField, 267, SpringLayout.WEST, LoginScreen);
		LoginScreen.add(usernameField);
		usernameField.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		springLayout.putConstraint(SpringLayout.NORTH, usernameField, 0, SpringLayout.NORTH, lblUsername);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		LoginScreen.add(lblUsername);
		
		JLabel passwordTitle = new JLabel("Password");
		springLayout.putConstraint(SpringLayout.NORTH, passwordTitle, 153, SpringLayout.NORTH, LoginScreen);
		springLayout.putConstraint(SpringLayout.SOUTH, lblUsername, -17, SpringLayout.NORTH, passwordTitle);
		springLayout.putConstraint(SpringLayout.EAST, lblUsername, 0, SpringLayout.EAST, passwordTitle);
		passwordTitle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		LoginScreen.add(passwordTitle);
		
		passwordField = new JPasswordField();
		springLayout.putConstraint(SpringLayout.WEST, passwordField, 267, SpringLayout.WEST, LoginScreen);
		springLayout.putConstraint(SpringLayout.EAST, passwordField, -216, SpringLayout.EAST, LoginScreen);
		springLayout.putConstraint(SpringLayout.EAST, usernameField, 0, SpringLayout.EAST, passwordField);
		springLayout.putConstraint(SpringLayout.EAST, passwordTitle, -6, SpringLayout.WEST, passwordField);
		springLayout.putConstraint(SpringLayout.NORTH, passwordField, 0, SpringLayout.NORTH, passwordTitle);
		LoginScreen.add(passwordField);
		JButton loginBtn = new JButton("Login");
		springLayout.putConstraint(SpringLayout.NORTH, loginBtn, 17, SpringLayout.SOUTH, passwordField);
		springLayout.putConstraint(SpringLayout.WEST, loginBtn, 254, SpringLayout.WEST, LoginScreen);
		
		JLabel lblError = new JLabel("Username and/or Password are incorrect");
		springLayout.putConstraint(SpringLayout.NORTH, lblError, 29, SpringLayout.SOUTH, loginBtn);
		springLayout.putConstraint(SpringLayout.WEST, lblError, 175, SpringLayout.WEST, LoginScreen);
		lblError.setVisible(false);
		lblError.setForeground(new Color(255, 51, 0));
		LoginScreen.add(lblError);
		LoginScreen.add(loginBtn);
		
		JLabel RSVPTitle = new JLabel("TITLE");
		springLayout.putConstraint(SpringLayout.NORTH, RSVPTitle, 10, SpringLayout.NORTH, LoginScreen);
		springLayout.putConstraint(SpringLayout.WEST, RSVPTitle, 0, SpringLayout.WEST, loginBtn);
		RSVPTitle.setFont(new Font("Times New Roman", Font.BOLD, 20));
		LoginScreen.add(RSVPTitle);
		
		JButton exitBtn = new JButton("Close Program");
		springLayout.putConstraint(SpringLayout.SOUTH, exitBtn, -39, SpringLayout.SOUTH, LoginScreen);
		springLayout.putConstraint(SpringLayout.EAST, exitBtn, -10, SpringLayout.EAST, LoginScreen);
		exitBtn.setBackground(new Color(240, 240, 240));
		exitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		LoginScreen.add(exitBtn);
		
		JLabel timeLabel = new JLabel(getTime());
		//timeLabel.repaint(10000);
		springLayout.putConstraint(SpringLayout.WEST, timeLabel, 30, SpringLayout.WEST, LoginScreen);
		springLayout.putConstraint(SpringLayout.SOUTH, timeLabel, -27, SpringLayout.SOUTH, LoginScreen);
		LoginScreen.add(timeLabel);
		
		JLabel lbError2 = new JLabel("If you are not a user, contact Administration for authorized access");
		springLayout.putConstraint(SpringLayout.NORTH, lbError2, 5, SpringLayout.SOUTH, lblError);
		springLayout.putConstraint(SpringLayout.WEST, lbError2, 92, SpringLayout.WEST, LoginScreen);
		lbError2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbError2.setForeground(Color.RED);
		LoginScreen.add(lbError2);
		//setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{usernameField, passwordField, loginBtn, exitBtn, aboutBtn}));
		lbError2.setVisible(false);
		
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String usernameValue=usernameField.getText();
				String passwordValue=passwordField.getText();
				System.out.println(usernameValue);
				System.out.println(passwordValue);
				
				try {
					stmt=con.createStatement();
					String query="select * from login where username='"+usernameValue+"'";
					System.out.println(query);
					ResultSet loginCheck=stmt.executeQuery(query);
					String passwordString="";
					while(loginCheck.next())
					{
						passwordString=loginCheck.getString("password");
						firstName=loginCheck.getString("fname");
						lastName=loginCheck.getString("lname");
						admin=loginCheck.getInt("admin");
						
					
					if(passwordString.equals(passwordValue))
					{
						System.out.println("SUCCESS");
						lbError2.setVisible(false);
						lblError.setVisible(false);
						if(admin==1)
						{
						dispose();
						//RSVP_Main.main(stmt,firstName,lastName,admin,"");
						}
						else
						{
							LoginScreen.setVisible(false);
						//	buildingSelect.setVisible(true);
						}
					}
					else
					{
						counter++;
						lblError.setVisible(true);
						if(counter==3)
						{
							lbError2.setVisible(true);
						}
						if(counter==10)
						{
						
							//aboutBtn.setEnabled(false);
							passwordField.setEnabled(false);
							usernameField.setEnabled(false);
						}
						if(counter==11)
						{
							System.exit(0);
						}
						
					}

					}
					
					} catch (SQLException e1) {
					}
				
				}
		});
		setTitle("Login Screen");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 599, 421);
		
		
		
		
	}
	public String getTime()
	{
			DateFormat df = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
	       Date dateobj = new Date();
	       String time= df.format(dateobj);

	       /*getting current date time using calendar class 
	        * An Alternative of above*/
	       //Calendar calobj = Calendar.getInstance();
	      // String time=df.format(calobj.getTime());
	       return time;
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
