package br.com.oracleone.ScreamMatch.model;

public enum Categoria {
    ACAO("Action"),
    ROMANCE("Romance"),
    COMEDIA("Comedy"),
    DRAMA("Drama"),
    HORROR("Horror"),
    FICCAO("Fiction"),
    CRIME("Crime"),
    SEM_CATEGORIA(null);

    private final String categoriaImdb;

    Categoria(String categoriaImdb){
        this.categoriaImdb = categoriaImdb;
    }

    public static Categoria fromString(String text){
        for(Categoria categoria: Categoria.values()){
            if(categoria.categoriaImdb.equalsIgnoreCase(text) ){
                return categoria;
            }
        }
            throw new IllegalArgumentException("Nenhuma categoria encontrada para a Sring fornecida: "+ text);
    }
}
