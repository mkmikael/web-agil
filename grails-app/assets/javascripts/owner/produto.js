function saveTributo(e, id) {
    $.ajax('/web-agil/produto/saveTributo', {
        type: 'POST',
        data: { id: id, 'taxa': e.value.replace(',', '.').replace('%', '') },
        success: function(data) {
            console.log(data);
        }
    });
}
