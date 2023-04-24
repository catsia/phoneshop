function CartAjax(phoneIdParam) {
    var quantity = $('#'+phoneIdParam+'_quantity').val();
    var jsonData = {
       "phoneId": phoneIdParam,
       "quantity": quantity
    };
    var data = JSON.stringify(jsonData);
    $.ajax({
            headers: {
                        "Accept": "application/json",
                        "Content-Type": "application/json"
            },
            type: "POST",
            url:"ajaxCart",
            dataType: "text",
            contentType: 'application/json; charset=utf-8',
           data: data,
        }).done(function(data) {
            $('#successes').text(data);
            $('#error').text("");
            $('#'+phoneIdParam+'_error').text("");
        }).fail(function(e) {
            $('#error').text("Error while adding to cart");
            $('#'+phoneIdParam+'_error').text(e.responseText);
            $('#successes').text("");
    });
}