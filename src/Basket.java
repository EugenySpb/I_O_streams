import java.io.*;
import java.util.Arrays;

public class Basket implements Serializable {
    private final String[] products;
    private final int[] prices;
    private static int[] cart;

    public Basket(String[] products, int[] prices) {
        this.prices = prices;
        this.products = products;
        this.cart = new int[10];
    }

    public void addToCart(int productNum, int amount) {
        if (cart == null) {
            cart = new int[10];
        }
        if (productNum >= 0 && productNum < products.length && amount > 0) {
            cart[productNum] += amount;
        }
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

    public void saveTxt(File textFile) throws IOException {
        try (PrintWriter out = new PrintWriter(textFile)) {
            for (int price : prices) {
                out.print(price + " ");
            }
            out.println();

            for (String product : products) {
                out.print(product + " ");
            }
            out.println();

            for (int carts : cart) {
                out.print(carts + " ");
            }
            out.println();
        }
    }

    public static Basket loadFromTxtFile(File textFile) {
        Basket basket = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile))) {
            String pricesStr = bufferedReader.readLine();
            String productsStr = bufferedReader.readLine();
            String cartStr = bufferedReader.readLine();

            int[] prices = Arrays.stream(pricesStr.split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            String[] products = productsStr.split(" ");
            int[] cart = Arrays.stream(cartStr.split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            basket = new Basket(products, prices);
            Basket.cart = cart;
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла");
        }
        return basket;
    }

    public void saveBin(File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Basket loadFromBinFile(File file) {
        Basket basket;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            basket = (Basket) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return basket;
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
}
