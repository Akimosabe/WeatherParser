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
        //Elements names = table.select("td[class=day]");

        Element dateElement = table.select("td.day span").last();
        String date = dateElement.text() ;
        //Вытаскиваем дату текстом

        Elements timeElement = table.select("span[class=c1]");
        String time = timeElement.text() ;
        //Вытаскиваем время

        Elements temperatureElement = table.select("span[class=ht]");
        String temperature = temperatureElement.text() ;
        //Вытаскиваем температуру

        Elements windElement = table.select("span[class=wd]");
        String wind = windElement.text();
        Elements windspeedElement = table.select("span[class=ws]");
        String windspeed = windspeedElement.text();
        //Вытаскиваем ветер

        Elements tempTextElement = table.select("div");
        String tempText = tempTextElement.text();
        //Вытаскиваем описание погоды

        System.out.println(date+time+temperature+wind+windspeed+tempText);

}
}