package my.application.service;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

@Singleton
public class ScheduledEmailProducer {
  private static final Logger LOGGER = Logger.getLogger(ScheduledEmailProducer.class.getName());

  @Inject
  private EmailService emailService;

  @Inject
  @JMSConnectionFactory("java:jboss/DefaultJMSConnectionFactory")
  private JMSContext jmsContext;

  @Resource(mappedName = "java:/jms/queue/EmailQueue")
  private Queue queue;

  @Schedule(hour = "*", minute = "*", second = "*/10")
  public synchronized void scheduleEmailsAtEmailQueue() {
    emailService.findNonScheduledEmails().stream().forEach(email -> {
      LOGGER.info("Scheduling email: " + email);
      emailService.scheduleEmail(email);
      jmsContext.createProducer().send(queue, email);
    });
  }
}
