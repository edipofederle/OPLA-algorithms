package database;

import org.junit.Test;

import persistence.ExperimentConfs;

public class ExperimentConfsTest {
    
    @Test
    public void test(){
	Database.setPathToDB("/Users/elf/oplatool/db/oplatool.db");
	
	System.out.println(ExperimentConfs.getConfigs("4717712141"));
	
    }

}
