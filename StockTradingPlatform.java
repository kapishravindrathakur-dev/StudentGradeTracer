import java.util.ArrayList;
import java.util.Scanner;

// Stock Class
class Stock {

    String symbol;
    String companyName;
    double price;

    Stock(String symbol, String companyName, double price) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.price = price;
    }
}

// Portfolio Item Class
class PortfolioItem {

    Stock stock;
    int quantity;

    PortfolioItem(Stock stock, int quantity) {
        this.stock = stock;
        this.quantity = quantity;
    }

    double getTotalValue() {
        return stock.price * quantity;
    }
}

// User Class
class User {

    String name;
    double balance;
    ArrayList<PortfolioItem> portfolio;

    User(String name, double balance) {
        this.name = name;
        this.balance = balance;
        portfolio = new ArrayList<>();
    }

    // Buy Stock
    void buyStock(Stock stock, int quantity) {

        double totalCost = stock.price * quantity;

        if (totalCost > balance) {
            System.out.println("Insufficient balance!");
            return;
        }

        balance -= totalCost;

        // Check if stock already exists
        for (PortfolioItem item : portfolio) {

            if (item.stock.symbol.equals(stock.symbol)) {
                item.quantity += quantity;
                System.out.println("Stock purchased successfully!");
                return;
            }
        }

        portfolio.add(new PortfolioItem(stock, quantity));

        System.out.println("Stock purchased successfully!");
    }

    // Sell Stock
    void sellStock(String symbol, int quantity) {

        for (PortfolioItem item : portfolio) {

            if (item.stock.symbol.equalsIgnoreCase(symbol)) {

                if (quantity > item.quantity) {
                    System.out.println("Not enough shares to sell!");
                    return;
                }

                item.quantity -= quantity;

                double amount = quantity * item.stock.price;
                balance += amount;

                System.out.println("Stock sold successfully!");

                if (item.quantity == 0) {
                    portfolio.remove(item);
                }

                return;
            }
        }

        System.out.println("Stock not found in portfolio.");
    }

    // Display Portfolio
    void showPortfolio() {

        System.out.println("\n===== Portfolio =====");

        if (portfolio.isEmpty()) {
            System.out.println("No stocks owned.");
            return;
        }

        double totalPortfolioValue = 0;

        for (PortfolioItem item : portfolio) {

            double value = item.getTotalValue();

            totalPortfolioValue += value;

            System.out.println(
                    item.stock.symbol + " | " +
                    item.stock.companyName + " | " +
                    "Shares: " + item.quantity + " | " +
                    "Price: $" + item.stock.price + " | " +
                    "Value: $" + value
            );
        }

        System.out.println("\nTotal Portfolio Value: $" + totalPortfolioValue);
        System.out.println("Available Balance: $" + balance);
    }
}

public class StockTradingPlatform {

    static Scanner scanner = new Scanner(System.in);

    // Display Market Stocks
    static void displayMarket(ArrayList<Stock> marketStocks) {

        System.out.println("\n===== Market Data =====");

        for (Stock stock : marketStocks) {

            System.out.println(
                    stock.symbol + " | " +
                    stock.companyName + " | " +
                    "Price: $" + stock.price
            );
        }
    }

    // Find Stock
    static Stock findStock(ArrayList<Stock> marketStocks, String symbol) {

        for (Stock stock : marketStocks) {

            if (stock.symbol.equalsIgnoreCase(symbol)) {
                return stock;
            }
        }

        return null;
    }

    public static void main(String[] args) {

        // Market Stocks
        ArrayList<Stock> marketStocks = new ArrayList<>();

        marketStocks.add(new Stock("AAPL", "Apple Inc.", 180));
        marketStocks.add(new Stock("GOOG", "Google", 140));
        marketStocks.add(new Stock("TSLA", "Tesla", 250));
        marketStocks.add(new Stock("AMZN", "Amazon", 130));

        // Create User
        User user = new User("Investor", 10000);

        int choice;

        do {

            System.out.println("\n===== Stock Trading Platform =====");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    displayMarket(marketStocks);
                    break;

                case 2:

                    displayMarket(marketStocks);

                    System.out.print("Enter stock symbol to buy: ");
                    String buySymbol = scanner.nextLine();

                    Stock buyStock = findStock(marketStocks, buySymbol);

                    if (buyStock == null) {
                        System.out.println("Invalid stock symbol!");
                        break;
                    }

                    System.out.print("Enter quantity: ");
                    int buyQty = scanner.nextInt();

                    user.buyStock(buyStock, buyQty);

                    break;

                case 3:

                    user.showPortfolio();

                    System.out.print("Enter stock symbol to sell: ");
                    String sellSymbol = scanner.nextLine();

                    System.out.print("Enter quantity: ");
                    int sellQty = scanner.nextInt();
                    scanner.nextLine();

                    user.sellStock(sellSymbol, sellQty);

                    break;

                case 4:
                    user.showPortfolio();
                    break;

                case 5:
                    System.out.println("Exiting platform...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 5);

        scanner.close();
    }
}