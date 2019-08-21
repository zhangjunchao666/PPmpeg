package com.hk;

import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author 胡冉
 * @Description: TODO
 * @date 2018/11/1515:17
 * @copyright {@link www.hndfsj.com}
 */
public class Demo {


    public static void main(String[] args) {
 List<User> list=new ArrayList<>();
 User user=new User();
 user.setName("df");
// list.add(user);
     // List<User> userList=  Optional.ofNullable(list).get();
       assert list!=null;

    }

    public static User createUser(){
        User user=new User();
        user.setName("huran");
        return  user;
    }
}
@Data
class User{
    private String name;

}

