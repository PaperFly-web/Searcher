package cn.aariety.question;

/**
 * 表示题型的枚举类型
 */
public enum QuestionType {
    /**
     * 单选题
     */
    SINGLE,

    /**
     * 多选题
     */
    MULTIPLE,

    /**
     * 判断题
     */
    TRUE_FALSE,

    /**
     * 填空题
     */
    FILL;

    /**
     * 获取该题型对应的 Class 对象
     *
     * @return 返回值
     */
    public Class<? extends Question> getQuestionClass() {
        switch (this) {
            case SINGLE:
                return Single.class;
            case MULTIPLE:
                return Multiple.class;
            case TRUE_FALSE:
                return TrueFalse.class;
            case FILL:
                return Fill.class;
            default:
                throw new RuntimeException();
        }
    }
}
