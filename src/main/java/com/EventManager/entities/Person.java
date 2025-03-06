package com.EventManager.entities;

import com.EventManager.dto.PersonDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "people")
@EqualsAndHashCode(of = "id")

public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "full_name", nullable = false)
    @Size(min = 5, message = "Full name must be at least 5 characters long")
    private String fullName;

    @Column(name = "tag_name")
    private String tagName;

    @Column(name = "work_field")
    private String workField;

    @Column(name = "cpf")
    @Size(min = 11, message = "CPF must be at least 11 characters long")
    private String cpf;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    @Size(min = 10, message = "Phone number must be at least 10 characters long")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "postal_code")
    @Size(min = 8, message = "Postal code must be at least 8 characters long")
    private String postalCode;

    public Person(PersonDTO personDTO) {
        this.fullName = personDTO.fullName();
        this.tagName = personDTO.tagName();
        this.workField = personDTO.workField();
        this.cpf = personDTO.cpf();
        this.email = personDTO.email();
        this.phoneNumber = personDTO.phoneNumber();
        this.address = personDTO.address();
        this.postalCode = personDTO.postalCode();
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;

        this.tagName = generateTagName(fullName);
    }

    public void setTagName(String tagName) {
        this.tagName = tagName.toUpperCase();
    }

    @PrePersist
    @PreUpdate
    public void prePersistOrUpdate() {
        if (this.fullName != null && (this.tagName == null || this.tagName.isEmpty())) {
            this.tagName = generateTagName(this.fullName);
        }
    }

    private String generateTagName(String fullName) {
        String[] nameParts = fullName.trim().split("\\s+");

        if (nameParts.length < 2) {
            return fullName.toUpperCase();
        }

        String firstName = nameParts[0];
        String lastName = nameParts[nameParts.length - 1];
        return (firstName + " " + lastName).toUpperCase();
    }
}
