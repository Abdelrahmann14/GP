package com.spring.boot.rest.service.user.impl;
import com.spring.boot.rest.dto.user.UserSignUpDto;
import com.spring.boot.rest.exceptions.customExceptions.ResourceFoundException;
import com.spring.boot.rest.mapper.user.UserMapper;
import com.spring.boot.rest.model.user.Role;
import com.spring.boot.rest.model.user.RoleEnum;
import com.spring.boot.rest.model.user.User;
import com.spring.boot.rest.model.user.UserDetails;
import com.spring.boot.rest.repo.user.RoleRepo;
import com.spring.boot.rest.repo.user.UserDetailsRepo;
import com.spring.boot.rest.repo.user.UserRepo;
import com.spring.boot.rest.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
@Autowired
private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
@Autowired
private @Lazy PasswordEncoder passwordEncoder;
@Autowired
private UserDetailsRepo userDetailsRepo;
    @Override
    public UserSignUpDto getUserByName(String userName) throws ResourceFoundException {
        return userRepo.findByUsername(userName)
                .map(UserMapper.userMapper::toUserDto)
                .orElseThrow(() -> new ResourceFoundException("User.NotExists"));
    }


    @Override
    public boolean addUser(UserSignUpDto userSignUpDto) throws ResourceFoundException {
        if (userRepo.findByUsername(userSignUpDto.getUsername()).isPresent()) {
            throw new ResourceFoundException("User.Exists");
        }
        User newUser = UserMapper.userMapper.toUser(userSignUpDto);
        Role userRole = roleRepo.findByRoleName(RoleEnum.USER.name());
        newUser.setRoles(List.of(userRole));
        newUser.setPassword(passwordEncoder.encode(userSignUpDto.getPassword()));
        UserDetails userDetails = newUser.getUserDetails();
        if (userDetails != null) {
            userDetails.setUser(newUser);
        }
        userRepo.save(newUser);
        return true;
    }

}
