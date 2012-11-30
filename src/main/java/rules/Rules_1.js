//Variables.
var invoice;
var i;  //i is the Invoice object. Values will be added during runtime in ValidationProcess.java and JSON.
var errorMessages = [];

//Adressname is required
function adresseNameRule(){
        if(i.adresse.name == null){
            errorMessages.push("error.adresse.name.required");
        }
}

//Adressortname is optional
function adresseOrtNameRule(){
        if(i.adresse.ort.name != null){
            if(i.adresse.ort.name.indexOf("AT-") == -1){
                errorMessages.push("error.adresse.ort.name.startWithAT");
            }      
        }
}

//Adressortplz is required
function adresseOrtPlzRule(){ 
        if(i.adresse.ort.plz == null){
            errorMessages.push("error.adresse.ort.plz.required");
        }
}

//Betrag is optional
function betragRule(){
        if(i.betrag > 100){
            errorMessages.push("error.betrag.higher");
        }
}
    
//zweck is required
function zweckRule(){
        if(i.zweck == null){
            errorMessages.push("error.zweck.required");
        } else {
            if(i.zweck.indexOf("Kundennummer") == -1){
                errorMessages.push("error.zweck.customer.required");
            } else {
                var re = /^[-]?\d*\.?\d*$/;
                var filled = /.+/;
                if(!i.zweck.substring(12).match(re) || !i.zweck.substring(12).match(filled)){
                    errorMessages.push("error.zweck.customer.required");
                }
            }
        } 
}

//ust is optional
function ustRule(){
        if(i.ust > 20){
            errorMessages.push("error.ust.higher");
        }
}

function adresseNameUstRule(){
        if(i.adresse.name != null){
            if(i.adresse.name == "POST AG"){
                if(i.ust != 20){
                    errorMessages.push("error.ust.recipientIsPOST_AG");
                }
            }
        }
}

function callFunctions(){
    adresseNameRule();
    adresseOrtNameRule();
    adresseOrtPlzRule();
    zweckRule();
    ustRule();
    betragRule();
    adresseNameUstRule();
    return errorMessages; //return an Array with all errors.
}

function evaluation(){
    i = eval('(' + invoice + ')');
}