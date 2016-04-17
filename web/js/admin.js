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