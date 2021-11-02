package com.micaelps.dogs.dogs;

import com.micaelps.dogs.security.LoggedUser;
import com.micaelps.dogs.user.UserSystem;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewDogController {

    @GetMapping(path = "/dogs")
    public ResponseEntity<UserSystem> get(@AuthenticationPrincipal LoggedUser loggedUser){
        return ResponseEntity.ok(loggedUser.get());
    }
}
