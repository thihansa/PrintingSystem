public class PaperTechnician extends Thread  {

    private ThreadGroup group;
    private ServicePrinter printerUsed;
    private String paperTechnicianName;

    public PaperTechnician(ThreadGroup group, ServicePrinter printerUsed, String paperTechnicianName) {
        super(group,paperTechnicianName);
        this.group = group;
        this.printerUsed = printerUsed;
        this.paperTechnicianName = paperTechnicianName;
    }

    @Override
    public void run() {
        for(int i=0; i < 3; i++){ //Attempt to refill the paper trays 3 times
            System.out.println("Checking if paper can be refilled.....");
            if (PrintingSystem.students.activeCount() == 0){ //If there are any student threads that are running
                return;
            }
            printerUsed.refillPaper();
            int num = ((int)( Math.random() * 100 + 1));
            try {
                Thread.sleep(num); //Sleeps for a random amount of time between each attempt to refill the paper
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            System.out.println("Paper Technician Finished, packs of paper used: " + ((LaserPrinter)printerUsed).getPacksUsed());
        }
        catch (NullPointerException err){
            ;

        }

    }
}
