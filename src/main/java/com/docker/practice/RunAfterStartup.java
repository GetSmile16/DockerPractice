package com.docker.practice;

import com.docker.practice.domain.User;
import com.docker.practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RunAfterStartup {
    public final UserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        List<User> users = new ArrayList<>();

        users.add(User.builder()
                .name("admin")
                .email("admin@mail.ru")
                .build()
        );

        users.add(User.builder()
                .name("vladislav")
                .email("custom@gmail.com")
                .build()
        );

        userRepository.saveAll(users);
    }
}