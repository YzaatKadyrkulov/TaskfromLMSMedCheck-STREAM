package Dao.DaoImpl;

import Dao.HospitalDao;
import Database.DataBase;
import exceptions.MyException;
import model.Hospital;
import model.Patient;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HospitalDaoImpl implements HospitalDao {
    @Override
    public String addHospital(Hospital hospital) {
        DataBase.hospitals.add(hospital);
        return "Successfully added " + hospital;
    }

    @Override
    public Hospital findHospitalById(Long id) {
        return DataBase.hospitals.stream()
                .filter(hospital -> hospital.getId().equals(id))
                .findFirst().orElseThrow(() -> new RuntimeException("The hospital not found"));
    }

    @Override
    public List<Hospital> getAllHospital() {
        return new ArrayList<>(DataBase.hospitals);
    }

    @Override
    public List<Patient> getAllPatientFromHospital(Long id) {
        List<Patient> patients = DataBase.hospitals.stream()
                .filter(hospital -> hospital.getId().equals(id))
                .flatMap(hospital -> hospital.getPatients().stream())
                .collect(Collectors.toList());

        if (patients.isEmpty()) {
            throw new RuntimeException("No patients found in the hospital with ID " + id);
        }

        return patients;
    }

    @Override
    public String deleteHospitalById(Long id) {
        Optional<Hospital> optionalHospital = DataBase.hospitals.stream()
                .filter(hospital -> hospital.getId().equals(id))
                .findFirst();

        if (optionalHospital.isPresent()) {
            Hospital hospital = optionalHospital.get();
            DataBase.hospitals.remove(hospital);
            return "Hospital with ID " + id + " deleted successfully";
        } else {
            throw new RuntimeException("Hospital with ID " + id + " not found");
        }
    }

    @Override
    public Map<String, Hospital> getAllHospitalByAddress(String address) {
          try {
              return DataBase.hospitals.stream()
                      .filter(hospital -> hospital.getAddress().equals(address))
                      .collect(Collectors.toMap(Hospital::getAddress, hospital -> hospital, (existing, replacement) -> existing));
          }catch (NullPointerException e){
              System.err.println("An error occurred while processing hospitals: " + e.getMessage());
              return new HashMap<>();
          }

    }
}
