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

import org.junit.Test;
import org.sonar.openapi.OpenApiCheckVerifier;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import static org.sonar.openapi.checks.PathQueryParametersCheck.hasQueryParams;

public class PathQueryParametersCheckTest {


  @Test
  public void verify_path_string() {
    assertTrue(hasQueryParams("/path/with/query?param"));
    assertTrue(hasQueryParams("/query?param"));

    assertFalse(hasQueryParams("/invalid/character?/path"));
  }

  @Test
  public void verify_path_valid_characters_v2() {
    OpenApiCheckVerifier.verify("src/test/resources/checks/v2/path-query-parameters.yaml", new PathQueryParametersCheck(), true);
  }

    @Test
  public void verify_path_valid_characters_v3() {
    OpenApiCheckVerifier.verify("src/test/resources/checks/v3/path-query-parameters.yaml", new PathQueryParametersCheck(), false);
  }

}
