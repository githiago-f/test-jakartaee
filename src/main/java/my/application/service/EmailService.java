package my.application.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.NoArgsConstructor;
import my.application.dao.ScheduledEmailDAO;
import my.application.entity.ScheduledEmail;

@Stateless
@NoArgsConstructor
public class EmailService {
  ScheduledEmailDAO scheduledEmailDAO;

  @Inject
  public EmailService(ScheduledEmailDAO scheduledEmailDAO) {
    this.scheduledEmailDAO = scheduledEmailDAO;
  }

  public List<ScheduledEmail> getAllEmails() {
    return scheduledEmailDAO.findAll();
  }

  public ScheduledEmail getEmail(Long id) {
    return scheduledEmailDAO.findById(id);
  }

  public ScheduledEmail insert(ScheduledEmail email) {
    return scheduledEmailDAO.insert(email);
  }

  public void update(ScheduledEmail email) {
    scheduledEmailDAO.update(email);
  }

  public void delete(Long id) {
    scheduledEmailDAO.delete(id);
  }
}
