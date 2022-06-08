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
package org.springframework.samples.petclinic.patient;

import org.springframework.samples.petclinic.physician.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Repository class for <code>Physician</code> domain objects All method names are compliant
 * with Spring Data naming conventions so this interface can easily be extended for Spring
 * Data. See:
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 */
public interface PatientRepository extends Repository<Patient, Integer> {

	
	/**
	 * Retrieve an {@link Patient} from the data store by id.
	 * @param id the id to search for
	 * @return the {@link Patient} if found
	 */
	@Query("SELECT id FROM  Patient WHERE id =:id")
	@Transactional(readOnly = true)
	Patient findById(@Param("id") Integer id);
       
    
        
        /**
	 * Retrieve all <code>Physicians</code>s from the data store.
	 * @return a <code>Collection</code> of <code>Physicians</code>s
	 */
	@Transactional(readOnly = true)
	@Cacheable("patients")
	Collection<Patient> findAll() throws DataAccessException;

	/**
	 * Retrieve all <code>Physician</code>s from data store in Pages
	 * @param pageable
	 * @return
	 * @throws DataAccessException
	 */
	@Transactional(readOnly = true)
	@Cacheable("patients")
	Page<Patient> findAll(Pageable pageable) throws DataAccessException;
        
        /**
	 * Save an {@link Owner} to the data store, either inserting or updating it.
	 * @param patient the {@link Patient} to save
	 */
	void save(Patient patient);


	;
        
        
       

}
