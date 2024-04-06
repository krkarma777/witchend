package com.witchend.domain.sevice.user;

import com.witchend.domain.dto.user.UserCreateRequestDTO;
import com.witchend.domain.dto.user.UserUpdateRequestDTO;
import com.witchend.domain.entity.UserEntity;
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

    /**
     * Method to handle user registration process. It validates the request data,
     * encrypts the password, and saves the new user entity.
     *
     * @param requestDTO DTO containing user creation request data
     */
    @Transactional
    public void registerProcess(UserCreateRequestDTO requestDTO) {
        // Initialize a new user entity from the request data
        UserEntity newUser = new UserEntity(requestDTO);

        // Validate user registration details
        userValidator.registerCheck(newUser);

        // Encrypt the user's password for security
        String hashedPassword = bCryptPasswordEncoder.encode(newUser.getPassword());
        newUser.setPassword(hashedPassword);

        // Save the new user entity to the database
        userService.save(newUser);
    }

    /**
     * Method to handle user information update requests. It validates the request data,
     * updates the current user's information, and saves the updated user entity.
     *
     * @param requestDTO DTO containing user update request data
     * @param principal Security principal of the currently authenticated user
     */
    public void updateProcess(UserUpdateRequestDTO requestDTO, Principal principal) {
        // Retrieve the current user entity based on the security principal
        UserEntity currentUser = userAuthValidator.getCurrentUser(principal);

        // Validate user update details
        userValidator.updateCheck(requestDTO, currentUser);

        // Update the current user entity with the new details
        currentUser.update(requestDTO);

        // Save the updated user entity to the database
        userService.save(currentUser);
    }

    /**
     * Method to handle user deletion requests. It validates the request and checks
     * if the requester has permission to delete the user, then deletes the user entity.
     *
     * @param id        ID of the user to be deleted
     * @param principal Security principal of the currently authenticated user
     */
    public void deleteProcess(Long id, Principal principal) {
        // Retrieve the user entity to be deleted by ID
        UserEntity userById = userAuthValidator.getCurrentUserById(id);

        // Retrieve the current user entity based on the security principal
        UserEntity currentUser = userAuthValidator.getCurrentUser(principal);

        // Validate user deletion request
        userValidator.deleteCheck(userById, currentUser);

        // Delete the user entity from the database
        userService.delete(currentUser);
    }
}
