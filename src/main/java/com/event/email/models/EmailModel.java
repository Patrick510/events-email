package com.event.email.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.event.email.enums.StatusEmail;

@Data
@Entity
@Table(name = "TB_EMAIL")
@AllArgsConstructor
@NoArgsConstructor
public class EmailModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long emailId;
    @Column(nullable = false, length = 100)
    private String ownerRef;
    @Column(nullable = false, length = 100)
    private String emailFrom;
    @Column(nullable = false, length = 100)
    private String emailTo;
    @Column(nullable = false, length = 50)
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String body;
    @Column(nullable = false)
    private LocalDateTime sendDataEmail;
    @Column(nullable = false)
    private StatusEmail statusEmail;

}
