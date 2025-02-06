package tomnolane.otus.loganalytics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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

    @GetMapping("/logs/metrics")
    public String viewMetrics(Model model) {
        Mono<Long> totalLogs = logService.countLogs();
        Mono<Long> errorLogs = logService.countLogsByLevel("ERROR");
        Mono<Long> infoLogs = logService.countLogsByLevel("INFO");
        Mono<Long> successLogs = logService.countLogsByLevel("SUCCESS");

        model.addAttribute("totalLogs", totalLogs);
        model.addAttribute("errorLogs", errorLogs);
        model.addAttribute("infoLogs", infoLogs);
        model.addAttribute("successLogs", successLogs);

        return "metrics";
    }
}
