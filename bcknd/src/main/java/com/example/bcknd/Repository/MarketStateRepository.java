package com.example.bcknd.Repository;

import com.example.bcknd.Entity.MarketState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MarketStateRepository extends JpaRepository<MarketState, UUID> {
    boolean existsByStock_Id(UUID stockId);
}
