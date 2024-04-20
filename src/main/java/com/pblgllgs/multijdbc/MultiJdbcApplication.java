package com.pblgllgs.multijdbc;

import com.pblgllgs.multijdbc.enums.Tier;
import com.pblgllgs.multijdbc.persistence.mysql.entity.Client;
import com.pblgllgs.multijdbc.persistence.mysql.repository.ClientRepository;
import com.pblgllgs.multijdbc.persistence.postgresql.entity.Log;
import com.pblgllgs.multijdbc.persistence.postgresql.repository.LogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
@Slf4j
public class MultiJdbcApplication implements CommandLineRunner {

    private final ClientRepository clientRepository;
    private final LogRepository logRepository;


    public MultiJdbcApplication(
            ClientRepository clientRepository,
            LogRepository logRepository) {
        this.clientRepository = clientRepository;
        this.logRepository = logRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(MultiJdbcApplication.class, args);
    }

    @Override
    @Transactional("mysqlTransactionManager")
    public void run(String... args) throws Exception {
        Client client = Client.builder()
                .email(UUID.randomUUID() + "@gmail.com")
                .name("user")
                .build();
        clientRepository.save(client);
        List<Client> allClients = clientRepository.findAll();
        allClients.forEach(System.out::println);

        log.info(String.valueOf((long) allClients.size()));

        Log logger = Log.builder()
                .tier(Tier.INFO.getValue())
                .message("This is a log message with id: "+ UUID.randomUUID())
                .build();

        logRepository.save(logger);

        List<Log> allLogs = logRepository.findAll();
        allLogs.forEach(System.out::println);

    }
}
