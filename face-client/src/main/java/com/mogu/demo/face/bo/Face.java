package com.mogu.demo.face.bo;

import com.mogu.demo.face.bo.base.BaseFace;

public class Face  extends BaseFace {
    public enum Sex {
        MAN(1,"男"),
        WOMAN(2,"女"),
        ;
        private int id;
        private String name;

        Sex(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static String getName(int id) {
            for (Sex sex : Sex.values()) {
                if (id == sex.id)
                    return sex.name;
            }
            return "";
        }
    }
}
