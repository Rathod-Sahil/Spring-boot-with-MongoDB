package com.first.decorator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails implements Serializable {
    private String recipient;
    private String subject;
    private String content;

}
