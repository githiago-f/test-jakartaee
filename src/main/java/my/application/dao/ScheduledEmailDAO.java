package my.application.dao;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import my.application.entity.ScheduledEmail;

@Stateless
public class ScheduledEmailDAO {
  @PersistenceContext(unitName = "schedule-emails")
  EntityManager em;

  private static final Logger LOG = Logger.getLogger(ScheduledEmailDAO.class.getName());

  public ScheduledEmail insert(ScheduledEmail email) {
    LOG.info("insert email " + email.getEmail());
    em.persist(email);
    email.createDefaultLink();
    return email;
  }

  public List<ScheduledEmail> findAll() {
    String sql = "SELECT e FROM ScheduledEmail e";
    return em.createQuery(sql, ScheduledEmail.class).getResultList();
  }

  public Optional<ScheduledEmail> findById(Long id) {
    ScheduledEmail findEmail = em.find(ScheduledEmail.class, id);
    return Optional.ofNullable(findEmail);
  }

  public ScheduledEmail update(ScheduledEmail email) {
    return em.merge(email);
  }

  public void delete(Long id) {
    Optional<ScheduledEmail> findEmail = findById(id);
    findEmail.ifPresent(email -> {
      LOG.info("delete email " + email.getId());
      em.remove(email);
    });
    findEmail.orElseThrow(() -> new IllegalArgumentException("email not found"));
  }

  public List<ScheduledEmail> findNonScheduledEmails() {
    LOG.info("find non scheduled emails");
    String sql = "SELECT e FROM ScheduledEmail e WHERE e.scheduled = 0";
    return em.createQuery(sql, ScheduledEmail.class).getResultList();
  }
}
