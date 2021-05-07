package cn.aariety.question;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 根据 map 来创建 Question 对象
 */
class QuestionFactory {
    static Question createQuestion(QuestionType qType, Map<?, ?> map) {

        // 根据不同的 Question 子类，创建不同的构造器参数列表以及参数类型列表
        Class<?>[] argsClass;
        Object[] args;
        switch (qType) {
            case SINGLE:
            case MULTIPLE:
                // 如果是单选或多选
                argsClass =
                        new Class[]{String.class, List.class, String.class};
                args = new Object[]{
                        (String) map.get("question"),
                        (List) map.get("options"),
                        (String) map.get("answer"),
                };
                break;
            case TRUE_FALSE:
            case FILL:
                // 如果是判断或填空
                argsClass = new Class[]{String.class, String.class};
                args = new Object[]{
                        (String) map.get("question"),
                        (String) map.get("answer"),
                };
                break;
            default:
                throw new RuntimeException();
        }

        // 获取具体 Question 实现类的构造器
        Class<? extends Question> clazz = qType.getQuestionClass();
        Constructor<? extends Question> constructor = null;
        try {
            constructor = clazz.getConstructor(argsClass);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        // 构造 Question 对象 question
        Question question = null;
        try {
            question = Objects.requireNonNull(constructor).newInstance(args);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return question;
    }
}
