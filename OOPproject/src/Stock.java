import java.io.Serializable;
import java.util.Random;

public class Stock implements Serializable {
    private static final long serialVersionUID = 1L;

    private double price;
    private String stockName;
    private int volume;
    private Random random = new Random();

    public Stock(double price, String stockName, int volume) {
        this.stockName = stockName;
        this.price = price;
        this.volume = volume;
    }

    public void buyShares(int numShares) {
        price += 0.1;
        volume += numShares;
        applyRandomMarketVolatility();
    }

    public void sellShares(int numShares) {
        price -= 0.1;
        volume -= numShares;
        applyRandomMarketVolatility();
    }

    private void applyRandomMarketVolatility() {
        double volatilityFactor = 1 + (random.nextDouble() - 0.5) * 0.1; // Adjust volatility range as needed
        price *= volatilityFactor;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stockName='" + stockName + '\'' +
                ", price=" + price +
                ", volume=" + volume +
                '}';
    }
}
