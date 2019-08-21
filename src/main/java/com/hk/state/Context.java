package com.hk.state;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 胡冉
 * @Description: TODO
 * @date 2018/11/1411:06
 * @copyright {@link www.hndfsj.com}
 */
@Data
@AllArgsConstructor
public class Context {
    private State state;

    public void stateChange() {
        System.out.println("========================");
        state.action(this);
    }
}
