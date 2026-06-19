package com.pawpet.bffadmin.client;

import com.pawpet.bffadmin.dto.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "patientClient", url = "http://localhost:8082/api/patients")
public interface PatientClient {

    @GetMapping
    List<Patient> getAllPatients();

}
