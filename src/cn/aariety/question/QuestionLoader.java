package cn.aariety.question;

import org.yaml.snakeyaml.Yaml;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 题目加载器
 * 从 yaml 格式的 Reader 中获取 Question 列表。
 */
public class QuestionLoader {
    private Map<QuestionType, Reader> readers;
    private Map<QuestionType, List<Question>> questionTypes;

    /**
     * 构造一个 QuestionLoader
     *
     * @param readers 各个题型与其 Reader 的映射
     */
    public QuestionLoader(Map<QuestionType, Reader> readers) {
        this.readers = readers;
        questionTypes = new HashMap<>();
    }

    /**
     * 获取所给题型对应的题目列表
     *
     * @param questionType 题型
     * @return 返回值
     */
    public List<Question> getQuestions(QuestionType questionType) {
        if (!questionTypes.containsKey(questionType)) {

            // 利用 reader 中的 Reader 解析 yaml 文件
            List<?> questionsData =
                    new Yaml().loadAs(readers.get(questionType), List.class);

            // 创建该题型的题目
            List<Question> questions = new ArrayList<>();
            for (Object questionData : questionsData) {
                // 创建 Question 并加入到 questions 中
                Map<?, ?> questionMap = (Map) questionData;
                Question question = QuestionFactory
                        .createQuestion(questionType, questionMap);
                questions.add(question);
            }

            questionTypes.put(questionType, questions);
        }

        return questionTypes.get(questionType);
    }
}
