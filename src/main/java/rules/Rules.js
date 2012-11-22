//Variables.
var invoice;
var i;  //i is the Invoice object. Values will be added during runtime in ValidationProcess.java and JSON.
var errorMessages = [];

//Adressname is required
function adresseNameRule(){
    try{
        if(i.adresse.name == null){
            errorMessages.push("error.adresse.name.required");
        }
    } catch (e){
        errorMessages.push("error.adresse.name.required");
    }
}

//Adressortname is optional
function adresseOrtNameRule(){
    try{
        if(i.adresse.ort.name != null){
            if(i.adresse.ort.name.indexOf("AT-") == -1){
                errorMessages.push("error.adresse.ort.name.startWithAT");
            }      
        }
    } catch (e){
    //    errorMessages.push("error.adresse.ort.name.required");  //COMMENTED BECAUSE OPTIONAL
    }
}

//Adressortplz is required
function adresseOrtPlzRule(){ 
    try {
        if(i.adresse.ort.plz == null){
            errorMessages.push("error.adresse.ort.plz.required");
        }
    } catch (e) {
        errorMessages.push("error.adresse.ort.plz.required"); 
    }
}

//Betrag is optional
function betragRule(){
    try {
        if(i.betrag > 100){
            errorMessages.push("error.betrag.higher");
        }
        
    } catch (e){  
    //   errorMessages.push("JS Error: " + e + "!");
    }
}
    
//zweck is required
function zweckRule(){
    try {
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
    } catch (e){
    //  errorMessages.push("JS Error: " + e + "!");
    }
}

//ust is optional
function ustRule(){
    try {
        if(i.ust > 20){
            errorMessages.push("error.ust.higher");
        }
    }
    catch (e){
    //    errorMessages.push("JS Error: " + e + "!");
    }
}

function adresseNameUstRule(){
    try {
        if(i.adresse.name != null){
            if(i.adresse.name == "POST AG"){
                if(i.Ust != 10){
                    errorMessages.push("error.ust.recipientIsPOST_AG");
                }
            }
        }
    } catch (e){
     //   errorMessages.push("JS Error: " + e + "!");
    }
}

//if u need to define a new rule, use this template
function templateRule(){
    try {
    //DEFINE RULES HERE
    } catch (e){
    //    errorMessages.push("JS Error: " + e + "!");
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