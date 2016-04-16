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
    
    var style = $("div[data-id='style']").attr("data-info");
    var styleSplited = style.split(",");
    $("input[type='checkbox']").each(function(){       
       for ( var i = 0; i < styleSplited.length; i++ ){       
           if ( $(this).attr("name") == styleSplited[i] )
               $(this).attr("checked", true);
       }

    });
    
    var collection = $("div[data-id='collection']").attr("data-info");
    var collectionSplited = collection.split(",");
    $("input[type='checkbox']").each(function(){        
       for ( var i = 0; i < collectionSplited.length; i++ ){       
           if ( $(this).attr("name") == collectionSplited[i] )
               $(this).attr("checked", true);
       }

    });
    
});