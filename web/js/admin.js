var product = {
    remove: function(btn){
        var id = btn.attr("data-info");
        
        if ( confirm("Tem certeza que deseja excluir este produto?") ){
            window.location.href = "product-manager?action=remove-product&&product="+id+"";
        }
        
    }    
};

var cupom = {
    generate: function(){
        var code = new Date().getFullYear();
        for ( var i = 0; i <= 10; i++ ){
            var num = Math.floor(Math.random() * 10 + 1);  
            switch (num){
                case 1:
                    var letter = "a";
                    break;
                case 2:
                    var letter = "C";
                    break;
                case 3:
                    var letter = "Z";
                    break;
                case 4:
                    var letter = "3";
                    break;
                case 5:
                    var letter = "C";
                    break;
                case 6:
                    var letter = "X";
                    break;
                case 7:
                    var letter = "6";
                    break;
                case 8:
                    var letter = "7";
                    break;
                case 9:
                    var letter = "1";
                    break;
                case 10:
                    var letter = "9";
                    break;
            }
            code += letter;          
            
        }
        
        $("input[data-reactid='cupom-generate']").val(code);
             
    },
    remove: function(btn){
        var id = btn.attr("data-info");
        
        if ( confirm("Tem certeza que deseja excluir este cupom?") ){
            window.location.href = "cupom-manager?action=remove-cupom&&cupom="+id+"";
        }
        
    }    
};

price = {
    update: function(){
        var modal = $("#price-modal");
        
        var radio1 = $("input[data-id='new']");
        var radio2 = $("input[data-id='update']");
        
        var newStock = $("input[data-id='new-stock']").val();
        var newPrice = $("input[data-id='new-price']").val();
        newStock = parseInt(newStock);
        newPrice = parseFloat(newPrice);
        
        var price = $("input[name='product-price']"); 
        var stock = $("input[name='product-stock']");
        
        if ( radio1.prop("checked")){
            
            newPrice += (newPrice * (36.25 / 100));
            price.val(newPrice.toFixed(2));
            stock.val(newStock);
            modal.modal("hide");
            
        }else{
            var oldStock = $("input[data-id='old-stock']").val();
            var oldPrice = $("input[data-id='old-stock']").val();
            oldStock = parseInt(oldStock);
            oldPrice = parseFloat(oldPrice);
            
            var previewPrice = ((oldStock * oldPrice) + newStock * new Price);
            previewPrice += (previewPrice * (36.25 / 100));
            price.val(previewPrice.toFixed(2));
            stock.val(oldStock + newStock);
            modal.modal("hide");
            
        }
    }    
};