package com.lima.clientApi.model;

import com.lima.clientApi.model.dto.PhoneDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_seq")
    @SequenceGenerator(name = "phone_seq", sequenceName = "phone_seq", allocationSize = 1)
    private Long id;
    private String ddd;
    private String number;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Client client;

    public Phone(PhoneDTO phoneDTO, Client client) {
        this.id = phoneDTO.getId();
        this.ddd = phoneDTO.getDdd();
        this.number = phoneDTO.getNumber();
        this.client = client;
    }
}
