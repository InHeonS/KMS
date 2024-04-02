package com.stupig.kms.common.utils;

public class StringUtils extends org.springframework.util.StringUtils {

    public static final String EMPTY = "";

    /**
     * Null Or Empty String 확인
     * <pre>
     *     boolean bEmpty = StringUtils.isNullOrEmpty(str);
     * </pre>
     *
     * @param str 문자열
     * @return boolean
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 가변 인자 문자열 리스트 중 Null Or Empty String 존재 확인
     * <pre>
     *     boolean bAnyEmpty = StringUtils.isAnyNullOrEmpty(str1, str2);
     * </pre>
     *
     * @param strList 문자열 리스트
     * @return boolean
     */
    public static boolean isAnyNullOrEmpty(String... strList) {
        for(String str : strList) {
            if(isNullOrEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 가변 인자 문자열 리스트 모두 Null Or Empty String 여부 확인
     * <pre>
     *     boolean bAllEmpty = StringUtils.isAllNullOrEmpty(str1, str2);
     * </pre>
     *
     * @param strList 문자열 리스트
     * @return boolean
     */
    public static boolean isAllNullOrEmpty(String... strList) {
        for(String str : strList) {
            if(!isNullOrEmpty(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Null Or Empty Or Blank String 확인
     * <pre>
     *     boolean bBlank = StringUtils.isNullOrEmpty(str);
     * </pre>
     *
     * @param str 문자열
     * @return boolean
     */
    public static boolean isNullOrBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 가변 인자 문자열 리스트 중 Null Or Empty Or Blank String 존재 확인
     * <pre>
     *     boolean bAnyBlank = StringUtils.isAnyNullOrBlank(str1, str2);
     * </pre>
     *
     * @param strList 문자열 리스트
     * @return boolean
     */
    public static boolean isAnyNullOrBlank(String... strList) {
        for(String str : strList) {
            if(isNullOrBlank(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 가변 인자 문자열 리스트 모두 Null Or Empty Or Blank String 여부 확인
     * <pre>
     *     boolean bAllBlank = StringUtils.isAllNullOrBlank(str1, str2);
     * </pre>
     *
     * @param strList 문자열 리스트
     * @return boolean
     */
    public static boolean isAllNullOrBlank(String... strList) {
        for(String str : strList) {
            if(!isNullOrBlank(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Null -> def
     *
     * @param str 원본 문자열
     * @param def 대체 문자열
     * @return String
     */
    public static String nvl(String str, String def) {
        return str == null ? def : str;
    }

    /**
     * Null -> ""
     *
     * @param str 원본 문자열
     * @return String
     */
    public static String nvl(String str) {
        return nvl(str, EMPTY);
    }

    /**
     * Null Or Empty String -> def
     *
     * @param str 원본 문자열
     * @param def 대체 문자열
     * @return String
     */
    public static String nvlE(String str, String def) {
        return isNullOrEmpty(str) ? def : str;
    }

    /**
     * Null Or Empty String -> ""
     *
     * @param str 원본 문자열
     * @return String
     */
    public static String nvlE(String str) {
        return nvlE(str, EMPTY);
    }

    /**
     * Null Or Empty Or Blank String -> dev
     *
     * @param str 원본 문자열
     * @param def 대체 문자열
     * @return String
     */
    public static String nvlB(String str, String def) {
        return isNullOrBlank(str) ? def: str;
    }

    /**
     * Null Or Empty Or Blank String -> ""
     *
     * @param str 원본 문자열
     * @return String
     */
    public static String nvlB(String str) {
        return nvlB(str, EMPTY);
    }

    /**
     * Null Or Empty Or Blank String -> Next List
     * 최종 ""
     *
     * @param strList
     * @return
     */
    public static String nvlBL(String... strList) {
        for (String str : strList) {
            if (!StringUtils.isNullOrBlank(str)) {
                return str;
            }
        }

        return StringUtils.EMPTY;
    }


}
