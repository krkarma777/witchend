package com.witchend.domain.sevice;

import com.witchend.domain.entity.dungeon.Item;
import com.witchend.domain.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository repository;

    public Item save(Item item) {
        return repository.save(item);
    }

    public Item findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 아이템입니다."));
    }

    public void delete(Item item) {
        repository.delete(item);
    }
}
