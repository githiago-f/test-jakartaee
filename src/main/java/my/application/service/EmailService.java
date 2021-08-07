package my.application.service;

import java.util.List;
import javax.ejb.Stateless;

@Stateless
public class EmailService {
  List<String> emails = List.of("email0@email.com", "email1@email.com", "email2@email.com", "email3@email.com",
      "email4@email.com");

  public List<String> getAllEmails() {
    return emails;
  }

  public String getEmail(Integer index) {
    return emails.get(index);
  }
}
