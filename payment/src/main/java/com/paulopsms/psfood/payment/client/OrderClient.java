package com.paulopsms.psfood.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("order-ms")
public interface OrderClient {

    @RequestMapping(method = RequestMethod.PUT, value = "/orders/{id}/status")
    void updateStatus(@PathVariable("id") Long id, @RequestBody OrderStatusRequest request);
}
