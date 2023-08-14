import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

public class BuyGUI extends JFrame implements Serializable{
	
		private JPanel contentPane,buyPane;
		private JTable table;
		private JButton placeOrderBtn, closeBtn;
		private JLabel lblVolume, lblTitle, lblTarget;
		private JTextField txtVolume, txtTarget;
		
		public BuyGUI(Investor investor) {
			
			setTitle("Buy Stock");
	        setSize(600, 600);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
	        contentPane = new JPanel(new BorderLayout());
	        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
	        
	         String[] columnNames = {"stock name", "market price", "volumn"};
	         DefaultTableModel model = new DefaultTableModel(columnNames, 0);
	         table = new JTable(model);
	         
	         displayTable(model, investor);
	         
	         JScrollPane scrollPane = new JScrollPane(table);
	         JPanel tablePane= new JPanel();
	         JLabel lblPortfolio =new JLabel("Stock Market");
	         tablePane.add(lblPortfolio, BorderLayout.EAST);
	         tablePane.add(scrollPane,BorderLayout.CENTER);
	         contentPane.add(tablePane, BorderLayout.CENTER);
	        
	        lblTitle = new JLabel("Please select the stock you want to buy and enter the volumn and target price:");
	        lblTitle.setBounds(50, 20, 500, 16);
			lblVolume = new JLabel("Volume");
			lblVolume.setBounds(50, 55, 100, 16);
			txtVolume = new JTextField();
			txtVolume.setBounds(170, 50, 200, 26);
			txtVolume.setColumns(20);
			lblTarget = new JLabel("Target Price");
			lblTarget.setBounds(50, 85, 100, 16);
			txtTarget = new JTextField();
			txtTarget.setBounds(170, 80, 200, 26);
			txtTarget.setColumns(20);
			JPanel volumePane= new JPanel();
			volumePane.setSize(560, 120);
			volumePane.setLayout(null);
			volumePane.add(lblTitle);
			volumePane.add(lblVolume);
			volumePane.add(txtVolume);
			volumePane.add(lblTarget);
			volumePane.add(txtTarget);
			
			placeOrderBtn =new JButton("Place Order");
			closeBtn =new JButton("Close");
			JPanel buttonPanel= new JPanel(new GridLayout(2, 1, 0, 20));
			buttonPanel.setSize(300, 150);
			buttonPanel.setBorder(new EmptyBorder(0,50,30,50));
			buttonPanel.add(placeOrderBtn);
			buttonPanel.add(closeBtn);
			
			buyPane= new JPanel(new GridLayout(2, 1, 0, 30));
			buyPane.add(volumePane);
			buyPane.add(buttonPanel);
			contentPane.add(buyPane, BorderLayout.SOUTH);
			
			
			placeOrderBtn.addActionListener(new ActionListener() {
	
				@Override
				public void actionPerformed(ActionEvent e) {
					btn_placeorder_click(investor, model);
				
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
		
		public void displayTable( DefaultTableModel model, Investor investor) {
    		for (Stock stock: investor.getStockMarket().getStockList()) {
    			String [] data =stock.toString().split(",");
    			model.addRow(data);
    		}
    	}
		
		public void btn_placeorder_click(Investor investor, DefaultTableModel model) {
			try {	
				int selectedRow = table.getSelectedRow();
		        if (selectedRow >= 0) {
		            String stockname = (String) model.getValueAt(selectedRow, 0);		            	
					int volume= Integer.parseInt(txtVolume.getText());
					double price=Double.parseDouble(txtTarget.getText());
					if(volume>=0 & price>=0) {
						investor.placeOrder("Buy", price, stockname, volume);
						investor.serializeOrderBook();
						JOptionPane.showMessageDialog(null, "You place the order successfully! Please wait for the broker to excute your order.");
					}
					else {
						JOptionPane.showMessageDialog(null, "Please enter valid input");
					}
				
				} else {
		            JOptionPane.showMessageDialog(null, "Please select a stock to sell.");
		        }
			
		}catch(IllegalArgumentException i) {
			JOptionPane.showMessageDialog(null, "Please enter valid input");
		}	
			
	    }
		

}
