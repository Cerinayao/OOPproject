import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.awt.EventQueue;

public class InvestorGUI extends JFrame implements Serializable{
	private JPanel contentPanel, buttonPanel;
    private JTable table;
    private JButton buyBtn, sellBtn;
    private JButton closeBtn;
    
    public InvestorGUI(Investor investor){
    	 
    	 
    	 setTitle("Investor Interface");
         setSize(650, 400);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
         contentPanel = new JPanel(new BorderLayout());
         contentPanel.setBorder(new EmptyBorder(15, 15, 20, 15));
         
         //investor portfolio table
         String[] columnNames = {"stock name", "volumn","market price"};
         DefaultTableModel model = new DefaultTableModel(columnNames, 0);
         table = new JTable(model);
         
         displayTable(model, investor);
         
         JScrollPane scrollPane = new JScrollPane(table);
         JPanel tablePane= new JPanel();
         JLabel lblPortfolio =new JLabel("Investor Portfolio");
         tablePane.add(lblPortfolio, BorderLayout.NORTH);
         tablePane.add(scrollPane,BorderLayout.CENTER);
         contentPanel.add(tablePane, BorderLayout.CENTER);
         
         //button setup
         buttonPanel = new JPanel(new GridLayout(3, 1, 20, 70));
         buttonPanel.setBorder(new EmptyBorder(30,0,15,5));
         buyBtn = new JButton("Buy Stock");
         sellBtn = new JButton("Sell Stock");
         closeBtn= new JButton("Close");
         buttonPanel.add(buyBtn);
         buttonPanel.add(sellBtn);
         buttonPanel.add(closeBtn);
         contentPanel.add(buttonPanel, BorderLayout.EAST);
         
         setLayout(new BorderLayout());
         add(contentPanel, BorderLayout.CENTER);
         setVisible(true);
         
    	 sellBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btn_sell_click(investor, model);
				
			}
    		 
    	 });
    	 
         
    	 closeBtn.addActionListener(new ActionListener() {
    		    @Override
    		    public void actionPerformed(ActionEvent e) {
    		        dispose();
    		    }
    		});
    	 
    	 buyBtn.addActionListener(new ActionListener() {
 		    @Override
 		    public void actionPerformed(ActionEvent e) {
 		        btn_buy_click(investor);
 		        
 		    }
 		});
      
         
         
    }
    public void displayTable( DefaultTableModel model, Investor investor) {
    	for (Stock stock: investor.getPortfolio()) {
			String marketprice="";
			for (Stock s: investor.getStockMarket().getStockList()) {
				if (s.getStockName().equals(stock.getStockName())) {
					marketprice=Double.toString(s.getPrice());
					break;
				}
			}
			String rowcontent= stock.toStringportfolio()+","+marketprice;
			String [] data =rowcontent.split(",");
			model.addRow(data);
		}
    		
    	}
    
    public void btn_buy_click(Investor investor) {
		BuyGUI buy= new BuyGUI(investor);
	        buy.show();
    }
    
    public void btn_sell_click(Investor investor, DefaultTableModel model) {
    	int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String stockname = (String) model.getValueAt(selectedRow, 0);		            
            
            SellGUI sell= new SellGUI(stockname, investor);
            sell.show();
            
        } else {
            JOptionPane.showMessageDialog(null, "Please select a stock to sell.");
        }
    }

    
    
}



