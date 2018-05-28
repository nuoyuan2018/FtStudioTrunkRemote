package cn.ftoutiao.account.android.model.db;

import com.acmenxd.retrofit.HttpDataEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;

import java.io.Serializable;

@Entity(nameInDb = "tbl_bill")
public class ListItemBO extends HttpDataEntity implements Serializable, Cloneable {

    static final long serialVersionUID = 42L;

    /**
     * itemId : 45397692
     * aId : 70536628
     * aType :
     * cType : 2
     * cId : 22001
     * cName : 家装建材
     * rId : 22001
     * rName : 家装建材
     * aDate : 20180227
     * ctime : 1519703729
     * mtime : 1519703729
     * amount : 665
     * remark :
     * icon : payout_icon_22001.png
     * color : #c0b18f
     * uid : fQfSTQ7dBUYDka6tQY7P45fOmKKaPzIi
     * nickname : 唐僧
     */


    @Id
    private Long id;

    @Unique
    public String itemId;
    public String aId;
    public String aType;
    public int cType;
    public int cId;
    public String cName;
    public int rId;
    public String rName;
    public String aDate;


    public int ctime;
    public int mtime;
    public float amount;
    public String remark = "";
    public String icon;
    public String color;

    public String uid;
    public String nickname = "";
    public String year;
    public String month;
    public String day;

    @Transient
    public String count;//同一个类别个数
    @Transient
    public double typeAmountCount; //类别amount 总和
    @Transient
    public float amountCount; //总个数





    @Generated(hash = 126754596)
    public ListItemBO(Long id, String itemId, String aId, String aType, int cType,
            int cId, String cName, int rId, String rName, String aDate, int ctime,
            int mtime, float amount, String remark, String icon, String color,
            String uid, String nickname, String year, String month, String day) {
        this.id = id;
        this.itemId = itemId;
        this.aId = aId;
        this.aType = aType;
        this.cType = cType;
        this.cId = cId;
        this.cName = cName;
        this.rId = rId;
        this.rName = rName;
        this.aDate = aDate;
        this.ctime = ctime;
        this.mtime = mtime;
        this.amount = amount;
        this.remark = remark;
        this.icon = icon;
        this.color = color;
        this.uid = uid;
        this.nickname = nickname;
        this.year = year;
        this.month = month;
        this.day = day;
    }


    @Generated(hash = 1121472553)
    public ListItemBO() {
    }





    @Override
    public ListItemBO clone() {
        ListItemBO listItemBO = null;
        try {
            listItemBO = (ListItemBO) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return listItemBO;
    }


    @Override
    public String toString() {
        return "ListItemBO{" +
                " aId='" + aId + '\'' +
                ", count='" + count + '\'' +
                ", typeAmountCount=" + typeAmountCount +
                ", amountCount=" + amountCount +
                '}';
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getItemId() {
        return this.itemId;
    }


    public void setItemId(String itemId) {
        this.itemId = itemId;
    }


    public String getAId() {
        return this.aId;
    }


    public void setAId(String aId) {
        this.aId = aId;
    }


    public String getAType() {
        return this.aType;
    }


    public void setAType(String aType) {
        this.aType = aType;
    }


    public int getCType() {
        return this.cType;
    }


    public void setCType(int cType) {
        this.cType = cType;
    }


    public int getCId() {
        return this.cId;
    }


    public void setCId(int cId) {
        this.cId = cId;
    }


    public String getCName() {
        return this.cName;
    }


    public void setCName(String cName) {
        this.cName = cName;
    }


    public int getRId() {
        return this.rId;
    }


    public void setRId(int rId) {
        this.rId = rId;
    }


    public String getRName() {
        return this.rName;
    }


    public void setRName(String rName) {
        this.rName = rName;
    }


    public String getADate() {
        return this.aDate;
    }


    public void setADate(String aDate) {
        this.aDate = aDate;
    }


    public int getCtime() {
        return this.ctime;
    }


    public void setCtime(int ctime) {
        this.ctime = ctime;
    }


    public int getMtime() {
        return this.mtime;
    }


    public void setMtime(int mtime) {
        this.mtime = mtime;
    }



    public String getRemark() {
        return this.remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getIcon() {
        return this.icon;
    }


    public void setIcon(String icon) {
        this.icon = icon;
    }


    public String getColor() {
        return this.color;
    }


    public void setColor(String color) {
        this.color = color;
    }


    public String getUid() {
        return this.uid;
    }


    public void setUid(String uid) {
        this.uid = uid;
    }


    public String getNickname() {
        return this.nickname;
    }


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public String getYear() {
        return this.year;
    }


    public void setYear(String year) {
        this.year = year;
    }


    public String getMonth() {
        return this.month;
    }


    public void setMonth(String month) {
        this.month = month;
    }


    public String getDay() {
        return this.day;
    }


    public void setDay(String day) {
        this.day = day;
    }


    public float getAmount() {
        return this.amount;
    }


    public void setAmount(float amount) {
        this.amount = amount;
    }


}