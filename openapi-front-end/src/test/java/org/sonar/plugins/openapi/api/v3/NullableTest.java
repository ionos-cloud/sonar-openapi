package org.sonar.plugins.openapi.api.v3;

import org.junit.Test;
import org.sonar.openapi.BaseNodeTest;
import org.sonar.sslr.yaml.grammar.JsonNode;

public class NullableTest extends BaseNodeTest<OpenApi3Grammar> {
    @Test
    public void can_parse_nullable_and_throw_errors() {
        JsonNode model = parseResource(OpenApi3Grammar.PARAMETER, "/models/v3/nullable.yaml");

        assertEquals("id", model, "/name");
        assertTrue(model, "/nullable");
        assertFalse(model, "/required");
    }
}
