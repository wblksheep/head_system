package com.haiyin.usingmysql.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
public class SprinklerAllocationDTO {
    @NotBlank(message = "喷头编号不能为空")
    private String sprinklerNo;

    @NotBlank(message = "领用人不能为空")
    private String owner;

    @NotBlank(message = "机台名称不能为空")
    private String machine;

    @Pattern(regexp = "[A-Z]", message = "颜色分类格式错误")
    private String color;

    @Pattern(regexp = "\\d+", message = "位置格式错误")
    private String position;

    private LocalDate usageDate;

    @NotNull(message = "喷头类型不能为空")
    private SprinklerType type;

    private String history;


    public @NotBlank(message = "喷头编号不能为空") String getSprinklerNo() {
        return this.sprinklerNo;
    }

    public @NotBlank(message = "领用人不能为空") String getOwner() {
        return this.owner;
    }

    public @NotBlank(message = "机台名称不能为空") String getMachine() {
        return this.machine;
    }

    public @Pattern(regexp = "[A-Z]", message = "颜色分类格式错误") String getColor() {
        return this.color;
    }

    public @Pattern(regexp = "\\d+", message = "位置格式错误") String getPosition() {
        return this.position;
    }

    public LocalDate getUsageDate() {
        return this.usageDate;
    }

    public @NotNull(message = "喷头类型不能为空") SprinklerType getType() {
        return this.type;
    }

    public String getHistory() {
        return this.history;
    }

    public void setSprinklerNo(@NotBlank(message = "喷头编号不能为空") String sprinklerNo) {
        this.sprinklerNo = sprinklerNo;
    }

    public void setOwner(@NotBlank(message = "领用人不能为空") String owner) {
        this.owner = owner;
    }

    public void setMachine(@NotBlank(message = "机台名称不能为空") String machine) {
        this.machine = machine;
    }

    public void setColor(@Pattern(regexp = "[A-Z]", message = "颜色分类格式错误") String color) {
        this.color = color;
    }

    public void setPosition(@Pattern(regexp = "\\d+", message = "位置格式错误") String position) {
        this.position = position;
    }

    public void setUsageDate(LocalDate usageDate) {
        this.usageDate = usageDate;
    }

    public void setType(@NotNull(message = "喷头类型不能为空") SprinklerType type) {
        this.type = type;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SprinklerAllocationDTO)) return false;
        final SprinklerAllocationDTO other = (SprinklerAllocationDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$sprinklerNo = this.getSprinklerNo();
        final Object other$sprinklerNo = other.getSprinklerNo();
        if (this$sprinklerNo == null ? other$sprinklerNo != null : !this$sprinklerNo.equals(other$sprinklerNo))
            return false;
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
        final Object this$usageDate = this.getUsageDate();
        final Object other$usageDate = other.getUsageDate();
        if (this$usageDate == null ? other$usageDate != null : !this$usageDate.equals(other$usageDate)) return false;
        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        final Object this$history = this.getHistory();
        final Object other$history = other.getHistory();
        if (this$history == null ? other$history != null : !this$history.equals(other$history)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SprinklerAllocationDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $sprinklerNo = this.getSprinklerNo();
        result = result * PRIME + ($sprinklerNo == null ? 43 : $sprinklerNo.hashCode());
        final Object $owner = this.getOwner();
        result = result * PRIME + ($owner == null ? 43 : $owner.hashCode());
        final Object $machine = this.getMachine();
        result = result * PRIME + ($machine == null ? 43 : $machine.hashCode());
        final Object $color = this.getColor();
        result = result * PRIME + ($color == null ? 43 : $color.hashCode());
        final Object $position = this.getPosition();
        result = result * PRIME + ($position == null ? 43 : $position.hashCode());
        final Object $usageDate = this.getUsageDate();
        result = result * PRIME + ($usageDate == null ? 43 : $usageDate.hashCode());
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        final Object $history = this.getHistory();
        result = result * PRIME + ($history == null ? 43 : $history.hashCode());
        return result;
    }

    public String toString() {
        return "SprinklerAllocationDTO(sprinklerNo=" + this.getSprinklerNo() + ", owner=" + this.getOwner() + ", machine=" + this.getMachine() + ", color=" + this.getColor() + ", position=" + this.getPosition() + ", usageDate=" + this.getUsageDate() + ", type=" + this.getType() + ", history=" + this.getHistory() + ")";
    }
}


