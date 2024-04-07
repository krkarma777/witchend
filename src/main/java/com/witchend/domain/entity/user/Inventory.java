package com.witchend.domain.entity.user;

import com.witchend.domain.entity.dungeon.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "characterId", unique = true)
    private GameCharacter gameCharacter;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InventoryItem> inventoryItems = new ArrayList<>();

    public Inventory() {
    }

    public Inventory(GameCharacter gameCharacter) {
        this.gameCharacter = gameCharacter;
    }

    // 아이템 추가 메소드
    public void addItem(Item item, int quantity) {
        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setItem(item);
        inventoryItem.setInventory(this);
        inventoryItem.setQuantity(quantity);
        this.inventoryItems.add(inventoryItem);
    }

    // 아이템 삭제 메소드
    public void removeItem(InventoryItem inventoryItem) {
        this.inventoryItems.remove(inventoryItem);
    }

    // 인벤토리 내 아이템 조회 메소드
    public List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        for (InventoryItem inventoryItem : this.inventoryItems) {
            items.add(inventoryItem.getItem());
        }
        return items;
    }
}
