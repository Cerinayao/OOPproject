import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;


public class SellGUI extends JFrame implements Serializable{
	private JPanel contentPane;
	private JButton placeOrderBtn, closeBtn;
	private JLabel lblTarget, lblVolume, lblTitle;
	private JTextField txtTarget, txtVolume;
	
	public SellGUI(String stockname, Investor investor) {
		
		setTitle("Sell Stock");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        contentPane.setLayout(null);
        
        
        lblTitle= new JLabel("You select "+stockname+" to SELL."+" Please enter the volume:");
        lblTitle.setBounds(41, 16, 500, 16);
		contentPane.add(lblTitle);
		
		lblVolume = new JLabel("Volume");
		lblVolume.setBounds(41, 55, 120, 16);
		contentPane.add(lblVolume);
		
		lblTarget = new JLabel("Target Price");
		lblTarget.setBounds(41, 92, 120, 16);
		contentPane.add(lblTarget);
		
		txtVolume = new JTextField();
		txtVolume.setBounds(120, 50, 200, 26);
		contentPane.add(txtVolume);
		txtVolume.setColumns(20);
		
		txtTarget = new JTextField();
		txtTarget.setBounds(120, 87, 200, 26);
		contentPane.add(txtTarget);
		txtTarget.setColumns(20);
		
		placeOrderBtn =new JButton("Place Order");
		closeBtn =new JButton("Close");
		placeOrderBtn.setBounds(150, 140, 150, 30);
		contentPane.add(placeOrderBtn);
		closeBtn.setBounds(150, 180, 150, 30);
		contentPane.add(closeBtn);
		
		placeOrderBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btn_placeorder_click(investor, stockname);
			
			}
			
		});
		
		closeBtn.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        dispose();
		    }
		});
		
		
		
		setLayout(new BorderLayout());
        add(contentPane, BorderLayout.CENTER);
        setVisible(true);
	}
	
	public void btn_placeorder_click(Investor investor, String stockname) {
		
		try {	int volume= Integer.parseInt(txtVolume.getText());
		double price=Double.parseDouble(txtTarget.getText());
		if(volume>=0 & price>=0) {
			investor.placeOrder("Sell",price, stockname, volume);
			investor.serializeOrderBook();
			JOptionPane.showMessageDialog(null, "You place the order successfully! Please wait for the broker to excute your order.");
		}
		else {
			JOptionPane.showMessageDialog(null, "Please enter valid input");
		}
	
		}catch(IllegalArgumentException i) {
			JOptionPane.showMessageDialog(null, "Please enter valid input");
		}	
    }
	
}
