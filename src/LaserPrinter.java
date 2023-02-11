public class LaserPrinter implements ServicePrinter{

    private String name;
    private int id;
    private int currentPaperLevel;
    private int currentTonerLevel;
    private int noOfDocPrinted;

    private static int packsUsed = 0;
    private static int catridgesUsed = 0;

    public int getPacksUsed() {
        return packsUsed;
    }

    public int getCatridgesUsed() {
        return catridgesUsed;
    }

    public LaserPrinter(String name, int id, int currentPaperLevel, int currentTonerLevel, int noOfDocPrinted) {
        this.name = name;
        this.id = id;
        this.currentPaperLevel = currentPaperLevel;
        this.currentTonerLevel = currentTonerLevel;
        this.noOfDocPrinted = noOfDocPrinted;
    }

    @Override
    public synchronized void printDocument(Document document) {
        String docId = document.getUserID(); //Document id
        String docName = document.getDocumentName(); //Document name
        int noOfPages = document.getNumberOfPages(); //No of pages in the document

        System.out.println("Checking if document can be printed.....");
        while (currentTonerLevel < document.getNumberOfPages() || currentPaperLevel < document.getNumberOfPages()) {
            //If either the paper or toner (or both) are less than 10 then the document cannot be printed
            System.out.println("Paper and Toner level are both less than the number of pages in document so cannot be printed. Paper level: "
                    + currentPaperLevel + ", Toner level: " + currentTonerLevel + ", Number of pages in the document: "
                    + document.getNumberOfPages());
            try {
                wait(); //Printing must wait until there is enough of both to print it.
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (currentTonerLevel > document.getNumberOfPages() && currentPaperLevel > document.getNumberOfPages()) {
            //If toner and paper are both greater than the no of pages in a document , it can be printed
            System.out.println("Paper and Toner level are both greater than the number of pages in document so can be printed. Paper level: "
                    + currentPaperLevel + ", Toner level: " + currentTonerLevel + ", Number of pages in the document: "
                    + document.getNumberOfPages());
            //Assuming that to print one page of a document you need 1 sheet of paper and 1 unit of toner.
            this.currentPaperLevel -= document.getNumberOfPages(); //
            this.currentTonerLevel -= document.getNumberOfPages();
            this.noOfDocPrinted++; //No of documents printed is increased each time
            System.out.println("Printing Document " + docId + ": "  + "'" + docName + "'" + " that contains " + noOfPages + " pages" );
            System.out.println("Successfully printed the document " + docName);
            notifyAll();
        }
    }

    @Override
    public synchronized void refillPaper() {
        while (currentPaperLevel + SheetsPerPack > Full_Paper_Tray) { //If current paper level +
            // sheets per paper pack exceeds maximum amount of papers that can be stored in the printer , it waits
            System.out.println("No need to refill paper as current paper level is " + currentPaperLevel);
            try {
                if (PrintingSystem.students.activeCount() == 0){ //If there are any student threads that are running
                    return;
                }
                System.out.println("Paper technician waiting");
                wait(5000); //Waiting for 5 seconds
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
//        if(currentPaperLevel + SheetsPerPack <= Full_Paper_Tray) {
            System.out.println("Paper can be refilled as current paper level is " + currentPaperLevel);
            currentPaperLevel += SheetsPerPack; //Refill
            packsUsed++; //To get the number of packs used
            System.out.println("Successfully refilled paper, current level of paper is: " + currentPaperLevel);
            notifyAll();
//        }
    }

    @Override
    public synchronized void replaceTonerCartridge() {
        while (currentTonerLevel >= Minimum_Toner_Level) {
            //If it has a level of Minimum_Toner_Level or greater, then it cannot be replaced, and the technician should wait
            System.out.println("No need to replace toner as current toner level is " + currentTonerLevel);
            try {
                if (PrintingSystem.students.activeCount() == 0){ //If there are any student threads that are running
                    return;
                }
                System.out.println("Toner technician waiting");
                wait(5000); //Waiting for 5 seconds
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
//        if(currentTonerLevel < Minimum_Toner_Level) {
            System.out.println("Toner can be replaced as current toner level is " + currentTonerLevel);
            currentTonerLevel = PagesPerTonerCartridge; //Replace
            catridgesUsed++; //To get the number of cartridges used
            System.out.println("Successfully replaced toner, current level of toner is: " + currentTonerLevel);
            notifyAll();
//        }
    }

    @Override
    public String toString() {
        return "[ PrinterID:" + "name='" + name + '\'' + "." + id + ", Paper Level: " + currentPaperLevel +
                ", Toner Level: " + currentTonerLevel +
                ", Documents Printed: " + noOfDocPrinted + ']';
        //Example [ PrinterID: lp-CG.24, Paper Level: 35, Toner Level: 310, Documents Printed: 4 ]
    }
}
