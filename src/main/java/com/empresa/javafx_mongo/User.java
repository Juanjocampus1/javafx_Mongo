package com.empresa.javafx_mongo;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User {
    private String name;
    private String email;
    private String password;
}