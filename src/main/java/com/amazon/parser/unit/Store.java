package com.amazon.parser.unit;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class is used for store goods in json to file
 * @param <T>
 */
public class Store<T> {
    private final CopyOnWriteArrayList<T> list = new CopyOnWriteArrayList<T>();
    private final String storeName;

    /**
     * This is default constructor
     * @param storeName store file name
     */
    public Store(String storeName) {
        this.storeName = storeName;
    }

    /**
     * This method adds the new goods in store
     * @param elements goods list
     */
    public void add(List<T> elements) {
        list.addAll(elements);
    }

    /**
     * This method creates a new file with json goods
     * @throws IOException - for unpredictable situation
     */
    public void save() throws IOException {
        FileOutputStream out = new FileOutputStream(storeName);
        try {
            ObjectMapper mapper = new ObjectMapper();
            for (T t : list) {
                out.write(mapper.writeValueAsBytes(t));
                out.write("\r\n".getBytes());
            }
        } finally {
            out.close();
        }
    }
}
