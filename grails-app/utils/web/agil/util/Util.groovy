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

	static removeSpecialCaracter(str = "") {
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
}