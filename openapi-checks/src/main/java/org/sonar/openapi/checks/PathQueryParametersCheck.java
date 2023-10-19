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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
import com.sonar.sslr.api.AstNodeType;
import java.util.Set;
import java.util.Collection;

import org.sonar.check.Rule;
import org.sonar.plugins.openapi.api.OpenApiCheck;
import org.sonar.plugins.openapi.api.v2.OpenApi2Grammar;
import org.sonar.plugins.openapi.api.v3.OpenApi3Grammar;
import org.sonar.sslr.yaml.grammar.JsonNode;
import static org.sonar.openapi.checks.PathMaskeradingCheck.split;

@Rule(key = PathQueryParametersCheck.CHECK_KEY)
public class PathQueryParametersCheck extends OpenApiCheck {
  public static final String CHECK_KEY = "PathQueryParameters";

  @Override
  public Set<AstNodeType> subscribedKinds() {
    return Sets.newHashSet(OpenApi2Grammar.PATHS, OpenApi3Grammar.PATHS);
  }

  @Override
  protected void visitNode(JsonNode node) {
    Collection<JsonNode> paths = node.propertyMap().values();
    checkPathCharacters(paths);
  }

  private void checkPathCharacters(Collection<JsonNode> paths){
    for (JsonNode p : paths) {
        String path = p.key().getTokenValue();
        if (hasQueryParams(path)){
            addIssue("Path should not include query parameters.", p.key());
        }
    }
  }

  @VisibleForTesting
  static boolean hasQueryParams(String path){
    String[] splitPath = split(path);
    if (splitPath[splitPath.length - 1].contains("?")){
        return true;
    }
    return false;
  }

}