import java.lang.reflect.InvocationTargetException;

public class TestInvoke {


    public static void checkUser(Object obj, Class<?> clazz) {
        try {
            if (clazz.getMethod("getUserId").invoke(obj) == null) {
                clazz.getMethod("setUserId", Integer.class).invoke(obj,
                        ((String) clazz.getMethod("getPin").invoke(obj)).length());
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(obj);
    }


    public static void main(String[] args) {
        Ad ad = new Ad("test", null);
        checkUser(ad, Ad.class);
        System.out.println(ad);
        checkUser(new Ad("ad", null), Ad.class);
        checkUser(new AdGroup("adgroup", 12), AdGroup.class);
        checkUser(new Campaign("", null), Campaign.class);
    }
}


class Ad {
    String pin = "ad";
    Integer userId;

    public Ad(String pin, Integer userId) {
        this.pin = pin;
        this.userId = userId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Ad{" +
                "pin='" + pin + '\'' +
                ", userId=" + userId +
                '}';
    }
}

class AdGroup {
    String pin;
    Integer userId;

    public AdGroup(String pin, Integer userId) {
        this.pin = pin;
        this.userId = userId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "AdGroup{" +
                "pin='" + pin + '\'' +
                ", userId=" + userId +
                '}';
    }
}

class Campaign {
    String pin;
    Integer userId;

    public Campaign(String pin, Integer userId) {
        this.pin = pin;
        this.userId = userId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "pin='" + pin + '\'' +
                ", userId=" + userId +
                '}';
    }
}