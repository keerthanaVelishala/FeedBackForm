package com.lavanya.springjwt.repository;

import com.lavanya.springjwt.models.ERole;
import com.lavanya.springjwt.models.FormDocument;
import com.lavanya.springjwt.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormRepository extends JpaRepository<FormDocument,Integer> {

    List<FormDocument> findByUserId(Integer userId);
}
