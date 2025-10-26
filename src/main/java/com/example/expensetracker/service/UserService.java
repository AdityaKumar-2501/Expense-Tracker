package com.example.expensetracker.service;

import com.example.expensetracker.dto.UserDTO;
import com.example.expensetracker.exception.InvalidRequestException;
import com.example.expensetracker.exception.NoDataException;
import com.example.expensetracker.exception.NotFoundException;
import com.example.expensetracker.exception.UserAlreadyExistsException;
import com.example.expensetracker.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUser() throws NoDataException;
    User create(UserDTO user) throws InvalidRequestException, UserAlreadyExistsException;
    User getUserById(Long userId) throws NotFoundException;
    User getUserByEmail(String email) throws NotFoundException;
    User updateUserById(Long userId, UserDTO user) throws NotFoundException, InvalidRequestException;
    void deleteUserById(Long userId) throws NotFoundException;
}
