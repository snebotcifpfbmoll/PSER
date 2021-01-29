package com.snebot.fbmoll.helper;

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

    public static <T extends Serializable> String marshallJSON(T content) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(content);
        } catch (Exception e) {
            log.error("failed to marshal item as json ", e);
            return "";
        }
    }

    public static <T extends Serializable> T unmarshallJSON(String content, Class<T> classType) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(content, classType);
        } catch (Exception e) {
            log.error("failed to unmarshal json ", e);
            return null;
        }
    }
}
