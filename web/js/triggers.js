// TO OPEN REGISTER FORM
$(document).on("click", "div[data-id='register-open']", function(){
   registerForm.show($(this)); 
});

$(document).on("click", "input[data-id='hide-modal']", function(){
   fade.hide();
});
