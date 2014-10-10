package algorithms;

import arquitetura.builders.ArchitectureBuilder;
import arquitetura.representation.Architecture;

public class TEste {
    
    public static void main(String args[]) throws Exception{
	ArchitectureBuilder b = new ArchitectureBuilder();
	Architecture a = b.create("/Users/elf/Documents/workspace/PLAS/VAR_All_MobileMedia-2169811619.uml");
	
	System.out.println(a.findClassByName("Media").get(0).getAllConcerns());
    }

}
