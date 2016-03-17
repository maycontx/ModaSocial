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

