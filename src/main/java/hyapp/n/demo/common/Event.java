package hyapp.n.demo.common;

/**
 * @author Wang Zhen
 * @date 2020/7/24 11:28 上午
 */
public enum Event {
    JOIN("join"),
    LEAVE("leave"),
    READY("ready"),
    UNREADY("unready"),
    START("start"),
    FINISH("finish"),
    RESULT("result"),
    BARRAGE("barrage");

    private String event;

    private Event(String event) {
        this.event = event;
    }

    public String getEvent() {
        return event;
    }
}
