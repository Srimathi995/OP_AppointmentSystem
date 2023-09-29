import java.util.ArrayList;
import java.util.List;

class Doctor {
    private int id;
    private String name;
    private int maxPatientsPerDay;

    public Doctor(int id, String name, int maxPatientsPerDay) {
        this.id = id;
        this.name = name;
        this.maxPatientsPerDay = maxPatientsPerDay;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxPatientsPerDay() {
        return maxPatientsPerDay;
    }
}

class Appointment {
    private int id;
    private Doctor doctor;
    private String date;
    private String time;

    public Appointment(int id, Doctor doctor, String date, String time) {
        this.id = id;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}

public class AppointmentSystem {
    private List<Doctor> doctors = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();
    private int appointmentCounter = 1;

    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public Doctor getDoctorById(int id) {
        for (Doctor doctor : doctors) {
            if (doctor.getId() == id) {
                return doctor;
            }
        }
        return null;
    }

    public boolean bookAppointment(int doctorId, String date, String time) {
        Doctor doctor = getDoctorById(doctorId);
        if (doctor != null) {
            // Check if the doctor has available slots
            int bookedAppointments = countAppointmentsForDoctorOnDate(doctor, date);
            if (bookedAppointments < doctor.getMaxPatientsPerDay()) {
                Appointment appointment = new Appointment(appointmentCounter++, doctor, date, time);
                appointments.add(appointment);
                return true;
            }
        }
        return false;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public int countAppointmentsForDoctorOnDate(Doctor doctor, String date) {
        int count = 0;
        for (Appointment appointment : appointments) {
            if (appointment.getDoctor().equals(doctor) && appointment.getDate().equals(date)) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        AppointmentSystem appointmentSystem = new AppointmentSystem();

        // Add doctors to the system
        Doctor doctor1 = new Doctor(1, "Dr. Smith", 3);
        Doctor doctor2 = new Doctor(2, "Dr. Johnson", 4);
        appointmentSystem.addDoctor(doctor1);
        appointmentSystem.addDoctor(doctor2);

        // List available doctors
        List<Doctor> availableDoctors = appointmentSystem.getDoctors();
        for (Doctor doctor : availableDoctors) {
            System.out.println("Doctor ID: " + doctor.getId());
            System.out.println("Doctor Name: " + doctor.getName());
            System.out.println("Max Patients Per Day: " + doctor.getMaxPatientsPerDay());
            System.out.println();
        }

        // Book an appointment
        boolean appointmentBooked = appointmentSystem.bookAppointment(1, "2023-10-01", "18:00");
        if (appointmentBooked) {
            System.out.println("Appointment booked successfully!");
        } else {
            System.out.println("Appointment could not be booked.");
        }

        // List appointments
        List<Appointment> bookedAppointments = appointmentSystem.getAppointments();
        for (Appointment appointment : bookedAppointments) {
            System.out.println("Appointment ID: " + appointment.getId());
            System.out.println("Doctor: " + appointment.getDoctor().getName());
            System.out.println("Date: " + appointment.getDate());
            System.out.println("Time: " + appointment.getTime());
            System.out.println();
        }
    }
}
