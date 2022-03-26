package server;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class TestRestApiController {

   static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/time")
    @PreAuthorize("hasAuthority('SCOPE_accesstime-consent-req') or hasAuthority('SCOPE_accesstime-noconsent-req')") //
    public String getMessageOfTheDay(Principal principal) {
        return "Hi  " + principal.getName() + ", current time is "+ LocalDateTime.now().format(formatter)+"";
    }
}
