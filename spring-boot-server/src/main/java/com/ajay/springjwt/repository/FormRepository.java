package com.ajay.springjwt.repository;

import com.ajay.springjwt.models.ERole;
import com.ajay.springjwt.models.FormDocument;
import com.ajay.springjwt.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormRepository extends JpaRepository<FormDocument,Integer> {

    List<FormDocument> findByUserId(Integer userId);
}
