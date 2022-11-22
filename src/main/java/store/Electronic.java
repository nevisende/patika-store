package store;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Comparator;

public class Electronic extends Product {
    protected static final ArrayList<Electronic> instances = new ArrayList<>();
    private String electronicId;
    private ObjectId generatedId;
    private int memoryStorage;
    private double screenSize;
    private int batteryPower;
    private int ram;

    public static void createElectronic(ObjectId id , double unitPrice, int discountRate, long stockAmount, String brandName, String productName, double screenSize, int batteryPower, int ram, String color, int memoryStorage){
        Electronic electronic = new Electronic(id, unitPrice,  discountRate,  stockAmount,  brandName,  productName,  screenSize,  batteryPower,  ram,  color,  memoryStorage);
        instances.add(new WeakReference<>(electronic).get());
    }

    public static Electronic createElectronic(double unitPrice, int discountRate, long stockAmount, String brandName, String productName, double screenSize, int batteryPower, int ram, String color, int memoryStorage){
        Electronic electronic = new Electronic( unitPrice,  discountRate,  stockAmount,  brandName,  productName,  screenSize,  batteryPower,  ram,  color,  memoryStorage);
        instances.add(new WeakReference<>(electronic).get());
        return  electronic;
    }

    Electronic() {
        generatedId = new ObjectId();
        setElectronicId(generatedId);
    }

    private Electronic(double unitPrice, int discountRate, long stockAmount, String brandName, String productName, double screenSize, int batteryPower, int ram, String color, int memoryStorage) {
        generatedId = new ObjectId();
        setElectronicId(generatedId);
        this.setUnitPrice(unitPrice);
        this.setDiscountRate(discountRate);
        this.setStockAmount(stockAmount);
        this.setBrandName(brandName);
        this.setProductName(productName);
        this.setScreenSize(screenSize);
        this.setBatteryPower(batteryPower);
        this.setRam(ram);
        this.setColor(color);
        this.setBrandId();
        this.setMemoryStorage(memoryStorage);
    }

    private Electronic(ObjectId id, double unitPrice, int discountRate, long stockAmount, String brandName, String productName, double screenSize, int batteryPower, int ram, String color, int memoryStorage) {
        setElectronicId(id);
        this.setUnitPrice(unitPrice);
        this.setDiscountRate(discountRate);
        this.setStockAmount(stockAmount);
        this.setBrandName(brandName);
        this.setProductName(productName);
        this.setScreenSize(screenSize);
        this.setBatteryPower(batteryPower);
        this.setRam(ram);
        this.setColor(color);
        this.setBrandId();
        this.setMemoryStorage(memoryStorage);
    }

    // GETTERS
    public String getElectronicId() {
        return this.electronicId;
    }

    public int getMemoryStorage() {
        return this.memoryStorage;
    }

    public double getScreenSize() {
        return this.screenSize;
    }

    public int getBatteryPower() {
        return this.batteryPower;
    }

    public int getRam() {
        return this.ram;
    }

    // SETTERS
    private void setElectronicId(ObjectId generatedId) {
        this.electronicId = String.valueOf(generatedId);
    }

    public void setMemoryStorage(int memoryStorage) {
        this.memoryStorage = memoryStorage;
    }

    public void setScreenSize(double screenSize) {
        this.screenSize = screenSize;
    }

    public void setBatteryPower(int batteryPower) {
        this.batteryPower = batteryPower;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    // OTHER METHODS

    public Document generateMongoDBDocument() {
        return new Document("_id", generatedId)
                .append("brand_name", getBrandName())
                .append("brand_id", getBrandId())
                .append("unit_price", getUnitPrice())
                .append("discount_rate", getDiscountRate())
                .append("stock_amount", getStockAmount())
                .append("product_name", getProductName())
                .append("memory_storage", getMemoryStorage())
                .append("screen_size", getScreenSize())
                .append("battery_power", getBatteryPower())
                .append("ram", getRam())
                .append("color",getColor());
    }

    public static void printLine(){
        System.out.format("----------------------------------------------------------------------------" +
                "-----------------------------------------------------------------------------------------" +
                "--------------------------------------------%n");
    }
    public static void printTableHeader() {
        printLine();
        System.out.format("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tCell Phones        %n\n");
        System.out.format("| %-25s| %-15s| %-15s| %-15s| %-15s| %-15s| %-15s| %-15s| %-15s| %-15s| %-15s| %-15s|%n",
                "Product Id",
                "Product Name",
                "Brand Id",
                "Brand Name",
                "Unit Price",
                "Discount Rate",
                "Stock Amount",
                "Memory Storage",
                "Screen Size",
                "Battery Power",
                "RAM",
                "Color");
        printLine();
    }
    public void printDetails() {
        System.out.format("| %-25s| %-15s| %-15s| %-15s| %-15s| %-15s| %-15s| %-15s| %-15s| %-15s| %-15s| %-15s|%n",
                getElectronicId(),
                getProductName(),
                getBrandId(),
                getBrandName(),
                getUnitPrice(),
                getDiscountRate(),
                getStockAmount(),
                getMemoryStorage(),
                getScreenSize(),
                getBatteryPower(),
                getRam(),
                getColor());
    }

    public static void printAllInstances(){
        instances.sort(Comparator.comparing(Electronic::getBrandName));
        instances.forEach(Electronic::printDetails);
    }

    public static void printElectronicArrayList(ArrayList<Electronic> electronicsArray) {
        for (Electronic electronic : electronicsArray) {
            electronic.printDetails();
        }
    }

    public static Electronic createElectronicFromTerminal(){
        Console console = new Console();
        double unitPrice = console.askAndGetDoubleValue("Unit price: ");
        int discountRate = console.askAndGetIntValue("Discount Rate(0-100): ");
        while (discountRate<0 || discountRate >100){
            System.out.println("Please enter a valid number!");
            discountRate = console.askAndGetIntValue("Discount Rate(0-100): ");
        }
        long stockAmount = console.askAndGetLongValue("Stock amount of the product: ");
        console.preventSkipScannerIssue();
        String brandName = console.askAndGetBrand();
        String productName = console.askAndGetStringValue("Product Name: ");
        double screenSize = console.askAndGetDoubleValue("Screen size: ");
        int batteryPower = console.askAndGetIntValue("Battery Power: ");
        int ram = console.askAndGetIntValue("RAM: ");
        console.preventSkipScannerIssue();
        String color = console.askAndGetStringValue("Color: ");
        int memoryStorage = console.askAndGetIntValue("Memory storage: ");

        return createElectronic( unitPrice, discountRate, stockAmount, brandName, productName, screenSize, batteryPower, ram, color, memoryStorage);
    }

    public static void createFromMongoDBDocument(Document document){
        ObjectId id = (ObjectId) document.get("_id");
        double unitPrice = (double) document.get("unit_price");
        int discountRate = (int) document.get("discount_rate");
        long stockAmount = (long) document.get("stock_amount");
        String brandName = (String) document.get("brand_name");
        String productName = (String) document.get("product_name");
        double screenSize = (double) document.get("screen_size");
        int batteryPower = (int) document.get("battery_power");
        int ram = (int) document.get("ram");
        String color = (String) document.get("color");
        int memoryStorage = (int) document.get("memory_storage");
        createElectronic(id, unitPrice, discountRate, stockAmount, brandName, productName, screenSize, batteryPower, ram, color, memoryStorage);
    }

    public static ArrayList<Electronic> filterBy(String type, String key){
        ArrayList<Electronic> result = new ArrayList<>();
        if(type.equals("id")) {
            instances.forEach(instance -> {
                if (instance.getElectronicId().equals(key)) {
                    result.add(instance);
                }
            });
        } else if(type.equals("brandName")){
            instances.forEach(instance -> {
                if (instance.getBrandName().equals(key)) {
                    result.add(instance);
                }
            });
        }
        return result;
    }
}