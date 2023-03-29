package com.meliksah.banka.app.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogMessage implements Serializable {

    private Long id;
    private String message;
    private String description;
    private Date dateTime;
}
