package com.deliverytech.delivery_api.services.interfaces;

import com.deliverytech.delivery_api.data.request.ClientDTORequest;
import com.deliverytech.delivery_api.data.response.ClientDTOResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientServiceInterface {
    ClientDTOResponse registerClient(ClientDTORequest dto);

    ClientDTOResponse findClientPerId(Long id);

    ClientDTOResponse findClientPerEmail(String email);

    ClientDTOResponse updateClient(Long id, ClientDTORequest dto);

    ClientDTOResponse activateDesativateClient(Long id);

    Page<ClientDTOResponse> listActiveClients(Pageable pageable);
}