package com.amazon.parser;

import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static com.amazon.parser.unit.ParserGoods.*;
import static junit.framework.Assert.assertEquals;

/**
 * This tests check the parser work
 */
public class ParserGoodsTest {
    @Test
    public void withResult() throws IOException {
        assertEquals(sizeResult("/result.html"), 16);
    }

    @Test
    public void notFound() throws IOException {
        assertEquals(sizeResult("/notFound.html"), 0);
    }

    @Test
    public void page10() throws IOException {
        assertEquals(sizeResult("/result_10.html"), 16);
    }

    private int sizeResult(String fileName) throws IOException {
        String html = IOUtils.toString(this.getClass().getResourceAsStream(fileName), "UTF-8");
        return parse(html).size();
    }
}
