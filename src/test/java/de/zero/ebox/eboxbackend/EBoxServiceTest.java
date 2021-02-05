package de.zero.ebox.eboxbackend;

import de.zero.ebox.eboxbackend.model.JsonSetNode;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EBoxServiceTest {

	@Autowired
	private EBoxService eBoxService;

	@Test
	void getNode() {
		JsonSetNode block = eBoxService.getNode(173893).block();
		assertNotNull(block);
	}
}