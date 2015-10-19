package bdstatement;

import java.text.Normalizer;

public abstract class AbstractDBStatement {

	protected String removeAccents(String string) {
		// Normalizer to remove accents.
		string = Normalizer.normalize(string, Normalizer.Form.NFD);
		string = string.replaceAll("[^\\p{ASCII}]", "");
		return string;
	}

	protected abstract DialectEnum getDialect();

	protected abstract void setDialect(DialectEnum dialectEnum);

}
