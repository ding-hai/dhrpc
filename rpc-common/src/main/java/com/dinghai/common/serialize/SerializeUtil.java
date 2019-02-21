package com.dinghai.common.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.IOException;

public class SerializeUtil {
    private static ObjectMapper mapper = new ObjectMapper(new MessagePackFactory());
    public static byte[] serialize(Object object) throws JsonProcessingException {
        return mapper.writeValueAsBytes(object);
    }

    public static  <T> T deserialize(byte[] bytes, Class<T> cls) throws IOException {
        return mapper.readValue(bytes, cls);
    }
}
