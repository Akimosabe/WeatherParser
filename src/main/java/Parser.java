import java.io.IOException;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import javax.mail.MessagingException;
import java.time.LocalTime;

public class Parser {
    private static Document getPage() throws IOException{
         String url = "https://ekaterinburg.nuipogoda.ru/погода-на-сегодня";
         Document page = Jsoup.parse(new URL(url), 3000);
         return page;
    }


    public static void main(String[] args) {
        while (true) {
            LocalTime currentTime = LocalTime.now();
            LocalTime desiredTime = LocalTime.of(18,39); // Смена времени (не ставить секунды!)

            //if (currentTime.equals(desiredTime)) { не работает из-за секунд
            if (currentTime.getHour() == desiredTime.getHour() && currentTime.getMinute() == desiredTime.getMinute()) {
                try {
                    Sending();
                } catch (MessagingException | IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(60000); // Задержка в 1 минуту
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //Теперь парсинг и отправка будут в отдельном методе (возможно стоит поменять имя), а не в методе main
    public static void Sending() throws MessagingException, IOException {
        Document page = getPage();

        Element table = page.select("table[class=weather]").first();
        //System.out.println(table);                         Уже не нужно
        //Elements names = table.select("td[class=day]");    Уже не нужно

        //Вытаскиваем дату текстом
        Element dateElement = table.select("td.day span").last();
        String date = dateElement.text();

        //Вытаскиваем время
        Elements timeElement = table.select("span[class=c1]");
        String time = timeElement.text();

        //Вытаскиваем температуру
        Elements temperatureElement = table.select("span[class=ht]");
        String temperature = temperatureElement.text();

        //Вытаскиваем ветер
        Elements windElement = table.select("span[class=wd]");
        String wind = windElement.text();
        Elements windspeedElement = table.select("span[class=ws]");
        String windspeed = windspeedElement.text();

        //Вытаскиваем описание погоды
        Elements tempTextElement = table.select("div");
        String tempText = tempTextElement.text();

        //Делаем массивы для упрощения вывода текста в нужном мне формате

        //Время
        String[] timeArr = new String[timeElement.size()];
        for (int i = 0; i < timeElement.size(); i++) {
            timeArr[i] = timeElement.get(i).text();
        }
        //Температура
        String[] temperatureArr = new String[temperatureElement.size()];
        for (int i = 0; i < temperatureElement.size(); i++) {
            temperatureArr[i] = temperatureElement.get(i).text();
        }
        //Ветер
        String[] windArr = new String[windElement.size()];
        for (int i = 0; i < windElement.size(); i++) {
            windArr[i] = windElement.get(i).text();
        }
        //Скорость ветра
        String[] windspeedArr = new String[windspeedElement.size()];
        for (int i = 0; i < windspeedElement.size(); i++) {
            windspeedArr[i] = windspeedElement.get(i).text();
        }
        //Описание погоды
        String[] tempTextArr = tempText.split("   "); // 3 пробела!
        for (int i = 0, j = 0; i < tempTextArr.length; i += 4, j++) {
            tempTextArr[j] = tempTextArr[i];
        }


        System.out.println(date +" 📅");
        for (int i = 0; i < timeArr.length; i++) {
            String timeEmoji = Emoji.getTimeEmoji(timeArr[i]);
            String temperatureEmoji = Emoji.getTemperatureEmoji(temperatureArr[i]);

            String output = String.format("%s\t%-9s\t%-7s\t%-3s\t%-6s\t%s\t%s",
                    timeEmoji, timeArr[i], temperatureArr[i], temperatureEmoji, windArr[i], windspeedArr[i], tempTextArr[i]);

            System.out.println(output);
        }

//            System.out.println(date);
//            for (int i = 0; i < timeArr.length; i++) {
//                System.out.printf("%-5s    %-4s    %-4s    %-3s    %s%n", timeArr[i], temperatureArr[i], windArr[i], windspeedArr[i], tempTextArr[i]);
//            }

        /*
        for (int i = 0; i < tempTextArr.length; i++) {
            System.out.printf(tempTextArr[i]);}
        Проверка количества элементов в массиве

        for (int i = 0; i < tempTextArr.length; i++) {
            System.out.printf(tempTextArr[i]);}
        Вывод массива
        */

            //Отправка в телеграмм
            String botToken = "6250795881:AAGMufC9SHW2lZl0cdBm07_jz2Kylaa3fOQ";
            String chatId = "-1001912806627";
            Bot bot = new Bot(botToken);

        StringBuilder telegramMessage = new StringBuilder();
        telegramMessage.append(date).append("  📅").append("\n\n");
        for (int i = 0; i < timeArr.length; i++) {
            String timeEmoji = Emoji.getTimeEmoji(timeArr[i]);
            String temperatureEmoji = Emoji.getTemperatureEmoji(temperatureArr[i]);

            String output = String.format("%s\t%-9s\t%-4s\t%-2s\t\t\t%-6s\t%s",
                    timeEmoji, timeArr[i],temperatureArr[i], temperatureEmoji, windspeedArr[i], windArr[i]);
            telegramMessage.append(output).append("\n");
        }
            bot.sendMessageToChat(chatId, telegramMessage.toString());
            // В <dependency> пришлось добавлять ch.qos.logback , без этого  все работало, но вываливалась ошибка



        //Отправка на почту
        String who = "he7seven@gmail.com"; //akimosabe@yandex.ru umbrellacademy@mail.ru  he7seven@gmail.com
        String subject = "Погода на сегодня";

        StringBuilder message = new StringBuilder();
        message.append(date).append(" 📅").append("\n");

        for (int i = 0; i < timeArr.length; i++) {
            String timeEmoji = Emoji.getTimeEmoji(timeArr[i]);
            String temperatureEmoji = Emoji.getTemperatureEmoji(temperatureArr[i]);

            String output = String.format("%-1s %-8s  %-3s%s    %-6s     %3s     %2s",
                    timeEmoji, timeArr[i], temperatureEmoji, temperatureArr[i], windspeedArr[i], windArr[i], tempTextArr[i]);
            message.append(output).append("\n");
        }
            Email.sendEmail(who, subject, message.toString());


        }


    }