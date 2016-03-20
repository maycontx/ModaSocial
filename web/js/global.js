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
}

