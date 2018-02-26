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
import javax.swing.JProgressBar;
import org.eclipse.wb.swing.FocusTraversalOnArray;

public class billing extends JFrame {

	private JPanel contentPane;
	private JTextField txtID;
	private JTextField txtDOB;
	private JTextField txtDate;
	private JTextField txtName;
	private JTextField txtTotal;
	private JTextField textField_6;
	private JTextField txtOther;
	private static Statement stmt;
	private static Connection con;
	private String physician="Dr. Bryce Pain";
	private double total=0.0;
	private int counter=0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Class.forName("com.mysql.jdbc.Driver");  
					con=(Connection) DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/rsvpsystem","root","ResidenceLife1873!");  
					stmt=(Statement) con.createStatement();  
					billing frame = new billing();
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
	public billing() {
		setResizable(false);
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
		
		JLabel lblIDNum = new JLabel("Patient ID Number");
		lblIDNum.setBounds(10, 92, 103, 14);
		contentPane.add(lblIDNum);
		
		txtID = new JTextField();
		txtID.setBounds(118, 89, 122, 20);
		contentPane.add(txtID);
		txtID.setColumns(10);
		
		JLabel lblDob = new JLabel("DOB:");
		lblDob.setBounds(10, 124, 46, 14);
		contentPane.add(lblDob);
		
		txtDOB = new JTextField();
		txtDOB.setBounds(51, 121, 95, 20);
		contentPane.add(txtDOB);
		txtDOB.setColumns(10);
		
		JLabel lblDate = new JLabel("Today's Date:");
		lblDate.setBounds(10, 52, 78, 14);
		contentPane.add(lblDate);
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
		
		JButton btnNext1 = new JButton("Next");
		
		btnNext1.setBounds(272, 88, 89, 23);
		contentPane.add(btnNext1);
		
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
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
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
		rdbtnApprove.setBounds(97, 61, 264, 23);
		panel_1.add(rdbtnApprove);
		panel_1.setVisible(false);
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(173, 91, 89, 23);
		panel_1.add(btnSubmit);
		
		JLabel agreelbl = new JLabel("AGREE");
		agreelbl.setForeground(Color.RED);
		agreelbl.setBounds(46, 65, 47, 14);
		panel_1.add(agreelbl);
		agreelbl.setVisible(false);
		JRadioButton rdG = new JRadioButton("PET Scan");
		rdG.setBounds(22, 213, 137, 20);
		panel.add(rdG);
		JButton btnNewButton = new JButton("Next");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				total=0;
				counter=0;
				if(rdA.isSelected())
				{
					total=total+500.00;
					counter++;
				}
				if(rdB.isSelected())
				{
					total=total+2000.00;
					counter++;
				}
				if(rdC.isSelected())
				{
					total=total+1200.00;
					counter++;
				}
				if(rdD.isSelected())
				{
					total=total+263.00;
					counter++;
				}
				if(rdF.isSelected())
				{
					total=total+102.00;
					counter++;
				}
				if(rdG.isSelected())
				{
					total=total+5000.00;
					counter++;
				}
				if(rdH.isSelected())
				{
					String totalAmount=txtOther.getText();
					double amountTotal=Double.parseDouble(totalAmount);
					total=total+amountTotal;
					counter++;
				}
				//NumberFormat formatter = NumberFormat.getCurrencyInstance();
				String amount=Double.toString(total);
				//String amount2=formatter.format(amount);
				
				if(counter>0)
				{
			
				txtTotal.setText(" $ "+amount);
				panel_1.setVisible(true);
				btnNewButton.setText("Update Values");
				}
				else
				{}
				
				
			}
		});
		btnNewButton.setBounds(254, 212, 125, 23);
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
					agreelbl.setVisible(true);
					
					
					
					
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
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtID, txtDOB, btnNext1, txtTotal, rdH, txtOther, btnNewButton, btnSubmit, btnExit}));
		
		btnNext1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String ID =txtID.getText();
				String DOB=txtDOB.getText();
				if(ID.equals("")||DOB.equals("")||DOB.equals("MM/DD/YYYY"))
				{
					
				}
				else
				{
				
				ResultSet cur;
				try {
					String sql=("select * from patientInfo where ID="+ID+" && DOB="+DOB);
					cur = stmt.executeQuery(sql);
					//openSQL
					while(cur.next())
					{
					panel.setVisible(true);
					btnNext1.setVisible(false);
					String fname=cur.getString("fname");
					String lname=cur.getString("lname");
					String fullname=fname+" "+lname;
					txtName.setText(fullname);
					txtID.setEditable(false);
					txtDOB.setEditable(false);
					
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
