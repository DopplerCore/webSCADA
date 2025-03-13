package by.savushkin.scada_server.services;

import by.savushkin.scada_server.models.Tag;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {
    private List<Tag> tags = new ArrayList<>();

    @PostConstruct
    public void init() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Загрузка файла из resources
            InputStream inputStream = new ClassPathResource("tags.json").getInputStream();
            TagsWrapper tagsWrapper = objectMapper.readValue(inputStream, TagsWrapper.class);
            tags = tagsWrapper.getTags();
        } catch (IOException e) {
            e.printStackTrace();
            // В случае ошибки инициализируем пустым списком
            tags = new ArrayList<>();
        }
    }

    public List<Tag> getTags() {
        return tags;
    }

    public Tag getTagById(int id) {
        return tags.stream().filter(tag -> tag.getId() == id).findFirst().orElse(null);
    }

    public Tag updateTagValue(int id, double value) {
        Tag tag = getTagById(id);
        if (tag != null) {
            tag.setValue(value);
        }
        return tag;
    }

    public void updateTags(List<Tag> updatedTags) {
        this.tags = updatedTags;
    }

    // Вспомогательный класс для десериализации JSON
    private static class TagsWrapper {
        private List<Tag> tags;

        public List<Tag> getTags() {
            return tags;
        }

        public void setTags(List<Tag> tags) {
            this.tags = tags;
        }
    }
}