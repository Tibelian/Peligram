package com.tibelian.peligram.database;

public class PostDbSchema {

    public static final class PostTable {

        public static final String NAME = "post";

        public static final class Cols
        {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String IMAGE = "image";
            public static final String LIKES = "likes";
        }

    }

}
