package com.example.dao;

import com.example.annotations.MybatisDao;
import com.example.entity.Teacher;

import java.util.List;
import java.util.Map;

@MybatisDao
public interface TeacherDao {
    List<Teacher> findAllTeacher();
    Map<String,Object> getTeacherById(int id);
    List<Map<String,Object>> getTeacherByAge(int age);
}
