
// quiz maker
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class file {
    static Scanner scan = new Scanner(System.in);
    @SuppressWarnings("unchecked")
    public static void main(String args[]) throws EmptyFile {
        try {
            // BufferedWriter f = new BufferedWriter(new FileWriter("f.txt"));
            BufferedReader file = new BufferedReader(new FileReader("f.txt"));
            String line;
            ArrayList<String> lines = new ArrayList<>();
            boolean empty = true;
            while ((line = file.readLine()) != null) {
                empty = false;
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
            if(empty)
            {
                file.close();
                throw new EmptyFile("FileEmpty");
            }
            LinkedHashMap<Integer, String> lm = new LinkedHashMap<>();
            for(int i = 0; i < lines.size(); i++)
            {   
                lm.put(i, lines.get(i));
            }
            ObjectOutputStream f = new ObjectOutputStream(new FileOutputStream("f.ser"));
            for(Integer key: lm.keySet())
            {
                System.out.println("Answer for: " + lm.get(key));
                String answer =  scan.nextLine();
                QuestionsWanswers<String> q= new QuestionsWanswers<>(lm.get(key), 0, answer);
                f.writeObject(q);
            }
            ObjectInputStream fr = new ObjectInputStream(new FileInputStream("f.ser"));
            Object obj;
            System.out.println("Question answers stored are: ");
            try
            {
            
                while(true)
                {
                    obj = fr.readObject();
                    if(obj instanceof QuestionsWanswers)
                    {
                        QuestionsWanswers<String> q = (QuestionsWanswers<String>) obj;
                        System.out.println("Q: "+ q.question );
                        System.out.println("A: "+q.answer);
                        System.out.println();
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
            finally
            {
                f.close();
                fr.close();
                file.close();   
            }
            

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        finally
        {
          
        }

        // BufferedReader f = new BufferedReader(new FileReader("f.txt"));
    }

}
class EmptyFile extends Exception
{
    EmptyFile(String msg)
    {
        super(msg);
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

class QuestionsWanswers<answerType> extends Questions {
    answerType answer;

    QuestionsWanswers(String question, int marks, answerType answer) {
        super(question, marks);
        this.answer = answer;
    }

}