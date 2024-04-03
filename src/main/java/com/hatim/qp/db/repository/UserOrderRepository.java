package com.hatim.qp.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hatim.qp.db.model.UserOrder;

@Repository
public interface UserOrderRepository extends JpaRepository<UserOrder, Integer> {
    UserOrder findByCartId(long id);

    @SuppressWarnings("unchecked")
    UserOrder save(UserOrder userCart);

    void deleteByCartId(long id);
}
