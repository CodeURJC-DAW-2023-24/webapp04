package webapp4.main.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import webapp4.main.model.Transfer;
import webapp4.main.repository.TransferRepository;
import webapp4.main.transferDataUtils.ProcessedTransfer;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DynamicContentController {
    private static int currentPage = 10;
    private static int pageSize = 10;
    @Autowired
    private TransferRepository transferRepository;
    @GetMapping("/load-dynamic-content")
    @ResponseBody
    public String loadDynamicContent(@RequestParam String client_iban, HttpServletRequest request){
        // --- Setting transfer list ---
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        List<Transfer> transferList = transferRepository.findBySenderOrReceiverContaining(client_iban, pageable);
        ArrayList<ProcessedTransfer> processedTransferList = new ArrayList<>();
        for (Transfer transfer : transferList) {
            ProcessedTransfer processedTransfer = new ProcessedTransfer(transfer, client_iban);
            processedTransferList.add(processedTransfer);
        }
        currentPage += pageSize;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(processedTransferList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Error al convertir a JSON";
        }
    }
}
