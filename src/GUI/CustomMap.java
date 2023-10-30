package GUI;

import GUI.Product;

import java.util.ArrayList;


/**
 * GUI.CustomMap class that simulates a map data structure using two ArrayLists.
 * The first ArrayList, keys, stores the keys of the map.
 * The second ArrayList, values, stores the values of the map.
 * Each key-value pair is stored at the same index in their respective lists.
 */
public class CustomMap {
    private ArrayList<String> keys;
    private ArrayList<Product> values;

    public ArrayList<String> getKeys() {
        return keys;
    }

    public void setKeys(ArrayList<String> keys) {
        this.keys = keys;
    }

    public ArrayList<Product> getValues() {
        return values;
    }

    public void setValues(ArrayList<Product> values) {
        this.values = values;
    }


    public CustomMap() {
        keys = new ArrayList<>();
        values = new ArrayList<>();
    }

    /**
     * Method that adds a key-value pair to the map.
     * @param key The key to be added.
     * @param value The value to be added.
     */
    public void put(String key, Product value) {
        keys.add(key);
        values.add(value);
    }

    /**
     * Method that retrieves the value associated with a given key.
     * @param key The key to retrieve the value for.
     * @return The value associated with the given key, or null if the key doesn't exist.
     */
    public Product get(String key) {
        int index = keys.indexOf(key);
        if (index != -1) {
            return values.get(index);
        }
        return null;
    }

    /**
     * Method that removes a key-value pair from the map and returns the removed value.
     * @param key The key to remove.
     * @return The value associated with the removed key, or null if the key doesn't exist.
     */
    public Product remove(String key) {
        int index = keys.indexOf(key);
        if (index != -1) {
            keys.remove(index);

            return values.remove(index);
//            return null;
        }
        return null;
    }
}
