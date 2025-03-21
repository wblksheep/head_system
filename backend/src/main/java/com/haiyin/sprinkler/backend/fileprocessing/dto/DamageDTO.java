package com.haiyin.sprinkler.backend.fileprocessing.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
public class DamageDTO {
    private LocalDate damageDate;
    private String headSerial;
    private String history;
    private String note;
    private String damageType;
    private String realType;

    public LocalDate getDamageDate() {
        return this.damageDate;
    }

    public String getHeadSerial() {
        return this.headSerial;
    }

    public String getHistory() {
        return this.history;
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

    public void setDamageDate(LocalDate damageDate) {
        this.damageDate = damageDate;
    }

    public void setHeadSerial(String headSerial) {
        this.headSerial = headSerial;
    }

    public void setHistory(String history) {
        this.history = history;
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

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof DamageDTO)) return false;
        final DamageDTO other = (DamageDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$damageDate = this.getDamageDate();
        final Object other$damageDate = other.getDamageDate();
        if (this$damageDate == null ? other$damageDate != null : !this$damageDate.equals(other$damageDate))
            return false;
        final Object this$headSerial = this.getHeadSerial();
        final Object other$headSerial = other.getHeadSerial();
        if (this$headSerial == null ? other$headSerial != null : !this$headSerial.equals(other$headSerial))
            return false;
        final Object this$history = this.getHistory();
        final Object other$history = other.getHistory();
        if (this$history == null ? other$history != null : !this$history.equals(other$history)) return false;
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
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DamageDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $damageDate = this.getDamageDate();
        result = result * PRIME + ($damageDate == null ? 43 : $damageDate.hashCode());
        final Object $headSerial = this.getHeadSerial();
        result = result * PRIME + ($headSerial == null ? 43 : $headSerial.hashCode());
        final Object $history = this.getHistory();
        result = result * PRIME + ($history == null ? 43 : $history.hashCode());
        final Object $note = this.getNote();
        result = result * PRIME + ($note == null ? 43 : $note.hashCode());
        final Object $damageType = this.getDamageType();
        result = result * PRIME + ($damageType == null ? 43 : $damageType.hashCode());
        final Object $realType = this.getRealType();
        result = result * PRIME + ($realType == null ? 43 : $realType.hashCode());
        return result;
    }

    public String toString() {
        return "DamageDTO(damageDate=" + this.getDamageDate() + ", headSerial=" + this.getHeadSerial() + ", history=" + this.getHistory() + ", note=" + this.getNote() + ", damageType=" + this.getDamageType() + ", realType=" + this.getRealType() + ")";
    }
}
