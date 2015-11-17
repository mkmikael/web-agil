package web.agil

class TipoUnidade {

    static TipoUnidade UNI
    static TipoUnidade CXA

    static UNI() {
        if (UNI == null) {
            UNI = TipoUnidade.get(1)
        }
    }

    static CXA() {
        if (CXA == null) {
            CXA = TipoUnidade.get(2)
        }
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
