package my.application.entity;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;
import javax.ws.rs.core.UriBuilder;

import lombok.Data;

@Data
@Entity
@Table(name = "emails")
public class ScheduledEmail implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String email;
  private Boolean scheduled;

  @Transient
  @JsonbProperty(value = "_links")
  private List<String> links;

  public ScheduledEmail() {
    links = new ArrayList<>();
    createDefaultLink();
  }

  public ScheduledEmail(String email, Boolean scheduled) {
    this();
    this.email = email;
    this.scheduled = scheduled;
  }

  public void createDefaultLink() {
    if (id != null) {
      URI uri = UriBuilder.fromPath("/schedule/api/v1/emails/{id}").build(id);
      links.add(uri.toString());
    }
  }

  public void setScheduled(Boolean scheduled) {
    this.scheduled = scheduled;
  }

  public void addLink(URI link) {
    this.links.add(link.toString());
  }
}
