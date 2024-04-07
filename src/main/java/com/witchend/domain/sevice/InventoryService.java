package com.witchend.domain.sevice;

import com.witchend.domain.entity.user.GameCharacter;
import com.witchend.domain.entity.user.Inventory;
import com.witchend.domain.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository repository;

    public Inventory save(Inventory inventory) {
        return repository.save(inventory);
    }

    public Inventory findByGameCharacter(GameCharacter gameCharacter) {
        return repository.findByGameCharacter(gameCharacter);
    }
}
