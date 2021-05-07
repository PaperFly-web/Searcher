package cn.aariety.question;

import java.util.List;

/**
 * 多选题
 */
public class Multiple implements Question {
    private String question;
    private List<String> options;
    private String answer;

    /**
     * 构造一个 Multiple 对象
     *
     * @param question 题目内容
     * @param options  选项列表
     * @param answer   题目答案
     */
    public Multiple(String question, List<String> options, String answer) {
        this.question = question;
        this.options = options;
        this.answer = answer;
    }

    /**
     * 搜索题目和选项是否有匹配 keyword 的内容
     *
     * @param keyword 搜索关键字
     * @return 匹配时返回 true 否则为 false
     */
    @Override
    public boolean match(String keyword) {
        if (question.contains(keyword)) {
            return true;
        }
        for (String option : options) {
            if (option.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取 question
     *
     * @return 返回值
     */
    @Override
    public String getQuestion() {
        return question;
    }

    /**
     * 获取 options
     *
     * @return 返回值
     */
    public List<String> getOptions() {
        return options;
    }

    /**
     * 获取 answer
     *
     * @return 返回值
     */
    @Override
    public String getAnswer() {
        return answer;
    }

    /**
     * 展示题目
     *
     * @return 返回值
     */
    @Override
    public String toString() {
        return QuestionFormater.format(this);
    }
}
