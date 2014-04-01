package persistence;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import metrics.Elegance;
import metrics.FeatureDriven;

public class FeatureDrivenPersistence {
	
	
	private Statement statement;
	
	public FeatureDrivenPersistence(Statement statement){
		this.statement = statement;
	}
	
	
    public void save(FeatureDriven fd) {
        StringBuilder query = new StringBuilder();
        query.append("insert into FeatureDrivenMetrics (msiAggregation, cdac, cdai, cdao, cibc, iibc, oobc, lcc, lccClass, cdaClass, cibClass, execution_id) values (");
        query.append(fd.getMsiAggregation());
        query.append(",");
        query.append(fd.getCdac());
        query.append(",");
        query.append(fd.getCdai());
        query.append(",");
        query.append(fd.getCdao());
        query.append(",");
        query.append(fd.getCibc());
        query.append(",");
        query.append(fd.getIibc());
        query.append(",");
        query.append(fd.getOobc());
        query.append(",");
        query.append(fd.getLcc());
        query.append(",");
        query.append(fd.getLccClass());
        query.append(",");
        query.append(fd.getCdaClass());
        query.append(",");
        query.append(fd.getCibClass());
        query.append(",");
        query.append(fd.getExecution().getId());
        query.append(")");
             
        try {
            this.statement.executeUpdate(query.toString());
        } catch (SQLException ex) {
            Logger.getLogger(Elegance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
