package Dao.DaoImpl;

import Dao.DepartmentDao;
import Database.DataBase;
import exceptions.MyException;
import model.Department;
import model.Hospital;
import service.HospitalService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DepartmentDaoImpl implements DepartmentDao<Department> {
    @Override
    public List<Department> getAllDepartmentByHospital(Long id) {
        try {
            return DataBase.hospitals.stream()
                    .filter(hospital -> hospital.getId().equals(id))
                    .flatMap(hospital -> hospital.getDepartments().stream())
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

    }

    @Override
    public Department findDepartmentByName(String name) {
        Optional<Department> department = DataBase.hospitals.stream()
                .flatMap(hospital -> {
                    if (hospital.getDepartments() != null) {
                        return hospital.getDepartments().stream();
                    }
                    return Stream.empty();
                })
                .filter(department1 -> department1.getDepartmentName().equals(name))
                .findFirst();
        return department.orElse(null);
    }

    @Override
    public String add(Long hospitalId, Department department) {
        Optional<Hospital> optionalHospital = DataBase.hospitals.stream()
                .filter(hospital -> hospital.getId().equals(hospitalId))
                .findFirst();

        if (optionalHospital.isPresent()) {
            Hospital hospital = optionalHospital.get();

            if (hospital.getDepartments() == null) {
                hospital.setDepartments(new ArrayList<>());
            }

            hospital.getDepartments().add(department);
            return "Successfully added to hospital with id " + hospitalId;
        } else {
            throw new NullPointerException("The given hospitalId " + hospitalId + " is not correct");
        }
    }

    @Override
    public void removeById(Long id) {
        Optional<Hospital> optionalHospital = DataBase.hospitals.stream()
                .filter(hospital -> {
                    if (hospital.getDepartments() != null) {
                        return hospital.getDepartments().stream().anyMatch(department -> department.getId().equals(id));
                    }
                    return false;
                })
                .findFirst();

        optionalHospital.ifPresent(hospital -> {
            try {
                if (hospital.getDepartments() != null) {
                    hospital.getDepartments().removeIf(department -> department.getId().equals(id));
                }
            } catch (Exception e) {
                throw new RuntimeException("The given " + id + " is not correct");
            }
        });
    }

    @Override
    public String updateById(Long id, Department department) {
        Optional<Department> optionalDepartment = DataBase.hospitals.stream()
                .flatMap(hospital -> {
                    if (hospital.getDepartments() != null) {
                        return hospital.getDepartments().stream();
                    }
                    return Stream.empty();
                })
                .filter(department1 -> department1.getId().equals(id))
                .findFirst();
        if (optionalDepartment.isPresent()) {

            Department existingDepartment = optionalDepartment.get();
            existingDepartment.setDepartmentName(department.getDepartmentName());

            return "Department with id " + id + " has been successfully updated.";
        } else {
            return " Department with id " + id + " not found";
        }
    }
}