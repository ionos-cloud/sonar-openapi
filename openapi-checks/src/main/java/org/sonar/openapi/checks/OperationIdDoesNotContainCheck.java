/*
 * SonarQube OpenAPI Plugin
 * Copyright (C) 2018-2019 Societe Generale
 * vincent.girard-reydet AT socgen DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.openapi.checks;

import com.google.common.collect.ImmutableSet;
import com.sonar.sslr.api.AstNodeType;
import org.apache.commons.lang3.StringUtils;
import org.sonar.check.Rule;
import org.sonar.plugins.openapi.api.OpenApiCheck;
import org.sonar.plugins.openapi.api.v2.OpenApi2Grammar;
import org.sonar.plugins.openapi.api.v3.OpenApi3Grammar;
import org.sonar.sslr.yaml.grammar.JsonNode;

import java.util.Set;

@Rule(key = OperationIdDoesNotContainCheck.CHECK_KEY)
public class OperationIdDoesNotContainCheck extends OpenApiCheck {
    public static final String CHECK_KEY = "OperationIdDoesNotContain";
    private static final String OPERATION_ID = "operationId";

    @Override
    public Set<AstNodeType> subscribedKinds() {
        return ImmutableSet.of(OpenApi2Grammar.OPERATION, OpenApi3Grammar.OPERATION);
    }

    @Override
    protected void visitNode(JsonNode node) {
        JsonNode opIdNode = node.propertyMap().get(OPERATION_ID);


        if (!opIdNode.isMissing() && !opIdNode.isNull()) {
            String nodeOperationId = opIdNode.value().getToken().getValue();
            if (StringUtils.containsIgnoreCase(nodeOperationId, "post") && StringUtils.containsIgnoreCase(nodeOperationId, "create")) {
                addIssue(String.format("Found %s: `%s` should not contain both `create` and `post`", OPERATION_ID, nodeOperationId), opIdNode.key());

            }
            if (StringUtils.containsIgnoreCase(nodeOperationId, "put") && StringUtils.containsIgnoreCase(nodeOperationId, "update")) {
                addIssue(String.format("Found %s: `%s` should not contain both `put` or `patch` and `update`", OPERATION_ID, nodeOperationId), opIdNode.key());
            }
        }
    }
}
