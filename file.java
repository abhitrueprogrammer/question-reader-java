
// quiz maker
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class file {
    @SuppressWarnings("unchecked")
    public static void main(String args[]) {

        try {
            // BufferedWriter f = new BufferedWriter(new FileWriter("f.txt"));
            BufferedReader file = new BufferedReader(new FileReader("f.txt"));
            String line;
            ArrayList<String> lines = new ArrayList<>();
            while ((line = file.readLine()) != null) {
                String lineArr[] = line.split("\\?");
                for(int i =0; i< lineArr.length; i++)
                {
                    if(lineArr[i].length() < 2)
                    {

                        continue;
                    }
                    String lower = lineArr[i].toLowerCase();
                    lower += "?";
                    lower = lower.strip();
                    boolean isDup = false;
                    for(int j = 0; j < lines.size(); j++)
                    {
                        if(lines.get(i).equals(lower))
                        {
                            isDup = true;
                            break;
                        }
                    }
                    if(isDup) continue;

                    lines.add(lower);

                }
            
            }
            LinkedHashMap<Integer, String> lm = new LinkedHashMap<>();
            for(int i = 0; i < lines.size(); i++)
            {   
                lm.put(i, lines.get(i));
            }
            ObjectOutputStream f = new ObjectOutputStream(new FileOutputStream("f.ser"));
            for(Integer key: lm.keySet())
            {
                QuestionsWtime<Integer> q= new QuestionsWtime<>(lm.get(key), 0, 10);
                f.writeObject(q);
            }
            ObjectInputStream fr = new ObjectInputStream(new FileInputStream("f.ser"));
            Object obj;
            try
            {
            
                while(true)
                {
                    obj = fr.readObject();
                    if(obj instanceof QuestionsWtime)
                    {
                        QuestionsWtime<Integer> q = (QuestionsWtime<Integer>) obj;
                        System.out.println(q.question );
                    } 
                }
            }
            catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch(Exception e)
            {

            }
            f.close();
            fr.close();
            file.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 

        // BufferedReader f = new BufferedReader(new FileReader("f.txt"));
    }

}

class Questions implements Serializable {
    String question = "";
    int marks = 0;

    Questions(String question, int marks) {
        this.question = question;
        this.marks = marks;
    }
}

class QuestionsWtime<timeType> extends Questions {
    timeType time;

    QuestionsWtime(String question, int marks, timeType time) {
        super(question, marks);
        this.time = time;
    }

}