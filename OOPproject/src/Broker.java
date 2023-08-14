import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Broker {
    private Investor investor;
    public Broker(Investor investor) {
    	this.investor=investor;
    }
    public void viewOrderBook() {
        System.out.println("Order Book:");
        for (Order order :investor.getOrderBook()) {
            System.out.println(order.toString());
        }
    }
//    public void processOrders(Investor investor) {
//        Iterator<Order> iterator = investor.getOrderBook().iterator();
//        while (iterator.hasNext()) {
//            Order order = iterator.next();
//            executeOrder(investor, order);
//            iterator.remove();
//        }
//     
//    }
    public void executeOrder(Investor investor, Order order) {
        String type = order.getType();
        String stockName = order.getStockName();
        int volume = order.getVolume();

        if ("Buy".equalsIgnoreCase(type)) {
        	investor.addToPortfolio(stockName, investor.getStockMarket());
            investor.buyToPortfolio(stockName, volume);
        } else if ("Sell".equalsIgnoreCase(type)) {
            investor.sellFromPortfolio(stockName, volume);
        }//portfolio change
        investor.getStockMarket().placeOrder(order);//market change
//        investor.RemoveFromOrderBook(order);
        System.out.println("Order executed for Investor: " + investor.getInvestorName());
    }
    public void randomizeMarket() {
    	for (Stock stock: this.investor.getStockMarket().getStockList()) {
    		stock.applyRandomMarketVolatility();
    	}
    }
    
}
