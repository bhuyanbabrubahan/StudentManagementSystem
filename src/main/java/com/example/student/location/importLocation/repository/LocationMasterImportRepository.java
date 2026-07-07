package com.example.student.location.importLocation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.student.location.importLocation.entity.LocationMasterImport;

@Repository
public interface LocationMasterImportRepository
        extends JpaRepository<LocationMasterImport, Long> {

}