package com.guinchae.guinchae.model;

import com.guinchae.guinchae.model.type.RequestStatusType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "_request")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class RequestModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "request_sequence")
    @SequenceGenerator(name = "request_sequence", sequenceName = "request_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserModel client;
    @ManyToOne(fetch = FetchType.LAZY)
    private TowTruckDriverModel towTruckDriver;
    @Enumerated(EnumType.STRING)
    private RequestStatusType status;

    // TO DO → Sistema de localização utilizando GoogleMapsAPI
    private String pickupAddress;
    @Size(max = 300)
    private String message;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;
}