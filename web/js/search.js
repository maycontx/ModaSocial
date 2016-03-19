function Search(){
    this.brand = null;
    this.price = null;
    this.category = null;
    this.lastComponents = [];
};

Search.prototype = {    
    onFilterChange: function(){
        this.reverse();
        
        var productAmount = $("span[data-reactid='search-brand']").size();
        
        var parentClass = this;
        if ( this.brand != null ){       
            $("span[data-reactid='search-brand']").each(function(){
                var boxText = $(this).text().trim();
                var productBox = $(this).closest("div[data-id='search-product']");
                if ( boxText != parentClass.brand && productBox.css("display") == "block" ){                    
                    productAmount--;
                    productBox.css("display", "none");
                }
            });          
        }
        
        if ( this.price != null ){       
            $("div[data-reactid='search-price']").each(function(){  
                var boxText = $(this).text().trim().split(" ")[1];               
                var productBox = $(this).closest("div[data-id='search-product']");
                if ( boxText != parentClass.price && productBox.css("display") == "block" ){ 
                    productAmount--;
                    productBox.css("display", "none");
                }
            });          
        }
        
        if ( this.category != null ){       
            $("span[data-reactid='search-category']").each(function(){
                var boxText = $(this).text().trim();
                var productBox = $(this).closest("div[data-id='search-product']");
                if ( boxText != parentClass.category && productBox.css("display") == "block" ){                    
                    productAmount--;
                    productBox.css("display", "none");
                }
            });          
        }
        
        if ( productAmount <= 0 ){
            alert("Nenhum produto encontrado!");
        }
        
    },
    reverse: function(){
        $("div[data-id='search-product']").css("display", "block");
    },
    clearValue: function(value){
        return value.split("(")[0].slice(0, -1);        
    },    
    highlightComponent: function(component, index, object){
        var lastComponent = object.lastComponents[index];
        if ( lastComponent != null )
            lastComponent.attr("search-active", false);
       
        if ( component != null ){            
            component.attr("search-active", true);
        
            component.animate({
               "text-indent": "20px" 
            });
            component.css({
               "font-weight": "bold",
               "color": "#000000"
            });
        }  
        
        if ( lastComponent != null && lastComponent.attr("search-active") != "true" ){
           
            
            lastComponent.css({
                "text-indent": "10px",
                "font-weight": "normal",
                "color": "#777777"
             });            
        }
        
        
    }
};

$(document).ready(function(){
    
    var search = new Search();
   
    $(document).on("click", "div[data-id='search-brand']", function(){ 
        var value = $(this).text().trim();        
        value = search.clearValue(value);
        if ( $(this).attr("search-active") == "true"){
            search.brand = null;
            search.highlightComponent(null, 0, search);
        }
        else{
            search.brand = value; 
            search.highlightComponent($(this), 0, search);            
        }            
        search.lastComponents[0] = $(this);
        search.onFilterChange();
    });

    // TO HIDE MODAL
    $(document).on("click", "div[data-id='search-price']", function(){
        var value = $(this).text().trim();        
        value = search.clearValue(value);
        if ( $(this).attr("search-active") == "true"){
            search.price = null;
            search.highlightComponent(null, 1, search);
        }
        else{
            search.price = value; 
            search.highlightComponent($(this), 1, search);            
        }             
        search.lastComponents[1] = $(this);
        search.onFilterChange();
    });

    // REMOVE PRODUCT
    $(document).on("click", "div[data-id='search-category']", function(){       
        var value = $(this).text().trim();        
        value = search.clearValue(value);
        if ( $(this).attr("search-active") == "true"){
            search.category = null;
            search.highlightComponent(null, 2, search);
        }
        else{
            search.category = value; 
            search.highlightComponent($(this), 2, search);            
        }                   
        
        search.lastComponents[2] = $(this);
        search.onFilterChange();
    });    
});