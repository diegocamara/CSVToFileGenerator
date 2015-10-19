package bdstatement;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class InsertStatement extends AbstractDBStatement implements
		IDBStatement {

	private final String EXPRESSION_PART_1 = "INSERT INTO";
	private final String EXPRESSION_PART_2 = "VALUES";

	private String tableName;
	private Map<String, String> attrs;
	private DialectEnum dialectEnum;
	private List<String> columns;
	private List<String> values;

	public InsertStatement() {
		setTableName(StringUtils.EMPTY);
		setAttrs(new HashMap<String, String>());
		setDialect(DialectEnum.MYSQL);
		setColumns(new LinkedList<String>());
		setValues(new LinkedList<String>());
	}

	public InsertStatement(String tableName, Map<String, String> attrs,
			DialectEnum dialectEnum) {
		setTableName(tableName);
		setAttrs(attrs);
		setDialect(dialectEnum);
		setColumns(new LinkedList<String>());
		setValues(new LinkedList<String>());
		configureColumnsAndValues();
	}

	public void put(String column, String value) {
		if (isAttrsNotNull()) {
			getAttrs().put(column, value);
		}
	}

	public void removeColumn(String column) {
		if (isAttrsNotNull()) {
			getAttrs().remove(column);
		}
	}

	private boolean isAttrsNotNull() {
		return getAttrs() != null;
	}

	@Override
	public String toSql() {
		return String.format("%s %s (%s) %s (%s);", EXPRESSION_PART_1,
				getTableName(), obtainColumns(), EXPRESSION_PART_2,
				obtainValues());
	}

	private String obtainColumns() {

		String columns = StringUtils.EMPTY;

		for (int column = 0; column < this.columns.size(); column++) {
			columns = columns.concat(this.columns.get(column)
					+ (column + 1 < this.columns.size() ? ", "
							: StringUtils.EMPTY));
		}

		columns = removeAccents(columns);

		return columns.toUpperCase();
	}

	private String obtainValues() {

		String values = StringUtils.EMPTY;

		for (int value = 0; value < this.values.size(); value++) {
			values = values.concat(this.values.get(value)
					+ (value + 1 < this.values.size() ? ", "
							: StringUtils.EMPTY));
		}

		return values;
	}

	private void configureColumnsAndValues() {

		for (Map.Entry<String, String> attr : getAttrs().entrySet()) {

			String column = attr.getKey();
			String value = attr.getValue();

			getColumns().add(column);
			getValues().add(value);

		}

	}

	@Override
	protected DialectEnum getDialect() {
		return this.dialectEnum;
	}

	@Override
	protected void setDialect(DialectEnum dialectEnum) {
		this.dialectEnum = dialectEnum;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Map<String, String> getAttrs() {
		return attrs;
	}

	public void setAttrs(Map<String, String> attrs) {
		this.attrs = attrs;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

}
