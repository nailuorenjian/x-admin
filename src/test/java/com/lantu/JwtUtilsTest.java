package com.lantu;

import com.lantu.common.vo.utils.JwtUtils;
import com.lantu.sys.entity.User;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtUtilsTest {

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    public void testCreateJwt(){
        User user = new User();
        user.setUsername("zz");
        user.setPhone("18366980850");
        String token = jwtUtils.createToken(user);
        System.out.println(token);
    }

    @Test
    public void testParseJwt(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5NmY4ZTM0Yy01ZDhhLTRiZGQtYTgzNy1jMTJhNTlmNjc2YWUiLCJzdWIiOiJ7XCJwaG9uZVwiOlwiMTgzNjY5ODA4NTBcIixcInVzZXJuYW1lXCI6XCJ6elwifSIsImlhdCI6MTcwNjAxMzA0MiwiZXhwIjoxNzA2MDE0ODQyfQ.iU84VyYssgyNUG6FkAbZMom4EYLIG-dOytem71xBTKY";
        Claims claims = jwtUtils.parseToken(token);
        System.out.println(claims);
    }

    @Test
    public void testParseJwt2(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5NmY4ZTM0Yy01ZDhhLTRiZGQtYTgzNy1jMTJhNTlmNjc2YWUiLCJzdWIiOiJ7XCJwaG9uZVwiOlwiMTgzNjY5ODA4NTBcIixcInVzZXJuYW1lXCI6XCJ6elwifSIsImlhdCI6MTcwNjAxMzA0MiwiZXhwIjoxNzA2MDE0ODQyfQ.iU84VyYssgyNUG6FkAbZMom4EYLIG-dOytem71xBTKY";
        User user = jwtUtils.parseToken(token, User.class);
        System.out.println(user);
    }
}
