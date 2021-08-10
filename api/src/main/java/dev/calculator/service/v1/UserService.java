package dev.calculator.service.v1;

import dev.calculator.controller.v1.error.ConflictException;
import dev.calculator.model.Role;
import dev.calculator.model.Status;
import dev.calculator.model.User;
import dev.calculator.model.v1.network.PageResponse;
import dev.calculator.model.v1.network.UserModifyRequest;
import dev.calculator.model.v1.network.UserResponse;
import dev.calculator.repository.UserRepository;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Builder
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    public void signUp(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new ConflictException("Username not available");
        }

        String password = bCryptPasswordEncoder.encode(user.getPassword());
        user.setUuid(UUID.randomUUID());
        user.setPassword(password);
        user.setRole(Role.ROLE_USER);
        user.setStatus(Status.ACTIVE);

        // TODO this might be from a configuration. ?
        user.setUserBalance(100.0);

        userRepository.save(user);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean canExecuteService(User user, dev.calculator.model.Service service) {
        return (user.getUserBalance() - service.getCost()) >= 0.0;
    }

    public void removeBalance(User user, double cost) {
        Double userBalance = user.getUserBalance();
        user.setUserBalance(userBalance - cost);

        userRepository.save(user);
    }

    public UserResponse createUserResponse(User user) {
        return UserResponse.fromUser(user);
    }

    public PageResponse getUsersByUsername(String username, PageRequest pageRequest) {
        Page<User> users;
        if (ObjectUtils.isEmpty(username)) {
            users = userRepository.findAll(pageRequest);
        } else {
            users = userRepository.findByUsernameContaining(username, pageRequest);
        }

        List<Object> result = users.get()
                .map(UserResponse::fromUser)
                .collect(Collectors.toList());

        return PageResponse.builder()
                .totalPages(users.getTotalPages())
                .pageSize(result.size())
                .currentPage(pageRequest.getPageNumber())
                .result(result)
                .build();
    }

    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }

    public UserResponse modifyUser(UserModifyRequest userModifyRequest) {
        User user;
        if (userModifyRequest.getId() > 0) {
            user = userRepository.findById(userModifyRequest.getId()).orElseThrow();
            user.setUsername(userModifyRequest.getUsername());
            user.setUuid(UUID.fromString(userModifyRequest.getUuid()));
            user.setRole(Role.valueOf(userModifyRequest.getRole()));
            user.setStatus(userModifyRequest.getStatus());
            user.setUserBalance(userModifyRequest.getUserBalance());
        } else {
            user = User.builder()
                    .username(userModifyRequest.getUsername())
                    .password(bCryptPasswordEncoder.encode(userModifyRequest.getPassword()))
                    .uuid(UUID.fromString(userModifyRequest.getUuid()))
                    .role(Role.valueOf(userModifyRequest.getRole()))
                    .status(userModifyRequest.getStatus())
                    .userBalance(userModifyRequest.getUserBalance())
                    .build();
        }

        user = userRepository.save(user);

        return UserResponse.fromUser(user);
    }
}
