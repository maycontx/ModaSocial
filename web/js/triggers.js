// TO OPEN REGISTER FORM
$(document).on("click", "div[data-id='register-open']", function(){
   registerForm.show($(this)); 
});

$(document).on("click", "input[data-id='hide-modal']", function(){
   fade.hide();
});

// REMOVE PRODUCT
$(document).on("click", "span[data-id='remove-product']", function(){
   product.remove($(this)); 
});
