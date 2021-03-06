package com.dfj.test;

import com.dfj.dao.UserDao;
import com.dfj.entity.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserDaoTest {
    @Test
    public void test1() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserDao userDao = ac.getBean("userDao", UserDao.class);
        User user = userDao.findByName("test1");
        System.out.println(user);
    }
}
