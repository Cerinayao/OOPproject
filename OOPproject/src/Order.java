import java.io.Serializable;

public class Order implements Serializable{
	
	private String investorName;
	private String type;//"Buy" or "Sell"
	private String stockName;
	private double targetPrice;
	private int volume;
	
	public Order(String type, double targetPrice, int volume, String stockName, String investorName) {
        this.type = type;
        this.targetPrice = targetPrice;
        this.volume = volume;
        this.stockName = stockName;
        this.investorName=investorName;
    }
	
    @Override
    public String toString() {
        return investorName+
                "," + type +
                "," + stockName +
                "," + volume +
                "," + targetPrice ;
    }
//  public String toString() {
//  return "Order{" +"investor="+investorName+
//          "type=" + type +
//          ", targetPrice=" + targetPrice +
//          ", volume=" + volume +
//          ", stockName='" + stockName + '\'' +
//          '}';
//}
    
    

    public String getInvestorName() {
		return investorName;
	}

	public void setInvestorName(String investorName) {
		this.investorName = investorName;
	}

	public String getType() {
        return type;
    }

    public double getTargetPrice() {
        return targetPrice;
    }

    public int getVolume() {
        return volume;
    }

    public String getStockName() {
        return stockName;
    }

	public void setType(String type) {
		this.type = type;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public void setTargetPrice(double targetPrice) {
		this.targetPrice = targetPrice;
	}

	public void setVolume(int volumes) {
		this.volume = volumes;
	}

}
