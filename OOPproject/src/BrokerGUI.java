import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class BrokerGUI extends JFrame implements Serializable{
	private JPanel contentPanel, buttonPanel;
    private JTable table;
    private JButton executeBtn;
    private JButton closeBtn;
    private JLabel lblTitle;
    
    public BrokerGUI(Investor investor) {
    	
    	Broker broker= new Broker(investor);
    	setTitle("Broker Interface");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        //order book table
        String[] columnNames = {"investor name", "type", "stock name", "volume", "target price", "market price"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        
        displayTable(model, investor);
        
        JScrollPane scrollPane = new JScrollPane(table);
        JPanel tablePane= new JPanel();
        JLabel lblPortfolio =new JLabel("                                                             Order Book");
        tablePane.add(scrollPane,BorderLayout.CENTER);
        contentPanel.add(tablePane, BorderLayout.CENTER);
        contentPanel.add(lblPortfolio, BorderLayout.NORTH);
        
        setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);
        setVisible(true);
        
        //button
        executeBtn =new JButton("Excute Order");
		closeBtn =new JButton("Close");
		lblTitle= new JLabel("Please select the order you want to excute");
		JPanel buttonPanel= new JPanel(new GridLayout(3, 1, 0, 10));
		buttonPanel.setSize(200, 150);
		buttonPanel.setBorder(new EmptyBorder(20,70,30,70));
		buttonPanel.add(lblTitle,BorderLayout.NORTH);
		buttonPanel.add(executeBtn);
		buttonPanel.add(closeBtn);
		contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
		closeBtn.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        dispose();
		    }
		});
		
		executeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			
				btn_execute_click(broker,investor, model);
			
			}
		});
    }
    
    public void displayTable( DefaultTableModel model, Investor investor) {
		for (Order order: investor.getOrderBook()) {
			String marketprice="";
			for (Stock stock: investor.getStockMarket().getStockList()) {
				if (order.getStockName().equals(stock.getStockName())) {
					marketprice=Double.toString(stock.getPrice());
					break;
				}
			}
			String rowcontent= order.toString()+","+marketprice;
			String [] data =rowcontent.split(",");
			model.addRow(data);
		}
	}
    
    public void btn_execute_click(Broker broker, Investor investor, DefaultTableModel model) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String investorname = (String) model.getValueAt(selectedRow, 0);
            String type = (String) model.getValueAt(selectedRow, 1);
            String stockname = (String) model.getValueAt(selectedRow, 2);
            int volume = Integer.parseInt((String) model.getValueAt(selectedRow, 3));
            double target = Double.parseDouble((String) model.getValueAt(selectedRow, 4));
            double market = Double.parseDouble((String) model.getValueAt(selectedRow, 5));

            Order selectorder = new Order(type, target, volume, stockname, investorname);

            if ("Buy".equalsIgnoreCase(type)) {
            	if(market > target) {
            		JOptionPane.showMessageDialog(null, "Market price is changing constantly...Automatically execute when it reaches the target price over a period of time.");
            	}
            	boolean ExecuteOrNot=true;
            	
            	for (int i=0; i<20; i++) {
            		if (market<= target) {
            			ExecuteOrNot=true;
            			break;
            		}
            		market = investor.getStockMarket().getStock(stockname).randomPrice();
            		ExecuteOrNot=false;
            	}
            	
               
                if(ExecuteOrNot) {
	                investor.addToPortfolio(stockname, investor.getStockMarket());
	                broker.executeOrder(investor, selectorder);
	                model.removeRow(selectedRow);
	                investor.RemoveFromOrderBook(selectorder);
	                JOptionPane.showMessageDialog(null, "You executed the order successfully! Investor can see the changes in the portfolio.");
	            }else {
	            	JOptionPane.showMessageDialog(null, "Market price doesn't reach investor's expectation. Execution failed.");
	            }
                
            } else if ("Sell".equalsIgnoreCase(type)) {
            	if(market < target) {
            		JOptionPane.showMessageDialog(null, "Market price is changing constantly...Automatically execute when it reaches the target price over a period of time.");
            	}
            	boolean ExecuteOrNot=true;
            	
            	for (int i=0; i<20; i++) {
            		if (market>= target) {
            			ExecuteOrNot=true;
            			break;
            		}
            		market = investor.getStockMarket().getStock(stockname).randomPrice();
            		ExecuteOrNot=false;
            	}
            	 if(ExecuteOrNot) {
            		 broker.executeOrder(investor, selectorder);
                     model.removeRow(selectedRow);
                     investor.RemoveFromOrderBook(selectorder);
                     JOptionPane.showMessageDialog(null, "You executed the order successfully! Investor can see the changes in the portfolio.");
 	            }else {
 	            	JOptionPane.showMessageDialog(null, "Market price doesn't reach investor's expectation. Execution failed.");
 	            }
                
            } else {
                JOptionPane.showMessageDialog(null, "Market price doesn't reach investor's target price. Execution failed.");
            }
            
            // Remove the executed order row from the displayed table

            investor.serializeAll();
            
        } else {
            JOptionPane.showMessageDialog(null, "Please select an order to execute.");
        }
    }
}
