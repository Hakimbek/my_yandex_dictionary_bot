package org.example;

import com.google.gson.Gson;
import org.example.dictionary.Root;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

public class MyDictionary extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String word = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            SendMessage message = new SendMessage()
                    .setChatId(chatId);

            try {
                Root root = connectToYandexAPI(word);
                if (root.getDef().isEmpty()) {
                    message.setText("Unknown command!");
                } else {
                    message.setText(word + " - " + root.getDef().get(0).getTr().get(0).getText());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public static Root connectToYandexAPI(String word) throws IOException {
        Gson gson = new Gson();
        String key = "dict.1.1.20201218T111826Z.d00dfeb24d10df74.b21d6e65903e85b5329406d532e49cfb107baf5a";
        URL url = new URL("https://dictionary.yandex.net/api/v1/dicservice.json/lookup?key=" + key + "&lang=en-ru&text=" + word);
        URLConnection urlConnection = url.openConnection();
        Reader reader = new InputStreamReader(urlConnection.getInputStream());
        Root root = gson.fromJson(reader, Root.class);
        return root;
    }

    @Override
    public String getBotToken() {
        return "5026951849:AAGWTFEoYcD6hSr2f69dUWLwr7rqbCfw4R0";
    }

    @Override
    public String getBotUsername() {
        return "my_yandex_dictionary_bot";
    }

}
