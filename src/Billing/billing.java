package Billing;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;
import java.awt.Component;
import javax.swing.Box;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
//import org.eclipse.wb.swing.FocusTraversalOnArray;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JSeparator;

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
	private JTable table;
	private JRadioButton rdA;
	private JRadioButton rdB;
	private JRadioButton rdC;
	private JRadioButton rdD;
	private JRadioButton rdE;
	private JRadioButton rdF;
	private JRadioButton rdG;
	private JRadioButton rdH;
	
	/**
	 * Launch the application.
	 * @return 
	 */
	//public static void main(String[] args) {
	public static void billingStart() {
		//EventQueue.invokeLater(new Runnable() {
			//@Override
			//public void run() {
				try {
					Class.forName("com.mysql.jdbc.Driver");  
					con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/risystem","root","ResidenceLife1873!"); 
					stmt=(Statement) con.createStatement();  
					billing frame = new billing();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			//}
		//});
	}

	/**
	 * Create the frame.
	 */
	public billing() {
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
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
		lblServicesIncurred.setBounds(714, 65, 137, 14);
		panel.add(lblServicesIncurred);
		
		rdA = new JRadioButton("X-Ray");
		rdA.setBounds(714, 86, 137, 23);
		panel.add(rdA);
		
		rdB = new JRadioButton("MRI");
		rdB.setBounds(714, 112, 137, 23);
		panel.add(rdB);
		
		rdC = new JRadioButton("CT Scan");
		rdC.setBounds(714, 138, 137, 23);
		panel.add(rdC);
		
		rdD = new JRadioButton("Ultrasound");
		rdD.setBounds(714, 164, 137, 23);
		panel.add(rdD);
		
		rdF = new JRadioButton("Mammography");
		rdF.setBounds(714, 213, 137, 23);
		panel.add(rdF);
		
		JLabel label = new JLabel("");
		label.setBounds(22, 293, 46, 14);
		panel.add(label);
		
		JLabel lblSubtotal = new JLabel("TOTAL: ");
		lblSubtotal.setBounds(872, 249, 101, 14);
		panel.add(lblSubtotal);
		
		txtTotal = new JTextField();
		txtTotal.setEditable(false);
		txtTotal.setBounds(872, 265, 101, 20);
		panel.add(txtTotal);
		txtTotal.setColumns(10);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setBounds(22, 65, 336, 14);
		panel.add(horizontalStrut);
		
		txtOther = new JTextField();
		txtOther.setBounds(913, 113, 153, 20);
		panel.add(txtOther);
		txtOther.setColumns(10);
		
		rdG = new JRadioButton("Other Incurred Amounts");
		rdG.setBounds(903, 86, 191, 23);
		panel.add(rdG);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_1.setBounds(714, 317, 451, 146);
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
		JRadioButton rdE = new JRadioButton("PET Scan");
		rdE.setBounds(714, 190, 137, 20);
		panel.add(rdE);
		
		
		JButton btnNewButton = new JButton("Next");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
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
				if(rdE.isSelected())
				{
					total=total+5000.00;
					counter++;
				}
				if(rdG.isSelected())
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
		btnNewButton.setBounds(935, 189, 125, 23);
		panel.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(185, 167, 101, 309);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setColumnHeaderView(table);
		scrollPane.setColumnHeaderView(table);
		scrollPane.setViewportView(table);
		JLabel lblBills = new JLabel("Bills");
		lblBills.setHorizontalAlignment(SwingConstants.CENTER);
		lblBills.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblBills.setBounds(22, 95, 417, 14);
		panel.add(lblBills);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(52, 120, 352, 2);
		panel.add(separator);
		
		JLabel lblSelectABill = new JLabel("Select a Bill ID to Begin Billing");
		lblSelectABill.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectABill.setBounds(148, 142, 176, 14);
		panel.add(lblSelectABill);
		
		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row=table.getSelectedRow();
				Object a=table.getValueAt(row, 0);
				String name=a.toString();
				System.out.println(name);
				try {
					String totalList=null;
					ResultSet list=stmt.executeQuery("select procedureList from billing where billID="+name);
					while(list.next())
					{
						totalList=list.getString("procedureList");
					}
					System.out.println(totalList);
					
					List<String> elephantList = Arrays.asList(totalList.split(","));
					for(int i=0;i<elephantList.size();i++)
					{
						selecter(elephantList.get(i));
						//System.out.println(elephantList.get(i));
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnNext.setBounds(313, 245, 89, 23);
		panel.add(btnNext);
		
		
		btnSubmit.addActionListener(new ActionListener() {
			@Override
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
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false); //you can't see me!
				dispose();
			}
		});
		btnExit.setBounds(994, 49, 83, 20);
		contentPane.add(btnExit);
		
		JLabel lblMmddyyyy = new JLabel("YYYY-MM-DD");
		lblMmddyyyy.setBounds(156, 124, 122, 14);
		contentPane.add(lblMmddyyyy);
		//setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtID, txtDOB, btnNext1, txtTotal, rdG, txtOther, btnNewButton, btnSubmit, btnExit}));
		
		btnNext1.addActionListener(new ActionListener() {
			@Override
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
					String sql=("select * from patient where patientID='"+ID+"' && dateofBirth='"+DOB+"';");
					cur = stmt.executeQuery(sql);
					//openSQL
					while(cur.next())
					{
					panel.setVisible(true);
					//btnNext1.setVisible(false);
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
				try {
					filledRequest();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			
				
			}
		});
		
		
		
		
		
	}
	public void filledRequest() throws SQLException
	{
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Bill ID"
			}
		));
		

			//table_1.getColumnModel().getColumn(3).setPreferredWidth(100);
		String sql="select * from billing where patientID='"+txtID.getText()+"' && inProgress="+0+";";
		ResultSet cur = stmt.executeQuery(sql);
		//System.out.println("select * from loginLog");
		//int counter2=0; 
		try {
			while(cur.next())
			{
				String A=cur.getString("billID");
					
				DefaultTableModel model2 = (DefaultTableModel) table.getModel();
				model2.addRow(new Object[]{A});
				
				
				//counter2++;
			}
			//lblYouHave.setText("You have "+counter2+" more Image request(s) for today.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//cur1 = stmt.executeQuery("Select * from resident where ID=");
			
			
		
		
	}
	
	
	public void selecter(String word)
	{
		 switch (word) {
         case "XRAY": rdA.setSelected(true);
                  break;
         case "MRI":  rdB.setSelected(true);
                  break;
         case "CT": rdC.setSelected(true);
                  break;
         case "ULTRA":  rdD.setSelected(true);
                  break;
         case "PET":  rdE.setSelected(true);
                  break;
         case "MAMMO":  rdF.setSelected(true);
        
         default: rdG.setSelected(true);
                  break;
		 }
		
		
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
