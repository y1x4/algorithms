

class Event {
    private Thief thief;
    public Event(Thief t) {
        thief = t;
    }
}

interface CopListener {
    void shot(Event event);
}

class Thief {

    private CopListener listener;

    public void registerListener(CopListener listener) {
        this.listener = listener;
    }

    public void steal() {
        // 偷之前，告诉警察
        if (listener != null) {
            Event event = new Event(this);
            // 喂，有胆开枪啊！
            listener.shot(event);
        }
        // 偷东西
        System.out.println("to steal money...");
    }
}

public class Observer {
    public static void main(String[] args) {
        Thief thief = new Thief();
        // 监听器，直接new一个接口的匿名类对象
        CopListener thiefListener = new CopListener() {
            @Override
            public void shot(Event event) {
                System.out.println("啪啪啪！！！！");
            }
        };

        // 注册监听
        thief.registerListener(thiefListener);

        // 特定行为，触发监听器：内部调用listener.shot()
        thief.steal();
    }
}
