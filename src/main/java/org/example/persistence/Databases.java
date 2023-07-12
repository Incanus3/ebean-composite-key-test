package org.example.persistence;

import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
import org.example.domain.Concept;
import org.example.domain.ConceptId;
import org.example.domain.Connection;

public class Databases {
  public static final String DEFAULT = "db";

  public static Database getDefault() {
    DataSourceConfig dsConfig = new DataSourceConfig();

    dsConfig.setUsername("sa");
    dsConfig.setPassword("sa");
    dsConfig.setUrl("jdbc:h2:mem:semanticdb");
    dsConfig.setDriver("org.h2.Driver");

    DatabaseConfig databaseConfig = new DatabaseConfig();

    databaseConfig.setName(Databases.DEFAULT);
    databaseConfig.setDataSourceConfig(dsConfig);

    databaseConfig.setDdlRun(true);
    databaseConfig.setDdlGenerate(true);
    databaseConfig.setDefaultServer(false);

    databaseConfig.addClass(ConceptId.class);
    databaseConfig.addClass(Concept.class);
    databaseConfig.addClass(Connection.class);

    return DatabaseFactory.create(databaseConfig);
  }
}
