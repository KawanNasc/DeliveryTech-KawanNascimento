package com.deliverytech.delivery_api.services.interfaces;

import com.deliverytech.delivery_api.data.request.ClientDTORequest;
import com.deliverytech.delivery_api.data.response.ClientDTOResponse;

import java.util.List;

public interface ClientServiceInterface {
    ClientDTOResponse registerClient(ClientDTORequest dto);

    ClientDTOResponse findClientPerId(Long id);

    ClientDTOResponse findClientPerEmail(String email);

    ClientDTOResponse updateClient(Long id, ClientDTORequest dto);

    ClientDTOResponse activateDesativateClient(Long id);

    List<ClientDTOResponse> listActiveClients();
}