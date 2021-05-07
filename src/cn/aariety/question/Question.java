package cn.aariety.question;

/**
 * 题目类，代表一个题目
 */
public interface Question {
    /**
     * 在题目中进行搜索
     *
     * @param keyword 搜索关键字
     * @return 是否匹配
     */
    boolean match(String keyword);

    /**
     * 获取题目的问题部分
     *
     * @return 返回值
     */
    String getQuestion();

    /**
     * 获取题目的答案
     *
     * @return 返回值
     */
    String getAnswer();
}
