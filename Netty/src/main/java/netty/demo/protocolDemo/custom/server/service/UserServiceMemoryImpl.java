package netty.demo.protocolDemo.custom.server.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserServiceMemoryImpl implements UserService {
    private Map<String, String> allUserMap = new ConcurrentHashMap<>();

    {
        allUserMap.put("NieChen", "123");
        allUserMap.put("LiSi", "123");
        allUserMap.put("HuangJiaNuo", "123");
        allUserMap.put("PanRui", "123");
        allUserMap.put("ZhaoJunMei", "123");
    }

    @Override
    public boolean login(String username, String password) {
        String pass = allUserMap.get(username);
        if (pass == null) {
            return false;
        }
        return pass.equals(password);
    }
}
