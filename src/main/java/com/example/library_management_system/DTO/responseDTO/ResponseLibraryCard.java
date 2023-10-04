package com.example.library_management_system.DTO.responseDTO;

import com.example.library_management_system.Enum.CardStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResponseLibraryCard {
    String cardNo;

    CardStatus cardStatus;

    Date issueDate;
}