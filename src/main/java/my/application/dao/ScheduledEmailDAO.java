package my.application.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import my.application.entity.ScheduledEmail;

@Stateless
public class ScheduledEmailDAO {
  Logger logger = Logger.getLogger(ScheduledEmailDAO.class.getName());

  @PersistenceContext(unitName = "schedule-emails")
  EntityManager em;

  public ScheduledEmail insert(ScheduledEmail email) {
    logger.info("insert email " + email.getEmail());
    em.persist(email);
    email.createDefaultLink();
    return email;
  }

  public List<ScheduledEmail> findAll() {
    String sql = "SELECT e FROM ScheduledEmail e";
    return em.createQuery(sql, ScheduledEmail.class).getResultList();
  }

  public List<ScheduledEmail> findByScheduled(boolean status) {
    String sql = "SELECT e FROM ScheduledEmail e WHERE e.scheduled = 1";
    return em.createQuery(sql, ScheduledEmail.class).getResultList();
  }

  public ScheduledEmail findById(Long id) {
    return em.find(ScheduledEmail.class, id);
  }

  public void update(ScheduledEmail email) {
    em.merge(email);
  }

  public void delete(Long id) {
    ScheduledEmail email = em.find(ScheduledEmail.class, id);
    if (email == null) {
      throw new IllegalArgumentException("No email found with id " + id);
    }
    em.remove(email);
  }
}
