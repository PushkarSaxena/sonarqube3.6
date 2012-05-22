/*
 * Sonar, open source software quality management tool.
 * Copyright (C) 2008-2012 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * Sonar is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Sonar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Sonar; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.core.filters;

import org.sonar.core.filter.FilterColumnDto;

import org.sonar.core.filter.CriteriaDto;

import org.junit.Before;
import org.junit.Test;
import org.sonar.core.filter.FilterDao;
import org.sonar.core.filter.FilterDto;
import org.sonar.core.persistence.DaoTestCase;

import static org.fest.assertions.Assertions.assertThat;

public class FilterDaoTest extends DaoTestCase {
  private FilterDao dao;

  @Before
  public void createDao() {
    dao = new FilterDao(getMyBatis());
  }

  @Test
  public void should_find_filter() {
    setupData("shouldFindFilter");

    FilterDto filter = dao.findFilter("Projects");

    assertThat(filter.getId()).isEqualTo(1L);
    assertThat(filter.getUserId()).isNull();
    assertThat(dao.findFilter("<UNKNOWN>")).isNull();
  }

  @Test
  public void should_insert() {
    setupData("shouldInsert");

    FilterDto filterDto = new FilterDto();
    filterDto.setName("Projects");
    filterDto.setUserId(null);
    filterDto.setShared(true);
    filterDto.setFavourites(false);
    filterDto.setResourceId(null);
    filterDto.setDefaultView("list");
    filterDto.setPageSize(10L);
    filterDto.setPeriodIndex(1L);

    CriteriaDto criteriaDto = new CriteriaDto();
    criteriaDto.setFamily("family");
    criteriaDto.setKey("key");
    criteriaDto.setOperator("=");
    criteriaDto.setValue(1.5f);
    criteriaDto.setTextValue("1.5");
    criteriaDto.setVariation(true);
    filterDto.addCriteria(criteriaDto);

    FilterColumnDto filterColumnDto = new FilterColumnDto();
    filterColumnDto.setFamily("family");
    filterColumnDto.setKey("key");
    filterColumnDto.setSortDirection("ASC");
    filterColumnDto.setOrderIndex(2L);
    filterColumnDto.setVariation(true);
    filterDto.addColumn(filterColumnDto);

    dao.insert(filterDto);

    checkTables("shouldInsert", "filters", "criteria", "filter_columns");
  }
}