package by.jahimees.coworking.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OldObjectCacheService {

    private final Map<String, Object> cache = new HashMap<>();

    public void put(String requestId, Object object) {
        cache.put(requestId, object);
    }

    public Object get(String requestId) {
        return cache.get(requestId);
    }

    public Object pop(String requestId) {
        return cache.remove(requestId);
    }

}
