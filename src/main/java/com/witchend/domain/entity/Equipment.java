package com.witchend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Equipment")
@Getter
@Setter
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "characterId")
    private GameCharacter gameCharacter;

    @Column(nullable = false)
    private String type; // 예: "Weapon", "Clothing", "Shoe", "Accessory" enum 추가 필요

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer strengthRequirement;

    @Column(nullable = false)
    private Integer attackValue;

    @Column(nullable = false)
    private Integer defenseValue;

    @Column(nullable = false)
    private Integer agilityBoost;
}