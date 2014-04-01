package database;



import static org.junit.Assert.*;

import java.io.File;
import java.sql.Statement;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

/**
 *
 * @author elf
 */
public class DatabaseTest {
    
    private static final String PATH_TO_DB = "src/test/resources/opla.db";
    
    @After
    public void cleanUp(){
        File f = new File(PATH_TO_DB);
        f.delete();
    }
    
    @Test
    public void testConnectionUnique() throws Exception{
        Database.setPathToDB(PATH_TO_DB);
        Statement connection1 = Database.getInstance().getConnection();
        Statement connection2 = Database.getInstance().getConnection();
        
        assertNotNull(connection1);
        assertNotNull(connection2);

        Assert.assertNotSame(connection1, connection2);
    }
    
}
