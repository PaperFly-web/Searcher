package cn.aariety.question;

/**
 * 填空题
 */
public class Fill implements Question {
    private String question;
    private String answer;

    /**
     * 构造一个 Fill 对象
     *
     * @param question 题目内容
     * @param answer   题目答案
     */
    public Fill(String question, String answer) {
        this.question = question;
        this.answer = answer;
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
     * 获取 answer
     *
     * @return 返回值
     */
    @Override
    public String getAnswer() {
        return answer;
    }

    /**
     * 搜索题目中是否有匹配 keyword 的内容
     *
     * @param keyword 搜索关键字
     * @return 匹配时返回 true 否则为 false
     */
    @Override
    public boolean match(String keyword) {
        return question.contains(keyword);
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
