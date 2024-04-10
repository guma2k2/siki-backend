package com.siki.review.utils;

public class Constants {
    public final class ERROR_CODE {
        public static final String CART_NOT_FOUND = "Cart with id : %d is not found";
        public static final String ACCESS_DENIED_ERROR_FORMAT = "%s: Client %s don't have access right for this resource";
    }

    public final class PageableConstant{
        public static final int DEFAULT_PAGE_NUMBER = 0;
        public static final int DEFAULT_PAGE_SIZE = 2;
    }
}
