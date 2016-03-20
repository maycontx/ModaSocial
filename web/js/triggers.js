// TO OPEN REGISTER FORM
$(document).on("click", "div[data-id='register-open']", function(){
   registerForm.show($(this)); 
});

// TO OPEN LOGIN FORM
$(document).on("click", "div[data-id='login-open']", function(){  
   loginForm.show($(this)); 
});

// TO HIDE MODAL
$(document).on("click", "input[data-id='hide-modal']", function(){
   fade.hide();
});

// REMOVE PRODUCT
$(document).on("click", "span[data-id='remove-product']", function(){
   product.remove($(this)); 
});

// SEARCH TRIGGER
$(document).on("click", "span[data-id='search-trigger']", function(){   
   var form = $("form[name='search-form']");
   form.submit();
});

// RATING MOUSE OVER TRIGGER
$(document).on("click", "span[data-reactid='fav-star']", function(){   
    rating.selectContent($(this));
});
