package com.pblgllgs.multijdbc.persistence.postgresql.entity;
/*
 *
 * @author pblgl
 * Created on 20-04-2024
 *
 */

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Entity
@Table(name = "logs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String message;
    private Instant date;
    private String tier;

    @PrePersist
    public void prePersist() {
        this.date = Instant.now();
    }
}
