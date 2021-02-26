package com.souther.could.conf;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @Description: 序列化类型转换
 * @Author souther
 * @Date: 2021/1/31 17:55
 */
@Configuration
public class SerializerConfig {

  private String pattern = "yyyy-MM-dd HH:mm:ss";

  @Bean
  @Primary
  public ObjectMapper serializingObjectMapper() {

    ObjectMapper objectMapper = new ObjectMapper();
    JavaTimeModule javaTimeModule = new JavaTimeModule();

    javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
    javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());

    objectMapper.setDateFormat(new SimpleDateFormat(pattern));
    objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

    objectMapper.registerModule(javaTimeModule);
    return objectMapper;
  }

  public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers)
        throws IOException {
      gen.writeString(value.format(DateTimeFormatter.ofPattern(pattern)));
    }
  }

  public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext deserializationContext)
        throws IOException {
      return LocalDateTime.parse(p.getValueAsString(), DateTimeFormatter.ofPattern(pattern));
    }
  }

}
