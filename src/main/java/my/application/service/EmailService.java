package my.application.service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.NoArgsConstructor;
import my.application.dao.ScheduledEmailDAO;
import my.application.entity.ScheduledEmail;

@Stateless
@NoArgsConstructor
public class EmailService {
  private static final Logger LOG = Logger.getLogger(EmailService.class.getName());
  private ScheduledEmailDAO scheduledEmailDAO;

  @Inject
  public EmailService(ScheduledEmailDAO scheduledEmailDAO) {
    this.scheduledEmailDAO = scheduledEmailDAO;
  }

  public List<ScheduledEmail> getAllEmails() {
    return scheduledEmailDAO.findAll();
  }

  public Optional<ScheduledEmail> findEmail(Long id) {
    return scheduledEmailDAO.findById(id);
  }

  public ScheduledEmail insert(ScheduledEmail email) {
    return scheduledEmailDAO.insert(email);
  }

  public ScheduledEmail scheduleEmail(ScheduledEmail email) {
    email.setScheduled(true);
    return scheduledEmailDAO.update(email);
  }

  public void delete(Long id) {
    scheduledEmailDAO.delete(id);
  }

  public List<ScheduledEmail> findNonScheduledEmails() {
    return scheduledEmailDAO.findNonScheduledEmails();
  }

  public void send(ScheduledEmail email) {
    try {
      Thread.sleep(2000);
      LOG.info(String.format("Sending email %s", email.getEmail()));
    } catch (InterruptedException e) {
      LOG.warning(e.getMessage());
    }
  }
}
