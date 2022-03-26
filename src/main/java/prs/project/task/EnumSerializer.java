package prs.project.task;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class EnumSerializer extends JsonSerializer<Enum> {

    /**
     *Gender code the HumanSex object when generating JSON(Integer value)Convert to.
     */
    @Override
    public void serialize(Enum value, JsonGenerator generator, SerializerProvider serializers) throws IOException {
        generator.writeString(value.toString());
    }
}
