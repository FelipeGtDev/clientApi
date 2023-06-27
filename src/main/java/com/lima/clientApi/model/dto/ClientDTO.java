package com.lima.clientApi.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClientDTO {

    private Long id;
    private String name;
    private String cpf;
    @JsonInclude(Include.NON_NULL)
    private List<PhoneDTO> phones;
    @JsonInclude(Include.NON_NULL)
    private List<EmailDTO> emails;

}
