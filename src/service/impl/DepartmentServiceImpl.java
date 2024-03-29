package service.impl;

import Dao.DaoImpl.DepartmentDaoImpl;
import Dao.DepartmentDao;
import exceptions.MyException;
import model.Department;
import service.DepartmentService;

import java.util.List;

public class DepartmentServiceImpl implements DepartmentService<Department> {
    DepartmentDao<Department> departmentDao = new DepartmentDaoImpl();
    @Override
    public List<Department> getAllDepartmentByHospital(Long id) {
        return departmentDao.getAllDepartmentByHospital(id);

    }

    @Override
    public Department findDepartmentByName(String name) {
        return departmentDao.findDepartmentByName(name);
    }

    @Override
    public String add(Long hospitalId, Department department) {
        return departmentDao.add(hospitalId,department);
    }

    @Override
    public void removeById(Long id) {
        departmentDao.removeById(id);
    }

    @Override
    public String updateById(Long id, Department department) {
        return departmentDao.updateById(id,department);
    }
}
