package Dao.DaoImpl;

import Dao.DepartmentDao;
import Database.DataBase;
import exceptions.MyException;
import model.Department;
import model.Doctor;
import model.Hospital;
import service.GenericService;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DepartmentDaoImpl implements DepartmentDao<Department> {
    @Override
    public List<Department> getAllDepartmentByHospital(Long id) {
        for (Hospital hospital : DataBase.hospitals) {
            try {
                if (hospital.getId().equals(id)) {
                    return hospital.getDepartments();
                } else {
                    throw new MyException("The department " + id + " not found\nTry again");
                }
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public Department findDepartmentByName(String name) {
        try {
            for (Hospital hospital : DataBase.hospitals) {
                for (Department department : hospital.getDepartments()) {
                    if (department.getDepartmentName().equals(name)) {
                        return department;
                    }
                }
            }
            throw new MyException("The given name " + name + " is not correct\nTry again");
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String add(Long hospitalId, Department department) {
        try {
            for (Hospital hospital : new ArrayList<>(DataBase.hospitals)) {
                if (hospital.getId().equals(hospitalId)) {
                    List<Department> departments = hospital.getDepartments();
                    if (departments == null) {
                        departments = new ArrayList<>();
                        hospital.setDepartments(departments);
                    }
                    departments.add(department);
                    return "Department added successfully " + departments;
                }
            }
            throw new MyException("The department not found\nTry again");
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @Override
    public void removeById(Long id) {
        try {
            for (Hospital hospital : DataBase.hospitals) {
                Iterator<Department> iterator = hospital.getDepartments().iterator();
                while (iterator.hasNext()) {
                    Department department = iterator.next();
                    if (department.getId().equals(id)) {
                        iterator.remove();
                        System.out.println("Department with ID " + id + " removed from hospital " + hospital.getId());
                        break;

                    }
                }
            }
            throw new MyException("The department " + id + " not found\nTry again");
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String updateById(Long id, Department department) {
        boolean departmentFound = false;
        try {
            for (Hospital hospital : DataBase.hospitals) {
                for (Department department1 : hospital.getDepartments()) {
                    if (department1.getId().equals(id)) {
                        department1.setDepartmentName(department.getDepartmentName());
                        departmentFound = true;
                        break;
                    }
                }
                if (departmentFound) {
                    break;
                }
            }
            if (!departmentFound) {
                throw new MyException("The hospital " + id + " not found\nTry again");
            }
            return " Changed successfully";
        } catch (MyException e) {
            return e.getMessage();
        }
    }
}
