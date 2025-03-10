package com.haiyin.usingmysql.pojo;

import com.haiyin.usingmysql.dto.SprinklerStatus;
import com.haiyin.usingmysql.dto.SprinklerType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HeadInventory {

    private Integer id; // 自增主键

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

    private String color;
    private String position;

    private SprinklerStatus status;

    private SprinklerType type = SprinklerType.NEW;    // 默认值 OLD
    private Integer version = 0;     // 乐观锁默认值

    private LocalDateTime updateTime;

    public HeadInventory() {
    }

    public Integer getId() {
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

    public SprinklerStatus getStatus() {
        return this.status;
    }

    public SprinklerType getType() {
        return this.type;
    }

    public Integer getVersion() {
        return this.version;
    }

    public LocalDateTime getUpdateTime() {
        return this.updateTime;
    }

    public void setId(Integer id) {
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

    public void setStatus(SprinklerStatus status) {
        this.status = status;
    }

    public void setType(SprinklerType type) {
        this.type = type;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof HeadInventory)) return false;
        final HeadInventory other = (HeadInventory) o;
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
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
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
        return other instanceof HeadInventory;
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
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        final Object $version = this.getVersion();
        result = result * PRIME + ($version == null ? 43 : $version.hashCode());
        final Object $updateTime = this.getUpdateTime();
        result = result * PRIME + ($updateTime == null ? 43 : $updateTime.hashCode());
        return result;
    }

    public String toString() {
        return "HeadInventory(id=" + this.getId() + ", shippingDate=" + this.getShippingDate() + ", purchaseDate=" + this.getPurchaseDate() + ", contractNumber=" + this.getContractNumber() + ", headModel=" + this.getHeadModel() + ", headSerial=" + this.getHeadSerial() + ", warehouseDate=" + this.getWarehouseDate() + ", voltage=" + this.getVoltage() + ", jetsout=" + this.getJetsout() + ", usageDate=" + this.getUsageDate() + ", user=" + this.getUser() + ", usagePurpose=" + this.getUsagePurpose() + ", headHistory=" + this.getHeadHistory() + ", color=" + this.getColor() + ", position=" + this.getPosition() + ", status=" + this.getStatus() + ", type=" + this.getType() + ", version=" + this.getVersion() + ", updateTime=" + this.getUpdateTime() + ")";
    }
}
