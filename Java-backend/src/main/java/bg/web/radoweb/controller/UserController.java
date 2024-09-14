package bg.web.radoweb.controller;

import bg.web.radoweb.config.JwtTokenUtil;
import bg.web.radoweb.exceptions.UsernameAlreadyExistsException;
import bg.web.radoweb.model.Dto.UserExistDto;
import bg.web.radoweb.model.Dto.UserLoginDTO;
import bg.web.radoweb.model.Dto.UserRegisterDTO;
import bg.web.radoweb.model.Dto.UserResponseDTO;
import bg.web.radoweb.service.Impl.CustomUserDetailsService;
import bg.web.radoweb.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    private final CustomUserDetailsService customUserDetailsService;
   private final JwtTokenUtil jwtTokenUtil;



    public UserController(UserService userService, CustomUserDetailsService customUserDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;

        this.customUserDetailsService = customUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;

    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        try {
            userService.registerUser(userRegisterDTO);
            return ResponseEntity.ok("User registered successfully");
        } catch (UsernameAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    @PostMapping("/userExist")
    public ResponseEntity<String> userExist(@RequestBody UserExistDto userExistDto) {
        try {
            userService.userExist(userExistDto);
            return ResponseEntity.ok("User");
        } catch (UsernameAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO) {

           try { UserDetails userDetails = customUserDetailsService.loadUserByUsername(userLoginDTO.getUsername());
            final String token = jwtTokenUtil.generateToken(userDetails);

            UserResponseDTO userResponse = new UserResponseDTO();
            userResponse.setUsername(userDetails.getUsername());
            userResponse.setRoles(userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet()));
            userResponse.setToken(token);

            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        userService.logout(request);
        return ResponseEntity.ok("Logout successful");
    }
}
