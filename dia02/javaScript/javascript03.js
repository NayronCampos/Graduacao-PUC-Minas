var num1 = parseInt(prompt("Informe o primeiro número:"));
var num2 = parseInt(prompt("Informe o segundo número:"));

var soma = num1 + num2;

document.getElementById("resp").innerHTML = `${num1} + ${num2} = ${soma}`;