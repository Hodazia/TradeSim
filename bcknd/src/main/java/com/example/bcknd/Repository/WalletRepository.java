package com.example.bcknd.Repository;

import com.example.bcknd.Entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {

    boolean existsByUser_Id(UUID userid);
}
