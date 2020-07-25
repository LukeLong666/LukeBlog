package com.luke.luke_blog;

import com.luke.luke_blog.utils.IdWorker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LukeBlogApplicationTests {

    @Autowired
    IdWorker idWorker;

    @Test
    void contextLoads() {
        String fcc339d2230c5d3c47c7b370454ce502 = AA.convertMD5("fcc339d2230c5d3c47c7b370454ce502");
        System.out.println(fcc339d2230c5d3c47c7b370454ce502);

    }

}

class AA{
    public static String convertMD5(String inStr){

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++){
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }
}
