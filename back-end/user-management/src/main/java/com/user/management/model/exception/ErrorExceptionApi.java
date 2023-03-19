package com.user.management.model.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorExceptionApi {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime localDateTime;
    private String message;

    public ErrorExceptionApi(HttpStatus status, String message) {
        localDateTime = LocalDateTime.now();
        this.status = status;
        this.message = message;
    }
}
