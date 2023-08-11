import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Investor implements Serializable {
    private static final long serialVersionUID = 1L;

    private String investorName;
    private List<Order> orderBook;
    private List<Stock> portfolio;
    private StockMarket stockMarket;

    public Investor(String investorName) {
        this.investorName = investorName;
        this.orderBook = deserializeOrderBook();
        this.portfolio = deserializePortfolio();
        this.stockMarket = StockMarket.deserializeStockMarket(); // Note the change here
    }

    public void placeOrder(String type, String stockName, double targetPrice, int volume) {
        Order order = new Order(type, targetPrice, volume, stockName, investorName);
        orderBook.add(order);
    }

    public void addToPortfolio(String stockName, StockMarket stockMarket) {
        boolean alreadyExists = false;
        for (Stock stock : portfolio) {
            if (stock.getStockName().equals(stockName)) {
                alreadyExists = true;
                break;
            }
        }
        if (!alreadyExists) {
            double price = 0; // Default price if stock not found in stock market
            for (Stock stock : stockMarket.getStockList()) {
                if (stock.getStockName().equals(stockName)) {
                    price = stock.getPrice();
                    break;
                }
            }
            Stock newStock = new Stock(price, stockName, 0);
            portfolio.add(newStock);
        }
    }
  
    public void buyToPortfolio(String stockName, int volume) {
        for (Stock stock : portfolio) {
            if (stock.getStockName().equals(stockName)) {
                int updatedVolume = stock.getVolume() +volume;
                stock.setVolume(updatedVolume);
                break;
            }
        }
    }
    public void sellFromPortfolio(String stockName, int volume) {
        for (Stock stock : portfolio) {
            if (stock.getStockName().equals(stockName)) {
                int updatedVolume = stock.getVolume() - volume;
                if (updatedVolume <= 0) {
                    portfolio.remove(stock);
                } else stock.setVolume(updatedVolume);
                break;
            }
        }
    }
    public void viewPorfolio() {
        System.out.println("Portfolio:");
        for (Stock stock : portfolio){
            System.out.println(stock.toString());
        }
    }
    
    public void viewStockMarket() {
        System.out.println("stock market:");
        for (Stock stock : stockMarket.getStockList()){
            System.out.println(stock.toString());
        }
    }

    public List<Order> getOrderBook() {
        return orderBook;
    }

    public List<Stock> getPortfolio() {
        return portfolio;
    }

    public String getInvestorName() {
    	return investorName;
    }
    public StockMarket getStockMarket() {
    	return stockMarket;
    }
    public void serializeOrderBook() {
        String fileName = "OrderBook.ser";
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(orderBook);
            System.out.println("Order book serialized to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public List<Order> deserializeOrderBook() {
        String fileName = "OrderBook.ser";
        List<Order> deserializedOrderBook = new ArrayList<>();
        try (FileInputStream fileIn = new FileInputStream(fileName);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            deserializedOrderBook = (List<Order>) objectIn.readObject();
            System.out.println("Order book deserialized from " + fileName);
        } catch (FileNotFoundException e) {
            // No file exists yet, create an empty list
            System.out.println("No OrderBook file found. Initializing an empty order book.");
            deserializedOrderBook = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return deserializedOrderBook;
    }

    public void serializePortfolio() {
        String fileName = "Portfolio.ser";
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(portfolio);
            System.out.println("Portfolio serialized to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public List<Stock> deserializePortfolio() {
        String fileName = "Portfolio.ser";
        List<Stock> deserializedPortfolio = new ArrayList<>();
        try (FileInputStream fileIn = new FileInputStream(fileName);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            deserializedPortfolio = (List<Stock>) objectIn.readObject();
            System.out.println("Portfolio deserialized from " + fileName);
        } catch (FileNotFoundException e) {
            // No file exists yet, create an empty list and serialize it
            System.out.println("No Portfolio file found. Initializing an empty portfolio.");
            deserializedPortfolio = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return deserializedPortfolio;
    }

    public void serializeAll() {
    	serializePortfolio();
    	serializeOrderBook();
    	stockMarket.serializeStockMarket();
    	
    }



}



