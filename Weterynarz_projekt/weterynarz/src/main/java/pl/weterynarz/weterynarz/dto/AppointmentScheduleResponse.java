package pl.weterynarz.weterynarz.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentScheduleResponse {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String vetLogin;
}
