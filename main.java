import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.util.*;
import java.time.*;


/*work left
 CALCULATE TIME(MAYBE)
 ACCOUNT HISTORY(MAYBE)
*/

class calcPrice
  {
    ArrayList<String> filestr = new ArrayList<>();
    
    Scanner sc = new Scanner(System.in);

    String board[] = {"AnnaNagar","Tambaram","Navalur","Guindy","Broadway","Velachery"};
    String dest[] = board.clone();
    
    int annanagar[] = {28,35,46,10,20};
    int tambaram[] = {28,23,20,30,18};
    int navalur[] = {35,23,22,32,20};
    int guindy[] = {46,20,22,14,4};
    int broadway[] ={10,30,32,14,20};
    int velachery[] = {20,18,20,4,20};
    
   
    //comment start
    int[] readVehiclesAvailable(){

      int filenum[] = {5,5,5,5};
      File fp = null;
      try{
        fp = new File("AvailableVehicles.txt");

        Scanner fpread = new Scanner(fp);
        
        String lineInfo;
        lineInfo = fpread.nextLine();
        fpread.close();
        String tempstr[] = lineInfo.split(",");
        for(int i=0;i<4;i++){
          filenum[i]=Integer.parseInt(tempstr[i]);
        }
      }
      catch(IOException exception){
         System.out.println("An unexpected error is occurred.");
         exception.printStackTrace();
      }
      return filenum;
    }
    
    
    static Map<String, Integer> vehiclesAvailable = new HashMap<>();
    int availableVehicles[] = readVehiclesAvailable();
    String vehicleNames[] = {"Auto","HatchBack","Sedan","SUV"};
    int assign(){
        for(int i=0;i<4;i++){
          vehiclesAvailable.put(vehicleNames[i],availableVehicles[i]);
        }
        return 1;
    }
    int d1 = assign();
    Map <String, Integer> pendingVehicles = new HashMap<>();
    int arrsize;
  
    void printTime()
    {
      try{
        int i,j,c;
        Date datet = new Date();
        System.out.println("Current time: "+ datet);
  
        int presentHours = datet.getHours()+5;
        int presentMinutes = datet.getMinutes()+30;
        int presentSeconds = datet.getSeconds();
  
        int pastHours = 1;
        int pastMinutes = 1;
        int pastSeconds = 1;
  
        //Values taken from reading a file: 5,4,3,2......
        
        System.out.println(vehiclesAvailable);
        /////current work....
        
        ArrayList<String> strLine = new ArrayList<String>();  //contains each line from io txt
        ArrayList<String> iocarType = new ArrayList<String>(); //contains cars which has not returned
        ArrayList<Integer> iocarNum = new ArrayList<Integer>(); //contains time of those cars
        
        Map <String, Integer> carTimes = new HashMap<>();
        String[] carTimesType = new String[10]; //contains total cars in io
        int[] carTimesTime = new int[10];  //contains total time of cars in io
        
        
        
        
        String carDetails[] = new String[2];
        File pastfp =null;
        pastfp = new File("inOut.txt");
        Scanner pastfpread = new Scanner(pastfp);
        String lineInfo;
      
        while(pastfpread.hasNextLine()){
          lineInfo = pastfpread.nextLine();
          strLine.add(lineInfo);
        }
        for(i=0;i<strLine.size();i++){
          carDetails = strLine.get(i).split(",");
          carTimesType[i] = carDetails[0];
          carTimesTime[i] = Integer.parseInt(carDetails[1]);
          //carTimes.put(carDetails[0],Integer.parseInt(carDetails[1]));
        }
        //System.out.println(carTimes);
        arrsize = strLine.size();
        System.out.println("n: "+strLine.size());
        for(i=0;i<arrsize;i++) System.out.print(carTimesType[i]+"  ");
        for(i=0;i<arrsize;i++) System.out.print(carTimesTime[i]+",");
        
  
        /////work done....
        /*System.out.print("\nEnter type of vehicle which is to be returned: Sedan\n");
        String returnVehicle = "Sedan";*/
  
        System.out.println("\nCurrent Time:  Hours: "+presentHours+"  Minutes: "+presentMinutes+" Seconds:  "+presentSeconds);
  
        int presentTime,pastTime;
          presentTime = (presentHours*60) +presentMinutes;
        System.out.println("present time: "+presentTime);
        for(i=0;i<arrsize;i++){
          pastTime = carTimesTime[i];
          //Checking if the car has returned to company for further transport...
          //FOR loop till the text file is completely read
          if(presentTime - pastTime >80 ){
            //since vehicle has returned increment that type by 1 in available txt
            vehiclesAvailable.put(carTimesType[i],vehiclesAvailable.get(carTimesType[i])+1);
            /*for(c=0;c<4;c++){
              if(carTimesType[i].equals(vehicleNames[c])){
                carTimesTime[c]++;
              }
            }*/
          }
          else{
            //since it has not returned add it to another array for further write to io txt
            pendingVehicles.put(carTimesType[i],pastTime);
            iocarType.add(carTimesType[i]);
            iocarNum.add(carTimesTime[i]);
          }
        }
        System.out.println(vehiclesAvailable);
        System.out.println(pendingVehicles);
        writeFile(pendingVehicles,iocarType,iocarNum);
      }
      catch(IOException exception){
       System.out.println("An unexpected error is occurred.");
       exception.printStackTrace();
      }
    }

    //The function takes the selected car as input and reduces it in available txt and further APPENDS it to io txt
    void selectedType(String car){

      System.out.println("\nAfter selecting car....");
        int i,j,c;
      String vehicleNames[] = {"Auto","HatchBack","Sedan","SUV"};
      Date datet = new Date();
      int presentHours = datet.getHours()+5;
      int presentMinutes = datet.getMinutes()+30;
      int presentSeconds = datet.getSeconds();
      int presentTime = (presentHours*60) +presentMinutes;

      File fp = null;
      File fp1 = null;
      try{
        fp = new File("AvailableVehicles.txt");
        fp1 =new File("inOut.txt");
  
        FileWriter fpwrite = new FileWriter("AvailableVehicles.txt");
        FileWriter fp1write = new FileWriter("inOut.txt",true);

        BufferedWriter fp1append = new BufferedWriter(fp1write);
        
  
        for(i=0;i<4;i++){
          if(car.equals(vehicleNames[i])){
            fpwrite.write((vehiclesAvailable.get(vehicleNames[i])-1)+",");
            System.out.println((vehiclesAvailable.get(vehicleNames[i])-1)+",");
          }
          else{
            fpwrite.write(vehiclesAvailable.get(vehicleNames[i])+",");
            System.out.println(vehiclesAvailable.get(vehicleNames[i])+",");
          }
        }
  
        fp1append.write(car+","+presentTime+",\n");

        fp1append.close();
        fp1write.close();
        fpwrite.close();
      }
      catch(IOException exception){
         System.out.println("An unexpected error is occurred.");
         exception.printStackTrace();
      }
      
      
    }

    //the function writes to io txt and writes to available
    void writeFile(Map <String, Integer> pendingVehicles,ArrayList<String>  iocarType,ArrayList<Integer> iocarNum){
      
      //VehiclesAvailable ={auto:1,....};   pendingVehicles = {"auto":'...currentTime...'...}

      int i,j;
  
    String vehicleNames[] = {"Auto","HatchBack","Sedan","SUV"};
    /*for(i=0;i<4;i++){
        passingArray[i] = vehiclesAvailable.get(vehicleNames[i]);
      }*/
    File fp = null;
    File fp1 = null;
    try{
      fp = new File("AvailableVehicles.txt");
      fp1 =new File("inOut.txt");

      FileWriter fpwrite = new FileWriter("AvailableVehicles.txt");
      FileWriter fp1write = new FileWriter("inOut.txt");

      for(i=0;i<4;i++){
        fpwrite.write(vehiclesAvailable.get(vehicleNames[i])+",");
        System.out.print(vehiclesAvailable.get(vehicleNames[i])+",");
      }

      for(i=0;i<iocarType.size();i++){
        fp1write.write(iocarType.get(i)+","+iocarNum.get(i)+",\n");
      }
      

      fp1write.close();
      fpwrite.close();
    }
    catch(IOException exception){
       System.out.println("An unexpected error is occurred.");
       exception.printStackTrace();
    }
    
  availableVehicles = readVehiclesAvailable().clone();
  }
    //comment stops
}

class Model extends JFrame{
    JFrame f=new JFrame();//creating instance of JFrame
    JButton b=new JButton();
    JPasswordField value = new JPasswordField();
    JLabel l1,l2;
    final JTextField tf=new JTextField();
    JLabel l3 = new JLabel(new ImageIcon("bg.jpg"));  //Background
    //JLabel error = new JLabel(new ImageIcon("error.png")); //error message
    String board[] = {"annaNagar","Tambaram","Navalur","Guindy","Broadway","Velachery"};
    String dest[] = board.clone();
    
    int annanagar[] = {28,35,46,10,20};
    int tambaram[] = {28,23,20,30,18};
    int navalur[] = {35,23,22,32,20};
    int guindy[] = {46,20,22,14,4};
    int broadway[] ={10,30,32,14,20};
    int velachery[] = {20,18,20,4,20};
    
    
    int calDistance(String pickup,String drop)
    {
      int i;
      int currentarr[]={0,0,0,0,0,0};
      for(i=0;i < board.length;i++)
        {
          if(board[i].equals(pickup))
          {
            System.out.println("board: "+board[i]+"  i: "+i);
            if(i==0) currentarr = annanagar.clone();
            else if(i==1) currentarr =tambaram.clone();
            else if(i==2) currentarr =navalur.clone();
            else if(i==3) currentarr =guindy.clone();
            else if(i==4) currentarr =broadway.clone();
            else if(i==5) currentarr =velachery.clone();
          }
        }
      int j=0,c=0;
      for (j=0;j<dest.length+1;j++)
        {
          System.out.println(j+c);
          if(dest[j].equals(pickup)) {
            c++;
            System.out.print(dest[j]+" ");
          }
          if(dest[j+c].equals(drop))
          {
            System.out.println("board: "+dest[j+c]+"  i: "+(j+c));
            return currentarr[j];
            //return 0;
          }
        }
      return 0;
    }
    
    int[] readVehiclesAvailable(){

      int filenum[] = {5,5,5,5};
      File fp = null;
      try{
        fp = new File("AvailableVehicles.txt");

        Scanner fpread = new Scanner(fp);
        
        String lineInfo;
        lineInfo = fpread.nextLine();
        fpread.close();
        String tempstr[] = lineInfo.split(",");
        for(int i=0;i<4;i++){
          filenum[i]=Integer.parseInt(tempstr[i]);
        }
      }
      catch(IOException exception){
         System.out.println("An unexpected error is occurred.");
         exception.printStackTrace();
      }
      return filenum;
    }
    
    
    static Map<String, Integer> vehiclesAvailable = new HashMap<>();
    int availableVehicles[] = readVehiclesAvailable();
    String vehicleNames[] = {"Auto","HatchBack","Sedan","SUV"};
    int assign(){
        for(int i=0;i<4;i++){
          vehiclesAvailable.put(vehicleNames[i],availableVehicles[i]);
        }
        return 1;
    }
    int d1 = assign();
    //auto,time....auto,time
    
    int[] passingArray= new int[4];
    
    
    
    Model(){
        
        
        /*final JTextField tf=new JTextField();  //Textfield
        tf.setBounds(50,50, 150,20);
        
         //passwords
        value.setBounds(100,100,100,30);
        f.add(value);
        
        b.setText("Click");//creating instance of JButton
        
        //JButton b=new JButton(new ImageIcon("D:\\icon.png")); //For image on button
        
        b.setBounds(130,100,100, 40);//x axis, y axis, width, height
        
        b.addActionListener(new ActionListener(){ //Button functionality
            public void actionPerformed(ActionEvent e){
                tf.setText("Yo");
                f.add(tf);
                System.out.println(value.getText());
                //String s = tf.getText() //for returning values from entry box
            }
        });
        
                               //Labels
        l1=new JLabel("First Label.");
        l1.setBounds(50,50, 100,30);
        l2=new JLabel("Second Label.");
        l2.setBounds(50,100, 100,30);
        f.add(l1); f.add(l2);
          
        f.add(b);//adding button in JFrame
                  
        f.setSize(400,500);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
    }
    void write_file(String s1, String s2)
    {
        try{
        FileWriter fw= new FileWriter("login.txt",true);
        fw.write(s1+" "+s2+"\n");
        fw.close();
        }catch(IOException  e)
        {
            System.out.println("An error occured");
        }
    }
    int check(String s1, String s2)
     {
        int flag=0;
        try
        {
         File myObj = new File("login.txt");
         Scanner myReader = new Scanner(myObj);
         while (myReader.hasNextLine()) {
             String data = myReader.nextLine();
             String check[]=data.split(" ");
             if(check[0].equals(s1))
             {
                 //System.out.println("Username already exist...");
                 if(check[1].equals(s2))
                 {
                     f.dispose();
                     new Menu();
                 }
                     
                 return 1;
             }
             
            

         }
         myReader.close();
         return 0;
         }
         catch (FileNotFoundException e) {
             System.out.println("An error occurred.");
             e.printStackTrace();
             return 3;
         }
         
     }
    void create_file(String f){
        try{
            File myobj=new File( "acct_histry/" + f +".txt");
            if(myobj.createNewFile()){
               System.out.println("File created "+myobj.getName());
            }else{
               System.out.println("File already exist...");
            }
        }catch (IOException e){
            System.out.println("An error occured");
            e.printStackTrace();
        }

    }
    void setMenu(){
        JMenu options;
        JMenuItem log,acct;
        JMenuBar mb = new JMenuBar();
        options = new JMenu("Options");
        log = new JMenuItem("Logout");
        acct = new JMenuItem("Account");
        log.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                f.dispose();
                new FirstWindow();
            }
        });
        options.add(log);options.add(acct);
        mb.add(options);
        f.setJMenuBar(mb);
        
    }
    
    
    
}

class Payment extends Model{
    Payment(){
     
     }
    Payment(int price,int d){
        setMenu();
        String s1 = "Your amount is: " + price;
        l1 = new JLabel(s1);
        l1.setFont(new Font("Verdana",Font.BOLD,18));
        l1.setOpaque(true);
        l1.setBackground(Color.WHITE);
        l1.setBounds(560,100,250,50);
        String options[] = {"--Select--","COD","Credit Card","Debit Card","UPI"};
        JComboBox cb = new JComboBox(options);
        cb.setBounds(580,250,150,20);
        
        b.setIcon(new ImageIcon("back.png"));
        b.setBounds(10,10,100,100);
        b.setOpaque(false);
        f.add(b);
        b.addActionListener(new ActionListener(){ //Button functionality
            public void actionPerformed(ActionEvent e){
                f.dispose();
                new ChooseCar(d);
            }
        });
        JButton b1 = new JButton();
        b1.setText("Confirm");
        b1.setBounds(600,400,100,50);
        calcPrice c1 = new calcPrice();
        b1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(price/d == 16)
                    c1.selectedType("Auto");
                else if(price/d == 20)
                    c1.selectedType("HatchBack");
                else if(price/d == 25)
                    c1.selectedType("Sedan");
                else c1.selectedType("SUV");
                JFrame f2 = new JFrame();
                JLabel j3 = new JLabel("Your taxi has been booked!");
                j3.setBounds(100,10,200,10);
                JButton bb = new JButton("OK");
                bb.setBounds(175,70,50,20);
                f2.add(bb);f2.add(j3);
                bb.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        f2.dispose();
                        f.dispose();
                        //write "from to car_type amount date&time"
                    }
                });
                f2.setSize(400,200);
                f2.setLayout(null);
                f2.setVisible(true);
                f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
            }
        });
        
        f.add(l1);f.add(cb);f.add(b);f.add(b1);
        l3.setBounds(0,0,1280,720);
        f.add(l3);
        
        f.setSize(1280,720);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
}

class ChooseCar extends Model{
    ChooseCar(){
        
    }
    ChooseCar(int d){
        setMenu();
        JButton auto = new JButton(new ImageIcon("auto.jpeg"));
        JLabel r1 = new JLabel("       Auto");
        JButton hatch = new JButton(new ImageIcon("hatch.jpeg"));
        JLabel r2 = new JLabel(" Hatchback");
        JButton sedan = new JButton(new ImageIcon("sedan.jpeg"));
        JLabel r3 = new JLabel("    Sedan");
        JButton suv = new JButton(new ImageIcon("suv.png"));
        JLabel r4 = new JLabel("       SUV");
        auto.setBounds(400,50,150,150);
        hatch.setBounds(400,210,150,150);
        sedan.setBounds(400,370,150,150);
        suv.setBounds(400,530,150,150);
        r1.setOpaque(true);
        r2.setOpaque(true);
        r3.setOpaque(true);
        r4.setOpaque(true);
        r1.setBackground(Color.WHITE);
        r2.setBackground(Color.WHITE);
        r3.setBackground(Color.WHITE);
        r4.setBackground(Color.WHITE);
        r1.setFont(new Font("Verdana",Font.BOLD,16));
        r2.setFont(new Font("Verdana",Font.BOLD,16));
        r3.setFont(new Font("Verdana",Font.BOLD,16));
        r4.setFont(new Font("Verdana",Font.BOLD,16));
        r1.setBounds(600,100,100,50);
        r2.setBounds(600,260,100,50);
        r3.setBounds(600,420,100,50);
        r4.setBounds(600,580,100,50);
        JLabel j1 = new JLabel();
        j1.setFont(new Font("Verdana",Font.BOLD,16));
        
        j1.setBounds(855,400,210,40);
        f.add(j1);
        
        //auto=1 (per km)
        auto.addActionListener(new ActionListener(){       //calculate price for auto using some criteria...for eg, for auto it is rs 1/km
            public void actionPerformed(ActionEvent e){
                if(vehiclesAvailable.get("Auto")>0){
                    int price=16*d;
                    f.dispose();
                    new Payment(price,d);
                }
                else{
                    j1.setOpaque(true);
                    j1.setBackground(Color.WHITE);
                    j1.setForeground(Color.RED);
                    j1.setText("  Auto not available");

                }
                                }
        });
        hatch.addActionListener(new ActionListener(){      //calcuate price for hatchback
            public void actionPerformed(ActionEvent e){
                
                if(vehiclesAvailable.get("HatchBack")>0){
                    int price = 20*d;
                    f.dispose();
                    new Payment(price,d);
                }
                else{
                    j1.setOpaque(true);
                    j1.setBackground(Color.WHITE);
                    j1.setForeground(Color.RED);
                    j1.setText("HatchBack not available");
                }
                    
            }
        });
        sedan.addActionListener(new ActionListener(){       //calculate price for sedan
            public void actionPerformed(ActionEvent e){
                
                if(vehiclesAvailable.get("Sedan")>0){
                    int price = 25*d;
                    f.dispose();
                    new Payment(price,d);
                }
                else{
                    j1.setOpaque(true);
                    j1.setBackground(Color.WHITE);
                    j1.setForeground(Color.RED);
                    j1.setText("Sedan not available");
                }
                    
            }
                
        });
        suv.addActionListener(new ActionListener(){         //calculate price for suv
            public void actionPerformed(ActionEvent e){
                
                if(vehiclesAvailable.get("SUV")>0){
                    int price = 30*d;
                    f.dispose();
                    new Payment(price,d);
                }
                else{
                    j1.setOpaque(true);
                    j1.setBackground(Color.WHITE);
                    j1.setForeground(Color.RED);
                    j1.setText("SUV not available");
                }
                    
            }
        });
        
        
        f.add(auto);f.add(hatch);f.add(sedan);f.add(suv);f.add(r1);f.add(r2);f.add(r3);f.add(r4);
        
        b.setIcon(new ImageIcon("back.png"));
        b.setBounds(10,10,100,100);
        b.setOpaque(false);
        f.add(b);
        b.addActionListener(new ActionListener(){ //Button functionality
            public void actionPerformed(ActionEvent e){
                f.dispose();
                new Menu();
            }
        });
        
        l3.setBounds(0,0,1280,720);
        f.add(l3);
        
        f.setSize(1280,720);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class Menu extends Model{
    Menu(){
        /*JFrame f1 = new JFrame();
        
        JLabel area = new JLabel("You will be redirected to google maps. Please enter your");
        JLabel a = new JLabel("pickup and drop locations and check the distance");
        area.setBounds(100,10,400,50);
        a.setBounds(100,60,400,50);
        f1.add(area);
        f1.add(a);
        
        JButton b1 = new JButton();
        b1.setText("OK");
        b1.setBounds(250,120,50,50);
        b1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                f1.dispose();
                try{
                    Desktop desk = Desktop.getDesktop();
                    desk.browse(new URI("https://www.google.com/maps/dir///@13.622994,77.3135758,6.83z"));
                }
                catch(Exception e1){
                    e1.printStackTrace();
                }*/
                
                setMenu();
                l1 = new JLabel("  Enter pickup location");
                l1.setBounds(400,100,200,40);
                l1.setFont(new Font("Verdana",Font.PLAIN,16));
                l1.setOpaque(true);
                l1.setBackground(Color.WHITE);
                String board[] = {"--Select--","annaNagar","Tambaram","Navalur","Guindy","Broadway","Velachery"};
                JComboBox tf2 = new JComboBox(board);
                tf2.setBounds(650,100,150,40);
        
                
                l2 = new JLabel("  Enter drop location");
                l2.setBounds(400,300,200,40);
                l2.setFont(new Font("Verdana",Font.PLAIN,16));
                l2.setOpaque(true);
                l2.setBackground(Color.WHITE);
                JComboBox tf1 = new JComboBox(board);
                tf1.setBounds(650,300,150,40);
                
                /*JLabel l4 = new JLabel("  Enter distance(extra charges for wrong distance)");
                l4.setBounds(170,500,420,40);
                l4.setFont(new Font("Verdana",Font.PLAIN,16));
                l4.setOpaque(true);
                l4.setBackground(Color.WHITE);
                JTextField tf2 = new JTextField();
                tf2.setBounds(650,500,150,40);*/
                
                f.add(l1);f.add(l2);f.add(tf1);f.add(tf2);
                JButton b1 = new JButton("Next");
                b1.setBounds(555,570,100,50);
                f.add(b1);
                b1.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e){
                       String s1 = "" + tf2.getItemAt(tf2.getSelectedIndex());
                       String s2 = "" + tf1.getItemAt(tf1.getSelectedIndex());
                       if(s1.equals("--Select--")){/*pass*/}
                       else if(s1.equals(s2)) {/*pass}*/}
                       else{
                           int d = calDistance(s1,s2);
                           System.out.println(d);
                           f.dispose();
                           new ChooseCar(d);
                       }
                    }
                });
                
                l3.setBounds(0,0,1280,720);
                f.add(l3);
                
                f.setSize(1280,720);
                f.setLayout(null);
                f.setVisible(true);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                
        
        /*});
        f1.add(b1);
        f1.setSize(600,200);
        f1.setLayout(null);
        f1.setVisible(true);
        f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
        
        
        
    }
}

class Login extends Model{
    Login(){
        l1 = new JLabel(" Username");
        l1.setBounds(400,100,100,40);
        l1.setFont(new Font("Verdana",Font.PLAIN,16));
        l1.setOpaque(true);
        l1.setBackground(Color.WHITE);
        tf.setBounds(550,100,150,40);
        
        l2 = new JLabel(" Password");
        l2.setBounds(400,300,100,40);
        l2.setFont(new Font("Verdana",Font.PLAIN,16));
        l2.setOpaque(true);
        l2.setBackground(Color.WHITE);
        value.setBounds(550,300,150,40);
        
        f.add(l1);f.add(value);f.add(tf);f.add(l2);
       
        b.setIcon(new ImageIcon("back.png"));
        b.setBounds(10,10,100,100);
        b.setOpaque(false);
        f.add(b);
        b.addActionListener(new ActionListener(){ //Button functionality
            public void actionPerformed(ActionEvent e){
                f.dispose();
                new FirstWindow();
            }
        });
        
        JLabel j1 = new JLabel();
        j1.setBounds(455,400,300,40);
        f.add(j1);
        
        JButton b1 = new JButton(new ImageIcon("login.png"));
        b1.setBounds(555,450,100,100);
        f.add(b1);
        b1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String s1 = tf.getText();
                String s2 = value.getText();
                int s = check(s1,s2);
                j1.setFont(new Font("Verdana",Font.PLAIN,16));
                j1.setOpaque(true);
                j1.setBackground(Color.WHITE);
                j1.setForeground(Color.RED);
                j1.setText("Username or password is incorrect");
                
                    
                //f.dispose();
                //new Menu();
            }
        });
        
        l3.setBounds(0,0,1280,720);
        f.add(l3);
        
        f.setSize(1280,720);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
}

class SignUp extends Model{
    SignUp(){
        l1 = new JLabel(" Username");
        l1.setBounds(400,100,100,40);
        l1.setFont(new Font("Verdana",Font.PLAIN,16));
        l1.setOpaque(true);
        l1.setBackground(Color.WHITE);
        tf.setBounds(550,100,150,40);
        
        l2 = new JLabel(" Password");
        l2.setBounds(400,200,100,40);
        l2.setFont(new Font("Verdana",Font.PLAIN,16));
        l2.setOpaque(true);
        l2.setBackground(Color.WHITE);
        value.setBounds(550,200,150,40);
        
        JLabel l4 = new JLabel("Confirm Password");
        l4.setFont(new Font("Verdana",Font.PLAIN,16));
        l4.setOpaque(true);
        l4.setBackground(Color.WHITE);
        l4.setBounds(360,300,150,40);
        JPasswordField value1 = new JPasswordField();
        value1.setBounds(550,300,150,40);
        
        JLabel l5 = new JLabel("Phone number");
        l5.setFont(new Font("Verdana",Font.PLAIN,16));
        l5.setOpaque(true);
        l5.setBackground(Color.WHITE);
        l5.setBounds(380,400,130,40);
        JTextField tf1 = new JTextField();
        tf1.setBounds(550,400,150,40);
        
        JLabel error = new JLabel();
        error.setBounds(800,100,50,50);
        JLabel error1 = new JLabel();
        error1.setBounds(800,200,50,50);
        JLabel error2 = new JLabel();
        error2.setBounds(800,300,50,50);
        JLabel error3 = new JLabel();
        error3.setBounds(800,400,50,50);
        
        
        f.add(l1);f.add(value);f.add(tf);f.add(l2);f.add(l4);f.add(value1);f.add(l5);f.add(tf1);f.add(error);f.add(error1);f.add(error2);f.add(error3);
        
        
        
        b.setIcon(new ImageIcon("back.png"));
        b.setBounds(10,10,100,100);
        b.setOpaque(false);
        f.add(b);
        JLabel j1 = new JLabel();
        j1.setBounds(455,500,250,40);
        f.add(j1);
        b.addActionListener(new ActionListener(){ //Button functionality
            public void actionPerformed(ActionEvent e){
                f.dispose();
                new FirstWindow();
            }
        });
        
        
        
        JButton b1 = new JButton(new ImageIcon("login.png"));
        b1.setBounds(555,550,100,100);
        f.add(b1);
        b1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String s1 = tf.getText();
                String s2 = value.getText();
                String s3 = value1.getText();
                String s4 = tf1.getText();
                if(s2.length()<8)
                    error1.setIcon(new ImageIcon("error.png"));
                if (s2.equals(s3)==false){
                    error1.setIcon(new ImageIcon("error.png"));
                    error2.setIcon(new ImageIcon("error.png"));
                }
                if (s4.length()!=10)
                    error3.setIcon(new ImageIcon("error.png"));
                if(check(s1," ")==1){
                    error.setIcon(new ImageIcon("error.png"));
                    j1.setFont(new Font("Verdana",Font.PLAIN,16));
                    j1.setOpaque(true);
                    j1.setBackground(Color.WHITE);
                    j1.setForeground(Color.RED);
                    j1.setText("  Username already exists.");
                    
                    
                }
                    
                if(s2.equals(s3) && s4.length()==10 && s2.length()>=8 && check(s1,s2)==0){
                    write_file(s1,s2);
                    create_file(s1);
                    f.dispose();
                    new FirstWindow();
                }
                    
                
                
                
            }
        });
        
        l3.setBounds(0,0,1280,720);
        f.add(l3);
        
        f.setSize(1280,720);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
}

class FirstWindow extends Model{
    FirstWindow(){
        //f.removeAll();
        calcPrice c1 = new calcPrice();
        c1.printTime();
        l1 = new JLabel("Welcome to SAIL");
        l1.setBounds(390,50,500,300);
        l1.setFont(new Font("Verdana",Font.BOLD,50));
        l1.setForeground(Color.WHITE);
        f.add(l1);
        
        b.setText("Login");
        b.setBounds(100,400,150,100);
        b.setFont(new Font("Verdana",Font.BOLD,20));
        //b.setBackground(Color.BLACK);
        //b.setForeground(Color.WHITE);
        b.addActionListener(new ActionListener(){ //Button functionality
            public void actionPerformed(ActionEvent e){
                f.dispose();
                new Login();
            }
        });
        f.add(b);
        JButton b1 = new JButton("Sign Up");
        b1.setBounds(1000,400,150,100);
        b1.setFont(new Font("Verdana",Font.BOLD,20));
        //b1.setBackground(Color.BLACK);
        //b1.setForeground(Color.WHITE);
        b1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                f.dispose();
                new SignUp();
            }
        });
        f.add(b1);
        
        //l2 = new JLabel(new ImageIcon("bg.jpg"));  //Background
        l3.setBounds(0,0,1280,720);
        f.add(l3);
        
        Image img = Toolkit.getDefaultToolkit().getImage("");
        
        f.getContentPane().setBackground(Color.WHITE);
        f.setSize(1280,720);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

public class main extends calcPrice{
    
    public static void main(String[] args){
        //Model f1 = new Model();
        new FirstWindow();
        
    }
}
