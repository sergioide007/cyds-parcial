package dycs.bankaccounts.application.deserializer;

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

import dycs.bankaccounts.application.dto.BankAccountCreateDto;

public class BankAccountCreateDeserializer extends JsonDeserializer<BankAccountCreateDto> {
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public BankAccountCreateDto deserialize(JsonParser jsonParser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		BankAccountCreateDto bankAccountCreateDto = null;
		String json = jsonParser.readValueAsTree().toString();
		JsonNode node = new ObjectMapper().readTree(json);
		modelMapper.getConfiguration()
		  .addValueReader(new JsonNodeValueReader());
		try {
		  bankAccountCreateDto = modelMapper.map(node, BankAccountCreateDto.class);
		} catch(Exception ex) {
			bankAccountCreateDto = new BankAccountCreateDto();
		}
		return bankAccountCreateDto;
	}
}
