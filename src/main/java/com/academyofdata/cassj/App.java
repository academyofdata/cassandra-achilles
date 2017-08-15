package com.academyofdata.cassj;

import com.datastax.driver.core.*;
import info.archinnov.achilles.generated.ManagerFactoryBuilder_For_MovieLens;
import info.archinnov.achilles.generated.ManagerFactory_For_MovieLens;
import info.archinnov.achilles.generated.manager.User_Manager;

import java.util.Arrays;


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
    public static void main(String[] args) {

        App client = new App();
        client.connect("192.168.56.88");
        System.out.println("connected");
        ManagerFactory_For_MovieLens managerFactory = ManagerFactoryBuilder_For_MovieLens
                .builder(client.getCluster())
                .withDefaultKeyspaceName("movielens")
                .doForceSchemaCreation(true)
                .build();

        //User_Manager manager = managerFactory.forUser();
        managerFactory.forUser().crud().insert(new User(3L,"F",42,"23","22355-1")).execute();
        //managerFactory.forMovie().crud().insert(new Movie(2,"Other title etc", Arrays.asList("tragedy","comedy"))).execute();

        User uu = managerFactory.forUser().crud().findById(3L,42).get();
        //managerFactory.forUser().crud().findById(2).get().getAge();
        //managerFactory.forUser().crud().f
        managerFactory.forMovie().crud().findById(1).get().getTitle();
        System.out.println(uu.getGender());

        client.close();

    }
}
