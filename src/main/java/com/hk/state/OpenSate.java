package com.hk.state;

/**
 * @author 胡冉
 * @Description: TODO
 * @date 2018/11/1411:09
 * @copyright {@link www.hndfsj.com}
 */
public class OpenSate extends State {
    @Override
    public void action(Context context) {
        System.out.println("开");
        context.setState(new CloseState());
    }
}
