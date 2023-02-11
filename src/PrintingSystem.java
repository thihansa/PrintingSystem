import java.sql.Array;

public class PrintingSystem {
    //2 groups of threads used in the system
    static ThreadGroup students = new ThreadGroup("Students");
    static ThreadGroup technicians = new ThreadGroup("Technicians");
    public static void main(String[] args) {

        ServicePrinter printer = new LaserPrinter("IJ-printer",9,250,9,0); //Current printer

        System.out.println("  ==  ==  ==  ==  ==  ==  ==  ==  ==  ==\n" +
                           "==  ==  ==  ==  ==  ==  ==  ==  ==  ==  ==\n" +
                           "          WELCOME TO IJ-printer           \n" +
                           "==  ==  ==  ==  ==  ==  ==  ==  ==  ==  ==\n" +
                           "  ==  ==  ==  ==  ==  ==  ==  ==  ==  ==\n");


        Thread studentThread1 = new Student(students,printer, "Student 1");
        Thread studentThread2 = new Student(students,printer,"Student 2" );
        Thread studentThread3 = new Student(students,printer,"Student 3");
        Thread studentThread4 = new Student(students,printer ,"Student 4");
        Thread paperTechnicianThread = new PaperTechnician(technicians,printer,"Paper Technician");
        Thread tonerTechnicianThread = new TonerTechnician(technicians,printer,"Toner Technician");

        studentThread1.start();
        studentThread2.start();
        studentThread3.start();
        studentThread4.start();
        paperTechnicianThread.start();
        tonerTechnicianThread.start();

        try {
            studentThread1.join();
            studentThread2.join();
            studentThread3.join();
            studentThread4.join();
            paperTechnicianThread.join();
            tonerTechnicianThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(" ");
        System.out.println("Printer Successfully Finished All Processes");
        System.out.println(" ");

        //Printer summary
        System.out.println("  ==  ==  ==  ==  ==  ==  ==  ==  ==  ==\n" +
                           "==  ==  ==  ==  ==  ==  ==  ==  ==  ==  ==\n" +
                           "             IJ-printer Summary           \n" +
                           "==  ==  ==  ==  ==  ==  ==  ==  ==  ==  ==\n" +
                           "  ==  ==  ==  ==  ==  ==  ==  ==  ==  ==\n");

        System.out.println(printer.toString());
        System.out.println();

    }
}