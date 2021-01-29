package com.snebot.fbmoll.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.Serializable;

public class BallTaskHelper {
    private static final Logger log = LoggerFactory.getLogger(BallTaskHelper.class);

    /**
     * Generate random number with range.
     *
     * @param min Minimum range.
     * @param max Maximum range.
     * @return Random number.
     */
    public static int random(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    /**
     * Close a closeable object.
     *
     * @param closeable Closeable object.
     */
    public static void close(Closeable closeable) {
        try {
            if (closeable != null) closeable.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ObjectMapper getMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }

    public static <T extends Serializable> String marshallJSON(T content) {
        try {
            return getMapper().writeValueAsString(content);
        } catch (Exception e) {
            log.error("failed to marshal item as json ", e);
            return "";
        }
    }

    public static <T extends Serializable> T unmarshallJSON(String content, Class<T> classType) {
        try {
            return getMapper().readValue(content, classType);
        } catch (Exception e) {
            log.error("failed to unmarshal json ", e);
            return null;
        }
    }
}
