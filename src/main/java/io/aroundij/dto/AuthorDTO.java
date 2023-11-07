package io.aroundij.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class AuthorDTO {
    private long authorId;
    private String name;
    private Date birthdate;
    private String sport;

}
