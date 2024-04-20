package com.pblgllgs.multijdbc.persistence.postgresql.repository;

import com.pblgllgs.multijdbc.persistence.postgresql.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, Integer> {
}
