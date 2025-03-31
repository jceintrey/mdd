package com.jerem.mdd.configuration;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jerem.mdd.dto.SubscriptionDto;
import com.jerem.mdd.mapper.SubscriptionMapper;
import com.jerem.mdd.model.Subscription;
import com.jerem.mdd.model.Topic;
import com.jerem.mdd.model.User;
import com.jerem.mdd.repository.SubscriptionRepository;
import com.jerem.mdd.repository.TopicRepository;
import com.jerem.mdd.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class DataInitializer implements CommandLineRunner {


    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final PasswordEncoder passwordEncoder;
    private final SubscriptionMapper subscriptionMapper;
    private final SubscriptionRepository subscriptionRepository;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder,
            TopicRepository topicRepository, SubscriptionMapper subscriptionMapper,
            SubscriptionRepository subscriptionRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.topicRepository = topicRepository;
        this.subscriptionMapper = subscriptionMapper;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.debug("init sample data");
        initUsers();
        initTopics();
        initSubscriptions();


    }

    private void initUsers() throws Exception {
        if (userRepository.count() == 0) {
            List<User> sampleUsers = loadUsersFromFile("data/users.json");
            userRepository.saveAll(sampleUsers);
            log.debug("successfully added " + sampleUsers.size() + " sample users");
        }
    }

    private void initTopics() throws Exception {
        List<Topic> sampleTopics = loadTopicsFromFile("data/topics.json");
        topicRepository.saveAll(sampleTopics);
        log.debug("successfully added " + sampleTopics.size() + " sample topics");
    }

    private void initSubscriptions() throws Exception {
        List<Subscription> sampleSubscriptions =
                loadSubscriptionsFromFile("data/subscriptions.json");
        subscriptionRepository.saveAll(sampleSubscriptions);
        log.debug("successfully added " + sampleSubscriptions.size() + " sample subscriptions");
    }



    private List<Topic> loadTopicsFromFile(String filePath)
            throws StreamReadException, DatabindException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource(filePath);
        List<Topic> topics = objectMapper.readValue(resource.getInputStream(),
                new TypeReference<List<Topic>>() {});

        return topics;
    }


    private List<Subscription> loadSubscriptionsFromFile(String filePath)
            throws StreamReadException, DatabindException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource(filePath);
        List<SubscriptionDto> subscriptionsDtos = objectMapper.readValue(resource.getInputStream(),
                new TypeReference<List<SubscriptionDto>>() {});

        List<Subscription> subscriptions = subscriptionsDtos.stream()
                .map(subscriptionMapper::toEntity).collect(Collectors.toList());

        return subscriptions;


    }

    // private List<Topic> loadTopicsFromFile(String filePath)
    // throws StreamReadException, DatabindException, IOException {
    // ObjectMapper objectMapper = new ObjectMapper();
    // ClassPathResource resource = new ClassPathResource(filePath);
    // List<TopicDto> topicDtos = objectMapper.readValue(resource.getInputStream(),
    // new TypeReference<List<TopicDto>>() {});

    // List<Topic> topics =
    // topicDtos.stream().map(topicMapper::toEntity).collect(Collectors.toList());
    // return topics;


    // }
    private List<User> loadUsersFromFile(String filePath)
            throws StreamReadException, DatabindException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource(filePath);
        List<User> users = objectMapper.readValue(resource.getInputStream(),
                new TypeReference<List<User>>() {});


        users.forEach((user) -> {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        });

        return users;

    }


}
