import java.io.ObjectInputStream;
import java.util.Queue;
import java.io.IOException;
/**
 * Listen for a specific client.
 *
 */
public class ListenFor implements Runnable{
    /** the Object Input Stream. */
    private ObjectInputStream ois;
    private Queue<Message> listenQueue;
    private Queue<Message> listenDelayQueue;
    private Configuration myConfig;
    public ListenFor(ObjectInputStream oistream, Queue listenQ,Queue listendqueue, Configuration c) {
        this.ois = oistream;
        this.listenQueue = listenQ;
        this.listenDelayQueue = listendqueue;
        this.myConfig = c;
    }
    @Override
    public void run() {
        while(true) {
            try {
                Message newMes = (Message)ois.readObject();
                //
                System.out.println("New message! --");
                System.out.println(newMes.toString());   
                String checkResult = checkReceiveRule(newMes,myConfig);
                if (checkResult != null) {
                    if (checkResult.equals("drop")) {
                        continue;
                    } 
                    else if(checkResult.equals("delay")){
                        listenDelayQueue.offer(newMes);
                        
                    }else{}
                }
                else {
                    listenQueue.offer(newMes);
                    while (!listenDelayQueue.isEmpty()){
                        Message msg = listenDelayQueue.poll();
                        listenQueue.offer(newMes);
                    }
               }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } 
        }
    }
    public String checkReceiveRule(Message newMes ,Configuration myConfig){
        for (Rule r : myConfig.receiveRules) {
            int result = r.match(newMes);
            if (result == 1) {
                if (r.get_action().equals("dropAfter")){
                    return null;
                }
                return r.get_action();
            }
            if (result == 2){
                return "drop";
            }
        }
        return null;
    }

}
