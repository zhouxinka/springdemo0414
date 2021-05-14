package com.example.serviceImpl;

import com.example.dao.TeacherDao;
import com.example.entity.Teacher;
import com.example.service.TeacherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author zhoupeng
 * @create time 2021-04-14-15:10
 */
@Service
public class TeacherServiceImpl implements TeacherService {
    @Resource
    private TeacherDao teacherDao;

    public List<Teacher> findAllTeacher() {
        System.out.println("TeacherServiceImpl.findAllTeacher()...");
        return teacherDao.findAllTeacher();
    }

    public Map<String, Object> getTeacherById(int id) {
        System.out.println("TeacherServiceImpl.getTeacherById()...");
        return teacherDao.getTeacherById(id);
    }

    public List<Map<String, Object>> getTeacherByAge(int age) {
        System.out.println("TeacherServiceImpl.getTeacherByAge()...");
        return teacherDao.getTeacherByAge(age);
    }
}
