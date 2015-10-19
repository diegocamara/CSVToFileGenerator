package filegenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import bdstatement.DialectEnum;
import bdstatement.InsertStatement;

public class CSVFileGenerator {

	private String filePath;
	private String outputPath;
	private String encode;

	public CSVFileGenerator(String filepath, String outputPath, String encode) {
		setFilePath(filepath);
		setOutputPath(outputPath);
		setEncode(encode);

	}

	public void generateJson() {
		jsonGenerate();
	}

	public void generateSqlInsert(String tableName, DialectEnum dialectEnum) {
		sqlInsertGenerate(tableName, dialectEnum);
	}

	private void sqlInsertGenerate(String tableName, DialectEnum dialectEnum) {

		BufferedReader bufferedReader = null;
		FileWriter fileWriter = null;

		try {

			File file = new File(getFilePath());

			bufferedReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), getEncode()));

			fileWriter = new FileWriter(getOutputPath());

			// Primeira linha é referente aos atributos.
			String line = bufferedReader.readLine();
			String[] coluns = line.split(";");

			while ((line = bufferedReader.readLine()) != null) {

				Map<String, String> attrs = new HashMap<String, String>();

				String[] attr = line.split(";");

				for (int column = 0; column < coluns.length; column++) {

					if (column < attr.length) {
						attrs.put(coluns[column].toLowerCase(), attr[column]);
					} else {
						attrs.put(coluns[column].toLowerCase(), "");
					}

				}

				InsertStatement insertStatement = new InsertStatement(
						tableName, attrs, dialectEnum);

				fileWriter.write(insertStatement.toSql() + "\n");
				fileWriter.flush();

			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			try {
				fileWriter.close();
				bufferedReader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}

	}

	private void jsonGenerate() {

		BufferedReader bufferedReader = null;
		FileWriter fileWriter = null;

		try {

			File file = new File(getFilePath());

			bufferedReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), getEncode()));

			fileWriter = new FileWriter(getOutputPath());

			// Primeira linha é referente aos atributos.
			String line = bufferedReader.readLine();
			String[] coluns = line.split(";");

			Integer linesCount = obtainLinesCount(getFilePath()) - 1;

			Integer index = 1;

			fileWriter.write("{\n");
			fileWriter.flush();

			while ((line = bufferedReader.readLine()) != null) {

				JSONObject jsonObject = new JSONObject();

				String[] attr = line.split(";");

				for (int column = 0; column < coluns.length; column++) {

					if (column < attr.length) {
						jsonObject.put(coluns[column].toLowerCase(),
								attr[column]);
					} else {
						jsonObject.put(coluns[column].toLowerCase(), "");
					}

				}

				fileWriter.write("\"index_"
						+ index
						+ "\":"
						+ jsonObject.toJSONString().concat(
								obterVirgular(index, linesCount) + "\n"));
				fileWriter.flush();
				index++;

			}

			fileWriter.write("}");
			fileWriter.flush();

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			try {
				fileWriter.close();
				bufferedReader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}
	}

	private Integer obtainLinesCount(String path) {

		Integer linesCount = 0;

		File file = null;

		BufferedReader bufferedReader = null;

		try {
			file = new File(path);
			bufferedReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), getEncode()));

			while (bufferedReader.readLine() != null) {
				linesCount++;
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			try {
				bufferedReader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}

		return linesCount;
	}

	private static String obterVirgular(Integer index, Integer linesCount) {
		return index < linesCount ? "," : "";
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

}
