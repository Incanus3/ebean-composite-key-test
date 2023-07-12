package org.example.domain;

import io.ebean.Database;
import org.example.persistence.Databases;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

@SuppressWarnings("CommentedOutCode")
class CompositeForeignKeyTest {
  Database database;

  @BeforeEach
  public void setUp() {
    database = Databases.getDefault();
  }

  @AfterEach
  public void tearDown() {
    database.shutdown();
  }

  @Test
  public void createConnectionWithCompositeForeignKey() {
    String networkId = "test-network";
    Concept concept1 = new Concept(networkId, "concept1", "concept 1");
    Concept concept2 = new Concept(networkId, "concept2", "concept 2");

    database.saveAll(concept1, concept2);

    // Concept concept1Copy = new Concept(
    //   concept1.id, concept1.networkId, concept1.label,
    // );
    // Concept concept2Copy = new Concept(
    //   concept2.id, concept2.networkId, concept2.label,
    // );

    // System.out.println("=".repeat(100));
    // System.out.println(database.beanId(concept1));
    // System.out.println(database.reference(Concept.class, database.beanId(concept1)));
    // System.out.println(database.beanId(concept1Copy)); // this returns null
    // so this fails on the requireNonNull` call
    // Concept reference = database.reference(Concept.class, database.beanId(concept1Copy));
    // System.out.println(reference);
    // System.out.println(reference.id);
    // System.out.println(reference.networkId);
    // System.out.println(database.beanId(reference));
    // System.out.println(database.reference(Concept.class, database.beanId(concept1Copy)))

    // database.refresh(concept1Copy) // THIS FAILS with "id is null"
    // database.merge(concept1Copy) // THIS FAILS with "id is null"

    String connectionId = "test-connection";

    Connection connection = new Connection(
      networkId, connectionId, "test", concept1, concept2

      // THIS FAILS with NULL not allowed for column "CONN_FROM"
      // from = concept1Copy; to = concept2Copy

      // from = database.reference(Concept.class, database.beanId(concept1Copy))
      // to = database.reference(Concept.class, database.beanId(concept2Copy))
    );

    System.out.println("before save");
    System.out.println(connection);
    System.out.println(connection.from);
    System.out.println(connection.to);

    database.save(connection);

    Connection reloaded = database.find(Connection.class, new ConceptId(networkId, connectionId));
    Objects.requireNonNull(reloaded);

    System.out.println("after save and reload:");
    System.out.println(reloaded);
    // these are null
    System.out.println(reloaded.from);
    System.out.println(reloaded.to);
    // so these fail
    // System.out.println(reloaded.from.id);
    // System.out.println(reloaded.from.networkId);
    // and so do these, although this seems to work if we initialize `from` and `to`
    // to new Concepts - then their direct attributes are wrong, but the results of calling
    // `beanId` on them seems to be correct
    // System.out.println(database.beanId(reloaded.from));
    // System.out.println(database.beanId(reloaded.to));

    database.createQuery(Connection.class).delete();
    // this breaks if we uncomment the OneToMany relationships with cascade = ALL
    database.createQuery(Concept.class).delete();
  }
}
