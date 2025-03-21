package com.haiyin.sprinkler.backend.fileprocessing.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
public class AllocateDTO {
    private String headSerial;

    private String user;

    private String usagePurpose;

    private String color;

    private String position;

    private LocalDate usageDate;

    private String history;

    public String getHeadSerial() {
        return this.headSerial;
    }

    public String getUser() {
        return this.user;
    }

    public String getUsagePurpose() {
        return this.usagePurpose;
    }

    public String getColor() {
        return this.color;
    }

    public String getPosition() {
        return this.position;
    }

    public LocalDate getUsageDate() {
        return this.usageDate;
    }

    public String getHistory() {
        return this.history;
    }

    public void setHeadSerial(String headSerial) {
        this.headSerial = headSerial;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setUsagePurpose(String usagePurpose) {
        this.usagePurpose = usagePurpose;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setUsageDate(LocalDate usageDate) {
        this.usageDate = usageDate;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof AllocateDTO)) return false;
        final AllocateDTO other = (AllocateDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$headSerial = this.getHeadSerial();
        final Object other$headSerial = other.getHeadSerial();
        if (this$headSerial == null ? other$headSerial != null : !this$headSerial.equals(other$headSerial))
            return false;
        final Object this$user = this.getUser();
        final Object other$user = other.getUser();
        if (this$user == null ? other$user != null : !this$user.equals(other$user)) return false;
        final Object this$usagePurpose = this.getUsagePurpose();
        final Object other$usagePurpose = other.getUsagePurpose();
        if (this$usagePurpose == null ? other$usagePurpose != null : !this$usagePurpose.equals(other$usagePurpose))
            return false;
        final Object this$color = this.getColor();
        final Object other$color = other.getColor();
        if (this$color == null ? other$color != null : !this$color.equals(other$color)) return false;
        final Object this$position = this.getPosition();
        final Object other$position = other.getPosition();
        if (this$position == null ? other$position != null : !this$position.equals(other$position)) return false;
        final Object this$usageDate = this.getUsageDate();
        final Object other$usageDate = other.getUsageDate();
        if (this$usageDate == null ? other$usageDate != null : !this$usageDate.equals(other$usageDate)) return false;
        final Object this$history = this.getHistory();
        final Object other$history = other.getHistory();
        if (this$history == null ? other$history != null : !this$history.equals(other$history)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AllocateDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $headSerial = this.getHeadSerial();
        result = result * PRIME + ($headSerial == null ? 43 : $headSerial.hashCode());
        final Object $user = this.getUser();
        result = result * PRIME + ($user == null ? 43 : $user.hashCode());
        final Object $usagePurpose = this.getUsagePurpose();
        result = result * PRIME + ($usagePurpose == null ? 43 : $usagePurpose.hashCode());
        final Object $color = this.getColor();
        result = result * PRIME + ($color == null ? 43 : $color.hashCode());
        final Object $position = this.getPosition();
        result = result * PRIME + ($position == null ? 43 : $position.hashCode());
        final Object $usageDate = this.getUsageDate();
        result = result * PRIME + ($usageDate == null ? 43 : $usageDate.hashCode());
        final Object $history = this.getHistory();
        result = result * PRIME + ($history == null ? 43 : $history.hashCode());
        return result;
    }

    public String toString() {
        return "AllocateDTO(headSerial=" + this.getHeadSerial() + ", user=" + this.getUser() + ", usagePurpose=" + this.getUsagePurpose() + ", color=" + this.getColor() + ", position=" + this.getPosition() + ", usageDate=" + this.getUsageDate() + ", history=" + this.getHistory() + ")";
    }
}
