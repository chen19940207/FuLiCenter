package cn.ucai.fulicenter.model.bean;


import cn.ucai.fulicenter.application.I;

/**
 * Created by Administrator on 2017/1/9 0009.
 */

public class User {

    /**
     * muserName : a123456
     * muserNick : 1234563
     * mavatarId : 245
     * mavatarPath : user_avatar
     * mavatarSuffix : .jpg
     * mavatarType : 0
     * mavatarLastUpdateTime : 1477446355442
     */

    private String muserName;
    private String muserNick;
    private int mavatarId;
    private String mavatarPath;
    private String mavatarSuffix;
    private int mavatarType;
    private String mavatarLastUpdateTime;

    public String getMuserName() {
        return muserName;
    }

    public void setMuserName(String muserName) {
        this.muserName = muserName;
    }

    public String getMuserNick() {
        return muserNick;
    }

    public void setMuserNick(String muserNick) {
        this.muserNick = muserNick;
    }

    public int getMavatarId() {
        return mavatarId;
    }

    public void setMavatarId(int mavatarId) {
        this.mavatarId = mavatarId;
    }

    public String getMavatarPath() {
        return mavatarPath;
    }

    public void setMavatarPath(String mavatarPath) {
        this.mavatarPath = mavatarPath;
    }

    public String getMavatarSuffix() {
        return mavatarSuffix != null ? mavatarSuffix : I.AVATAR_SUFFIX_JPG;
    }

    public void setMavatarSuffix(String mavatarSuffix) {
        this.mavatarSuffix = mavatarSuffix;
    }

    public int getMavatarType() {
        return mavatarType;
    }

    public void setMavatarType(int mavatarType) {
        this.mavatarType = mavatarType;
    }

    public String getMavatarLastUpdateTime() {
        return mavatarLastUpdateTime;
    }

    public void setMavatarLastUpdateTime(String mavatarLastUpdateTime) {
        this.mavatarLastUpdateTime = mavatarLastUpdateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!getMuserName().equals(user.getMuserName())) return false;
        return getMavatarLastUpdateTime().equals(user.getMavatarLastUpdateTime());

    }

    @Override
    public int hashCode() {
        int result = getMuserName().hashCode();
        result = 31 * result + getMavatarLastUpdateTime().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "mavatarId=" + mavatarId +
                ", muserName='" + muserName + '\'' +
                ", muserNick='" + muserNick + '\'' +
                ", mavatarPath='" + mavatarPath + '\'' +
                ", mavatarSuffix='" + mavatarSuffix + '\'' +
                ", mavatarType=" + mavatarType +
                ", mavatarLastUpdateTime='" + mavatarLastUpdateTime + '\'' +
                '}';
    }

}
