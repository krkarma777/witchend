package com.witchend.domain.model.character;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Jade implements GameCharacterModel{

    private final Integer health = 45;

    private final Integer strength = 9;

    private final Integer agility = 8;

    private final Integer dexterity = 5;

    private final Integer hunger = 20;

    private final Integer defense = 0;
}
