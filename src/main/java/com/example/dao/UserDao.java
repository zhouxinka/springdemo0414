package com.example.dao;

import com.example.annotations.MybatisDao;
import com.example.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MybatisDao
public interface UserDao extends CrudDao<User>{
    void addUser(User user);

    List<User> findAllUser(User user);

    void deleteById(User user);

    User insertUser(User user);

    User getUserByName(String name);



    /**
     * @param id
     * @return
     * @Param的作用是使参数与mapper.xml里面的参数一致
     */
    User getUserById(@Param("i") Integer id);
}
