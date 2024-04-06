package com.witchend.domain.generator;

import com.witchend.domain.entity.GameCharacter;
import com.witchend.domain.entity.User;
import com.witchend.domain.enums.CharacterClass;
import com.witchend.domain.model.character.Diamond;
import com.witchend.domain.model.character.Jade;
import com.witchend.domain.sevice.GameCharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class GameCharacterGenerator {

    private final GameCharacterService gameCharacterService;

    public void generate(CharacterClass characterClass, User user) {
        GameCharacter gameCharacter = getGameCharacter(characterClass, user);
        gameCharacterService.save(gameCharacter);
    }

    private GameCharacter getGameCharacter(CharacterClass characterClass, User user) {
        GameCharacter gameCharacter;
        switch (characterClass) {
            case DIAMOND:
                Diamond diamond = new Diamond();
                System.out.println("diamond = " + diamond);
                gameCharacter = new GameCharacter(new Diamond(), user);
                break;
            case JADE:
                gameCharacter = new GameCharacter(new Jade(), user);
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.");
        }
        return gameCharacter;
    }
}
