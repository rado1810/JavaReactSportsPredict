package bg.web.radoweb.service.Impl;

import bg.web.radoweb.exceptions.UsernameAlreadyExistsException;
import bg.web.radoweb.model.Dto.*;
import bg.web.radoweb.model.Enum.UserRoles;
import bg.web.radoweb.model.Role;
import bg.web.radoweb.model.User;
import bg.web.radoweb.repository.RolesRepository;
import bg.web.radoweb.repository.UserRepository;
import bg.web.radoweb.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RolesRepository rolesRepository;



    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, RolesRepository rolesRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepository = rolesRepository;

    }


    @Override
    public void registerUser(UserRegisterDTO userRegisterDTO) {
        User existingUser  = userRepository.findByUsername(userRegisterDTO.getUsername());
        if (existingUser  !=null){
          throw new UsernameAlreadyExistsException("Username already exists");
        }
        Role userRole = rolesRepository.findByName(UserRoles.ROLE_USER);
        if (userRole == null) {

            userRole = new Role();
            userRole.setName(UserRoles.ROLE_USER);
            rolesRepository.save(userRole);
        }
        User user = modelMapper.map(userRegisterDTO, User.class);
        user.setRole(Set.of(userRole));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);


    }

    @Override
    public boolean userExist(UserExistDto userExistDto) {
        User existingUser  = userRepository.findByUsername(userExistDto.getUsername());
        if (existingUser  !=null){
            throw new UsernameAlreadyExistsException("Username already exists");
        }else {
            return true;
        }
    }



    @Override
    public void logout(HttpServletRequest request) {
        Authentication auth1 = SecurityContextHolder.getContext().getAuthentication();


        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            SecurityContextHolder.clearContext();
        }
    }

}
