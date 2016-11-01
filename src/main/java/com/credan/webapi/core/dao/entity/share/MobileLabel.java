package com.credan.webapi.core.dao.entity.share;

import java.util.Date;

public class MobileLabel {
    private String id;

    private String phone;

    private String isZhapian;

    private String province;

    private String city;

    private String sp;

    private String rptType;

    private String rptComment;

    private Long rptCnt;

    private String hy;

    private String countdesc;

    private String source;

    private String memo;

    private String delFlag;

    private Date createdTime;

    private Date lastUpdated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getIsZhapian() {
        return isZhapian;
    }

    public void setIsZhapian(String isZhapian) {
        this.isZhapian = isZhapian == null ? null : isZhapian.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getSp() {
        return sp;
    }

    public void setSp(String sp) {
        this.sp = sp == null ? null : sp.trim();
    }

    public String getRptType() {
        return rptType;
    }

    public void setRptType(String rptType) {
        this.rptType = rptType == null ? null : rptType.trim();
    }

    public String getRptComment() {
        return rptComment;
    }

    public void setRptComment(String rptComment) {
        this.rptComment = rptComment == null ? null : rptComment.trim();
    }

    public Long getRptCnt() {
        return rptCnt;
    }

    public void setRptCnt(Long rptCnt) {
        this.rptCnt = rptCnt;
    }

    public String getHy() {
        return hy;
    }

    public void setHy(String hy) {
        this.hy = hy == null ? null : hy.trim();
    }

    public String getCountdesc() {
        return countdesc;
    }

    public void setCountdesc(String countdesc) {
        this.countdesc = countdesc == null ? null : countdesc.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}