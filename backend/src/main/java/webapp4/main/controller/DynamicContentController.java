package webapp4.main.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import webapp4.main.service.TransferService;
import javax.servlet.http.HttpServletRequest;

@Controller
public class DynamicContentController {
    @Autowired
    private TransferService transferService;
    @GetMapping("/load-dynamic-content")
    @ResponseBody
    public String loadDynamicContent(@RequestParam String client_iban, HttpServletRequest request){
        try {
            return new ObjectMapper().writeValueAsString(transferService.getProcessedTransfers(client_iban));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Error while converting to JSON";
        }
    }
}