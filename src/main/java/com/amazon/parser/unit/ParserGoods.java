package com.amazon.parser.unit;

import com.amazon.parser.model.Goods;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class works for parsing text. It has only static method
 */
public final class ParserGoods {
    /**
     * This method parses inter text for Goods list
     * @param text inter text
     * @return goods list
     */
    public static List<Goods> parse(final String text) {
        List<Goods> goods = new ArrayList<Goods>(200);
        Document doc = Jsoup.parse(text);
        for (Element element : doc.select("div[id^=result_]")) {
            Iterator<Element> name = element.select("span.lrg").iterator();
            Iterator<Element> price = element.select("span.bld").iterator();
            if (name.hasNext() && price.hasNext()) {
                goods.add(new Goods(name.next().text(), price.next().text()));
            }
        }
        return goods;
    }
}
