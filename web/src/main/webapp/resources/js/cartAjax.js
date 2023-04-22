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
            contentType : 'application/json; charset=utf-8',
           data: data,
        }).done(function(data) {
            $('#successes').text(data);
            $('#error').text("");
        }).fail(function(data) {
            $('#error').text("Error while adding to cart");
            $('#'+phoneIdParam+'_error').text("Quantity can't be zero or negative");
            $('#successes').text("");
    });
}