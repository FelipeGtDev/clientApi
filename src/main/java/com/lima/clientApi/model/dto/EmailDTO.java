package com.lima.clientApi.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO {
    private Long id;
    private String email;

}
