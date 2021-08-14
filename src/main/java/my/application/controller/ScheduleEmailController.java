package my.application.controller;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import lombok.NoArgsConstructor;
import my.application.dto.ScheduledEmailDTO;
import my.application.entity.ScheduledEmail;
import my.application.service.EmailService;

@Path("/emails")
@NoArgsConstructor
public class ScheduleEmailController {
  EmailService emailService;

  @Inject
  public ScheduleEmailController(EmailService emailService) {
    this.emailService = emailService;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response list() {
    List<ScheduledEmail> allEmails = emailService.getAllEmails();
    allEmails.forEach(ScheduledEmail::createDefaultLink);
    return Response.ok(allEmails).build();
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response get(@PathParam("id") Long id) {
    ScheduledEmail email = emailService.getEmail(id);
    if (email == null) {
      return Response.status(Status.NOT_FOUND).build();
    }
    email.createDefaultLink();
    return Response.ok(email).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response create(@Valid ScheduledEmailDTO scheduledEmailDTO) {
    ScheduledEmail persistScheduledEmail = emailService.insert(scheduledEmailDTO.toEntity());
    return Response.status(201).entity(persistScheduledEmail).build();
  }

  @PUT
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response update(ScheduledEmail email) {
    emailService.update(email);
    email.createDefaultLink();
    return Response.status(Status.OK).entity(email).build();
  }

  @DELETE
  @Path("/{id}")
  public Response delete(@PathParam("id") Long id) {
    emailService.delete(id);
    return Response.status(Status.OK).build();
  }
}
