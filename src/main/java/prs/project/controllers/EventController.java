package prs.project.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import prs.project.checker.AkcjaWithTimestamp;
import prs.project.checker.Ledger;
import prs.project.generator.Generator;
import prs.project.redis.queue.RedisMessagePublisherTask;
import prs.project.task.Akcja;

@AllArgsConstructor
@Controller
@RequestMapping("action")
public class EventController {

    Ledger ledger;
    RedisMessagePublisherTask redisMessagePublisher;

    @GetMapping(value = "/generate", consumes = "application/json")
    public ResponseEntity<String> generateActions() {
        Generator generator = new Generator(1000);
        List<Akcja> akcje = generator.generate();

        akcje.forEach(akcja -> redisMessagePublisher.publish(akcja));

        return new ResponseEntity<>("Generated", HttpStatus.OK);
    }

    @PostMapping(value = "/log", produces = "application/json")
    public ResponseEntity<Akcja> logAction(@RequestBody Akcja action) throws InterruptedException {
        if (!ledger.getLog().containsKey(action.getKlientId())) {

            ledger.getLog().put(action.getKlientId(), new ArrayList<>());

        } else {

            ArrayList<AkcjaWithTimestamp> list = ledger.getLog().get(action.getKlientId());
            list.add(AkcjaWithTimestamp.from(action, LocalDateTime.now(ZoneId.systemDefault())));

        }

        Thread.sleep(100);

        return new ResponseEntity<>(action, HttpStatus.OK);
    }

}
