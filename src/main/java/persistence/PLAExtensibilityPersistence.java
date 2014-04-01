package persistence;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import metrics.Elegance;
import metrics.PLAExtensibility;

public class PLAExtensibilityPersistence {

	private Statement statement;

	public PLAExtensibilityPersistence(Statement statement) {
		this.statement = statement;
	}

	public void save(PLAExtensibility plaExtensibility) {
		StringBuilder query = new StringBuilder();

		query.append("insert into PLAExtensibilityMetrics (plaExtensibility, execution_id) values (");
		query.append(plaExtensibility.getPlaExtensibility());
		query.append(",");
		query.append(plaExtensibility.getExecution().getId());
		query.append(")");

		try {
			this.statement.executeUpdate(query.toString());
		} catch (SQLException ex) {
			Logger.getLogger(Elegance.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
