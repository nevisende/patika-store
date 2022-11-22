package store;


import java.util.Arrays;

public class Product {
    private static final String[] storedBrands = {"Samsung", "Lenovo", "Apple", "Huawei", "Casper", "Asus", "HP", "Xiaomi", "Monster"};

    private String brandId;
    private String brandName;
    private double unitPrice;
    private int discountRate;
    private long stockAmount;
    private String productName;
    private String color;


    // GETTERS
    public String getBrandId() {
        return brandId;
    }

    public String getBrandName() {
        return this.brandName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getDiscountRate() {
        return discountRate;
    }

    public long getStockAmount() {
        return stockAmount;
    }

    public String getProductName() {
        return productName;
    }

    public String getColor() {
        return color;
    }
    // SETTERS
    public void setBrandId() {
        int indexOfBrandName = Arrays.asList(storedBrands).indexOf(getBrandName());
        this.brandId = String.valueOf(indexOfBrandName);
    }

    public void setBrandName(String brandName) {
        if(Arrays.asList(storedBrands).contains(brandName)) {
            this.brandName = brandName;
        }
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setDiscountRate(int discountRate) {
        if(discountRate > 0 && discountRate < 100){
            this.discountRate = discountRate;
        }
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setStockAmount(long stockAmount) {
        this.stockAmount = stockAmount;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
