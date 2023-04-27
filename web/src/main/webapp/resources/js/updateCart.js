 $("#formUpdate").submit(function(event){
       event.preventDefault();
              alert("something happened 1");

       $.ajax({
           type : 'PUT',
           url : "cart",
           contentType: 'application/json; charset=utf-8',
           data : {
                "id" = 1001,
                "quantity" = 1
           },
           success : function(data, status, xhr){
              alert("something happened");
           },
           error: function(xhr, status, error){
             alert(error);
           }
       });
}