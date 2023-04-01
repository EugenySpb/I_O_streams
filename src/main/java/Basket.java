import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class Basket implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String[] products;
    private int[] prices;
    private int[] cart;

    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        this.cart = new int[products.length];
    }
    public void saveToJsonFile(File file) {
        try (PrintWriter writer = new PrintWriter(file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(this);
            writer.print(json);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Basket loadFromJsonFile(File file) {
        Basket basket;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            Gson gson = new Gson();
            basket = gson.fromJson(builder.toString(), Basket.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }

    public void addToCart(int productNum, int amount) {
        if (productNum < 0 || productNum >= products.length) {
            throw new IllegalArgumentException("Неверный номер продукта: " + productNum);
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Недопустимое количество: " + amount);
        }
        cart[productNum] += amount;
    }

    public void printCart() {
        int sumProducts = 0;
        System.out.println("Ваша корзина: ");
        for (int i = 0; i < products.length; i++) {
            if (cart[i] > 0) {
                System.out.printf("%s по %d руб. - %d шт. (в сумме %d руб.)%n",
                        products[i], prices[i], cart[i], prices[i] * cart[i]);
                sumProducts += cart[i] * prices[i];
            }
        }
        System.out.println("Итого: " + sumProducts);
    }

    public int[] getPrices() {
        return prices;
    }

    public String[] getProducts() {
        return products;
    }

    public int[] getCart() {
        return cart;
    }

    public void setCart(int[] cart) {
        this.cart = cart;
    }
}
