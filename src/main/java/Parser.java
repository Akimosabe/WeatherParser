import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.jsoup.select.Elements;

public class Parser {
    private static Document getPage() throws IOException{
         String url = "https://ekaterinburg.nuipogoda.ru/погода-на-сегодня";
         Document page = Jsoup.parse(new URL(url), 3000);
         return page;
    }

    public static void main(String[] args) throws IOException{
        Document page = getPage();

        Element table = page.select("table[class=weather]").first();
        //System.out.println(table);
        Elements names = table.select("td[class=day]");
        String date  ;
        System.out.println(names);

}
}