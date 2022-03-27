package prs.project.task;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

@Getter
public enum ZamowieniaAkcje {

    POJEDYNCZE_ZAMOWIENIE ,
    GRUPOWE_ZAMOWIENIE ,
    ODBIÓR_REZERWACJI,
    REZERWACJA;

    @JsonCreator
    public static ZamowieniaAkcje forValue(String value) {
        return ZamowieniaAkcje.valueOf(value);
    }

    @JsonValue
    public String toValue() {
        return this.toString();
    }

}

