var num1 = prompt("Informe o primeiro número:");
var num2 = prompt("Informe o segundo número:");

var soma = parseInt(num1) + parseInt(num2);

document.getElementById("resp").innerHTML += `${num1} + ${num2} = ${soma}`;
