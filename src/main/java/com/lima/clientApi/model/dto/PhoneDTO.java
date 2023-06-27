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

    @Override
    public String toString() {
        return "PhoneDTO{" +
                "id=" + id +
                ", ddd='" + ddd + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
