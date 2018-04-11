import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.Blob;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.Cursor;
import java.awt.Desktop;
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
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
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
	private JTextField txtY;
	private JTextField txtX;
	private JTextField txtZ;
	private JLabel txtE;
	private JTable table;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
			
					try {
						Class.forName("com.mysql.jdbc.Driver");  
						con=DriverManager.getConnection(  
						"jdbc:mysql://localhost:3306/rsvpsystem","root","ResidenceLife1873!");  
						stmt=con.createStatement();  
					referringPhysician window = new referringPhysician();
					
					window.frmReferringPhysicianView.setVisible(true);
					}
				 catch (Exception e) {
					e.printStackTrace();
				}
				
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
		
		JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
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
		
		JLabel lblCompleteReports = new JLabel("Completed Reports");
		lblCompleteReports.setHorizontalAlignment(SwingConstants.CENTER);
		lblCompleteReports.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblCompleteReports.setBounds(0, 11, 759, 24);
		panel_4.add(lblCompleteReports);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(233, 33, 323, 2);
		panel_4.add(separator);
		
		txtY = new JTextField();
		txtY.setBounds(307, 65, 145, 22);
		panel_4.add(txtY);
		txtY.setColumns(10);
		
		txtX = new JTextField();
		txtX.setColumns(10);
		txtX.setBounds(129, 65, 145, 22);
		panel_4.add(txtX);
		
		txtZ = new JTextField();
		txtZ.setColumns(10);
		txtZ.setBounds(486, 65, 145, 22);
		panel_4.add(txtZ);
		
		JLabel lblNewLabel = new JLabel("First Name:");
		lblNewLabel.setBounds(127, 53, 147, 14);
		panel_4.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Last Name:");
		lblNewLabel_1.setBounds(307, 53, 145, 14);
		panel_4.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Date of Birth (YYYY-MM-DD)");
		lblNewLabel_2.setBounds(486, 53, 175, 14);
		panel_4.add(lblNewLabel_2);
		JButton btnOpenReport = new JButton("Open Report");
		JButton btnNext_1 = new JButton("Pull Reports");
		//btnNext_1.setVisible(false);
		btnNext_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int counter=0;
				String plus=" where completed=1 &&";
				if(!txtX.getText().trim().isEmpty())
				{
					counter++;
					if(!(plus==" where completed=1 &&"))
					{
						
						plus=plus+" && ";
					}
					plus=plus+" fName='"+txtX.getText()+"'";
					System.out.println(plus);
				}
				if((!txtY.getText().trim().isEmpty()))
				{
					counter++;
					if(!(plus==" where completed=1 &&"))
					{
						plus=plus+" && ";
					}
					plus=plus+"lName='"+txtY.getText()+"'";
					System.out.println(plus);
				}
				if(!txtZ.getText().trim().isEmpty())
				{
					counter++;
					if(!(plus==" where completed=1 && "))
					{
						plus=plus+" && ";
					}
					plus=plus+"dateOfBirth='"+txtZ.getText()+"'";
					System.out.println(plus);
				}
				System.out.println(plus);
				if(counter==3)
				{
				try {
					btnOpenReport.setEnabled(true);
					filledRequest(plus);
				}
				
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
				else
				{}
			}
		});
		btnNext_1.setBounds(633, 65, 116, 23);
		panel_4.add(btnNext_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 133, 739, 496);
		panel_4.add(scrollPane);
		
		table = new JTable();
		scrollPane.setColumnHeaderView(table);
		scrollPane.setViewportView(table);
		JLabel lblError1 = new JLabel("*Select a row in the column to Open a Report");
		
		btnOpenReport.setEnabled(false);
		btnOpenReport.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				try
				{
				int row=table.getSelectedRow();
				//int col=table.getSelectedColumn();
				Object a=table.getValueAt(row, 0);
				Object b=table.getValueAt(row, 1);
				Object c=table.getValueAt(row, 2);
				System.out.println(a.toString());
				System.out.println(b.toString());
				System.out.println(c.toString());
				lblError1.setVisible(false);
				writeFile(a.toString(),b.toString(),c.toString());
				
				//create report using the selected information
				 
				}
				catch (ArrayIndexOutOfBoundsException e)
				{
					lblError1.setVisible(true);
				}
			}
		});
		btnOpenReport.setBounds(317, 99, 120, 23);
		panel_4.add(btnOpenReport);
		
		
		lblError1.setForeground(Color.RED);
		lblError1.setBounds(33, 98, 276, 14);
		panel_4.add(lblError1);
		lblError1.setVisible(false);
		
		JLabel lblStaffId = new JLabel("Staff ID:");
		lblStaffId.setBounds(10, 6, 54, 14);
		panel_3.add(lblStaffId);
		
		txtE = new JLabel("9959");
		txtE.setBounds(66, 6, 46, 14);
		panel_3.add(txtE);
		
		JLabel lblSystemoutprintlnhelloWorld = new JLabel("System.out.println(\"Hello World\");");
		lblSystemoutprintlnhelloWorld.setVisible(false);
		lblSystemoutprintlnhelloWorld.setBounds(10, 155, 221, 14);
		panel_3.add(lblSystemoutprintlnhelloWorld);
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
		btnSendRequest.setBounds(432, 323, 114, 23);
		panel_5.add(btnSendRequest);
		
		JLabel lblPatientId = new JLabel("Patient ID:");
		lblPatientId.setBounds(48, 258, 178, 14);
		panel_5.add(lblPatientId);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(48, 274, 186, 20);
		panel_5.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblSystemoutprintlnhelloWorld.setVisible(true);
			}
		});
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setBackground(Color.WHITE);
		btnNewButton_1.setBounds(4, 202, 6, 23);
		panel.add(btnNewButton_1);
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
			DateFormat df = new SimpleDateFormat("MMM dd, yyyy hh:mm");
	      // Date dateobj = new Date();
	       //String time= df.format(dateobj);

	       /*getting current date time using calendar class 
	        * An Alternative of above*/
	      Calendar calobj = Calendar.getInstance();
	       String time=df.format(calobj.getTime());
	       return time;
	}
	public static String getTimeFile()
	{
			DateFormat df = new SimpleDateFormat("MMM-dd-yyyy-hh-mm");
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
	public void filledRequest(String plus) throws SQLException
	{
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Report ID", "First Name", "Last Name"
				}
			));
			
		
			
			
		String sql="select Distinct * from report,patient,`file`"+plus;
		System.out.println("SQL: "+sql);
		ResultSet cur = stmt.executeQuery(sql);
			
		try {
			while(cur.next())
			{
				String first=cur.getString("reportID");
				String last=cur.getString("fName");
				String proc=cur.getString("lName");
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[]{first,last,proc});
			
			
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//cur1 = stmt.executeQuery("Select * from resident where ID=");
			
			
		
		
	}
	public void writeFile(String a, String b, String c)
	{
		String FILENAME = System.getProperty("user.home") + "/Desktop/Report"+getTimeFile()+".doc";
		BufferedWriter bw = null;
		FileWriter fw = null;

				try {

					String data = "PATIENT REPORT \nDOWNLOADED: "+getTime()+"\n\n";
					File file = new File(FILENAME);

					// if file doesnt exists, then create it
					if (!file.exists()) {
						file.createNewFile();
					}
					String reportID = a;
					fw = new FileWriter(file.getAbsoluteFile(), true);
					bw = new BufferedWriter(fw);
					bw.write(data);
					bw.write("Patient Record Report\n");
					bw.write("Report Number: "+a+"\n\n");
					bw.write("Report Created By: "+txtE.getText()+"\n");
					String body=null;
					String fileID = null;
					String imageID=null;
					Blob image=null;
					String dateTaken=null;
					String mode=null;
					String desc=null;
					int patID = 0;
					String fname=null;
					String lname=null;
					String dob=null;
					String gen=null;
					String phone=null;
					String addressOne=null;
					String addressTwo=null;
					String addressCity=null;
					String addressState=null;
					String addressZip=null;
					try {
						ResultSet ans=stmt.executeQuery("select * from report where reportID="+a+";");
						while(ans.next())
						{
							body=ans.getString("reportBody");
							fileID=ans.getString("fileID");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					bw.write("File ID:"+fileID+"\n");
					bw.write("\n");
					try {
						ResultSet abs=stmt.executeQuery("select * from images where reportID="+a+";");
						while(abs.next())
						{
							image=abs.getBlob("image");
							dateTaken=abs.getString("dateTaken");
							mode=abs.getString("modality");
							desc=abs.getString("description");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						ResultSet pat=stmt.executeQuery("select patientID from file where fileID="+fileID);
						while(pat.next())
						{
							patID=pat.getInt("patientID");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ResultSet all;
					try {
						all = stmt.executeQuery("select * from patient where patientID="+patID+";");
					while(all.next())
					{
						fname=all.getString("fname");
						lname=all.getString("lname");
						dob=all.getString("dateOfBirth");
						gen=all.getString("gender");
						phone=all.getString("phoneNum");
						addressOne=all.getString("addressOne");
						addressTwo=all.getString("addressTwo");
						addressCity=all.getString("addressCity");
						addressState=all.getString("addressState");
						addressZip=all.getString("addressZip");
					}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					bw.write("Patient Information:\n\n");
					bw.write("-----------------------------------------------\n");
					bw.write("Patient Name: "+fname+" "+lname+"\n");
					bw.write("Date of Birth: "+dob+"\n");
					bw.write("Gender: "+gen+"\n");
					bw.write("Phone Number: "+phone+"\n");
					bw.write("Address: "+addressOne+"\n");
					bw.write(addressTwo+"\n");
					bw.write(addressCity+","+addressState+" "+addressZip+"\n");
					try {
						all = stmt.executeQuery("select * from vitals where patientID="+patID+"&& reportID='"+reportID+"';");
					while(all.next())
					{
						fname=all.getString("fname");
						lname=all.getString("lname");
						dob=all.getString("dateOfBirth");
						gen=all.getString("gender");
						phone=all.getString("phoneNum");
						addressOne=all.getString("addressOne");
						addressTwo=all.getString("addressTwo");
						addressCity=all.getString("addressCity");
						addressState=all.getString("addressState");
						addressZip=all.getString("addressZip");
					}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Done");
					Desktop.getDesktop().open( file);

				} catch (IOException e) {

					e.printStackTrace();

				} finally {

					try {

						if (bw != null)
							bw.close();

						if (fw != null)
							fw.close();

					} catch (IOException ex) {

						ex.printStackTrace();

					}
				}

			}
}
