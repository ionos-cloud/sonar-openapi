package org.sonar.openapi.checks;

import com.google.common.collect.Sets;
import com.sonar.sslr.api.AstNodeType;
import com.sonar.sslr.api.Token;
import org.sonar.check.Rule;
import org.sonar.plugins.openapi.api.OpenApiCheck;
import org.sonar.plugins.openapi.api.v3.OpenApi3Grammar;

import java.util.Set;


@Rule(key = NullableDetectedCheck.CHECK_KEY)
public class NullableDetectedCheck extends OpenApiCheck {
    public static final String CHECK_KEY = "NullableDetected";

    private static final String Nullable = "nullable";

    @Override
    public Set<AstNodeType> subscribedKinds() {
        return Sets.newHashSet(OpenApi3Grammar.SCHEMA_PROPERTIES );//OpenApi3Grammar.SCHEMA_PROPERTIES
    }
    @Override
    public void visitToken(Token token) {
        if (token.getValue().equals(Nullable)) {
            addLineIssue("nullable fields are discouraged unless absolutely necessary for backward compatibility purposes", token.getLine());
        }
    }
}
