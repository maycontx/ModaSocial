var fade = {
    show: function(){
        var fade = $("div[data-id='fade']");
        fade.css("display", "block");
    },
    hide: function(){
        var fade = $("div[data-id='fade']");       
        fade.children().each(function(){
            $(this).css("display", "none");
        });
        fade.css("display", "none");
    }
};

var loginForm = {
    show: function(btn){
        var id = btn.attr("data-id");
        var component = $("div[data-reactid='" + id + "']");
        fade.show();
        component.slideDown();        
    }
};

var registerForm = {
    show: function(btn){
        var id = btn.attr("data-id");
        var component = $("div[data-reactid='" + id + "']");
        fade.show();
        component.slideDown();   
        
    }
};

var rating = {
    selectContent: function(btn){
        var rate = btn.attr("data-info");        
        var inputRate = $("input[name='rating-rate']");
        var inputComment = $("textarea[name='rating-comment']");
        
        $("span[data-reactid='fav-star']").each(function(){
            if ( $(this).attr("data-info") <= rate ){
                $(this).removeClass("glyphicon-star-empty");
                $(this).addClass("glyphicon-star");
            }else{
                $(this).removeClass("glyphicon-star");
                $(this).addClass("glyphicon-star-empty");
            }
        });
        inputRate.val(rate);
        
    }
};

var feature = {
    more: function(btn){
        var featureBox = $("div[data-info='first']");
        var content = featureBox.closest(".form-row");
        var clone = featureBox.children(".col-lg-10").clone();
        
        clone.find("input").val("");
        
        var cloneContent = $("<div>");
        cloneContent.addClass("col-lg-3");
        cloneContent.attr("data-id", "feature");
        cloneContent.append(clone);
        
        var button = $("<button>");
        button.attr("data-id", "remove-feature");
        button.attr("type", "button");
        button.css("background-color", "#A54E4E");
        
        var span = $("<span>");
        span.addClass("glyphicon glyphicon-remove");
        
        button.append(span);
        
        var buttonContent = $("<div>");
        buttonContent.addClass("col-lg-2");
        
        buttonContent.append(button);
        
        content.append(cloneContent);
        cloneContent.append(buttonContent);
    },
    remove: function(btn){
        var box = btn.closest("div[data-id='feature']");
        box.remove();
    },
    submit: function(){
        var i = 1;
        $("div[data-id='feature']").each(function(e){
            var field = $(this).find("input[name='feature-field']");
            var value = $(this).find("input[name='feature-value']");
            
            if ( field.val().trim() != "" && value.val().trim() != ""){
                field.attr("name", "feature-field" + i);
                value.attr("name", "feature-value" + i);                 
                i++;
                
            }
            
            $("input[name='feature-amount']").val(i);
           
            
        });   
    }
};

