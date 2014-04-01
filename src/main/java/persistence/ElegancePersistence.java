package persistence;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import metrics.Elegance;

public class ElegancePersistence {
	
	private Statement statement;
	
	public ElegancePersistence(Statement statement){
		this.statement = statement;
	}
	
    /**
     * Save to database
     */
    public void save(Elegance eleganceMetric) {
    	StringBuilder query = new StringBuilder();
    	query.append("insert into EleganceMetrics (nac,atmr,ec,elegance,execution_id) values (");
    	query.append(eleganceMetric.getNac());
    	query.append(",");
    	query.append(eleganceMetric.getAtmr());
    	query.append(",");
    	query.append(eleganceMetric.getEc());
    	query.append(",");
    	query.append(eleganceMetric.total());
    	query.append(",");
    	query.append(eleganceMetric.getExecution().getId());
    	query.append(")");
    	
        try {
            this.statement.executeUpdate(query.toString());
        } catch (SQLException ex) {
            Logger.getLogger(Elegance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
