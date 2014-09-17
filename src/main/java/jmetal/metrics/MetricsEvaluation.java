package jmetal.metrics;

import jmetal.metrics.PLAMetrics.extensibility.ExtensPLA;
import jmetal.metrics.concernDrivenMetrics.concernCohesion.LCC;
import jmetal.metrics.concernDrivenMetrics.concernCohesion.LCCClass;
import jmetal.metrics.concernDrivenMetrics.concernCohesion.LCCClassComponentResult;
import jmetal.metrics.concernDrivenMetrics.concernCohesion.LCCComponentResult;
import jmetal.metrics.concernDrivenMetrics.concernDiffusion.CDAC;
import jmetal.metrics.concernDrivenMetrics.concernDiffusion.CDACResult;
import jmetal.metrics.concernDrivenMetrics.concernDiffusion.CDAClass;
import jmetal.metrics.concernDrivenMetrics.concernDiffusion.CDAClassResult;
import jmetal.metrics.concernDrivenMetrics.concernDiffusion.CDAI;
import jmetal.metrics.concernDrivenMetrics.concernDiffusion.CDAIResult;
import jmetal.metrics.concernDrivenMetrics.concernDiffusion.CDAO;
import jmetal.metrics.concernDrivenMetrics.concernDiffusion.CDAOResult;
import jmetal.metrics.concernDrivenMetrics.interactionBeteweenConcerns.CIBC;
import jmetal.metrics.concernDrivenMetrics.interactionBeteweenConcerns.CIBCResult;
import jmetal.metrics.concernDrivenMetrics.interactionBeteweenConcerns.CIBClass;
import jmetal.metrics.concernDrivenMetrics.interactionBeteweenConcerns.CIBClassResult;
import jmetal.metrics.concernDrivenMetrics.interactionBeteweenConcerns.IIBC;
import jmetal.metrics.concernDrivenMetrics.interactionBeteweenConcerns.IIBCResult;
import jmetal.metrics.concernDrivenMetrics.interactionBeteweenConcerns.OOBC;
import jmetal.metrics.concernDrivenMetrics.interactionBeteweenConcerns.OOBCResult;
import jmetal.metrics.conventionalMetrics.ATMRElegance;
import jmetal.metrics.conventionalMetrics.ClassDependencyIn;
import jmetal.metrics.conventionalMetrics.ClassDependencyOut;
import jmetal.metrics.conventionalMetrics.DependencyIn;
import jmetal.metrics.conventionalMetrics.DependencyOut;
import jmetal.metrics.conventionalMetrics.ECElegance;
import jmetal.metrics.conventionalMetrics.MeanDepComponents;
import jmetal.metrics.conventionalMetrics.MeanNumOpsByInterface;
import jmetal.metrics.conventionalMetrics.NACElegance;
import jmetal.metrics.conventionalMetrics.RelationalCohesion;
import arquitetura.representation.Architecture;

public class MetricsEvaluation {

    public double evaluateATMRElegance(Architecture architecture) {
	ATMRElegance ATMR = new ATMRElegance(architecture);
	return ATMR.getResults();
    }

    public double evaluateECElegance(Architecture architecture) {
	ECElegance EC = new ECElegance(architecture);
	return EC.getResults();
    }

    public double evaluateNACElegance(Architecture architecture) {
	NACElegance NAC = new NACElegance(architecture);
	return NAC.getResults();
    }

    public double evaluateElegance(Architecture architecture) {
	Double EleganceFitness = 0.0;
	EleganceFitness = evaluateATMRElegance(architecture) + evaluateECElegance(architecture)
		+ evaluateNACElegance(architecture);
	return EleganceFitness;
    }

    public float evaluatePLAExtensibility(Architecture architecture) {
	float ExtensibilityFitness = 0;
	float Extensibility;
	ExtensPLA PLAExtens = new ExtensPLA(architecture);
	ExtensibilityFitness = PLAExtens.getValue();
	if (ExtensibilityFitness == 0)
	    Extensibility = 1000;
	else
	    Extensibility = 1 / ExtensibilityFitness;
	return (Extensibility);
    }

    public double evaluateMSIFitness(Architecture architecture) {
	Double sumCIBC = 0.0;
	Double sumIIBC = 0.0;
	Double sumOOBC = 0.0;
	Double sumCDAC = 0.0;
	Double sumCDAI = 0.0;
	Double sumCDAO = 0.0;
	Double sumLCC = 0.0;
	Double MSIFitness = 0.0;

	sumLCC = evaluateLCC(architecture);

	CIBC cibc = new CIBC(architecture);
	for (CIBCResult c : cibc.getResults().values()) {
	    sumCIBC += c.getInterlacedConcerns().size();
	}

	IIBC iibc = new IIBC(architecture);
	for (IIBCResult c : iibc.getResults().values()) {
	    sumIIBC += c.getInterlacedConcerns().size();
	}

	OOBC oobc = new OOBC(architecture);
	for (OOBCResult c : oobc.getResults().values()) {
	    sumOOBC += c.getInterlacedConcerns().size();
	}

	CDAC cdac = new CDAC(architecture);
	for (CDACResult c : cdac.getResults()) {
	    sumCDAC += c.getElements().size();
	}

	CDAI cdai = new CDAI(architecture);
	for (CDAIResult c : cdai.getResults()) {
	    sumCDAI += c.getElements().size();
	}

	CDAO cdao = new CDAO(architecture);
	for (CDAOResult c : cdao.getResults()) {
	    sumCDAO += c.getElements().size();
	}

	MSIFitness = sumLCC + sumCDAC + sumCDAI + sumCDAO + sumCIBC + sumIIBC + sumOOBC;
	return MSIFitness;
    }

    public Double evaluateCIBC(Architecture architecture) {
	Double sumCIBC = 0.0;

	CIBC cibc = new CIBC(architecture);
	for (CIBCResult c : cibc.getResults().values()) {
	    sumCIBC += c.getInterlacedConcerns().size();
	}

	return sumCIBC;
    }

    public Double evaluateIIBC(Architecture architecture) {

	Double sumIIBC = 0.0;

	IIBC iibc = new IIBC(architecture);
	for (IIBCResult c : iibc.getResults().values()) {
	    sumIIBC += c.getInterlacedConcerns().size();
	}

	return sumIIBC;
    }

    public Double evaluateOOBC(Architecture architecture) {

	Double sumOOBC = 0.0;

	OOBC oobc = new OOBC(architecture);
	for (OOBCResult c : oobc.getResults().values()) {
	    sumOOBC += c.getInterlacedConcerns().size();
	}
	return sumOOBC;
    }

    public Double evaluateCDAC(Architecture architecture) {

	Double sumCDAC = 0.0;
	CDAC cdac = new CDAC(architecture);
	for (CDACResult c : cdac.getResults()) {
	    sumCDAC += c.getElements().size();
	}
	return sumCDAC;
    }

    public Double evaluateCDAI(Architecture architecture) {
	Double sumCDAI = 0.0;

	CDAI cdai = new CDAI(architecture);
	for (CDAIResult c : cdai.getResults()) {
	    sumCDAI += c.getElements().size();
	}
	return sumCDAI;
    }

    public Double evaluateCDAO(Architecture architecture) {
	Double sumCDAO = 0.0;
	CDAO cdao = new CDAO(architecture);
	for (CDAOResult c : cdao.getResults()) {
	    sumCDAO += c.getElements().size();
	}
	return sumCDAO;
    }

    public Double evaluateLCC(Architecture architecture) {
	Double sumLCC = 0.0;
	LCC result = new LCC(architecture);

	for (LCCComponentResult component : result.getResults()) {
	    sumLCC += component.numberOfConcerns();
	}
	return sumLCC;
    }

    public Double evaluateCDAClass(Architecture architecture) {

	Double sumCDAClass = 0.0;

	CDAClass cdaclass = new CDAClass(architecture);
	for (CDAClassResult c : cdaclass.getResults()) {
	    sumCDAClass += c.getElements().size();
	}

	return sumCDAClass;
    }

    public Double evaluateCIBClass(Architecture architecture) {

	Double sumCIBClass = 0.0;

	CIBClass cibclass = new CIBClass(architecture);
	for (CIBClassResult c : cibclass.getResults().values()) {
	    sumCIBClass += c.getInterlacedConcerns().size();
	}

	return sumCIBClass;
    }

    public Double evaluateLCCClass(Architecture architecture) {
	Double sumLCCClass = 0.0;
	LCCClass result = new LCCClass(architecture);

	for (LCCClassComponentResult cls : result.getResults()) {
	    sumLCCClass += cls.numberOfConcerns();

	}
	return sumLCCClass;
    }

    // ----------------------------------------------------------------------------------
    public Double evaluateMACFitness(Architecture architecture) {
	double MACFitness = 0.0;
	double meanNumOps = 0.0;
	double meanDepComps = 0.0;
	double sumCohesion = 0.0;
	double sumClassesDepIn = 0.0;
	double sumClassesDepOut = 0.0;
	double sumDepIn = 0.0;
	double sumDepOut = 0.0;
	double iCohesion = 0.0;

	MeanNumOpsByInterface numOps = new MeanNumOpsByInterface(architecture);
	meanNumOps = numOps.getResults();

	MeanDepComponents depComps = new MeanDepComponents(architecture);
	meanDepComps = depComps.getResults();

	ClassDependencyOut classesDepOut = new ClassDependencyOut(architecture);
	sumClassesDepOut = classesDepOut.getResults();

	ClassDependencyIn classesDepIn = new ClassDependencyIn(architecture);
	sumClassesDepIn = classesDepIn.getResults();

	DependencyOut DepOut = new DependencyOut(architecture);
	sumDepOut = DepOut.getResults();

	DependencyIn DepIn = new DependencyIn(architecture);
	sumDepIn = DepIn.getResults();

	RelationalCohesion cohesion = new RelationalCohesion(architecture);
	sumCohesion = cohesion.getResults();
	if (sumCohesion == 0) {
	    iCohesion = 1.0;
	} else
	    iCohesion = 1 / sumCohesion;

	MACFitness = meanNumOps + meanDepComps + sumClassesDepIn + sumClassesDepOut + sumDepIn + sumDepOut
		+ (1 / sumCohesion);

	return MACFitness;
    }

    public Double evaluateMeanNumOps(Architecture architecture) {
	MeanNumOpsByInterface numOps = new MeanNumOpsByInterface(architecture);
	return numOps.getResults();
    }

    public Double evaluateMeanDepComps(Architecture architecture) {
	MeanDepComponents depComps = new MeanDepComponents(architecture);
	return depComps.getResults();
    }

    public int evaluateSumClassesDepIn(Architecture architecture) {
	ClassDependencyIn classesDepIn = new ClassDependencyIn(architecture);
	return classesDepIn.getResults();
    }

    public int evaluateSumClassesDepOut(Architecture architecture) {
	ClassDependencyOut classesDepOut = new ClassDependencyOut(architecture);
	return classesDepOut.getResults();
    }

    public double evaluateSumDepIn(Architecture architecture) {
	DependencyIn DepIn = new DependencyIn(architecture);
	return DepIn.getResults();
    }

    public double evaluateSumDepOut(Architecture architecture) {
	DependencyOut DepOut = new DependencyOut(architecture);
	return DepOut.getResults();
    }

    // ---------------------------------------------------------------------------------
    public double evaluateCohesion(Architecture architecture) {
	RelationalCohesion cohesion = new RelationalCohesion(architecture);
	return  cohesion.getResults();
    }

    public Double evaluateICohesion(Double sumCohesion) {
	return sumCohesion == 0 ? 1.0 : 1 / sumCohesion;
    }

}
