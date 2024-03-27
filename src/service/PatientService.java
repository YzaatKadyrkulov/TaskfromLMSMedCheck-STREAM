package service;

import model.Patient;

import java.util.List;
import java.util.Map;

public interface PatientService<T> {
    String addPatientsToHospital(Long id, List<Patient> patients);

    Patient getPatientById(Long id);

    Map<Integer, Patient> getPatientByAge();

    List<Patient> sortPatientsByAge(String ascOrDesc);
    String add(Long hospitalId, T t);

    void removeById(Long id);

    String updateById(Long id, T e);
}
