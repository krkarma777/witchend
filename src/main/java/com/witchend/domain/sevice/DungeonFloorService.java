package com.witchend.domain.sevice;

import com.witchend.domain.entity.dungeon.DungeonFloor;
import com.witchend.domain.repository.DungeonFloorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class DungeonFloorService {

    private final DungeonFloorRepository repository;

    public DungeonFloor save(DungeonFloor dungeonFloor) {
        return repository.save(dungeonFloor);
    }

    public DungeonFloor findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 던전의 정보가 존재하지 않습니다."));
    }

    public void delete(DungeonFloor dungeonFloor) {
        repository.delete(dungeonFloor);
    }
}
