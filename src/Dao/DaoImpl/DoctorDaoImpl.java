package Dao.DaoImpl;

import Dao.DoctorDao;
import Database.DataBase;
import exceptions.MyException;
import model.Department;
import model.Doctor;
import model.Hospital;
import service.GenericService;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class DoctorDaoImpl implements DoctorDao<Doctor> {
    @Override
    public Doctor findDoctorById(Long id) {
        try {
            Optional<Doctor> optionalDoctor = DataBase.hospitals.stream()
                    .flatMap(hospital -> hospital.getDoctors().stream())
                    .filter(doctor -> doctor.getId().equals(id))
                    .findFirst();

            return optionalDoctor.orElseThrow(() -> new IllegalArgumentException("Doctor with id " + id + " not found."));
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    public String assignDoctorToDepartment(Long departmentId, List<Long> doctorsId) {
        try {
            Optional<Department> optionalDepartment = DataBase.hospitals.stream()
                    .flatMap(hospital -> hospital.getDepartments().stream())
                    .filter(department -> department.getId().equals(departmentId))
                    .findFirst();

            if (optionalDepartment.isPresent()) {
                Department department = optionalDepartment.get();

                if (department.getDoctors() == null) {
                    department.setDoctors(new ArrayList<>());
                }

                for (Long doctorId : doctorsId) {
                    Optional<Doctor> optionalDoctor = DataBase.hospitals.stream()
                            .flatMap(hospital -> hospital.getDoctors().stream())
                            .filter(doctor -> doctor.getId().equals(doctorId))
                            .findFirst();

                    optionalDoctor.ifPresent(doctor -> department.getDoctors().add(doctor));
                }

                return "Doctors assigned to department successfully.";
            } else {
                throw new IllegalArgumentException("Department with id " + departmentId + " not found.");
            }
        } catch (Exception e) {
            return "An error occurred while assigning doctors to department: " + e.getMessage();
        }
    }


    @Override
    public List<Doctor> getAllDoctorsByHospitalId(Long id) {
        try {
            return DataBase.hospitals.stream()
                    .filter(hospital -> hospital.getId().equals(id))
                    .findFirst()
                    .map(Hospital::getDoctors)
                    .orElse(new ArrayList<>());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }


    @Override
    public List<Doctor> getAllDoctorsByDepartmentId(Long id) {
        try {
            Optional<Department> optionalDepartment = DataBase.hospitals.stream()
                    .flatMap(hospital -> hospital.getDepartments().stream())
                    .filter(department -> department.getId().equals(id))
                    .findFirst();

            return optionalDepartment.map(Department::getDoctors).orElse(new ArrayList<>());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    @Override
    public String add(Long hospitalId, Doctor doctor) {
        Optional<Hospital> optionalHospital = DataBase.hospitals.stream()
                .filter(hospital -> hospital.getId().equals(hospitalId))
                .findFirst();

        if (optionalHospital.isPresent()) {
            Hospital hospital = optionalHospital.get();

            if (hospital.getDoctors() == null) {
                hospital.setDoctors(new ArrayList<>());
            }

            hospital.getDoctors().add(doctor);
            return "Successfully added to hospital with id " + hospitalId;
        } else {
            throw new RuntimeException("The given hospitalId " + hospitalId + " is not correct");
        }
    }


    @Override
    public void removeById(Long id) {
        try {
            for (Hospital hospital : DataBase.hospitals) {
                List<Doctor> doctors = hospital.getDoctors();
                if (doctors != null) {
                    doctors.removeIf(doctor -> doctor.getId().equals(id));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while removing doctor with id " + id);
        }
    }
    @Override
    public String updateById(Long id, Doctor doctor) {
        try {
            Optional<Hospital> optionalHospital = DataBase.hospitals.stream()
                    .filter(hospital -> hospital.getDoctors().stream().anyMatch(doctor1 -> doctor1.getId().equals(id)))
                    .findFirst();

            if (optionalHospital.isPresent()) {
                Hospital hospital = optionalHospital.get();

                hospital.getDoctors().stream()
                        .filter(doctor1 -> doctor1.getId().equals(id))
                        .findFirst()
                        .ifPresent(existingDoctor -> {
                            existingDoctor.setId(doctor.getId());
                            existingDoctor.setFirstName(doctor.getFirstName());
                            existingDoctor.setLastName(doctor.getLastName());
                            existingDoctor.setGender(doctor.getGender());
                            existingDoctor.setExperienceYear(doctor.getExperienceYear());
                        });

                return "Doctor with id " + id + " has been successfully updated.";
            } else {
                throw new IllegalArgumentException("Doctor with id " + id + " not found.");
            }
        } catch (Exception e) {
            return "An error occurred while updating doctor with id " + id + ": " + e.getMessage();
        }
    }
}