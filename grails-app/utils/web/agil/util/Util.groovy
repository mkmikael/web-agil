package web.agil.util

class Util {
	static generateCodigo(length, number) {
		def builder = new StringBuilder()
		if (!number || !length) return null
		def strNumber = number?.toString()
		for (int i = 0;i < length - strNumber.length(); i++) {
			builder << "0"
		}
		builder + number
	}

	static removeSpecialCaracter(String str) {
        if (str)
            return str
                    .replace('Á', 'A')
                    .replace('Ã', 'A')
                    .replace('Â', 'A')
                    .replace('Ê', 'E')
                    .replace('É', 'E')
                    .replace('Í', 'I')
                    .replace('Ó', 'O')
                    .replace('Ô', 'O')
                    .replace('Ú', 'U')
                    .replace("?", "")
                    .replace("Ç", "C")
                    ;
	}

    static onlyNumber(String str) {
        if (str) {
            str.replaceAll("[^\\d]", '')
        }
    }

    static maskTelefone(String telefone) { // TODO - implementar
        if (telefone) {
            try {
                return new StringBuilder(telefone)
                        .insert(3, '.')
                        .insert(7, '.')
                        .insert(11, '-')
            } catch (Exception e) {
                e.printStackTrace()
                return telefone
            }
        }
    }

    static maskCpf(String cpf) {
        if (cpf) {
            try {
                return new StringBuilder(cpf)
                        .insert(3, '.')
                        .insert(7, '.')
                        .insert(11, '-')
            } catch (Exception e) {
                e.printStackTrace()
                return cpf
            }
        }
    }

    static maskCnpj(String cnpj) {
        if (cnpj) {
            try {
                return new StringBuilder(cnpj)
                        .insert(2, '.')
                        .insert(6, '.')
                        .insert(10, '/')
                        .insert(15, '-')
            } catch (Exception e) {
                e.printStackTrace()
                return cnpj
            }
        }
    }
}