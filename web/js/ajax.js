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

var address = {
    new: function(){
        var state = $("select[name='new-address-state']").val();
        var city = $("input[name='new-address-city']").val();
        var local = $("input[name='new-address-local']").val();
        var street = $("input[name='new-address-street']").val();
        var local2 = $("input[name='new-address-number']").val();
        var complement = $("input[name='new-address-complement']").val();
        var cep = $("input[name='new-address-code']").val();
        var phone = $("input[name='new-address-phone']").val();
        var name = $("input[name='new-address-name']").val();
        
        $.ajax({
            url: "step1",
            method: "POST",
            data: {
                name: name,
                status: "new-address",
                state: state,
                city: city,
                local: local,
                street: street,
                local2: local2,
                complement: complement,
                cep: cep,
                phone: phone
            },
            success: function() {
               $("#address").load("step1?refresh=true");
            },
            error: function(){
                alert("WRONG");
            }
    
        });
        
    },
    cep: function(cep){
        $.ajax({
            url: "step1",
            method: "POST",
            data: {
                status: "new-cep",
                newcep: cep                
            },
            success: function() {
               $("#address").load("step1?refresh=true");
            },
            error: function(){
                alert("WRONG");
            }
    
        });
        
    },
    changeVal: function(btn){
        var address = btn.attr("data-info");
        var shipping = btn.attr("shipping-value");
        $("input[name='address']").val(address);
        $("input[name='shipping-value']").val(shipping);
    }
};