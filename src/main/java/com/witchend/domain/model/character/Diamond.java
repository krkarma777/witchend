package com.witchend.domain.model.character;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Diamond implements GameCharacterModel {

    private final Integer health = 60;

    private final Integer strength = 12;

    private final Integer agility = 4;

    private final Integer dexterity = 3;

    private final Integer hunger = 15;

    private final Integer defense = 0;
}
