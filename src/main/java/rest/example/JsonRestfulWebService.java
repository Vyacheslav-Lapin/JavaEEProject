package rest.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.functions.ExceptionalFunction;

import javax.ws.rs.core.Response;
import java.util.Collection;

public interface JsonRestfulWebService {

    ExceptionalFunction<Object, String, JsonProcessingException> toJsonExceptional =
            new ObjectMapper().writer().withDefaultPrettyPrinter()::writeValueAsString;

    default String toJson(Object o) {
        return toJsonExceptional.apply(o).getOrThrowUnchecked();
    }

    default Response ok(Collection<?> objects) {
        return Response.ok(toJson(objects)).build();
    }

    default Response ok(Object o) {
        return Response.ok(toJson(o)).build();
    }

    default Response noContent() {
        return Response.noContent().build();
    }
}
