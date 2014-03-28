package com.amazon.parser.unit;

import com.amazon.parser.model.Goods;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import static com.amazon.parser.unit.ParserGoods.parse;

/**
 * This is fetcher class. It is used for fetching page context in multithreading
 */
public class Fetcher implements Callable<List<Goods>> {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final String url;

    /**
     * Default constructor
     * @param key search key
     * @param page current page
     */
    public Fetcher(String key, int page) {
        this.url = String.format("http://www.amazon.com/s/?keywords=%s&page=%d", key, page);
    }

    @Override
    public List<Goods> call() {
        try {
            long start = System.currentTimeMillis();
            List<Goods> list = parse(Request.Get(url).connectTimeout(300).execute().returnContent().asString());
            if (log.isDebugEnabled()) log.debug("load page {}, size {}, time {}", new Object[] {url, list.size(), (System.currentTimeMillis() - start)});
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }
}
