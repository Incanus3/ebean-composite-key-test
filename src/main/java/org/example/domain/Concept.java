package org.example.domain;

import io.ebean.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@IdClass(ConceptId.class)
@SuppressWarnings("unused")
public class Concept {
  @Id
  @Column(name = "id", nullable = false)
  String id;

  @Id
  @Column(name = "network_id", nullable = false)
  String networkId;

  @Column(name = "label", nullable = false)
  String label;

  @JsonIgnore
  @OneToMany(mappedBy = "from", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  List<Connection> outgoingConnections;

  @JsonIgnore
  @OneToMany(mappedBy = "to", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  List<Connection> incomingConnections;

  public Concept(String networkId, String id, String label) {
    this.networkId = networkId;
    this.id = id;
    this.label = label;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNetworkId() {
    return networkId;
  }

  public void setNetworkId(String networkId) {
    this.networkId = networkId;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }
}
