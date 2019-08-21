package com.hk.state;

import com.sun.org.apache.xerces.internal.impl.xpath.XPath;

/**
 * @author 胡冉
 * @Description: TODO
 * @date 2018/11/1411:10
 * @copyright {@link www.hndfsj.com}
 */
public class CloseState extends State {
    @Override
    public void action(Context context) {
        System.out.println("关");
        context.setState(new OpenSate());
    }
}
