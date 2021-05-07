package cn.aariety.view;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.IOException;

/**
 * 用户输入获取器，也包括获取输入前给予用户的指示。
 */
public class InputGetter {
    private BufferedReader reader =
            new BufferedReader(new FileReader(FileDescriptor.in));

    String getInput() {
        // 输出“输入指示”
        System.out.print(">>>");
        try {
            // 读取用户输入
            String input =  reader.readLine();
            // 空出一行
            System.out.println();
            return input;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
