package Bot.BonchBot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TableEditor {
	private Connection connection = null;;
	private PreparedStatement pstAdd;
	private PreparedStatement pstUpdate;
	private PreparedStatement pstSearch;
	ResultSet rs;

	TableEditor() {
		try {
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "1");

			pstAdd = connection.prepareStatement(
					"Insert into Prepod  (name, dept, mail, address, number) values (?, ?, ?, ?, ?);");

			pstUpdate = connection.prepareStatement("Update Prepod set address = ?, number = ? where name = ?;");

			pstSearch = connection
					.prepareStatement("Select name, dept, mail, address, number from Prepod where name Like ?;");

		} catch (Exception e) {
			System.out.println("Не удалось подключиться к БД!");
		}
	}

	void add(String name, String dept, String mail, String address, String number) {
		try {
			pstAdd.setString(1, name);
			pstAdd.setString(2, dept);
			pstAdd.setString(3, mail);
			pstAdd.setString(4, address);
			pstAdd.setString(5, number);
			pstAdd.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Не удалось добавить запись! " + e.getMessage());
		}
	}

	void update(String address, String number, String lastName) {
		try {
			pstUpdate.setString(1, address);
			pstUpdate.setString(2, number);
			pstUpdate.setString(3, lastName);
			pstUpdate.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Не удалось обновить запись!");
		}
	}

	void close() throws SQLException {
		if (connection != null) {
			pstAdd.close();
			pstUpdate.close();
			connection.close();
		}
	}

	ArrayList<Prepod> search(String lastName) {
		ArrayList<Prepod> list = new ArrayList<>();
		try {
			pstSearch.setString(1, (lastName + "%"));
			rs = pstSearch.executeQuery();

			while (rs.next()) {
				String name = rs.getString("name");
				String dept = rs.getString("dept");
				String mail = rs.getString("mail");
				String address = rs.getString("address");
				String number = rs.getString("number");
				Prepod prep = new Prepod(name, dept, mail, address, number);
				list.add(prep);
			}
		} catch (SQLException e) {
			System.out.println("Поиск не удался: " + e.getMessage());
		}

		return list;
	}

}
