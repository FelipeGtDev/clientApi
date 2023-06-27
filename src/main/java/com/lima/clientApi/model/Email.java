package com.lima.clientApi.model;

import com.lima.clientApi.model.dto.EmailDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@Setter
@Getter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_seq")
    @SequenceGenerator(name = "email_seq", sequenceName = "email_seq", allocationSize = 1)
    private Long id;
    private String email;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Client client;


    public Email(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public Email(EmailDTO emailDTO, Client client) {
        this.id = emailDTO.getId();
        this.email = emailDTO.getEmail();
        this.client = client;
    }
}
