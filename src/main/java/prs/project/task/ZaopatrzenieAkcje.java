package prs.project.task;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@Getter
public enum ZaopatrzenieAkcje {

    POJEDYNCZE_ZAOPATRZENIE,
    GRUPOWE_ZAOPATRZENIE;

    @JsonCreator
    public static ZaopatrzenieAkcje forValue(String value) {
        return ZaopatrzenieAkcje.valueOf(value);
    }

    @JsonValue
    public String toValue() {
        return this.toString();
    }


}
