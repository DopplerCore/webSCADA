package by.savushkin.scada_server.controllers;

import by.savushkin.scada_server.models.Tag;
import by.savushkin.scada_server.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@CrossOrigin(origins = "*") // Разрешить все источники
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping
    public List<Tag> getTags() {
        return tagService.getTags();
    }

    @GetMapping("/{id}")
    public Tag getTagById(@PathVariable int id) {
        return tagService.getTagById(id);
    }

    @PostMapping("/{id}")
    public Tag updateTagValue(@PathVariable int id, @RequestBody Tag tag) {
        return tagService.updateTagValue(id, tag.getValue());
    }
}
