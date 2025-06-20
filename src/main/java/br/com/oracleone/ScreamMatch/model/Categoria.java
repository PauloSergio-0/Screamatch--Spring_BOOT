package br.com.oracleone.ScreamMatch.model;

public enum Categoria {
    ACAO("Action", "Ação"),
    ROMANCE("Romance", "Romance"),
    COMEDIA("Comedy", "Comédia"),
    DRAMA("Drama", "Drama"),
    HORROR("Horror", "Horror"),
    FICCAO("Fiction","Ficção"),
    CRIME("Crime","Crime"),
    SEM_CATEGORIA(null,null);

    private final String categoriaImdb;
    private final String categoriaPortugues;

    Categoria(String categoriaImdb, String categoriaPortugues){
        this.categoriaImdb = categoriaImdb;
        this.categoriaPortugues = categoriaPortugues;
    }

    public static Categoria fromString(String text){
        for(Categoria categoria: Categoria.values()){
            if(categoria.categoriaImdb.equalsIgnoreCase(text) ){
                return categoria;
            }
        }
            throw new IllegalArgumentException("Nenhuma categoria encontrada para a Sring fornecida: "+ text);
    }

    public static Categoria fromPortugues(String text){
        for(Categoria categoria: Categoria.values()){
            if(categoria.categoriaPortugues.equalsIgnoreCase(text) ){
                return categoria;
            }
        }
            throw new IllegalArgumentException("Nenhuma categoria encontrada para a Sring fornecida: "+ text);
    }
}
