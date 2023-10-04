package com.example.library_management_system.DTO.requestDTO;

import com.example.library_management_system.Enum.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestStudent {
    String name;

    int age;

    String email;

    Gender gender;
}
