package com.xu.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlphaController {
    @ResponseBody
    @RequestMapping("/hello")
    public String HelloSpringBoot(){
        return "Hello Spring Boot";
    }
    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response){
        //获取请求数据
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String s = headerNames.nextElement();
            String value = request.getHeader(s);
            System.out.println(s + ": "+ value );
        }
        System.out.println(request.getParameter("code"));
        //返回响应数据
        response.setContentType("text/html;charset=utf-8");
        try(PrintWriter writer = response.getWriter();) {
            writer.write("<h1>安旗</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //Get请求
    @ResponseBody
    @RequestMapping(path = "/students",method = RequestMethod.GET)
    public String getStudents(@RequestParam(value = "current",required = false,defaultValue="1") int current,@RequestParam(value = "limit",required = false,defaultValue="20 ") int limit){
        return "some student"+current+" "+limit;
    }
    //RestFul风格
    @GetMapping(path = "/student/{id}")
    @ResponseBody
    public String getStudent(@PathVariable("id") int id){
        System.out.println(id);
        return "a student";
    }
    //Post请求
    @PostMapping(path ="/student")
    @ResponseBody
    public String saveStudent(String name, int age){
        System.out.println(name+age);
        return "success";
    }
    //响应HTML数据
    @RequestMapping(path="/teacher",method = RequestMethod.GET)
    public ModelAndView getTeacher(String name){
        ModelAndView mav = new ModelAndView();
        mav.addObject("name","张三");
        mav.addObject("age",30);
        mav.setViewName("/demo/view");
        return mav;
    }
    @GetMapping("/school")
    public String getSchool(Model model){
        model.addAttribute("name","北京大学");
        model.addAttribute("age",40);
        return "/demo/view";
    }

    //响应JSON数据(异步请求)
    //Java对象 -> JSON字符串 -> JS对象

    @GetMapping("/emp")
    @ResponseBody
    public Map<String,Object> getEmp(){
        Map<String,Object> emp = new HashMap<>();
        emp.put("name","Anqi");
        emp.put("age",23);
        emp.put("salary",6000);
        return emp;
    }

    @GetMapping("/emps")
    @ResponseBody
    public List<Map<String,Object>> getEmps(){
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> emp = new HashMap<>();
        emp.put("name","Anqi");
        emp.put("age",23);
        emp.put("salary",6000);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name","wenwen");
        emp.put("age",23);
        emp.put("salary",5000);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name","Anqierzi");
        emp.put("age",2);
        emp.put("salary",60000);
        list.add(emp);
        return list;
    }



}
