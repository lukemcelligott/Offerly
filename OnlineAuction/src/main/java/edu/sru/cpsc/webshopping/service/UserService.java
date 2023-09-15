package edu.sru.cpsc.webshopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sru.cpsc.webshopping.domain.user.SellerRating;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User updateUserProfile(Long userId, User user) {
        User userToUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "User with id " + userId + " does not exist"
                ));
        return userRepository.save(userToUpdate);
    }

    //Add user
    public User addUser(User user) {
        return userRepository.save(user);
    }

    //Delete user
    public void deleteUser(Long userId) {
        boolean exists = userRepository.existsById(userId);
        if (!exists) {
            throw new IllegalStateException("User with id " + userId + " does not exist");
        }
        userRepository.deleteById(userId);
    }

    //Get user by id
    public User getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "User with id " + userId + " does not exist"
                ));
        return user;
    }

    //Rate user
    public User rateUser(Long userId, float rating) {
        User userToRate = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "User with id " + userId + " does not exist"
                ));
        //set rating with new incoming rating from a user. This will update the average rating.
        userToRate.getSellerRating().setRating(rating);
        return userRepository.save(userToRate);
    }
}