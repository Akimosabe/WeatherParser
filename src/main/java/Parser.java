import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.jsoup.select.Elements;

//import java.time.LocalDate;
//import java.time.LocalTime;

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

        //Вытаскиваем дату текстом
        Element dateElement = table.select("td.day span").last();
        String date = dateElement.text() ;

        //Вытаскиваем время
        Elements timeElement = table.select("span[class=c1]");
        String time = timeElement.text() ;

        //Вытаскиваем температуру
        Elements temperatureElement = table.select("span[class=ht]");
        String temperature = temperatureElement.text() ;

        //Вытаскиваем ветер
        Elements windElement = table.select("span[class=wd]");
        String wind = windElement.text();
        Elements windspeedElement = table.select("span[class=ws]");
        String windspeed = windspeedElement.text();

//Вытаскиваем описание погоды ! С этим проблемы, т. к данный текст невозоможно нормально преобразовать в массив, необходимо исправить
        Elements tempTextElement = table.select("div");
        String tempText = tempTextElement.text();


        //Делаем массивы для упрощения вывода текста в нужном мне формате
        String[] timeArr = new String[timeElement.size()];
        for (int i = 0; i < timeElement.size(); i++) {
            timeArr[i] = timeElement.get(i).text();
        }

        String[] temperatureArr = new String[temperatureElement.size()];
        for (int i = 0; i < temperatureElement.size(); i++) {
            temperatureArr[i] = temperatureElement.get(i).text();
        }

        String[] windArr = new String[windElement.size()];
        for (int i = 0; i < windElement.size(); i++) {
            windArr[i] = windElement.get(i).text();
        }

        String[] windspeedArr = new String[windspeedElement.size()];
        for (int i = 0; i < windspeedElement.size(); i++) {
            windspeedArr[i] = windspeedElement.get(i).text();
        }

        /*
        String[] tempTextArr = new String[tempTextElement.size()];
        for (int i = 0; i < tempTextElement.size(); i++) {
            tempTextArr[i] = tempTextElement.get(i).text();
            }
            С этим массивом проблемы
            */

        System.out.println(date);


        for (int i = 0; i < timeArr.length; i++) {
            System.out.printf("%-5s    %-4s    %-4s    %-3s%n", timeArr[i], temperatureArr[i], windArr[i], windspeedArr[i]);
        }
        //выводим элементы массива

        /*for (int i = 0; i < tempTextArr.length; i++) {
            System.out.printf(tempTextArr[i]);}*/


}
}