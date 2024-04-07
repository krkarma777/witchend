package com.witchend.domain.repository;

import com.witchend.domain.entity.dungeon.DungeonFloor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DungeonFloorRepository extends JpaRepository<DungeonFloor, Long> {
}
