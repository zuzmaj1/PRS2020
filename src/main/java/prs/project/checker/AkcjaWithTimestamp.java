package prs.project.checker;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import prs.project.task.Akcja;

@SuperBuilder
@Getter
@Setter
public class AkcjaWithTimestamp extends Akcja {
    LocalDateTime timestamp;

    public static AkcjaWithTimestamp from(Akcja action, LocalDateTime now) {
        return AkcjaWithTimestamp.builder()
                .liczba(action.getLiczba())
                .Typ(action.getTyp())
                .product(action.getProduct())
                .cena(action.getCena())
                .grupaProduktów(action.getGrupaProduktów())
                .klientId(action.getKlientId())
                .timestamp(now)
                .build();
    }
}
