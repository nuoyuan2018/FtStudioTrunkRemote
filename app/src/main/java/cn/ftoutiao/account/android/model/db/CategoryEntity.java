package cn.ftoutiao.account.android.model.db;

import com.acmenxd.retrofit.HttpDataEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;

import java.io.Serializable;

@Entity(nameInDb = "tbl_category")
public class CategoryEntity extends HttpDataEntity implements Serializable, Cloneable {
    static final long serialVersionUID = 42L;
    /**
     * cId : 10001
     * rId : 10001
     * cName : 工资
     * rName : 工资
     * aId :
     * aType : 1
     * cType : 1
     * color : #f96262
     * icon : income_icon_10001.png
     */


    @Id
    private Long id;
    public String uid;
    public int cId;
    @Unique
    public int rId;
    public String cName;
    public String rName;
    public String aId;
    public int cType;
    public String color;
    public String icon;
    @Transient
    public String itemId;
    @Transient
    public int isSelect = 0; //是否选择当前item 自定义变量 0 moren


    @Generated(hash = 214281210)
    public CategoryEntity(Long id, String uid, int cId, int rId, String cName, String rName,
                          String aId, int cType, String color, String icon) {
        this.id = id;
        this.uid = uid;
        this.cId = cId;
        this.rId = rId;
        this.cName = cName;
        this.rName = rName;
        this.aId = aId;
        this.cType = cType;
        this.color = color;
        this.icon = icon;
    }


    @Generated(hash = 725894750)
    public CategoryEntity() {
    }


    @Override
    public CategoryEntity clone() {

        CategoryEntity categoryEntity = null;
        try {
            categoryEntity = (CategoryEntity) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return categoryEntity == null ? new CategoryEntity() : categoryEntity;
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getUid() {
        return this.uid;
    }


    public void setUid(String uid) {
        this.uid = uid;
    }


    public int getCId() {
        return this.cId;
    }


    public void setCId(int cId) {
        this.cId = cId;
    }


    public int getRId() {
        return this.rId;
    }


    public void setRId(int rId) {
        this.rId = rId;
    }


    public String getCName() {
        return this.cName;
    }


    public void setCName(String cName) {
        this.cName = cName;
    }


    public String getRName() {
        return this.rName;
    }


    public void setRName(String rName) {
        this.rName = rName;
    }


    public String getAId() {
        return this.aId;
    }


    public void setAId(String aId) {
        this.aId = aId;
    }


    public int getCType() {
        return this.cType;
    }


    public void setCType(int cType) {
        this.cType = cType;
    }


    public String getColor() {
        return this.color;
    }


    public void setColor(String color) {
        this.color = color;
    }


    public String getIcon() {
        return this.icon;
    }


    public void setIcon(String icon) {
        this.icon = icon;
    }


}