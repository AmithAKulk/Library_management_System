package com.example.library_management_system.DTO.responseDTO;

import com.example.library_management_system.Enum.Genre;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.aspectj.weaver.GeneratedReferenceTypeDelegate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResponseBook_AuthorAndGenre {
    String title;

    String authorName;

    Genre genre;
}
