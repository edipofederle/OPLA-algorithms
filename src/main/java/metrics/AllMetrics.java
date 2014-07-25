package metrics;

import java.util.ArrayList;
import java.util.List;

public class AllMetrics {

    private List<Conventional> conventional = new ArrayList<>();
    private List<Elegance> elegance = new ArrayList<>();
    private List<FeatureDriven> featureDriven = new ArrayList<>();
    private List<PLAExtensibility> plaExtensibility = new ArrayList<>();

    public List<Conventional> getConventional() {
	return conventional;
    }

    public void setConventional(List<Conventional> conventional) {
	this.conventional = conventional;
    }

    public List<Elegance> getElegance() {
	return elegance;
    }

    public void setElegance(List<Elegance> elegance) {
	this.elegance = elegance;
    }

    public List<FeatureDriven> getFeatureDriven() {
	return featureDriven;
    }

    public void setFeatureDriven(List<FeatureDriven> featureDriven) {
	this.featureDriven = featureDriven;
    }

    public List<PLAExtensibility> getPlaExtensibility() {
	return plaExtensibility;
    }

    public void setPlaExtensibility(List<PLAExtensibility> plaExtensibility) {
	this.plaExtensibility = plaExtensibility;
    }

}
