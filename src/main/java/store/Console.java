package store;

import java.util.Scanner;

public class Console {
    protected final Scanner input = new Scanner(System.in);
    public void preventSkipScannerIssue(){
        input.nextLine();
    }

    public int askAndGetIntValue(String question) {
        System.out.print(question);
        return input.nextInt();
    }

    public long askAndGetLongValue(String question) {
        System.out.print(question);
        return input.nextLong();
    }

    public double askAndGetDoubleValue(String question) {
        System.out.print(question);
        return input.nextDouble();
    }

    public String askAndGetStringValue(String question) {
        System.out.print(question);
        return input.nextLine();
    }

    public String askAndGetBrand(){
        System.out.println("0-> Samsung\t"+ "1-> Lenovo\t"+ "2-> Apple\t"+ "3-> Huawei\t"+ "4-> Casper\t"+ "5-> Asus\t"+ "6-> HP\t\t"+ "7-> Xiaomi\t"+ "8-> Monster\t");
        System.out.print("Enter a number of the brand you want: ");
        int brandNumber = input.nextInt();
        preventSkipScannerIssue();
        return switch (brandNumber){
            case 0 -> "Samsung";
            case 1 -> "Lenovo";
            case 2 -> "Apple";
            case 3 -> "Huawei";
            case 4 -> "Casper";
            case 5 -> "Asus";
            case 6 -> "HP";
            case 7 -> "Xiaomi";
            case 8 -> "Monster";
            default -> askAndGetBrand();
        };
    }
}


