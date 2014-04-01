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
		
    	String executionID = "''";
    	if(plaExtensibility.getExecution() != null)
    		executionID = plaExtensibility.getExecution().getId();
    	
		StringBuilder query = new StringBuilder();

		query.append("insert into PLAExtensibilityMetrics (plaExtensibility, execution_id, is_all, experiement_id) values (");
		query.append(plaExtensibility.getPlaExtensibility());
		query.append(",");
		query.append(executionID);
		query.append(",");
        if(plaExtensibility.getExecution() == null)
        	query.append("1");
        else
        	query.append("0");
        query.append(",");
        query.append(plaExtensibility.getExperiement().getId());
		query.append(")");

		try {
			this.statement.executeUpdate(query.toString());
		} catch (SQLException ex) {
			Logger.getLogger(Elegance.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
