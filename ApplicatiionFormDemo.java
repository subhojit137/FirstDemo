
package applicatiionformdemo;
import java.io.*;
import java.util.*;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.text.*;

class Member implements Serializable
{
    private int memID;
    private String name;
    private String add;
    
    public Member() {}
    public Member(int  mi,String n,String a)
    {
        memID=mi;
        name=n;
        add=a;
    }
    public int getID()  {return memID;}
    public String getname()  {return name;}
    public String getadd()     {return add;}
    
}

public class ApplicatiionFormDemo extends Application
{
    int count=0;
    @Override
    public void start(Stage stages) throws Exception
    {
        HashMap<Integer,Member> hm=new HashMap<>();
    
        Font f=new Font("Times New Roman",20);
        Font f1=new Font("Dubai",20);
        
         Label l1=new Label("Member ID ");    l1.setFont(f);
         Label l2=new Label("Member Name ");   l2.setFont(f);
         Label l3=new Label("Member Address ");   l3.setFont(f);
         
         ComboBox<Integer> cb=new ComboBox<>();
         cb.setStyle("-fx-font-size:20");
         
         TextField name=new TextField();   name.setFont(f1);
         TextField add=new TextField();      add.setFont(f1);
        

         name.setPrefColumnCount(15);
         add.setPrefColumnCount(25);
        
         
         Button save=new Button("Save");            save.setFont(f);
         Button create=new Button("Create");     create.setFont(f);
         Button search=new Button("Search");    search.setFont(f);
         
         HBox hb1=new HBox();    hb1.getChildren().addAll(l1,cb);
         hb1.setAlignment(Pos.CENTER);

         HBox hb2=new HBox();    hb2.getChildren().addAll(l2,name);
         hb2.setAlignment(Pos.CENTER);
         
         HBox hb3=new HBox();    hb3.getChildren().addAll(l3,add);
         hb3.setAlignment(Pos.CENTER);

         HBox hb4=new HBox();   hb4.getChildren().addAll(create,save,search);
         hb4.setAlignment(Pos.CENTER);
 
         VBox vb=new VBox();
         vb.getChildren().addAll(hb1,hb2,hb3,hb4);
         vb.setAlignment(Pos.CENTER);
         
         create.setOnAction(e->                                                                                                //CREATING A DETAILS
         {
               name.setText("");
               add.setText("");
               cb.setValue(++count);
               cb.getItems().add(count);
         });
         save.setOnAction(e->{
             Member m=new Member(cb.getValue(),name.getText(),add.getText());         //FOR SAVING ALL THE DATA INTO HASHMAP
             hm.put(count, m);
             
             try                                                                                                                                    //FOR SAVING ALL THE THINGS INTO A FILE 
             {
                 FileOutputStream fos=new FileOutputStream("mem.txt");
                 ObjectOutputStream oos=new ObjectOutputStream(fos);
                 oos.writeInt(hm.size());
                 for(Member mm:hm.values())
                 {
                     oos.writeObject(mm);
                 }
             }
             catch(Exception ex) {}
                  });
         try                                                                                                         //FOR RETRIVING THE DATA
         {
             FileInputStream fis=new FileInputStream("mem.txt");
             ObjectInputStream ois=new ObjectInputStream(fis);
             int counts=ois.readInt();
             for(int i=0;i<counts;i++)
             {
                 Member m=(Member)ois.readObject();
                 hm.put(m.getID(), m);
                 if(m.getID()>count)
                     count=m.getID();
                 cb.getItems().add(m.getID());
             }
         }
         catch(Exception ex) {}
                                                                                                                      //SEARCHING A PARTICULAR DETAILS
         search.setOnAction(e->{
         
             if(cb.getValue()!=null)
             {
               int mi=cb.getValue();
               Member m=hm.get(mi);
               name.setText(m.getname());
               add.setText(m.getadd());
             }
                });

         Scene sc=new Scene(vb,800,500);
         stages.setScene(sc);
         stages.setTitle("Member Form");
         stages.show();

    }

    public static void main(String[] args)
    {
        launch(args);
    }
    
}
