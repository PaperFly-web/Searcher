package cn.aariety.view;

import cn.aariety.question.Question;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 负责循环接收用户的搜索关键字，执行搜索，将结果展示给用户。
 */
public class SearchProcess {

    /**
     * 用户输入获取器
     */
    private InputGetter inputGetter;

    /**
     * 构造一个 SearchProcess 对象
     *
     * @param inputGetter 用户输入获取器
     */
    public SearchProcess(InputGetter inputGetter) {
        this.inputGetter = inputGetter;
    }

    /**
     * 用 keyword 在 questions 中仅行搜索。并向用户输出所有匹配的题目。
     *
     * @param questions 被搜索的题目列表
     * @param keyword 用于搜索的关键字
     * @return 是否有匹配的题目
     */
    private static boolean searchInQuestionType(List<Question> questions,
                                                String keyword) {
        boolean haveMatched = false;
        for (Question question : questions) {
            boolean isMatch = question.match(keyword);
            if (isMatch) {
                haveMatched = true;
                System.out.println(question);
            }
        }
        return haveMatched;
    }

    /**
     * questionTypes 包括了多个题目列表。该方法会获取用户输入的单行文本，并在
     * questionTypes 中所包含的所有题目中进行搜索。搜索到的结果将会展示给用户。
     * 然后循环执行，获取用户的下一个输入。
     *
     * @param questionTypes 包含所有题型的题目列表的 Set 对象
     */
    public void run(Set<List<Question>> questionTypes) {
        interact(keyword -> {
            boolean haveAnyMatched = false;
            for (List<Question> questions : questionTypes) {
                boolean haveMatched = searchInQuestionType(questions, keyword);
                if (haveMatched) {
                    haveAnyMatched = true;
                }
            }
            if (!haveAnyMatched) {
                printNoMatch();
            }
        });
    }

    /**
     * 该方法会获取用户输入的单行文本，并在 questions 中进行搜索。输出所有匹配的
     * 题目。这个过程会循环执行。
     *
     * @param questions 被搜索的题目列表
     */
    public void run(List<Question> questions) {
        interact(keyword -> {
            boolean haveMatched = searchInQuestionType(questions, keyword);
            if (!haveMatched) {
                printNoMatch();
            }
        });
    }

    /**
     * 负责循环获取用户的输入，并用该输入作为参数，调用 search 提供的搜索方法。
     *
     * @param search 执行搜索过程的 Consumer 对象
     */
    private void interact(Consumer<String> search) {
        printPrompt();
        String keyword = inputGetter.getInput();
        while (!keyword.isEmpty()) {
            search.accept(keyword);
            keyword = inputGetter.getInput();
        }
    }

    /**
     * 输出没有匹配题目时的提示语
     */
    private void printNoMatch() {
        System.out.println("没有匹配的题目，请继续输入：");
    }

    /**
     * 在首次获取用户输入关键字前调用，给予用户提示
     */
    private void printPrompt() {
        System.out.println("输入题目或选项的一部分来搜索题目，" +
                "输入空行可以返回主菜单");
    }

}
