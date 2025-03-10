package com.haiyin.usingmysql.pojo;

import com.haiyin.usingmysql.dto.SprinklerStatus;
import com.haiyin.usingmysql.dto.SprinklerType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
public class Sprinkler {
    private Long id;
    private String sprinklerNo;      // 喷头编号（与 sprinkler_no 自动映射）
    private SprinklerStatus status;          // 状态枚举
    private String owner;
    private String machine;
    private String color;
    private String position;
    private SprinklerType type = SprinklerType.OLD;    // 默认值 OLD
    private Integer version = 0;     // 乐观锁默认值
    private LocalDateTime updateTime;

    public Long getId() {
        return this.id;
    }

    public String getSprinklerNo() {
        return this.sprinklerNo;
    }

    public SprinklerStatus getStatus() {
        return this.status;
    }

    public String getOwner() {
        return this.owner;
    }

    public String getMachine() {
        return this.machine;
    }

    public String getColor() {
        return this.color;
    }

    public String getPosition() {
        return this.position;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setSprinklerNo(String sprinklerNo) {
        this.sprinklerNo = sprinklerNo;
    }

    public void setStatus(SprinklerStatus status) {
        this.status = status;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPosition(String position) {
        this.position = position;
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
        if (!(o instanceof Sprinkler)) return false;
        final Sprinkler other = (Sprinkler) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$sprinklerNo = this.getSprinklerNo();
        final Object other$sprinklerNo = other.getSprinklerNo();
        if (this$sprinklerNo == null ? other$sprinklerNo != null : !this$sprinklerNo.equals(other$sprinklerNo))
            return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final Object this$owner = this.getOwner();
        final Object other$owner = other.getOwner();
        if (this$owner == null ? other$owner != null : !this$owner.equals(other$owner)) return false;
        final Object this$machine = this.getMachine();
        final Object other$machine = other.getMachine();
        if (this$machine == null ? other$machine != null : !this$machine.equals(other$machine)) return false;
        final Object this$color = this.getColor();
        final Object other$color = other.getColor();
        if (this$color == null ? other$color != null : !this$color.equals(other$color)) return false;
        final Object this$position = this.getPosition();
        final Object other$position = other.getPosition();
        if (this$position == null ? other$position != null : !this$position.equals(other$position)) return false;
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
        return other instanceof Sprinkler;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $sprinklerNo = this.getSprinklerNo();
        result = result * PRIME + ($sprinklerNo == null ? 43 : $sprinklerNo.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final Object $owner = this.getOwner();
        result = result * PRIME + ($owner == null ? 43 : $owner.hashCode());
        final Object $machine = this.getMachine();
        result = result * PRIME + ($machine == null ? 43 : $machine.hashCode());
        final Object $color = this.getColor();
        result = result * PRIME + ($color == null ? 43 : $color.hashCode());
        final Object $position = this.getPosition();
        result = result * PRIME + ($position == null ? 43 : $position.hashCode());
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        final Object $version = this.getVersion();
        result = result * PRIME + ($version == null ? 43 : $version.hashCode());
        final Object $updateTime = this.getUpdateTime();
        result = result * PRIME + ($updateTime == null ? 43 : $updateTime.hashCode());
        return result;
    }

    public String toString() {
        return "Sprinkler(id=" + this.getId() + ", sprinklerNo=" + this.getSprinklerNo() + ", status=" + this.getStatus() + ", owner=" + this.getOwner() + ", machine=" + this.getMachine() + ", color=" + this.getColor() + ", position=" + this.getPosition() + ", type=" + this.getType() + ", version=" + this.getVersion() + ", updateTime=" + this.getUpdateTime() + ")";
    }
}
