package com.ecom.controller.payment;


import com.ecom.dto.payment.MoyasarPaymentResponse;
import com.ecom.dto.payment.PaymentRequest;
import com.ecom.service.payment.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private  final PaymentService paymentService;

    public PaymentController (PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<MoyasarPaymentResponse> createPayment(@Valid @RequestBody
                                                              PaymentRequest paymentRequest ){
         MoyasarPaymentResponse paymentResponse = paymentService.createPayment(paymentRequest);
         return ResponseEntity.ok(paymentResponse);
    }
    @GetMapping("/{paymentId}")
    public ResponseEntity<MoyasarPaymentResponse> getPayment(@PathVariable String paymentId){
        MoyasarPaymentResponse paymentResponse = paymentService.getPayment(paymentId);
        return  ResponseEntity.ok(paymentResponse);
    }
}
