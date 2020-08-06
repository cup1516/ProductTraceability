package com.pt.center.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class UsernameService {
    private static final char[] CHARS = new char[]{
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '~', '!', '@', '#', '$', '%', '^', '-', '+', '&', '_'
    };
    public static String randomUsername(int l){//前5位字母，后面全数字
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < 5; i++) {
            int num=(int) (Math.random()*(51-0+1)+0);
            buf.append(CHARS[num]);
        }
        for (int i = 5; i < l; i++) {
            int num=(int) (Math.random()*(61-52+1)+52);
            buf.append(CHARS[num]);
        }
        return buf.toString();
    }

    public static String randomWebsite(int l){//生成随机l位的字母组合
        StringBuffer sb = new StringBuffer();
        sb.append("www.");
        for (int i = 0; i < l; i++) {
            int num=(int) (Math.random()*(51-0+1)+0);
            sb.append(CHARS[num]);
        }
        sb.append(".com");
        return sb.toString();
    }


    private void PasswordUtil() {
    }

    public static String randomGenerate(int length) {//字母数字符号随机
        List<String> list = new ArrayList<String>(CHARS.length);
        for (int i = 0; i < CHARS.length; i++) {
            list.add(String.valueOf(CHARS[i]));
        }
        Collections.shuffle(list);

        int count = 0;
        StringBuffer sb = new StringBuffer();
        Random random = new Random(System.nanoTime());
        while (count < length) {
            int i = random.nextInt(list.size());
            sb.append(list.get(i));
            count++;
        }
        return sb.toString();
    }
}
