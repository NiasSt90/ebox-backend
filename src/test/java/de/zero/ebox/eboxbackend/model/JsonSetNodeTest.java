package de.zero.ebox.eboxbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.nio.file.Files;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(SpringExtension.class)
class JsonSetNodeTest {

    @Value("classpath:/json/node_173893.json")
    File node173893;
    @Value("classpath:/json/node_178903.json")
    File node173903;

    ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
            .indentOutput(true)
            .featuresToDisable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)
            .build();

    @Test
    void serializeNode173893() throws Exception {
        var set = SetNodeFactory.createNode173893();
        String actual = objectMapper.writeValueAsString(set);
        String expectedStr = Files.readString(node173893.toPath());
        JSONAssert.assertEquals(expectedStr, actual, JSONCompareMode.LENIENT);
    }

    @Test
    void serializeNode173903() throws Exception {
        var set = SetNodeFactory.createNode178903();
        String actual = objectMapper.writeValueAsString(set);
        String expectedStr = Files.readString(node173903.toPath());
        JSONAssert.assertEquals(expectedStr, actual, JSONCompareMode.LENIENT);
    }

    @Test
    void serializeInstant() throws JsonProcessingException, JSONException {

        TestInstantSerialize serializedObj = new TestInstantSerialize();
        serializedObj.setValue(Instant.ofEpochSecond(1547119281));

        String jsonText = objectMapper.writeValueAsString(serializedObj);
        JSONAssert.assertEquals("{ \"value\" : \"1547119281\" }", jsonText, JSONCompareMode.LENIENT);

        TestInstantSerialize unserializedObj = objectMapper.readValue(jsonText, TestInstantSerialize.class);
        assertEquals(serializedObj.value, unserializedObj.value);
    }

    private static class TestInstantSerialize {

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "")
        @JsonSerialize(using = InstantToEpochSecondsSerializer.class)
        private Instant value;


        public Instant getValue() {
            return value;
        }


        public void setValue(Instant value) {
            this.value = value;
        }
    }
}