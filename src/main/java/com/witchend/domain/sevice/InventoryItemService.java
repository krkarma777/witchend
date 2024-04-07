package com.witchend.domain.sevice;

import com.witchend.domain.entity.user.InventoryItem;
import com.witchend.domain.repository.InventoryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class InventoryItemService {

    private final InventoryItemRepository repository;

    public InventoryItem save(InventoryItem inventoryItem) {
        return repository.save(inventoryItem);
    }

    public InventoryItem findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "인벤토리에 해당 아이템이 존재하지 않습니다."));
    }

    public void delete(InventoryItem inventoryItem) {
        repository.delete(inventoryItem);
    }
}
