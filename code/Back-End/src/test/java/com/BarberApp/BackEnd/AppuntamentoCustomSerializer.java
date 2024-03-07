package com.BarberApp.BackEnd;

import com.BarberApp.BackEnd.model.appuntamento.Appuntamento;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class AppuntamentoCustomSerializer extends StdSerializer<Appuntamento> {
    public AppuntamentoCustomSerializer(){
        this(null);
    }
    public AppuntamentoCustomSerializer(Class<Appuntamento> a){
        super(a);
    }

    @Override
    public void serialize(Appuntamento value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id",value.getId());
        gen.writeStringField("date",value.getDate().toString());
        gen.writeStringField("time",value.getTime().toString());
        gen.writeObjectField("cliente", value.getCliente());
        gen.writeObjectField("dipendente",value.getDipendente());
        gen.writeObjectField("servizio",value.getServizio());
        gen.writeEndObject();
    }


}
