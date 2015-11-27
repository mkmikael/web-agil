package web.agil

class TipoUnidade {

    static TipoUnidade UNI
    static TipoUnidade CXA

    static TipoUnidade getUNI() {
        if ( !UNI )
            UNI = TipoUnidade.findByDescricao('UNI')
        return UNI
    }

    static TipoUnidade getCXA() {
        if ( !CXA )
            CXA = TipoUnidade.findByDescricao('CXA')
        return CXA
    }
    String descricao

    static constraints = {
    }

    String toString() {
        descricao
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        TipoUnidade that = (TipoUnidade) o

        if (descricao != that.descricao) return false

        return true
    }

    int hashCode() {
        return (descricao != null ? descricao.hashCode() : 0)
    }
}
