package prs.project.checker;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import prs.project.task.Akcja;

@Service
@Getter
@Setter
@NoArgsConstructor
public class Ledger {

    ConcurrentHashMap<Long, ArrayList<AkcjaWithTimestamp>> log = new ConcurrentHashMap<>();

}
