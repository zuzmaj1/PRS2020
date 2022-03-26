package prs.project.task;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

@Getter
public enum WycenaAkcje {

    ZMIEN_CENE,
    PODAJ_CENE;

    @JsonCreator
    public static WycenaAkcje forValue(String value) {
        return WycenaAkcje.valueOf(value);
    }

    @JsonValue
    public String toValue() {
        return this.toString();
    }

}
