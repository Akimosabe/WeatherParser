
public class Emoji {
    public static String getTimeEmoji(String time) {
        if (time.startsWith("00:") || time.startsWith("03:") || time.startsWith("24:")) {
            return "🌙";
        } else if (time.startsWith("06:") || time.startsWith("09:")) {
            return "☀";
        } else if (time.startsWith("12:") || time.startsWith("15:")) {
            return "🌞";
        } else if (time.startsWith("18:") || time.startsWith("21:")) {
            return "🌆";
        } else {
            return ""; // Если время не соответствует заданным условиям, возвращаем пустую строку
        }
    }

    public static String getTemperatureEmoji(String temperature) {
        int temp = Integer.parseInt(temperature.replace("+", "").replace("°", ""));

        if (temp >= 30) {
            return "🥵";
        } else if (temp <= -20) {
            return "🥶";
        } else {
            return "🌡️";
        }
    }
}