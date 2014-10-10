import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import arquitetura.builders.ArchitectureBuilder;
import arquitetura.helpers.UtilResources;
import arquitetura.representation.Architecture;
import arquitetura.representation.Attribute;
import arquitetura.representation.Class;
import arquitetura.representation.Concern;
import arquitetura.representation.Element;
import arquitetura.representation.Interface;
import arquitetura.representation.Method;

public class DiffModel {

    static List<Class> klassesOriginal = new LinkedList<>();
    static List<Class> klassesGerado = new LinkedList<>();
    static List<Class> diffKlasses = new LinkedList<>();
    
    static List<Interface> interfacesOriginal = new LinkedList<>();
    static List<Interface> interfaceGerado = new LinkedList<>();
    static List<Interface> diffInterfaces = new LinkedList<>();

    public static void main(String args[]) throws Exception {
	String modelGerado = "/Users/elf/Documents/workspace/PLAS/9316217788/VAR_All_agm-1796845156.uml";
	String modelOriginal = "/Users/elf/mestrado/sourcesMestrado/architecture-representation/src/test/java/resources/agmfinal/agm.uml";

	ArchitectureBuilder b = new ArchitectureBuilder();
	Architecture gerado = b.create(modelGerado);
	Architecture original = b.create(modelOriginal);
//
	loadKlass(klassesOriginal, original);
	loadKlass(klassesGerado, gerado);
	
	loadInterfaces(interfacesOriginal, original);
	loadInterfaces(interfaceGerado, gerado);
	
	System.out.println("------------------------ ORIGINAL ---------------------------");
	print(original, "o", klassesOriginal);
	printInterface(original, "o", interfacesOriginal);
	System.out.println("------------------------ GERADO ---------------------------");
	print(gerado, "g", klassesGerado);
	printInterface(gerado, "g", interfaceGerado);

	System.out.println("\n\n------------------------ DIFF INFOS ---------------------------\n");
	printDiff();

    }

    private static void printDiff() {
	System.out.println("Original Classes Number: " + klassesOriginal.size());
	System.out.println("Generated Classes Number: " + klassesGerado.size());
	System.out.println("Diff Number Classes: " + diffKlasses.size());
	for (Class diffKlass : diffKlasses) {
	    Class k = getOriginalKlassByName(diffKlass.getName(), klassesOriginal);
	    printKlassInfoComplete(diffKlass, k == null ? false : true, diffKlass);
	}
    }

    private static Class getOriginalKlassByName(String name, List<Class> list) {
	for (Class klass : klassesOriginal) {
	    if (klass.getName().equalsIgnoreCase(name))
		return klass;
	}
	return null;
    }

    private static void printKlassInfoComplete(Class diffKlass, boolean isOriginal, Class klassName) {
	String newElement = null;
	if (diffKlassesContains(klassName.getName(), diffKlasses)) {
	    newElement = " (WARNING: The klass:'" + diffKlass.getName() + "'"
		    + " changed OR is new one. The content here:)";
	    diffKlass = klassName;
	}

	StringBuilder sb = new StringBuilder();
	sb.append(diffKlass.getName()).append(newElement != null ? newElement : "").append("\n");

	for (Attribute attr : diffKlass.getAllAttributes()) {
	    sb.append("\t").append(attr.getName()).append("<").append(attr.getOwnConcerns()).append(">\n");
	}
	if (diffKlass.getAllAttributes().isEmpty())
	    sb.append("\t;;no attributes\n");
	sb.append("\t").append("--------------------\t\n");

	for (Method m : diffKlass.getAllMethods()) {
	    sb.append("\t").append(m.getName()).append("<").append(m.getOwnConcerns()).append(">\n");
	}
	if (diffKlass.getAllMethods().isEmpty())
	    sb.append("\t;;no methods\n");

	System.out.println(sb.append("\n").toString());
	if (diffKlassesContains(diffKlass.getName(), diffKlasses) && diffKlassesContains(diffKlass.getName(), klassesOriginal)) {
	    Class originalKlass = getOriginalKlassByName(klassName.getName(), klassesGerado);
	    if (originalKlass != null && isOriginal)
		printKlassInfoComplete(originalKlass, false, originalKlass);
	}
    }

    private static boolean diffKlassesContains(String name, List<Class> list) {
	for (Class klass : list) {
	    if (klass.getName().equalsIgnoreCase(name)) {
		return true;
	    }
	}
	return false;
    }

    private static void loadKlass(List<Class> list, Architecture original) {
	for (Class class1 : original.getAllClasses()) {
	    list.add(class1);
	}
    }
    
    private static void loadInterfaces(List<Interface> list, Architecture original) {
	for (Interface class1 : original.getAllInterfaces()) {
	    list.add(class1);
	}
    }
    
    private static void print(Architecture original, String type, List<Class> klasses) {
	Collections.<Class> sort(klasses);
	System.out.println("\tClasses: (" + klasses.size() + ")");
	for (Class klass : klasses) {
	    if (type.equals("g") && originalContainsKlasse(klass)) {
		printKlassInfo(klass);
	    } else if (type.equals("o")) {
		printKlassInfo(klass);
	    }
	}
    }
    
    private static void printInterface(Architecture original, String type, List<Interface> interfaces) {
	Collections.<Interface> sort(interfaces);
	System.out.println("\tInterface: (" + interfaces.size() + ")");
	for (Interface inter : interfaces) {
	    if (type.equals("g") && originalContainsInterface(inter)) {
		printInterfaceInfo(inter);
	    } else if (type.equals("o")) {
		printInterfaceInfo(inter);
	    }
	}
    }

    private static void printKlassInfo(Class klass) {
	System.out.println("\t\tName: " + klass.getName() + klass.getOwnConcerns() + " m:"
		+ klass.getAllMethods().size() + " a:" + klass.getAllAttributes().size() + " - |"
		+ UtilResources.extractPackageName(klass.getNamespace()) + "|");
    }
    
    private static void printInterfaceInfo(Interface klass) {
	System.out.println("\t\tName: " + klass.getName() + klass.getOwnConcerns() + " m:"
		+ klass.getOperations().size() + " a:" + klass.getOperations().size() + " - |"
		+ UtilResources.extractPackageName(klass.getNamespace()) + "|");
    }

    private static boolean originalContainsKlasse(Class klass) {
	for (Class oKlass : klassesOriginal) {
	    if (oKlass.getName().equalsIgnoreCase(klass.getName())
		    && oKlass.getOwnConcerns().size() == klass.getOwnConcerns().size()
		    && klass.getAllAbstractMethods().size() == oKlass.getAllAbstractMethods().size()
		    && klass.getAllAttributes().size() == oKlass.getAllAttributes().size()
		    && haveSameConcernsForClass(klass, oKlass)
		    && haveSameConcerns(klass.getAllAttributes(), oKlass.getAllAttributes())
		    && haveSameConcerns(klass.getAllMethods(), oKlass.getAllMethods()))
		return true;
	}
	diffKlasses.add(klass);
	return false;
    }
    
    private static boolean originalContainsInterface(Interface inter) {
	for (Interface oKlass : interfacesOriginal) {
	    if (oKlass.getName().equalsIgnoreCase(inter.getName())
		    && oKlass.getOwnConcerns().size() == inter.getOwnConcerns().size()
		    && haveSameConcernsForClass(inter, oKlass)
		    && haveSameConcerns(inter.getOperations(), oKlass.getOperations()))
		return true;
	}
	diffInterfaces.add(inter);
	return false;
    }

    private static boolean haveSameConcerns(Set<? extends Element> allAttributes, Set<? extends Element> allAttributes2) {
	List<String> gConcerns = new ArrayList<>();
	for (Element attr : allAttributes)
	    for (Concern concern : attr.getOwnConcerns())
		gConcerns.add(concern.getName());

	for (Element attr : allAttributes2)
	    for (Concern concern : attr.getOwnConcerns())
		if (!gConcerns.contains(concern.getName()))
		    return false;
	return true;
    }

    private static boolean haveSameConcernsForClass(Element gklasss, Element oKlass) {
	List<String> gConcerns = new ArrayList<>();
	for (Concern concern : gklasss.getOwnConcerns())
	    gConcerns.add(concern.getName());

	for (Concern concern : oKlass.getOwnConcerns())
	    if (!gConcerns.contains(concern.getName()))
		return false;
	return true;
    }

}