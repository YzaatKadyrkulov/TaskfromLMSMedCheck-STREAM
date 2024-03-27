package service.impl;

import Dao.DaoImpl.DoctorDaoImpl;
import Dao.DoctorDao;
import model.Doctor;
import service.DoctorService;
import service.GenericService;

import java.util.List;

public class DoctorServiceImpl implements DoctorService<Doctor> {
    DoctorDao<Doctor> doctorDao = new DoctorDaoImpl();

    @Override
    public Doctor findDoctorById(Long id) {
        return doctorDao.findDoctorById(id);
    }

    @Override
    public String assignDoctorToDepartment(Long departmentId, List<Long> doctorsId) {
        return doctorDao.assignDoctorToDepartment(departmentId, doctorsId);
    }

    @Override
    public List<Doctor> getAllDoctorsByHospitalId(Long id) {
        return doctorDao.getAllDoctorsByHospitalId(id);
    }

    @Override
    public List<Doctor> getAllDoctorsByDepartmentId(Long id) {
        return doctorDao.getAllDoctorsByDepartmentId(id);
    }

    @Override
    public String add(Long hospitalId, Doctor doctor) {
        return doctorDao.add(hospitalId, doctor);
    }

    @Override
    public void removeById(Long id) {
        doctorDao.removeById(id);
    }

    @Override
    public String updateById(Long id, Doctor doctor) {
        return doctorDao.updateById(id,doctor);
    }
}
