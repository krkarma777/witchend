package com.witchend.domain.generator;

import com.witchend.domain.entity.user.GameCharacter;
import com.witchend.domain.entity.user.Inventory;
import com.witchend.domain.entity.user.User;
import com.witchend.domain.enums.CharacterClass;
import com.witchend.domain.model.character.Diamond;
import com.witchend.domain.model.character.Jade;
import com.witchend.domain.sevice.GameCharacterService;
import com.witchend.domain.sevice.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class GameCharacterGenerator {

    private final GameCharacterService gameCharacterService;
    private final InventoryService inventoryService;

    public void generate(CharacterClass characterClass, User user) {
        GameCharacter gameCharacter = getGameCharacter(characterClass, user);
        GameCharacter savedGameCharacter = gameCharacterService.save(gameCharacter);

        Inventory inventory = new Inventory(savedGameCharacter);
        inventoryService.save(inventory);
    }

    private GameCharacter getGameCharacter(CharacterClass characterClass, User user) {
        return switch (characterClass) {
            case DIAMOND -> new GameCharacter(new Diamond(), user);
            case JADE -> new GameCharacter(new Jade(), user);
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.");
        };
    }
}
