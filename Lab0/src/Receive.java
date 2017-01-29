import java.util.Queue;

public class Receive implements Runnable{
    private Queue<Message> receiveQueue;
    public Receive(Queue<Message> receiveQ) {
        this.receiveQueue = receiveQ;
//        System.out.println(receiveQueue);
    }
    @SuppressWarnings("resource")
    @Override
    public void run(){
        try {
            while (true) {
                try {
                    Message receMes = receive();
                } catch (Exception e) {
                    e.printStackTrace();
                } 
            }
        } catch(Exception e) {
            e.printStackTrace();
        } 
    }  
    public Message receive(){
        Message msg = null;
        if (!receiveQueue.isEmpty()){
            System.out.println("not empty");
            msg = receiveQueue.poll();
            System.out.println("receive from queue" + msg);
        }
        return msg;
    }
}
