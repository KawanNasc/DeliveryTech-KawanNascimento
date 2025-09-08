package com.deliverytech.delivery_api.services.interfaces;

import com.deliverytech.delivery_api.data.request.CalculateRequestDTORequest;
import com.deliverytech.delivery_api.data.request.RequestDTORequest;
import com.deliverytech.delivery_api.data.response.CalculateRequestDTOResponse;
import com.deliverytech.delivery_api.data.response.RequestDTOResponse;

import com.deliverytech.delivery_api.data.request.StatusRequestDTORequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface RequestServiceInterface {
    RequestDTOResponse createRequest(RequestDTORequest dto);
    
    Page<RequestDTOResponse> listRequest(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    RequestDTOResponse findRequestPerId(Long id);

    List<RequestDTOResponse> findRequestsPerClient(Long clientId);

    RequestDTOResponse updateStatusRequesT(Long id, StatusRequestDTORequest statusRequest);

    CalculateRequestDTOResponse calculateTotalRequest(CalculateRequestDTORequest request);

    void cancelRequest(Long id);
}