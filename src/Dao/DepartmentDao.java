package Dao;

import model.Department;

import java.util.List;

public interface DepartmentDao<T> {
    List<Department> getAllDepartmentByHospital(Long id);

    Department findDepartmentByName(String name);
    String add(Long hospitalId, T t);

    void removeById(Long id);

    String updateById(Long id, T e);
}
