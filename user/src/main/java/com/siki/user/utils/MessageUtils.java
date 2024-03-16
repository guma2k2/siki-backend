package com.siki.user.utils;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

public class MessageUtils {
    public static String getMessage(String errorCode, Object ...var2) {
        FormattingTuple formattingTuple = MessageFormatter.format(errorCode, var2);
        return formattingTuple.getMessage();
    }
}
