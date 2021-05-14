package com.example.serviceImpl;

import com.example.dao.UserDao;
import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * @author zhoupeng
 * @create time 2021-04-14-15:10
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public void addUser(User user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User current_user = (User)session.getAttribute("CURRENT_USER");
        //插入前处理的方法
        user.preInsert(current_user);
        System.out.println("UserServiceImpl里面的addUser方法的user:"+user.toString());
        userDao.addUser(user);
    }

    @Override
    public void deleteUser(User user) {
        System.out.println("UserServiceImpl.deleteUser()...");
        userDao.deleteById(user);
    }
    @Override
    public List<User> findAllUser(User user) {
        return userDao.findAllUser(user);
    }
    @Override
    public User getUserById(Integer id) {
        return userDao.getUserById(id);
    }
    @Override
    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }
}
