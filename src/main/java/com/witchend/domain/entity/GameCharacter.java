package com.witchend.domain.entity;

import com.witchend.domain.model.character.GameCharacterModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "GameCharacter")
public class GameCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Column(nullable = false)
    private Integer health;

    @Column(nullable = false)
    private Integer hunger;

    @Column(nullable = false)
    private Integer experience = 0;

    @Column(nullable = false)
    private Integer characterLevel = 1;

    @Column(nullable = false)
    private Integer strength;

    @Column(nullable = false)
    private Integer agility;

    @Column(nullable = false)
    private Integer dexterity;

    @Column(nullable = false)
    private Integer defense;

    @OneToMany(mappedBy = "gameCharacter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Equipment> equipments;

    public GameCharacter(GameCharacterModel model, User user) {
        this.health = model.getHealth();
        this.hunger = model.getHunger();
        this.strength = model.getStrength();
        this.agility = model.getAgility();
        this.dexterity = model.getDexterity();
        this.defense = model.getDefense();
        this.user = user;
    }

    public GameCharacter() {
    }
}
