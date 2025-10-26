package com.example.expensetracker.service.impl;

import com.example.expensetracker.dto.UserDTO;
import com.example.expensetracker.exception.InvalidRequestException;
import com.example.expensetracker.exception.NoDataException;
import com.example.expensetracker.exception.NotFoundException;
import com.example.expensetracker.exception.UserAlreadyExistsException;
import com.example.expensetracker.model.User;
import com.example.expensetracker.repository.UserRepository;
import com.example.expensetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    @Autowired
    UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUser() throws NoDataException {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()) throw new NoDataException("No User Present");
        return users;
    }

    @Override
    public User create(UserDTO user) throws InvalidRequestException, UserAlreadyExistsException {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User already exists with email " + user.getEmail());
        }

        if (user.getMonthlyBudget() <= 0) {
            throw new InvalidRequestException("Monthly Budget cannot be less than or equal to 0");
        }
        User newUser = convertFromDtoToEntity(user);
        return userRepository.save(newUser);
    }


    @Override
    public User getUserById(Long userId) throws NotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found with id " + userId));
    }


    @Override
    public User getUserByEmail(String email) throws NotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found with email " + email));
    }


    @Override
    public User updateUserById(Long userId, UserDTO user) throws NotFoundException, InvalidRequestException {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found with id " + userId));
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setFullName(user.getFullName());
        existingUser.setMonthlyBudget(user.getMonthlyBudget());
        return userRepository.save(existingUser);
    }


    @Override
    public void deleteUserById(Long userId) throws NotFoundException {
        userRepository.findById(userId).orElseThrow(() ->new NotFoundException("User not found with id " + userId));
        userRepository.deleteById(userId);
    }

    // ************* HELPER FUNCTION **************
    private User convertFromDtoToEntity(UserDTO user){
        User newUser = new User();
        newUser.setFullName(user.getFullName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setMonthlyBudget(user.getMonthlyBudget());
        return newUser;
    }
}
