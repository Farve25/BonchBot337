package Bot.BonchBot;

import java.util.ArrayList;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class BotMain extends TelegramLongPollingBot {
	TableEditor te = new TableEditor();

	public static void main(String[] args) {
		ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			telegramBotsApi.registerBot(new BotMain());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getBotUsername() {
		return "bonch337bot";
	}

	@Override
	public String getBotToken() {
		return "346385765:AAHUero60JgKi5F7ak8w6gRWQpxXtE6HFZw";
	}

	@Override
	public void onUpdateReceived(Update update) {
		Message message = update.getMessage();
		if (message != null && message.hasText()) {
			switch (message.getText()) {
			case "/start":
				sendMsg(message, "Привет, я робот");
				break;
			case "/help":
				sendMsg(message, "Ты че вообще еблан, с простейшим интерфейсом справится не можешь!");
				break;
			default:
				if (message.getText().startsWith("/фио")) {
					sendFio(message, te.search(message.getText().substring(5)));
				} else {
					if (message.getText().startsWith("/поиск")) {
						sendResult(message, te.search(message.getText().substring(7)));
					} else {
						sendMsg(message, "Я не знаю что ответить на это");
					}
				}
				break;
			}
		}
	}

	private void sendMsg(Message message, String text) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.enableMarkdown(true);
		sendMessage.setChatId(message.getChatId().toString());
		// sendMessage.setReplyToMessageId(message.getMessageId());
		sendMessage.setText(text);
		try {
			sendMessage(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private void sendFio(Message message, ArrayList<Prepod> list) {
		if (list.isEmpty()) {
			sendMsg(message, "По запросу ничего не найдено!");
		} else {
			sendMsg(message, "Результат запроса:");
			for (Prepod prep : list) {
				sendMsg(message, prep.name);
			}
		}

	}

	private void sendResult(Message message, ArrayList<Prepod> list) {
		if (list.isEmpty()) {
			sendMsg(message, "По запросу ничего не найдено!");
		} else {
			sendMsg(message, "Результат запроса:");
			for (Prepod prep : list) {
				String s = prep.name + ", " + prep.dept + ", " + prep.mail + ", " + prep.address + ", " + prep.number;
				sendMsg(message, s);
			}
		}
	}

}