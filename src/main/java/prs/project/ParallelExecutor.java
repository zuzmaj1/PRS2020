package prs.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import prs.project.controllers.Settings;
import prs.project.model.Warehouse;
import prs.project.task.Akcja;
import prs.project.task.Wycena;
import prs.project.task.Wydarzenia;
import prs.project.task.Zamowienia;
import prs.project.task.Zaopatrzenie;

import com.fasterxml.jackson.databind.json.JsonMapper;

@Service
@Slf4j
public class ParallelExecutor {

    Settings settings;
    List<Akcja> akcje = new ArrayList<>();
    boolean active = true;
    Set<Enum> mojeTypy = new HashSet<>();
    ConcurrentLinkedDeque<Akcja> kolejka = new ConcurrentLinkedDeque();
    Warehouse magazyn = new Warehouse();

    public ParallelExecutor(Settings settings, List<Akcja> akcje) {
        this.settings = settings;
        this.akcje = akcje;
        mojeTypy.addAll(Wycena.valueOf(settings.getWycena()).getAkceptowane());
        mojeTypy.addAll(Zamowienia.valueOf(settings.getZamowienia()).getAkceptowane());
        mojeTypy.addAll(Zaopatrzenie.valueOf(settings.getZaopatrzenie()).getAkceptowane());
        mojeTypy.addAll(Wydarzenia.valueOf(settings.getWydarzenia()).getAkceptowane());
        Thread thread = new Thread(() ->
        {
            while (active) {
                threadProcess();
            }
        });
        thread.start();
        Thread thread2 = new Thread(() ->
        {
            while (active) {
                threadProcess();
            }
        });
        thread2.start();
        Thread thread3 = new Thread(() ->
        {
            while (active) {
                threadProcess();
            }
        });
        thread3.start();

    }

    public void process(Akcja jednaAkcja) {
        synchronized (this) {
            Stream.of(jednaAkcja)
                    .filter(akcja -> mojeTypy.contains(akcja.getTyp()))
                    .forEach(akcja -> {
                        kolejka.add(akcja);
                    });
            notifyAll();
        }
    }

    public void threadProcess() {
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!kolejka.isEmpty()) {
                Akcja akcja = kolejka.pollFirst();
                procesujAkcje(akcja);
                try {
                    zalogujAkcje(akcja);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void procesujAkcje(Akcja akcja) {
        log.info("procesuje " + akcja.getTyp());
    }

    public void zalogujAkcje(Akcja akcja) throws IOException {
        akcja.setKlientId(settings.getNumerIndeksu());
        HttpPost post = new HttpPost("http://localhost:8080/action/log");

        JsonMapper mapper = new JsonMapper();
        String json = mapper.writeValueAsString(akcja);
        StringEntity entity = new StringEntity(json);
        post.setEntity(entity);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(post)) {

            HttpEntity rEntity = response.getEntity();
            if (rEntity != null) {
                // return it as a String
                String result = EntityUtils.toString(rEntity);
                log.info(result);
            }
        }
    }

}
