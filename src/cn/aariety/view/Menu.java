package cn.aariety.view;

import cn.aariety.question.QuestionType;

import java.util.ArrayList;
import java.util.List;

/**
 * 该类用于显示主菜单，并且获取用户选择的菜单项。
 * 一个完整的菜单应该如下：
 * <pre>
 * &lt;菜单描述&gt;
 * &lt;选项1&gt;&lt;选项分隔符&gt;&lt;选项描述&gt;
 * &lt;选项2&gt;&lt;选项分隔符&gt;&lt;选项描述&gt;
 * &gt;&gt;&gt;&lt;用户输入的选项&gt;
 *
 * [选项不存在，请重新选择
 * &gt;&gt;&gt;&lt;用户输入的选项&gt;
 *
 * [...]]
 * </pre>
 */
public class Menu {

    /**
     * 表示退出选项
     */
    private static Option exitOption = new Option("退出");

    /**
     * 表示所有题型选项
     */
    private static Option allOption = new Option("所有题型");

    /**
     * 用户输入获取器
     */
    private InputGetter inputGetter;

    /**
     * 菜单选项之前给显示用户的提示内容
     */
    private String description;

    /**
     * 包含了所有的选项
     */
    private Options options;

    /**
     * 用户在上一次选择中是否选择了“退出”。
     */
    private boolean shouldExit;

    /**
     * 用户在上一次选择中是否选择了“所有题型”。仅当 shouldExit 为 false 时该变量
     * 才有效
     */
    private boolean isAllQuestionType;

    /**
     * 用户在上一次选择中所选择的题型。仅当 shouldExit 和 isAllQuestionType 均为
     * false 时，该变量才有效
     */
    private QuestionType lastQuestionType;

    /**
     * 构造一个 Menu 对象
     *
     * @param description 菜单选项前的描述内容
     * @param optionSeparator 选项与其描述之间的分隔字符串
     * @param inputGetter 输入获取器
     */
    public Menu(String description, String optionSeparator,
                InputGetter inputGetter) {
        this.inputGetter = inputGetter;
        this.description = description;
        options = new Options(optionSeparator);
    }

    /**
     * 添加一个题型选项
     *
     * @param questionType 题型
     * @param description 题型描述，该描述将会作为选项描述显示给用户
     */
    public void addQuestionType(QuestionType questionType, String description) {
        options.addQuestionOption(questionType, description);
    }

    /**
     * 显示菜单并获取选项
     */
    public void run() {
        // 输出菜单描述
        System.out.println(description);

        // 输出每一个选项
        System.out.println(options);

        // 获取合法的输入，不合法时重复执行
        String input = inputGetter.getInput();
        Option option = options.get(input);
        while (option == null) {
            System.out.println("选项不存在，请重新选择");
            input = inputGetter.getInput();
            option = options.get(input);
        }

        // 判断用户的输入，改变相关的字段
        if (option instanceof QuestionOption) {
            QuestionOption questionOption = (QuestionOption) option;
            shouldExit = false;
            isAllQuestionType = false;
            lastQuestionType = questionOption.getQuestionType();
        } else if (option == allOption) {
            shouldExit = false;
            isAllQuestionType = true;
        } else if (option == exitOption) {
            shouldExit = true;
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * 用户上次是否选择了退出
     *
     * @return 返回值
     */
    public boolean shouldExit() {
        return shouldExit;
    }

    /**
     * 用户上次是否选择了"所有题型"
     * 使用前必须判断 shouldExit() 是否为 false，
     * 当其值为 false 时调用该函数将会抛出一个异常
     *
     * @return 返回值
     */
    public boolean isAllQuestionType() {
        if (shouldExit) {
            throw new RuntimeException();
        }
        return isAllQuestionType;
    }

    /**
     * 返回用户上次选择的题型
     * 使用前必须判断 shouldExit() 与 isAllQuestionType() 是否为 false，
     * 当其中有任意一个为 false 时，调用该函数将会抛出一个异常
     *
     * @return 返回值
     */
    public QuestionType getLastQuestionType() {
        if (shouldExit || isAllQuestionType) {
            throw new RuntimeException();
        }
        return lastQuestionType;
    }

    /**
     * 获取使用的 InputGetter 对象
     *
     * @return 返回值
     */
    public InputGetter getInputGetter() {
        return inputGetter;
    }

    /**
     * 获取 inputGetter
     *
     * @param inputGetter 的值
     */
    public void setInputGetter(InputGetter inputGetter) {
        this.inputGetter = inputGetter;
    }

    /**
     * 获取使用的菜单描述
     *
     * @return 返回值
     */
    public String getDescription() {
        return description;
    }

    /**
     * 获取菜单描述
     *
     * @param description 的值
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取选项分隔符
     *
     * @return 返回值
     */
    public String getSeparator() {
        return options.getSeparator();
    }

    /**
     * 设置选项分隔符
     *
     * @param separator 分隔符
     */
    public void setSeparator(String separator) {
        options.setSeparator(separator);
    }

    /**
     * 表示一个选项
     */
    static class Option {
        /**
         * 选项描述符
         */
        private String description;

        Option(String description) {
            this.description = description;
        }

        String getDescription() {
            return description;
        }
    }

    /**
     * 具体题型的选项
     */
    static class QuestionOption extends Option {
        /**
         * 题型
         */
        private QuestionType questionType;

        QuestionOption(String description,
                       QuestionType questionType) {
            super(description);
            this.questionType = questionType;
        }

        QuestionType getQuestionType() {
            return questionType;
        }
    }

    /**
     * 包含所有选项
     */
    static class Options {
        /**
         * 题型选项
         */
        private List<QuestionOption> questionOptions;

        /**
         * 选项分隔符
         */
        private String separator;

        Options(String separator) {
            this.separator = separator;
            questionOptions = new ArrayList<>();
        }

        String getSeparator() {
            return separator;
        }

        void setSeparator(String separator) {
            this.separator = separator;
        }

        /**
         * 添加题型选项
         *
         * @param questionType 题型
         * @param description 选项描述
         */
        void addQuestionOption(QuestionType questionType, String description) {
            questionOptions.add(new QuestionOption(description, questionType));
        }

        /**
         * 获取选项名对应的 Option 对象
         *
         * @param optionName 选项名
         * @return Option 对象
         */
        Option get(String optionName) {
            int optionPos;
            try {
                optionPos = Integer.parseInt(optionName);
            } catch (NumberFormatException e) {
                return null;
            }
            int optionNum = questionOptions.size();
            if (optionPos <= optionNum && optionPos >= 1) {
                return questionOptions.get(optionPos - 1);
            } else if (optionPos == optionNum + 1) {
                return allOption;
            } else if (optionPos == optionNum + 2) {
                return exitOption;
            } else {
                return null;
            }
        }

        /**
         * 展示所有选项
         *
         * @return 返回值
         */
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            int optionNum = questionOptions.size();
            for (int i = 0; i < optionNum; i++) {
                sb.append(optionString(i + 1, questionOptions.get(i)));
                sb.append(System.lineSeparator());
            }
            sb.append(optionString(optionNum + 1, allOption));
            sb.append(System.lineSeparator());
            sb.append(optionString(optionNum + 2, exitOption));
            return sb.toString();
        }

        /**
         * 展示单个选项
         *
         * @param optionName 选项名
         * @param option Option 对象
         * @return 生成单个选项的字符串表示
         */
        private String optionString(int optionName, Option option) {
            return optionName + separator + option.getDescription();
        }

    }
}
