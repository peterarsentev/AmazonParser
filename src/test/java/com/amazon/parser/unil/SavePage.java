package com.amazon.parser.unil;

import org.apache.http.client.fluent.Request;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class SavePage {
    @Test
    public void save() throws IOException {
        Request.Get("http://www.amazon.com/s/?keywords=phone&page=10").connectTimeout(300).execute().saveContent(new File("result_10.html"));;
    }
}
