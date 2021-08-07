package my.application.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import my.application.service.EmailService;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("emails")
public class ApplicationServlet extends HttpServlet {

  EmailService emailService;

  @Inject
  public ApplicationServlet(EmailService emailService) {
    this.emailService = emailService;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setStatus(200);
    PrintWriter pw = resp.getWriter();
    emailService.getAllEmails().forEach(email -> pw.println(email));
    emailService.getEmail(1);
  }
}
