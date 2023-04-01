import java.io.File;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static String[] products = {"Молоко", "Хлеб", "Гречневая крупа"};
    static int[] prices = {150, 200, 300};

    static File saveFile = new File("basket.json");

    public static void main(String[] args) {
        Basket basket;
        if (saveFile.exists()) {
            basket = Basket.loadFromJsonFile(saveFile);
        } else {
            basket = new Basket(products, prices);
        }

        ClientLog log = new ClientLog();
        while (true) {
            showPrice();
            String input = scanner.nextLine();
            if (input.equals("end")) {
                log.exportAsCSV(new File("log.csv"));
                break;
            }
            String[] selection = input.split(" ");
            int productNum = Integer.parseInt(selection[0]) - 1;
            int amount = Integer.parseInt(selection[1]);
            basket.addToCart(productNum, amount);
            log.log(productNum, amount);
            basket.saveToJsonFile(saveFile);
        }
        basket.printCart();
    }

    private static void showPrice() {
        System.out.println("Список возможных товаров для покупки:");
        for (int i = 0; i < products.length; i++) {
            System.out.printf("%d\t%s\t%d руб.\n", i + 1, products[i], prices[i]);
        }
        System.out.println("Выберите товар и количество или введите `end`");
    }
}