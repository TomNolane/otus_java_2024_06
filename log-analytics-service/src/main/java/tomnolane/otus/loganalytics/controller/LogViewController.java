package tomnolane.otus.loganalytics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;
import tomnolane.otus.loganalytics.service.LogService;

@Controller
public class LogViewController {
    private final LogService logService;

    public LogViewController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/logs/view")
    public String viewLogs(Model model) {
        Flux<?> logs = logService.getAllLogs();
        model.addAttribute("logs", logs);
        return "logs";
    }
}
