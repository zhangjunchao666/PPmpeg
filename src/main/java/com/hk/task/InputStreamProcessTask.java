package com.hk.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

/**
 * @author 胡冉
 * @ClassName ProcessTask
 * @Date 2019/5/8 11:07
 * @Version 2.0
 */
public class InputStreamProcessTask implements Callable<Void> {
    private InputStream inputStream;

   public InputStreamProcessTask(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public Void call() throws Exception {
        BufferedReader br1 = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line1 = null;
            while ((line1 = br1.readLine()) != null) {
                if (line1 != null) {
                    System.out.println(line1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

