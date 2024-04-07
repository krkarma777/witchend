package com.witchend.domain.repository;

import com.witchend.domain.entity.user.GameCharacter;
import com.witchend.domain.entity.user.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Inventory findByGameCharacter(GameCharacter gameCharacter);
}
