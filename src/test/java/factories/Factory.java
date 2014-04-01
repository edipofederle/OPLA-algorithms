package factories;

import java.util.ArrayList;
import java.util.List;

import results.Execution;
import results.Experiment;
import results.FunResults;
import results.InfoResult;

/**
 *
 * @author elf
 */
public class Factory {

   public static List<FunResults> givenFuns(Execution execution, Experiment experiement) {
       
	   	List<FunResults> funs = new ArrayList<FunResults>();
	   
        FunResults f1 = new FunResults();
        f1.setExecution(execution);
        f1.setExperiement(experiement);
        f1.setIsAll(0);
        f1.setObjectives("101010|19919191|1919911");
        
        FunResults f2 = new FunResults();
        f2.setExecution(execution);
        f2.setExperiement(experiement);
        f1.setIsAll(0);
        f2.setObjectives("101010|19919191|1919911");
        
        funs.add(f2);
        funs.add(f1);

        return funs;
    }

    public static List<InfoResult> givenInfos(Execution execution, Experiment experiement) {
        List<InfoResult> infos = new ArrayList<InfoResult>();
        InfoResult i1 = new InfoResult();
        i1.setIsAll(0);
        i1.setExecution(execution);
        i1.setExperiement(experiement);
        i1.setListOfConcerns("c1 | c2");
        i1.setNumberOfAbstraction(1);
        i1.setNumberOfAssociations(2);
        i1.setNumberOfClasses(10);
        i1.setNumberOfDependencies(1);
        i1.setNumberOfGeneralizations(2);
        i1.setNumberOfInterfaces(2);
        i1.setNumberOfPackages(2);
        i1.setNumberOfVariabilities(1);
        i1.setNumberOfassociationsClass(0);

        InfoResult i2 = new InfoResult();
        i2.setIsAll(0);
        i2.setExecution(execution);
        i2.setExperiement(experiement);
        i2.setListOfConcerns("c1 | c2");
        i2.setNumberOfAbstraction(1);
        i2.setNumberOfAssociations(2);
        i2.setNumberOfClasses(10);
        i2.setNumberOfDependencies(1);
        i2.setNumberOfGeneralizations(2);
        i2.setNumberOfInterfaces(2);
        i2.setNumberOfPackages(2);
        i2.setNumberOfVariabilities(1);
        i2.setNumberOfassociationsClass(0);

        infos.add(i1);
        infos.add(i2);
        return infos;
    }

}
