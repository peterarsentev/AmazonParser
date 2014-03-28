package com.amazon.parser;

import com.amazon.parser.model.Goods;
import com.amazon.parser.unit.Fetcher;
import com.amazon.parser.unit.Store;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

/**
 * This class is runner. It fetches the contents of searching from amazon.com.
 * The main method should take the parameters:
 * keys.txt file (this file should have the search keys)
 * store.txt file (it is a file where result will be saved in json format)
 */
public class Runner {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Runner.class);

    /**
     * All calculation will be done in multithreading. For this reason is used pool thread. It has the size of available processors
     */
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    /**
     * The main method should take the parameters
     * @param arg file keys.txt - search keys. store.txt - file for save result
     * @throws IOException for unpredictable situation
     * @throws ExecutionException for unpredictable situation
     * @throws InterruptedException for unpredictable situation
     */
    public static void main(String[] arg) throws IOException, ExecutionException, InterruptedException {
        if (checkInputParams(arg)) {
            long start = System.currentTimeMillis();
            Set<Callable<List<Goods>>> callables = new HashSet<Callable<List<Goods>>>();
            for (String key : loadSearchKeys(arg[0])) {
                for (int page=1;page!=21;page++) {
                    callables.add(new Fetcher(key, page));
                }
            }
            Store<Goods> store = new Store<Goods>(arg[1]);
            for(Future<List<Goods>> future : EXECUTOR.invokeAll(callables)){
                store.add(future.get());
            }
            store.save();
            if (log.isDebugEnabled()) log.debug("time : {}", (System.currentTimeMillis() - start));
        }
        EXECUTOR.shutdown();
    }

    /**
     * This method loads the keys from files
     * @param keysFile file name, It should consist from keys located by new line
     * @return array of keys
     */
    private static String[] loadSearchKeys(String keysFile) throws IOException {
        String text = FileUtils.readFileToString(new File(keysFile), "UTF-8");
        return text.replaceAll("\r\n", "").split(";");
    }

    /**
     * This method checks the input params in show the info about it
     * @param arg input params
     * @return true if params are correct
     */
    private static boolean checkInputParams(String[] arg) {
        if (arg.length != 2) {
            log.error("You should set the input params for this app.");
            log.error("java -jar AmazonParser-1.0.jar etc/keys.txt store.txt");
            log.error("where etc/keys.txt is a search keys");
            log.error("store.txt is a file for save result in json format");
            return false;
        }
        return true;
    }
}
