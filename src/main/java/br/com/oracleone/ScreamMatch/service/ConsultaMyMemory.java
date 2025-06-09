package br.com.oracleone.ScreamMatch.service;

import br.com.oracleone.ScreamMatch.model.DadosTraducao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.yaml.snakeyaml.util.UriEncoder;

public class ConsultaMyMemory {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ConsumoApi consumoApi = new ConsumoApi();
    private static final String url = "https://api.mymemory.translated.net";
    private static final String langpair = UriEncoder.encode("en|pt-br");

    public static String traducao(String text){
        String textoEncode = UriEncoder.encode(text);

        String json = consumoApi.obterDados(url+"/get?q="+ textoEncode +"&langpair="+langpair);

        DadosTraducao traducao;

        try {
            traducao= mapper.readValue(json,DadosTraducao.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return traducao.dadosResposta().textoTraduzido();
    }

}
