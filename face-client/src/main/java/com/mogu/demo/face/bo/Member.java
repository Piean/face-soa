package com.mogu.demo.face.bo;

import com.mogu.demo.face.bo.base.BaseMember;

public class Member extends BaseMember {
    public enum MemberType {
        FIRST(1,"首次进店"),
        AGAIN(2,"复购顾客"),
        MEMBER(3,"本店会员"),
        ;
        private int id;
        private String name;

        MemberType(int id, String name) {
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

        public String getName(int id) {
            for (MemberType member : MemberType.values()) {
                if (id == member.id)
                    return member.name;
            }
            return "";
        }
    }

    public enum CardType {
        SILVER(1,"银卡会员"),
        GOLD(2,"金卡会员"),
        ;
        private int id;
        private String name;

        CardType(int id, String name) {
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

        public String getName(int id) {
            for (CardType card : CardType.values()) {
                if (id == card.id)
                    return card.name;
            }
            return "";
        }
    }
}
