package prs.project.task;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
public enum SterowanieAkcja {

    ZAMKNIJ_SKLEP;

    @JsonCreator
    public static SterowanieAkcja forValue(String value) {
        return SterowanieAkcja.valueOf(value);
    }

    @JsonValue
    public String toValue() {
        return this.toString();
    }
}
