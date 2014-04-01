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
		
    	String executionID = "''";
    	if(conventional.getExecution() != null)
    		executionID = conventional.getExecution().getId();
    	
		StringBuilder query = new StringBuilder();

		query.append("insert into ConventionalMetrics (choesion,"
				+ " macAggregation, meanDepComps, meanNumOps, sumClassesDepIn,"
				+ " sumClassesDepOut, sumDepIn, sumDepOut, execution_id, is_all, experiement_id)"
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
		query.append(executionID);
		query.append(",");
		if(conventional.getExecution() == null)
			query.append("1");
		else
			query.append("2");
		query.append(",");
		query.append(conventional.getExperiement().getId());
		query.append(")");

		try {
			this.statement.executeUpdate(query.toString());
		} catch (SQLException ex) {
			Logger.getLogger(Elegance.class.getName()).log(Level.SEVERE, null,	ex);
		}
	}

}
