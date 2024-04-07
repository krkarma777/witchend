package com.witchend.domain.entity.dungeon;

import com.witchend.domain.entity.Monster;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "DungeonFloor")
public class DungeonFloor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 각 던전 층의 고유 ID

    @Column(nullable = false)
    private Integer floorNumber; // 던전 층 번호

    @Column(nullable = false, length = 255)
    private String description; // 층 설명

    @OneToMany(mappedBy = "dungeonFloor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Monster> monsters = new HashSet<>(); // 이 층에 존재하는 몬스터 목록

    @OneToMany(mappedBy = "dungeonFloor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Item> items = new HashSet<>(); // 이 층에 존재하는 아이템 목록

//    @OneToMany(mappedBy = "dungeonFloor", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Trap> traps = new HashSet<>(); // 이 층에 배치된 트랩 목록
//
//    @OneToMany(mappedBy = "dungeonFloor", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Event> events = new HashSet<>(); // 이 층에서 발생할 수 있는 이벤트 목록

    // 이 층의 레이아웃 정보를 나타낼 수 있는 필드 추가 (예: JSON, XML 형태로 저장 가능)
    @Lob
    @Column(nullable = true)
    private String layout; // 층 레이아웃 정보

    public DungeonFloor() {
    }

    public DungeonFloor(Integer floorNumber, String description) {
        this.floorNumber = floorNumber;
        this.description = description;
    }
}
