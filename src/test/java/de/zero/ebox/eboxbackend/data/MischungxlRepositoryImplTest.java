package de.zero.ebox.eboxbackend.data;

import de.zero.ebox.eboxbackend.model.JsonSetNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MischungxlRepositoryImplTest {

    @Autowired
    private MischungxlRepository repository;

    @Test
    void testSearch() {
        assertNotNull(repository);
        Mono<JsonSetNode> mischungxlNodes = repository.getMischungxlNode(93103);
        JsonSetNode set = mischungxlNodes.block();
        assertEquals(93103, set.getNid());
        assertEquals("soundexile - live @ viva warriors (mandarine park buenos aires)", set.getTitle());
        assertEquals(Instant.ofEpochSecond(1431769228), set.getCreated());
    }


    @Test
    void getNode() {
        repository.getMischungxlNode(93103);
    }


    @Test
    void update() {
        int status = repository.getMischungxlNode(93103).block().getStatus();
        repository.updateStatus(93103, 1 - status)
              .as(StepVerifier::create)
              .expectNext(true)
              .verifyComplete();
        repository.getMischungxlNode(93103)
              .as(StepVerifier::create)
              .expectNextMatches((set) -> set.getStatus() == (1-status))
              .verifyComplete();
        repository.updateStatus(93103, status)
              .as(StepVerifier::create).expectNext(true)
              .verifyComplete();
    }
}