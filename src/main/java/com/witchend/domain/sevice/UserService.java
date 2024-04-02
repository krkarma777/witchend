package com.witchend.domain.sevice;

import com.witchend.domain.entity.UserEntity;
import com.witchend.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void save(UserEntity user) {
        userRepository.save(user);
    }

    public void delete(UserEntity user) {
        userRepository.delete(user);
    }

    public Optional<UserEntity> findById(Long Id) {
        return userRepository.findById(Id);
    }
}
