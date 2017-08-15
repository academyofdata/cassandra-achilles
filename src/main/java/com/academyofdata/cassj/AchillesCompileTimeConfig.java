package com.academyofdata.cassj;


import info.archinnov.achilles.annotations.CompileTimeConfig;
import info.archinnov.achilles.type.CassandraVersion;
import info.archinnov.achilles.type.strategy.ColumnMappingStrategy;
import info.archinnov.achilles.type.strategy.InsertStrategy;
import info.archinnov.achilles.type.strategy.NamingStrategy;

@CompileTimeConfig(cassandraVersion = CassandraVersion.CASSANDRA_3_0_X,
        columnMappingStrategy = ColumnMappingStrategy.IMPLICIT,
        namingStrategy = NamingStrategy.SNAKE_CASE,
        insertStrategy = InsertStrategy.ALL_FIELDS,
        projectName = "MovieLens")

public interface AchillesCompileTimeConfig {

}