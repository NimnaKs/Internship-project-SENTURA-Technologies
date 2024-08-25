package lk.ijse.weavyapiintegration.controller;

import jakarta.validation.Valid;
import lk.ijse.weavyapiintegration.dto.UserDTO;
import lk.ijse.weavyapiintegration.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@Valid @RequestBody UserDTO userDTO) {

        UserDTO savedUser = userService.saveUser(userDTO);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String userId,
                                           @RequestParam(required = false) Boolean trashed) {
        UserDTO user = userService.getUser(userId, trashed);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
