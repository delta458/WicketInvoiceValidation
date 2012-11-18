//Variables.
var invoice;
var i = eval('(' + invoice + ')');  //i is the Invoice object. Values will be added during runtime in ValidationProcess.java and JSON.
var errorMessages = [];

//Functions. Custom rules can be defined here.
function priceRule(){
    if(i.betrag == null){
        errorMessages.push("error.price.required");
    } else {
        if(i.betrag > 100){
            errorMessages.push("error.price.higher");
        }
    }
}

function recipientRule(){
    if(i.betrag > 10){
        if(i.adresse != null){
            if(i.adresse.length > 10){
                errorMessages.push("error.recipient");
            }
        }
    }
}

function detailsRule(){
    if(i.zweck == null){
        errorMessages.push("error.details.required");
    } else {
        if(i.zweck.indexOf("Kundennummer") == -1){
            errorMessages.push("error.details.customerid");
        } else {
            var re = /^[-]?\d*\.?\d*$/;
            var filled = /.+/;
            if(!i.zweck.substring(12).match(re) || !i.zweck.substring(12).match(filled)){
                errorMessages.push("error.details.customerid.number");
            }
        }
    }
}

function taxRule(){
    if(i.Ust == 10 || i.Ust == null){
        if(i.adresse == "POST AG"){
            errorMessages.push("error.tax.recipientIsPOST_AG");
        }
    }
}

//if u need to define a new rule, use this template
function templateRule(){
    //if required fill in this if clause with an error
    if(i.template == null){
        errorMessages.push("error.template");
    }
    //if optional fill in this if clause with rules, set return in require block to ""
    else {
        if(i.template.length > 200){
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