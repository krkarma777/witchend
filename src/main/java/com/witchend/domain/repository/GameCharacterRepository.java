package com.witchend.domain.repository;

import com.witchend.domain.entity.user.GameCharacter;
import com.witchend.domain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameCharacterRepository extends JpaRepository<GameCharacter, Long> {
    List<GameCharacter> findByUser(User user);
}
