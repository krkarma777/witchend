package com.witchend.domain.entity.dungeon;

import com.witchend.domain.enums.item.Effect;
import com.witchend.domain.enums.item.ItemType;
import com.witchend.domain.enums.item.Rarity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 아이템의 고유 ID

    @Column(nullable = false, length = 50)
    private String name; // 아이템 이름

    @Column(nullable = false, length = 255)
    private String description; // 아이템 설명

    @Column(nullable = false)
    private ItemType type; // 아이템 유형

    @Column(nullable = false)
    private int quantity; // 아이템 수량 또는 사용 가능 횟수

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rarity rarity; // 아이템 희귀도

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Effect effect; // 아이템 사용 효과

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dungeonFloorId")
    private DungeonFloor dungeonFloor;
}
