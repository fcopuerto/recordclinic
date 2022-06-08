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

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
class PatientController {

	private final PatientRepository patients;

	public PatientController(PatientRepository clinicService) {
		this.patients = clinicService;
	}

	@GetMapping("/patients.html")
	public String showPhysicianList(@RequestParam(defaultValue = "1") int page, Model model) {
		// Here we are returning an object of type 'Physicians' rather than a collection of Physician
		// objects so it is simpler for Object-Xml mapping
		Patients patients = new Patients();
		Page<Patient> paginated = findPaginated(page);
		patients.getPatientsList().addAll(paginated.toList());
		return addPaginationModel(page, paginated, model);

	}
        	/**
	 * Custom handler for displaying an owner.
	 * @param ownerId the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/patients/{patientId}")
	public ModelAndView showOwner(@PathVariable("patientId") int patientId) {
		ModelAndView mav = new ModelAndView("patients/patientDetails");
		Patient patient = this.patients.findById(patientId);
		mav.addObject(patient);
		return mav;
	}
        

	private String addPaginationModel(int page, Page<Patient> paginated, Model model) {
		List<Patient> listPhysicians = paginated.getContent();
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", paginated.getTotalPages());
		model.addAttribute("totalItems", paginated.getTotalElements());
		model.addAttribute("listPatients", listPhysicians);
		return "patients/patientList";
	}

	private Page<Patient> findPaginated(int page) {
		int pageSize = 5;
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		return patients.findAll(pageable);
	}

	@GetMapping({ "/patients" })
	public @ResponseBody Patients showResourcesPhysicianList() {
		// Here we are returning an object of type 'Physicians' rather than a collection of Physician
		// objects so it is simpler for JSon/Object mapping
		Patients patients = new Patients();
		patients.getPatientsList().addAll(this.patients.findAll());
		return patients;
	}

}
