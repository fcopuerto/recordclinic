/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.physician;

import org.springframework.samples.petclinic.physician.Physician;
import org.springframework.samples.petclinic.physician.Specialty;
import org.springframework.samples.petclinic.physician.PhysicianController;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.physician.Physician;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.samples.petclinic.physician.PhysicianRepository;
import org.springframework.samples.petclinic.physician.PhysicianRepository;
import org.springframework.samples.petclinic.physician.Specialty;

/**
 * Test class for the {@link physicianController}
 */

@WebMvcTest(PhysicianController.class)
class PhysicianControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PhysicianRepository physicians;

	private Physician james() {
		Physician james = new Physician();
		james.setFirstName("James");
		james.setLastName("Carter");
		james.setId(1);
		return james;
	};

	private Physician helen() {
		Physician helen = new Physician();
		helen.setFirstName("Helen");
		helen.setLastName("Leary");
		helen.setId(2);
		Specialty radiology = new Specialty();
		radiology.setId(1);
		radiology.setName("radiology");
		helen.addSpecialty(radiology);
		return helen;
	};

	@BeforeEach
	void setup() {
		given(this.physicians.findAll()).willReturn(Lists.newArrayList(james(), helen()));
		given(this.physicians.findAll(any(Pageable.class)))
				.willReturn(new PageImpl<Physician>(Lists.newArrayList(james(), helen())));

	}

	@Test
	void testShowphysicianListHtml() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/physicians.html?page=1")).andExpect(status().isOk())
				.andExpect(model().attributeExists("listPhysicians")).andExpect(view().name("physicians/physicianList"));

	}

	@Test
	void testShowResourcesphysicianList() throws Exception {
		ResultActions actions = mockMvc.perform(get("/physicians").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		actions.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.physicianList[0].id").value(1));
	}

}
