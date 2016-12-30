package com.groupproject.android.PharmTech_SA_TN_DP_FY.database;


/**  * Created by thomasnewell on 11/1/16. 
 *
 * to provide constants for database use
 * if a field/object property name is changed, it should happen here first
 *
 * */

    public class DrugDbSchema{
    public static final class DrugTable{
        public static final String NAME = "drugs";
        public static final class Cols {
            public static final String ID = "_id";
            public static final String GENERIC = "generic";
            public static final String BRAND = "brand";
            public static final String PURPOSE = "purpose";
            public static final String SCHEDULE = "schedule";
            public static final String SPECIAL = "special";
            public static final String CATEGORY = "category";
            public static final String TOPIC = "topic";
            public static final String NOTES = "notes";

        }
    }
}

