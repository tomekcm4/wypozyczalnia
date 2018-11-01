package pl.moja.wypozyczalnia.utils.converters;

import pl.moja.wypozyczalnia.database.models.Client;
import pl.moja.wypozyczalnia.modelFx.ClientFx;


public class ConverterClient {


    public static Client converToClient(ClientFx clientFx){
        Client client = new Client();
        client.setId(clientFx.getId());
        client.setName(clientFx.getName());
        client.setSurname(clientFx.getSurname());
        return client;
    }

    public static ClientFx convertToClientFx(Client client){
        ClientFx clientFx = new ClientFx();
        clientFx.setId(client.getId());
        clientFx.setName(client.getName());
        clientFx.setSurname(client.getSurname());
        return clientFx;
    }
}
