package service.impl;

import Dao.DaoImpl.PatientDaoImpl;
import Dao.PatientDao;
import model.Patient;
import service.GenericService;
import service.PatientService;

import java.util.List;
import java.util.Map;

public class PatientServiceImpl implements PatientService<Patient> {
    PatientDao<Patient> patientDao = new PatientDaoImpl();

    @Override
    public String add(Long hospitalId, Patient patient) {
        return patientDao.add(hospitalId, patient);
    }

    @Override
    public void removeById(Long id) {
   patientDao.removeById(id);
    }

    @Override
    public String updateById(Long id, Patient patient) {
        return patientDao.updateById(id,patient);
    }

    @Override
    public String addPatientsToHospital(Long id, List<Patient> patients) {
        return patientDao.addPatientsToHospital(id, patients);
    }

    @Override
    public Patient getPatientById(Long id) {
        return patientDao.getPatientById(id);
    }

    @Override
    public Map<Integer, Patient> getPatientByAge() {
        return patientDao.getPatientByAge();
    }

    @Override
    public List<Patient> sortPatientsByAge(String ascOrDesc) {
        return patientDao.sortPatientsByAge(ascOrDesc);
    }
}
