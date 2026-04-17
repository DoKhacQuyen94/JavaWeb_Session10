package com.example.ss10.service;

import com.example.ss10.dto.BorrowRequestDTO;
import com.example.ss10.model.Entity;
import com.example.ss10.model.ItemType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MockDataService {
    private static List<Entity> items = new ArrayList<>();
    private static List<BorrowRequestDTO> requests = new ArrayList<>();
    private static int nextId = 4;

    static {
        // Khởi tạo không có ảnh sẵn, bạn hãy dùng Admin để upload ảnh
        items.add(new Entity(1, "Màn hình Dell 24 inch", ItemType.EQUIPMENT, null, 10, "Màn hình độ phân giải Full HD"));
        items.add(new Entity(2, "Phòng Lab 402", ItemType.LAB_ROOM, null, 1, "Phòng thực hành mạng"));
        items.add(new Entity(3, "Bộ kit Arduino Uno", ItemType.EQUIPMENT, null, 5, "Bộ kit thực hành IOT"));
    }

    public List<Entity> getAllItems() {
        return items;
    }

    public Entity getItemById(int id) {
        return items.stream().filter(i -> i.getId() == id).findFirst().orElse(null);
    }

    public void addRequest(BorrowRequestDTO request) {
        requests.add(request);
        
        if (request.getItemId() != null) {
            Entity item = getItemById(request.getItemId().intValue());
            if (item != null) {
                int newStock = item.getStock() - request.getQuantity();
                item.setStock(Math.max(0, newStock));
            }
        }
    }

    public List<BorrowRequestDTO> getAllRequests() {
        return requests;
    }

    public void saveItem(Entity item) {
        if (item.getId() == 0) {
            item.setId(nextId++);
            items.add(item);
        } else {
            Entity existing = getItemById(item.getId());
            if (existing != null) {
                existing.setName(item.getName());
                existing.setType(item.getType());
                existing.setStock(item.getStock());
                existing.setDescription(item.getDescription());
                if (item.getImageUrl() != null) {
                    existing.setImageUrl(item.getImageUrl());
                }
            }
        }
    }

    public void deleteItem(int id) {
        items.removeIf(item -> item.getId() == id);
    }
}
