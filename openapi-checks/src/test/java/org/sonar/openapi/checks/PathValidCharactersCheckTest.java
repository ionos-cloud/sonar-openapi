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

import static org.sonar.openapi.checks.PathValidCharactersCheck.isValidPath;

public class PathValidCharactersCheckTest {


  @Test
  public void verify_path_string() {
    assertTrue(isValidPath("/valid/path"));
    assertTrue(isValidPath("/valid/path/with/{parameter}"));
    assertTrue(isValidPath("/valid/path/number2/with/{parameter}"));

    assertFalse(isValidPath("/invalid_path"));
    assertFalse(isValidPath("/invalid_path/with/{parameter}"));
    assertFalse(isValidPath("/valid/path/{with_invalid_parameter}/format"));
    assertFalse(isValidPath("/random/inv@lid/path"));
  }

  @Test
  public void verify_path_valid_characters_v2() {
    OpenApiCheckVerifier.verify("src/test/resources/checks/v2/path-valid-characters.yaml", new PathValidCharactersCheck(), true);
  }

    @Test
  public void verify_path_valid_characters_v3() {
    OpenApiCheckVerifier.verify("src/test/resources/checks/v3/path-valid-characters.yaml", new PathValidCharactersCheck(), false);
  }

}
