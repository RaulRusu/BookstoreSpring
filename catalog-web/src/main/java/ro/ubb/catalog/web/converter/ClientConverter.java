package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.domain.Client;
import ro.ubb.catalog.web.dto.ClientDto;

@Component
public class ClientConverter extends BaseConverter<Integer, Client, ClientDto>{
    @Override
    public Client convertDtoToModel(ClientDto dto) {
        Client client = Client.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .build();

        client.setId(dto.getId());
        return client;
    }

    @Override
    public ClientDto convertModelToDto(Client client) {
        ClientDto clientDto = ClientDto.builder()
                .name(client.getName())
                .age(client.getAge())
                .build();

        clientDto.setId(client.getId());
        return clientDto;
    }
}
