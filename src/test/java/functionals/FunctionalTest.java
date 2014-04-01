///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package functionals;
//
//import java.sql.Statement;
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import persistence.ExecutionPersistence;
//import persistence.FunsResultPersistence;
//import persistence.InfosResultPersistence;
//import results.Execution;
//import results.Experiment;
//import results.FunResults;
//import results.InfoResult;
//import database.Database;
//import factories.Factory;
//
///**
// *
// * @author elf
// */
//public class FunctionalTest {
//    
//    
//    private static final String PATH_TO_DB = "src/test/resources/opla_test.db";
//    private Statement connection;
//    
//    
//    @Before
//    public void setup() throws Exception{
//        Database.setPathToDB(PATH_TO_DB);
//        connection = Database.getInstance().getConnection();
//    }
//    
//    @Test
//    public void test1() throws Exception{
//        
//        //First step: Creates a experiement and persist it
//        Experiment experiement = new Experiment("ExpTest1", "a description"); 
//        experiement.save();
//        
//        //Second step
//        //Create a execution. Execution is each run of experiement.
//        //A execution belongs to a experiement.
//        Execution execution = new Execution(experiement);
//        
//        //Third step: Some fake datas (Test propose only).
//        //This datas will come from algoritm execution.
//        List<InfoResult> infos = Factory.givenInfos(execution.getId());
//        List<FunResults> funs = Factory.givenFuns(execution.getId());
//        
//        execution.setInfos(infos);
//        execution.setFuns(funs);
//        
//        //Fourth step: Initialize ExecutionPersistence class
//        ExecutionPersistence persistence = new ExecutionPersistence(
//                connection,
//                new InfosResultPersistence(connection),
//                new FunsResultPersistence(connection));
//        
//        
//        //Fifth Step: Persiste One execution.
//        //This will persiste the execution, funs datas (objectives), infos datas (numbers of classes etc.)
//        //Also persite Metrics
//        persistence.persist(execution);
//        
//
//    }
//    
//}
