package com.siki.product.utils;

public class Constants {
    public final class ERROR_CODE {
        public static final String PRODUCT_NAME_IS_EXISTED = "Product with name : %s is existed";
        public static final String PRODUCT_NOT_FOUND = "Product with id : %d is not found";
        public static final String BRAND_NOT_FOUND = "Brand with id : %d is not found";
        public static final String CATEGORY_NOT_FOUND = "Category with id : %d is not found";
        public static final String ACCESS_DENIED_ERROR_FORMAT = "%s: Client %s don't have access right for this resource";
    }

    public final class PageableConstant{
        public static final String DEFAULT_PAGE_NUMBER = "0";
        public static final String DEFAULT_PAGE_SIZE = "20";
        public static final String DEFAULT_SORT_FIELD = "id";
        public static final String DEFAULT_SORT_DIR = "desc";
    }
}
