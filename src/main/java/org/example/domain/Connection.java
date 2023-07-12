package org.example.domain;

import io.ebean.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@IdClass(ConceptId.class)
@SuppressWarnings("unused")
public class Connection {
  @Id
  @Column(name = "id", nullable = false)
  String id;

  @Id
  @Column(name = "network_id", nullable = false)
  String networkId;

  @Column(name = "label", nullable = false)
  String label;

  public Connection(
    String networkId, String id, String label,
    Concept from, Concept to
  ) {
    this.networkId = networkId;
    this.id = id;
    this.label = label;
    this.from = from;
    this.to = to;
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

  @JsonIgnore
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumns({
    @JoinColumn(name = "from_conc", referencedColumnName = "id", nullable = false),
    @JoinColumn(
      name = "network_id", referencedColumnName = "network_id",
      nullable = false, insertable = false, updatable = false
    )
  })
  Concept from;

  @JsonIgnore
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumns({
    @JoinColumn(name = "to_conc", referencedColumnName = "id", nullable = false),
    @JoinColumn(
      name = "network_id", referencedColumnName = "network_id",
      nullable = false, insertable = false, updatable = false
    )
  })
  Concept to;
}
