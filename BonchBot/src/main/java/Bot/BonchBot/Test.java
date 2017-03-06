package Bot.BonchBot;

import java.sql.SQLException;

public class Test {

	public static void main(String[] args) {
		Parser parser = new Parser();

		try {
			parser.readFile();
		} catch (SQLException e) {
			System.out.println("Ошибка во время выполнения программы: " + e.getMessage());
		} finally {
			parser.close();
		}
	}

}
