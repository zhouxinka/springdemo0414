package com.example.service;

import com.example.entity.Page;
import com.example.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {
    void addUser(User user, HttpServletRequest request);
    void deleteUser(User user);
    List<User> findAllUser(User user);
    User getUserById(Integer id);
    User getUserByName(String name);
    Page<User> findPage(Page<User> page, User user);
}
