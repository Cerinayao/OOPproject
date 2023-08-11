import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Broker {
    private Investor investor;

    public Broker() {
    	
    }
    
    

    public void viewOrderBook() {
        System.out.println("Order Book:");
        for (Order order :investor.getOrderBook()) {
            System.out.println(order.toString());
        }
    }

    public void processOrders(Investor investor) {
        Iterator<Order> iterator = investor.getOrderBook().iterator();
        while (iterator.hasNext()) {
            Order order = iterator.next();
            executeOrder(investor, order);
            iterator.remove();
        }
        investor.serializeOrderBook();
    }

    private void executeOrder(Investor investor, Order order) {
        String type = order.getType();
        String stockName = order.getStockName();
        int volume = order.getVolume();

        if ("Buy".equalsIgnoreCase(type)) {
            investor.buyToPortfolio(stockName, volume);
        } else if ("Sell".equalsIgnoreCase(type)) {
            investor.sellFromPortfolio(stockName, volume);
        }//portfolio change
        investor.getStockMarket().placeOrder(order);//market change
        System.out.println("Order executed for Investor: " + investor.getInvestorName());
    }
    
}
