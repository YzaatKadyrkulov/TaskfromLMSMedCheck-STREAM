package service;

import model.Doctor;

import java.util.List;

public interface DoctorService<T> {
    Doctor findDoctorById(Long id);

    String assignDoctorToDepartment(Long departmentId, List<Long> doctorsId);

    List<Doctor> getAllDoctorsByHospitalId(Long id);

    List<Doctor> getAllDoctorsByDepartmentId(Long id);
    String add(Long hospitalId, T t);

    void removeById(Long id);

    String updateById(Long id, T e);


    // Department‘ти id менен табып, анан Doctor‘лорду листтеги айдилери менен табып анан
    // ошол табылган Department'ге табылган Doctor'лорду кошуп коюнунуз


}
