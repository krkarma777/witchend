package com.witchend.domain.sevice.user;

import com.witchend.domain.dto.user.UserCreateRequestDTO;
import com.witchend.domain.dto.user.UserUpdateRequestDTO;
import com.witchend.domain.entity.user.User;
import com.witchend.domain.enums.CharacterClass;
import com.witchend.domain.generator.GameCharacterGenerator;
import com.witchend.domain.validator.UserValidator;
import com.witchend.domain.validator.user.UserAuthValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserRequestProcessService {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserAuthValidator userAuthValidator;
    private final UserValidator userValidator;
    private final GameCharacterGenerator gameCharacterGenerator;


    @Transactional
    public void registerProcess(UserCreateRequestDTO requestDTO) {
        userValidator.registerCheck(requestDTO);

        User newUser = new User(requestDTO);
        String hashedPassword = bCryptPasswordEncoder.encode(newUser.getPassword());
        newUser.setPassword(hashedPassword);

        User savedUser = userService.save(newUser);

        gameCharacterGenerator.generate(CharacterClass.DIAMOND, savedUser);
    }

    public void updateProcess(UserUpdateRequestDTO requestDTO, Principal principal) {
        User currentUser = userAuthValidator.getCurrentUser(principal);

        userValidator.updateCheck(requestDTO, currentUser);

        currentUser.update(requestDTO);

        userService.save(currentUser);
    }

    public void deleteProcess(Long id, Principal principal) {
        User userById = userAuthValidator.getCurrentUserById(id);

        User currentUser = userAuthValidator.getCurrentUser(principal);

        userValidator.deleteCheck(userById, currentUser);

        userService.delete(currentUser);
    }
}
