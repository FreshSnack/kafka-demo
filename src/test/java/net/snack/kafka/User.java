package net.snack.kafka;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class User implements Serializable {

    public static final String USER_FLAG = "chat";

    private static final long serialVersionUID = -6674548108361939077L;

    private String name;

    private Integer age;

    private LocalDate birth;

    private LocalDateTime birthTime;

    private Date entryDate;

    private Double salary;
}
