package cn.aariety;

import cn.aariety.question.Question;
import cn.aariety.question.QuestionLoader;
import cn.aariety.question.QuestionType;
import cn.aariety.view.InputGetter;
import cn.aariety.view.Menu;
import cn.aariety.view.SearchProcess;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;

/**
 * 主类
 */
public class Main {
    /**
     * 程序执行入口
     *
     * @param args 命令行参数（无需）
     * @throws IOException 异常
     */
    public static void main(String[] args) throws IOException {
        // 用户输入获取器，也包括输出“输入提示”
        InputGetter inputGetter = new InputGetter();

        // 构造 Menu 类，用于输出菜单，以及获取用户选项
        Menu menu = new Menu("选择题型或退出程序", ". ", inputGetter);
        menu.addQuestionType(QuestionType.SINGLE, "选择题");
        menu.addQuestionType(QuestionType.MULTIPLE, "多选题");
        menu.addQuestionType(QuestionType.TRUE_FALSE, "判断题");
        menu.addQuestionType(QuestionType.FILL, "填空题");

        // 构造 SearchProcess 类，用于用户的题目搜索过程
        SearchProcess searchProcess = new SearchProcess(inputGetter);

        // 获取各个题型文件的 Reader 对象
        // 题型文件所在的目录
        String resourcesDir = "resources";
        // 题型文件名
        String[] fileNames = new String[]{
                "single.yaml",
                "multiple.yaml",
                "true-false.yaml",
                "fill.yaml"
        };
        // 根据文件路径以及文件名构造 Reader 对象。
        Reader[] readers = new Reader[4];
        for (int i = 0; i < 4; i++) {
            // 从文件路径以及文件名生成完整路径名
            String filePath = Path.of(resourcesDir, fileNames[i]).toString();
            // 构造 Reader
            readers[i] = new FileReader(filePath, StandardCharsets.UTF_8);
        }

        // 获取 QuestionLoader，该对象可以从相应题型文件的 Reader 中解析出多个
        // Question 对象
        Map<QuestionType, Reader> fileReaders = new HashMap<>();
        fileReaders.put(QuestionType.SINGLE, readers[0]);
        fileReaders.put(QuestionType.MULTIPLE, readers[1]);
        fileReaders.put(QuestionType.TRUE_FALSE, readers[2]);
        fileReaders.put(QuestionType.FILL, readers[3]);
        QuestionLoader questionLoader = new QuestionLoader(fileReaders);

        while (true) {
            // 展示菜单并获取用户输入
            menu.run();
            if (menu.shouldExit()) {
                // 如果用户指示退出程序
                return;
            } else {
                if (menu.isAllQuestionType()) {
                    // 如果用户选择搜索所有题型
                    Set<List<Question>> set = new HashSet<>();
                    set.add(questionLoader.getQuestions(QuestionType.SINGLE));
                    set.add(questionLoader.getQuestions(QuestionType.MULTIPLE));
                    set.add(questionLoader.getQuestions(QuestionType.TRUE_FALSE));
                    set.add(questionLoader.getQuestions(QuestionType.FILL));
                    searchProcess.run(set);
                } else {
                    // 如果用户选择搜索单个题型
                    QuestionType questionType = menu.getLastQuestionType();
                    List<Question> questions =
                            questionLoader.getQuestions(questionType);
                    searchProcess.run(questions);
                }
            }
        }
    }
}
