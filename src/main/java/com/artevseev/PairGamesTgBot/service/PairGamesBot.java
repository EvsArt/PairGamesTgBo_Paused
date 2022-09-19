package com.artevseev.PairGamesTgBot.service;

import com.artevseev.PairGamesTgBot.config.BotConfig;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.artevseev.PairGamesTgBot.service.Funcs.isNumber;
import static com.artevseev.PairGamesTgBot.service.Funcs.reverseText;

@Component  // Позволяет автоматически создать экземпляр
public class PairGamesBot extends TelegramLongPollingBot {

    final BotConfig config;

    public PairGamesBot(BotConfig config){
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();

            switch (messageText){
                case "/start":
                    sendMessage(chatId, "Введите неотрицательное число или строку");
                    break;
                case "/board":
                    sendMessage(chatId, "Тут будет раздел с играми на шахматной доске");
                    break;
                case "/card":
                    sendMessage(chatId, "Тут будет раздел с карточными играми");
                    break;
                case "/text":
                    sendMessage(chatId, "Тут будет раздел с текстовыми играми");
                    break;
                default: defaultDoing(chatId, update.getMessage().getText());
            }
        }
    }

    private void sendMessage(long chatId, String message){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void defaultDoing(long chatId, String text) {
        if (isNumber(text)) {
            if (text.charAt(0) == '-') {
                sendMessage(chatId, "Неотрицательное - это когда минуса нет.....");
                return;
            }
            if (Integer.parseInt(Character.toString(text.charAt(text.length()-1))) % 2 == 0) sendMessage(chatId, "Это число чётное");
            else sendMessage(chatId, "Это число нечётное");
        } else sendMessage(chatId, reverseText(text));
    }

}
