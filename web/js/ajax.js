var cartAmount = {
    change: function(input, status){        
        var product = input.attr("name").split("-")[1];
        if (status == "delete"){             
            var value = "0";
        } else if (status == "update") {            
            var value = input.val();            
        }
               
        $.ajax({
            url: "cart",
            method: "POST",
            data: {
                status: "amountChange",
                value: value,
                product: product
            },
            success: function() {
               $("#cart").load("cart?refresh=true");
            },
            error: function(){
                alert("WRONG");
            }
    
        });
    }
};