package Bot.BonchBot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Parser {
	XSSFWorkbook workbook = null;
	XSSFSheet sheet = null;
	TableEditor te;

	public void readFile() throws SQLException {

		try {
			workbook = new XSSFWorkbook(new FileInputStream("C:\\Users\\Farvest\\Desktop\\prepod.xlsx"));
		} catch (FileNotFoundException fe) {
			System.out.println("Файл не найден!");
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}

		sheet = workbook.getSheet("Лист1");

		te = new TableEditor();

		Iterator<Row> it = sheet.iterator();

		String mail = "";
		String address = "";
		String number = "";
		String lastName = "";
		int counter = 0;

		it.next();
		while (it.hasNext()) {
			counter++;
			Row row = it.next();
			String name = readCell(3, row);

			if (name != "") {
				String dept = readCell(1, row);
				mail = readCell(4, row);
				number = readCell(5, row);
				address = readCell(7, row);

				address = readAddress(row);

				lastName = name;

				te.add(name, dept, mail, address, number);

			} else {

				String num = readCell(5, row);
				number = num == "" ? num : number + ", " + num;

				String ad = readAddress(row);
				address = ((ad == "" && address != "") ? address : (ad + ", " + address));

				te.update(address, number, lastName);
			}

		}
		System.out.println("Выполнение программы закончено, записей обработано " + counter);
	}

	String readCell(int i, Row row) {
		try {
			return row.getCell(i).getStringCellValue();
		} catch (Exception e) {
			return ((Double) row.getCell(i).getNumericCellValue()).toString();
		}
	}

	void close() {
		if (workbook != null)
			try {
				workbook.close();
				te.close();
			} catch (Exception e) {
				System.out.println("Программа завершена не корректно: " + e.getMessage());
			}
	}

	String readAddress(Row row) {
		String address;
		String aud = readCell(8, row);

		switch (readCell(7, row)) {
		case "пр.Большевиков,22,к.2":
			address = " Б22/2";
			break;
		case "пр.Большевиков,22,к.1":
			address = " Б22/1";
			break;
		case "Наб. Мойки, 61":
			address = " Мойка 61";
			break;
		case "119; пр.Большевиков,22,к.1":
			address = " 119 Б22/1";
			break;
		case "Английский пр, д. 3":
			address = " Англ.пр.3";
		case "Колледж, ВО 3-я линия д.30":
			address = " ВО 3 линия д30";
			break;
		default:
			address = "";
			break;
		}

		switch (aud) {
		case "":
		case ".":
		case "0.":
			return address;
		case "Спортивный комплекс.":
			return aud;
		default:
			return aud + address;
		}

	}
}
