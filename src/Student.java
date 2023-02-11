public class Student extends Thread  {

    private ThreadGroup studentGroup;
    private Printer printerUsed;
    private String studentName;

    public Student(ThreadGroup studentGroup, Printer printerUsed, String studentName) {
        super(studentGroup,studentName);
        this.studentGroup = studentGroup;
        this.printerUsed = printerUsed;
        this.studentName = studentName;
    }

    @Override
    public void run() {
        //Documents created
        Document [] document = new Document[5];
        document[0] = new Document("Doc01", "Past paper",10);
        document[1] = new Document("Doc02", "Analytics Coursework",3);
        document[2] = new Document("Doc03", "Shared printer specification",5);
        document[3] = new Document("Doc04", "Robot-maze specification",9);
        document[4] = new Document("Doc05", "Profit maximization presentation",4);
        int pageNumber = 0;
        for(Document doc: document ){
            printerUsed.printDocument(doc);
            System.out.println(Thread.currentThread().getName() + " Finished printing " + doc.getUserID()); //Student printed document
            pageNumber += doc.getNumberOfPages();
            int sleepTime = ((int)( Math.random() * 100 + 1));
            try {
                Thread.sleep(sleepTime); //Sleeps for a random amount of time between each printing request.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(this.studentName + " Finished Printing: 5 Documents, " + pageNumber + " pages " );
    }

}
