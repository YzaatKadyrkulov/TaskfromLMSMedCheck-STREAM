import Database.GeneratedId;
import enums.Gender;
import exceptions.MyException;
import model.Department;
import model.Doctor;
import model.Hospital;
import model.Patient;
import service.*;
import service.impl.DepartmentServiceImpl;
import service.impl.DoctorServiceImpl;
import service.impl.HospitalServiceImpl;
import service.impl.PatientServiceImpl;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /**
         * Task of teacher
         * Deadline : 29.03.2024 / 17:00
         * MedCheck
         *
         *
         * 1)  Оорукана тууралу бир программа жазыныз. Dao жана Service менен иштешкиле (ар бир
         * класстын dao жана service interface'тери жана ошол interface'ти implements класстары болуш керек).
         * Database деген класс ачып, ичинде бардык маалыматтарды сактаган маалымат структурасы
         * болсун(List<Hospital> hospitals).
         *
         * 2) Класстар:
         *        * Hospital (Long id, String hospitalName, String address, List<Department> departments, List<Doctor> doctors, List<Patient> patients),
         *        * Department (Long id, String departmentName, List<Doctor> doctors),
         *        * Doctor (Long id, String firstName, String lastName, Gender gender, int experienceYear),
         *        * Patient (Long id, String firstName, String lastName, int age, Gender gender);3) Gender enum.
         *
         * 3)
         *
         *      HospitalService :
         *
         *              String addHospital(Hospital hospital);
         *              Hospital findHospitalById(Long id);
         *              List<Hospital> getAllHospital();
         *              List<Patient> getAllPatientFromHospital(Long id);
         *              String deleteHospitalById(Long id);
         *              Map<String, Hospital> getAllHospitalByAddress(String address);
         *
         *
         *
         *     GenericService : (Department, Doctor , Patient)
         *
         *
         *              String add(Long  hospitalId, T t);
         *
         *              void removeById(Long id);
         *
         *              String updateById(Long id, T t);
         *
         *
         *
         *      DepartmentService:
         *
         *
         *            List<Department> getAllDepartmentByHospital(Long id);
         *            Department findDepartmentByName(String name);
         *
         *
         *
         *      DoctorService:
         *
         *
         *             Doctor findDoctorById(Long id);
         *
         *             // Department‘ти id менен табып, анан Doctor‘лорду листтеги айдилери менен табып анан ошол табылган Department'ге табылган Doctor'лорду кошуп коюнунуз
         *             String assignDoctorToDepartment(Long departmentId, List<Long> doctorsId);
         *             List<Doctor> getAllDoctorsByHospitalId(Long id);
         *             List<Doctor> getAllDoctorsByDepartmentId(Long id);
         *
         *
         *
         *      PatientService:
         *
         *
         *           String addPatientsToHospital(Long id,List<Patient> patients);
         *           Patient getPatientById(Long id);
         *           Map<Integer, Patient> getPatientByAge();
         *           List<Patient> sortPatientsByAge(String ascOrDesc);
         */

        DoctorService<Doctor> doctorService = new DoctorServiceImpl();
        DepartmentService<Department> departmentService = new DepartmentServiceImpl();
        PatientService<Patient> patientService = new PatientServiceImpl();

        while (true) {
            System.out.println("""
                    1.Hospital
                    2.Department
                    3.Doctor
                    4.Patient
                    5.Exit
                    """);
            try {
                int choice = new Scanner(System.in).nextInt();
                boolean isTrue = false;
                while (!isTrue) {
                    switch (choice) {
                        case 1:
                            System.out.println("""
                                    1.add Hospital
                                    2.find Hospital By Id
                                    3.get All Hospital
                                    4.get All Patient From Hospital
                                    5.delete Hospital By Id
                                    6.get All Hospital By Address
                                    7.Exit""");

                            switch (new Scanner(System.in).nextInt()) {
                                case 1 -> {
                                    HospitalService hospitalService = new HospitalServiceImpl();

                                    Hospital hospital = new Hospital();

                                    hospital.setId(GeneratedId.genHospitalId());

                                    System.out.println("Write the hospital's name: ");
                                    String hospitalName = new Scanner(System.in).nextLine();
                                    hospital.setHospitalName(hospitalName);

                                    System.out.println("Write the address of the hospital: ");
                                    String hospitalAddress = new Scanner(System.in).nextLine();
                                    hospital.setAddress(hospitalAddress);

                                    System.out.println(hospitalService.addHospital(hospital));
                                }
                                case 2 -> {
                                    HospitalService hospitalService = new HospitalServiceImpl();

                                    System.out.println("Write the id of the hospital: ");
                                    Long id = new Scanner(System.in).nextLong();

                                    System.out.println(hospitalService.findHospitalById(id));
                                }
                                case 3 -> {
                                    HospitalService hospitalService = new HospitalServiceImpl();

                                    System.out.println(hospitalService.getAllHospital());
                                }
                                case 4 -> {
                                    HospitalService hospitalService = new HospitalServiceImpl();

                                    System.out.println("Write the id of the hospital: ");
                                    Long id = new Scanner(System.in).nextLong();

                                    System.out.println(hospitalService.getAllPatientFromHospital(id));
                                }
                                case 5 -> {
                                    HospitalService hospitalService = new HospitalServiceImpl();

                                    System.out.println("Write the id of the hospital: ");
                                    Long id = new Scanner(System.in).nextLong();

                                    System.out.println(hospitalService.deleteHospitalById(id));
                                }
                                case 6 -> {
                                    HospitalService hospitalService = new HospitalServiceImpl();

                                    System.out.println("Write the address of the hospital: ");
                                    String address = new Scanner(System.in).nextLine();

                                    System.out.println(hospitalService.getAllHospitalByAddress(address));
                                }
                                case 7 -> {
                                    System.out.println("Going back to main");
                                    isTrue = true;

                                }
                            }
                            break;
                        case 2:
                            boolean exitFromDepartment = false;
                            while (!exitFromDepartment) {
                                System.out.println("""
                                        1.Add department by id hospital to hospital
                                        2.Remove the department inside the hospital, with the id of the department 
                                        3.Update the department inside the hospital, with the id of the department
                                        4.get All Department By Hospital id
                                        5.find Department By Name
                                        6.Exit""");

                                switch (new Scanner(System.in).nextInt()) {
                                    case 1 -> {
                                        Department department = new Department();

                                        department.setId(GeneratedId.genDepartmentId());

                                        System.out.println("Write the id of the hospital: ");
                                        Long id = new Scanner(System.in).nextLong();


                                        System.out.println("Write the name of the department: ");
                                        department.setDepartmentName(new Scanner(System.in).nextLine());

                                        System.out.println(departmentService.add(id, department));
                                    }
                                    case 2 -> {
                                        System.out.println("Write the id of the department: ");
                                        Long id = new Scanner(System.in).nextLong();
                                        departmentService.removeById(id);
                                    }
                                    case 3 -> {
                                        Department department = new Department();

                                        System.out.println("Write the id of the department: ");
                                        Long departmentId = new Scanner(System.in).nextLong();

                                        System.out.println("Write a new name of the department: ");
                                        String departmentName = new Scanner(System.in).nextLine();

                                        department.setId(departmentId);
                                        department.setDepartmentName(departmentName);

                                        System.out.println(departmentService.updateById(departmentId, department));
                                    }
                                    case 4 -> {
                                        System.out.println("Write the id of the hospital: ");
                                        Long id = new Scanner(System.in).nextLong();

                                        System.out.println(departmentService.getAllDepartmentByHospital(id));
                                    }
                                    case 5 -> {
                                        System.out.println("Write the name of the department: ");
                                        String departmentName = new Scanner(System.in).nextLine();
                                        System.out.println(departmentService.findDepartmentByName(departmentName));
                                    }
                                    case 6 -> {
                                        System.out.println("Going back to main");
                                        exitFromDepartment = true;
                                    }

                                }
                            }
                            break;
                            case 3:
                            boolean exitFromDoctor = false;
                            while (!exitFromDoctor) {
                                System.out.println("""
                                        1.Add doctor by id hospital to hospital
                                        2.Remove the doctor inside the hospital, with the id of the doctor
                                        3.Update the doctor inside the hospital, with the id of the doctor
                                        4.get All doctor By Hospital id
                                        5.get All Doctors By Department Id
                                        6.find Doctor By Id
                                        7.assign Doctor To Department
                                        8.Exit""");


                                switch (new Scanner(System.in).nextInt()) {
                                    case 1 -> {
                                        System.out.println("Write the id of the hospital: ");
                                        Long hospitalId = new Scanner(System.in).nextLong();

                                        System.out.println("Write the doctor's first name: ");
                                        String firstName = new Scanner(System.in).nextLine();

                                        System.out.println("Write the doctor's last name: ");
                                        String lastName = new Scanner(System.in).nextLine();

                                        System.out.println("Write the doctor's MALE/FEMALE: ");
                                        String gender = new Scanner(System.in).nextLine();

                                        System.out.println("Write the doctor's experience year: ");
                                        int experienceYear = new Scanner(System.in).nextInt();

                                        if (gender.equalsIgnoreCase(Gender.MALE.name())) {
                                            gender = Gender.MALE.name();
                                        } else if (gender.equalsIgnoreCase(Gender.FEMALE.name())) {
                                            gender = Gender.FEMALE.name();
                                        } else {
                                            throw new MyException("Write correct.");
                                        }

                                        Doctor doctor1 = new Doctor(GeneratedId.genDoctorId(), firstName, lastName, gender, experienceYear);
                                        System.out.println(doctorService.add(hospitalId, doctor1));
                                    }
                                    case 2 -> {
                                        System.out.println("Write the id of the doctor: ");
                                        Long doctorId = new Scanner(System.in).nextLong();

                                        doctorService.removeById(doctorId);
                                    }
                                    case 3 -> {
                                        Doctor doctor = new Doctor();
                                        System.out.println("Write the id of the doctor: ");
                                        Long departmentId = new Scanner(System.in).nextLong();

                                        System.out.println("Write a new doctor's first name: ");
                                        String doctorName = new Scanner(System.in).nextLine();

                                        System.out.println("Write a new doctor's name: ");
                                        String doctorLastName = new Scanner(System.in).nextLine();

                                        System.out.println("Write a new doctor's MALE/FEMALE: ");
                                        String gender = new Scanner(System.in).nextLine();

                                        System.out.println("Write a new doctor's experience year: ");
                                        int experienceYear = new Scanner(System.in).nextInt();

                                        doctor.setId(departmentId);
                                        doctor.setFirstName(doctorName);
                                        doctor.setLastName(doctorLastName);
                                        doctor.setGender(gender);
                                        doctor.setExperienceYear(experienceYear);

                                        System.out.println(doctorService.updateById(departmentId, doctor));
                                    }
                                    case 4 -> {
                                        System.out.println("Write the id of the hospital: ");
                                        Long id = new Scanner(System.in).nextLong();

                                        System.out.println(doctorService.getAllDoctorsByHospitalId(id));
                                    }
                                    case 5 -> {
                                        System.out.println("Write the id of the department: ");
                                        Long departmentId = new Scanner(System.in).nextLong();

                                        System.out.println(doctorService.getAllDoctorsByDepartmentId(departmentId));
                                    }
                                    case 6 -> {
                                        System.out.println("Write the id of the doctor: ");
                                        Long idHospital = new Scanner(System.in).nextLong();

                                        System.out.println(doctorService.findDoctorById(idHospital));
                                    }
                                    case 7 -> {
                                        System.out.println("Write the id of the department: ");
                                        Long departmentId = new Scanner(System.in).nextLong();

                                        System.out.println("Write the id of the doctor: ");
                                        Long doctorId = new Scanner(System.in).nextLong();

                                        System.out.println(doctorService.assignDoctorToDepartment(departmentId, Collections.singletonList(doctorId)));
                                    }
                                    case 8 -> {
                                        System.out.println("Going back to main");
                                        exitFromDoctor = true;
                                    }
                                }
                }
                            break;
                                    case 4:
                                        boolean exitFromPatient = false;
                                        while (!exitFromPatient) {
                                            System.out.println("""
                                                    1.Add patient by id hospital to hospital
                                                    2.Remove the patient inside the hospital, with the id of the patient 
                                                    3.Update the patient inside the hospital, with the id of the patient
                                                    4.add Patients by id hospital To Hospital
                                                    5.get Patient By Id
                                                    6.get Patient By Age
                                                    7.sort Patients By Age
                                                    8.Exit""");

                                                switch (new Scanner(System.in).nextInt()) {
                                                    case 1 -> {
                                                        System.out.println("Write the id of the hospital: ");
                                                        Long hospitalId = new Scanner(System.in).nextLong();

                                                        System.out.println("Write the patient's first name: ");
                                                        String firstName = new Scanner(System.in).nextLine();

                                                        System.out.println("Write the patient's  last name: ");
                                                        String lastName = new Scanner(System.in).nextLine();

                                                        System.out.println("Write the patient's  MALE/FEMALE: ");
                                                        String gender = new Scanner(System.in).nextLine();

                                                        System.out.println("Write the patient's age: ");
                                                        int patientAge = new Scanner(System.in).nextInt();

                                                        if (gender.equalsIgnoreCase(Gender.MALE.name())) {
                                                            gender = Gender.MALE.name();
                                                        } else if (gender.equalsIgnoreCase(Gender.FEMALE.name())) {
                                                            gender = Gender.FEMALE.name();
                                                        } else {
                                                            throw new MyException("The given " + gender + " is not correct");
                                                        }

                                                        Patient patient = new Patient(GeneratedId.genPatientId(), firstName, lastName, patientAge, gender);
                                                        System.out.println(patientService.add(hospitalId, patient));
                                                    }
                                                    case 2 -> {
                                                        System.out.println("Write the id of the patient: ");
                                                        Long idPatient = new Scanner(System.in).nextLong();
                                                        patientService.removeById(idPatient);
                                                    }
                                                    case 3 -> {
                                                        System.out.println("Write the id of the patient: ");
                                                        Long departmentId = new Scanner(System.in).nextLong();

                                                        System.out.println("Write a new patient's first name: ");
                                                        String doctorName = new Scanner(System.in).nextLine();

                                                        System.out.println("Write a new patient's name: ");
                                                        String doctorLastName = new Scanner(System.in).nextLine();

                                                        System.out.println("Write a new patient's MALE/FEMALE: ");
                                                        String gender = new Scanner(System.in).nextLine();

                                                        System.out.println("Write a new patient's age: ");
                                                        int age = new Scanner(System.in).nextInt();

                                                        Patient patient = new Patient();

                                                        patient.setId(departmentId);
                                                        patient.setFirstName(doctorName);
                                                        patient.setLastName(doctorLastName);
                                                        patient.setGender(gender);
                                                        patient.setAge(age);

                                                        System.out.println(patientService.updateById(departmentId, patient));
                                                    }
                                                    case 4 -> {
                                                        System.out.println("Write the id of the hospital: ");
                                                        Long idHospital = new Scanner(System.in).nextLong();

                                                        List<Patient> patients = new ArrayList<>();

                                                        System.out.println("Enter the number of patients to add: ");
                                                        int numOfPatients = new Scanner(System.in).nextInt();

                                                        for (int i = 0; i < numOfPatients; i++) {
                                                            System.out.println("Enter patient details(id,firstName,lastName,gender,patientAge) for patient: ");

                                                            System.out.println("Write the patient's first name: " + (i + 1) + ":");
                                                            String firstName = new Scanner(System.in).nextLine();

                                                            System.out.println("Write the patient's  last name: " + (i + 1) + ":");
                                                            String lastName = new Scanner(System.in).nextLine();

                                                            System.out.println("Write the patient's  MALE/FEMALE: " + (i + 1) + ":");
                                                            String gender = new Scanner(System.in).nextLine();

                                                            System.out.println("Write the patient's age: " + (i + 1) + ":");
                                                            int patientAge = new Scanner(System.in).nextInt();

                                                            if (gender.equalsIgnoreCase(Gender.MALE.name())) {
                                                                gender = Gender.MALE.name();
                                                            } else if (gender.equalsIgnoreCase(Gender.FEMALE.name())) {
                                                                gender = Gender.FEMALE.name();
                                                            }else{
                                                                throw new MyException("Write MALE/FEMALE: ");
                                                            }
                                                            patients.add(new Patient(GeneratedId.genPatientId(), firstName, lastName, patientAge, gender));
                                                        }

                                                        String result = patientService.addPatientsToHospital(idHospital, patients);
                                                        System.out.println(result);
                                                    }
                                                    case 5 -> {
                                                        System.out.println("Write patient's id");
                                                        Long id = new Scanner(System.in).nextLong();

                                                        System.out.println(patientService.getPatientById(id));
                                                    }
                                                    case 6 -> {
                                                        System.out.println("Write patient's age: ");
                                                        int age = new Scanner(System.in).nextInt();
                                                        System.out.println(patientService.getPatientByAge(age));
                                                    }
                                                    case 7 -> {
                                                        System.out.println("Write the asc or desc to sortPatientByAge");
                                                        String sortByAge = new Scanner(System.in).nextLine();

                                                        System.out.println(patientService.sortPatientsByAge(sortByAge));
                                                    }
                                                    case 8 -> {
                                                        System.out.println("Going back to main");
                                                        exitFromPatient = true;
                                                    }

                                                }
                                            }
                                        break;
                                    case 5:
                                        System.out.println("See you soon");
                                        return;
                                }
                                     break;
                        }
            } catch (Exception e) {
                System.out.println("Write correct:");
            }
        }
    }
}