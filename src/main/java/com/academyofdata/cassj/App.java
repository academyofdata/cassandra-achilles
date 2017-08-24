package com.academyofdata.cassj;

import com.datastax.driver.core.*;
import info.archinnov.achilles.generated.ManagerFactoryBuilder_For_MovieLens;
import info.archinnov.achilles.generated.ManagerFactory_For_MovieLens;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.concurrent.Future;


public class App {
    private Cluster cluster;
    private Session session;

    public void connect(String node) {
        cluster = Cluster.builder().addContactPoint(node).withQueryOptions(new QueryOptions().setConsistencyLevel(ConsistencyLevel.ONE).setFetchSize(100)).build();
        Metadata cd = cluster.getMetadata();
        System.out.println("Connected to cluster:"+ cd.getClusterName());
        for ( Host host : cd.getAllHosts() ) {
            System.out.printf("Datatacenter: %s; Host: %s; Rack: %s\n", host.getDatacenter(), host.getAddress(), host.getRack());
        }
        session = cluster.connect();

    }

    public Cluster getCluster() {
        return cluster;
    }

    public Session getSession() {
        return session;
    }

    public void close() {
        cluster.close();
        //cluster.shutdown();
    }


    private static void achillesBasicTest(App client, ManagerFactory_For_MovieLens managerFactory){


        //User_Manager manager = managerFactory.forUser();
        managerFactory.forUser().crud().insert(new User(3L,"F",42,"23","22355-1")).execute();
        managerFactory.forMovie().crud().insert(new Movie(3,"3rd Other title etc", Arrays.asList("drama","comedy"))).execute();

        User uu = managerFactory.forUser().crud().findById(3L,42).get();
        System.out.println("gender of user with id 3 and age 42:"+uu.getGender());
        //managerFactory.forUser().crud().findById(2).get().getAge();
        //managerFactory.forUser().crud().f
        System.out.println("title of movie with id 3:"+managerFactory.forMovie().crud().findById(3).get().getTitle());
    }

    private static void achillesFileTest(App client, ManagerFactory_For_MovieLens managerFactory, String file){
        String sep = ",";
        try {
            java.io.BufferedReader br = new BufferedReader(new FileReader(file));

            CLineReader clr = new CLineReader(br);
            String current;
            while(clr.iterator().hasNext()){
                current = clr.iterator().next();

                System.out.println("processing:"+current);
                String[] parts = current.split(sep);
                if(parts.length>4){
                    managerFactory.forUser().crud().insert(
                            new User(new Long(parts[0]).longValue(),parts[1],new Integer(parts[2]).intValue(),parts[3],parts[4])
                    ).executeAsync().thenRun( () -> System.out.println("Inserted uid:"+parts[0]) );

                    //System.out.println("done:"+f.get());
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) {

        App client = new App();
        String nodeIP = "192.168.56.88";
        client.connect(nodeIP);
        System.out.println("connected to node:"+nodeIP);

        ManagerFactory_For_MovieLens managerFactory = ManagerFactoryBuilder_For_MovieLens
                .builder(client.getCluster())
                .withDefaultKeyspaceName("movielens")
                .doForceSchemaCreation(true)
                .build();

        achillesBasicTest(client,managerFactory);
        if(args.length>0){
            achillesFileTest(client,managerFactory,args[0]);
        }

        client.close();

    }
}
