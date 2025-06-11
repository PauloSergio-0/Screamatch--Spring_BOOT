package br.com.oracleone.ScreamMatch.model;

import br.com.oracleone.ScreamMatch.service.ConsultaMyMemory;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "series")
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NOM_SERIE",unique = true)
    private String titulos;
    @Column(name = "TOTAL_TEMPORADAS")
    private Integer totalTemporadas;
    @Column(name = "AVALIACAO_SERIE")
    private double avaliacao;
    @Column(name = "GENERO_SERIE")
    @Enumerated(EnumType.STRING)
    private Categoria genero;
    @Column(name = "ATORES_SERIE")
    private String atores;
    @Column(name = "SINOPSE_SERIE")
    private String sinopse;
    private String poster;

    @Transient
    @Column(name = "EPISODIO_SERIE")
    private List<Episodio> episodios = new ArrayList<>();

    public Serie(DadosSerie dadosSerie){
        try{
            this.titulos = dadosSerie.titulos();
            this.totalTemporadas = dadosSerie.totalTemporadas();
            this.avaliacao = OptionalDouble.of(Double.parseDouble(dadosSerie.avaliacao())).orElse(0);
            this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());
            this.atores = dadosSerie.atores();
            this.sinopse = ConsultaMyMemory.traducao(dadosSerie.sinopse().trim());
            this.poster = dadosSerie.poster();
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulos() {
        return titulos;
    }

    public void setTitulos(String titulos) {
        this.titulos = titulos;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        this.episodios = episodios;
    }

    @Override
    public String toString() {
        return
                "Genero=" + genero +
                ", titulos='" + titulos + '\'' +
                ", totalTemporadas=" + totalTemporadas +
                ", avaliacao=" + avaliacao +
                ", atores='" + atores + '\'' +
                ", sinopse='" + sinopse + '\'' +
                ", poster='" + poster + '\'' ;
    }
}
