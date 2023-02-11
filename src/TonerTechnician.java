public class TonerTechnician extends Thread {

    private ThreadGroup group;
    private ServicePrinter printerUsed;
    private String tonerTechnicianName;

    public TonerTechnician(ThreadGroup group, ServicePrinter printerUsed, String tonerTechnicianName) {
        super(group,tonerTechnicianName);
        this.group = group;
        this.printerUsed = printerUsed;
        this.tonerTechnicianName = tonerTechnicianName;
    }


    @Override
    public void run() {
        for(int i=0; i < 3; i++){ //Attempt to refill the toner cartridges 3 times,
            System.out.println("Checking if toner can be replaced.....");
            if (PrintingSystem.students.activeCount() == 0){ //If there are any student threads that are running
                try {
                    System.out.println("Toner Technician Finished, cartridges replaced:" + ((LaserPrinter) printerUsed).getCatridgesUsed());
                    }
                catch (NullPointerException err){
                    ;
                    }
                return;
            }
            printerUsed.replaceTonerCartridge();
            int num = ((int)( Math.random() * 100 + 1));
            try {
                Thread.sleep(num); //Sleeps for a random amount of time between each attempt to replace toner
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
