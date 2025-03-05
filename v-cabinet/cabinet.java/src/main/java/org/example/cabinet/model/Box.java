package org.example.cabinet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Box {
    private Long id;
    private String name;
    private String size;
    private String code;
    private LocalDateTime expireTime;
}
