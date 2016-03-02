package com.example.zjb.bamin.models.about_used_contact;

/**
 * Created by zjb on 2016/2/19.
 */
public class UsedPersonID {

    /**
     * msg : success
     * result : {"area":"广西壮族自治区百色市靖西县","birthday":"1980年04月11日","sex":"女"}
     * retCode : 200
     */

    private String msg;
    /**
     * area : 广西壮族自治区百色市靖西县
     * birthday : 1980年04月11日
     * sex : 女
     */

    private ResultEntity result;
    private String retCode;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getMsg() {
        return msg;
    }

    public ResultEntity getResult() {
        return result;
    }

    public String getRetCode() {
        return retCode;
    }

    public static class ResultEntity {
        private String area;
        private String birthday;
        private String sex;

        public void setArea(String area) {
            this.area = area;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getArea() {
            return area;
        }

        public String getBirthday() {
            return birthday;
        }

        public String getSex() {
            return sex;
        }
    }
}
