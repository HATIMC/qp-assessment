package com.hatim.qp.db.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hatim.qp.db.model.ApiKey;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Integer> {
    // You can define custom query methods here if needed
}
