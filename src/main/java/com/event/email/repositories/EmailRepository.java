package com.event.email.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.event.email.models.EmailModel;

@Repository
public interface EmailRepository extends JpaRepository<EmailModel, Long> {
}
