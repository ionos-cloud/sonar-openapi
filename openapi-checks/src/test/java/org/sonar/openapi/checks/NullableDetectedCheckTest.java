package org.sonar.openapi.checks;

import org.junit.Test;
import org.sonar.openapi.OpenApiCheckVerifier;

public class NullableDetectedCheckTest {
    @Test
    public void detects_operationId_failures_v3() {
        OpenApiCheckVerifier.verify("src/test/resources/checks/v3/nullable-detected.yaml", new NullableDetectedCheck(), false);
    }
}
