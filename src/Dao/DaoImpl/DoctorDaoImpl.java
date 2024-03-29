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

public class DoctorDaoImpl implements DoctorDao<Doctor> {
    @Override
    public Doctor findDoctorById(Long id) {
        try {
            for (Hospital hospital : DataBase.hospitals) {
                for (Doctor doctor : hospital.getDoctors()) {
                    if (doctor.getId().equals(id)) {
                        return doctor;
                    }
                }
            }
            throw new MyException("The given " + id + " is not correct\nTry again");
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String assignDoctorToDepartment(Long departmentId, List<Long> doctorsId) {
        try {
            for (Hospital hospital : DataBase.hospitals) {
                for (Department department : hospital.getDepartments()) {
                    if (department.getId().equals(departmentId)) {
                        for (Long doctorId : doctorsId) {
                            for (Doctor doctor : hospital.getDoctors()) {
                                if (doctor.getId().equals(doctorId)) {
                                    if (department.getDoctors() == null) {
                                        department.setDoctors(new ArrayList<>());
                                    }
                                    department.getDoctors().add(doctor);
                                } else {
                                    throw new MyException("The given doctor ID " + doctorId + " is not correct for department ID " + departmentId + "\nTry again");
                                }
                            }
                        }
                        return "Doctors assigned successfully to department";
                    }
                }
            }
            throw new MyException("The given department ID " + departmentId + " is not correct\nTry again");
        } catch (MyException e) {
            return e.getMessage();
        }
    }

    @Override
    public List<Doctor> getAllDoctorsByHospitalId(Long id) {
        for (Hospital hospital : DataBase.hospitals) {
            if (hospital.getId().equals(id)) {
                return hospital.getDoctors();
            } else {
                System.out.println("Mistake:");
            }
        }
        return null;
    }


    @Override
    public List<Doctor> getAllDoctorsByDepartmentId(Long id) {
        try {
            for (Hospital hospital : DataBase.hospitals) {
                for (Department department : hospital.getDepartments()) {
                    if (department.getId().equals(id)) {
                        return department.getDoctors();
                    }
                }
            }
            throw new MyException("The given " + id + " is not correct\nTry again");
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String add(Long hospitalId, Doctor doctor) {
        try {
            for (Hospital hospital : new ArrayList<>(DataBase.hospitals)) {
                if (hospital.getId().equals(hospitalId)) {
                    List<Doctor> hospitals = hospital.getDoctors();
                    if (hospitals == null) {
                        hospitals = new ArrayList<>();
                        hospital.setDoctors(hospitals);
                    }
                    hospitals.add(doctor);
                    return "Doctor added successfully " + hospitals;
                }
            }
            throw new MyException("The  " + hospitalId + " not found\nTry again");

        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }


    @Override
    public void removeById(Long id) {
        try {
            for (Hospital hospital : DataBase.hospitals) {
                Iterator<Doctor> iterator = hospital.getDoctors().iterator();
                while (iterator.hasNext()) {
                    Doctor doctor = iterator.next();
                    if (doctor.getId().equals(id)) {
                        iterator.remove();
                        System.out.println("Doctor with ID " + id + " removed from hospital " + hospital.getId());
                        break;
                    } else {
                        throw new MyException("The Doctor " + id + " not found\nTry again");
                    }
                }
            }
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String updateById(Long id, Doctor doctor) {
        boolean doctorFound = false;
        try {
            for (Hospital hospital : DataBase.hospitals) {
                for (Doctor doctor1 : hospital.getDoctors()) {
                    if (doctor1.getId().equals(id)) {
                        doctor1.setFirstName(doctor.getFirstName());
                        doctor1.setLastName(doctor.getLastName());
                        doctor1.setGender(doctor.getGender());
                        doctor1.setExperienceYear(doctor.getExperienceYear());
                        doctorFound = true;
                        break;
                    }
                }
                if (doctorFound) {
                    break;
                }
            }
            if (!doctorFound) {
                throw new MyException("The hospital " + id + " not found\nTry again");
            }
            return " Changed successfully";
        } catch (MyException e) {
            return e.getMessage();
        }
    }
}
