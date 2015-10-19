package filegenerator;

import bdstatement.DialectEnum;

public class StatementApp {

	public static void main(String[] args) {

		String filePath = "/home/diego/Downloads/teatros.csv";
		String outputPath = "/home/diego/Downloads/teatrosInserts.txt";
		String encode = "UTF-8";

		CSVFileGenerator fileGenerator = new CSVFileGenerator(filePath, outputPath,
				encode);

		fileGenerator.generateSqlInsert("TEATRO", DialectEnum.MYSQL);

	}

}
