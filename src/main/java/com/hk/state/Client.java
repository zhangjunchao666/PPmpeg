package com.hk.state;

/**
 * @author 胡冉
 * @Description: TODO
 * @date 2018/11/1411:11
 * @copyright {@link www.hndfsj.com}
 */
public class Client {
    public static void main(String[] args) {
        Context context=new Context(new CloseState());
        context.stateChange();
        context.stateChange();
        context.stateChange();
        context.stateChange();
        context.stateChange();
        context.stateChange();
        context.stateChange();
        context.stateChange();
    }
}
