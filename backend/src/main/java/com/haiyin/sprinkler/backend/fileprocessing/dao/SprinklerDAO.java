package com.haiyin.sprinkler.backend.fileprocessing.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_sprinklers")
public class SprinklerDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 自增主键

    private LocalDate shippingDate;//发货日期

    private LocalDate purchaseDate; // 购入日期

    private String contractNumber; // 合同编号

    private String headModel; // 喷头型号

    private String headSerial; // 喷头序列号，唯一且不能为空

    private LocalDate warehouseDate; // 入仓日期

    private Float voltage;//电压

    private Integer jetsout;//jetsout

    private LocalDate usageDate; // 领用日期

    private String user; // 领用人

    private String usagePurpose; // 领用用途

    private String headHistory; // 喷头历史

    private String color;// 颜色
    private String position;// 位置

    private LocalDate damageDate;

    private String note;
    private String damageType;
    private String realType;

    @Enumerated(EnumType.ORDINAL)
    @Column(columnDefinition = "TINYINT NOT NULL")
    private HeadStatus status; //当前状态

    private Integer version = 0;     // 乐观锁默认值

    private LocalDateTime updateTime;

    public SprinklerDAO() {
    }

    public Long getId() {
        return this.id;
    }

    public LocalDate getShippingDate() {
        return this.shippingDate;
    }

    public LocalDate getPurchaseDate() {
        return this.purchaseDate;
    }

    public String getContractNumber() {
        return this.contractNumber;
    }

    public String getHeadModel() {
        return this.headModel;
    }

    public String getHeadSerial() {
        return this.headSerial;
    }

    public LocalDate getWarehouseDate() {
        return this.warehouseDate;
    }

    public Float getVoltage() {
        return this.voltage;
    }

    public Integer getJetsout() {
        return this.jetsout;
    }

    public LocalDate getUsageDate() {
        return this.usageDate;
    }

    public String getUser() {
        return this.user;
    }

    public String getUsagePurpose() {
        return this.usagePurpose;
    }

    public String getHeadHistory() {
        return this.headHistory;
    }

    public String getColor() {
        return this.color;
    }

    public String getPosition() {
        return this.position;
    }

    public LocalDate getDamageDate() {
        return this.damageDate;
    }

    public String getNote() {
        return this.note;
    }

    public String getDamageType() {
        return this.damageType;
    }

    public String getRealType() {
        return this.realType;
    }

    public HeadStatus getStatus() {
        return this.status;
    }

    public Integer getVersion() {
        return this.version;
    }

    public LocalDateTime getUpdateTime() {
        return this.updateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setShippingDate(LocalDate shippingDate) {
        this.shippingDate = shippingDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public void setHeadModel(String headModel) {
        this.headModel = headModel;
    }

    public void setHeadSerial(String headSerial) {
        this.headSerial = headSerial;
    }

    public void setWarehouseDate(LocalDate warehouseDate) {
        this.warehouseDate = warehouseDate;
    }

    public void setVoltage(Float voltage) {
        this.voltage = voltage;
    }

    public void setJetsout(Integer jetsout) {
        this.jetsout = jetsout;
    }

    public void setUsageDate(LocalDate usageDate) {
        this.usageDate = usageDate;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setUsagePurpose(String usagePurpose) {
        this.usagePurpose = usagePurpose;
    }

    public void setHeadHistory(String headHistory) {
        this.headHistory = headHistory;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setDamageDate(LocalDate damageDate) {
        this.damageDate = damageDate;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setDamageType(String damageType) {
        this.damageType = damageType;
    }

    public void setRealType(String realType) {
        this.realType = realType;
    }

    public void setStatus(HeadStatus status) {
        this.status = status;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SprinklerDAO)) return false;
        final SprinklerDAO other = (SprinklerDAO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$shippingDate = this.getShippingDate();
        final Object other$shippingDate = other.getShippingDate();
        if (this$shippingDate == null ? other$shippingDate != null : !this$shippingDate.equals(other$shippingDate))
            return false;
        final Object this$purchaseDate = this.getPurchaseDate();
        final Object other$purchaseDate = other.getPurchaseDate();
        if (this$purchaseDate == null ? other$purchaseDate != null : !this$purchaseDate.equals(other$purchaseDate))
            return false;
        final Object this$contractNumber = this.getContractNumber();
        final Object other$contractNumber = other.getContractNumber();
        if (this$contractNumber == null ? other$contractNumber != null : !this$contractNumber.equals(other$contractNumber))
            return false;
        final Object this$headModel = this.getHeadModel();
        final Object other$headModel = other.getHeadModel();
        if (this$headModel == null ? other$headModel != null : !this$headModel.equals(other$headModel)) return false;
        final Object this$headSerial = this.getHeadSerial();
        final Object other$headSerial = other.getHeadSerial();
        if (this$headSerial == null ? other$headSerial != null : !this$headSerial.equals(other$headSerial))
            return false;
        final Object this$warehouseDate = this.getWarehouseDate();
        final Object other$warehouseDate = other.getWarehouseDate();
        if (this$warehouseDate == null ? other$warehouseDate != null : !this$warehouseDate.equals(other$warehouseDate))
            return false;
        final Object this$voltage = this.getVoltage();
        final Object other$voltage = other.getVoltage();
        if (this$voltage == null ? other$voltage != null : !this$voltage.equals(other$voltage)) return false;
        final Object this$jetsout = this.getJetsout();
        final Object other$jetsout = other.getJetsout();
        if (this$jetsout == null ? other$jetsout != null : !this$jetsout.equals(other$jetsout)) return false;
        final Object this$usageDate = this.getUsageDate();
        final Object other$usageDate = other.getUsageDate();
        if (this$usageDate == null ? other$usageDate != null : !this$usageDate.equals(other$usageDate)) return false;
        final Object this$user = this.getUser();
        final Object other$user = other.getUser();
        if (this$user == null ? other$user != null : !this$user.equals(other$user)) return false;
        final Object this$usagePurpose = this.getUsagePurpose();
        final Object other$usagePurpose = other.getUsagePurpose();
        if (this$usagePurpose == null ? other$usagePurpose != null : !this$usagePurpose.equals(other$usagePurpose))
            return false;
        final Object this$headHistory = this.getHeadHistory();
        final Object other$headHistory = other.getHeadHistory();
        if (this$headHistory == null ? other$headHistory != null : !this$headHistory.equals(other$headHistory))
            return false;
        final Object this$color = this.getColor();
        final Object other$color = other.getColor();
        if (this$color == null ? other$color != null : !this$color.equals(other$color)) return false;
        final Object this$position = this.getPosition();
        final Object other$position = other.getPosition();
        if (this$position == null ? other$position != null : !this$position.equals(other$position)) return false;
        final Object this$damageDate = this.getDamageDate();
        final Object other$damageDate = other.getDamageDate();
        if (this$damageDate == null ? other$damageDate != null : !this$damageDate.equals(other$damageDate))
            return false;
        final Object this$note = this.getNote();
        final Object other$note = other.getNote();
        if (this$note == null ? other$note != null : !this$note.equals(other$note)) return false;
        final Object this$damageType = this.getDamageType();
        final Object other$damageType = other.getDamageType();
        if (this$damageType == null ? other$damageType != null : !this$damageType.equals(other$damageType))
            return false;
        final Object this$realType = this.getRealType();
        final Object other$realType = other.getRealType();
        if (this$realType == null ? other$realType != null : !this$realType.equals(other$realType)) return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final Object this$version = this.getVersion();
        final Object other$version = other.getVersion();
        if (this$version == null ? other$version != null : !this$version.equals(other$version)) return false;
        final Object this$updateTime = this.getUpdateTime();
        final Object other$updateTime = other.getUpdateTime();
        if (this$updateTime == null ? other$updateTime != null : !this$updateTime.equals(other$updateTime))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SprinklerDAO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $shippingDate = this.getShippingDate();
        result = result * PRIME + ($shippingDate == null ? 43 : $shippingDate.hashCode());
        final Object $purchaseDate = this.getPurchaseDate();
        result = result * PRIME + ($purchaseDate == null ? 43 : $purchaseDate.hashCode());
        final Object $contractNumber = this.getContractNumber();
        result = result * PRIME + ($contractNumber == null ? 43 : $contractNumber.hashCode());
        final Object $headModel = this.getHeadModel();
        result = result * PRIME + ($headModel == null ? 43 : $headModel.hashCode());
        final Object $headSerial = this.getHeadSerial();
        result = result * PRIME + ($headSerial == null ? 43 : $headSerial.hashCode());
        final Object $warehouseDate = this.getWarehouseDate();
        result = result * PRIME + ($warehouseDate == null ? 43 : $warehouseDate.hashCode());
        final Object $voltage = this.getVoltage();
        result = result * PRIME + ($voltage == null ? 43 : $voltage.hashCode());
        final Object $jetsout = this.getJetsout();
        result = result * PRIME + ($jetsout == null ? 43 : $jetsout.hashCode());
        final Object $usageDate = this.getUsageDate();
        result = result * PRIME + ($usageDate == null ? 43 : $usageDate.hashCode());
        final Object $user = this.getUser();
        result = result * PRIME + ($user == null ? 43 : $user.hashCode());
        final Object $usagePurpose = this.getUsagePurpose();
        result = result * PRIME + ($usagePurpose == null ? 43 : $usagePurpose.hashCode());
        final Object $headHistory = this.getHeadHistory();
        result = result * PRIME + ($headHistory == null ? 43 : $headHistory.hashCode());
        final Object $color = this.getColor();
        result = result * PRIME + ($color == null ? 43 : $color.hashCode());
        final Object $position = this.getPosition();
        result = result * PRIME + ($position == null ? 43 : $position.hashCode());
        final Object $damageDate = this.getDamageDate();
        result = result * PRIME + ($damageDate == null ? 43 : $damageDate.hashCode());
        final Object $note = this.getNote();
        result = result * PRIME + ($note == null ? 43 : $note.hashCode());
        final Object $damageType = this.getDamageType();
        result = result * PRIME + ($damageType == null ? 43 : $damageType.hashCode());
        final Object $realType = this.getRealType();
        result = result * PRIME + ($realType == null ? 43 : $realType.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final Object $version = this.getVersion();
        result = result * PRIME + ($version == null ? 43 : $version.hashCode());
        final Object $updateTime = this.getUpdateTime();
        result = result * PRIME + ($updateTime == null ? 43 : $updateTime.hashCode());
        return result;
    }

    public String toString() {
        return "SprinklerDAO(id=" + this.getId() + ", shippingDate=" + this.getShippingDate() + ", purchaseDate=" + this.getPurchaseDate() + ", contractNumber=" + this.getContractNumber() + ", headModel=" + this.getHeadModel() + ", headSerial=" + this.getHeadSerial() + ", warehouseDate=" + this.getWarehouseDate() + ", voltage=" + this.getVoltage() + ", jetsout=" + this.getJetsout() + ", usageDate=" + this.getUsageDate() + ", user=" + this.getUser() + ", usagePurpose=" + this.getUsagePurpose() + ", headHistory=" + this.getHeadHistory() + ", color=" + this.getColor() + ", position=" + this.getPosition() + ", damageDate=" + this.getDamageDate() + ", note=" + this.getNote() + ", damageType=" + this.getDamageType() + ", realType=" + this.getRealType() + ", status=" + this.getStatus() + ", version=" + this.getVersion() + ", updateTime=" + this.getUpdateTime() + ")";
    }
}
