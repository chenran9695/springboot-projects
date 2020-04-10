package com.cr;

import com.cr.pojo.Dog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBoot2ConfigApplicationTests {
    @Autowired
    private Dog dog;

    @Test
    void contextLoads() {
        System.out.print(dog.toString());
    }

}
