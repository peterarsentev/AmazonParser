package com.amazon.parser.unil;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.fluent.Request;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * This tests check the time of lib Net and Fluent
 */
public class LoadPageTest {
    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * This method checks the Net lib
     * @throws IOException
     */
    @Test
    public void apacheIO() throws IOException {
        long start = System.currentTimeMillis();
        URL url = new URL("http://www.amazon.com/s/?keywords=phone&page=1");
        URLConnection con = url.openConnection();
        String str = IOUtils.toString(con.getInputStream(), "UTF-8");
        if (log.isDebugEnabled()) log.debug("Net time : {}", (System.currentTimeMillis() - start));
    }

    /**
     * This method checks the Fluent lib
     * @throws IOException
     */
    @Test
    public void apacheFluent() throws IOException  {
        long start = System.currentTimeMillis();
        String page = Request.Get("http://www.amazon.com/s/?keywords=phone&page=1").connectTimeout(150).execute().returnContent().asString();
        if (log.isDebugEnabled()) log.debug("Fluent time : {}", (System.currentTimeMillis() - start));
    }
}
