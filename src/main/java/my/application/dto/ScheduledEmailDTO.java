package my.application.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;
import my.application.entity.ScheduledEmail;

@Data
@NoArgsConstructor
public class ScheduledEmailDTO {
  @NotEmpty
  @Email
  private String email;

  public ScheduledEmail toEntity() {
    return new ScheduledEmail(email, false);
  }
}
