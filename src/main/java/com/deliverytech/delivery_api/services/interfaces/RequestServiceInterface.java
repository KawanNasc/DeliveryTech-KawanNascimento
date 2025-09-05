package com.deliverytech.delivery_api.services.interfaces;

import com.deliverytech.delivery_api.data.request.RequestDTORequest;
import com.deliverytech.delivery_api.data.response.RequestDTOResponse;
import com.deliverytech.delivery_api.data.ItemRequestDTO;

import com.deliverytech.delivery_api.enums.StatusRequest;

import java.math.BigDecimal;
import java.util.List;

public interface RequestServiceInterface {
    RequestDTOResponse createRequest(RequestDTORequest dto);

    RequestDTOResponse findRequestPerId(Long id);

    List<RequestDTOResponse> findRequestsPerClient(Long clientId);

    RequestDTOResponse updateStatusRequesT(Long id, StatusRequest statusRequest);

    BigDecimal calculateTotalRequest(List<ItemRequestDTO> items);

    void cancelRequest(Long id);
}
