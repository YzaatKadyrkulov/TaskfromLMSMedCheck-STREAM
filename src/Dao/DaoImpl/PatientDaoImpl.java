package Dao.DaoImpl;

import Dao.PatientDao;
import Database.DataBase;
import exceptions.MyException;
import model.Department;
import model.Hospital;
import model.Patient;


import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PatientDaoImpl implements PatientDao<Patient> {
    @Override
    public String addPatientsToHospital(Long id, List<Patient> patients) {
//        Optional<Hospital> optionalHospital = DataBase.hospitals.stream()
//                .filter(hospital -> hospital.getId().equals(id))
//                .findFirst();
//        if (optionalHospital.isPresent()) {
//            optionalHospital.get().getPatients().addAll(patients);
//            return "Successfully added";
//        } else throw new RuntimeException("The hospital by " + id + " not found");
//    }
        return DataBase.hospitals.stream()
                .filter(hospital -> hospital.getId().equals(id))
                .findFirst()
                .map(hospital -> {
                    hospital.getPatients().addAll(patients);
                    return "Successfully added";
                })
                .orElseThrow(() -> new RuntimeException("The hospital by " + id + " not found"));
    }


    public Patient getPatientById(Long id) {
        try {
            Optional<Patient> optionalPatient = DataBase.hospitals.stream()
                    .flatMap(hospital -> hospital.getPatients().stream())
                    .filter(patient -> patient.getId().equals(id))
                    .findFirst();

            return optionalPatient.orElseThrow(() -> new IllegalArgumentException("Patient with id " + id + " not found"));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Map<Integer, Patient> getPatientByAge(int age) {
        try {
            return DataBase.hospitals.stream()
                    .flatMap(hospital -> hospital.getPatients().stream())
                    .filter(patient -> patient.getAge() == age)
                    .collect(Collectors.toMap(Patient::getAge, Function.identity()));
        } catch (Exception e) {
//            throw new MyException("An error occurred while processing patients: " + e.getMessage());
        }
        return null;
    }

    public List<Patient> sortPatientsByAge(String ascOrDesc) {
        try {
            if (!ascOrDesc.equalsIgnoreCase("asc") && !ascOrDesc.equalsIgnoreCase("desc")) {
                throw new MyException("The " + ascOrDesc + " not found\nTry again");
            }

            Comparator<Patient> comparator = Comparator.comparing(Patient::getAge);
            if (ascOrDesc.equalsIgnoreCase("desc")) {
                comparator = comparator.reversed();
            }

            return DataBase.hospitals.stream()
                    .flatMap(hospital -> hospital.getPatients().stream())
                    .sorted(comparator)
                    .collect(Collectors.toList());
        } catch (MyException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override

    public String add(Long hospitalId, Patient patient) {
        try {
            Optional<Hospital> optionalHospital = DataBase.hospitals.stream()
                    .filter(hospital -> hospital.getId().equals(hospitalId))
                    .findFirst();

            if (optionalHospital.isPresent()) {
                Hospital hospital = optionalHospital.get();
                List<Patient> patients = hospital.getPatients();
                if (patients == null) {
                    patients = new ArrayList<>();
                    hospital.setPatients(patients);
                }
                patients.add(patient);
                return patient + " added successfully ";
            } else {
                throw new MyException("The hospital " + hospitalId + " not found\nTry again");
            }
        } catch (MyException e) {
            System.out.println(e.getMessage());
            return "Not correct";
        }
    }

    @Override
    public void removeById(Long id) {
        try {
            boolean patientRemoved = DataBase.hospitals.stream()
                    .anyMatch(hospital -> hospital.getPatients().removeIf(patient -> patient.getId().equals(id)));

            if (!patientRemoved) {
                throw new MyException("The given " + id + " is not correct");
            }
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String updateById(Long id, Patient patient) {
        try {
            boolean patientFound = DataBase.hospitals.stream()
                    .flatMap(hospital -> hospital.getPatients().stream())
                    .filter(patient1 -> patient1.getId().equals(id))
                    .peek(patient1 -> {
                        patient1.setFirstName(patient.getFirstName());
                        patient1.setLastName(patient.getLastName());
                        patient1.setGender(patient.getGender());
                        patient1.setAge(patient.getAge());
                    })
                    .findFirst()
                    .isPresent();

            if (!patientFound) {
                throw new MyException("The patient " + id + " not found\nTry again");
            }
            return "Changed successfully";
        } catch (MyException e) {
            return e.getMessage();
        }
    }
}