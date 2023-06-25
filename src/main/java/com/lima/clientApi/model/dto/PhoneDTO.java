package com.lima.clientApi.model.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDTO {
    private Long id;
    private String ddd;
    private String number;
}
