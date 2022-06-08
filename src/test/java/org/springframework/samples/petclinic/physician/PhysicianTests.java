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
import org.junit.jupiter.api.Test;
import org.springframework.util.SerializationUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Dave Syer
 */
class physicianTests {

	@Test
	void testSerialization() {
		Physician physician = new Physician();
		physician.setFirstName("Zaphod");
		physician.setLastName("Beeblebrox");
		physician.setId(123);
		Physician other = (Physician) SerializationUtils.deserialize(SerializationUtils.serialize(physician));
		assertThat(other.getFirstName()).isEqualTo(physician.getFirstName());
		assertThat(other.getLastName()).isEqualTo(physician.getLastName());
		assertThat(other.getId()).isEqualTo(physician.getId());
	}

}
