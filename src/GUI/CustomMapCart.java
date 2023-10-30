package GUI;

import java.math.BigDecimal;
import java.util.ArrayList;
/**
 * GUI.CustomMap class that mimics a map data structure using two ArrayLists.
 * The first ArrayList, keys, stores the keys of the map.
 * The second ArrayList, values, stores the values of the map.
 * Each key-value pair is stored at the same index in their respective lists.
 */
public class CustomMapCart {
    private ArrayList<Product> keys;

    private ArrayList<BigDecimal> values;
    public CustomMapCart() {
        keys = new ArrayList<>();
        values = new ArrayList<>();
    }

    public ArrayList<Product> getKeys() {
        return keys;
    }


    public void setKeys(ArrayList<Product> keys) {
        this.keys = keys;
    }


    public ArrayList<BigDecimal> getValues() {
        return values;
    }


    public void setValues(ArrayList<BigDecimal> values) {
        this.values = values;
    }


    /**
     * Method that adds a key-value pair to the map.
     * @param key The key to be added.
     * @param value The value to be added.
     */
    public void put(Product key, BigDecimal value) {
        keys.add(key);
        values.add(value);
    }

    /**
     * Method that retrieves the value associated with a given key.
     * @param key The key to retrieve the value for.
     * @return The value associated with the given key, or null if the key doesn't exist.
     */
    public BigDecimal get(Product key) {
        int index = keys.indexOf(key);
        if (index != -1) {
            return values.get(index);
        }
        return null;
    }

    /**
     * Method that removes a key-value pair from the map and returns the removed value.
     *
     * @param key The key to remove.
     */
    public void remove(Product key) {
        System.out.println("Removing Product: " + key.getId() + " " + key.getName() +" " + key.getQuantity() + " " + key.getPrice() +  " " + key.getTaxRate());

        int index = keys.indexOf(key);

        if (index != -1) {
            keys.remove(index);
            values.remove(index);
            System.out.println("Successfully removed product!");
        }
        else{
            System.out.println("Product not found!");

        }
    }
}
