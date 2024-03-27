package Dao.DaoImpl;

import Dao.HospitalDao;
import Database.DataBase;
import exceptions.MyException;
import model.Hospital;
import model.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HospitalDaoImpl implements HospitalDao {
    @Override
    public String addHospital(Hospital hospital) {
        DataBase.hospitals.add(hospital);
        return "Successfully added " + hospital;
    }

    @Override
    public Hospital findHospitalById(Long id) {
        try {
            for (Hospital hospital : new ArrayList<>(DataBase.hospitals)) {
                if (hospital.getId().equals(id)) {
                    return hospital;
                } else {
                    throw new MyException("The given name " + id + " is not correct\nTry again");
                }
            }
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Hospital> getAllHospital() {
        return DataBase.hospitals;
    }

    @Override
    public List<Patient> getAllPatientFromHospital(Long id) {
        try {
            for (Hospital hospital : new ArrayList<>(DataBase.hospitals)) {
                if (hospital.getId().equals(id)) {
                    return hospital.getPatients();
                } else {
                    throw new MyException("The given " + id + " is not correct\nTry again");
                }
            }
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String deleteHospitalById(Long id) {
        try {
            for (Hospital hospital : new ArrayList<>(DataBase.hospitals)) {
                if (hospital.getId().equals(id)) {
                    DataBase.hospitals.remove(hospital);
                } else {
                    throw new MyException("The given " + id + " is not correct\nTry again");
                }
            }
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        return "Successfully removed";
    }

    @Override
    public Map<String, Hospital> getAllHospitalByAddress(String address) {
        try {
            for (Hospital hospital : new ArrayList<>(DataBase.hospitals)) {
                if (hospital.getAddress().equals(address)) {
                    System.out.println(hospital);
                } else {
                    throw new MyException("The given " + address + " is not correct\nTry again");
                }
            }
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}


