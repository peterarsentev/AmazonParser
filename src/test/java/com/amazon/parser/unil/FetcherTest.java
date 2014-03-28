package com.amazon.parser.unil;

import com.amazon.parser.unit.Fetcher;
import org.junit.Test;

/**
 * This tests check the execute fetcher
 */
public class FetcherTest {
    @Test
    public void checkTime() {
        long start = System.currentTimeMillis();
        new Fetcher("phone", 10).call();
        System.out.println(String.format("time : %s", (System.currentTimeMillis() - start)));
    }
}
