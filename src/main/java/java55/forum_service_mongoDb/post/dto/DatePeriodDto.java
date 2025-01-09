package java55.forum_service_mongoDb.post.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DatePeriodDto {
    LocalDate dateFrom;
    LocalDate dateTo;
}
