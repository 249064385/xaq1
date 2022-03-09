package com.xu.community.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class AlphaService {
    public AlphaService(){
        System.out.println("实例化AlphaServie");
    }

    @PostConstruct
    public void init(){
        System.out.println("初始化AlphaService");
    }
    @PreDestroy
    public void destroy(){
        System.out.println("销毁");
    }
}