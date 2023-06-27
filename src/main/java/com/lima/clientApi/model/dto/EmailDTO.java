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


    @Override
    public String toString() {
        return "EmailDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}
