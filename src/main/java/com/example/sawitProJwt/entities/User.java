package com.example.sawitProJwt.entities;

import com.example.sawitProJwt.dto.constants.TableName;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TableName.USER)
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_user", nullable = false)
    private Long id;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Builder
    public User(Date createdDate, Date updatedDate, Long id, String phoneNumber, String name, String password) {
        super(createdDate, updatedDate);
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.password = password;
    }
}
