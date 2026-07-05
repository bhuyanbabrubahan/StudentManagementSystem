package com.example.student.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass  //It tells hibernate please add these fields to child class and dont create aseparate table for me
@Getter
@Setter
@NoArgsConstructor
public abstract class BaseEntity {

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    public void onCreate() {

        this.createdAt = LocalDateTime.now();

        this.updatedAt = LocalDateTime.now();

    }

    @PreUpdate
    public void onUpdate() {

        this.updatedAt = LocalDateTime.now();

    }
	
}
