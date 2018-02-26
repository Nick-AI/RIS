package Billing;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class confirmedBilling extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					confirmedBilling frame = new confirmedBilling();
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
	public confirmedBilling() {
		setTitle("Confirmed");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(650, 350, 516, 348);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(34, 139, 34));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("From the Finance Department:");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel.setBounds(106, 11, 289, 24);
		contentPane.add(lblNewLabel);
		
		JTextPane txtpnTheBillingRequest = new JTextPane();
		txtpnTheBillingRequest.setForeground(new Color(0, 0, 0));
		txtpnTheBillingRequest.setEditable(false);
		txtpnTheBillingRequest.setBackground(new Color(34, 139, 34));
		txtpnTheBillingRequest.setFont(new Font("Times New Roman", Font.BOLD, 16));
		txtpnTheBillingRequest.setText("The Billing Request has successfully been sent to the Finance Department. The bill will be processed.");
		txtpnTheBillingRequest.setBounds(34, 60, 437, 54);
		contentPane.add(txtpnTheBillingRequest);
		
		JTextPane txtpnPleaseContactThe = new JTextPane();
		txtpnPleaseContactThe.setForeground(new Color(0, 0, 0));
		txtpnPleaseContactThe.setEditable(false);
		txtpnPleaseContactThe.setBackground(new Color(34, 139, 34));
		txtpnPleaseContactThe.setText("Please contact the Finance Department if you have any further questions.");
		txtpnPleaseContactThe.setFont(new Font("Times New Roman", Font.BOLD, 16));
		txtpnPleaseContactThe.setBounds(34, 146, 437, 54);
		contentPane.add(txtpnPleaseContactThe);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setBounds(196, 238, 89, 23);
		contentPane.add(btnClose);
	}

}
