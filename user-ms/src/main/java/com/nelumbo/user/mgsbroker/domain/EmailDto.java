package com.nelumbo.user.mgsbroker.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {

    private String id;
    private String emailFrom;
    private String emailTo;
    private String subject;
    private String text;
    private String status;

}