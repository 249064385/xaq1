package com.xu.community.service;

import com.xu.community.dao.UserMapper;
import com.xu.community.entity.User;
import com.xu.community.util.CommunityUtil;
import com.xu.community.util.ComunityConstant;
import com.xu.community.util.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserService implements ComunityConstant {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${community.path.domain}")
    private String domain;

    @Value("@{server.servlet.context-path}")
    private String contextPath;

    public User findUserById(int userId){
        return userMapper.selectUserById(userId);
    }

    public Map<String,Object> register(User user){
        Map<String,Object> map = new HashMap<String,Object>();

        //空值处理
        if(user==null){
            throw new IllegalArgumentException("参数不能为空");
        }
        if(StringUtils.isBlank(user.getUsername())){
            map.put("usernameMsg","账号不能为空！");
        }
        if(StringUtils.isBlank(user.getPassword())){
            map.put("passwordMsg","密码不能为空！");

        }
        if(StringUtils.isBlank(user.getEmail())){
            map.put("emailMsg","邮箱不能为空！");
        }
        //验证账号是否重复
        User temp = userMapper.selectUserByName(user.getUsername());
        if(temp!=null){
            map.put("usernameMsg","该账号已存在！");
            return map;
        }
        //验证邮箱
        temp = userMapper.selectUserByEmail(user.getEmail());
        if(temp!=null){
            map.put("emailMsg","该邮箱已被注册");
            return map;
        }
        //注册用户
        user.setSalt(CommunityUtil.generateUUID().substring(0,5));
        user.setPassword(CommunityUtil.md5(user.getPassword()+user.getSalt()));

        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(CommunityUtil.generateUUID());
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        user.setCreateTime(new Date());
        userMapper.insertUser(user);
        //邮件
        Context context = new Context();
        context.setVariable("email",user.getEmail());
        //http://locolhost:8080/community/activation/id/code
        String url = domain+CONTEXT_PATH +"/activation/"+user.getId()+"/"+user.getActivationCode();
        context.setVariable("url",url);
        String content = templateEngine.process("/mail/activation",context);
        mailClient.sendMail(user.getEmail(),"请激活您的账号！",content);
        return map;
    }

    public int activation(int userId, String code){
        User user = userMapper.selectUserById(userId);
        if(user.getStatus()==1){
            return ACTIVATION_REPEAT;
        }else if(user.getActivationCode().equals(code)){
            userMapper.updateStatus(userId,1);
            return ACTIVATION_SUCCESS;
        }else{
            return ACTIVATION_FAILURE;
        }

    }


}