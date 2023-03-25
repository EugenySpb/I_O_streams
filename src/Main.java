import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static String[] products = {"Молоко", "Хлеб", "Гречневая крупа"};
    static int[] prices = {150, 200, 300};

    static File saveFile = new File("basket.txt");

    public static void main(String[] args) throws IOException {
        Basket basket;

        if (saveFile.exists()) {
            basket = Basket.loadFromTxtFile(saveFile);
        } else {
            basket = new Basket(products, prices);
        }

        while (true) {

            System.out.println("Список возможных товаров для покупки:");
            for (int i = 0; i < products.length; i++) {
                System.out.printf("%d\t%s\t%d руб.\n", i + 1, products[i], prices[i]);
            }
            System.out.println("Выберите товар и количество или введите `end`");
            String input = scanner.nextLine();
            if (input.equals("end")) {
                break;
            }
            String[] selection = input.split(" ");
            if (selection.length < 2) {
                System.out.println("Ошибка: недостаточно аргументов");
                continue;
            }
            try {
                int productNum = Integer.parseInt(selection[0]) - 1;
                int amount = Integer.parseInt(selection[1]);
                assert basket != null;
                basket.addToCart(productNum, amount);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: неверный формат ввода");
                continue;
            }
            try {
                basket.saveTxt(saveFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            basket.printCart();
        }
    }
}