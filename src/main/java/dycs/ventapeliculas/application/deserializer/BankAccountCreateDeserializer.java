package dycs.ventapeliculas.application.deserializer;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.modelmapper.jackson.JsonNodeValueReader;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dycs.ventapeliculas.application.dto.VentaPeliculaCreateDto;

public class BankAccountCreateDeserializer extends JsonDeserializer<VentaPeliculaCreateDto> {
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public VentaPeliculaCreateDto deserialize(JsonParser jsonParser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		VentaPeliculaCreateDto bankAccountCreateDto = null;
		String json = jsonParser.readValueAsTree().toString();
		JsonNode node = new ObjectMapper().readTree(json);
		modelMapper.getConfiguration()
		  .addValueReader(new JsonNodeValueReader());
		try {
		  bankAccountCreateDto = modelMapper.map(node, VentaPeliculaCreateDto.class);
		} catch(Exception ex) {
			bankAccountCreateDto = new VentaPeliculaCreateDto();
		}
		return bankAccountCreateDto;
	}
}
