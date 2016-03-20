$(document).ready(function(){
    
    var rateBox = $("div[data-id='rate-box']");
    rateBox.each(function(){
        var rate = $(this).attr("data-info");
        $(this).children("span").each(function(){
            var base = $(this).attr("data-info");
            if ( base <= rate ){
               $(this).removeClass("glyphicon-star-empty");
               $(this).addClass("glyphicon-star");
            }else{
                $(this).removeClass("glyphicon-star");
                $(this).addClass("glyphicon-star-empty");               
           }
       });
    });
    
});