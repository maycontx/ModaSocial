var product = {
    remove: function(btn){
        var id = btn.attr("data-info");
        
        if ( confirm("Tem certeza que deseja excluir este produto?") ){
            window.location.href = "product-manager?action=remove-product&&product="+id+"";
        }
        
    }    
};