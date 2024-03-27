package Dao.DaoImpl;

import Dao.PatientDao;
import Database.DataBase;
import exceptions.MyException;
import model.Doctor;
import model.Hospital;
import model.Patient;
import service.GenericService;

import java.util.*;

public class PatientDaoImpl implements PatientDao<Patient> {
        @Override
        public String addPatientsToHospital(Long id, List<Patient> patients) {
            try {
                boolean hospitalFound = false;
                for (Hospital hospital : DataBase.hospitals) {
                    if (hospital.getId().equals(id)) {
                        hospitalFound = true;
                        List<Patient> hospitalPatients = hospital.getPatients();
                        if (patients.isEmpty()) {
                            hospitalPatients.addAll(patients);
                        } else {
                            throw new MyException("No patients provided to add.");
                        }
                        break;
                    }
                }
                if (!hospitalFound) {
                    throw new MyException("Hospital with id " + id + " not found.");
                }
            } catch (MyException e) {
                System.out.println(e.getMessage());
                return "Successfully added";
            }
            return "Successfully added";
        }
    @Override
    public Patient getPatientById(Long id) {
        try {
            for (Hospital hospital : DataBase.hospitals) {
                for (Patient patient : hospital.getPatients()) {
                    if (patient.getId().equals(id)) {
                        return patient;
                    } else {
                        throw new MyException("The patient " + id + " not found\nTry again");
                    }
                }
            }
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Map<Integer, Patient> getPatientByAge() {
        Map<Integer, List<Patient>> patientsByAge = new HashMap<>();

        for (Hospital hospital : DataBase.hospitals) {
            for (Patient patient : hospital.getPatients()) {
                Integer age = patient.getAge();

                List<Patient> patientsWithSameAge = patientsByAge.getOrDefault(age, new ArrayList<>());
                patientsWithSameAge.add(patient);
                patientsByAge.put(age, patientsWithSameAge);
                System.out.println(patientsByAge);
            }
        }
        return null;
    }

    @Override
    public List<Patient> sortPatientsByAge(String ascOrDesc) {
        List<Patient> sortedPatients = new ArrayList<>();
        try {
            for (Hospital hospital : DataBase.hospitals) {
                Comparator<Patient> sortPatientsByAge = Comparator.comparing(Patient::getAge);
                if (ascOrDesc.equalsIgnoreCase("asc")) {
                    Collections.sort(hospital.getPatients(), sortPatientsByAge);
                } else if (ascOrDesc.equalsIgnoreCase("desc")) {
                    Collections.sort(hospital.getPatients(), sortPatientsByAge.reversed());
                } else {
                    throw new MyException("The  " + ascOrDesc + " not found\nTry again");
                }
                sortedPatients.addAll(hospital.getPatients());

            }
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        return sortedPatients;
    }

    @Override
    public String add(Long hospitalId, Patient patient) {
        try {
            for (Hospital hospital : DataBase.hospitals) {
                if (hospital.getId().equals(hospitalId)) {
                    List<Patient> patients = hospital.getPatients();
                    if(patients == null){
                        patients = new ArrayList<>();
                        hospital.setPatients(patients);
                    }
                    patients.add(patient);
                    return patients +" added successfully ";
                } else {
                    throw new MyException("The hospital " + hospitalId + " not found\nTry again");
                }
            }
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        return "Not correct";
    }

    @Override
    public void removeById(Long id) {
        try {
            for (Hospital hospital : DataBase.hospitals) {
                Iterator<Patient> iterator = hospital.getPatients().iterator();
                while (iterator.hasNext()) {
                    Patient patient = iterator.next();
                    if (patient.getId().equals(id)) {
                        iterator.remove();
                        System.out.println("Patient with ID " + id + " removed from hospital " + hospital.getId());
                        break;
                    } else {
                        throw new MyException("The Patient " + id + " not found\nTry again");
                    }
                }
            }
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public String updateById(Long id, Patient patient) {
        boolean patientFound = false;
        try {
            for (Hospital hospital : DataBase.hospitals) {
                for (Patient patient1 : hospital.getPatients()) {
                    if (patient1.getId().equals(id)) {
                        patient1.setFirstName(patient.getFirstName());
                        patient1.setLastName(patient.getLastName());
                        patient1.setGender(patient.getGender());
                        patient1.setAge(patient.getAge());
                        patientFound = true;
                        break;
                    }
                }
                if (patientFound) {
                    break;
                }
            }
            if (!patientFound) {
                throw new MyException("The patient " + id + " not found\nTry again");
            }
            return " Changed successfully";
        } catch (MyException e) {
            return e.getMessage();
        }
    }
}
