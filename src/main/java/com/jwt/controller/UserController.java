package com.jwt.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.entity.ERole;
import com.jwt.entity.Role;
import com.jwt.entity.User;
import com.jwt.helper.JwtUtil;
import com.jwt.repository.RoleRepository;
import com.jwt.repository.UserRepository;
import com.jwt.request.JwtRequest;
import com.jwt.service.UserService;
import com.jwt.services.CustomUserDetailService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/test")
    public String hello() {
        return "testing";
    }

    @PostMapping("/token")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        System.out.println(jwtRequest);
        try {
            System.out.println("inside try");
            final Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
            System.out.println("name from authenticate :" + authenticate.getCredentials());
        } catch (final UsernameNotFoundException e) {
            e.printStackTrace();
            System.out.println("************************");
            throw new Exception("User not found");
        } catch (final BadCredentialsException e) {
            e.printStackTrace();
            System.out.println("************************");
            throw new Exception("Bad Crdentials");
        }

        final UserDetails userDetails = customUserDetailService.loadUserByUsername(jwtRequest.getUsername());
        final String token = jwtUtil.generateToken(userDetails);
        System.out.println("generate token " + token);
        return ResponseEntity.ok(token);

    }

    @PostMapping("/add-user")
    public ResponseEntity<?> addUser(@RequestBody User userDetail) {
        final String pass = passwordEncoder.encode(userDetail.getPassword());
        userDetail.setPassword(pass);
        final User addUser = userService.addUser(userDetail);
        return ResponseEntity.ok("User Added successfully :" + addUser);

    }

    @GetMapping("/getUsers")
    public ResponseEntity<?> getUsers() {
        final List<User> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getRole")
    public ResponseEntity<?> getRoles() {
        final Iterable<Role> findAll = roleRepo.findAll();
        /*
         * findAll.forEach(role -> { System.out.println(role.toString()); });
         */
        /*
         * for (final Role role : findAll) { System.out.println(role);
         *
         * }
         */
        return ResponseEntity.ok(findAll);
    }

    @PostMapping("/test")
    public ResponseEntity<?> test() {
        final ArrayList<Role> roles = new ArrayList<Role>();
        final Role role = new Role();
        role.setRoleName(ERole.ADMIN);
        final Role role1 = new Role();
        role1.setRoleName(ERole.USER);
        roles.add(role);
        roles.add(role1);

        final User user = new User();
        user.setEmail("abc@gmail.com");
        user.setPassword("abc");
        user.setUsername("cvdvdv");
        user.setRoles(roles);
        role.setUser(user);

        role1.setUser(user);

        /*
         * role.setUserId(user.getId()); role1.setUserId(user.getId());
         */

        final User save = userRepo.save(user);
        /*
         * roleRepo.save(role); roleRepo.save(role1);
         */

        return ResponseEntity.ok(save);

    }

}
