function chama_funcao(){

    somar();

}
function somar(){

    var num1 = parseInt(document.getElementById("num1_1").value);
    var num2 = parseInt(document.getElementById("num2_1").value);
    
    var soma = num1 + num2;
    
    document.getElementById("resp_1").innerHTML = `${num1} + ${num2} = ${soma}<br>`;
    }


    function somar2(num1,num2){

        var soma = num1 + num2;
        
        document.getElementById("resp_2").innerHTML = `${num1} + ${num2} = ${soma}<br>`;
        }