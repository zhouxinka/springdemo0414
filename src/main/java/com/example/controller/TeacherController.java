package com.example.controller;

import com.example.entity.Teacher;
import com.example.serviceImpl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

/**
 * @author zhoupeng
 * @create time 2021-05-07-16:23
 */
@Controller
@RequestMapping("${adminPath}")
//@Scope("prototype")
public class TeacherController extends BaseController {
    @Autowired
    private TeacherServiceImpl teacherServiceImpl;

    @RequestMapping(value="/teacherInfo")
    public String teacherInfo(Model model){
        Map<String, Object> map = teacherServiceImpl.getTeacherById(1);
        System.out.println(map.toString());
        System.out.println(map.size());
        System.out.println("===================================");
        List<Map<String, Object>> teacherList = teacherServiceImpl.getTeacherByAge(30);
        for (Map<String, Object> stringObjectMap : teacherList) {
            System.out.println("stringObjectMap:"+stringObjectMap.toString());
        }
        System.out.println("teacherInfo...");
        List<Teacher> list = teacherServiceImpl.findAllTeacher();
        for (Teacher Teacher : list) {
            System.out.println(Teacher.toString());
        }
        model.addAttribute("list",list);
        return "teacherInfo";
    }

    /**
     * @ModelAttribute修是的方法会先于@RequestMapping修饰的方法执行，并且拿到请求里面的参数
     * @param model
     * @param string
     */
    @ModelAttribute
    public void testModelAttribute1(Model model,String string){
        model.addAttribute("test","it is a test...");
        System.out.println("string=========>:"+string);
    }
    @RequestMapping(value="/test")
    public void testModelAttribute2(Model model,RedirectAttributes redirectAttributes){
        String test = (String)model.getAttribute("test");
        System.out.println("model.getAttribute(\"test\"):"+test);
    }
}
