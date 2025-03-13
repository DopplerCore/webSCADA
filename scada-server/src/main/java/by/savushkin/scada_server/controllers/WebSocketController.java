package by.savushkin.scada_server.controllers;

import by.savushkin.scada_server.handlers.MyWebSocketHandler;
import by.savushkin.scada_server.models.Tag;
import by.savushkin.scada_server.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

@Controller
public class WebSocketController {

    @Autowired
    private MyWebSocketHandler webSocketHandler;

    @Autowired
    private TagService tagService;

    @Scheduled(fixedRate = 2000)
    public void sendUpdates() {
        List<Tag> tags = tagService.getTags();
        DecimalFormat df = new DecimalFormat("#.#", new DecimalFormatSymbols(Locale.US)); // Используем точку как разделитель
        tags.forEach(tag -> {
            if (tag.getUnit().equals("°C") || tag.getUnit().equals("kPa") || tag.getUnit().equals("L/min")) {
                double newValue = tag.getValue() + (Math.random() * 2 - 1);
                String formattedValue = df.format(newValue); // Форматируем значение
                tag.setValue(Double.parseDouble(formattedValue)); // Преобразуем обратно в число
            }
        });
        webSocketHandler.sendTagsToAllClients();
    }
}