package com.witchend.domain.sevice;

import com.witchend.domain.entity.GameCharacter;
import com.witchend.domain.entity.User;
import com.witchend.domain.repository.GameCharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameCharacterService {

    private final GameCharacterRepository repository;

    public void save(GameCharacter gameCharacter) {
        repository.save(gameCharacter);
    }

    public void delete(GameCharacter gameCharacter) {
        repository.delete(gameCharacter);
    }
    public List<GameCharacter> findByUser(User user) {
        return repository.findByUser(user);
    }
}
