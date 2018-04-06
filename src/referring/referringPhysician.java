import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JSplitPane;
import java.awt.Cursor;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JEditorPane;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class referringPhysician {

	private JFrame frmReferringPhysicianView;
	private JTextField txtA;
	private JTextField txtB;
	private static JLabel txtF = new JLabel();
	private static java.sql.Connection con;
	static java.sql.Statement stmt;
	private int counter=0;
	private JTextField txtDate;
	private JTextField txtPhone;
	private JTextField textField;
	/**
	 * Launch the application.
	 */
	public static void referringStart() {
	//public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
			
					//try {
						//Class.forName("com.mysql.jdbc.Driver");  
						//con=DriverManager.getConnection(  
						//"jdbc:mysql://localhost:3306/rsvpsystem","root","changeme!");  
						//stmt=con.createStatement();  
					referringPhysician window = new referringPhysician();
					
					window.frmReferringPhysicianView.setVisible(true);
					//}
				 //catch (Exception e) {
					//e.printStackTrace();
				//}
				
			}});
	}

	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public referringPhysician() throws SQLException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 */
	private void initialize() throws SQLException {
		frmReferringPhysicianView = new JFrame();
		frmReferringPhysicianView.setTitle("Referring Physician View");
		frmReferringPhysicianView.setResizable(false);
		frmReferringPhysicianView.setBounds(100, 100, 1459, 763);
		frmReferringPhysicianView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmReferringPhysicianView.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 1399, 702);
		frmReferringPhysicianView.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		tabbedPane.addTab("Home", null, panel, null);
		panel.setLayout(null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(20, 23, 592, 180);
		panel.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblWelcome = new JLabel("Welcome \\\\Referring Physician\\\\");
		lblWelcome.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblWelcome.setBounds(10, 31, 274, 22);
		panel_3.add(lblWelcome);
		
		
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(625, 23, 759, 640);
		panel.add(panel_4);
		panel_4.setLayout(null);
		
		JLabel lblStaffId = new JLabel("Staff ID:");
		lblStaffId.setBounds(10, 6, 54, 14);
		panel_3.add(lblStaffId);
		
		JLabel txtE = new JLabel("9959");
		txtE.setBounds(66, 6, 46, 14);
		panel_3.add(txtE);
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(20, 214, 592, 449);
		panel.add(panel_5);
		panel_5.setLayout(null);
		
		JLabel lblRequestForImages = new JLabel("Request for Images");
		lblRequestForImages.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblRequestForImages.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		lblRequestForImages.setBounds(224, 11, 136, 29);
		panel_5.add(lblRequestForImages);
		
		JButton btnNewButton = new JButton("Send Request");
		btnNewButton.setVisible(false);
		btnNewButton.setToolTipText("Request for Images");
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		btnNewButton.setBounds(432, 323, 114, 23);
		panel_5.add(btnNewButton);
		
		txtA = new JTextField();
		txtA.setSelectedTextColor(Color.YELLOW);
		txtA.setName("");
		txtA.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		txtA.setBounds(34, 93, 186, 23);
		panel_5.add(txtA);
		txtA.setColumns(10);
		
		JLabel lblPatientFirstName = new JLabel("*Patient First Name");
		lblPatientFirstName.setBounds(34, 76, 186, 14);
		panel_5.add(lblPatientFirstName);
		
		JLabel lblPatientLastname = new JLabel("*Patient Last Name");
		lblPatientLastname.setBounds(267, 76, 186, 14);
		panel_5.add(lblPatientLastname);
		
		txtB = new JTextField();
		txtB.setSelectedTextColor(Color.YELLOW);
		txtB.setName("");
		txtB.setColumns(10);
		txtB.setBounds(267, 93, 186, 23);
		panel_5.add(txtB);
		
		JComboBox txtC = new JComboBox();
		txtC.setEnabled(false);
		txtC.setModel(new DefaultComboBoxModel(new String[] {"PROCEDURE TYPE", "X-Ray", "MRI", "PET Scan", "Ultrasound", "CT Scan"}));
		txtC.setBounds(48, 227, 186, 23);
		panel_5.add(txtC);
		
		JEditorPane txtD = new JEditorPane();
		txtD.setEnabled(false);
		txtD.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtD.setBounds(267, 227, 186, 60);
		panel_5.add(txtD);
		
		JLabel lblProcedure = new JLabel("*Procedure");
		lblProcedure.setBounds(48, 213, 186, 14);
		panel_5.add(lblProcedure);
		
		JLabel lblReason = new JLabel("Reason");
		lblReason.setBounds(268, 213, 185, 14);
		panel_5.add(lblReason);
		
		JLabel lblError = new JLabel("//Error//");
		lblError.setVisible(false);
		lblError.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblError.setForeground(Color.RED);
		lblError.setBounds(34, 367, 274, 60);
		panel_5.add(lblError);
		
		JLabel lblRequired = new JLabel("* Required");
		lblRequired.setFont(new Font("Times New Roman", Font.ITALIC, 11));
		lblRequired.setForeground(Color.RED);
		lblRequired.setBounds(295, 157, 114, 14);
		panel_5.add(lblRequired);
		
		JLabel lblOrderId = new JLabel("Order ID: ");
		lblOrderId.setBounds(10, 20, 63, 14);
		panel_5.add(lblOrderId);
		txtF.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtF.setBounds(72, 20, 83, 14);
		panel_5.add(txtF);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Reports", null, panel_1, null);
		panel_1.setLayout(null);
		txtF.setText(newNumber());
		
		JLabel lblSent = new JLabel("Request Successfully Sent");
		lblSent.setHorizontalAlignment(SwingConstants.CENTER);
		lblSent.setForeground(Color.RED);
		lblSent.setBounds(295, 357, 152, 14);
		panel_5.add(lblSent);
		
		JLabel lblDateOfBirth = new JLabel("*Date of Birth: YYYY-MM-DD");
		lblDateOfBirth.setBounds(34, 138, 186, 14);
		panel_5.add(lblDateOfBirth);
		
		txtDate = new JTextField();
		txtDate.setBounds(34, 153, 95, 23);
		panel_5.add(txtDate);
		txtDate.setColumns(10);
		
		JLabel lblPatientPhoneNumber = new JLabel("*Patient Phone Number:");
		lblPatientPhoneNumber.setBounds(48, 310, 186, 14);
		panel_5.add(lblPatientPhoneNumber);
		JLabel lblZ = new JLabel("New Patient. Record Phone Number");
		txtPhone = new JTextField();
		txtPhone.setEditable(false);
		txtPhone.setBounds(48, 324, 186, 20);
		panel_5.add(txtPhone);
		txtPhone.setColumns(10);
		JButton btnSendRequest = new JButton("Send Request");
		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblSent.setVisible(false);
				if(!(txtA.getText().trim().isEmpty())&&!(txtB.getText().trim().isEmpty()&&!(txtDate.getText().trim().isEmpty())))
				{
					if(!(txtDate.getText().trim().isEmpty()))
					{
				
				
				
				String query="select * from patient where fName='"+
						txtA.getText()+"' && lName='"+txtB.getText()+
						"' && dateOfBirth='"+txtDate.getText()+"';";
				System.out.println(query);
			
				String val="";
				
					try {
						
						ResultSet cur=stmt.executeQuery(query);
						if(!cur.next() )
						{
							System.out.println("PATIENT IS NEW");
							txtPhone.setEditable(true);
							lblZ.setVisible(true);
							txtC.setEnabled(true);
							txtD.setEnabled(true);
							btnSendRequest.setVisible(true);
							btnNewButton.setVisible(false);
							btnNext.setVisible(false);
							txtA.setEditable(false);
							txtB.setEditable(false);
							txtDate.setEditable(false);
							String newNum=newPatientNum();
							textField.setText(newNum);
							
							
						}
						else
						{
							val =cur.getString("phoneNum");
							textField.setText(cur.getString("patientID"));
							System.out.println("VAL: "+val);
							
							
							System.out.println("PATIENT IS EXISTING");
							txtPhone.setText(val);
							txtC.setEnabled(true);
							txtD.setEnabled(true);
							btnNewButton.setVisible(true);
							btnSendRequest.setVisible(false);
							btnNext.setVisible(false);
							txtA.setEditable(false);
							txtB.setEditable(false);
							txtDate.setEditable(false);
						}
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
	}
					else{}
				}
				else
				{}
				}
		});
		btnNext.setBounds(145, 153, 89, 23);
		panel_5.add(btnNext);
		
		
		lblZ.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblZ.setBounds(38, 352, 215, 14);
		panel_5.add(lblZ);
		
		
		btnSendRequest.setVisible(false);
		btnSendRequest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if((txtPhone.getText().trim().isEmpty()))
						{}
				else{
					//Find Next available PatientID 
				try {
					stmt.executeUpdate("insert into patient (patientID,fName,lName,dateOfBirth,gender,"+
					"phoneNum,addressOne,addressTwo,addressCity,addressState,addressZip) values('"+textField.getText()+
							"','"+txtA.getText()+"','"+txtB.getText()+"','"+txtDate.getText()+"','null','"+txtPhone.getText()+"',null,null,null,null,null);");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String sql="insert into radOrder values ('"+txtE.getText()+"','"+txtC.getSelectedItem().toString()+
						"','"+txtD.getText()+"','"+textField.getText()+"','"+txtA.getText()+
						"','"+txtB.getText()+"','"+txtF.getText()+
						"','"+txtPhone.getText()+"');";
				try {
					stmt.executeUpdate(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				}
				
				txtA.setText("");
				txtB.setText("");
				txtC.setSelectedIndex(0);
				txtD.setText("");
				txtF.setText(newNumber());
				lblSent.setVisible(true);
				txtA.setEditable(true);
				txtB.setEditable(true);
				btnNext.setVisible(true);
				txtDate.setEditable(true);
				txtD.setEnabled(false);
				txtC.setEnabled(false);
				txtPhone.setEditable(false);
				txtPhone.setText("");
				lblZ.setVisible(false);
				txtDate.setText("");
				textField.setText("");
				btnSendRequest.setVisible(false);
				
				
			}
		});
		btnSendRequest.setBounds(432, 301, 114, 23);
		panel_5.add(btnSendRequest);
		
		JLabel lblPatientId = new JLabel("Patient ID:");
		lblPatientId.setBounds(48, 258, 178, 14);
		panel_5.add(lblPatientId);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(48, 274, 186, 20);
		panel_5.add(textField);
		textField.setColumns(10);
		lblZ.setVisible(false);
		lblSent.setVisible(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(txtA.toString());
				if(!(txtA.getText().trim().isEmpty())&&!(txtB.getText().trim().isEmpty()))
				{
					if(!(txtC.getSelectedItem().toString()=="PROCEDURE TYPE"))
					{
						lblError.setVisible(false);
						System.out.println("Correct");
						
						try {
							String sql="insert into radOrder values ('"+txtE.getText()+"','"+txtC.getSelectedItem().toString()+"','"+txtD.getText()+"','"+textField.getText()+"','"+txtA.getText()+"','"+txtB.getText()+"','"+txtF.getText()+"','"+txtPhone.getText()+"');";
							System.out.println(sql);
							stmt.executeUpdate(sql);
							txtA.setText("");
							txtB.setText("");
							txtC.setSelectedIndex(0);
							txtD.setText("");
							txtF.setText(newNumber());
							lblSent.setVisible(true);
							txtA.setEditable(true);
							txtB.setEditable(true);
							btnNext.setVisible(true);
							txtDate.setEditable(true);
							txtD.setEnabled(false);
							txtC.setEnabled(false);
							txtPhone.setEnabled(true);
							txtPhone.setText("");
							lblZ.setVisible(false);
							txtDate.setText("");
							textField.setText("");
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							lblError.setVisible(true);
							lblError.setText("An Error as Occured. Please contact IT");
							e.printStackTrace();
						}
					}
					else
					{
						
						lblError.setVisible(true);
						lblError.setText("Choose Procedure");lblError.repaint();
						//System.out.println("Choose Procedure");
					}
				}
				else
				{
					lblError.setVisible(true);
					lblError.setText("Fill name completely");
					//System.out.println("Fill name completely");
				}
			}
		});
	}
	
	public static String getTime()
	{
			DateFormat df = new SimpleDateFormat("MMM dd, yyyy");
	      // Date dateobj = new Date();
	       //String time= df.format(dateobj);

	       /*getting current date time using calendar class 
	        * An Alternative of above*/
	      Calendar calobj = Calendar.getInstance();
	       String time=df.format(calobj.getTime());
	       return time;
	}

	public static String newNumber()
	{

		ResultSet cur;
		String value = null;
		try {
			cur=stmt.executeQuery("SELECT orderID FROM radOrder ORDER BY orderID DESC LIMIT 1;");
			
			while(cur.next())
		{
			
			int val=cur.getInt("orderID");
			val=val+1;
			value=Integer.toString(val);
			
		}
			
			
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return value;
	}
	public static String newPatientNum()
	{

		ResultSet cur;
		String value = null;
		try {
			cur=stmt.executeQuery("SELECT patientID FROM patient ORDER BY patientID DESC LIMIT 1;");
			
			while(cur.next())
		{
			
			int val=cur.getInt("patientID");
			val=val+1;
			value=Integer.toString(val);
			
		}
			
			
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return value;
	}
}
