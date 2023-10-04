package com.example.library_management_system.DTO.responseDTO;

import com.example.library_management_system.Enum.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResponseStudentWhenAdded{
    String name;

    int age;

    Gender gender;

    ResponseLibraryCard responseLibraryCard;
}
