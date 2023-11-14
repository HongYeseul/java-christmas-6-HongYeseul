package christmas.view.constants;

public class Regex {
    /**
     * 숫자에 대한 범위를 나타내는 정규표현식
     * 결과: 1~39까지의 정수(true)
     */
    public static final String REGEX_DATE_NUMERIC = "([1-3]\\d)|(^[1-9])";

    /**
     * "한글-nn,한글-n..."에 대한 정규표현식
     * nn: 1-9로 시작하는 한자리거나 두자리 숫자
     * 결과: "한글-10"(true), "한글-0"(false)
     */
    public static final String REGEX_ORDER_FORMAT = "(([ㄱ-ㅎ가-힣]){1,}-([1-9]\\d?)(,)?){1,}";
}
