package cn.ftoutiao.account.android.model.db;

import com.acmenxd.retrofit.HttpDataEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;

import java.io.Serializable;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

@Entity(nameInDb = "tbl_seq_category")
public class ATypeListEntity extends HttpDataEntity implements Serializable {
    static final long serialVersionUID = 42L;
    /**
     * aid : 1
     * incomeSeq1 : 12001,12002,12003
     * incomeSeq2 : 12004
     * outgoSeq1 : 22001,22002,22003,22004,22005,22006,22007,22008,22009,22010,22011,22012,22013
     * outgoSeq2 : 22014,22015
     */
    @Id
    private Long id;
    @Unique
    public String aId; //账本id
    public String uid; //用户id


    public String incomeSeq1;
    public String incomeSeq2;
    public String outgoSeq1;
    public String outgoSeq2;

    @Transient
    public List<CategoryEntity> category = new ArrayList<>();
    @Generated(hash = 1708992934)
    public ATypeListEntity(Long id, String aId, String uid, String incomeSeq1, String incomeSeq2, String outgoSeq1, String outgoSeq2) {
        this.id = id;
        this.aId = aId;
        this.uid = uid;
        this.incomeSeq1 = incomeSeq1;
        this.incomeSeq2 = incomeSeq2;
        this.outgoSeq1 = outgoSeq1;
        this.outgoSeq2 = outgoSeq2;
    }
    @Generated(hash = 2020946591)
    public ATypeListEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAId() {
        return this.aId;
    }
    public void setAId(String aId) {
        this.aId = aId;
    }
    public String getUid() {
        return this.uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getIncomeSeq1() {
        return this.incomeSeq1;
    }
    public void setIncomeSeq1(String incomeSeq1) {
        this.incomeSeq1 = incomeSeq1;
    }
    public String getIncomeSeq2() {
        return this.incomeSeq2;
    }
    public void setIncomeSeq2(String incomeSeq2) {
        this.incomeSeq2 = incomeSeq2;
    }
    public String getOutgoSeq1() {
        return this.outgoSeq1;
    }
    public void setOutgoSeq1(String outgoSeq1) {
        this.outgoSeq1 = outgoSeq1;
    }
    public String getOutgoSeq2() {
        return this.outgoSeq2;
    }
    public void setOutgoSeq2(String outgoSeq2) {
        this.outgoSeq2 = outgoSeq2;
    }

    

}