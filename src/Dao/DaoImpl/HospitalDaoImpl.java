package Dao.DaoImpl;

import Dao.HospitalDao;
import Database.DataBase;
import exceptions.MyException;
import model.Hospital;
import model.Patient;

import java.util.ArrayList;
import java.util.HashMap;
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
            for (Hospital hospital : DataBase.hospitals) {
                if (hospital.getId().equals(id)) {
                    return hospital;
                }
            }
            throw new MyException("The given name " + id + " is not correct\nTry again");
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
                    return "Successfully removed";
                }
            }
            throw new MyException("The given " + id + " is not correct\nTry again");
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @Override
    public Map<String, List<Hospital>> getAllHospitalByAddress(String address) {
        Map<String, List<Hospital>> hospitalsByAddress = new HashMap<>();
        boolean isHospitalExists = false;

        try {
            if (!DataBase.hospitals.isEmpty()) {
                for (Hospital hospital : DataBase.hospitals) {
                    if (hospital.getAddress().equalsIgnoreCase(address)) {
                        if (!hospitalsByAddress.containsKey(hospital.getAddress())) {
                            hospitalsByAddress.put(hospital.getAddress(), new ArrayList<>());
                        }
                        hospitalsByAddress.get(hospital.getAddress()).add(hospital);
                        isHospitalExists = true;
                    }
                }
                if (!isHospitalExists) {
                    throw new MyException("The hospital with address " + address + " not found!");
                }
            }
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        return hospitalsByAddress;
    }

}
