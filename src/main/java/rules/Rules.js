//Variables.
var price;
var recipient;
var details;
var tax;

var errorMessages = [];

//Functions. Custom rules can be defined here.
function priceRule(){
    if(price == null){
        errorMessages.push("error.price.required");
    } else {
        if(price > 100){
            errorMessages.push("error.price.higher");
        }
    }
}

function recipientRule(){
    if(price > 10){
        if(recipient != null){
            if(recipient.length > 10){
                errorMessages.push("error.recipient");
            }
        }
    }
}

function detailsRule(){
    if(details == null){
        errorMessages.push("error.details.required");
    } else {
        if(details.indexOf("Kundennummer") == -1){
            errorMessages.push("error.details.customerid");
        } else {
            var re = /^[-]?\d*\.?\d*$/;
            var filled = /.+/;
            if(!details.substring(12).match(re) || !details.substring(12).match(filled)){
                errorMessages.push("error.details.customerid.number");
            }
        }
    }
}

function taxRule(){
    if(tax == 10){
        if(recipient == "POST AG"){
            errorMessages.push("error.tax");
        }
    }
}

//if u need to define a new rule, use this template
function templateRule(){
    //if required fill in this if clause with an error
    if(template == null){
        errorMessages.push("error.template");
    }
    //if optional fill in this if clause with rules, set return in require block to ""
    else {
        if(template.length > 200){
            errorMessages.push("error.template.suberror");
        }
    }
}

function callFunctions(){
    priceRule();
    recipientRule();
    detailsRule();
    taxRule();
    return errorMessages; //gibt ein Array mit allen Errors zurück
}