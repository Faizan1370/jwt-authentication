package com.jwt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwt.entity.User;
import com.jwt.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /*
     * @Autowired private RoleRepository roleRepo;
     */

    @Override
    public User addUser(User userDetail) {
        // User user = new User(userDetail);
        /*
         * // if (userDetail.getRoles().contains(ERole.ADMIN)) { final List<Role> roles = userDetail.getRoles(); // userDetail.setRoles(roles); for (final Role role : roles) { role.setUser(userDetail); }
         */
        final User user = userRepository.save(userDetail);
        /*
         * for (String role : userDetail.getRoles()) {
         *
         * role = new Role(); role.setRoleName(user.getRoles().eg); role.setUser(user); roleRepo.save(role);
         *
         * }
         */

        return user;
    }

    @Override
    public List<User> getUsers() {
        final Iterable<User> users = userRepository.findAll();
        return (List<User>) users;
    }

}
