package my.application.mdb;

import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;

import my.application.entity.ScheduledEmail;
import my.application.service.EmailService;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/queue/EmailQueue"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
public class EmailSchedulingMDB implements MessageListener {
  private static final Logger LOG = Logger.getLogger(EmailSchedulingMDB.class.getName());

  @Inject
  private EmailService emailService;

  @Override
  public void onMessage(Message message) {
    try {
      ScheduledEmail scheduledEmail = message.getBody(ScheduledEmail.class);
      LOG.info("JMS received message: " + scheduledEmail.getEmail());
      emailService.send(scheduledEmail);
    } catch (Exception e) {
      LOG.warning(e.getMessage());
    }
  }
}
