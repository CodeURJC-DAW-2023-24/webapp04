package webapp4.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import webapp4.main.model.Transfer;
import webapp4.main.repository.TransferRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TransfersManagerController {
    @Autowired
    private TransferRepository transferRepository;
    private List<Transfer> allTransfers;
    @ModelAttribute
    public void auxMethod(Model model){
        allTransfers = transferRepository.findAll();
    }
    @GetMapping("/transfers_manager")
    public String home(Model model){
        return "transfers_manager_page";
    }
    @GetMapping("/transfers_manager_load")
    @ResponseBody
    public List<Transfer> transferManager(@RequestParam int startIndex, @RequestParam int chunkSize){
        if (startIndex + chunkSize < allTransfers.size()){
            return allTransfers.subList(startIndex, startIndex + chunkSize);
        }
        return new ArrayList<>();
    }
}
