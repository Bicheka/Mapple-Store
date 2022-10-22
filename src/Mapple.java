import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.FileWriter;   // Import the FileWriter class
import java.io.BufferedWriter;
public class Mapple {
    public static ArrayList<Product> shoppingCart = new ArrayList<>();
    public static HashMap<String, Product> productsMap = new HashMap<>();
    public static void main(String[] args){

        System.out.println("Welcome to the Mapple store \n");
        menu();
    }

    public static void menu(){

        System.out.println("Main menu \nSelect an option ");
        System.out.println("1: See products");
        System.out.println("2: See shopping cart ");
        System.out.println("3: Exit\n");
        Scanner scanner = new Scanner(System.in);
        int optionSelected = scanner.nextInt();
        scanner.nextLine();
        if (optionSelected == 1){
            displayProducts(scanner);
        } else if (optionSelected == 2) {
            shoppingCart();
        }
    }

    //Display all Products that the store offers with its given prices
    public static void displayProducts(Scanner scanner){
        ArrayList<Product> products = new ArrayList<>();

        try {
            File myObj = new File("productPrice.csv");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(",");
                Product product = new Product(data[0], Double.parseDouble(data[1]));
                products.add(product); // add to arrayList to display the menu because it won't lose original order
                productsMap.put(data[0], product); //put into hashmap to search easily later
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.printf("Product %29s "," ");
        System.out.printf("Price %n");
        for (Product i : products) {
            System.out.printf("%-35s ", i.productName);
            System.out.printf("$% .2f %n ",i.productPrice);
        }
        System.out.println("\n");

        //Loop until the user has selected every item he/she wants and then press b to go back to the main menu
        while(true){
           System.out.println("Select a product or ('b') to get back to the Main Menu: \n");
           String selection  = scanner.nextLine();
           if(selection.equals("b")){
               break;
           }
           else{ //add the product selected to the shopping cart
               System.out.println();
               shoppingCart.add(productsMap.get(selection));
           }
        }
        //go back to the main menu updating the shopping cart
        menu();

    }

    //Print shopping cart so the user verify what's in it and how much will it cost
    public static void shoppingCart(){
        Scanner scanner = new Scanner(System.in);
        showCart();

        int selection;
        while (true){
            System.out.println("Select an option: ");
            System.out.println("1: go back to the Main Menu ");
            System.out.println("2: Checkout");
            System.out.println("3: remove an item");
            selection = scanner.nextInt();
            if(selection == 1){
                menu();
                break;
            } else if (selection == 2) {
                checkOut();
                break;
            } else if (selection == 3) { //remove item from list
                removeItemFromCart();
            }
        }

    }

    public static double calculateTotal(){
        double total = 0;
        for (Product i : shoppingCart) {
            total += i.productPrice;
        }
        return total;
    }
    public static void showCart(){
        System.out.println("Shopping cart: \n");

        for (Product i : shoppingCart) {
            System.out.printf("%-35s ", i.productName);
            System.out.printf("$% .2f %n ",i.productPrice);
        }
        System.out.printf("%-35s ","Total");
        System.out.printf("$% .2f %n \n", calculateTotal());
    }

    public static void removeItemFromCart(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which item do you want to remove from the shopping cart: ");
        shoppingCart.remove(productsMap.get(scanner.nextLine()));
        shoppingCart();
    }

    public static void checkOut(){
        System.out.println("Printing receipt");
        try {
            BufferedWriter outputWriter;
            outputWriter = new BufferedWriter(new FileWriter("receipt.csv"));
            for (Product i : shoppingCart) {
                outputWriter.write(i.productName);
                outputWriter.write(",");
                outputWriter.write("$"+(i.productPrice));
                outputWriter.newLine();
            }
            outputWriter.write("Total,"+"$"+calculateTotal());
            outputWriter.flush();
            outputWriter.close();
            System.out.println("Successfully Printed receipt.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
