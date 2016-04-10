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

// AMOUNT CART CHANGE
$(document).on("change", "input[data-id='amount-cart']", function(){   
    cartAmount.change($(this), "update");
});

// AMOUNT REMOVER CART
$(document).on("click", "span[data-id='remove-amount-cart']", function(){   
    cartAmount.change($(this), "delete");
});

// NEW ADDRESS TRIGGER
$(document).on("click", "button[data-id='new-address']", function(){  
    address.new();
});

//CEP TRIGGER
$(document).on("keyup", "input[name='new-address-code']", function(){  
    if ( $(this).val().length == 9 )
        address.cep($(this).val());
});




