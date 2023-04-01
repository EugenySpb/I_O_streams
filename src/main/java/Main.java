import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static String[] products = {"Молоко", "Хлеб", "Гречневая крупа"};
    static int[] prices = {150, 200, 300};

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        XMLSettingsReader settings = new XMLSettingsReader(new File("shop.xml"));
        File loadFile = new File(settings.loadFile);
        File saveFile = new File(settings.saveFile);
        File logFile = new File(settings.logFile);

        Basket basket = createBasket(loadFile, settings.isLoad, settings.loadFormat);
        ClientLog log = new ClientLog();

        while (true) {
            showPrice();
            String input = scanner.nextLine();
            if (input.equals("end")) {
                if (settings.isLog) {
                    log.exportAsCSV(logFile);
                }
                break;
            }
            String[] selection = input.split(" ");
            int productNum = Integer.parseInt(selection[0]) - 1;
            int amount = Integer.parseInt(selection[1]);
            basket.addToCart(productNum, amount);
            if (settings.isLog) {
                log.log(productNum, amount);
            }
            if (settings.isSave) {
                switch (settings.saveFormat) {
                    case "json" -> basket.saveToJsonFile(saveFile);
                    case "txt" -> basket.saveTxt(saveFile);
                }
            }
        }
        basket.printCart();
    }

    private static Basket createBasket(File loadFile, boolean isLoad, String loadFormat) {
        Basket basket;
        if (isLoad && loadFile.exists()) {
            basket = switch (loadFormat) {
                case "json" -> Basket.loadFromJsonFile(loadFile);
                case "txt" -> Basket.loadFromTxtFile(loadFile);
                default -> new Basket(products, prices);
            };
        } else {
            basket = new Basket(products, prices);
        }
        return basket;
    }


    private static void showPrice() {
        System.out.println("Список возможных товаров для покупки:");
        for (int i = 0; i < products.length; i++) {
            System.out.printf("%d\t%s\t%d руб.\n", i + 1, products[i], prices[i]);
        }
        System.out.println("Выберите товар и количество или введите `end`");
    }
}