package com.mogu.demo.baidu.http;

import com.mogu.demo.face.bo.Face;

import java.util.List;

public class FaceDetectResult {
    private int face_num;
    List<FaceInfo> face_list;

    public static class FaceInfo {
        private String face_token;
        private String base64Code;
        private double age;
        private Gender gender;
        private int beauty;
        private double face_probability;

        public static class Gender {
            private String type;
            private double probability;

            public int getType() {
                if ("male".equals(this.type)) {
                    return Face.Sex.MAN.getId();
                } else if ("female".equals(this.type)) {
                    return Face.Sex.WOMAN.getId();
                } else {
                    return 0;
                }
            }

            public void setType(String type) {
                this.type = type;
            }

            public double getProbability() {
                return probability;
            }

            public void setProbability(double probability) {
                this.probability = probability;
            }
        }

        public String getFace_token() {
            return face_token;
        }

        public void setFace_token(String face_token) {
            this.face_token = face_token;
        }

        public String getBase64Code() {
            return base64Code;
        }

        public void setBase64Code(String base64Code) {
            this.base64Code = base64Code;
        }

        public double getAge() {
            return age;
        }

        public void setAge(double age) {
            this.age = age;
        }

        public Gender getGender() {
            return gender;
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public int getBeauty() {
            return beauty;
        }

        public void setBeauty(int beauty) {
            this.beauty = beauty;
        }

        public double getFace_probability() {
            return face_probability;
        }

        public void setFace_probability(double face_probability) {
            this.face_probability = face_probability;
        }
    }

    public int getFace_num() {
        return face_num;
    }

    public void setFace_num(int face_num) {
        this.face_num = face_num;
    }

    public List<FaceInfo> getFace_list() {
        return face_list;
    }

    public void setFace_list(List<FaceInfo> face_list) {
        this.face_list = face_list;
    }
}
