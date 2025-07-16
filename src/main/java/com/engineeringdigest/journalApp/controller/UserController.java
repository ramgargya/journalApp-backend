package com.engineeringdigest.journalApp.controller;


import com.engineeringdigest.journalApp.entity.User;
import com.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;



//    @GetMapping   // this controller will only for admin because only admin can see all the users
//    public List<User> getAllUsers() {
//        return userService.getAll();
//    }


     // UpdateUser with authentication
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User userInDb = userService.findByUsername(userName);

        if(userInDb != null) {
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveNewUser(userInDb);
        }
        return new ResponseEntity<>("Password or UserName changed",HttpStatus.NO_CONTENT);
    }

    // UpdateUser without authentication
//    @PutMapping("/{userName}")
//    public ResponseEntity<?> updateUser(@RequestBody User user , @PathVariable String userName) {
//        User userInDb = userService.findByUsername(userName);
//        if(userInDb != null) {
//            userInDb.setUserName(user.getUserName());
//            userInDb.setPassword(user.getPassword());
//            userService.saveEntry(userInDb);
//        }
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }



    @DeleteMapping
    public ResponseEntity<?> DeleteUserByName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        userService.deleteByUserName(userName);

        return ResponseEntity.ok("User Deleted Successfully");
    }

    @GetMapping
    public ResponseEntity<?>  greetngs() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>("Hi" + authentication.getName(), HttpStatus.OK);
    }

}
