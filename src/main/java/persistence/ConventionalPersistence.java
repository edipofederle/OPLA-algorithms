package persistence;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import metrics.Conventional;
import metrics.Elegance;

public class ConventionalPersistence {

	
	private Statement statement;
	
	public ConventionalPersistence(Statement statement){
		this.statement = statement;
	}
	
	public void save(Conventional conventional) {
		StringBuilder query = new StringBuilder();

		query.append("insert into ConventionalMetrics (choesion,"
				+ " macAggregation, meanDepComps, meanNumOps, sumClassesDepIn,"
				+ " sumClassesDepOut, sumDepIn, sumDepOut, execution_id)"
				+ " values (");

		query.append(conventional.getChoesion());
		query.append(",");
		query.append(conventional.getMacAggregation());
		query.append(",");
		query.append(conventional.getMeanDepComps());
		query.append(",");
		query.append(conventional.getMeanNumOps());
		query.append(",");
		query.append(conventional.getSumClassesDepIn());
		query.append(",");
		query.append(conventional.getSumClassesDepOut());
		query.append(",");
		query.append(conventional.getSumDepIn());
		query.append(",");
		query.append(conventional.getSumDepOut());
		query.append(",");
		query.append(conventional.getExecution().getId());
		query.append(")");

		try {
			this.statement.executeUpdate(query.toString());
		} catch (SQLException ex) {
			Logger.getLogger(Elegance.class.getName()).log(Level.SEVERE, null,	ex);
		}
	}

}
