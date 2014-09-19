import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import arquitetura.builders.ArchitectureBuilder;
import arquitetura.representation.Architecture;
import arquitetura.representation.Class;
import arquitetura.representation.Interface;
import arquitetura.representation.Method;

public class DiffModel {

    public static void main(String args[]) throws Exception {
	String modelOriginal = "/Users/elf/Documents/workspace/PLAS/mm/MobileMedia.uml";
	String modelGerado = "/Users/elf/Documents/workspace/PLAS/VAR_All_MobileMedia-1618442217.uml";

	ArchitectureBuilder b = new ArchitectureBuilder();
	Architecture original = b.create(modelOriginal);
	Architecture gerado = b.create(modelGerado);

	System.out.println("Orignial");
	print(original);
	System.out.println("\n\n");
		
	System.out.println("Gerado");
	print(gerado);

    }

    @SuppressWarnings("unchecked")
    private static void print(Architecture original) {
	Set<Class> klasses = original.getAllClasses();
	
	System.out.println("\tClasses:");
	for (Class klass : klasses) {
	    System.out.println("\t\tName: " + klass.getName() + " m:"+klass.getAllMethods().size() + " a:"+klass.getAllAttributes().size());
	}
    }

}
