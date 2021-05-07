package cn.aariety.question;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * 题目格式化器，生成各个题目的字符串
 */
class QuestionFormater {

    /**
     * 获取题目的字符串表示
     *
     * @param question 题目
     * @return 返回值
     */
    static String format(Question question) {
        StringBuilder sb = new StringBuilder();

        // 追加题目内容
        sb.append(question.getQuestion());
        sb.append(System.lineSeparator());

        // 如果是单选或多选题则追加选项
        if (question instanceof Single || question instanceof Multiple) {
            String alphas = "ABCDE";

            // 利用反射机制获取选项列表
            List options = null;
            Class<? extends Question> clazz = question.getClass();
            try {
                Method mathod = clazz.getDeclaredMethod("getOptions");
                options = (List) mathod.invoke(question);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

            // 追加选项内容
            int i = 0;
            for (Object optionObj : Objects.requireNonNull(options)) {
                String option = (String) optionObj;
                sb.append(alphas.charAt(i));
                sb.append(". ");
                sb.append(option);
                sb.append(System.lineSeparator());
                i++;
            }
        }

        // 追加答案
        sb.append("答案：");
        sb.append(question.getAnswer());

        // 返回
        return sb.toString();
    }
}
