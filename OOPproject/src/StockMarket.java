import java.io.*;
import java.util.ArrayList;

public class StockMarket implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private ArrayList<Stock> StockList;
    
    public StockMarket(ArrayList<Stock> StockList) {
        this.StockList = StockList;
    }
    
    public void placeOrder(Order order) {
        for (Stock stock : this.getStockList()) {
            if (stock.getStockName().equals(order.getStockName())) {
                if (order.getType().equalsIgnoreCase("Buy")) {
                    stock.buyShares(order.getVolume());
                } else {
                    stock.sellShares(order.getVolume());
                }
            }
        }
    }
    
    public ArrayList<Stock> getStockList() {
        return StockList;
    }
    public Stock getStock(String stockName) {
    	for (Stock stock : this.getStockList()) {
            if (stock.getStockName().equals(stockName)) {
            	return stock;
            }
    	}
    	return null;
    }
    
    public void setStockList(ArrayList<Stock> stockList) {
        StockList = stockList;
    }
    public static StockMarket deserializeStockMarket() {
        String fileName = "StockMarket.ser";
        try (FileInputStream fileIn = new FileInputStream(fileName);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            StockMarket stockMarket = (StockMarket) objectIn.readObject();
            System.out.println("Stock market deserialized from " + fileName);
            return stockMarket;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("StockMarket file not found. Initializing with default stocks.");

            // Create a new StockMarket instance with the initial stock list
            ArrayList<Stock> stockList = new ArrayList<>();
            Stock aapl = new Stock(177.46, "AAPL", 26245690);
            Stock googl = new Stock(128.79, "GOOGL", 9705128);
            Stock amzn = new Stock(138.28, "AMZN", 24566373);
            Stock msft = new Stock(320.25, "MSFT", 12503370);
            Stock tsla = new Stock(240.97, "TSLA", 67083026);
            stockList.add(aapl);
            stockList.add(googl);
            stockList.add(amzn);
            stockList.add(msft);
            stockList.add(tsla);
            StockMarket newStockMarket = new StockMarket(stockList);

            // Serialize the new StockMarket instance to the file
            newStockMarket.serializeStockMarket();

            return newStockMarket;
        }
    }
    

    public void serializeStockMarket() {
        String fileName = "StockMarket.ser";
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(this);
            System.out.println("Stock market serialized to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
