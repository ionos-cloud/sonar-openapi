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

import com.google.common.collect.Sets;
import com.sonar.sslr.api.AstNodeType;
import java.util.Set;
import java.util.Map;

import org.sonar.check.Rule;
import org.sonar.plugins.openapi.api.OpenApiCheck;
import org.sonar.plugins.openapi.api.v2.OpenApi2Grammar;
import org.sonar.plugins.openapi.api.v3.OpenApi3Grammar;
import org.sonar.sslr.yaml.grammar.JsonNode;

@Rule(key = ProvidePropertiesDescriptionCheck.CHECK_KEY)
public class ProvidePropertiesDescriptionCheck extends OpenApiCheck {
  public static final String CHECK_KEY = "ProvidePropertiesDescription";

  @Override
  public Set<AstNodeType> subscribedKinds() {
    return Sets.newHashSet(OpenApi2Grammar.DEFINITIONS, OpenApi3Grammar.COMPONENTS);
  }

  @Override
  protected void visitNode(JsonNode node) {
    if (node.getType() == OpenApi2Grammar.DEFINITIONS) {
        visitDefinitionsProperties(node);
      } else {
        visitComponentsProperties(node);
      }
  }

  private void visitDefinitionsProperties(JsonNode node) {

    for ( Map.Entry<String, JsonNode> definition : node.propertyMap().entrySet()) {
        JsonNode content = definition.getValue();
        if (content.get("properties").isMissing()){
            continue;
        }
        CheckPropertiesDescription(content, "definition");
    }
  }

  private void visitComponentsProperties(JsonNode node) {
    JsonNode schemas = node.get("schemas");
    for ( Map.Entry<String, JsonNode> schema : schemas.propertyMap().entrySet()) {
        JsonNode content = schema.getValue();
        if (content.get("properties").isMissing()){
            continue;
        }
        CheckPropertiesDescription(content, "schema");
    }
}

  private void CheckPropertiesDescription(JsonNode node, String entity){
    JsonNode properties = node.get("properties");
    for (Map.Entry<String, JsonNode> property : properties.propertyMap().entrySet()){
        JsonNode propContent = property.getValue();
        // If the property is a reference, we don't need to check the description
        if (propContent.get("$ref").isMissing() && propContent.get("description").isMissing()){
            addIssue(String.format("Provide a description for each property of a %s.", entity), propContent.key());
        }
      }
    }
}
