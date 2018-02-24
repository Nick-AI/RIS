import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.JEditorPane;
import java.awt.Component;
import javax.swing.Box;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class billing extends JFrame {

	private JPanel contentPane;
	private JTextField txtID;
	private JTextField tctDOB;
	private JTextField txtDate;
	private JTextField txtName;
	private JTextField txtTotal;
	private JTextField textField_6;
	private JTextField txtOther;
	private static Statement stmt;
	private static Connection con;
	private String physician="Dr. Bryce Pain";
	private double total=0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	//THIS WILL NEED TO BE CHANGED TO PARAMETERS (Physician,con)
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				//con2=con;
				try {
					Class.forName("com.mysql.jdbc.Driver");  
					con=(Connection) DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/rsvpsystem","root","CHANGEME");  
					stmt=(Statement) con.createStatement();  
					billing frame = new billing();
					frame.setVisible(true);
				} catch (Exception e) {
					System.out.println("Credentials for SQL Server need to be changed for your system.");
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public billing() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setTitle("Billing");
		
		setBounds(350, 150, 1237, 745);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblBillingRequestFor = new JLabel("Billing Request for Finance Department");
		lblBillingRequestFor.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblBillingRequestFor.setBounds(426, 11, 293, 20);
		contentPane.add(lblBillingRequestFor);
		
		JLabel lblPatientIdNumber = new JLabel("Patient ID Number");
		lblPatientIdNumber.setBounds(10, 92, 103, 14);
		contentPane.add(lblPatientIdNumber);
		
		txtID = new JTextField();
		txtID.setBounds(118, 89, 122, 20);
		contentPane.add(txtID);
		txtID.setColumns(10);
		
		JLabel lblDob = new JLabel("DOB:");
		lblDob.setBounds(10, 124, 46, 14);
		contentPane.add(lblDob);
		
		tctDOB = new JTextField();
		tctDOB.setBounds(51, 121, 95, 20);
		contentPane.add(tctDOB);
		tctDOB.setColumns(10);
		
		JLabel lblTodaysDate = new JLabel("Today's Date:");
		lblTodaysDate.setBounds(10, 52, 78, 14);
		contentPane.add(lblTodaysDate);
		String date=getTime();
		txtDate = new JTextField("  "+ date);
		txtDate.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtDate.setBackground(SystemColor.scrollbar);
		//txtDate.setEnabled(false);
		txtDate.setEditable(false);
		txtDate.setBounds(93, 49, 103, 20);
		contentPane.add(txtDate);
		txtDate.setColumns(10);
		
		JTextPane txtpnfillFormOut = new JTextPane();
		txtpnfillFormOut.setBackground(SystemColor.activeCaption);
		txtpnfillFormOut.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtpnfillFormOut.setEditable(false);
		txtpnfillFormOut.setText("**Fill form out completely and accurately to send billing requests to the Finance Department to bill patients. Patients will be billed by the Finance Department of the balance due.");
		txtpnfillFormOut.setBounds(436, 36, 268, 70);
		contentPane.add(txtpnfillFormOut);
		
		JButton btnNext = new JButton("Next");
		
		btnNext.setBounds(272, 88, 89, 23);
		contentPane.add(btnNext);
		
		JPanel panel = new JPanel();
		panel.setVisible(false);
		panel.setBackground(SystemColor.scrollbar);
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 5));
		panel.setBounds(10, 189, 1201, 506);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblPatientName = new JLabel("Patient Name: ");
		lblPatientName.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPatientName.setBounds(22, 23, 90, 14);
		panel.add(lblPatientName);
		
		txtName = new JTextField();
		txtName.setEditable(false);
		txtName.setBounds(106, 20, 113, 20);
		panel.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblServicesIncurred = new JLabel("Services Incurred:");
		lblServicesIncurred.setBounds(22, 88, 137, 14);
		panel.add(lblServicesIncurred);
		
		JRadioButton rdA = new JRadioButton("X-Ray");
		rdA.setBounds(22, 109, 137, 23);
		panel.add(rdA);
		
		JRadioButton rdB = new JRadioButton("MRI");
		rdB.setBounds(22, 135, 137, 23);
		panel.add(rdB);
		
		JRadioButton rdC = new JRadioButton("CT Scan");
		rdC.setBounds(22, 161, 137, 23);
		panel.add(rdC);
		
		JRadioButton rdD = new JRadioButton("Ultrasound");
		rdD.setBounds(22, 187, 137, 23);
		panel.add(rdD);
		
		JRadioButton rdF = new JRadioButton("Mammography");
		rdF.setBounds(22, 239, 137, 23);
		panel.add(rdF);
		
		JLabel label = new JLabel("");
		label.setBounds(22, 293, 46, 14);
		panel.add(label);
		
		JLabel lblSubtotal = new JLabel("TOTAL: ");
		lblSubtotal.setBounds(77, 324, 46, 14);
		panel.add(lblSubtotal);
		
		txtTotal = new JTextField();
		txtTotal.setEditable(false);
		txtTotal.setBounds(133, 321, 101, 20);
		panel.add(txtTotal);
		txtTotal.setColumns(10);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setBounds(22, 65, 336, 14);
		panel.add(horizontalStrut);
		
		txtOther = new JTextField();
		txtOther.setBounds(241, 162, 153, 20);
		panel.add(txtOther);
		txtOther.setColumns(10);
		
		JRadioButton rdH = new JRadioButton("Other Incurred Amounts");
		rdH.setBounds(221, 135, 191, 23);
		panel.add(rdH);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(588, 146, 451, 146);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblApprovedBy = new JLabel("Approved By:");
		lblApprovedBy.setBounds(97, 23, 78, 14);
		panel_1.add(lblApprovedBy);
		
		textField_6 = new JTextField(physician);
		textField_6.setBounds(173, 20, 153, 20);
		panel_1.add(textField_6);
		textField_6.setEditable(false);
		textField_6.setColumns(10);
		
		JRadioButton rdbtnApprove = new JRadioButton("All payments are correct and accurate");
		rdbtnApprove.setBounds(107, 61, 227, 23);
		panel_1.add(rdbtnApprove);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(173, 91, 89, 23);
		panel_1.add(btnSubmit);
		JRadioButton rdG = new JRadioButton("PET Scan");
		rdG.setBounds(22, 213, 137, 20);
		panel.add(rdG);
		JButton btnNewButton = new JButton("Next");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				total=0;
				if(rdA.isSelected())
				{
					total=total+500.00;
				}
				if(rdB.isSelected())
				{
					total=total+2000.00;
				}
				if(rdC.isSelected())
				{
					total=total+1200.00;
				}
				if(rdD.isSelected())
				{
					total=total+263.00;
				}
				if(rdF.isSelected())
				{
					total=total+102.00;
				}
				if(rdG.isSelected())
				{
					total=total+5000.00;
				}
				if(rdH.isSelected())
				{
					String totalAmount=txtOther.getText();
					double amountTotal=Double.parseDouble(totalAmount);
					total=total+amountTotal;
				}
				//NumberFormat formatter = NumberFormat.getCurrencyInstance();
				String amount=Double.toString(total);
				//String amount2=formatter.format(amount);
				txtTotal.setText(" $ "+amount);
				
				
				
			}
		});
		btnNewButton.setBounds(269, 213, 89, 23);
		panel.add(btnNewButton);
		
		
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnApprove.isSelected())
				{
					System.out.println("Confirmed");
					panel.setVisible(false);
					dispose();
					confirmedBilling.main(null);
					
				}
				else
				{
				}
				
			}
		});
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false); //you can't see me!
				dispose();
			}
		});
		btnExit.setBounds(994, 49, 83, 20);
		contentPane.add(btnExit);
		
		JLabel lblMmddyyyy = new JLabel("MM/DD/YYYY");
		lblMmddyyyy.setBounds(156, 124, 122, 14);
		contentPane.add(lblMmddyyyy);
		
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String ID =txtID.getText();
				String DOB=tctDOB.getText();
				if(ID.equals("")||DOB.equals("")||DOB.equals("MM/DD/YYYY"))
				{
					
				}
				else
				{
				
				ResultSet cur;
				try {
					String sql=("select * from patientInfo where ID="+ID+" && DOB="+DOB);
					cur = stmt.executeQuery(sql);
					while(cur.next())
					{
					panel.setVisible(true);
					btnNext.setVisible(false);
					String fname=cur.getString("fname");
					String lname=cur.getString("lname");
					String fullname=fname+" "+lname;
					txtName.setText(fullname);
					
					
				}} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
			
				
			}
		});
		
		
		
		
		
	}
	public String getTime()
	{
			DateFormat df = new SimpleDateFormat("MMM dd, yyyy");
	       Date dateobj = new Date();
	       String time= df.format(dateobj);

	       /*getting current date time using calendar class 
	        * An Alternative of above*/
	       //Calendar calobj = Calendar.getInstance();
	      // String time=df.format(calobj.getTime());
	       return time;
	}
}
