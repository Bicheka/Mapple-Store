import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner; // Import the Scanner class to read text files

public class Mapple {

    public static void main(String[] args){
        ArrayList<Product> shoppingCart = new ArrayList<>();
        System.out.println("Welcome to the Mapple store \n");
        menu(shoppingCart);
    }

    public static void menu(ArrayList<Product> cart){

        System.out.println("Main menu \nSelect an option ");
        System.out.println("1: See products");
        System.out.println("2: See shopping cart ");
        System.out.println("3: Exit\n");
        Scanner scanner = new Scanner(System.in);
        int optionSelected = scanner.nextInt();
        scanner.nextLine();
        if (optionSelected == 1){
            displayProducts(scanner, cart);
        } else if (optionSelected == 2) {
            shoppingCart(cart);
        }
    }

    //Display all Products that the store offers with its given prices
    public static void displayProducts(Scanner scanner, ArrayList<Product> shoppingCart){
        ArrayList<Product> products = new ArrayList<>();

        HashMap<String, Product> productsMap = new HashMap<>();
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
        menu(shoppingCart);

    }

    //Print shopping cart so the user verify what's in it and how much will it cost
    public static void shoppingCart(ArrayList<Product> cart){
        Scanner scanner = new Scanner(System.in);
        showCart(cart);

        int selection;
        while (true){
            System.out.println("Select an option: ");
            System.out.println("1: go back to the Main Menu ");
            System.out.println("2: Checkout");
            System.out.println("3: remove an item");
            selection = scanner.nextInt();
            if(selection == 1){
                menu(cart);
            } else if (selection == 2) {
                //checkoutFunction
            } else if (selection == 3) { //remove item from list
                removeItemFromcart(cart);
            }
        }

    }

    public static void showCart(ArrayList<Product> cart){
        System.out.println("Shopping cart: \n");
        double total = 0;//stores the total amount to pay
        for (Product i : cart) {
            System.out.printf("%-35s ", i.productName);
            System.out.printf("$% .2f %n ",i.productPrice);
            total += i.productPrice;
        }
        System.out.printf("%-35s ","Total");
        System.out.printf("$% .2f %n ", total);
    }

    public static ArrayList<Product> removeItemFromcart(ArrayList<Product> cart){
        return cart;
    }

//    public static void checkOut(){
//
//    }

}
