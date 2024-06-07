package com.github.testapp.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "master")
public class MasterEntity {
    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "document_id", nullable = false)
    private Long documentId;
    @Column(name = "date", nullable = false)
    private LocalDate date;
    @Column(name = "comment")
    private String comment;
    @Column(name = "sum_details", insertable = false, updatable = false)
    private BigDecimal sumDetails;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "master_id",referencedColumnName = "document_id")
    private List<DetailEntity> details;
}
