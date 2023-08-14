//import java.io.FileOutputStream;
//
//public class MainGUI {
//    public static void main(String[] args) {
////        // Create an investor
////        Investor investor = new Investor("InvestorOnly");
////
////        // View the initial portfolio (should be empty)
////        investor.viewPortfolio();
////
////        // View the initial stock market
////        investor.viewStockMarket();
////
////        // Add a stock to the investor's portfolio
////        investor.addToPortfolio("AAPL", investor.getStockMarket());
////        investor.placeOrder("BUY","AAPL", 100);
////
////        // View the updated portfolio
////        investor.viewPortfolio();
////        
////        Broker broker = new Broker(investor);
////
////        // Process the investor's orders
////        broker.processOrders(investor);
////
////        // View the updated portfolio after orders are processed
////        investor.viewPortfolio();
////        
////        investor.serializeAll();
//   
//    }
//}
import java.awt.EventQueue;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class MainGUI extends JFrame implements ActionListener{
    private JButton InvestorButton, BrokerButton;
    private JPanel contentPane;
    private JPanel ButtonPane;

    public MainGUI() {
        setTitle("Stock Trading System");
        setSize(300, 200); // Adjusted the size for a bit more vertical space
        setBounds(100, 100, 450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        InvestorButton = new JButton("Investor Portal");
        BrokerButton = new JButton("Broker Portal");
        InvestorButton.addActionListener(this);	
        BrokerButton.addActionListener(this);
        ButtonPane = new JPanel(new GridLayout(3, 3, 10, 20)); // Added vertical gap of 10 pixels
        ButtonPane.add(BrokerButton);
        ButtonPane.add(InvestorButton);
        contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(30, 10, 10, 10));
        contentPane.add(ButtonPane, BorderLayout.CENTER);
        add(contentPane);
        setLocationRelativeTo(null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == InvestorButton) {
            btn_investor_click();
        } else if (e.getSource() == BrokerButton) {
            btn_broker_click();
        } 
    }
    
	public void btn_investor_click() {
		Investor investor = new Investor("Investor1");
        InvestorGUI investorgui= new InvestorGUI(investor);
        investorgui.show();
    }

    public void btn_broker_click() {
    	Investor investor = new Investor("Investor1");
        BrokerGUI brokergui=new BrokerGUI(investor);
        brokergui.show();
    }
    
    public static void main(String[] args) {
        MainGUI gui = new MainGUI();
        gui.setVisible(true);
    }
}

