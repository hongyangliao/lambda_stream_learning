package com.liao;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 人员类
 *
 * @author liaohongyang
 * @date 2020/7/29 17:42
 **/
@Data
@Accessors(chain = true)
public class Person {

    private String name;

    private int age;

    private String sex;
}
