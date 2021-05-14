package com.example.service;

import com.example.entity.Teacher;

import java.util.List;
import java.util.Map;

public interface TeacherService {
    List<Teacher> findAllTeacher();
    Map<String,Object> getTeacherById(int id);
    List<Map<String,Object>> getTeacherByAge(int age);
}
