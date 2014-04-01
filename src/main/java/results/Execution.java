package results;

import java.util.List;

import metrics.AllMetrics;
import utils.Id;

/**
 *
 * Essa classe representa cada execu����o de um dado experiementos.
 * 
 *
 */
public class Execution {
    
    private final String id;
    private List<InfoResult> infos;
    private List<FunResults> funs;
    private AllMetrics allMetrics;		
    private final Experiment experiment;
    private long time;
    
    //TODO metrics, time, hypervolume etc.
    
    public Execution(Experiment experiment){
        this.id = Id.generateUniqueId();
        this.experiment = experiment;
    }

    public List<InfoResult> getInfos() {
        return infos;
    }

    public void setInfos(List<InfoResult> infos) {
        this.infos = infos;
    }

    public List<FunResults> getFuns() {
        return funs;
    }

    public void setFuns(List<FunResults> funResults) {
        this.funs = funResults;
    }

    public String getId() {
        return this.id;
    }
    
    public Experiment getExperiement(){
        return this.experiment;
    }

	public long getTime() {
		return time;
	}

	public void setTime(long estimatedTime) {
		this.time = estimatedTime;
	}

	public void setAllMetrics(AllMetrics allMetrics) {
		this.allMetrics = allMetrics;
	}
	
	public AllMetrics getAllMetrics(){
		return this.allMetrics;
	}
	
}
