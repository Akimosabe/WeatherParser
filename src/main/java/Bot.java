import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

    //Токен бота
    private String botToken;

    public Bot(String botToken) {
        this.botToken = botToken;
    }


    /* Обработка входящих сообщений (в нем нет необходимости, но метод похоже обязательный при использовании класса
     * TelegramLongPollingBot, при попытке запустить без него, код перестает компилироваться */

    @Override
    public void onUpdateReceived(Update update) {
    }


    // Отправка сообщений в чат по chatId, который можно вытащить с помощью @getmyid_bot
    public void sendMessageToChat(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {

        return "WeatherParserEkbBot";
    }

    @Override
    public String getBotToken() {

        return botToken;
    }
}